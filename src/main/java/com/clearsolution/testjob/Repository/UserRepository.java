package com.clearsolution.testjob.Repository;

import com.clearsolution.testjob.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByBirthDateBetween(LocalDateTime from, LocalDateTime to);
}
