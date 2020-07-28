package access.main;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: Malakh
 * @Date: 19-6-29
 * @Description: Socket 通信的tcp服务端
 */
public class ObjectTcpServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectTcpServer.class);

    /**
     * 与请求主体连接的默认端口号，
     * 可以在启动时指定:
     * java -jar jar包名字 端口号
     */
    private static Integer serverPort = 9090;

    public static void main(String[] args) {
        try {
            if (args.length > 0) {
                String portStr = args[0];
                if (StringUtils.isNotEmpty(portStr)
                        && !StringUtils.equalsIgnoreCase("null", portStr)
                        && portStr.matches("^[0-9]*$"))
                    serverPort = Integer.valueOf(portStr);
            }

            LOGGER.info("[TCP server listen port:{}]", serverPort);
            // 服务端开启一个ServerSocket即可
            ServerSocket server = new ServerSocket(serverPort);
            boolean flag = true;
            while (flag) {
                // 为每一个客户端建立一个socket
                Socket listen = server.accept();
                // 开启线程
                new ObjectTcpThread(listen).start();
            }

            server.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
