package myUtils;

import bean.RequestMessage;
import bean.ResponseMessage;
import bean.ContractInfo;
import bean.DeviceInfo;
import bean.HomePage;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author: Malakh
 * @Date: 19-6-29
 * @Description:
 */
public class JsonParseUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonParseUtils.class);

    private static Gson gson = new Gson();

    /**
     * 请求消息json字符串转换成对象
     *
     * @param jsonStr
     * @return
     */
    public static RequestMessage parseToRequestMessage(String jsonStr) {
        if (StringUtils.isBlank(jsonStr) || StringUtils.equalsIgnoreCase("null", jsonStr)) {
            return null;
        }
        try {
            RequestMessage request = gson.fromJson(jsonStr, new TypeToken<RequestMessage>() {
            }.getType());

            return request;
        } catch (Exception e) {
            LOGGER.error("[parseToRequestMessage] exception", e);
            return null;
        }
    }

    /**
     * 从 homepage 返回的json里获取 DeviceInfo
     *
     * @param jsonStr
     * @return
     */
    public static Object getObjectFromHomePage(String jsonStr, Class objClass) {
        try {
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            Integer status = jsonObject.getInteger("status");
            if (status != 0) {
                return null;
            }

            String data = jsonObject.getString("data");
            if (StringUtils.isEmpty(data)
                    || StringUtils.equalsIgnoreCase("null", data)
                    || StringUtils.equalsIgnoreCase("[]", data)) {
                return null;
            }

            return JSON.parseObject(data, objClass);
        } catch (Exception e) {
            LOGGER.error("[getObjectFromHomePage] exception", e);
            return null;
        }
    }

    /**
     * 从 homepage 返回的json里获取 DeviceInfo
     *
     * @param jsonStr
     * @return
     */
    public static <T> List<T> getListFromHomePage(String jsonStr, Class objClass) {
        try {
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            Integer status = jsonObject.getInteger("status");
            if (status != 0) {
                return null;
            }

            String data = jsonObject.getString("data");
            if (StringUtils.isEmpty(data)
                    || StringUtils.equalsIgnoreCase("null", data)
                    || StringUtils.equalsIgnoreCase("[]", data)) {
                return null;
            }

            List<T> listObj = JSONArray.parseArray(data, objClass);
            return listObj;
        } catch (Exception e) {
            LOGGER.error("[getListFromHomePage] exception", e);
            return null;
        }
    }

    /**
     * 将对象转换成json 串
     *
     * @param obj
     * @return
     */
    public static String parseObjToJson(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            String jsonStr = gson.toJson(obj);
            return jsonStr;
        } catch (Exception e) {
            System.out.println("[parseObjToJson] caught an exception. ");
            e.printStackTrace();
            return null;
        }
    }

}
