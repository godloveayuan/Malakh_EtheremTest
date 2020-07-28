package access.main;

import access.contract.ContractService;
import bean.*;
import com.google.common.base.Preconditions;
import myUtils.DeviceOperateUtils;
import myUtils.UdpUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ContractInfoService;
import service.DeviceInfoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Malakh
 * @Date: 19-6-29
 * @Description: 代理设备接收请求进行访问权限验证，访问资源
 */
public class AgentServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(AgentServer.class);
    private static final boolean isUseContract = true;        // 是否使用智能合约
    private static final boolean isAccessDevice = false;       // 是否访问设备
    private static Map<String, String> deviceStatusMap = new HashMap<>();    // 保存设备状态的Map，模拟访问设备时使用

    /**
     * 代理根据接收到的根据主体标识、客体标识、操作类型、操作数据进行访问权限验证与资源访问
     *
     * @return
     */
    public static AccessResult accessExecute(RequestMessage requestMessage) {
        try {
            if (requestMessage == null) {
                return null;
            }

            /**
             * 获取主体客体设备信息
             */
            DeviceInfo subjectDevice = DeviceInfoService.queryByUid(requestMessage.getSubject());
            DeviceInfo objectDevice = DeviceInfoService.queryByUid(requestMessage.getObject());
            if (subjectDevice == null) {
                LOGGER.info("Error: 未找到访问主体设备信息！");
                return AccessResult.buildFailed(-1, "未找到访问主体设备信息");
            }
            if (objectDevice == null) {
                LOGGER.info("Error: 未找到访问客体设备信息！");
                return AccessResult.buildFailed(-1, "未找到访问客体设备信息");
            }
//            LOGGER.info("SubjectInfo: {}", JsonParseUtils.parseObjToJson(subjectDevice));
//            LOGGER.info("ObjectInfo : {}", JsonParseUtils.parseObjToJson(objectDevice));

            /**
             * 调用策略合约进行权限裁决
             */
            boolean checkResult = accessCheck(subjectDevice, objectDevice, requestMessage.getOperateType());
            if (!checkResult) {
                LOGGER.info("Message: 访问权限裁决未通过!");
                AccessResult accessResult = AccessResult.buildFailed(FailReasonEnum.FAIL_REASON_THREE.getId(), FailReasonEnum.FAIL_REASON_THREE.getMessage());
                return accessResult;
            }

            /**
             * 访问设备
             */
            AccessResult accessResult = accessDevice(objectDevice, requestMessage.getOperateType(), requestMessage.getOperateData());
//            LOGGER.info("====[accessExecute] accessResult:{}", accessResult);
            return accessResult;
        } catch (IllegalArgumentException e) {
            return AccessResult.buildFailed(-1, e.getMessage());
        }
    }

    /**
     * 访问控制设备,根据设备mac访问
     */
    public static AccessResult accessDevice(DeviceInfo objectDevice, String operateType, String operateData) {
        String objMac = objectDevice.getDeviceMac();

        AccessResult accessResult = AccessResult.buildSuccess();
        String receivedMessage;
        if (StringUtils.equalsIgnoreCase("read", operateType)) {
            // 构建搜索设备的命令
            String searchAllCmd = DeviceOperateUtils.getSearchAllCmd();
            if (isAccessDevice) {
                receivedMessage = UdpUtils.sendUdpMessage(UdpUtils.gateWayIP, UdpUtils.gateWayPort, searchAllCmd);
            } else {
                // 接收到的消息格式: 0BB switch 18-fe-34-a4-8c-2b 1
                receivedMessage = "0BB switch " + objectDevice.getDeviceMac() + " " + getDeviceStatus(objMac);            }
            LOGGER.info("ReadDevice: {}", receivedMessage);

            Boolean openFlag = DeviceOperateUtils.getDeviceStatus(receivedMessage, objMac);
            accessResult.setResultData((openFlag != null && openFlag) ? "on" : "off");
        } else if (StringUtils.equalsIgnoreCase("control", operateType)) {
            if (StringUtils.equalsIgnoreCase("on", operateData)) {
                operateData = DeviceOperateUtils.turnOn;
            } else if (StringUtils.equalsIgnoreCase("off", operateData)) {
                operateData = DeviceOperateUtils.turnOff;
            } else {
                accessResult = AccessResult.buildFailed(-1, "操作数据非法");
                return accessResult;
            }

            // 构建控制设备的命令
            String controlCMD = DeviceOperateUtils.getControlLightCmd(objMac, operateData);
            if (isAccessDevice) {
                receivedMessage = UdpUtils.sendUdpMessage(UdpUtils.gateWayIP, UdpUtils.gateWayPort, controlCMD);
            } else {
                // 接收到的消息格式: 0BB switch 18-fe-34-a4-8c-2b 1
                receivedMessage = "0BB switch " + objectDevice.getDeviceMac() + " " + operateData;
                setDeviceStatus(objMac, operateData);
            }

            LOGGER.info("ControlDevice: {}", receivedMessage);
            Boolean openFlag = DeviceOperateUtils.getDeviceStatus(receivedMessage, objMac);
            accessResult.setResultData((openFlag != null && openFlag) ? "on" : "off");
            accessResult.setMessage("操作成功");
        } else {
            accessResult = AccessResult.buildFailed(-1, "操作类型非法");
        }

        return accessResult;
    }

    /**
     * 根据主体设备标识、客体设备标识、访问操作，验证访问权限
     *
     * @return
     */
    public static boolean accessCheck(DeviceInfo subjectDevice, DeviceInfo objectDevice, String operateType) {
        // 获取策略合约地址
        List<ContractInfo> contractInfos = ContractInfoService.queryContractsByUid(objectDevice.getDeviceUid());
        Preconditions.checkArgument(CollectionUtils.isNotEmpty(contractInfos), "未找到客体相关的策略合约");

        //找到公共策略合约和专属策略合约
        String publicContractAddress = null;
        String privateContractAddress = null;
        for (ContractInfo contractInfo : contractInfos) {
            if (contractInfo == null) {
                continue;
            }
            String contractType = contractInfo.getType();
            String contractAddress = contractInfo.getAddress();
            if (StringUtils.isNotEmpty(contractType) && StringUtils.equalsIgnoreCase("public", contractType)) {
                publicContractAddress = contractAddress;
            }
            if (StringUtils.isNotEmpty(contractType) && StringUtils.equalsIgnoreCase("private", contractType)) {
                privateContractAddress = contractAddress;
            }
        }
        Preconditions.checkArgument(StringUtils.isNotEmpty(publicContractAddress), "未找到公共策略合约");
        LOGGER.info("PublicContract: {}", publicContractAddress);
        if (StringUtils.isNotEmpty(privateContractAddress)) {
            LOGGER.info("PrivateContract: {}", privateContractAddress);
        }

        // 公共合约裁决
        // 1. 位置属性裁决
        String subLocation = subjectDevice.getAttributePosition();
        String objLocation = objectDevice.getAttributePosition();
        if (subLocation == null || objLocation == null) {
            System.out.println("Message: Can't find subject location attribute");
            return false;
        }
        // 调用合约
        Boolean locationCheck;
        if (isUseContract) {
            locationCheck = ContractService.publicCheck(publicContractAddress, subLocation, objLocation);
        } else {
            locationCheck = StringUtils.equalsIgnoreCase(subLocation, objLocation);
        }

        if (!locationCheck) {
            LOGGER.info("Message: Subject can't access object because of position attribute");
            LOGGER.info("Message: Subject position:{} ", subLocation);
            LOGGER.info("Message: Object  position:{} ", objLocation);
            return false;
        }

        // 2. 设备系统属性裁决
        String subSystem = subjectDevice.getAttributeSystem();
        String objSystem = objectDevice.getAttributeSystem();
        if (StringUtils.isEmpty(subSystem) || StringUtils.isEmpty(objSystem)) {
            LOGGER.info("Message: Can't find subject system attribute");
            return false;
        }
        // 调用合约
        Boolean systemCheck;
        if (isUseContract) {
            systemCheck = ContractService.publicCheck(publicContractAddress, subSystem, objSystem);
        } else {
            systemCheck = StringUtils.equalsIgnoreCase(subSystem, objSystem);
        }
        if (!systemCheck) {
            LOGGER.info("Message: Subject can't access object because of system attribute");
            LOGGER.info("Message: Subject system:{}", subSystem);
            LOGGER.info("Message: Object  system:{}", objSystem);
            return false;
        }

        // 专属合约裁决
        if (StringUtils.isNotEmpty(privateContractAddress)) {
            // 2.设备类型属性裁决
            String subType = subjectDevice.getAttributeType();
            if (StringUtils.isEmpty(subType)) {
                LOGGER.info("Message: Can't find subject type attribute");
                return false;
            }
            if (StringUtils.isEmpty(operateType)) {
                LOGGER.info("Message: Subject operate is null");
                return false;
            }

            // 读取操作
            if (StringUtils.equalsIgnoreCase("read", operateType)) {
                Boolean readCheck;
                if (isUseContract) {
                    readCheck = ContractService.privateCheck(privateContractAddress, subType, operateType);
                } else {
                    readCheck = (StringUtils.equalsIgnoreCase("displayer", subType) || StringUtils.equalsIgnoreCase("controller", subType));
                }
                if (!readCheck) {
                    LOGGER.info("Message: Only display device or control device can read this object device");
                    LOGGER.info("Message: Subject type:{} ", subType);
                    return false;
                }
            } else if (StringUtils.equalsIgnoreCase("control", operateType)) {
                // 控制操作
                Boolean controlCheck;
                if (isUseContract) {
                    controlCheck = ContractService.privateCheck(privateContractAddress, subType, operateType);
                } else {
                    controlCheck = StringUtils.equalsIgnoreCase("controller", subType);
                }
                if (!controlCheck) {
                    LOGGER.info("Message: Only control device can control this object device");
                    LOGGER.info("Message: Subject type is " + subType);
                    return false;
                }
            }
        }

        return true;
    }
    /**
     * 记录设备状态
     * @param deviceMac
     * @param status
     */
    public static void setDeviceStatus(String deviceMac, String status) {
        if (deviceStatusMap == null) {
            deviceStatusMap = new HashMap<>();
        }
        deviceStatusMap.put(deviceMac, status);
    }

    /**
     * 获取设备状态
     * @param deviceMac
     * @return
     */
    public static String getDeviceStatus(String deviceMac) {
        String deviceStatus = "0";
        if (deviceStatusMap != null && deviceStatusMap.get(deviceMac) != null) {
            deviceStatus = deviceStatusMap.get(deviceMac);
        }

        return deviceStatus;
    }
}
