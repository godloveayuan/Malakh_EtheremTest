package bean;

/**
 * @Author: Malakh
 * @Date: 19-6-29
 * @Description: 主体发送给客体的请求消息
 */
public class RequestMessage {
    private String subject;         // 主体标识
    private String subMac;          // 主体mac
    private String object;          // 客体标识
    private String operateType;     // 操作类型
    private String operateData;     // 操作内容

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubMac() {
        return subMac;
    }

    public void setSubMac(String subMac) {
        this.subMac = subMac;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getOperateData() {
        return operateData;
    }

    public void setOperateData(String operateData) {
        this.operateData = operateData;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RequestMessage{");
        sb.append("subject='").append(subject).append('\'');
        sb.append(", subMac='").append(subMac).append('\'');
        sb.append(", object='").append(object).append('\'');
        sb.append(", operateType='").append(operateType).append('\'');
        sb.append(", operateData='").append(operateData).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
