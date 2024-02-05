package solutions.wo.it.security;

import org.apache.commons.lang3.StringUtils;
import solutions.wo.it.data.core.exceptions.EncryptException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Password {
    private Password() {}

    public static String encrypt(String content) throws EncryptException {
        if (StringUtils.isBlank(content)) {
            throw new EncryptException("The received password for encryption cannot be empty!");
        }

        String encryptedPassword = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(content.getBytes());
            encryptedPassword = Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if (StringUtils.isBlank(encryptedPassword)) {
            throw new EncryptException("Something went wrong. the encryption result was an empty value!");
        }

        return encryptedPassword;
    }
}
