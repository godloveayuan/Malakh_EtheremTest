package bean;


/**
 * @Author: Malakh
 * @Date: 2020/2/26
 * @Description:
 */
public enum FailReasonEnum {
    FAIL_REASON_ONE(1, "访问频率过高"),
    FAIL_REASON_TWO(2, "身份认证不通过"),
    FAIL_REASON_THREE(3, "访问权限不通过"),
    FAIL_REASON_FOUR(4, "设备惩罚中"),
    NOT_FAIL(5, "成功"),
    ;

    private Integer id;
    private String message;

    FailReasonEnum(Integer id, String message) {
        this.id = id;
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
