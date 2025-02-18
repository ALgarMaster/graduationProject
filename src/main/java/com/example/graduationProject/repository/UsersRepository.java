package com.example.graduationProject.repository;

import com.example.graduationProject.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByChatId(int chatId);

}
