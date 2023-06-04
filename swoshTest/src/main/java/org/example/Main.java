package org.example;

import org.example.DataBase.DbTablesInitializer;
import org.example.Views.UserView;

public class Main {

    public static void main(String[] args)  {

        new DbTablesInitializer();
        new UserView();

    }
}




