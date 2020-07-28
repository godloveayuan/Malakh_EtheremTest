package access.contract;

import access.contract.CheckContract;
import access.contract.PrivateCheckContract;
import myUtils.Web3JClient;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.Contract;

/**
 * @Author: Malakh
 * @Date: 19-6-29
 * @Description: 调用合约的方法
 */
public class ContractService {
    private static Web3j web3j = Web3JClient.getClient();

    /**
     * 调用公共策略合约的判断方法
     *
     * @param contractAddress
     * @param subAttribute
     * @param objAttribute
     * @return
     */
    public static Boolean publicCheck(String contractAddress, String subAttribute, String objAttribute) {
        try {
            // 获取第一个账户
            String minerBaseAccount = web3j.ethAccounts().send().getAccounts().get(0);

            Credentials credentials = Credentials.create(minerBaseAccount);

            // 合约对象
            CheckContract checkContract = CheckContract.load(contractAddress, web3j, credentials, Contract.GAS_PRICE, Contract.GAS_PRICE);
            return checkContract.check(subAttribute, objAttribute).send();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 调用专属策略合约的判断方法
     *
     * @param contractAddress
     * @param subAttribute
     * @param operate
     * @return
     */
    public static Boolean privateCheck(String contractAddress, String subAttribute, String operate) {
        try {
            // 获取第一个账户
            String minerBaseAccount = web3j.ethAccounts().send().getAccounts().get(0);

            Credentials credentials = Credentials.create(minerBaseAccount);

            // 合约对象
            PrivateCheckContract checkContract = PrivateCheckContract.load(contractAddress, web3j, credentials, Contract.GAS_PRICE, Contract.GAS_PRICE);
            return checkContract.check(subAttribute, operate).send();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
