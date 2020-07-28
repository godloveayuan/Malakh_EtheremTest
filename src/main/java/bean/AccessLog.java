package bean;

import java.util.Date;

/**
 * @Author: Malakh
 * @Date: 2020/2/18
 * @Description: 访问日志
 */
public class AccessLog {
    private Integer id;             // 主键id
    private String subjectUid;      // 请求主体
    private String objectUid;       // 请求客体
    private String operateType;     // 请求操作
    private String operateData;     // 操作数据
    private Boolean accessAllow;    // 是否允许访问
    private Integer failReason;     // 访问失败原因:1-频繁访问 2-身份认证不通过 3-越权访问
    private String resultData;      // 访问的结果数据
    private Date requestTime;       // 请求发送时间
    private String requestTimeStr;  // 请求时间
    private Date responseTime;      // 响应时间
    private String responseTimeStr; // 响应时间
    private Integer status;         // 日志是否可用作为规则判断依据，用于解除惩罚使用

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubjectUid() {
        return subjectUid;
    }

    public void setSubjectUid(String subjectUid) {
        this.subjectUid = subjectUid;
    }

    public String getObjectUid() {
        return objectUid;
    }

    public void setObjectUid(String objectUid) {
        this.objectUid = objectUid;
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

    public Boolean getAccessAllow() {
        return accessAllow;
    }

    public void setAccessAllow(Boolean accessAllow) {
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

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public String getRequestTimeStr() {
        return requestTimeStr;
    }

    public void setRequestTimeStr(String requestTimeStr) {
        this.requestTimeStr = requestTimeStr;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public String getResponseTimeStr() {
        return responseTimeStr;
    }

    public void setResponseTimeStr(String responseTimeStr) {
        this.responseTimeStr = responseTimeStr;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccessLog{");
        sb.append("id=").append(id);
        sb.append(", subjectUid='").append(subjectUid).append('\'');
        sb.append(", objectUid='").append(objectUid).append('\'');
        sb.append(", operateType='").append(operateType).append('\'');
        sb.append(", operateData='").append(operateData).append('\'');
        sb.append(", accessAllow=").append(accessAllow);
        sb.append(", failReason=").append(failReason);
        sb.append(", resultData='").append(resultData).append('\'');
        sb.append(", requestTime=").append(requestTime);
        sb.append(", requestTimeStr='").append(requestTimeStr).append('\'');
        sb.append(", responseTime=").append(responseTime);
        sb.append(", responseTimeStr='").append(responseTimeStr).append('\'');
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
