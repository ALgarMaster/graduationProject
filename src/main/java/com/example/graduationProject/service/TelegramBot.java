package com.example.graduationProject.service;
import com.example.graduationProject.config.BotConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot{

    private static final Logger log = LoggerFactory.getLogger(TelegramBot.class);
    final BotConfiguration botConfiguration;

    public TelegramBot(BotConfiguration configuration){
        this.botConfiguration = configuration;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatID = update.getMessage().getChatId();

            switch (messageText){
                case "/start":

                    try {
                        startCommandReceived(chatID, update.getMessage().getChat().getFirstName());
                    }catch (Exception e){
                        log.error("Error main bot" + e.getMessage());
                    }
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
        try {
            execute(message);
        }catch (TelegramApiException e){
            log.error("Error tg exception" + e.getMessage());
        }
    }


    @Override
    public String getBotUsername() {
        return botConfiguration.getBotName();
    }

    public  String getBotToken(){
        return botConfiguration.getToken();
    }

}
