package bean;

/**
 * @Author: Malakh
 * @Date: 2020/1/16
 * @Description: Controller 返回的数据格式
 */
public class HomePage<T> {
    private int status;
    private String message;
    private T data;

    public static HomePage buildAtFailed() {
        return buildAtFailed("失败", null);
    }

    public static HomePage buildAtFailed(String message) {

        return buildAtFailed(message, null);
    }

    public static HomePage buildAtFailed(String message, Object data) {
        HomePage homePage = new HomePage();
        homePage.setStatus(ResultStatus.FAILED.status);
        homePage.setMessage(message);
        homePage.setData(data);
        return homePage;
    }

    public static HomePage buildAtSuccess() {
        return buildAtSuccess("成功", null);
    }

    public static HomePage buildAtSuccess(String message) {

        return buildAtSuccess(message, null);
    }

    public static HomePage buildAtSuccess(String message, Object data) {
        HomePage homePage = new HomePage();
        homePage.setStatus(ResultStatus.SUCCEED.status);
        homePage.setMessage(message);
        homePage.setData(data);
        return homePage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HomePage{");
        sb.append("status=").append(status);
        sb.append(", message='").append(message).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }

    /**
     * 成功或失败码
     */
    public enum ResultStatus {
        SUCCEED(0),
        FAILED(-1),
        ;
        public Integer status;

        ResultStatus(Integer status) {
            this.status = status;
        }
    }
}
