package service;

import bean.SecurityRule;
import dao.SecurityRuleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author: Malakh
 * @Date: 2020/2/24
 * @Description:
 */
public class SecurityRuleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityRuleService.class);
    public static final int RULE_ACCESS_FREQUENCY = 1;          // 访问频率规则
    public static final int RULE_IDENTITY_FREQUENCY = 2;        // 身份认证频率规则
    public static final int RULE_AUTHORITY_FREQUENCY = 3;       // 越权访问频率规则

    public static List<SecurityRule> queryByRuleType(Integer ruleType) {
        if (ruleType == null) {
            return null;
        }
        List<SecurityRule> securityRules = SecurityRuleDao.selectByRuleType(ruleType);
        return securityRules;
    }

}
