
package org.example.Views;

import org.example.Controllers.AccountController;
import org.example.Controllers.TransactionController;
import org.example.Models.Transaction;
import org.example.Models.User;
import static org.example.Helpers.ViewHelper.*;


public class TransactionView {

    private final TransactionController tc = new TransactionController();

    private final AccountController ac = new AccountController();

    public TransactionView(User user) {
        displayTransactionView(user);
    }

    private void displayTransactionView(User user) {
        while (true) {
            sout("\n1. Send money\n2. Transaction history\n3. Exit\nChoice (number) : ");

            switch (getUserInput()) {
                case "1" -> sendMoney(user.getId());
                case "2" -> transactionHistory(user.getId());
                case "3" -> {
                    sout("Shutting down.");
                    return;
                }
                default -> sout("Invalid choice. Please select a valid option.\n");
            }
        }
    }

    private void sendMoney(int userId) {


        try {

            ac.getAccounts(userId);

            String sendAcc;
            do {
                sout("\nSelect account to send money from ( Attention! Use cellphone number) :");
                sendAcc = getUserInput();
                if(sendAcc.length() < 1) return;
            } while (!phoneNumberCheck(sendAcc));


            String recAcc;
            do {
                sout("\nEnter recipient's phone number:");
                recAcc = getUserInput();
                if(recAcc.length() < 1) return;
            } while (!phoneNumberCheck(recAcc));

            String amount;
            do {
                sout("\nEnter amount to send:");
                amount = getUserInput();
                if(recAcc.length() < 1) return;
            } while (!moneyInputCheck(amount));



            if (tc.processTransaction(new Transaction(recAcc, sendAcc, bigIt(amount)))) {
                sout("\nMoney transfer successful.");
            } else {
                sout("\nMoney transfer failed.");
            }
        } catch (Exception e) {
            sout("\nAn error occurred during money transfer.");
        }
    }



    private void transactionHistory(int userId) {
        while (true) {
            sout("\n1. All accounts\n2. Specific account\n3. Exit\nChoice (number): ");
            System.out.print("");
            String choice = getUserInput();
            switch (choice) {
                case "1", "2"-> displayAccountTransactions(userId, Integer.parseInt(choice));
                case "3" -> {
                    return;
                }
                default -> sout("Invalid choice. Please select a valid option.\n");
            }
        }
    }


    public void displayAccountTransactions(int userId, int choice) {
        String target = String.valueOf(userId);
        if (choice == 2) {
            ac.getAccounts(userId);
            sout("\nSelect account: ");
            target = getUserInput();
        }

        String[] options = {"All transactions", "Outgoing transactions", "Incoming transactions"};
        String[] sortOptions = {"Sort by date", "Sort by amount"};
        String[] order = {"Sort by ascending", "Sort by descending"};

        int option = getUserOption("Select option:", options);
        int sortType = getUserOption("Select sort option:", sortOptions);
        int orderType = getUserOption("Select order option:", order);

        int numberOfDays = 0;
        if (sortType <= 1) {
            numberOfDays = getNumberOfDays();
        }

        tc.getTransactions(target, option, sortType, orderType, numberOfDays);
    }

    private int getUserOption(String msg, String[] options) {
        sout(msg);
        for (int i = 0; i < options.length; i++) {
            sout("\n" + (i + 1) + ". " + options[i]);
        }

        while (true) {
            sout("Choice (number): ");
            try {
                int choice = Integer.parseInt(getUserInput());
                if (choice >= 1 && choice <= options.length) {
                    return choice - 1;
                }
            } catch (NumberFormatException ignored) {
                warning();
            }
        }
    }

    private int getNumberOfDays() {
        while (true) {
            sout("\nEnter number of days: ");
            try {
                int numberOfDays = Integer.parseInt(getUserInput());
                if (numberOfDays >= 0) {
                    return numberOfDays;
                } else warning();
            } catch (Exception ignored) {
                warning();
            }
        }
    }

}

