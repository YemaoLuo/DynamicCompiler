package io.github.yemaoluo.domain.java;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompileAndRunArgs {

    private String code;
    private String className;
    private String methodName;
    private Class inputType;
    private Class outputType;
    private List<Object> args;
}
