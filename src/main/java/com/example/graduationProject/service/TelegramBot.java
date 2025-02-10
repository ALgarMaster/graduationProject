package com.example.graduationProject.service;
import com.example.graduationProject.config.BotConfiguration;
import com.example.graduationProject.config.DBConfig;
import com.example.graduationProject.controller.ImagesController;
import com.example.graduationProject.entities.Images;
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

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot{

    private static final Logger log = LoggerFactory.getLogger(TelegramBot.class);
    final BotConfiguration botConfiguration;
    private ImagesController imagesController;

    private DBConfig dbConfig;

    public TelegramBot(BotConfiguration configuration, DBConfig dbConfig, ImagesController imagesController) throws SQLException {
        this.botConfiguration = configuration;
        this.dbConfig = dbConfig;
//        this.connection = dbConfig.getConnection(); // Получаем подключение через DBConfig
        this.imagesController = imagesController;
    }



    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatID = update.getMessage().getChatId();

            switch (messageText){
                case "/start":
                    Configuration configuration = new Configuration();
                    configuration.configure();
                    try(var sessionFactory = configuration.buildSessionFactory();
                    var session = sessionFactory.openSession();)
                    {
//                        session.beginTransaction();
//
//                        session.save(new Images("engwioew","//engwioengiow"));
//                        log.info("Add Image name " + "engwioew");
//                        session.getTransaction().commit();


                        startCommandReceived(chatID, update.getMessage().getChat().getFirstName());
                    }catch (Exception e){
                        log.error("Error main bot" + e.getMessage());
                    }
                    break;
                case "/probeImage":

                    String imagePath = "C:\\Users\\Stanislav\\Downloads\\40951835.jpg";

                    // Create the CustomMultipartFile
                    CustomMultipartFile file = new CustomMultipartFile(imagePath);

                    // Create the ImageController instance

                     // Make sure imageRepo is properly initialized

                    // Call the uploadImage method
                    ResponseEntity<String> response = null;
                    try {
                        response = imagesController.uploadImage(file, 0);
                        log.info("Added writ"+response.getBody());
                    } catch (IOException e) {
                        log.error("Error main bot in /probeImage"+e.getMessage());
                        throw new RuntimeException(e);

                    }

                    // Output the response (it should be the image URL)


                    break;
                case "/probeQueryImagesByAlbumId0":
                    try {
                        int probeAlbumId = 0;
                        handleAlbumImages(probeAlbumId, chatID);
                        log.info("/probeQueryImagesByAlbumId handled successfully.");
                    } catch (Exception e) {
                        log.error("Error in /probeQueryImagesByAlbumId0: " + e.getMessage(), e);
                    }
                    break;
                case "/probeQueryImagesByAlbumId1":
                    try {
                        int probeAlbumId = 1;
                        handleAlbumImages(probeAlbumId, chatID);
                        log.info("/probeQueryImagesByAlbumId handled successfully.");
                    } catch (Exception e) {
                        log.error("Error in /probeQueryImagesByAlbumId1: " + e.getMessage(), e);
                    }
                    break;
                case "/probeQueryImagesByAlbumId2":
                    try {
                        int probeAlbumId = 2;
                        handleAlbumImages(probeAlbumId, chatID);
                        log.info("/probeQueryImagesByAlbumId handled successfully.");
                    } catch (Exception e) {
                        log.error("Error in /probeQueryImagesByAlbumId2: " + e.getMessage(), e);
                    }
                    break;
                case "/probeQueryImagesByAlbumId3":
                    try {
                        int probeAlbumId = 3;
                        handleAlbumImages(probeAlbumId, chatID);
                        log.info("/probeQueryImagesByAlbumId handled successfully.");
                    } catch (Exception e) {
                        log.error("Error in /probeQueryImagesByAlbumId3: " + e.getMessage(), e);
                    }
                    break;
                case "/probeQueryImagesByAlbumId4":
                    try {
                        int probeAlbumId = 4;
                        handleAlbumImages(probeAlbumId, chatID);
                        log.info("/probeQueryImagesByAlbumId handled successfully.");
                    } catch (Exception e) {
                        log.error("Error in /probeQueryImagesByAlbumId4: " + e.getMessage(), e);
                    }
                    break;


                case "/testConnectionToDB":

                    // Output the response (it should be the image URL)

                    String responseMessage = testDatabaseConnection();
                    sendMessage(chatID, responseMessage);


                    break;

                default: sendMessage(chatID, "Ooooops, sorry, command was not recognized(((");
            }


        }
    }

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


    public void handleAlbumImages(int probeAlbumId, long chatID) {
        try {
            // Получаем список изображений для альбома
            List<File> imagesFileList = imagesController.getImagesByAlbumId(probeAlbumId);
            for (File imageFile : imagesFileList) {
                log.info("Image path: " + imageFile.getAbsolutePath()); // Логируем полный путь к каждому изображению
            }

            // Если в альбоме только одно изображение
            if (imagesFileList.size() == 1) {
                sendSingleImage(imagesFileList.get(0), probeAlbumId, chatID);
            }
            // Если в альбоме несколько изображений
            else if (!imagesFileList.isEmpty()) {
                sendMultipleImages(imagesFileList, probeAlbumId, chatID);
            }

            log.info("Album images handled successfully for albumId: " + probeAlbumId);
        } catch (Exception e) {
            log.error("Error in handleAlbumImages: " + e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    // Функция для отправки одного изображения
    private void sendSingleImage(File imageFile, int probeAlbumId, long chatID) {
        try {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setChatId(chatID);
            sendPhoto.setPhoto(new InputFile(imageFile)); // Передаем изображение
            sendPhoto.setCaption("📸 *Тестовый альбом " + probeAlbumId + " !*");
            sendPhoto.setParseMode("Markdown");

            // Отправляем одно изображение
            execute(sendPhoto);
            log.info("Single image sent successfully.");
        } catch (Exception e) {
            log.error("Error sending single image: " + e.getMessage(), e);
        }
    }

    // Функция для отправки нескольких изображений
    private void sendMultipleImages(List<File> imagesFileList, int probeAlbumId, long chatID) {
        try {
            SendMediaGroup sendMediaGroup = new SendMediaGroup();
            sendMediaGroup.setChatId(chatID);

            List<InputMedia> mediaList = new ArrayList<>();

            for (int i = 0; i < imagesFileList.size(); i++) {
                File currentImage = imagesFileList.get(i);
                log.info("Processing image: " + currentImage.getAbsolutePath());

                if (currentImage.exists()) {
                    InputMediaPhoto photo = new InputMediaPhoto();
                    photo.setMedia(currentImage, "1");

                    // Добавляем подпись только к первому изображению
                    if (i == 0) {
                        photo.setCaption("📸 *Тестовый альбом " + probeAlbumId + " !*");
                        photo.setParseMode("Markdown");
                    }

                    mediaList.add(photo);
                } else {
                    log.error("Image file does not exist: " + currentImage.getAbsolutePath());
                }
            }

            if (mediaList.size() >= 2) {
                sendMediaGroup.setMedias(mediaList);
                execute(sendMediaGroup);
                log.info("Multiple images sent successfully.");
            } else {
                log.error("Not enough images to send media group.");
            }
        } catch (Exception e) {
            log.error("Error sending multiple images: " + e.getMessage(), e);
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
