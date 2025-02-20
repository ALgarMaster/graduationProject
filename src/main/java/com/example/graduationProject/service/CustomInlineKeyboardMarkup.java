package com.example.graduationProject.service;

import com.example.graduationProject.enumeration.STATEMESSAGE;
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

    public InlineKeyboardMarkup addInlineKeyboardBySTATEMASSEGE(InlineKeyboardMarkup inlineKeyboard, STATEMESSAGE statemessage){

        switch (statemessage){
            case TYPE:
                return typeInlineKeyboard(inlineKeyboard);
            case SIZE:
                return sizeInlineKeyboard(inlineKeyboard);
            case FOR_WHOM:
                return genderInlineKeyboard(inlineKeyboard);
            case SUBJECT:
                return subjectInlineKeyboard(inlineKeyboard);
            case COLOR:
                return colorPaletteInlineKeyboard(inlineKeyboard);
            case SUBTYPEBOX:
                return boxInlineKeyboard(inlineKeyboard);
            case SUBTYPEBOUQET:
                return subTypeBouquetInlineKeyboard(inlineKeyboard);
        }

//        InlineKeyboardButton backButton = createInlineKeyboardButtonSetTextAndSetCallBack("Назад", "/back");
//        InlineKeyboardButton contactSellerButton = createInlineKeyboardButtonSetTextAndSetCallBack("Cвязь с продавцом", "/contactseller");
//        InlineKeyboardButton exitButton = createInlineKeyboardButtonSetTextAndSetCallBack("Отменити заказ", "/exit");
//
//        addRowsInLine(backButton,  exitButton);
//        addRowsInLine(contactSellerButton);
//        inlineKeyboard.setKeyboard(rowsInLine);

        return inlineKeyboard;
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

        InlineKeyboardButton basketButton = createInlineKeyboardButtonSetTextAndSetCallBack("Корзина", "basket");
        InlineKeyboardButton palletButton = createInlineKeyboardButtonSetTextAndSetCallBack("Поддон", "pallet");
        InlineKeyboardButton bouquetButton = createInlineKeyboardButtonSetTextAndSetCallBack("Букет", "bouquet");
        InlineKeyboardButton boxButton = createInlineKeyboardButtonSetTextAndSetCallBack("Коробка", "box");

        addRowsInLine(basketButton, palletButton, boxButton);
        addRowsInLine(bouquetButton);
        inlineKeyboard = addDefaultLine(inlineKeyboard);

        return inlineKeyboard;
    }

    public InlineKeyboardMarkup sizeInlineKeyboard(InlineKeyboardMarkup inlineKeyboard){

        InlineKeyboardButton smallButton = createInlineKeyboardButtonSetTextAndSetCallBack("Маленький", "small");
        InlineKeyboardButton averageButton = createInlineKeyboardButtonSetTextAndSetCallBack("Средний", "medium");
        InlineKeyboardButton bigButton = createInlineKeyboardButtonSetTextAndSetCallBack("Большой", "large");

        addRowsInLine(smallButton, averageButton, bigButton);
        inlineKeyboard = addDefaultLine(inlineKeyboard);

        return inlineKeyboard;
    }

    public InlineKeyboardMarkup genderInlineKeyboard(InlineKeyboardMarkup inlineKeyboard){

        InlineKeyboardButton himButton = createInlineKeyboardButtonSetTextAndSetCallBack("Для него", "he");
        InlineKeyboardButton herButton = createInlineKeyboardButtonSetTextAndSetCallBack("Для неё", "she");
        InlineKeyboardButton multipurposeButton = createInlineKeyboardButtonSetTextAndSetCallBack("Универсальный", "nothing");

        addRowsInLine(himButton, herButton, multipurposeButton);
        inlineKeyboard = addDefaultLine(inlineKeyboard);

        return inlineKeyboard;
    }

    public InlineKeyboardMarkup boxInlineKeyboard(InlineKeyboardMarkup inlineKeyboard){

        InlineKeyboardButton circleButton = createInlineKeyboardButtonSetTextAndSetCallBack("Круглая", "circle_box");
        InlineKeyboardButton squareButton = createInlineKeyboardButtonSetTextAndSetCallBack("Квадратная", "square_box");

        addRowsInLine(circleButton, squareButton);
        inlineKeyboard = addDefaultLine(inlineKeyboard);

        return inlineKeyboard;
    }

    public InlineKeyboardMarkup subTypeBouquetInlineKeyboard(InlineKeyboardMarkup inlineKeyboard){

        InlineKeyboardButton roundButton = createInlineKeyboardButtonSetTextAndSetCallBack("Круглый букет", "round_bouquet");
        InlineKeyboardButton littleBagButton = createInlineKeyboardButtonSetTextAndSetCallBack("Букет кулечком", "little_bag_bouquet");


        addRowsInLine(roundButton, littleBagButton);
        inlineKeyboard = addDefaultLine(inlineKeyboard);

        return inlineKeyboard;
    }

    public InlineKeyboardMarkup subjectInlineKeyboard(InlineKeyboardMarkup inlineKeyboard){
        InlineKeyboardButton newYear = createInlineKeyboardButtonSetTextAndSetCallBack("Новый год", "newYear");
        InlineKeyboardButton feb23 = createInlineKeyboardButtonSetTextAndSetCallBack("23 февраля", "feb23");
        InlineKeyboardButton march8 = createInlineKeyboardButtonSetTextAndSetCallBack("8 марта", "march8");
        InlineKeyboardButton lastBell = createInlineKeyboardButtonSetTextAndSetCallBack("Последний звонок", "lastBell");
        InlineKeyboardButton sept1 = createInlineKeyboardButtonSetTextAndSetCallBack("1 сентября", "sept1");
        InlineKeyboardButton teacherDay = createInlineKeyboardButtonSetTextAndSetCallBack("День учителя", "teacherDay");
        InlineKeyboardButton educatorDay = createInlineKeyboardButtonSetTextAndSetCallBack("День воспитателя", "educatorDay");
        InlineKeyboardButton birthday = createInlineKeyboardButtonSetTextAndSetCallBack("День рождения", "birthday");
        InlineKeyboardButton medicDay = createInlineKeyboardButtonSetTextAndSetCallBack("День медработника", "medicDay");
        InlineKeyboardButton coachDay = createInlineKeyboardButtonSetTextAndSetCallBack("День тренера", "coachDay");
        InlineKeyboardButton weddingDay = createInlineKeyboardButtonSetTextAndSetCallBack("Свадьба", "wedding");
        InlineKeyboardButton corporateDay = createInlineKeyboardButtonSetTextAndSetCallBack("Корпоративный", "corporate");
        InlineKeyboardButton anyDay = createInlineKeyboardButtonSetTextAndSetCallBack("Свой праздник", "anyDay");

        addRowsInLine(newYear, feb23 );
        addRowsInLine(march8, lastBell);
        addRowsInLine(sept1, teacherDay);
        addRowsInLine(educatorDay, birthday);
        addRowsInLine( medicDay, coachDay);
        addRowsInLine(weddingDay, corporateDay);
        addRowsInLine(anyDay);

        inlineKeyboard = addDefaultLine(inlineKeyboard);

        return inlineKeyboard;
    }

    public InlineKeyboardMarkup colorPaletteInlineKeyboard(InlineKeyboardMarkup inlineKeyboard){
        InlineKeyboardButton red = createInlineKeyboardButtonSetTextAndSetCallBack("Красный", "red");
        InlineKeyboardButton yellow = createInlineKeyboardButtonSetTextAndSetCallBack("Желтый", "yellow");
        InlineKeyboardButton pink = createInlineKeyboardButtonSetTextAndSetCallBack("Розовый", "pink");
        InlineKeyboardButton green = createInlineKeyboardButtonSetTextAndSetCallBack("Зеленый", "green");
        InlineKeyboardButton sky = createInlineKeyboardButtonSetTextAndSetCallBack("Голубой", "sky");
        InlineKeyboardButton blue = createInlineKeyboardButtonSetTextAndSetCallBack("Синий", "blue");
        InlineKeyboardButton brown = createInlineKeyboardButtonSetTextAndSetCallBack("Коричневый", "brown");
        InlineKeyboardButton violet = createInlineKeyboardButtonSetTextAndSetCallBack("Фиолетовый", "violet");
        InlineKeyboardButton darkGreen = createInlineKeyboardButtonSetTextAndSetCallBack("Темно зеленый", "darkGreen");
        InlineKeyboardButton purple = createInlineKeyboardButtonSetTextAndSetCallBack("Лиловый", "purple");
        InlineKeyboardButton cream = createInlineKeyboardButtonSetTextAndSetCallBack("Кремовый", "cream");

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
