package net.shashwathbhaskar.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.shashwathbhaskar.models.Ticket;
import net.shashwathbhaskar.models.User;
import net.shashwathbhaskar.utilities.UserServiceutil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import java.io.File;
import java.util.Optional;


public class UserService {

    private User user;

    private List<User> usersList;

    private static final String USERS_PATH = "src/main/java/net/shashwathbhaskar/localDB/users.json";

    private static final String TRAINS_PATH = "src/main/java/net/shashwathbhaskar/localDB/trains.json";

    private ObjectMapper objectMapper = new ObjectMapper();

    private void writeToFile() throws IOException{
        FileWriter users = new FileWriter(USERS_PATH);
        objectMapper.writeValue(users, this.usersList);
        return ;
    }

    public UserService(User currentUser) throws IOException{
        this.user = currentUser;
        this.usersList = loadUsers();
    }

    public UserService() throws IOException{
        this.usersList = loadUsers();
    }

    private List<User> loadUsers() throws IOException{
        File users = new File(USERS_PATH);
        return objectMapper.readValue(users , new TypeReference<List<User>>() {});
    }

    public Boolean login(User user){
        /*
        User foundUser = null;
        for(User currentUser: this.usersList){
            if(currentUser.getUsername().equals(user.getUsername())
                    && currentUser.getPassword().equals(user.getPassword())){
                foundUser = currentUser;
                break;
            }
        }
        if(foundUser != null) this.user = foundUser;
        return foundUser != null;

         */

        Optional<User> foundUser = this.usersList  // using lambda expression and streams
                .stream()
                .filter(currUser ->
                        currUser.getUsername().equals(user.getUsername())
                                && (UserServiceutil.verifyPassword(currUser.getHashPassword(), user.getPassword())))
                .findFirst();
        if(foundUser.isPresent()) this.user = foundUser.get();

        return foundUser.isPresent();
    }

    public Boolean register(User user) throws Exception{
        try{
            usersList.add(user);
            writeToFile();
            return Boolean.TRUE;
        }catch(Exception e){
            throw new IOException("Failed to register user " + e.getMessage(), e);
        }
    }


    public void fetchBookings(){
        List<Ticket> allBookings = this.user.getTickets();
        if(allBookings.isEmpty()) System.out.println("No Bookings");
        else allBookings.forEach(ticket-> TicketService.getTicketInfo(ticket));
    }

    public Boolean cancelBooking(String ticketId) throws IOException{
        List<Ticket> allBookings = this.user.getTickets();
        boolean removed = allBookings.removeIf(ticket -> ticket.getUuid().equals(ticketId));

        if (removed) {
            this.user.setTickets(allBookings);
            writeToFile();
            return true;
        }
        return false;
    }

}
