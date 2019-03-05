/*Домашнее задание 1. Хэширование
Разработайте Java-программу CalcMD5, которая подсчитывает MD5-хэши файлов.Программа должна
принимать один аргумент командной строки — имя файла, в котором содержатся имена файлов,
для которых требуется подсчитать хэши. Файлы перечислены по одному на строке.Программа должна
выдать на стандартный вывод MD5-хэши файлов в порядке их перечисления во входном файле. Хэши
должны выдаваться в виде 32-значных шестнадцатеричных чисел.Например, если файл input.txt
содержит только input.txt (9 символов), то при запуске java CalcMD5 input.txt, на консоль
должно быть выведено A8546347050ADC932FBEC189DC9FD50D.
Примечания.
Стандартная библиотека Java содержит реализацию алгоритма MD5.
Вы можете рассчитывать, что все файлы помещаются в память.
Можно написать решение, состоящее из четырех содержательных строк.

*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class CalcMD5 {
    public static String strToHex(byte[] str) {
        String hexStr = "";
        for (byte eachByte : str) {
            hexStr += String.format("%02x", eachByte);
        }
        return hexStr.toString();
    }

    public static void main(String[] args) throws Exception {
        BufferedReader mainFile = new BufferedReader(new FileReader(args[0]));
        String fileName;

        while ((fileName = mainFile.readLine()) != null) {
            FileInputStream file = new FileInputStream(fileName);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] content = new byte[3000];
            int numBytes = 0;
            while ((numBytes = file.read(content)) != -1) {
                md5.update(content, 0, numBytes);
            }
            System.out.println(strToHex(md5.digest()));
        }

    }
}