package org.example.Repositories;

import org.example.Helpers.DbHelper;
import org.example.Models.Transaction;
import java.util.ArrayList;
import java.util.List;

import static org.example.Helpers.ViewHelper.sout;
import static org.example.Helpers.ViewHelper.warning;


public class TransactionRepository {

    private static DbHelper dbH;

    public TransactionRepository() {
        dbH = DbHelper.getInstance();
    }

    public boolean processTransaction(Transaction transaction) {
        try {
            String query1 = """
                    UPDATE accounts
                    SET balance = balance - ? WHERE phone_number = ? AND balance >= ?;
                    """;
            String query2 = """
                    UPDATE accounts
                    SET balance = balance + ? WHERE phone_number = ?;""";
            String query3 = """
                    INSERT INTO transactions (sender, recipient, amount) VALUES (?, ?, ?)
                    """;
            if(transaction.getSender().equals(transaction.getRecipient())){
                return true;
            }
            if(dbH.executeUpdate(query1, transaction.getAmount(), transaction.getSender(), transaction.getAmount()) &&
                    dbH.executeUpdate(query2, transaction.getAmount(), transaction.getRecipient())){
                return dbH.executeUpdate(query3, transaction.getSender(), transaction.getRecipient(), transaction.getAmount());
            }

        } catch (Exception e) {
            sout("An error occurred during transaction processing.");
            return false;
        }
        return false;
    }

    public List<Transaction> getTransactions(String target, int option, int sortType, int orderType, int numberOfDays) {

        List<Transaction> transactions = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

        try {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("SELECT * FROM transactions");
            if(target.startsWith("07")){
                strBuilder.append(" WHERE ");
                switch (option){
                    case 0 -> {
                        strBuilder.append("(sender = ? OR recipient = ?) ");
                        parameters.add(target);
                        parameters.add(target);
                    }
                    case 1 -> {
                        strBuilder.append("sender = ? ");
                        parameters.add(target);
                    }
                    case 2 -> {
                        strBuilder.append("recipient = ? ");
                        parameters.add(target);
                    }
                }

            } else {
                strBuilder.append(" JOIN accounts ON ");
                switch (option){
                    case 0 -> strBuilder.append(" (transactions.sender = accounts.phone_number OR transactions.recipient = accounts.phone_number) ");
                    case 1 -> strBuilder.append(" (transactions.sender = accounts.phone_number) ");
                    case 2 -> strBuilder.append(" (transactions.recipient = accounts.phone_number) ");
                }
                strBuilder.append(" WHERE accounts.user_id = ? ");
                parameters.add(target);
            }

            if (sortType == 0 && numberOfDays > 0) {
                strBuilder.append("AND date_time BETWEEN NOW() - INTERVAL ? DAY AND NOW()");
                parameters.add(numberOfDays);
            }
            if (sortType == 0) {
                strBuilder.append(" ORDER BY date_time");
            } else if (sortType == 1) {
                strBuilder.append(" ORDER BY amount");
            }

            if (orderType == 0) {
                strBuilder.append(" ASC");
            } else if (orderType == 1) {
                strBuilder.append(" DESC");
            }

            String query = strBuilder.toString();
            List<Transaction> resultList = dbH.executeQuery(query, Transaction.class, parameters.toArray());

            if (resultList != null) {
                transactions.addAll(resultList);
            }
        } catch (Exception ignored) {
            warning();
        }

        return transactions;
    }

}
