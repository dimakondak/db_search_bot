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
        //when creating a BotWorker, we pass it a token to initialize the bot
        worker = new BotWorker(botToken);
        log("Well, now it seems to be work");
    }
}