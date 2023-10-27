package io.github.yemaoluo.domain.java;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JavaRunArgs {

    private Class clazz;
    private String methodName;
    private Class inputType;
    private Class outputType;
    private List<Object> args;
}
