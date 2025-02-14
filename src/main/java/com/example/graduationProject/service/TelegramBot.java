package com.example.graduationProject.service;
import com.example.graduationProject.config.BotConfiguration;
import com.example.graduationProject.config.DBConfig;
import com.example.graduationProject.controller.ImagesController;
import com.example.graduationProject.controller.OrderController;
import com.example.graduationProject.controller.StageController;
import com.example.graduationProject.entities.Images;
import com.example.graduationProject.entities.Stage;
import com.example.graduationProject.enumeration.STATEMESSAGE;
import com.example.graduationProject.repository.ImagesRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.example.graduationProject.DAO.ImageDAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.graduationProject.enumeration.STATEMESSAGE.SIZE;
import static com.example.graduationProject.enumeration.STATEMESSAGE.TYPE;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot{

    private static final Logger log = LoggerFactory.getLogger(TelegramBot.class);
    final BotConfiguration botConfiguration;
    private ImagesController imagesController;
    private StageController stageController;
    private OrderController orderController;

    private DBConfig dbConfig;

    public TelegramBot(BotConfiguration configuration, DBConfig dbConfig, ImagesController imagesController, StageController stageController, OrderController orderController) throws SQLException {
        this.botConfiguration = configuration;
        this.dbConfig = dbConfig;
//        this.connection = dbConfig.getConnection(); // Получаем подключение через DBConfig
        this.imagesController = imagesController;
        this.stageController = stageController;
        this.orderController = orderController;
    }


    //поик по айди чата объекта заказа и дальнейшее взаимодействие через чат ади с заказом, запись и поиск.


    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatID = update.getMessage().getChatId();
            log.info("Chat id: "+ chatID);

            switch (messageText){
                case "/start":
                    try {
                        startCommandReceived(chatID, update.getMessage().getChat().getFirstName());
                    }catch (Exception e){
                        log.error("Error main bot" + e.getMessage());
                    }
                    break;
                case "/typeOrder":
                    try{
                        STATEMESSAGE statemessage = TYPE;
                        Stage stage = stageController.getStageByIDAlbumStateMessage(statemessage);
                        handleAlbumImages(stage.getIdAlbum(),chatID, stage.getTitle(), statemessage);

                        log.info("/typeOrder handled successfully.");

                    }catch (Exception e){
                        log.error("Error main bot" + e.getMessage());
                    }
                    break;
                case "/probeImage":
                    // сделать сообщение что выбирите изображение
                    //подождать пока его загрузять
                    // валидация изображения
                    // загрузка изображения  из массива байт и сохранение его в определенном месте на диске, с генерируемым именем
                    //сообщнеи с выбором альбома
                    //запрос на список существующих альбомов
                    //сообщение с результатом
                    //ожидание сообщения с номером альбома
                    //валидация ответа
                    //запись изображения в базу
                    // валидация записи
                    //конечный ответ о результате

                    String imagePath = "C:\\Users\\Stanislav\\Downloads\\23141.jpg";

                    // Create the CustomMultipartFile
                    CustomMultipartFile file = new CustomMultipartFile(imagePath);

                    ResponseEntity<String> response = null;
                    try {
                        response = imagesController.uploadImage(file, 5);
                        imagesController.uploadImage(file, 6);
                        imagesController.uploadImage(file, 7);
                        imagesController.uploadImage(file, 8);
                        imagesController.uploadImage(file, 9);
                        imagesController.uploadImage(file, 10);
                        log.info("Added writ"+response.getBody());
                    } catch (IOException e) {
                        log.error("Error main bot in /probeImage"+e.getMessage());
                        throw new RuntimeException(e);
                    }
                    break;
                    //запись в объект выбранного типа подарка и вызов этапа с размером
                case "/basket":
                    STATEMESSAGE statemessage = SIZE;
                    handleAlbumImages(6,chatID, stageController.getStageByID(6).getTitle(), SIZE);

                    log.info("/basket handled successfully.");
                    break;
                case "/pallet":
                    handleAlbumImages(7,chatID, stageController.getStageByID(7).getTitle(), SIZE);
                    log.info("/pallet handled successfully.");
                    break;
                case "/bouqet":
                    handleAlbumImages(8,chatID, stageController.getStageByID(8).getTitle(), SIZE);
                    log.info("/pallet handled successfully.");
                    break;
                case "/box":
                    handleAlbumImages(9,chatID, stageController.getStageByID(9).getTitle(), SIZE);
                    log.info("/pallet handled successfully.");
                    break;
                    //кейсы с размером, которые вызывают следкющую форму пренадлежности, и записывает данные в объект подарка
                case "/small":
                    break;
                case "/medium":
                    break;
                case "/large":
                    break;
                //кейсы для формы для подтипа букета
                case "/bouqet1":
                    break;
                case "/bouqet2":
                    break;
                //выбор пренадлежности к полу
                case "/he":
                    break;
                case "/she":
                    break;
                case "/nothing":
                    break;
                    //
                case "/NewYear":
                    break;

                case "/February23":
                    break;

                case "/March8":
                    break;

                case "/Lastbell":
                    break;

                case "/September1":
                    break;

                case "/TeachersDay":
                    break;

                case "/EducatorsDay":
                    break;

                case "/Birthday":
                    break;

                case "/CoachsDay":
                    break;

                case "/MedicalWorkersDay":
                    break;

                case "/wedding":
                    break;

                case "/corporate":
                    break;

                    //команды с цветами
                case "/red":
                    break;

                case "/yellow":
                    break;

                case "/pink":
                    break;

                case "/green":
                    break;

                case "/sky":
                    break;

                case "/brown":
                    break;

                case "/violet":
                    break;

                case "/darkGreen":
                    break;

                case "/purple":
                    break;

                case "/blue":
                    break;

                case "/cream":
                    break;

                case "/":
                    break;
//                case "/probeQueryImagesByAlbumId0":
//                    try {
//                        int probeAlbumId = 0;
//                        handleAlbumImages(probeAlbumId, chatID, "0 альбом");
//                        log.info("/probeQueryImagesByAlbumId handled successfully.");
//                    } catch (Exception e) {
//                        log.error("Error in /probeQueryImagesByAlbumId0: " + e.getMessage(), e);
//                    }
//                    break;
//                case "/probeQueryImagesByAlbumId1":
//                    try {
//                        int probeAlbumId = 1;
//                        handleAlbumImages(probeAlbumId, chatID, "1 альбом");
//                        log.info("/probeQueryImagesByAlbumId handled successfully.");
//                    } catch (Exception e) {
//                        log.error("Error in /probeQueryImagesByAlbumId1: " + e.getMessage(), e);
//                    }
//                    break;
//                case "/probeQueryImagesByAlbumId2":
//                    try {
//                        int probeAlbumId = 2;
//                        handleAlbumImages(probeAlbumId, chatID, "2 альбом");
//                        log.info("/probeQueryImagesByAlbumId handled successfully.");
//                    } catch (Exception e) {
//                        log.error("Error in /probeQueryImagesByAlbumId2: " + e.getMessage(), e);
//                    }
//                    break;
//                case "/probeQueryImagesByAlbumId3":
//                    try {
//                        int probeAlbumId = 3;
//                        handleAlbumImages(probeAlbumId, chatID, "3 альбом");
//                        log.info("/probeQueryImagesByAlbumId handled successfully.");
//                    } catch (Exception e) {
//                        log.error("Error in /probeQueryImagesByAlbumId3: " + e.getMessage(), e);
//                    }
//                    break;
//                case "/probeQueryImagesByAlbumId4":
//                    try {
//                        int probeAlbumId = 4;
//                        handleAlbumImages(probeAlbumId, chatID, "4 альбом");
//                        log.info("/probeQueryImagesByAlbumId handled successfully.");
//                    } catch (Exception e) {
//                        log.error("Error in /probeQueryImagesByAlbumId4: " + e.getMessage(), e);
//                    }
//                    break;


                case "/testConnectionToDB":
                    String responseMessage = testDatabaseConnection();
                    sendMessage(chatID, responseMessage);
                    break;

                default: sendMessage(chatID, "Ooooops, sorry, command was not recognized(((");
            }


        }
    }



    //функция формы выбора размера
    private void formBySTATEMESSAGE(int chatID, STATEMESSAGE statemessage){
        try{
            Stage stage = stageController.getStageByIDAlbumStateMessage(statemessage);
            handleAlbumImages(stage.getIdAlbum(),chatID, stage.getTitle(), statemessage);

            log.info("sizeForm handled successfully.");

        }catch (Exception e){
            log.error("Error main bot in sizeForm" + e.getMessage());
        }
    }

    //функция формы подтверждения


    private String testDatabaseConnection() {
        try (Connection connection = dbConfig.getConnection()) {
            // Если подключение успешно, возвращаем успешное сообщение
            return "Подключение к базе данных успешно!";
        } catch (SQLException e) {
            // Если ошибка подключения, возвращаем сообщение с ошибкой
            return "Ошибка подключения к базе данных: " + e.getMessage();
        }
    }
