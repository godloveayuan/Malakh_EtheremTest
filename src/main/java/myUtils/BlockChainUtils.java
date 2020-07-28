package myUtils;

import org.apache.commons.collections.CollectionUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthGetBalance;

import java.math.BigInteger;
import java.util.List;

public class BlockChainUtils {
    private static Web3j web3j = Web3JClient.getClient();

    public static void main(String[] args) {
        showAllAccountBalance();
    }

    /**
     * 查询所有账户余额
     */
    public static void showAllAccountBalance() {

        // 查询所有账户及其余额
        List<String> accountList = getAccountList();
        if (CollectionUtils.isNotEmpty(accountList)) {
            for (String accountId : accountList) {
                BigInteger balance = getBalance(accountId);
                System.out.println(accountId + ":" + balance);
            }
        }
    }

    /**
     * 查询所有的账户id
     *
     * @return
     */
    public static List<String> getAccountList() {

        try {
            // 使用web3j 客户端
            EthAccounts ethAccounts = web3j.ethAccounts().send();
            List<String> accountIdList = ethAccounts.getAccounts();
            return accountIdList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据账户id查询余额
     *
     * @param accountId
     * @return
     */
    public static BigInteger getBalance(String accountId) {
        try {
            DefaultBlockParameter defaultBlockParameter = new DefaultBlockParameterNumber(web3j.ethBlockNumber().send().getBlockNumber());

            // 使用web3j连接
            EthGetBalance ethGetBalance = web3j.ethGetBalance(accountId, defaultBlockParameter).send();

            if (ethGetBalance != null) {
                return ethGetBalance.getBalance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
