package io.github.yemaoluo.factory;

import io.github.yemaoluo.domain.CompileAndRunArgs;

import java.util.List;

public class CompileAndRunArgsFactory {

    private CompileAndRunArgs args;

    public static CompileAndRunArgsFactory builder() {
        CompileAndRunArgsFactory factory = new CompileAndRunArgsFactory();
        CompileAndRunArgs args = new CompileAndRunArgs();
        args.setClassName("");
        factory.args = args;
        return factory;
    }

    public CompileAndRunArgsFactory code(String code) {
        args.setCode(code);
        return this;
    }

    public CompileAndRunArgsFactory className(String className) {
        args.setClassName(className);
        return this;
    }

    public CompileAndRunArgsFactory methodName(String methodName) {
        args.setMethodName(methodName);
        return this;
    }

    public CompileAndRunArgsFactory inputType(Class inputType) {
        args.setInputType(inputType);
        return this;
    }

    public CompileAndRunArgsFactory outputType(Class outputType) {
        args.setOutputType(outputType);
        return this;
    }

    public CompileAndRunArgsFactory args(List<Object> args) {
        this.args.setArgs(args);
        return this;
    }

    public CompileAndRunArgs build() {
        return args;
    }
}
