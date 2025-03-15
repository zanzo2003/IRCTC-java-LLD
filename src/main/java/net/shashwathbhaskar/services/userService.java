package net.shashwathbhaskar.services;

import net.shashwathbhaskar.models.User;

import java.io.File;

public class userService {

    private User currentUser;

    private static final String USERS_PATH = "../localDB/users.json";

    private static final String TRAINS_PATH = "../localDB/trains.json";

    public userService(User currentUser){
        this.currentUser = currentUser;
        File users = new File(USERS_PATH);


    }


}
