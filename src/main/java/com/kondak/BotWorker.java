package com.kondak;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BotWorker {
    private TelegramBot bot;
    private DatabaseConnectionInfo info;
    private DatabaseWorker dbWorker;

    //commands supported by the bot:
    private final String searchCommandPattern = "search";
    private final String addCommandPattern = "add";

    private final int name = 0;
    private final int surname = 1;
    private final int phone = 2;

    private static final Logger log = LogManager.getLogger();

    //by the keyword "search" the method searches the database and returns a string with the search results
    private String executeSearch(String message) {
        String patternToSearch = message.replaceFirst(searchCommandPattern, "").trim(); //separating a pattern to search from the keyword

        log.info("Pattern length: " + patternToSearch.length());

        String result = dbWorker.search(patternToSearch); //transfer the pattern to search to the worker with the database

        if (result.isEmpty()) result = "Nothing was found...";

        return result;
    }

    //by the "add" keyword, the method adds a new user to the database
    //returns a string with a message about the successful or unsuccessful addition of the user.
    private String executeAdd(String message) {
        String result;
        String pureData = message.replaceFirst(addCommandPattern, "").trim();
        String[] userInfo = pureData.split(",");

        Boolean executionResultSuccessful = false;

        log.info("Got " + userInfo.length + " values");

        //Since the bot database has three main columns, it requires three user information to correctly add a user to the database.
        if (userInfo.length == 3)
            executionResultSuccessful = dbWorker.add(userInfo[name], userInfo[surname], userInfo[phone]);

        if (executionResultSuccessful) {
            result = "Thank you for new user :3";
        } else {
            result = "Well, something gone wrong! Be sure, that you entered something like:\n";
            result += "add Username,UserSurname,UserPhone";
        }

        return result;
    }

    private String printHelp() {
        String helpInfo = "You can use commands:\n"
                + searchCommandPattern + " <key word> to get users by keyword\n"
                + addCommandPattern + "<Username>,<UserSurname>,<UserPhone> to insert user in DB";
        return helpInfo;
    }

    //actions that will be performed depending on a specific command
    private String checkResponse(String messageText) {
        log.info("Message: " + messageText);
        String responseText = "Haha!";
        if (messageText.contains("help"))
            responseText = printHelp();
        if (messageText.contains("search"))
            responseText = executeSearch(messageText);
        if (messageText.contains("add"))
            responseText = executeAdd(messageText);
        return responseText;
    }

    public void initializeBot(String token) {
        bot = new TelegramBot(token);

        // this lambda function is official way to set up listeners
        bot.setUpdatesListener(updates -> {
            Update lastUpdate = updates.get(updates.size() - 1);
            long chatId = lastUpdate.message().chat().id();
            String message = lastUpdate.message().text();
            log.info("New message from " + lastUpdate.message().from().username());
            //setting a method with commands available to the bot
            bot.execute(new SendMessage(chatId, checkResponse(message)));
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });

        log.info("Bot initialized");
    }

    //initializes bot and default connection settings to database
    public BotWorker() {
        log.info("Started worker");
        info = new DatabaseConnectionInfo();
        log.info("Initialized database connection info");
        dbWorker = new DatabaseWorker(info);
        log.info("Connecting to database...");
        dbWorker.connect();
    }
}
