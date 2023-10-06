package com.binarfud.challenge4.view;

public class MenuView {
    public static final String SEPARATOR = "===============================================================";
    public static void showMenu(){
        System.out.println(SEPARATOR);
        System.out.println(centerText("SELAMAT DATANG DI BINAR ", SEPARATOR.length()));
        System.out.println(SEPARATOR);
        System.out.println("1. Show Product\n2. Show Merchant \n3. Show Users\n4. Orders\n0. Exit");
        System.out.print("Select menu => ");
    }

    public static String centerText(String text, int width) {
        int padding = (width - text.length()) / 2;
        StringBuilder centeredText = new StringBuilder();
        for (int i = 0; i < padding; i++) {
            centeredText.append(" ");
        }
        centeredText.append(text);
        for (int i = 0; i < padding; i++) {
            centeredText.append(" ");
        }
        if (centeredText.length() < width) {
            centeredText.append(" ");
        }
        return centeredText.toString();
    }
    public static void showError(){
        System.out.println(SEPARATOR +"\n" +
                centerText("INPUT TIDAK VALID", SEPARATOR.length()) +
                "\n" +SEPARATOR + "\n");
        System.out.println("(Y) untuk Lanjut\n(N) untuk Keluar");
        System.out.print("=> ");
    }
}
