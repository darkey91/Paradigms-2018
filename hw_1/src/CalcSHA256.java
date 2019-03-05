import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class CalcSHA256 {
    public static String strToHex(byte[] str) {
        StringBuilder hexStr = new StringBuilder();
        for (byte eachByte : str) {
            hexStr.append(String.format("%02x", eachByte)); //string builder тут надо как-то исправить и идея может это сделать самостоятельно


        }
        return hexStr.toString();
    }

    public static void main(String[] args) throws Exception {
        BufferedReader mainFile = new BufferedReader(new FileReader(args[0]));
        String fileName;

        while ((fileName = mainFile.readLine()) != null) {
            FileInputStream file = new FileInputStream(fileName);
            MessageDigest md5 = MessageDigest.getInstance("SHA-256");
            byte[] content = new byte[2048];
            int numBytes = 0;
            while ((numBytes = file.read(content)) != -1) {
                md5.update(content, 0, numBytes);
            }
            System.out.println(strToHex(md5.digest()));
        }

    }
}