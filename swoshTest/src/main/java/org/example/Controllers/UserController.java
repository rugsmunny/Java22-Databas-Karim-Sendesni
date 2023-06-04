package org.example.Controllers;


import org.example.Models.User;
import org.example.Repositories.UserRepository;



public class UserController {
UserRepository ur = new UserRepository();

    public void updateLoginStatus(User user) {

        ur.setUserLoginStatus(user);

    }
    public void createAccount(User newUser) {

        ur.createUserAccount(newUser);

    }

    public boolean deleteAccount(User user) {

       return ur.deleteUser(user);

    }

    public User changeCredentials(User user, String paramToChange){
        return ur.updateCredentials(user, paramToChange);
    }


}
