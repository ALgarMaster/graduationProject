package com.example.graduationProject.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomInlineKeyboardMarkup extends InlineKeyboardMarkup {

    List<List<InlineKeyboardButton>> rowsInLine;


    CustomInlineKeyboardMarkup(){

        rowsInLine = new ArrayList<>();
    }



    public InlineKeyboardMarkup addDefaultLine(InlineKeyboardMarkup inlineKeyboard){

        InlineKeyboardButton backButton = createInlineKeyboardButtonSetTextAndSetCallBack("Назад", "/back");
        InlineKeyboardButton contactSellerButton = createInlineKeyboardButtonSetTextAndSetCallBack("Cвязь с продавцом", "/contactseller");
        InlineKeyboardButton exitButton = createInlineKeyboardButtonSetTextAndSetCallBack("Отменити заказ", "/exit");

        addRowsInLine(backButton,  exitButton);
        addRowsInLine(contactSellerButton);
        inlineKeyboard.setKeyboard(rowsInLine);

        return inlineKeyboard;
    }

    public InlineKeyboardMarkup typeInlineKeyboard(InlineKeyboardMarkup inlineKeyboard){

        InlineKeyboardButton basketButton = createInlineKeyboardButtonSetTextAndSetCallBack("Корзина", "/basket");
        InlineKeyboardButton palletButton = createInlineKeyboardButtonSetTextAndSetCallBack("Поддон", "/pallet");
        InlineKeyboardButton bouquetButton = createInlineKeyboardButtonSetTextAndSetCallBack("Букет", "/bouquet");

        addRowsInLine(basketButton, palletButton, bouquetButton);
        inlineKeyboard = addDefaultLine(inlineKeyboard);

        return inlineKeyboard;
    }

    public InlineKeyboardMarkup sizeInlineKeyboard(InlineKeyboardMarkup inlineKeyboard){

        InlineKeyboardButton smallButton = createInlineKeyboardButtonSetTextAndSetCallBack("Маленький", "/small");
        InlineKeyboardButton averageButton = createInlineKeyboardButtonSetTextAndSetCallBack("Средний", "/average");
        InlineKeyboardButton bigButton = createInlineKeyboardButtonSetTextAndSetCallBack("Большой", "/big");

        addRowsInLine(smallButton, averageButton, bigButton);
        inlineKeyboard = addDefaultLine(inlineKeyboard);

        return inlineKeyboard;
    }

    public InlineKeyboardMarkup genderInlineKeyboard(InlineKeyboardMarkup inlineKeyboard){

        InlineKeyboardButton himButton = createInlineKeyboardButtonSetTextAndSetCallBack("Для него", "/for_him");
        InlineKeyboardButton herButton = createInlineKeyboardButtonSetTextAndSetCallBack("Для неё", "/for_her");
        InlineKeyboardButton multipurposeButton = createInlineKeyboardButtonSetTextAndSetCallBack("Универсальный", "/for_multipurpose");

        addRowsInLine(himButton, herButton, multipurposeButton);
        inlineKeyboard = addDefaultLine(inlineKeyboard);

        return inlineKeyboard;
    }

    public InlineKeyboardMarkup boxInlineKeyboard(InlineKeyboardMarkup inlineKeyboard){

        InlineKeyboardButton circleButton = createInlineKeyboardButtonSetTextAndSetCallBack("Круглая", "/circle_box");
        InlineKeyboardButton squareButton = createInlineKeyboardButtonSetTextAndSetCallBack("Квадратная", "/square_box");

        addRowsInLine(circleButton, squareButton);
        inlineKeyboard = addDefaultLine(inlineKeyboard);

        return inlineKeyboard;
    }

    public InlineKeyboardMarkup subjectInlineKeyboard(InlineKeyboardMarkup inlineKeyboard){



        return inlineKeyboard;
    }

    public InlineKeyboardMarkup colorPaletteInlineKeyboard(InlineKeyboardMarkup inlineKeyboard){
        InlineKeyboardButton red = createInlineKeyboardButtonSetTextAndSetCallBack("Красный", "/red");
        InlineKeyboardButton yellow = createInlineKeyboardButtonSetTextAndSetCallBack("Желтый", "/yellow");
        InlineKeyboardButton pink = createInlineKeyboardButtonSetTextAndSetCallBack("Розовый", "/pink");
        InlineKeyboardButton green = createInlineKeyboardButtonSetTextAndSetCallBack("Зеленый", "/green");
        InlineKeyboardButton sky = createInlineKeyboardButtonSetTextAndSetCallBack("Голубой", "/sky");
        InlineKeyboardButton blue = createInlineKeyboardButtonSetTextAndSetCallBack("Синий", "/blue");
        InlineKeyboardButton brown = createInlineKeyboardButtonSetTextAndSetCallBack("Коричневый", "/brown");
        InlineKeyboardButton violet = createInlineKeyboardButtonSetTextAndSetCallBack("Фиолетовый", "/violet");
        InlineKeyboardButton darkGreen = createInlineKeyboardButtonSetTextAndSetCallBack("Темно зеленый", "/darkGreen");
        InlineKeyboardButton purple = createInlineKeyboardButtonSetTextAndSetCallBack("Лиловый", "/purple");
        InlineKeyboardButton cream = createInlineKeyboardButtonSetTextAndSetCallBack("Кремовый", "/cream");

        addRowsInLine(red, yellow, pink);
        addRowsInLine(green,sky, blue);
        addRowsInLine(brown, violet );
        addRowsInLine(darkGreen);
        addRowsInLine(purple, cream);

        inlineKeyboard = addDefaultLine(inlineKeyboard);

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
