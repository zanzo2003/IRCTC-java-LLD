package net.shashwathbhaskar.utilities;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class UserServiceutil {

    private static final int saltRounds = 12;


    public static String hashPassowrd(String password){
        return BCrypt.withDefaults().hashToString( saltRounds, password.toCharArray());
    }

    public static Boolean verifyPassword(String hashPassword, String loginPassword){
        return BCrypt.verifyer().verify(loginPassword.toCharArray(), hashPassword.toCharArray()).verified;
    }
}
