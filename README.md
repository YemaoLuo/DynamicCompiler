### For Java/Python source code in String type. Dynamic Compiler will compile and run your sourcecode in memory.

#### Features:

    1. Compile and run java source code in memory.
    2. Only Compile java source code in memory.
    3. Only run provided class.
    4. Only run Python source code in memory.

#### Usage example Java:

```java
    String code="""
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

        DynamicCompiler dc=new DynamicCompiler();

@Test
@SneakyThrows
public void test_compile_and_run(){
        CompileAndRunArgs args=CompileAndRunArgs.builder()
        .code(code)
        .inputType(int.class)
        .outputType(int.class)
        .methodName("add")
        // this can be ignored as will auto-detect classname
        .className("TestCode")
        .args(Arrays.asList(1,2))
        .build();
        Result result=dc.compile_and_run(args);

        log.info(result.toString());
        }

@Test
@SneakyThrows
public void test_compile(){
        CompileArgs args=CompileArgs.builder()
        .code(code)
        // this can be ignored as will auto-detect classname
        .className("TestCode")
        .build();
        Result result=dc.compile(args);
        Object clazz=result.getResult();

        log.info(result.toString());
        }

@Test
@SneakyThrows
public void test_run(){
        CompileArgs args=CompileArgs.builder()
        .code(code)
        // this can be ignored as will auto-detect classname
        .className("TestCode")
        .build();
        Result result=dc.compile(args);
        Object clazz=result.getResult();

        JavaRunArgs args2=JavaRunArgs.builder()
        .clazz((Class)clazz)
        .inputType(int.class)
        .outputType(int.class)
        .methodName("add")
        .args(Arrays.asList(1,2))
        .build();
        result=dc.run(args2);

        log.info(result.toString());
        }
```

#### Usage example Python:

```java
    String code = """
                def add(a, b):
                    return a + b
                
                a = 1
                b = a + 2
                print(a)
                print(b)
                """;

    DynamicCompiler dc = new DynamicCompiler();

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
```