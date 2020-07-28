package test;

import access.contract.ContractService;

/**
 * @Author:Malakh
 * @Date: 2020/4/23
 * @Description: 智能合约调用的测试类
 */

public class ContractServiceTest {
    private static String publicContractAddr = "0x486bb3a43dcd413e5c5a38fecfb988dda74cc007";
    private static String privateContractAddr = "0xd50e76c826f8e00f23f5e6ed8ce2eb093ab620a0";  // 卧室灯光的专属策略合约
    private static String privateContractAddr2 = "0x399861c54c28b453f0088dea0037a87d8271d006"; // 客厅空调的专属策略合约

    public static void main(String[] args) {
        publicContractTest();
        privateContractTest();
    }

    public static void publicContractTest() {
        Boolean check = ContractService.publicCheck(publicContractAddr, "bedroom", "bedroom");
        System.out.println(check);
    }

    public static void privateContractTest() {
        Boolean check = ContractService.privateCheck(privateContractAddr2, "displayer", "read");
        System.out.println(check);
    }
}
