package com.example.graduationProject.controller;

import com.example.graduationProject.entities.Users;
import com.example.graduationProject.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    // Метод для сохранения пользователя
    public Users saveUpdateUser(Users user) {
        return usersService.saveUser(user);
    }

    // Метод для получения пользователя по ID
    public Users getUserById(int id) {
        return usersService.getUserById(id).get();
    }

    // Метод для получения всех пользователей
    public Iterable<Users> getAllUsers() {
        return usersService.getAllUsers();
    }

    // Метод для удаления пользователя по ID
    public void deleteUserById(int id) {
        usersService.deleteUserById(id);
    }

    // Метод для получения пользователя по chat_id
    public int getUserIdByChatId(int chatId) {
        return usersService.getUserByChatId(chatId).get().getChatId();
    }

    // Новый метод для получения или создания пользователя
    public int getOrCreateUserByChatId(long chatId, String nickName) {
        Optional<Users> userOptional = usersService.getUserByChatId((int)chatId);

        // Если пользователь существует, возвращаем его ID
        if (userOptional.isPresent()) {
            return userOptional.get().getIdUser();
        }

        // Если пользователь не существует, создаем нового
        Users newUser = new Users();
        newUser.setChatId((int)chatId);
        newUser.setNiсkName_(nickName);// Устанавливаем chatId для нового пользователя
        Users savedUser = usersService.saveUser(newUser); // Сохраняем нового пользователя
        return savedUser.getIdUser(); // Возвращаем ID нового пользователя
    }
}