package com.leetTracker.leetcodeTracker.repository;


import com.leetTracker.leetcodeTracker.model.UserAccount;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends MongoRepository<UserAccount, ObjectId> {
    UserAccount findByUsername(String username);
}
