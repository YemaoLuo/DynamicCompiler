package io.github.yemaoluo.domain;

import lombok.Data;

import java.util.List;

@Data
public class RunArgs {

    private Class clazz;
    private String methodName;
    private Class inputType;
    private Class outputType;
    private List<Object> args;
}
