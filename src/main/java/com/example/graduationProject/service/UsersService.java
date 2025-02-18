package com.example.graduationProject.service;

import com.example.graduationProject.entities.Users;
import com.example.graduationProject.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;


    // Стандартные CRUD методы
    public Users saveUser(Users user) {
        return usersRepository.save(user);
    }

    public Optional<Users> getUserById(int id) {
        return usersRepository.findById(id);
    }

    public Iterable<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public void deleteUserById(int id) {
        usersRepository.deleteById(id);
    }

    // Метод для поиска по chat_id
    public Optional<Users> getUserByChatId(int chatId) {
        return usersRepository.findByChatId(chatId);
    }
}