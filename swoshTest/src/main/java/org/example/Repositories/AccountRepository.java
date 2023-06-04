package org.example.Repositories;

import org.example.DataBase.QueryBuilder;
import org.example.Helpers.DbHelper;
import org.example.Models.Account;
import org.example.Models.User;
import java.util.List;
import static org.example.Helpers.ViewHelper.bigIt;
import static org.example.Helpers.ViewHelper.sout;


public class AccountRepository {

    private static DbHelper dbH;
    QueryBuilder qb = new QueryBuilder();

    public AccountRepository() {
        dbH = DbHelper.getInstance();
    }

    public void createAccount(Account acc) {

        qb.insertInto("accounts", "phone_number", "user_id", "balance");
        Object[] params = {acc.getPhone_number(), acc.getUser_id(), acc.getBalance()};
        sout("Account created status -> " + dbH.executeUpdate(qb.build(), params));

    }

    public List<Account> getAccounts(int userId) {
        qb.select("accounts").where(false, "user_id");
        return dbH.executeQuery(qb.build(), Account.class, userId);
    }

    public void deleteAccount(String id, User user) {
        qb.delete("accounts").where(false, "phone_number", "user_id");
        sout("Account deletion status -> " + dbH.executeUpdate(qb.build(), id, user.getId()));
    }


    public boolean updateAccountBalance(boolean bool, String phoneNum, String amount) {

        if(bool){
            qb.updateAccount("accounts", "balance", " + ").where(false, "phone_number");
            return dbH.executeUpdate(qb.build() + ";", bigIt(amount),phoneNum);
        } else {

            qb.select("accounts").where(false, "phone_number", "balance");
            String q = qb.build().replace("balance = ", "balance >= ");

            if(dbH.executeQuery(q, Account.class, phoneNum, amount).size() > 0 ){
                String condition = "CASE WHEN balance >= ? THEN balance - ? ELSE balance END ";
                qb.update("accounts", "balance").where(false, "phone_number");
                String query = qb.build().replaceFirst("\\?",condition);
                return dbH.executeUpdate(query + ";", bigIt(amount), bigIt(amount), phoneNum);
            } else return false;


        }


    }
}

