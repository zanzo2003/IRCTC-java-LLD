package net.shashwathbhaskar.utilities;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class UserServiceutil {

    private final int saltRounds = 12;


    public String hashPassowrd(String password){
        return BCrypt.withDefaults().hashToString(this.saltRounds, password.toCharArray());
    }

    public Boolean verifyPassword(String hashPassword, String loginPassword){
        return BCrypt.verifyer().verify(loginPassword.toCharArray(), hashPassword.toCharArray()).verified;
    }
}
