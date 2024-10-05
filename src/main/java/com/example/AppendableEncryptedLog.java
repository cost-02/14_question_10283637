package com.example;

import java.io.FileOutputStream;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class AppendableEncryptedLog {
    public static void main(String[] args) {
        try {
            byte[] key = "1234567890123456".getBytes("UTF-8"); // Chiave segreta di 16 byte
            byte[] iv = new byte[16]; // IV nullo per semplicità, idealmente dovrebbe essere unico e casuale
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

            FileOutputStream fileOut = new FileOutputStream("encrypted.log", true);
            CipherOutputStream cipherOut = new CipherOutputStream(fileOut, cipher);

            String message = "Messaggio segreto";
            byte[] messageBytes = message.getBytes("UTF-8");
            // Assicurati che messageBytes sia un multiplo di 16 byte
            if (messageBytes.length % 16 != 0) {
                int paddingLength = 16 - (messageBytes.length % 16);
                byte[] paddedMessage = new byte[messageBytes.length + paddingLength];
                System.arraycopy(messageBytes, 0, paddedMessage, 0, messageBytes.length);
                cipherOut.write(paddedMessage);
            } else {
                cipherOut.write(messageBytes);
            }

            cipherOut.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
