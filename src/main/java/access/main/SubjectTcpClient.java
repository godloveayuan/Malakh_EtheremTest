package access.main;

import bean.DeviceInfo;
import bean.RequestMessage;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import myUtils.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.DeviceInfoService;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @Author: Malakh
 * @Date: 19-6-29
 * @Description: Socket 通信的TCP 客户端,用于作为访问主体发送访问请求
 */
public class SubjectTcpClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectTcpClient.class);
    private static final Integer defaultPort = 9090;        // 客体默认监听的端口号
    private static DeviceInfo selfDeviceInfo = null;        // 本设备信息
    private static String subjectUUID;
    private static String subjectMac;

    // 命令
    public static final String CMD_HELP = "help";
    public static final String CMD_SELF = "self";
    public static final String CMD_EXIT = "exit";
    public static final String CMD_DEVICES = "devices";

    public static void main(String[] args) {
        try {
            // 配置文件里的本设备的uuid和mac
            subjectUUID = PropertyUtils.getSubjectUUID();
            subjectMac = PropertyUtils.getSubjectMac();
            // 根据uuid获取到主体的设备信息
            selfDeviceInfo = DeviceInfoService.queryByUid(subjectUUID);

            Scanner scanner = new Scanner(System.in);
            String cinLine;
            printMessage();
            System.out.printf(">>");
            while (!StringUtils.equalsIgnoreCase(CMD_EXIT, cinLine = scanner.nextLine())) {
                if (StringUtils.isEmpty(cinLine)) {
                    LOGGER.info("请输入命令");
                } else if (StringUtils.equalsIgnoreCase(CMD_HELP, cinLine)) {
                    executeCmdHelp();
                } else if (StringUtils.equalsIgnoreCase(CMD_SELF, cinLine)) {
                    executeCmdSelf();
                } else if (StringUtils.equalsIgnoreCase(CMD_DEVICES, cinLine)) {
                    executeCmdDevices();
                } else {
                    executeCmdAccess(cinLine);
                }
                System.out.println("\n\n\n");
                System.out.printf(">>");
            }
        } catch (Exception e) {
            LOGGER.error("[Error] Catch exception.", e);
            return;
        }
    }

    /**
     * 执行help命令，显示帮助信息
     */
    public static void executeCmdHelp() {
        LOGGER.info("[操作格式]：");
        LOGGER.info("读取设备信息: read e3f2d408-f45e-4b7f-911d-6243c0e80318");
        LOGGER.info("控制设备状态: control e3f2d408-f45e-4b7f-911d-6243c0e80318 on");
    }

    /**
     * 执行self命令，显示本设备信息
     */
    public static void executeCmdSelf() {
        if (selfDeviceInfo == null) {
            LOGGER.info("未获取到本设备信息");
            return;
        }
        LOGGER.info("[本设备信息]:");
        LOGGER.info("设备名称:{}", selfDeviceInfo.getDeviceName());
        LOGGER.info("设备UUID:{}", selfDeviceInfo.getDeviceUid());
        LOGGER.info("设备MAC:{}", subjectMac);    // 使用配置文件里写的mac
        LOGGER.info("设备类型:{}", selfDeviceInfo.getDeviceType());
        LOGGER.info("位置属性:{}", selfDeviceInfo.getAttributePosition());
        LOGGER.info("类型属性:{}", selfDeviceInfo.getAttributeType());
        LOGGER.info("功能属性:{}", selfDeviceInfo.getAttributeSystem());
    }

    /**
     * 执行 devices命令，显示终端设备的uuid
     */
    public static void executeCmdDevices() {
        List<DeviceInfo> deviceInfoList = DeviceInfoService.queryAllTerminal();
        if (CollectionUtils.isEmpty(deviceInfoList)) {
            LOGGER.info("未找到终端设备");
        } else {
            for (DeviceInfo deviceInfo : deviceInfoList) {
                LOGGER.info("{} {}", deviceInfo.getDeviceName(), deviceInfo.getDeviceUid());
            }
        }
    }

    /**
     * 执行访问命令，访问控制终端设备
     *
     * @param cinLine
     */
    public static void executeCmdAccess(String cinLine) {
        if (selfDeviceInfo == null) {
            LOGGER.info("未获取到本设备相关信息，无法访问其他设备");
            return;
        }

        // 解析输入的命令，获取操作类型、客体uuid、操作数据
        Map<String, String> cmdMap = readCmdLine(cinLine);
        if (cmdMap == null) {
            LOGGER.info("请重新输入命令");
            return;
        }
        String operateType = cmdMap.get("operateType");
        String objectUUID = cmdMap.get("objectUUID");
        String operateData = cmdMap.get("operateData");

        // 获取客体设备的代理地址
        DeviceInfo objectDevice = DeviceInfoService.queryByUid(objectUUID);
        if (objectDevice == null) {
            LOGGER.info("未找到要访问的设备信息");
            return;
        }
        Map<String, String> hostAndPortMap = MyStringUtils.getHostAndPortFromAddress(objectDevice.getAgentAddress());
        if (hostAndPortMap == null) {
            LOGGER.info("未找到访问设备的地址");
            return;
        }
        String objHost = hostAndPortMap.get(MyStringUtils.HOST_KEY);
        String objPort = hostAndPortMap.get(MyStringUtils.PORT_KEY);
        if (StringUtils.isEmpty(objHost)) {
            LOGGER.info("未找到访问设备的地址");
        }
        objPort = MyStringUtils.checkIsNumber(objPort) ? objPort : defaultPort.toString();

        // 建立socket连接
        Socket sock = null;
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            sock = new Socket(InetAddress.getByName(objHost), Integer.valueOf(objPort));
            RequestMessage request = new RequestMessage();

            reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));

            //构建请求报文
            request.setSubject(selfDeviceInfo.getDeviceUid());
            request.setSubMac(subjectMac);  // 使用配置文件里的Mac
            request.setObject(objectDevice.getDeviceUid());
            request.setOperateType(operateType);
            request.setOperateData(operateData);
            String requestJson = JsonParseUtils.parseObjToJson(request);

            LOGGER.info("[SendMess]:\n" + requestJson);
            IOUtils.sendSocketString(writer, requestJson);

            String responseJson = IOUtils.receiveSocketString(reader);
            LOGGER.info("\n");
            LOGGER.info("[Received]:\n" + responseJson);

            // 断开连接：发送一个断开连接的报文，operateType=close
            request.setSubject(selfDeviceInfo.getDeviceUid());
            request.setSubject(subjectMac);
            request.setObject(objectDevice.getDeviceUid());
            request.setOperateType(StaticValues.closeOperateType);
            request.setOperateData(null);
            requestJson = JsonParseUtils.parseObjToJson(request);
            IOUtils.sendSocketString(writer, requestJson);
            IOUtils.receiveSocketString(reader);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeResult(writer, reader, sock);
        }
    }

    /**
     * 解析输入的命令
     *
     * @param cinLine
     * @return
     */
    public static Map<String, String> readCmdLine(String cinLine) {
        int cmdMinLength = 2;      // 命令最短长度
        if (StringUtils.isEmpty(cinLine)) {
            return null;
        }

        // 将输入命令按空白符(空格或制表符)切割
        cinLine = cinLine.trim();
        String[] cinArr = cinLine.split("\\s");
        List<String> cinList = Lists.newArrayList();
        for (String str : cinArr) {
            if (StringUtils.isNotBlank(str)) {
                cinList.add(str.trim());
            }
        }
        if (CollectionUtils.isEmpty(cinList) || cinList.size() < cmdMinLength) {
            LOGGER.info("输入命令格式错误，请重新输入!");
            return null;
        }
        String operateType = cinList.get(0);
        String objectUUID = cinList.get(1);
        String operateData = null;
        if (StringUtils.equalsIgnoreCase("control", operateType)) {
            if (cinList.size() < 3) {
                System.out.println("请输入操作状态");
                printMessage();
                return null;
            }
            operateData = cinList.get(2);
        }

        Map<String, String> cmdMap = Maps.newHashMap();
        cmdMap.put("operateType", operateType);
        cmdMap.put("objectUUID", objectUUID);
        cmdMap.put("operateData", operateData);

        return cmdMap;
    }

    /**
     * 关闭输入输出流与socket
     *
     * @param writer
     * @param reader
     * @param sock
     */
    public static void closeResult(BufferedWriter writer, BufferedReader reader, Socket sock) {
        try {
            if (writer != null) writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (sock != null) sock.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 打印提示输入信息
     */
    public static void printMessage() {
        LOGGER.info("--------------------------------------------");
        LOGGER.info(" [使用帮助]:                         ");
        LOGGER.info(" {}: 帮助                  ", CMD_HELP);
        LOGGER.info(" {}: 查看本设备信息          ", CMD_SELF);
        LOGGER.info(" {}: 查看终端设备的uuid      ", CMD_DEVICES);
        LOGGER.info(" {}: 退出                  ", CMD_EXIT);
        LOGGER.info("--------------------------------------------");
    }

}