package lk.ijse.mental_health_therapy_center.util;

import org.mindrot.jbcrypt.BCrypt;

public class Validator {

    public static String hashPassword(String plainPassword) {
            return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
        public  static boolean verifyPassword(String plainPassword, String hashedPassword) {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        }

}

