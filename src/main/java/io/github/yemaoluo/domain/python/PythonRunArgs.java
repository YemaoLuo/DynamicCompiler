package io.github.yemaoluo.domain.python;

import lombok.Builder;
import lombok.Data;

import javax.print.attribute.standard.NumberOfInterveningJobs;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class PythonRunArgs {

    private String code;
    private List<String> outputParams;
    private Map<String, List<Map<Class, Object>>> inputParams;
    private Map<String, Object> outputResult;
}
