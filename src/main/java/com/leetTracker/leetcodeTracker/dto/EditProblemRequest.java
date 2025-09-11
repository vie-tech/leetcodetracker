package com.leetTracker.leetcodeTracker.dto;

import java.util.List;
import java.util.Map;

public record EditProblemRequest(String problemName,
                                 int problemNumber,
                                 List<String> topics,
                                 String timeComplexity,
                                 String spaceComplexity,
                                 Map<String, String> solution,
                                 String status,
                                 String difficulty,
                                 List<String> notes) {
}
