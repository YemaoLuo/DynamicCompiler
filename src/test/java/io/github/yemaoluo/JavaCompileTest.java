package io.github.yemaoluo;

import io.github.yemaoluo.domain.java.CompileAndRunArgs;
import io.github.yemaoluo.domain.java.CompileArgs;
import io.github.yemaoluo.domain.Result;
import io.github.yemaoluo.domain.java.JavaRunArgs;
import lombok.SneakyThrows;
import org.junit.Test;

import java.util.Arrays;
import java.util.logging.Logger;

public class JavaCompileTest {

    Logger log = Logger.getLogger(JavaCompileTest.class.getName());

    String code = """
            public class TestCode {

                public static void main(String[] args) {
                    System.out.println("Hello world! from main.");
                }

                public int add(int a, int b) {
                    return a + b;
                }

                public void print() {
                    System.out.println("Hello world! from print.");
                }
            }
            """;

    DynamicCompiler dc = new DynamicCompiler();

    @Test
    @SneakyThrows
    public void test_compile_and_run() {
        CompileAndRunArgs args = CompileAndRunArgs.builder()
                .code(code)
                .inputType(int.class)
                .outputType(int.class)
                .methodName("add")
                // this can be ignored as will auto-detect classname
                .className("TestCode")
                .args(Arrays.asList(1, 2))
                .build();
        Result result = dc.compile_and_run(args);

        log.info(result.toString());
    }

    @Test
    @SneakyThrows
    public void test_compile() {
        CompileArgs args = CompileArgs.builder()
                .code(code)
                // this can be ignored as will auto-detect classname
                .className("TestCode")
                .build();
        Result result = dc.compile(args);
        Object clazz = result.getResult();

        log.info(result.toString());
    }

    @Test
    @SneakyThrows
    public void test_run() {
        CompileArgs args = CompileArgs.builder()
                .code(code)
                // this can be ignored as will auto-detect classname
                .className("TestCode")
                .build();
        Result result = dc.compile(args);
        Object clazz = result.getResult();

        JavaRunArgs args2 = JavaRunArgs.builder()
                .clazz((Class) clazz)
                .inputType(int.class)
                .outputType(int.class)
                .methodName("add")
                .args(Arrays.asList(1, 2))
                .build();
        result = dc.run(args2);

        log.info(result.toString());
    }
}
