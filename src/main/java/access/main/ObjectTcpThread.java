package access.main;

import bean.*;
import myUtils.IOUtils;
import myUtils.JsonParseUtils;
import myUtils.MyStringUtils;
import myUtils.StaticValues;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.AccessLogService;
import service.DataParseService;
import service.DeviceInfoService;
import service.PunishInfoService;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.List;

public class ObjectTcpThread extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectTcpThread.class);

    private Socket sock = null;

    public ObjectTcpThread(Socket sock) {
        this.sock = sock;
    }

    @Override
    public void run() {
        try {
            // 输入流：读取请求消息
            BufferedReader reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            // 输出流：写入响应消息
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));

            // 获取客户端的IP地址和发送端口
//            String clientIP = sock.getInetAddress().getHostAddress();
//            int clientPort = sock.getPort();

            AccessLog accessLog = null;
            RequestMessage requestMessage;
            ResponseMessage responseMessage;
            // 如果没有接收到是关闭消息的报文，则响应处理
            while ((requestMessage = readRequest(reader)) != null
                    && !StringUtils.equalsIgnoreCase(StaticValues.closeOperateType, requestMessage.getOperateType())) {
                // 1. 记录访问日志
                accessLog = addAccessLog(requestMessage);

                // 2. 查看是git add .否处于隔离状态
                String punishRuleName = checkIsInPunish(requestMessage.getSubject());
                if (StringUtils.isNotEmpty(punishRuleName)) {
                    LOGGER.info("Message: 设备处于惩罚状态. deviceUid:{}", requestMessage.getSubject());
                    //构建访问失败结果
                    AccessResult accessResult = AccessResult.buildFailed(FailReasonEnum.FAIL_REASON_FOUR.getId(), "设备惩罚中，违反" + punishRuleName);
                    //更新访问日志
                    updateAccessLog(accessResult, accessLog);
                    //根据访问结果构建响应报文
                    responseMessage = DataParseService.buildResponseByAccessResult(requestMessage, accessResult);
                    //将响应报文发送给访问主体
                    sendResponse(responseMessage, writer);
                    continue;
                }
                // 3. 身份认证
                if (!checkDevice(requestMessage.getSubject(), requestMessage.getSubMac())) {
                    LOGGER.info("Message: 设备身份认证不通过. deviceUid:{}", requestMessage.getSubject());
                    AccessResult accessResult = AccessResult.buildFailed(FailReasonEnum.FAIL_REASON_TWO.getId(), FailReasonEnum.FAIL_REASON_TWO.getMessage());
                    updateAccessLog(accessResult, accessLog);
                    responseMessage = DataParseService.buildResponseByAccessResult(requestMessage, accessResult);
                    sendResponse(responseMessage, writer);
                    continue;
                }
                // 4. 处理访问请求，返回响应消息
                AccessResult accessResult = AgentServer.accessExecute(requestMessage);
                updateAccessLog(accessResult, accessLog);
                responseMessage = DataParseService.buildResponseByAccessResult(requestMessage, accessResult);
                sendResponse(responseMessage, writer);
            }

            // 关闭资源
//            LOGGER.info("==== 关闭资源 ====");
            writer.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 从socket中读取请求信息
     *
     * @param reader
     * @return
     */
    public RequestMessage readRequest(BufferedReader reader) {
        LOGGER.info("\n");
        String requestJson = IOUtils.receiveSocketString(reader);
        RequestMessage requestMessage = JsonParseUtils.parseToRequestMessage(requestJson);
        if (!StringUtils.equalsIgnoreCase(StaticValues.closeOperateType, requestMessage.getOperateType())) {
            LOGGER.info("[Received]:\n{}", requestJson);
        }
        LOGGER.info("");  // 打印空行
        return requestMessage;
    }

    /**
     * 将响应信息发送给socket
     *
     * @param responseMessage
     * @param writer
     */
    public void sendResponse(ResponseMessage responseMessage, BufferedWriter writer) {
        LOGGER.info("");  // 打印空行
        LOGGER.info("[SendMess]:\n{}", responseMessage);
        String responseJson = JsonParseUtils.parseObjToJson(responseMessage);
        IOUtils.sendSocketString(writer, responseJson);
    }

    /**
     * 请求到达时添加访问日志
     *
     * @param requestMessage
     * @return
     */
    public AccessLog addAccessLog(RequestMessage requestMessage) {
        if (requestMessage == null) {
            return null;
        }

        //第一次构造访问日志
        AccessLog accessLog = new AccessLog();
        accessLog.setSubjectUid(requestMessage.getSubject());
        accessLog.setObjectUid(requestMessage.getObject());
        accessLog.setOperateType(requestMessage.getOperateType());
        accessLog.setOperateData(requestMessage.getOperateData());
        accessLog.setRequestTimeStr(MyStringUtils.parseDateToStr(new Date()));

        return AccessLogService.addAccessLog(accessLog);
    }

    /**
     * 请求结束后更新访问日志，添加访问结果信息
     *
     * @param accessResult
     * @param accessLog
     * @return
     */
    public AccessLog updateAccessLog(AccessResult accessResult, AccessLog accessLog) {
        accessLog.setAccessAllow(accessResult.isAccessAllow());
        accessLog.setFailReason(accessResult.getFailReason());
        accessLog.setResultData(accessResult.getResultData());
        accessLog.setRequestTime(null);
        accessLog.setResponseTime(new Date());

        return AccessLogService.updateAccessLog(accessLog);
    }

    /**
     * 检查设备是否处于惩罚状态
     *
     * @param deviceUid
     * @return
     */
    public String checkIsInPunish(String deviceUid) {
        if (StringUtils.isEmpty(deviceUid)) {
            return null;
        }

//        List<PunishInfo> punishInfos = HttpRequestService.httpQueryPunishByUid(deviceUid);
        List<PunishInfo> punishInfos = PunishInfoService.queryPunishByUid(deviceUid);

        if (CollectionUtils.isNotEmpty(punishInfos)) {
            for (PunishInfo punish : punishInfos) {
                if (punish != null && punish.getStatus() == 1) {
                    return punish.getRuleName();
                }
            }
        }

        return null;
    }

    /**
     * 身份认证
     *
     * @param deviceUid
     * @param deviceMac
     * @return
     */
    public boolean checkDevice(String deviceUid, String deviceMac) {
        if (StringUtils.isEmpty(deviceUid) || StringUtils.isEmpty(deviceMac)) {
            return false;
        }

//        DeviceInfo deviceInfo = HttpRequestService.httpQueryDeviceByUid(deviceUid);
        DeviceInfo deviceInfo = DeviceInfoService.queryByUid(deviceUid);
        if (deviceInfo != null && StringUtils.equalsIgnoreCase(deviceMac, deviceInfo.getDeviceMac())) {
            return true;
        }
        return false;
    }

}
