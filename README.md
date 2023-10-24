### For Java source code in String type. Dynamic Compiler will compile and run your sourcecode in memory.

#### Features:

    1. Compile and run java source code in memory.
    2. Only Compile java source code in memory.
    3. Only run provided class.

#### Usage example:

```java
@Test
@SneakyThrows
public void test_compile_and_run() {
        CompileAndRunArgs args = CompileAndRunArgsFactory.builder()
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
        CompileArgs args = CompileArgsFactory.builder()
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
        CompileArgs args = CompileArgsFactory.builder()
        .code(code)
        // this can be ignored as will auto-detect classname
        .className("TestCode")
        .build();
        Result result = dc.compile(args);
        Object clazz = result.getResult();

        RunArgs args2 = RunArgsFactory.builder()
        .clazz((Class) clazz)
        .inputType(int.class)
        .outputType(int.class)
        .methodName("add")
        .args(Arrays.asList(1, 2))
        .build();
        result = dc.run(args2);

        log.info(result.toString());
        }
```