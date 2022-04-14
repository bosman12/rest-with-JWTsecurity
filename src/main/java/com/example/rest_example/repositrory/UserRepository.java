package com.example.rest_example.repositrory;

import com.example.rest_example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
