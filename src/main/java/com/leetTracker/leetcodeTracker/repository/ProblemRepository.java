package com.leetTracker.leetcodeTracker.repository;

import com.leetTracker.leetcodeTracker.model.Problem;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProblemRepository extends MongoRepository<Problem, ObjectId> {

}
