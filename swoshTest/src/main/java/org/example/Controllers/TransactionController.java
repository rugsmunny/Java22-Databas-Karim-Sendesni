package org.example.Controllers;

import org.example.Helpers.ViewHelper;
import org.example.Models.Transaction;
import org.example.Repositories.TransactionRepository;
import java.util.List;

import static org.example.Helpers.ViewHelper.sout;


public class TransactionController {
    TransactionRepository tr = new TransactionRepository();

    public boolean processTransaction(Transaction tran) {
        return tr.processTransaction(tran);
    }

    public void getTransactions(String userOrAccount, int option, int sortType, int orderType, int numberOfDays) {
        List<Transaction> list = tr.getTransactions(userOrAccount, option, sortType, orderType, numberOfDays );
        if(list.size() < 1){
            sout("No transaction found in history.");
        } else list.forEach(ViewHelper::printOutModelValues);
    }


}
