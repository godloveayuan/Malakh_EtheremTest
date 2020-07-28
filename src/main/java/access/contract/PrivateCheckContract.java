package access.contract;

import java.math.BigInteger;
import java.util.Arrays;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.3.0.
 */
public class PrivateCheckContract extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b506104a4806100206000396000f300608060405260043610610041576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063725dbc4014610046575b600080fd5b34801561005257600080fd5b506100f3600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061010d565b604051808215151515815260200191505060405180910390f35b600060405180807f72656164000000000000000000000000000000000000000000000000000000008152506004019050604051809103902060001916826040518082805190602001908083835b60208310151561017f578051825260208201915060208101905060208303925061015a565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390206000191614156103145760405180807f646973706c617920646576696365000000000000000000000000000000000000815250600e019050604051809103902060001916836040518082805190602001908083835b6020831015156102265780518252602082019150602081019050602083039250610201565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040518091039020600019161480610301575060405180807f636f6e74726f6c20646576696365000000000000000000000000000000000000815250600e019050604051809103902060001916836040518082805190602001908083835b6020831015156102ce57805182526020820191506020810190506020830392506102a9565b6001836020036101000a038019825116818451168082178552505050505050905001915050604051809103902060001916145b1561030f5760019050610472565b61046d565b60405180807f636f6e74726f6c000000000000000000000000000000000000000000000000008152506007019050604051809103902060001916826040518082805190602001908083835b602083101515610384578051825260208201915060208101905060208303925061035f565b6001836020036101000a038019825116818451168082178552505050505050905001915050604051809103902060001916141561046c5760405180807f636f6e74726f6c20646576696365000000000000000000000000000000000000815250600e019050604051809103902060001916836040518082805190602001908083835b60208310151561042b5780518252602082019150602081019050602083039250610406565b6001836020036101000a038019825116818451168082178552505050505050905001915050604051809103902060001916141561046b5760019050610472565b5b5b600090505b929150505600a165627a7a72305820daf95f32264896fe4846a5b97688a13a4a4bce41aff21833aaf20ef8279ad59a0029";

    public static final String FUNC_CHECK = "check";

    @Deprecated
    protected PrivateCheckContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PrivateCheckContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PrivateCheckContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PrivateCheckContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<Boolean> check(String attributeSub, String operate) {
        final Function function = new Function(FUNC_CHECK, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(attributeSub), 
                new org.web3j.abi.datatypes.Utf8String(operate)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static PrivateCheckContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PrivateCheckContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PrivateCheckContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PrivateCheckContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PrivateCheckContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PrivateCheckContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PrivateCheckContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PrivateCheckContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PrivateCheckContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PrivateCheckContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PrivateCheckContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PrivateCheckContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<PrivateCheckContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(PrivateCheckContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<PrivateCheckContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(PrivateCheckContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
