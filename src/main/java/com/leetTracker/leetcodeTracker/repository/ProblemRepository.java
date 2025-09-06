package com.leetTracker.leetcodeTracker.repository;

import com.leetTracker.leetcodeTracker.model.Problem;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProblemRepository extends MongoRepository<Problem, ObjectId> {
    List<Problem> findByUserPublicId(String userPublicId);

    void deleteByProblemPublicIdAndUserPublicId(String problemPublicId, String userPublicId);
    Optional<Problem> findByProblemPublicIdAndUserPublicId(String problemPublicId, String userPublicId);
}
