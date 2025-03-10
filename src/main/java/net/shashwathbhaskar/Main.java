package net.shashwathbhaskar;

import net.shashwathbhaskar.models.Ticket;
import net.shashwathbhaskar.models.User;
import net.shashwathbhaskar.services.UserService;
import net.shashwathbhaskar.utilities.UserServiceutil;

import java.io.IOException;
import java.util.ArrayList;
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

            }
        }

    }
}