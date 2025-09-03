package com.leetTracker.leetcodeTracker.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Document(collection = "problems")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Problem {

    private ObjectId id;
    private String problemName;
    private List<String> topics;
    private String userPublicId;
    private int problemNumber;
    private Map<String, String> solution;
    private String timeComplexity;
    private LocalDate createdAt;
    private LocalDate revisitDate;
}
