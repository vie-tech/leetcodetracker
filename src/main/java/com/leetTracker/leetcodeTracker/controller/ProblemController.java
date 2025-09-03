package com.leetTracker.leetcodeTracker.controller;


import com.leetTracker.leetcodeTracker.dto.SaveProblemRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/problem")
@AllArgsConstructor
@Slf4j
public class ProblemController {

    @PostMapping("/save")
    public ResponseEntity<?> saveProblem(@Valid @RequestBody SaveProblemRequest request){
        return ResponseEntity.ok(Map.of("success", true, "message", "Problem saved successfully"));
    }

}
