package org.example.Repositories;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.example.DataBase.QueryBuilder;
import org.example.Helpers.DbHelper;
import org.example.Models.User;
import org.example.Views.UserView;
import java.lang.reflect.Method;

import static org.example.Helpers.ViewHelper.*;

public class UserRepository {

    private static DbHelper dbH;
    QueryBuilder qb = new QueryBuilder();
    public UserRepository() {
        dbH = DbHelper.getInstance();
    }


    public void setUserLoginStatus(User user) {
        if (!user.getLogged_in()){
            try{
                String dbPsw = fetchAllDataFromUser(user).getPassword();
                String inputPsw = user.getPassword();
                sout("1 db -> " + dbPsw + ", inputPws -> " + inputPsw);
                if(!verifyPws(inputPsw, dbPsw)){
                    sout("Password not matching, please try again");
                    return;
                }
            } catch (Exception e){
                sout("User not available. -> " + e);
            }

        }
        qb.update("users", "logged_in").where(false,"ssn");
        Object[] params = {!user.getLogged_in(), user.getSsn()};

        if (dbH.executeUpdate(qb.build(), params)) {

            if (!user.getLogged_in()) {
                sout("Login succeeded");
                new UserView(fetchAllDataFromUser(user));
            } else {
                sout("Logout succeeded");
            }

        } else {
            if (!user.getLogged_in()) {
                sout("Login failed");
            } else {
                sout("Logout failed");
            }
        }

    }

    public void createUserAccount(User newUser) {
        qb.insertInto("users", "first_name", "last_name", "password", "signature_number", "ssn");
        Object[] params = {newUser.getFirst_name(), newUser.getLast_name(), encrypt(newUser.getPassword()), newUser.getSignature_number(), newUser.getSsn()};
        sout("User account created status -> " + dbH.executeUpdate(qb.build(), params));

    }

    public boolean deleteUser(User user) {
        qb.delete("users").where(false,"id");
        if (dbH.executeUpdate(qb.build(), user.getId())) {
            sout("User account deletion status -> true");
            return true;
        } else {
            sout("User account deletion status -> false");
            return false;
        }

    }

    public User fetchAllDataFromUser(User user) {
        qb.select("users").where(false,"ssn");
        return dbH.executeQuery(qb.build(), User.class, user.getSsn()).get(0);
    }

    public User updateCredentials(User user, String paramToChange) {

        String firstEntryValue, secondEntryValue, updateSql, column;
        column = paramToChange.equals("name") ? "first_name = ?, last_name = ? " : paramToChange.concat(" = ? ");
        updateSql = "UPDATE users SET " + column + " WHERE id = ?;";

        try {
            Method getter;
            if (paramToChange.equals("name")) {
                sout("Enter signature to initiate change:");

                getter = User.class.getMethod("getSignature_number");
            } else {
                sout("Enter " + paramToChange + " to initiate change:");

                getter = User.class.getMethod("get" + capFirstLetter(paramToChange));
            }

            if (getter.invoke(fetchAllDataFromUser(user)).toString().equals(getUserInput())) {

                if (paramToChange.equals("name")) {
                    sout("Enter first name :");
                } else {
                    sout("Enter new " + paramToChange + ":");
                }
                firstEntryValue = getUserInput();

                if (paramToChange.equals("name")) {
                    sout("Enter last name :");
                } else {
                    sout("Repeat new " + paramToChange + ":");
                }
                secondEntryValue = getUserInput();


                boolean result = paramToChange.equals("name") ? DbHelper.getInstance().executeUpdate(updateSql, firstEntryValue, secondEntryValue, user.getId())
                        : DbHelper.getInstance().executeUpdate(updateSql, firstEntryValue, user.getId());

                user = result ? fetchAllDataFromUser(user) : user;
                sout(paramToChange + " update operation result -> " + result);

            } else {
                sout("Updating " + paramToChange + " initiation failed, value missmatch. Please try again.");
            }

        } catch (Exception e) {
            sout("Updating " + paramToChange + " not possible right now, try again later. See exception -> " + e);
        }
        return user;
    }

    private String encrypt(String str){

        return BCrypt.withDefaults().hashToString(12, str.toCharArray());
    }

    private boolean verifyPws(String input, String dbData){
        return BCrypt.verifyer().verify(input.toCharArray(), dbData).verified;
    }
}