//
//    //добавление к SendPhoto альбома
//    private SendPhoto setPhotoListOnSendPhoto(SendPhoto sendPhoto, List<File> list){
//        ArrayList<File> arrayList = new ArrayList<>(list);
//
//        arrayList.forEach(images -> sendPhoto.setPhoto(new InputFile(images)));
//
//        return sendPhoto;
//    }

    //Сделать так, чтобы по множеству стейтмасседж подставлялась нужная клавиатура
    public void handleAlbumImages(int probeAlbumId, long chatID, String title, STATEMESSAGE statemessage) {
        try {
            // Получаем список изображений для альбома
            List<File> imagesFileList = imagesController.getImagesByAlbumId(probeAlbumId);
            for (File imageFile : imagesFileList) {
                log.info("Image path: " + imageFile.getAbsolutePath()); // Логируем полный путь к каждому изображению
            }

            // Если в альбоме только одно изображение
            if (imagesFileList.size() == 1) {
                sendSingleImage(imagesFileList.get(0),  chatID, title, statemessage);
            }
            // Если в альбоме несколько изображений
            else if (!imagesFileList.isEmpty()) {
                sendMultipleImages(imagesFileList, chatID, title, statemessage);
            }

            log.info("Album images handled successfully for albumId: " + probeAlbumId);
        } catch (Exception e) {
            log.error("Error in handleAlbumImages: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    // сделать так чтобы можно было менять подпись картинки
    // Функция для отправки одного изображения

    //Сделать так, чтобы по множеству стейтмасседж подставлялась нужная клавиатура
    private void sendSingleImage(File imageFile, long chatID, String title, STATEMESSAGE statemessage) {
        try {
            CustomInlineKeyboardMarkup inlineKeyboard = new CustomInlineKeyboardMarkup();


            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatID);
            sendPhoto.setPhoto(new InputFile(imageFile)); // Передаем изображение
            sendPhoto.setCaption(title);
            sendPhoto.setParseMode("Markdown");


            // Отправляем одно изображение
            //сделать свитч с стейтмами с клавиатурами
            sendPhoto.setReplyMarkup(inlineKeyboard.addInlineKeyboardBySTATEMASSEGE(inlineKeyboard,statemessage));
            execute(sendPhoto);
            log.info("Single image sent successfully.");
        } catch (Exception e) {
            log.error("Error sending single image: " + e.getMessage(), e);
        }
    }

    // сделать так чтобы можно было менять подпись альбома
    // Функция для отправки нескольких изображений
    //Сделать так, чтобы по множеству стейтмасседж подставлялась нужная клавиатура
    // Функция для отправки нескольких изображений с возможностью изменить подпись альбома
    private void sendMultipleImages(List<File> imagesFileList, long chatID, String title, STATEMESSAGE statemessage) {
        try {
            if (imagesFileList.isEmpty()) {
                log.error("No images to send.");
                return;
            }

            SendMediaGroup sendMediaGroup = new SendMediaGroup();
            sendMediaGroup.setChatId(chatID);

            List<InputMedia> mediaList = new ArrayList<>();

            for (int i = 0; i < imagesFileList.size(); i++) {
                File currentImage = imagesFileList.get(i);
                if (currentImage.exists()) {
                    InputMediaPhoto photo = new InputMediaPhoto();
                    photo.setMedia(currentImage, "image_" + i);

                    // Добавляем подпись только к первому изображению
                    if (i == 0) {
                        photo.setCaption(title);
                        photo.setParseMode("Markdown");
                    }

                    mediaList.add(photo);
                } else {
                    log.error("Image file does not exist: " + currentImage.getAbsolutePath());
                }
            }

            if (!mediaList.isEmpty()) {
                sendMediaGroup.setMedias(mediaList);
                execute(sendMediaGroup);
                log.info("Album sent successfully.");
            } else {
                log.error("No valid images to send.");
                return;
            }

            // Отправляем клавиатуру отдельным сообщением
            //сделать свитч с стейтмами с клавиатурами
            sendInlineKeyboard(chatID, statemessage);

        } catch (Exception e) {
            log.error("Error sending multiple images: " + e.getMessage(), e);
        }
    }

    // Отдельный метод для отправки клавиатуры
    private void sendInlineKeyboard(long chatID, STATEMESSAGE statemessage) {
        try {
            CustomInlineKeyboardMarkup inlineKeyboard = new CustomInlineKeyboardMarkup();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatID);
            sendMessage.setText(" текст с пояснением ");
            sendMessage.setReplyMarkup(inlineKeyboard.addInlineKeyboardBySTATEMASSEGE(inlineKeyboard, statemessage));

            execute(sendMessage);
            log.info("Inline keyboard sent successfully.");
        } catch (Exception e) {
            log.error("Error sending inline keyboard: " + e.getMessage(), e);
        }
    }


    private void startCommandReceived(long chatID, String name){

        String answer = "Hi, " +name+ ", nice to meet you!";
        log.info("Loges name "+name);
        sendMessage(chatID, answer);

    }

    private void sendMessage(long chatID, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatID));
        message.setText(textToSend);
