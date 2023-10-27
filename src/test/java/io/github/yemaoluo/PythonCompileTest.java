package io.github.yemaoluo;

import io.github.yemaoluo.domain.Result;
import io.github.yemaoluo.domain.java.CompileAndRunArgs;
import io.github.yemaoluo.domain.java.CompileArgs;
import io.github.yemaoluo.domain.java.JavaRunArgs;
import io.github.yemaoluo.domain.python.PythonRunArgs;
import lombok.SneakyThrows;
import org.junit.Test;
import org.python.core.Py;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class PythonCompileTest {

    Logger log = Logger.getLogger(PythonCompileTest.class.getName());

    DynamicCompiler dc = new DynamicCompiler();

    String code = """
            def add(a, b):
                return a + b
            
            a = 1
            b = a + 2
            print(a)
            print(b)
            """;

    @Test
    @SneakyThrows
    public void test_run() {
        PythonRunArgs args = PythonRunArgs.builder()
                .code(code)
                .inputParams(Map.of("add", Arrays.asList(Map.of(Integer.class, 1), Map.of(Integer.class, 2))))
                .outputParams(Arrays.asList("a", "b"))
                .build();
        Result run = dc.run(args);

        log.info(run.toString());
    }
}
