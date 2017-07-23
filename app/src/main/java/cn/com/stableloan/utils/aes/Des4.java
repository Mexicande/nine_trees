package cn.com.stableloan.utils.aes;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * Created by apple on 2017/7/19.
 */

public class Des4 {

    private static byte[] iv = {0, 1, 2, 3, 4, 5, 6, 7};

    public static String encode(String plainText,String secretKey) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(secretKey.getBytes());
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(plainText.getBytes());
        return Base64.encodeToString(encryptedData, 1);
    }

    public static String decode(String encryptText,String secretKey) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(secretKey.getBytes());
        SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte[] decryptData = cipher.doFinal(Base64.decode(encryptText, 1));
        return new String(decryptData);
    }
}
