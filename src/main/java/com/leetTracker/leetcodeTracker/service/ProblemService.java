package com.leetTracker.leetcodeTracker.service;

import com.leetTracker.leetcodeTracker.dto.EditProblemRequest;
import com.leetTracker.leetcodeTracker.dto.SaveProblemRequest;
import com.leetTracker.leetcodeTracker.model.Problem;
import com.leetTracker.leetcodeTracker.model.UserAccount;
import com.leetTracker.leetcodeTracker.repository.ProblemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final UserService userService;

    public void saveProblemSolution(SaveProblemRequest request) {
        UserAccount user = Objects.requireNonNull(
                userService.getCurrentUser(),
                "Could not identify this user"
        );
        Problem problem = Problem.builder()
                .problemNumber(request.problemNumber())
                .problemName(request.problemName())
                .timeComplexity(request.timeComplexity())
                .topics(request.topics())
                .notes(request.notes())
                .userPublicId(user.getUserPublicID())
                .solution(request.solution())
                .createdAt(LocalDate.now())
                .revisitDate(LocalDate.now().plusWeeks(1))
                .build();

        problemRepository.save(problem);
    }


    public List<Problem> getAllProblemsForUser() {
        UserAccount user = userService.getCurrentUser();
        if (user == null) {
            throw new RuntimeException("Could not identify this user");
        }

        return problemRepository.findByUserPublicId(user.getUserPublicID());

    }

    public void deleteSavedProblem(String problemPublicId) {
        UserAccount user = userService.getCurrentUser();
        List<Problem> problems = problemRepository.findByUserPublicId(user.getUserPublicID());
        boolean problemExistsForThisUser =
                problems.stream().anyMatch(p -> p.getProblemPublicId().equals(problemPublicId));
        if (!problemExistsForThisUser) {
            throw new Error("Problem does not exist for this user");
        }

        problemRepository.deleteByProblemPublicIdAndUserPublicId(problemPublicId, user.getUserPublicID());
    }

    public void editSavedProblem(String problemPublicId, EditProblemRequest request) {
        UserAccount user = userService.getCurrentUser();
        Problem problem = problemRepository.findByProblemPublicIdAndUserPublicId(problemPublicId, user.getUserPublicID()).orElseThrow(() -> new IllegalArgumentException("Problem not found"));
        if(request.problemName()!= null) problem.setProblemName(request.problemName());
        if(request.problemNumber()!= 0) problem.setProblemNumber(request.problemNumber());
        if(request.topics()!= null) problem.setTopics(request.topics());
        if (request.solution() != null) {
             Map<String, String> currentSolutions = problem.getSolution();
             if (currentSolutions == null) currentSolutions = new HashMap<>();

             // merge new/updated solutions
             currentSolutions.putAll(request.solution());
             problem.setSolution(currentSolutions);
         }
        if (request.notes() != null) problem.setNotes(request.notes());
        problemRepository.save(problem);
    }
}
