package com.wyfx;

//数据转换操作类
public class TypeCast {

    // string to char bits array, MD5 to binary array

    public static String BitsToHex(char[] data) {
        //1000 0011 0101 00100110010010101000100110100000010001000110101100111011100011111010100010010000100011001101110010111111000101000010
        int len = data.length;

        int x = (int) Math.ceil((len / 4));

        String HexString = "";
        int tmp;

        while (x-- > 0) {
            tmp = 0;
            int base = 1;
            for (int i = 1; i < 5; i++) {

                if (len - i >= 0) {
                    tmp += base * (data[len - i] == '1' ? 1 : 0);
                    base = base * 2;
                }
            }
            len = len - 4;
            HexString = Integer.toHexString(tmp) + HexString;

        }
        System.out.println(HexString);
        return HexString;
    }

    public static char[] HexToBits(char[] data) {

        return null;
    }

    public static char[] StrToBits(String str) {
        // TODO Auto-generated method stub

        String result = "", tmp;
        for (int i = 0; i < str.length(); i++) {
            String s = str.substring(i, i + 1);
            tmp = "0000"
                    + Integer.toBinaryString(Integer.parseInt(
                    str.substring(i, i + 1), 16));
            result += tmp.substring(tmp.length() - 4);
        }
        return result.toCharArray();
    }

    // string to char
    public static char[] StrToChar(String str) {

        return str.toCharArray();
    }

    // double to long
    public static long DoubleToLong(double num) {

        return Double.doubleToLongBits(num);
    }

    // long to char bits array 64λ0��1�ַ���
    public static char[] LongToCharBits(long num) {

        char[] bits = new char[64];
        for (int i = 64 - 1; i >= 0; --i) {
            bits[i] = (num & 1) == 0 ? '0' : '1';
            num >>>= 1;
        }
        return bits;
    }

    // float to int
    public static long FloatToInt(float num) {

        return Float.floatToIntBits(num);
    }

    // int to char bits array 32λ��0��1�ַ���
    public static char[] IntToCharBits(int num) {

        char[] bits = new char[32];
        for (int i = 32 - 1; i >= 0; --i) {
            bits[i] = (num & 1) == 0 ? '0' : '1';
            num >>>= 1;
        }
        return bits;
    }

    // output bit char array
    public static void outputBits(char[] charBits) {

        for (char charBit : charBits) {
            System.out.print(charBit);
        }
        System.out.println();
    }

}
