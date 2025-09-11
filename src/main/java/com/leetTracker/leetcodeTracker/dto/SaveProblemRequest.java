package com.leetTracker.leetcodeTracker.dto;

import com.leetTracker.leetcodeTracker.enums.ProblemDifficulty;
import com.leetTracker.leetcodeTracker.enums.ProblemStatus;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

public record SaveProblemRequest(@NotNull String problemName,
                                 @NotNull int problemNumber,
                                 @NotNull List<String> topics,
                                 @NotNull String timeComplexity,
                                 @NotNull Map<String, String> solution,
                                 @NotNull List<String> notes,
                                 @NotNull ProblemDifficulty difficulty,
                                 @NotNull ProblemStatus status,
                                 @NotNull String spaceComplexity
                                 ) {
}
