package org.example;

import java.nio.charset.StandardCharsets;
import java.util.Map;


public class HomeResourceHandler implements IResourceHandler {


    @Override
    public ResponceContent handle(Map<String, String> params) {
        ResponceContent responceContent = new ResponceContent();
        responceContent.setMimeType("text/html; charset=utf-8");

        // Исходный HTML-контент
        String content = """
        <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
            "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
        <html>
        <head>
            <meta charset="utf-8"/>
            <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
            <title>Home page</title>
            <style>
                body {
                    background-color: rgb(255, 255, 255); /* Цвет по умолчанию - белый */
                }
            </style>
        </head>
        <body>
            <h1>Домашняя HTML страница</h1>
        </body>
        </html>
        """;

        // Проверяем корректность RGB-значений
        if (checkIfRGBIsValid(params)) {
            String rgb = "rgb(" + params.get("red") + ", " + params.get("green") + ", " + params.get("blue") + ")";
            content = content.replace("rgb(255, 255, 255)", rgb); // Заменяем цвет по умолчанию на переданный
        }

        responceContent.setContent(content.getBytes(StandardCharsets.UTF_8));
        return responceContent;
    }

    private boolean checkIfRGBIsValid(Map<String, String> params) {
        return checkIfByte(params.get("red"))&& checkIfByte(params.get("green")) && checkIfByte(params.get("blue"));
    }

    private boolean checkIfByte(String code) {
        return isInteger(code) && Integer.parseInt(code) >= 0 && Integer.parseInt(code) <= 255;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

}
