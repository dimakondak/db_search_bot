package com.kondak;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Starter {
    static BotWorker worker;
    //token issued by @BotFather when creating a bot
    static String botToken = "token";

    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args) {
        log.info("Starting BotWorker...");
        worker = new BotWorker();
        log.info("Initializing bot...");
        worker.initializeBot(botToken);
        log.info("Well, now it seems to be work");
    }
}