package bean;


/**
 * @Author: Malakh
 * @Date: 19-6-29
 * @Description: 客体返回给主体的响应消息
 */
public class ResponseMessage {
    private String subject;     // 请求主体
    private String object;      // 请求客体
    private Integer status;     // 操作状态
    private String data;        // 响应信息
    private String message;     // 操作信息

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResponseMessage{");
        sb.append("subject='").append(subject).append('\'');
        sb.append(", object='").append(object).append('\'');
        sb.append(", status=").append(status);
        sb.append(", message='").append(message).append('\'');
        sb.append(", data='").append(data).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
