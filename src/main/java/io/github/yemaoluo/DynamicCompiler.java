package io.github.yemaoluo;

import io.github.yemaoluo.domain.constant.Constant;
import io.github.yemaoluo.domain.java.CompileAndRunArgs;
import io.github.yemaoluo.domain.java.CompileArgs;
import io.github.yemaoluo.domain.Result;
import io.github.yemaoluo.domain.java.JavaRunArgs;
import io.github.yemaoluo.domain.python.PythonRunArgs;
import io.github.yemaoluo.rewrite.InMemoryFileManager;
import io.github.yemaoluo.rewrite.JavaSourceFromString;
import lombok.SneakyThrows;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.util.*;

public class DynamicCompiler {


    private final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    private final PythonInterpreter interpreter = new PythonInterpreter();
    private final DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

    @SneakyThrows
    public Result compile_and_run(CompileAndRunArgs args) {
        if (args.getClassName().isEmpty()) {
            args.setClassName(extractClassName(args.getCode()));
        }

        InMemoryFileManager manager = new InMemoryFileManager(compiler.getStandardFileManager(null, null, null));
        List<JavaFileObject> sourceFiles = Collections.singletonList(new JavaSourceFromString(args.getClassName(), args.getCode()));
        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, diagnostics, null, null, sourceFiles);

        boolean result = task.call();

        if (!result) {
            List<Object> dataList = new ArrayList<>(diagnostics.getDiagnostics());
            return new Result(Constant.fail, dataList, null);
        } else {
            ClassLoader classLoader = manager.getClassLoader(null);
            Class<?> clazz = classLoader.loadClass(args.getClassName());
            Object instance = clazz.newInstance();
            Object invoke = clazz.getMethod(args.getMethodName(), args.getInputType(), args.getOutputType())
                    .invoke(instance, args.getArgs().toArray());
            return new Result(Constant.success, null, invoke);
        }
    }

    @SneakyThrows
    public Result compile(CompileArgs args) {
        if (args.getClassName().isEmpty()) {
            args.setClassName(extractClassName(args.getCode()));
        }

        InMemoryFileManager manager = new InMemoryFileManager(compiler.getStandardFileManager(null, null, null));
        List<JavaFileObject> sourceFiles = Collections.singletonList(new JavaSourceFromString(args.getClassName(), args.getCode()));
        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, diagnostics, null, null, sourceFiles);

        boolean result = task.call();

        if (!result) {
            List<Object> dataList = new ArrayList<>(diagnostics.getDiagnostics());
            return new Result(Constant.fail, dataList, null);
        } else {
            ClassLoader classLoader = manager.getClassLoader(null);
            Class<?> clazz = classLoader.loadClass(args.getClassName());
            return new Result(Constant.success, null, clazz);
        }
    }

    @SneakyThrows
    public Result run(JavaRunArgs args) {
        Class<?> clazz = args.getClazz();
        Object instance = clazz.newInstance();
        Object invoke = clazz.getMethod(args.getMethodName(), args.getInputType(), args.getOutputType())
                .invoke(instance, args.getArgs().toArray());
        return new Result(1, null, invoke);
    }

    @SneakyThrows
    public Result run(PythonRunArgs args) {
        try {
            interpreter.exec(args.getCode());
        } catch (Exception e) {
            return new Result(Constant.fail, Collections.singletonList(e.getMessage()), null);
        }

        Map<String, Object> result = new HashMap<>();
        List<Object> outputParamList = new ArrayList<>();
        for (String outputParam : args.getOutputParams()) {
            outputParamList.add(interpreter.get(outputParam));
        }
        result.put(Constant.outputParams, outputParamList);

        Map<String, Object> outputResultMap = new HashMap<>();
        args.getInputParams().forEach((k, v) -> {
            PyObject method = interpreter.get(k);
            PyObject[] params = new PyObject[v.size()];
            for (int i = 0; i < v.size(); i++) {
                Map<Class, Object> classObjectMap = v.get(i);
                Class<?> paramType = classObjectMap.keySet().iterator().next();
                Object paramValue = classObjectMap.get(paramType);
                params[i] = Py.java2py(paramValue);
            }
            PyObject invoke = method.__call__(params);
            outputResultMap.put(k, invoke);
        });
        result.put(Constant.outputResult, outputResultMap);
        return new Result(Constant.success, null, result);
    }

    public String extractClassName(String sourceCode) {
        String className = "";

        String[] lines = sourceCode.split("\\r?\\n");

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("public class")) {
                int startIndex = line.indexOf("class") + 6;
                int endIndex = line.indexOf("{");
                className = line.substring(startIndex, endIndex).trim();
                break;
            }
        }

        return className;
    }
}
