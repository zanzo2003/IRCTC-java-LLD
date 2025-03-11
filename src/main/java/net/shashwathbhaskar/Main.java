package net.shashwathbhaskar;

import net.shashwathbhaskar.utilities.UserServiceutil;

public class Main {
    public static void main(String[] args) {
        UserServiceutil abc = new UserServiceutil();
        String password = "hello";
        String hashpswd = abc.hashPassowrd(password);
        Boolean verified = abc.verifyPassword(hashpswd, password);
        System.out.println("The real password is : - " + password);
        System.out.println("The hashed passoword is : - " + hashpswd);
        System.out.println("Is it verified ? " + (verified? "Yes": "No"));

    }
}