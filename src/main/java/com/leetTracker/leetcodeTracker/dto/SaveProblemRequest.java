package com.leetTracker.leetcodeTracker.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

public record SaveProblemRequest(@NotNull String problemName,
                                 @NotNull int problemNumber,
                                 @NotNull List<String> topics,
                                 @NotNull String timeComplexity,
                                 @NotNull Map<String, String> solution,
                                 @NotNull List<String> notes
) {
}
