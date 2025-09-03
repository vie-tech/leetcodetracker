package com.leetTracker.leetcodeTracker.dto;

import jakarta.validation.constraints.NotNull;

public record LoginUserRequest(@NotNull String username, @NotNull String password) {
}
