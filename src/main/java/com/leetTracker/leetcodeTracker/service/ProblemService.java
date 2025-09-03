package com.leetTracker.leetcodeTracker.service;

import com.leetTracker.leetcodeTracker.dto.SaveProblemRequest;
import com.leetTracker.leetcodeTracker.model.Problem;
import com.leetTracker.leetcodeTracker.model.UserAccount;
import com.leetTracker.leetcodeTracker.repository.ProblemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final UserService userService;

    public void saveProblemSolution(SaveProblemRequest request) {
        UserAccount user = userService.getCurrentUser();
        if (user == null) {
            throw new RuntimeException("Could not identify this user");
        }
        Problem problem = Problem.builder()
                .problemNumber(request.problemNumber())
                .problemName(request.problemName())
                .timeComplexity(request.timeComplexity())
                .topics(request.topics())
                .userPublicId(user.getUserPublicID())
                .solution(request.solution())
                .createdAt(LocalDate.now())
                .revisitDate(LocalDate.now().plusWeeks(1))
                .build();

        problemRepository.save(problem);
    }
}
