package io.github.yemaoluo;

import lombok.SneakyThrows;

import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DynamicCompiler {

    @SneakyThrows
    public RunResult compile_and_run(CompileAndRunArgs args) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        if (args.getClassName().isEmpty()) {
            args.setClassName(extractClassName(args.getCode()));
        }

        InMemoryFileManager manager = new InMemoryFileManager(compiler.getStandardFileManager(null, null, null));

        List<JavaFileObject> sourceFiles = Collections.singletonList(new JavaSourceFromString(args.getClassName(), args.getCode()));

        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, diagnostics, null, null, sourceFiles);

        boolean result = task.call();

        if (!result) {
            List<Object> dataList = new ArrayList<>();
            diagnostics.getDiagnostics()
                    .forEach(dataList::add);
            return new RunResult(0, dataList, null);
        } else {
            ClassLoader classLoader = manager.getClassLoader(null);
            Class<?> clazz = classLoader.loadClass(args.getClassName());
            Object instance = clazz.newInstance();
            Object invoke = clazz.getMethod(args.getMethodName(), args.getInputType(), args.getOutputType())
                    .invoke(instance, args.getArgs().toArray());
            return new RunResult(1, null, invoke);
        }
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
