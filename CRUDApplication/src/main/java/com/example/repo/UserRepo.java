package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.CRUDApplication.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

}
