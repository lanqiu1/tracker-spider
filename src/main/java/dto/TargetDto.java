package dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class TargetDto {
    private Map<String, List<String>> targets;
}
