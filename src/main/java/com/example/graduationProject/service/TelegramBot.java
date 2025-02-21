package com.example.graduationProject.service;
import com.example.graduationProject.config.BotConfiguration;
import com.example.graduationProject.config.DBConfig;
import com.example.graduationProject.controller.ImagesController;
import com.example.graduationProject.controller.OrderController;
import com.example.graduationProject.controller.StageController;
import com.example.graduationProject.controller.UsersController;
import com.example.graduationProject.entities.Images;
import com.example.graduationProject.entities.Order;
import com.example.graduationProject.entities.Stage;
import com.example.graduationProject.enumeration.*;
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
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
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

import static com.example.graduationProject.enumeration.COLORCOMBO.*;
import static com.example.graduationProject.enumeration.FORWHOM.*;
import static com.example.graduationProject.enumeration.SIZE.*;
import static com.example.graduationProject.enumeration.STATEMESSAGE.*;
import static com.example.graduationProject.enumeration.SUBJECT.*;
import static com.example.graduationProject.enumeration.TYPEORDER.*;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot{

    private static final Logger log = LoggerFactory.getLogger(TelegramBot.class);
    final BotConfiguration botConfiguration;
    private ImagesController imagesController;
    private StageController stageController;
    private OrderController orderController;
    private UsersController usersController;

    private DBConfig dbConfig;

    public TelegramBot(BotConfiguration configuration, DBConfig dbConfig, ImagesController imagesController, StageController stageController, OrderController orderController, UsersController usersController) throws SQLException {
        this.botConfiguration = configuration;
        this.dbConfig = dbConfig;
//        this.connection = dbConfig.getConnection(); // Получаем подключение через DBConfig
        this.imagesController = imagesController;
        this.stageController = stageController;
        this.orderController = orderController;
        this.usersController = usersController;
    }


    //поик по айди чата объекта заказа и дальнейшее взаимодействие через чат ади с заказом, запись и поиск.
    //написать в субботу обработчик колбеков if (update.hasMessage() && update.getMessage().hasText()){...}else if (update.hasCallbackQuery()){...}
    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatID = update.getMessage().getChatId();
            log.info("Chat id: "+ chatID);
            int idOrder;
            int idUsers;

            switch (messageText){
                case "/start":
                    try {
                        startCommandReceived(chatID, update.getMessage().getChat().getFirstName());
//                        Order order = new Order();
//                        order.setTitle(System.currentTimeMillis()+"");
//                        idOrder = orderController.saveUpdateOrderReturnOrder(order).getId_order();
//                        log.info("Id create order: " + idOrder);



//                        Order probeOrder2 = orderController.getOrderById(1);
//                        log.info("order name by id : " + probeOrder2.getTitle());
//                        log.info("order color by id : " + probeOrder2.getColor());
//

                    }catch (Exception e){
                        log.error("Error main bot " + e.getMessage());
                    }
                    break;
                case "/createOrder":
                    idUsers = usersController.getOrCreateUserByChatId(chatID, update.getMessage().getFrom().getUserName());
                    orderController.saveUpdateOrder(new Order(idUsers));
                    log.info("Id user : " + idUsers);
                    break;
                case "/probeOrderByUserId":
                    idUsers = usersController.getOrCreateUserByChatId(chatID, update.getMessage().getFrom().getUserName());
                    try {
                            // Получаем последний заказ для пользователя
                            Order probeOrder = orderController.getLastOrderByUserId(idUsers);

                            // Проверяем, что заказ не является null
                            if (probeOrder != null) {
                                log.info("Last order title for user id " + idUsers + ": " + probeOrder.getTitle());
                            } else {
                                log.warn("No orders found for user with id: " + idUsers);
                            }
                        } catch (Exception e) {
                            // Логируем ошибку, если что-то пошло не так
                            log.error("Error occurred while fetching the last order for user id " + idUsers + ": " + e.getMessage(), e);
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
                        response = imagesController.uploadImage(file, 11);
                        log.info("Added writ"+response.getBody());
                    } catch (IOException e) {
                        log.error("Error main bot in /probeImage"+e.getMessage());
                        throw new RuntimeException(e);
                    }
                    break;
                    //запись в объект выбранного типа подарка и вызов этапа с размером

                    //кейсы с размером, которые вызывают следкющую форму пренадлежности, и записывает данные в объект подарка


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


        } else if (update.hasCallbackQuery()) {
            String nickName = update.getCallbackQuery().getFrom().getUserName();
            int idUsers;
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callbackData = callbackQuery.getData();
            long chatID = callbackQuery.getMessage().getChatId();

            try {
                switch (callbackData) {
                    case "basket":
                        setTypeOrderByCallBack(chatID, nickName,TYPEORDER.BASKET);
                        handleAlbumImages(6,chatID, stageController.getStageByID(6).getTitle(), SIZE);
                        log.info("/basket handled successfully.");
                        break;
                    case "pallet":
                        setTypeOrderByCallBack(chatID, nickName,TYPEORDER.PALLET);
                        handleAlbumImages(7,chatID, stageController.getStageByID(7).getTitle(), SIZE);
                        log.info("/pallet handled successfully.");
                        break;
                    case "bouquet":
                        setTypeOrderByCallBack(chatID, nickName,TYPEORDER.BOUQUET);
                        handleAlbumImages(10,chatID, stageController.getStageByID(10).getTitle(), SUBTYPEBOUQET);
                        log.info("/pallet handled successfully.");
                        break;
                    case "box":
                        setTypeOrderByCallBack(chatID, nickName,TYPEORDER.BOX);
                        handleAlbumImages(11,chatID, stageController.getStageByID(11).getTitle(), SUBTYPEBOX);
                        log.info("/pallet handled successfully.");
                        break;
                    case "round_bouquet":
                        setTypeOrderByCallBack(chatID, nickName, ROUNDBOUQUET);
                        handleAlbumImages(8,chatID, stageController.getStageByID(8).getTitle(), SIZE);
                        break;
                    case "little_bag_bouquet":
                        setTypeOrderByCallBack(chatID, nickName, BOUQUETLEG);
                        handleAlbumImages(8,chatID, stageController.getStageByID(8).getTitle(), SIZE);
                        break;
                    case "circle_box":
                        setTypeOrderByCallBack(chatID, nickName, ROUNDBOX);
                        handleAlbumImages(10,chatID, stageController.getStageByID(9).getTitle(), SIZE);
                        break;
                    case "square_box":
                        setTypeOrderByCallBack(chatID, nickName,TYPEORDER.SQUAREBOX);
                        handleAlbumImages(10,chatID, stageController.getStageByID(9).getTitle(), SIZE);
                        break;
                    case "small":
                        setSizeOrderByCallBack(chatID,nickName,SMALL);
                        formBySTATEMESSAGE(chatID,FOR_WHOM);
                        break;
                    case "medium":
                        setSizeOrderByCallBack(chatID,nickName,MEDIUM);
                        formBySTATEMESSAGE(chatID,FOR_WHOM);
                        break;
                    case "large":
                        setSizeOrderByCallBack(chatID,nickName,LARGE);
                        formBySTATEMESSAGE(chatID,FOR_WHOM);
                        break;
                    //кейсы для формы для подтипа букета

                    //выбор пренадлежности к полу
                    case "he":
                        setGenderOrderByCallBack(chatID,nickName,HE);
                        formBySTATEMESSAGE(chatID,SUBJECT);
                        break;
                    case "she":
                        setGenderOrderByCallBack(chatID,nickName,SHE);
                        formBySTATEMESSAGE(chatID,SUBJECT);
                        break;
                    case "nothing":
                        setGenderOrderByCallBack(chatID,nickName,NOTHING);
                        formBySTATEMESSAGE(chatID,SUBJECT);
                        break;
                    //
                    case "newYear":
                        setSubjectOrderByCallBack(chatID,nickName,NEW_YEAR);
                        formBySTATEMESSAGE(chatID, COLOR);
                        break;

                    case "feb23":
                        setSubjectOrderByCallBack(chatID,nickName,FEBRUARY_23);
                        formBySTATEMESSAGE(chatID, COLOR);
                        break;

                    case "march8":
                        setSubjectOrderByCallBack(chatID,nickName,MARCH_8);
                        formBySTATEMESSAGE(chatID, COLOR);
                        break;

                    case "lastBell":
                        setSubjectOrderByCallBack(chatID,nickName,LAST_BELL);
                        formBySTATEMESSAGE(chatID, COLOR);
                        break;

                    case "sept1":
                        setSubjectOrderByCallBack(chatID,nickName,SEPTEMBER_1);
                        formBySTATEMESSAGE(chatID, COLOR);
                        break;

                    case "teacherDay":
                        setSubjectOrderByCallBack(chatID,nickName,TEACHERS_DAY);
                        formBySTATEMESSAGE(chatID, COLOR);
                        break;

                    case "educatorDay":
                        setSubjectOrderByCallBack(chatID,nickName,EDUCATORS_DAY);
                        formBySTATEMESSAGE(chatID, COLOR);
                        break;

                    case "birthday":
                        setSubjectOrderByCallBack(chatID,nickName,BIRTHDAY);
                        formBySTATEMESSAGE(chatID, COLOR);
                        break;

                    case "medicDay":
                        setSubjectOrderByCallBack(chatID,nickName,MEDICAL_WORKERS_DAY);
                        formBySTATEMESSAGE(chatID, COLOR);
                        break;

                    case "coachDay":
                        setSubjectOrderByCallBack(chatID,nickName,COACHS_DAY);
                        formBySTATEMESSAGE(chatID, COLOR);
                        break;

                    case "anyDay":
                        setSubjectOrderByCallBack(chatID,nickName,ANY_DAY);
                        formBySTATEMESSAGE(chatID, COLOR);
                        break;


                    //команды с цветами
                    case "red":
                        setColorOrderByCallBack(chatID,nickName,RED);
                        break;

                    case "yellow":
                        setColorOrderByCallBack(chatID,nickName,YELLOW);
                        break;

                    case "pink":
                        setColorOrderByCallBack(chatID,nickName,PINK);
                        break;

                    case "green":
                        setColorOrderByCallBack(chatID,nickName,GREEN);
                        break;

                    case "sky":
                        setColorOrderByCallBack(chatID,nickName,SKY);
                        break;

                    case "brown":
                        setColorOrderByCallBack(chatID,nickName,BROWN);
                        break;

                    case "violet":
                        setColorOrderByCallBack(chatID,nickName,VIOLET);
                        break;

                    case "darkGreen":
                        setColorOrderByCallBack(chatID,nickName,DARK_GREEN);
                        break;

                    case "purple":
                        setColorOrderByCallBack(chatID,nickName,PURPLE);
                        break;

                    case "blue":
                        setColorOrderByCallBack(chatID,nickName,BLUE);
                        break;

                    case "cream":
                        setColorOrderByCallBack(chatID,nickName,CREAM);
                        break;
                    case "back":
                        backForm(chatID, nickName);
                        break;
                }
            } catch (Exception e) {
                log.error("Error handling callback: " + e.getMessage());
            }
        }
    }

    private void backForm(long chatId, String nickName) {
        int idUsers = usersController.getOrCreateUserByChatId(chatId, nickName);
        Order order = orderController.getLastOrderByUserId(idUsers);

        if (order == null) {
            log.warn("No order found for user: " + nickName);
            return;
        }

        STATETURNBOT currentState = order.getStateOrder();
        log.info("Current state for user {}: {}", nickName, currentState);

        switch (currentState) {
            case COLOR:
                // Независимо от того, выбран цвет или нет, переходим на SUBJECT
                if (order.getColor() != null) {
                    log.debug("Resetting color for order: {}", order.getId_order());
                    order.setColor(null);
                }
                order.setStateOrder(STATETURNBOT.SUBJECT);
                formBySTATEMESSAGE(chatId, COLOR);
                break;

            case SUBJECT:
                if (order.getSubject() != null) {
                    log.debug("Resetting subject for order: {}", order.getId_order());
                    order.setSubject(null);
                }
                order.setStateOrder(STATETURNBOT.FOR_WHOM);
                formBySTATEMESSAGE(chatId, SUBJECT);
                break;

            case FOR_WHOM:
                if (order.getFromWhom() != null) {
                    log.debug("Resetting fromWhom for order: {}", order.getId_order());
                    order.setFromWhom(null);
                }
                order.setStateOrder(STATETURNBOT.SIZE);
                formBySTATEMESSAGE(chatId, FOR_WHOM);
                break;

            case SIZE:
                if (order.getSize() != null) {
                    log.debug("Resetting size for order: {}", order.getId_order());
                    order.setSize(null);
                }

                // Вызываем нужный альбом в зависимости от типа заказа
                switch (order.getType()) {
                    case BASKET:
                        // Например, для "basket" используем альбом с id 6 и форму SIZE
                        order.setStateOrder(STATETURNBOT.TYPE); // или другой нужный статус
                        handleAlbumImages(6, chatId, stageController.getStageByID(6).getTitle(), SIZE);
                        log.info("Transitioning to album for BASKET for order: {}", order.getId_order());
                        break;

                    case PALLET:
                        order.setStateOrder(STATETURNBOT.TYPE);
                        handleAlbumImages(7, chatId, stageController.getStageByID(7).getTitle(), SIZE);
                        log.info("Transitioning to album for PALLET for order: {}", order.getId_order());
                        break;

                    case BOUQUET:
                        order.setStateOrder(STATETURNBOT.SUBTYPEBOUQET);
                        handleAlbumImages(10, chatId, stageController.getStageByID(10).getTitle(), SUBTYPEBOUQET);
                        log.info("Transitioning to album for BOUQUET for order: {}", order.getId_order());
                        break;

                    case BOX:
                        order.setStateOrder(STATETURNBOT.SUBTYPEBOX);
                        handleAlbumImages(11, chatId, stageController.getStageByID(11).getTitle(), SUBTYPEBOX);
                        log.info("Transitioning to album for BOX for order: {}", order.getId_order());
                        break;

                    case ROUNDBOUQUET:
                    case BOUQUETLEG:
                        // Для этих типов альбом id 8 и форма SIZE
                        order.setStateOrder(STATETURNBOT.SUBTYPEBOUQET);
                        handleAlbumImages(8, chatId, stageController.getStageByID(8).getTitle(), SIZE);
                        log.info("Transitioning to album for ROUNDBOUQUET/BOUQUETLEG for order: {}", order.getId_order());
                        break;

                    case ROUNDBOX:
                    case SQUAREBOX:
                        // Для этих типов используем альбом id 10 (заголовок из stage id 9) и форму SIZE
                        order.setStateOrder(STATETURNBOT.SUBTYPEBOX);
                        handleAlbumImages(10, chatId, stageController.getStageByID(9).getTitle(), SIZE);
                        log.info("Transitioning to album for ROUNDBOX/SQUAREBOX for order: {}", order.getId_order());
                        break;

                    default:
                        log.warn("Unhandled order type: {} for order: {}", order.getType(), order.getId_order());
                        // Можно задать какое-либо значение по умолчанию или вернуть ошибку
                        order.setStateOrder(STATETURNBOT.TYPE);
                        formBySTATEMESSAGE(chatId, SIZE);
                        break;
                }
                break;

            case TYPE:
                if (order.getType() != null) {
                    log.debug("Resetting type for order: {}", order.getId_order());
                    order.setType(null);
                }
                order.setStateOrder(STATETURNBOT.NEW);
                formBySTATEMESSAGE(chatId, TYPE);
                log.info("Order reset to initial state for order: {}", order.getId_order());
                break;

            case SUBTYPEBOUQET:
                if (order.getType() == ROUNDBOUQUET ||order.getType() == BOUQUETLEG){
                    if (order.getType() != null) {
                        log.debug("Resetting type for order: {}", order.getId_order());
                        order.setType(null);
                    }
                    order.setStateOrder(STATETURNBOT.TYPE);
                    formBySTATEMESSAGE(chatId, SUBTYPEBOUQET);
                }
                break;
            case SUBTYPEBOX:
                if (order.getType() == SQUAREBOX ||order.getType() == ROUNDBOX){
                    if (order.getType() != null) {
                        log.debug("Resetting type for order: {}", order.getId_order());
                        order.setType(null);
                    }
                    order.setStateOrder(STATETURNBOT.TYPE);
                    formBySTATEMESSAGE(chatId, SUBTYPEBOX);
                }
                break;

            default:
                log.warn("Unknown state '{}' for order {}. Resetting to NEW state.", currentState, order.getId_order());
                order.setStateOrder(STATETURNBOT.NEW);
                formBySTATEMESSAGE(chatId, TYPE);
                break;
        }

        orderController.saveUpdateOrder(order);
        log.info("Order updated: {}", order);
    }




    //функция формы выбора размера
    private void formBySTATEMESSAGE(long chatID, STATEMESSAGE statemessage){
        try{
            Stage stage = stageController.getStageByIDAlbumStateMessage(statemessage);
            handleAlbumImages(stage.getIdAlbum(),chatID, stage.getTitle(), statemessage);

            log.info("sizeForm handled successfully.");

        }catch (Exception e){
            log.error("Error main bot in sizeForm" + e.getMessage());
        }
    }

    private void setSubjectOrderByCallBack(long chatId, String nickName, SUBJECT subject){
        int idUsers = usersController.getOrCreateUserByChatId(chatId, nickName);
        Order order = orderController.getLastOrderByUserId(idUsers);
        log.info(order.toString());
        order.setStateOrder(STATETURNBOT.SUBJECT);
        switch (subject){
            case WEDDING:
                order.setSubject(WEDDING);
                break;
            case MARCH_8:
                order.setSubject(MARCH_8);
                break;
            case BIRTHDAY:
                order.setSubject(BIRTHDAY);
                break;
            case NEW_YEAR:
                order.setSubject(NEW_YEAR);
                break;
            case CORPORATE:
                order.setSubject(CORPORATE);
                break;
            case LAST_BELL:
                order.setSubject(LAST_BELL);
                break;
            case COACHS_DAY:
                order.setSubject(COACHS_DAY);
                break;
            case FEBRUARY_23:
                order.setSubject(FEBRUARY_23);
                break;
            case SEPTEMBER_1:
                order.setSubject(SEPTEMBER_1);
                break;
            case TEACHERS_DAY:
                order.setSubject(TEACHERS_DAY);
                break;
            case EDUCATORS_DAY:
                order.setSubject(EDUCATORS_DAY);
                break;
            case MEDICAL_WORKERS_DAY:
                order.setSubject(MEDICAL_WORKERS_DAY);
                break;
        }

        orderController.saveUpdateOrder(order);
        order = orderController.getLastOrderByUserId(idUsers);
        log.info(order.toString());
        orderController.saveUpdateOrder(order);
    }

    private void setColorOrderByCallBack(long chatId, String nickName, COLORCOMBO color){
        int idUsers = usersController.getOrCreateUserByChatId(chatId, nickName);
        Order order = orderController.getLastOrderByUserId(idUsers);
        log.info(order.toString());
        order.setStateOrder(STATETURNBOT.COLOR);
        switch (color){
            case RED:
                order.setColor(RED);
                break;
            case SKY:
                order.setColor(SKY);
                break;
            case BLUE:
                order.setColor(BLUE);
                break;
            case PINK:
                order.setColor(PINK);
                break;
            case BROWN:
                order.setColor(BROWN);
                break;
            case CREAM:
                order.setColor(CREAM);
                break;
            case GREEN:
                order.setColor(GREEN);
                break;
            case PURPLE:
                order.setColor(PURPLE);
                break;
            case VIOLET:
                order.setColor(VIOLET);
                break;
            case YELLOW:
                order.setColor(YELLOW);
                break;
            case DARK_GREEN:
                order.setColor(DARK_GREEN);
                break;
        }
        orderController.saveUpdateOrder(order);
        order = orderController.getLastOrderByUserId(idUsers);
        log.info(order.toString());
        orderController.saveUpdateOrder(order);
    }

    private void setSizeOrderByCallBack(long chatId, String nickName, SIZE size){
        int idUsers = usersController.getOrCreateUserByChatId(chatId, nickName);
        Order order = orderController.getLastOrderByUserId(idUsers);
        log.info(order.toString());
        order.setStateOrder(STATETURNBOT.SIZE);

        switch (size){
            case SMALL:
                order.setSize(SMALL);
                break;
            case MEDIUM:
                order.setSize(MEDIUM);
                break;
            case LARGE:
                order.setSize(LARGE);
                break;
        }
        orderController.saveUpdateOrder(order);
        order = orderController.getLastOrderByUserId(idUsers);
        log.info(order.toString());
        orderController.saveUpdateOrder(order);
    }

    private void setGenderOrderByCallBack(long chatId, String nickName, FORWHOM forwhom){
        int idUsers = usersController.getOrCreateUserByChatId(chatId, nickName);
        Order order = orderController.getLastOrderByUserId(idUsers);
        log.info(order.toString());
        order.setStateOrder(STATETURNBOT.FOR_WHOM);
        switch (forwhom){
            case HE:
                order.setFromWhom(HE);
                break;
            case SHE:
                order.setFromWhom(SHE);
                break;
            case NOTHING:
                order.setFromWhom(NOTHING);
                break;
        }
        orderController.saveUpdateOrder(order);
        order = orderController.getLastOrderByUserId(idUsers);
        log.info(order.toString());
        orderController.saveUpdateOrder(order);
    }

    private void setTypeOrderByCallBack(long chatId, String nickName, TYPEORDER type){
        int idUsers = usersController.getOrCreateUserByChatId(chatId, nickName);
        Order order = orderController.getLastOrderByUserId(idUsers);
        log.info(order.toString());

        switch (type){
            case BASKET:
                order.setType(TYPEORDER.BASKET);
                order.setStateOrder(STATETURNBOT.TYPE);
                break;
            case BOX:
                order.setType(TYPEORDER.BOX);
                order.setStateOrder(STATETURNBOT.TYPE);
                break;
            case PALLET:
                order.setType(TYPEORDER.PALLET);
                order.setStateOrder(STATETURNBOT.TYPE);
                break;
            case BOUQUET:
                order.setType(TYPEORDER.BOUQUET);
                order.setStateOrder(STATETURNBOT.TYPE);
                break;
            case ROUNDBOX:
                order.setType(ROUNDBOX);
                order.setStateOrder(STATETURNBOT.SUBTYPEBOX);
                break;
            case SQUAREBOX:
                order.setType(TYPEORDER.SQUAREBOX);
                order.setStateOrder(STATETURNBOT.SUBTYPEBOX);
                break;
            case BOUQUETLEG:
                order.setType(BOUQUETLEG);
                order.setStateOrder(STATETURNBOT.SUBTYPEBOUQET);
                break;
            case ROUNDBOUQUET:
                order.setType(ROUNDBOUQUET);
                order.setStateOrder(STATETURNBOT.SUBTYPEBOUQET);
                break;
        }
        orderController.saveUpdateOrder(order);
        order = orderController.getLastOrderByUserId(idUsers);
        log.info(order.toString());
        orderController.saveUpdateOrder(order);
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
