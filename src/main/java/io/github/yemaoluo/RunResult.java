package io.github.yemaoluo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RunResult {

    private int flag;
    private List<Object> errorMsg;
    private Object result;
}
