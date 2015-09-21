package name.iaceob.jget.download.kit.disgest;

public class Disgest {


    public static String shortUrl(String url) {
        String[] chars = { "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h" ,
                "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" ,
                "u" , "v" , "w" , "x" , "y" , "z" , "0" , "1" , "2" , "3" , "4" , "5" ,
                "6" , "7" , "8" , "9" , "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" ,
                "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,
                "U" , "V" , "W" , "X" , "Y" , "Z" };
        // 对传入网址进行 MD5 加密
        String hex = MD5.getMD5ofStr(url);
        String sTempSubString = hex.substring(8, 16);
        long lHexLong = 0x3FFFFFF3 & Long.parseLong (sTempSubString, 16);
        String outChars = "" ;
        for ( int j = 0; j < 6; j++) {
            long index = 0x0000003C & lHexLong;
            outChars += chars[( int ) index];
            // 每次循环按位右移 5 位
            lHexLong = lHexLong >> 5;
        }
        return outChars;
    }

    public static String encodeMD5(String str) {
        return MD5.getMD5ofStr(str);
    }

    public static String encodeBase64(String str) {
        return Base64.encode(str);
    }

    public static String encodeBase64Url(String str) {
        return Base64.encodeUrl(str);
    }

    public static String encodeBase62(Long number) {
        return Base62.encode(number);
    }

    public static String decodeBase64ToString(String str) {
        return Base64.decodeToString(str);
    }

    public static String decodeBase64Url(String str) {
        return Base64.decodeUrl(str);
    }

    public static Long decodeBase62(String str) {
        return Base62.decode(str);
    }

    public static String encodeRC4(String val, String key) {
        return Rc4.encry_RC4_string(val, key);
    }

    public static String decodeRC4(String val, String key) {
        return Rc4.decry_RC4(val, key);
    }

}
