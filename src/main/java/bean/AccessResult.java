package bean;

/**
 * @Author: Malakh
 * @Date: 2020/2/26
 * @Description: 访问控制结果
 */
public class AccessResult {
    private boolean accessAllow;    // 是否允许访问
    private String message;         // 消息
    private Integer failReason;     // 失败原因 1- 访问频率过高 2- 身份认证不通过 3-访问权限不通过 4-设备惩罚中 5-未失败
    private String resultData;      // 访问数据

    public static AccessResult buildSuccess() {
        return buildSuccess(null);
    }

    public static AccessResult buildSuccess(String resultData) {
        AccessResult accessResult = new AccessResult();
        accessResult.setAccessAllow(true);
        accessResult.setFailReason(5);
        accessResult.setMessage("成功");
        accessResult.setResultData(resultData);

        return accessResult;
    }

    public static AccessResult buildFailed() {
        return buildFailed(null, null);
    }

    public static AccessResult buildFailed(Integer failReason, String message) {
        AccessResult accessResult = new AccessResult();
        accessResult.setAccessAllow(false);
        accessResult.setFailReason(failReason);
        accessResult.setMessage(message);

        return accessResult;
    }

    public boolean isAccessAllow() {
        return accessAllow;
    }

    public void setAccessAllow(boolean accessAllow) {
        this.accessAllow = accessAllow;
    }

    public Integer getFailReason() {
        return failReason;
    }

    public void setFailReason(Integer failReason) {
        this.failReason = failReason;
    }

    public String getResultData() {
        return resultData;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccessResult{");
        sb.append("accessAllow=").append(accessAllow);
        sb.append(", failReason=").append(failReason);
        sb.append(", resultData='").append(resultData).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
