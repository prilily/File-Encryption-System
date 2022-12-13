package main.java;

import java.io.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;


public class Algo {
    private static final String ALGORITHM="AES";
    private static final String TRANSFORMATION="AES";

    public static void encrypt(String key,File input_file,File output_file) throws CryptoException{
            doCrypto(Cipher.ENCRYPT_MODE,key,input_file,output_file);
        
    }

    public static void decrypt(String key,File input_file,File output_file) throws CryptoException{
            doCrypto(Cipher.DECRYPT_MODE,key,input_file,output_file);
        
    }

    private static void doCrypto(int cipherMode,String key,File input_file,File output_file) throws CryptoException{
        try{
            Key secretKey=new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher=Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream=new FileInputStream(input_file);
            byte[] inputBytes=new byte[(int) input_file.length()];
            inputStream.read(inputBytes);
            byte[] outputBytes=cipher.doFinal(inputBytes);

            FileOutputStream outputStream= new FileOutputStream(output_file);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();
        }
        catch(NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException | NoSuchPaddingException ex){
            throw new CryptoException("Error encrypting/decrypting file",ex);
        }
    }
    

    
}

// continue here https://www.codejava.net/coding/file-encryption-and-decryption-simple-example
