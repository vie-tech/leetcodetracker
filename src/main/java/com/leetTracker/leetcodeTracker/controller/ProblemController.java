package com.leetTracker.leetcodeTracker.controller;


import com.leetTracker.leetcodeTracker.dto.SaveProblemRequest;
import com.leetTracker.leetcodeTracker.model.Problem;
import com.leetTracker.leetcodeTracker.service.ProblemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/problem")
@AllArgsConstructor
@Slf4j
public class ProblemController {

    private final ProblemService problemService;

    @PostMapping("/save")
    public ResponseEntity<?> saveProblem(@Valid @RequestBody SaveProblemRequest request) {
        problemService.saveProblemSolution(request);
        return ResponseEntity.ok(Map.of("success", true, "message", "Problem " +
                "saved successfully"));
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllSavedProblems() {
        List<Problem> problemList = problemService.getAllProblemsForUser();
        return ResponseEntity.ok(Map.of("success", true, "problems",
                problemList));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProblem(@Valid @RequestParam String problemId){
        problemService
        return ResponseEntity.ok(Map.of("success", true));
    }

}
