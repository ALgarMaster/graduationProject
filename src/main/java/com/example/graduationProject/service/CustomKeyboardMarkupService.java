package com.example.graduationProject.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class CustomKeyboardMarkupService extends ReplyKeyboardMarkup {

    public ReplyKeyboardMarkup beginKeyboard(ReplyKeyboardMarkup keyboard){
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow(){{
            add("Старт");
        }};

        keyboardRows.add(row);
        keyboard.setKeyboard(keyboardRows);

        return keyboard;
    }


    public ReplyKeyboardMarkup starterKeyboard(ReplyKeyboardMarkup keyboard){
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow(){{
            add("Связатся с продавцом");
        }};

        keyboardRows.add(row);
        keyboard.setKeyboard(keyboardRows);

        return keyboard;
    }
}
