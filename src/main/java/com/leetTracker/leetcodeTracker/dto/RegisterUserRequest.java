package com.leetTracker.leetcodeTracker.dto;

import jakarta.validation.constraints.NotNull;

public record RegisterUserRequest(@NotNull String username,
                                  @NotNull String password,
                                  @NotNull String email
                                  ) {
}
