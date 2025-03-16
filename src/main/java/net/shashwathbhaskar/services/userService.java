package net.shashwathbhaskar.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.shashwathbhaskar.models.User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import java.io.File;
import java.util.Optional;

public class userService {

    private User user;

    private List<User> usersList;

    private static final String USERS_PATH = "src/main/java/net/shashwathbhaskar/localDB/users.json";

    private static final String TRAINS_PATH = "src/main/java/net/shashwathbhaskar/localDB/trains.json";

    private ObjectMapper objectMapper = new ObjectMapper();

    private void writeToFile() throws IOException{
        FileWriter users = new FileWriter(USERS_PATH);
        users.write(objectMapper.writeValueAsString(this.usersList));
        return ;
    }

    public userService(User currentUser) throws IOException{

        this.user = currentUser;
        File users = new File(USERS_PATH);
        this.usersList = objectMapper.readValue(users, new TypeReference<List<User>>() {});

    }

    public Boolean login(User user){
        User foundUser = null;
        for(User currentUser: this.usersList){
            if(currentUser.getUsername().equals(user.getUsername())
                    && currentUser.getPassword().equals(user.getPassword())){
                foundUser = currentUser;
                break;
            }
        }
        if(foundUser != null) this.user = foundUser;
        return !(foundUser.equals(null));
    }

    public Boolean register(User user) throws Exception{
        try{
            usersList.add(user);
            writeToFile();
            return Boolean.TRUE;
        }catch(Exception e){
            throw new Exception();
        }
    }

}
