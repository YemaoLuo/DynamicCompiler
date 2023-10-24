package io.github.yemaoluo.factory;

import io.github.yemaoluo.domain.CompileArgs;

public class CompileArgsFactory {

    private CompileArgs args;

    public static CompileArgsFactory builder() {
        CompileArgsFactory factory = new CompileArgsFactory();
        CompileArgs args = new CompileArgs();
        args.setClassName("");
        factory.args = args;
        return factory;
    }

    public CompileArgsFactory code(String code) {
        args.setCode(code);
        return this;
    }

    public CompileArgsFactory className(String className) {
        args.setClassName(className);
        return this;
    }

    public CompileArgs build() {
        return args;
    }
}
