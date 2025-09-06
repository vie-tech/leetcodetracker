package com.leetTracker.leetcodeTracker.dto;

import java.util.List;
import java.util.Map;

public record EditProblemRequest(String problemName,
                                 int problemNumber,
                                 List<String> topics,
                                 String timeComplexity,
                                 Map<String, String> solution,
                                 List<String> notes) {
}
