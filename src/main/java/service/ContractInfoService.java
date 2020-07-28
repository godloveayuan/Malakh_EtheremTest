package service;

import bean.ContractInfo;
import com.google.common.collect.Lists;
import dao.ContractInfoDao;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author: Malakh
 * @Date: 2020/2/24
 * @Description:
 */
public class ContractInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContractInfoService.class);

    /**
     * 根据uid查询关联的策略合约
     *
     * @param deviceUid
     * @return
     */
    public static List<ContractInfo> queryContractsByUid(String deviceUid) {
        List<ContractInfo> resultContracts = Lists.newArrayList();

        List<ContractInfo> publicContracts = ContractInfoDao.selectPublicContract();
        if (CollectionUtils.isNotEmpty(publicContracts)) {
            resultContracts.addAll(publicContracts);
        }

        List<ContractInfo> privateContracts = ContractInfoDao.selectPrivateContract(deviceUid);
        if (CollectionUtils.isNotEmpty(privateContracts)) {
            resultContracts.addAll(privateContracts);
        }

        if (CollectionUtils.isEmpty(resultContracts)) {
            resultContracts = null;
        }

        return resultContracts;
    }

}
