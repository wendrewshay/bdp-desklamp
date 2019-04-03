package com.desklamp.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * 流的读取和写入操作
 * @author jw
 * @date 2019/3/22
 */
@Slf4j
public class IOUtils {

    /**
     * 自定义字节数组的大小
     */
    private static final int SIZE = 1024 * 8;

    /**
     * 以字节流的方式读取到字节数组，并转为字符串
     * @author jw
     * @date 2019/3/22 15:55
     * @param is 输入流
     * @param charsetName 字符集,为null则默认字符集
     * @return java.lang.String
     */
    public static String readBytesToString(InputStream is, String charsetName) throws Exception{
        return new String(readBytes(is), charsetName);
    }

    /**
     * 以字节流的方式读取到byte[]
     * @author jw
     * @date 2019/3/22 15:55
     * @param is 输入流
     * @return byte[]
     */
    public static byte[] readBytes(InputStream is) {
        byte[] bytes;
        int len;
        try(BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayOutputStream outWriter = new ByteArrayOutputStream()){
            byte[] cbuf = new byte[SIZE];
            while ((len = bis.read(cbuf)) != -1) {
                outWriter.write(cbuf, 0, len);
            }
            outWriter.flush();
            bytes = outWriter.toByteArray();
            return bytes;
        }catch (Exception e){
            log.error("转为byte[]失败-{}",e.getMessage(),e);
            return null;
        }
    }

    /**
     * 根据文件读取为char数组
     * @author jw
     * @date 2019/3/22 15:55
     * @param file 文件
     * @param charsetName 编码
     * @return char[]
     */
    public static char[] readChars(File file, String charsetName) throws IOException{
        return readChars(new FileInputStream(file), charsetName);
    }

    /**
     * 以字符流的方式读取到char数组
     * @author jw
     * @date 2019/3/22 15:54
     * @param is 输入流
     * @param charsetName 编码
     * @return char[]
     */
    public static char[] readChars(InputStream is, String charsetName) {
        char[] chars;
        InputStreamReader isr;
        int len;
        try{
            if (charsetName == null) {
                isr = new InputStreamReader(is);
            } else {
                isr = new InputStreamReader(is, charsetName);
            }
            BufferedReader br = new BufferedReader(isr);
            char[] cbuf = new char[SIZE];
            CharArrayWriter outWriter = new CharArrayWriter();
            while ((len = br.read(cbuf)) != -1) {
                outWriter.write(cbuf, 0, len);
            }
            outWriter.flush();
            chars = outWriter.toCharArray();
            isr.close();
            br.close();
            outWriter.close();
            return chars;
        }catch (Exception e){
            log.error("转换为char数组失败-{}",e.getMessage(),e);
            return null;
        }
    }


    public static void main(String[] args) {
//        writeString(new File("D:\\test.txt"),"ssss");
    }

    /**
     * 将字符串以默认编码写入文件(默认覆盖)
     * @author jw
     * @date 2019/3/22 15:12
     * @param file 文件
     * @param text 字符串
     */
    public static void writeString(File file, String text) throws IOException{
        writeString(file, false, text, 0, text.length(), null);
    }

    /**
     * 将字符串写入文件(根据append判断是否追加还是覆盖)
     * @author jw
     * @date 2019/3/22 15:17
     * @param file 文件
     * @param append 是否追加,true为追加 false为覆盖
     * @param text 字符串
     * @param charsetName 编码名称
     */
    public static void writeString(File file, boolean append, String text, String charsetName) throws IOException{
        writeString(file, append, text, 0, text.length(), charsetName);
    }

    /**
     * 将字符串写入文件(上面两个重载方法的调用)
     * @author jw
     * @date 2019/3/22 15:12
     * @param file 文件
     * @param append 是否追加
     * @param text 字符串
     * @param off 起始下标
     * @param length 长度
     * @param charsetName 编码名称
     */
    public static void writeString(File file, boolean append, String text, int off, int length, String charsetName)
            throws IOException{
        writeString(new FileOutputStream(file, append), text, off, length, charsetName);
    }

    /**
     * 字符输出流输出字符串
     * @author jw
     * @date 2019/3/22 15:14
     * @param os 输出流
     * @param text 字符串
     * @param off 起始下标
     * @param length 长度
     * @param charsetName 编码
     */
    public static void writeString(OutputStream os, String text, int off, int length, String charsetName) throws IOException{
        OutputStreamWriter osw;
        BufferedWriter bw;
        try{
            if (charsetName == null) {
                osw = new OutputStreamWriter(os);
            } else {
                osw = new OutputStreamWriter(os, charsetName);
            }
            bw = new BufferedWriter(osw);
            bw.write(text, off, length);
            os.close();
            osw.close();
            bw.close();
        }catch (Exception e){
            log.error("写入文件失败-{}",e.getMessage(),e);
        }
    }

}
