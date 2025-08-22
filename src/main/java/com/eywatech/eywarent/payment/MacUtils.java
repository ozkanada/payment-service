package com.eywatech.eywarent.payment;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class MacUtils {

    public static boolean verifyMac(Map<String, String> params, String storeKey) throws NoSuchAlgorithmException {
        // Bankanın gönderdiği MAC (HashData / SecureHash)
        String receivedMac = params.get("HashData");
        if (receivedMac == null) return false;

        // MAC oluşturmak için sıralı olarak kullanılan parametreleri birleştir
        String data = params.get("clientid") +
                      params.get("oid") +
                      params.get("AuthCode") +
                      params.get("Response") +
                      params.get("ProcReturnCode") +
                      params.get("TransId") +
                      params.get("ErrMsg") +
                      storeKey;

        // SHA-1 ile hashle
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(data.getBytes(StandardCharsets.UTF_8));

        // Hash'i hex string formatına çevir
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02X", b));
        }
        String calculatedMac = sb.toString();

        // Gelen ve hesaplanan MAC eşit mi?
        return calculatedMac.equalsIgnoreCase(receivedMac);
    }
}
