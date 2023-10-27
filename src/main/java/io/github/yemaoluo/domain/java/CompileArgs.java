package io.github.yemaoluo.domain.java;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompileArgs {

    private String code;
    private String className;
}
