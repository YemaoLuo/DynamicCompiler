package io.github.yemaoluo.factory;

import io.github.yemaoluo.domain.RunArgs;

import java.util.List;

public class RunArgsFactory {

    private RunArgs args;

    public static RunArgsFactory builder() {
        RunArgsFactory factory = new RunArgsFactory();
        factory.args = new RunArgs();
        return factory;
    }

    public RunArgsFactory clazz(Class clazz) {
        args.setClazz(clazz);
        return this;
    }

    public RunArgsFactory methodName(String methodName) {
        args.setMethodName(methodName);
        return this;
    }

    public RunArgsFactory inputType(Class inputType) {
        args.setInputType(inputType);
        return this;
    }

    public RunArgsFactory outputType(Class outputType) {
        args.setOutputType(outputType);
        return this;
    }

    public RunArgsFactory args(List<Object> args) {
        this.args.setArgs(args);
        return this;
    }

    public RunArgs build() {
        return args;
    }
}