//        checkDatabaseConnection();

        CustomInlineKeyboardMarkup inlineKeyboard = new CustomInlineKeyboardMarkup();

        message.setReplyMarkup(inlineKeyboard.typeInlineKeyboard(inlineKeyboard));
        try {
            execute(message);
        }catch (TelegramApiException e){
            log.error("Error tg exception" + e.getMessage());
        }
    }

//    public ReplyKeyboardMarkup standartChoseKeyboard(){
//        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
//
//        List<KeyboardRow> keyboardRows = new ArrayList<>();
//
//        KeyboardRow row = new KeyboardRow(){{
//            add("назад");
//            add("выбрать");
//        }};
//
//        keyboardRows.add(row);
//
//        row = new KeyboardRow(){{
//            add("связатся с продавцом");
//        }};
//
//        keyboardRows.add(row);
//
//        keyboardMarkup.setKeyboard(keyboardRows);
//        return keyboardMarkup;
//    }

//    public void checkDatabaseConnection() {
//        try {
//            if (connection != null && !connection.isClosed()) {
//                log.info("Database connection is active!");
//            } else {
//                log.warn("Database connection is not active.");
//            }
//        } catch (SQLException e) {
//            log.error("Error checking database connection: " + e.getMessage());
//        }
//    }

    @Override
    public String getBotUsername() {
        return botConfiguration.getBotName();
    }

    public  String getBotToken(){
        return botConfiguration.getToken();
    }

}
