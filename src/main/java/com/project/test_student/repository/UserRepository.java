package com.project.test_student.repository;

import com.project.test_student.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {


    User findByUsername(String username);
}
