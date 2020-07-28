package myUtils;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author: Malakh
 * @Date: 19-6-24
 * @Description: 发送http请求到精准广告推荐接口
 */
public class HttpUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);
    private static Gson gson = new Gson();

    /**
     * 根据 baseUrl 和请求参数map 发送 http get 请求
     *
     * @param baseUrl
     * @param getParamMap
     * @return
     */
    public static String httpGet(String baseUrl, Map<String, Object> getParamMap) {
        String fullUrl = createGetParamUrl(baseUrl, getParamMap);
//        LOGGER.info("=== fullUrl:{}", fullUrl);
        if (StringUtils.isEmpty(fullUrl)) {
            LOGGER.error("[httpGet] url is empty.");
            return null;
        }

        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(fullUrl);
            client = HttpClients.createDefault();
            response = client.execute(httpGet);

            // 从响应体里读取响应内容
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            return result;
        } catch (Exception e) {
            LOGGER.error("[httpGet] exception.", e);
            return null;
        } finally {
            close(client, response);
        }
    }

    /**
     * 根据 baseUrl 和 请求参数map 发送 http post 请求, 请求参数按表单格式： key=value
     *
     * @param baseUrl
     * @param paramMap
     * @return
     */
    public static String httpPost(String baseUrl, Map<String, Object> paramMap) {
        if (StringUtils.isEmpty(baseUrl)) {
            LOGGER.error("[httpPost] url is empty.");
            return null;
        }
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;

        try {
            HttpPost httpPost = new HttpPost(baseUrl);

            // 将每对参数转换成键值对  String=String
            List<BasicNameValuePair> paramPairList = Lists.newArrayList();
            if (paramMap != null && paramMap.size() > 0) {
                for (String paramKey : paramMap.keySet()) {
                    Object paramValue = paramMap.get(paramKey);
                    String paramValueStr = trimBoth(parseObjToJson(paramValue));      // 转换成json字符串
                    if (StringUtils.isNotEmpty(paramKey) && StringUtils.isNotEmpty(paramValueStr)) {
                        BasicNameValuePair paramPair = new BasicNameValuePair(paramKey, paramValueStr);
                        paramPairList.add(paramPair);
                    }
                }
            }

            // 请求参数转换成 UrlEncodeFormEntity
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(paramPairList, "UTF-8");
            httpPost.setEntity(urlEncodedFormEntity);

            client = HttpClients.createDefault();
            response = client.execute(httpPost);

            // 从响应体里读取响应内容
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);

            return result;
        } catch (Exception e) {
            LOGGER.error("[httpPost] exception.", e);
            return null;
        } finally {
            close(client, response);
        }
    }

    /**
     * 根据 baseUrl 和 请求参数map 发送 http post 请求, 请求参数按json格式： Content-Type = application/json
     *
     * @param baseUrl
     * @param obj
     * @return
     */
    public static String httpPostJson(String baseUrl, Object obj) {
        if (StringUtils.isEmpty(baseUrl)) {
            LOGGER.error("[httpPost] url is empty.");
            return null;
        }
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;

        try {
            HttpPost httpPost = new HttpPost(baseUrl);
            // 设置请求格式为 application/json
            httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");

            // 请求参数转换成 json, 放入请求体里
            StringEntity stringEntity = new StringEntity(parseObjToJson(obj));
            httpPost.setEntity(stringEntity);

            client = HttpClients.createDefault();
            response = client.execute(httpPost);

            // 从响应体里读取响应内容
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);

            return result;
        } catch (Exception e) {
            LOGGER.error("[httpPostJson] exception.", e);
            return null;
        } finally {
            close(client, response);
        }
    }

    /**
     * 根据 baseUrl 和 请求参数，生成完整的get请求的url
     *
     * @param paramsMap
     * @return
     */
    private static String createGetParamUrl(String baseUrl, Map<String, Object> paramsMap) {
        // 添加参数
        try {
            if (StringUtils.isEmpty(baseUrl)) {
                return null;
            }
            if (paramsMap == null || paramsMap.size() <= 0) {
                return baseUrl;
            }

            StringBuilder sb = new StringBuilder();
            for (String key : paramsMap.keySet()) {
                sb.append(key).append("=");
                Object value = paramsMap.get(key);
                if (value != null) {
                    // 将参数值使用 URL encode
                    sb.append(trimBoth(parseObjToJson(value)));
                }
                sb.append("&");
            }
            String paramUrl = sb.toString();
            if (StringUtils.isNotEmpty(paramUrl) && paramUrl.endsWith("&")) {
                paramUrl = paramUrl.substring(0, paramUrl.length() - 1);
            }

            if (StringUtils.isNotEmpty(baseUrl) && !baseUrl.endsWith("?")) {
                baseUrl = baseUrl + "?";
            }
            return baseUrl + paramUrl;
        } catch (Exception e) {
            LOGGER.error("[createGetParamUrl] exception");
            return baseUrl;
        }
    }

    /**
     * 关闭资源
     *
     * @param client
     * @param response
     */
    public static void close(CloseableHttpClient client, CloseableHttpResponse response) {
        try {
            if (response != null) {
                response.close();
            }
        } catch (IOException e) {
            response = null;
            LOGGER.error("close response exception.", e);
        } finally {
            try {
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                client = null;
                LOGGER.error("close client exception.", e);
            }
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
            return null;
        }
    }

    /**
     * 去掉参数两边的引号
     *
     * @param param
     * @return
     */
    public static String trimBoth(String param) {
        if (StringUtils.isEmpty(param)) {
            return null;
        }

        if (param.startsWith("\"")) {
            param = param.substring(1, param.length());
        }
        if (param.endsWith("\"")) {
            param = param.substring(0, param.length() - 1);
        }

        return param;
    }

}
