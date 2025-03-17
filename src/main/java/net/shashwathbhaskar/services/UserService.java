package net.shashwathbhaskar.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.shashwathbhaskar.models.Ticket;
import net.shashwathbhaskar.models.Train;
import net.shashwathbhaskar.models.User;
import net.shashwathbhaskar.utilities.UserServiceutil;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import java.io.File;
import java.util.Optional;
import java.util.UUID;


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

    private List<Train> loadTrains() throws IOException{
        File trains = new File(TRAINS_PATH);
        return objectMapper.readValue(trains, new TypeReference<List<Train>>() {});
    }

    public Boolean login(User user){
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

    public Optional<Train> searchTrains(String source, String desitination) throws IOException{
        try {
            List<Train> allTrains = loadTrains();
            Optional<Train> firstTrain = allTrains.stream()
                    .filter(train -> {
                        //System.out.println(train.getTrainNo());
                        return train.getTimings().containsKey(source)
                                && train.getTimings().containsKey(desitination)
                                && train.getTimings().get(source).toLocalTime().isBefore(train.getTimings().get(desitination).toLocalTime());
                    }).findFirst();
            return firstTrain;
        }catch(IOException ex){
            throw new IOException(ex);
        }
    }

    public void fetchBookings(){
        List<Ticket> allBookings = this.user.getTickets();
        if(allBookings.isEmpty()) System.out.println("No Bookings");
        else allBookings.forEach(ticket-> TicketService.getTicketInfo(ticket));
    }

    public String reserveSeat(String source, String destination, String trainId, int row, int seat) throws IOException{
        try{
            List<Train> trains = loadTrains();
            Optional<Train> train = trains.stream()
                    .filter(t -> t.getTrainNo().equals(trainId))
                    .findFirst();
            if(train.isPresent()){
                List<List<Boolean>> seats = train.get().getSeats();
                if(!seats.get(row).get(seat)){
                    seats.get(row).set(seat, Boolean.TRUE);
                    train.get().setSeats(seats);
                    Ticket newTicket = new Ticket(UUID.randomUUID().toString(), this.user.getUuid(), source, destination, new Date(), train.get());
                    List<Ticket> bookings = this.user.getTickets();
                    bookings.add(newTicket);
                    this.user.setTickets(bookings);
                    writeToFile();
                    return "Booking completed. Ticket Id : " + newTicket.getUuid();
                }
                else{
                    return "Seat is already Booked!!";
                }
            }
            return "Train not Present";
        }catch (IOException ex) {
            throw new IOException("Sorry! Please try again!");
        }
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
