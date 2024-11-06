package com.example.graduationProject.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomInlineKeyboardMarkup extends InlineKeyboardMarkup {

    ArrayList<List<InlineKeyboardButton>> rowsInLine;


    public InlineKeyboardMarkup addDefaultLine(InlineKeyboardMarkup inlineKeyboard){

        InlineKeyboardButton backButton = createInlineKeyboardButtonSetTextAndSetCallBack("Назад", "/back");
        InlineKeyboardButton contactSellerButton = createInlineKeyboardButtonSetTextAndSetCallBack("Cвязь с продавцом", "/contactseller");
        InlineKeyboardButton exitButton = createInlineKeyboardButtonSetTextAndSetCallBack("Отменити заказ", "/exit");

        addRowsInLine(backButton, contactSellerButton, exitButton);

        inlineKeyboard.setKeyboard(rowsInLine);

        return inlineKeyboard;
    }

    public InlineKeyboardMarkup typeInlineKeyboard(InlineKeyboardMarkup inlineKeyboard){

        InlineKeyboardButton basketButton = createInlineKeyboardButtonSetTextAndSetCallBack("Корзина", "/basket");
        InlineKeyboardButton palletButton = createInlineKeyboardButtonSetTextAndSetCallBack("Поддон", "/pallet");
        InlineKeyboardButton bouquetButton = createInlineKeyboardButtonSetTextAndSetCallBack("Букет", "/bouquet");

        addRowsInLine(basketButton, palletButton, bouquetButton);
        addDefaultLine(inlineKeyboard);

        return inlineKeyboard;
    }

    public InlineKeyboardMarkup sizeInlineKeyboard(InlineKeyboardMarkup inlineKeyboard){



        return inlineKeyboard;
    }

    public InlineKeyboardMarkup genderInlineKeyboard(InlineKeyboardMarkup inlineKeyboard){

        return inlineKeyboard;
    }

    public InlineKeyboardMarkup subjectInlineKeyboard(InlineKeyboardMarkup inlineKeyboard){

        return inlineKeyboard;
    }

    public InlineKeyboardMarkup colorPaletteInlineKeyboard(InlineKeyboardMarkup inlineKeyboard){

        return inlineKeyboard;
    }

    public InlineKeyboardButton createInlineKeyboardButtonSetTextAndSetCallBack(String text, String callBack){

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callBack);

        return button;
    }

    public void addRowsInLine(InlineKeyboardButton ... inputArrayList){
        List<InlineKeyboardButton> rowInLine = new ArrayList<>(Arrays.stream(inputArrayList).toList());
        rowsInLine.add(rowInLine);
    }


}
