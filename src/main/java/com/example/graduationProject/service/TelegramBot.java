package com.example.graduationProject.service;
import com.example.graduationProject.config.BotConfiguration;
import com.example.graduationProject.config.DBConfig;
import com.example.graduationProject.controller.ImageController;
import com.example.graduationProject.entities.Images;
import com.example.graduationProject.repository.ImageRepo;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.Configuration;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot{

    private static final Logger log = LoggerFactory.getLogger(TelegramBot.class);
    final BotConfiguration botConfiguration;
    ImageRepo imageRepo;

//    private DBConfig dbConfig;
//
//    Connection connection = dbConfig.getConnection();




    public TelegramBot(BotConfiguration configuration, DBConfig dbConfig) throws SQLException {
        this.botConfiguration = configuration;
//        this.dbConfig = dbConfig;
//        this.connection = dbConfig.getConnection(); // Получаем подключение через DBConfig
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
                    ImageController imageController = new ImageController(imageRepo); // Make sure imageRepo is properly initialized

                    // Call the uploadImage method
                    ResponseEntity<String> response = null;
                    try {
                        response = imageController.uploadImage(file);
                        log.info(response.getBody());
                    } catch (IOException e) {
                        log.error("Error main bot in /probeImage"+e.getMessage());
                        throw new RuntimeException(e);

                    }

                    // Output the response (it should be the image URL)


                    break;

                default: sendMessage(chatID, "Ooooops, sorry, command was not recognized(((");
            }


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
