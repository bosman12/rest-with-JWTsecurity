package com.example.rest_example.repositrory;

import com.example.rest_example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUserName(String userName);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);


}
