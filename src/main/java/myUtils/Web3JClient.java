package myUtils;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

/**
 * @Author: Malakh
 * @Date: 19-5-22
 * @Description:
 */
public class Web3JClient {

    // 启动区块链服务端时的 rpcaddr 和 rpcport 参数，如果没有使用了其他参数，则需要在这里修改
    private static String url = "http://192.168.3.89:8545/";

    private Web3JClient() {
    }

    private volatile static Web3j web3j;

    public static Web3j getClient() {
        if (web3j == null) {
            synchronized (Web3JClient.class) {
                if (web3j == null) {
                    web3j = Web3j.build(new HttpService(url));
                }
            }
        }
        return web3j;
    }
}
