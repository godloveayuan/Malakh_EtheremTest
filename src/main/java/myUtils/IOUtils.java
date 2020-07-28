package myUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * @Author: Malakh
 * @Date: 19-6-29
 * @Description: 向sockect 的输入输出流里读取数据或写入数据
 */
public class IOUtils {
    private static final String END_MARK = "##";  // 报文结束标记

    /**
     * 将字符串写入 BufferWriter
     *
     * @param sender
     * @param sendString
     */
    public static void sendSocketString(BufferedWriter sender, String sendString) {
        try {
            sendString = new String(sendString.getBytes(),"GBK");
            sender.write(sendString);    // 发送消息
            sender.newLine();            // 写入一个行分隔符
            sender.write(END_MARK);      // 消息结束标记
            sender.newLine();            // 写入一个行分隔符
            sender.flush();              // 刷新缓冲区
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * 从 BufferReader 读取字符串
     *
     * @param reader
     * @return
     */
    public static String receiveSocketString(BufferedReader reader) {

        String readLine = null;
        StringBuilder receivedStr = new StringBuilder();
        try {
            // 如果没有遇到报文结束标志，则继续读取内容
            while (true) {
                readLine = reader.readLine();
                if (readLine == null || END_MARK.equalsIgnoreCase(readLine) || readLine.length() == 0) {
                    break;
                } else {
                    receivedStr.append(readLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return receivedStr.toString();
    }

}
