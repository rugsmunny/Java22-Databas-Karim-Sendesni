package org.example.Controllers;

import org.example.Helpers.ViewHelper;
import org.example.Models.Account;
import org.example.Models.User;
import org.example.Repositories.AccountRepository;
import java.util.List;

import static org.example.Helpers.ViewHelper.sout;


public class AccountController {
AccountRepository ar = new AccountRepository();

    public void createAccount(Account acc) {
        ar.createAccount(acc);
    }

    public void deleteAccount(String id, User user) {
       ar.deleteAccount(id, user);
    }

    public void getAccounts(int id) {

        List<Account> accountList = ar.getAccounts(id);
        if(accountList != null && accountList.size() > 0){
        accountList.forEach(ViewHelper::printOutModelValues);
        } else sout("You have no accounts to display");

    }

    public boolean updateAccountBalance(boolean deposit, String accNum, String amount) {
        return ar.updateAccountBalance(deposit, accNum, amount);
    }
}
