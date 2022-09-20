package com.example.telegrambot.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BotSetting {
    private BotSetting() {
    } // приватный конструктор, чтобы доступ к нему был закрыть за пределами класса,
    // тогда он не сможет возвращать новые объекты

    /*
    В классе BotSettings используется порождающий шаблон
    проектирования СИНГЛТОН, служит он для того, чтобы не было
    возможности создать несколько экземпляров класса в одном потоке.
     */

    public static final String PATH_TO_PROPERTIES = "src/main/resources/config.properties";
    private static BotSetting instance; //приватное статическое поле, содержащее одиночный объект
    private Properties properties;
    private String tokenBot;
    private String userName;
    private String tokenWeather;
//    private TelegramBotsApi telegramBotsApi;
    //sdfsfwdfdfdfdfdf
    //sdsgrge

    public String getTokenBot() {
        return tokenBot;
    }

    public String getUserName() {
        return userName;
    }

    public String getTokenWeather() {
        return tokenWeather;
    }

    public static BotSetting getInstance() { //статический создающий метод
        // , который будет использоваться для получения одиночки

        if (instance == null)
            instance = new BotSetting();
        return instance;

    }

    {
        try {
            properties = new Properties();
            try (FileInputStream fileInputStream = new FileInputStream(PATH_TO_PROPERTIES) {
            }) {
                properties.load(fileInputStream);
            } catch (IOException e) {
                try {
                    throw new IOException(String.format("Error loading properties file '%s'", PATH_TO_PROPERTIES));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            tokenBot = properties.getProperty("tokenBot");
            if (tokenBot == null) {
                throw new RuntimeException("TokenBot value is NULL");
            }
            userName = properties.getProperty("nameBot");
            if (userName == null)
                throw new RuntimeException("NameBot value is NULL");
            tokenWeather = properties.getProperty("tokenWeather");
            if (userName == null)
                throw new RuntimeException("tokenWeather value is NULL");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}

