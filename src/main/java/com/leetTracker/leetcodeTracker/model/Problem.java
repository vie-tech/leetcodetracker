package com.leetTracker.leetcodeTracker.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Document(collection = "problems")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Problem {

    private ObjectId id;
    @Indexed(unique = true)
    private String problemName;
    @Builder.Default
    private String problemPublicId = UUID.randomUUID().toString();
    private List<String> topics;
    private List<String> notes;
    private String userPublicId;
    private int problemNumber;
    private Map<String, String> solution;
    private String timeComplexity;
    private LocalDate createdAt;
    private LocalDate revisitDate;
}
