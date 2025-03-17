package net.shashwathbhaskar;

import net.shashwathbhaskar.models.Ticket;
import net.shashwathbhaskar.models.Train;
import net.shashwathbhaskar.models.User;
import net.shashwathbhaskar.services.UserService;
import net.shashwathbhaskar.utilities.UserServiceutil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Running IRCTC application......");
        System.out.println(UserServiceutil.hashPassowrd("shashwath"));
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        UserService userService;
        // to load all the users in the User Service
        try{
            userService = new UserService();
        }catch(IOException ex){
            System.out.println("Somthing is wrong!!" + ex.getMessage());
            return ;
        }

        while(option!=7) {
            System.out.println("\n");
            System.out.println("Choose option");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a Seat");
            System.out.println("6. Cancel my Booking");
            System.out.println("7. Exit the App");
            option = scanner.nextInt();

            String username;
            String password;
            String source;
            String destination;
            switch (option){
                case 1:
                    System.out.println("Enter your username : ");
                    username = scanner.next();
                    System.out.println("Enter your password : ");
                    password = scanner.next();
                    User newUser = new User(username, password,
                            UserServiceutil.hashPassowrd(password),
                            new ArrayList<Ticket>(),UUID.randomUUID().toString());
                    try {
                        userService.register(newUser);
                    } catch (Exception e) {
                        throw new IOException(e);
                    }
                    System.out.println("Registration successful!!");
                    break;

                case 2:
                    System.out.println("Enter your username : ");
                    username = scanner.next();
                    System.out.println("Enter your password : ");
                    password = scanner.next();
                    User loginUser = new User(username, password);
                    System.out.println(userService.login(loginUser)? "Login successfull!": "Invalid credentials");
                    break;

                case 3:
                    System.out.println("Fetching your bookings.....");
                    userService.fetchBookings();
                    break;

                case 4:
                    System.out.println("Enter Boarding station : ");
                    source = scanner.next();
                    System.out.println("Enter Destination station : ");
                    destination = scanner.next();
                    Optional<Train> train = userService.searchTrains(source, destination);
                    if(train.isPresent()){
                        System.out.println("Train no : " + train.get().getTrainNo());
                        System.out.printf("Starting time from  %s : %s \n" ,source, train.get().getTimings().get(source));
                        System.out.printf("Arrival time at  %s : %s" , destination, train.get().getTimings().get(destination));
                        break;
                    }
                    System.out.println("No trains available !");
                    break;

                case 5:
                    System.out.println("Enter Boarding station : ");
                    source = scanner.next();
                    System.out.println("Enter Destination station : ");
                    destination = scanner.next();
                    System.out.println("Enter Train ID : ");
                    String trainID = scanner.next();
                    System.out.println("Enter Row : ");
                    int row = scanner.nextInt();
                    System.out.println("Enter Seat : ");
                    int seat = scanner.nextInt();
                    System.out.println(userService.reserveSeat(source, destination, trainID, row, seat));
                    break;

                case 6:
                    System.out.println("Enter Booking Id : ");
                    String ticketId = scanner.next();
                    System.out.println(userService.cancelBooking(ticketId)? "Your Booking has been cancelled!": "Booking Not Found");

                case 7:
                    System.out.println("Exit IRCTC...");
                    break;
            }
        }

    }
}