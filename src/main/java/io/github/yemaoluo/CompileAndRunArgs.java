package io.github.yemaoluo;

import lombok.Data;

import java.util.List;

@Data
public class CompileAndRunArgs {

    private String code;
    private String className;
    private String methodName;
    private Class inputType;
    private Class outputType;
    private List<Object> args;
}
