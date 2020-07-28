package service;

import bean.RequestMessage;
import bean.ResponseMessage;
import bean.AccessResult;

/**
 * @Author: Malakh
 * @Date: 2020/2/26
 * @Description: 对象转换
 */
public class DataParseService {

    /**
     * 根据访问控制结果构建响应消息
     *
     * @param accessResult
     * @return
     */
    public static ResponseMessage buildResponseByAccessResult(RequestMessage requestMessage, AccessResult accessResult) {
        if (accessResult == null) {
            return null;
        }
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(accessResult.isAccessAllow() ? 0 : -1);
        responseMessage.setMessage(accessResult.getMessage());
        responseMessage.setData(accessResult.getResultData());
        responseMessage.setSubject(requestMessage.getSubject());
        responseMessage.setObject(requestMessage.getObject());

        return responseMessage;
    }
}
