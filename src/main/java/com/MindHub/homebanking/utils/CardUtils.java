package com.MindHub.homebanking.utils;

public final class CardUtils {

    private CardUtils(){}

    public static int getCVV() {
        int cvv = getRandomNumber(100, 999);
        return cvv;
    }

    public static String getCardNumber() {
        String number = getRandomNumber(1000,9999) + " "+"-"+" " + getRandomNumber(1000,9999) + " "+"-"+" " + getRandomNumber(1000,9999) + " "+"-"+" " + getRandomNumber(1000,9999);
        return number;
    }

    public static int getRandomNumber(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }
}
