package com.desklamp.common.utils;

/**
 * 字节数组与16进制字符串转换工具类
 * @author jw
 * @date 2019/3/22
 */
public class ByteToStringUtils {

    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 字节数组转为hex string
     * @author jw
     * @date 2019/3/22 15:35
     * @param bytes:字节数组
     * @return java.lang.String
     */
    public static String bytesToHex1(byte[] bytes) {
        // 一个byte为8位，可用两个十六进制位标识
        char[] buf = new char[bytes.length * 2];
        int a;
        int index = 0;
        // 使用除与取余进行转换
        for(byte b : bytes) {
            if(b < 0) {
                a = 256 + b;
            } else {
                a = b;
            }
            buf[index++] = HEX_CHAR[a / 16];
            buf[index++] = HEX_CHAR[a % 16];
        }
        return new String(buf);
    }

    /**
     * 将16进制字符串转换为byte[]
     * @author jw
     * @date 2019/3/22 15:35
     * @param str:字符串
     * @return byte[]
     */
    public static byte[] toBytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }
        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }
        return bytes;
    }


}
