package com.kondak;

public class Starter {
    static BotWorker worker;
    //token issued by @BotFather when creating a bot
    static String botToken = "token";

    static String prefix = "[Starter]: ";

    static void log(String text) {
        System.out.println(prefix + text);
    }

    public static void main(String[] args) {
        log("Starting BotWorker...");
        worker = new BotWorker();
        log("Initializing bot...");
        worker.initializeBot(botToken);
        log("Well, now it seems to be work");
    }
}