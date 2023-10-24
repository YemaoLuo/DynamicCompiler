package io.github.yemaoluo;

import lombok.SneakyThrows;
import org.junit.Test;

import java.util.Arrays;
import java.util.logging.Logger;

public class CompileTest {

    Logger log = Logger.getLogger(CompileTest.class.getName());

    @Test
    @SneakyThrows
    public void test_dynamic_compiler() {
        String code = "public class TestCode {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello world! from main.\");\n" +
                "    }\n" +
                "\n" +
                "    public int add(int a, int b) {\n" +
                "        return a + b;\n" +
                "    }\n" +
                "\n" +
                "    public void print() {\n" +
                "        System.out.println(\"Hello world! from print.\");\n" +
                "    }\n" +
                "}\n";

        DynamicCompiler dc = new DynamicCompiler();
        CompileAndRunArgs args = CompileAndRunArgsFactory.builder()
                .code(code)
                .inputType(int.class)
                .outputType(int.class)
                .methodName("add")
                // this can be ignored as will auto-detect classname
                .className("TestCode")
                .args(Arrays.asList(1, 2))
                .build();
        RunResult result = dc.compile_and_run(args);

        log.info(result.toString());
    }
}
