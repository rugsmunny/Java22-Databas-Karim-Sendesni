package org.example.Helpers;


import org.example.Controllers.AccountController;
import org.example.Models.User;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ViewHelper {

    public static String getUserInput() {
        return new Scanner(System.in).nextLine().trim();
    }

    public static boolean checkForValidInput(JSONObject credentials, int x, String key) {

        sout("Checking valid input -> " + key);
        while (true) {

            try {
                String str = getUserInput();

                if (str.length() > 0) {
                    switch (x) {
                        case 0 -> {
                            if (isValidSSN(str)) {
                                sout("isValidSSN true");
                                credentials.put(key, BigInteger.valueOf(Long.parseLong(str.replace("-", ""))));
                                sout(String.valueOf(credentials));
                                return true;
                            }
                        }
                        case 1 -> {
                            if (signatureCheck(str)) {
                                sout("signatureCheck true");
                                credentials.put(key, Long.parseLong(str));
                                sout(String.valueOf(credentials));
                                return true;
                            }
                        }
                        case 2, 3 -> {
                            if (containsOnlyLetters(str)) {
                                sout("containsOnlyLetters true");
                                credentials.put(key, str);
                                sout(String.valueOf(credentials));
                                return true;
                            }
                        }
                        case 4 -> {
                            sout("credentials true");
                            credentials.put(key, str);
                            sout(String.valueOf(credentials));
                            return true;
                        }
                    }

                } else {
                    credentials.clear();
                    return false;
                }
            } catch (Exception ignored) {
                sout("Something went wrong, please try again or come back later.");
            }

            if (credentials.length() == 5) {
                sout(String.valueOf(credentials));
                return true;
            }
        }
    }

    public static boolean containsOnlyLetters(String str) {
        if (!str.matches("[a-öA-Ö]+")) {
            sout("Use only letters, no numbers or special characters. ( To exit register process just press ENTER )");
            return false;
        }
        return true;
    }

    public static boolean signatureCheck(String str) {
        if (str.length() != 4 || !str.matches("[0-9]+")) {
            sout("Your signature should only contain 4 numbers. ( To exit process just press ENTER )");
            return false;
        }

        return true;
    }

    public static boolean moneyInputCheck(String str) {
        if (Integer.parseInt(str) < 0 || !str.matches("[0-9]+")) {
            sout("Value does not compjutter at all, please try again. ( To exit process just press ENTER )");
            return false;
        }

        return true;
    }

    public static boolean phoneNumberCheck(String str) {
        if (str.length() != 10 || (!str.matches("[0-9]+") || !str.startsWith("07"))) {
            sout("The cellphone number should consist of exactly 10 digits. ( To exit process just press ENTER )");
            return false;
        }

        return true;
    }

    public static boolean isValidSSN(String str) {
        if (str.matches("\\d{8}-\\d{4}") || str.matches("\\d{8}\\d{4}")) {

            String dateStr = str.substring(0, 8);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate date = LocalDate.parse(dateStr, formatter);

            LocalDate minDate = LocalDate.now().minusYears(18);
            LocalDate maxDate = LocalDate.now().minusYears(100);

            if (date.isAfter(minDate)) {
                sout("You can't be serious(...?) ( To exit register process just press ENTER )");
                return false;
            } else if (date.isBefore(maxDate)) {
                sout("The minimum age to register is 18. ( To exit register process just press ENTER )");
                return false;
            }
        } else {
            sout("Use only numbers and the right SSN format YYYYMMDD-XXXX. ( To exit register process just press ENTER )");
            return false;
        }
        return true;
    }
    public static String capFirstLetter(String getterStr) {

        if (getterStr == null || getterStr.isEmpty()) {
            return getterStr;
        }

        return getterStr.substring(0, 1).toUpperCase() + getterStr.substring(1).toLowerCase();
    }
    public static BigInteger bigIt(String str) {
        return BigInteger.valueOf(Long.parseLong(str));
    }

    public static <T> void printOutModelValues(T model) {
        try {
            Field[] fields = model.getClass().getDeclaredFields();
            sout("\n---------------------------------------------\n");
            for (Field field : fields) {
                field.setAccessible(true);

                if (!field.getName().equals("id")
                        && !field.getName().equals("password")
                        && !field.getName().equals("signature_number")
                        && !field.getName().equals("logged_in")
                        && !field.getName().equals("user_id")) {

                        sout(field.getName() + ": " + field.get(model));
                    }

            }sout("\n---------------------------------------------");
            if (model instanceof User) {
                new AccountController().getAccounts(((User) model).getId());
            }

        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to access fields of the object", e);
        }
    }

    public static void sout(String str) {
        System.out.println(str);
    }

    public static void warning() {
        sout("Your actions are being monitored, tread carefully.");
    }

}
