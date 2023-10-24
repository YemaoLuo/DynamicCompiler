package io.github.yemaoluo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Result {

    private int flag;
    private List<Object> errorMsg;
    private Object result;
}
