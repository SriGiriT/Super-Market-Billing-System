package com.billingsystem.utility;

import org.mindrot.jbcrypt.BCrypt;
/*
 * Bcrypt uses blowfish approach(hashing repetitively).
 * Which generate hash with the salt combined with the plain text. (for each round).
 * which is reason it provide different hash-value even for same plain text. (which prevent rainbow attack )
 * */
public class PasswordUtil {
	public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
