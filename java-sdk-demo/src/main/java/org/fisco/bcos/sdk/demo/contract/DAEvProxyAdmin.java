package org.fisco.bcos.sdk.demo.contract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.datatypes.Address;
import org.fisco.bcos.sdk.v3.codec.datatypes.DynamicBytes;
import org.fisco.bcos.sdk.v3.codec.datatypes.Event;
import org.fisco.bcos.sdk.v3.codec.datatypes.Function;
import org.fisco.bcos.sdk.v3.codec.datatypes.Type;
import org.fisco.bcos.sdk.v3.codec.datatypes.TypeReference;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes4;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class DAEvProxyAdmin extends Contract {
    public static final String[] BINARY_ARRAY = {
        "608060405234801561001057600080fd5b5061001a3361001f565b61006f565b600080546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b6107958061007e6000396000f3fe6080604052600436106100865760003560e01c80638da5cb5b116100595780638da5cb5b1461011e5780639623609d1461013c57806399a88ec41461014f578063f2fde38b1461016f578063f3b7dead1461018f57600080fd5b8063204e1c7a1461008b5780635306c026146100c7578063715018a6146100e95780637eff275e146100fe575b600080fd5b34801561009757600080fd5b506100ab6100a636600461053c565b6101af565b6040516001600160a01b03909116815260200160405180910390f35b3480156100d357600080fd5b506100e76100e2366004610560565b610240565b005b3480156100f557600080fd5b506100e76102b8565b34801561010a57600080fd5b506100e76101193660046105b8565b6102cc565b34801561012a57600080fd5b506000546001600160a01b03166100ab565b6100e761014a366004610607565b610334565b34801561015b57600080fd5b506100e761016a3660046105b8565b6103a3565b34801561017b57600080fd5b506100e761018a36600461053c565b6103d9565b34801561019b57600080fd5b506100ab6101aa36600461053c565b610457565b6000806000836001600160a01b03166040516101d590635c60da1b60e01b815260040190565b600060405180830381855afa9150503d8060008114610210576040519150601f19603f3d011682016040523d82523d6000602084013e610215565b606091505b50915091508161022457600080fd5b8080602001905181019061023891906106dd565b949350505050565b61024861047d565b60405163215c7ded60e01b81526001600160e01b0319831660048201526001600160a01b03828116602483015284169063215c7ded90604401600060405180830381600087803b15801561029b57600080fd5b505af11580156102af573d6000803e3d6000fd5b50505050505050565b6102c061047d565b6102ca60006104d7565b565b6102d461047d565b6040516308f2839760e41b81526001600160a01b038281166004830152831690638f283970906024015b600060405180830381600087803b15801561031857600080fd5b505af115801561032c573d6000803e3d6000fd5b505050505050565b61033c61047d565b60405163278f794360e11b81526001600160a01b03841690634f1ef28690349061036c90869086906004016106fa565b6000604051808303818588803b15801561038557600080fd5b505af1158015610399573d6000803e3d6000fd5b5050505050505050565b6103ab61047d565b604051631b2ce7f360e11b81526001600160a01b038281166004830152831690633659cfe6906024016102fe565b6103e161047d565b6001600160a01b03811661044b5760405162461bcd60e51b815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201526564647265737360d01b60648201526084015b60405180910390fd5b610454816104d7565b50565b6000806000836001600160a01b03166040516101d5906303e1469160e61b815260040190565b6000546001600160a01b031633146102ca5760405162461bcd60e51b815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e65726044820152606401610442565b600080546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b6001600160a01b038116811461045457600080fd5b60006020828403121561054e57600080fd5b813561055981610527565b9392505050565b60008060006060848603121561057557600080fd5b833561058081610527565b925060208401356001600160e01b03198116811461059d57600080fd5b915060408401356105ad81610527565b809150509250925092565b600080604083850312156105cb57600080fd5b82356105d681610527565b915060208301356105e681610527565b809150509250929050565b634e487b7160e01b600052604160045260246000fd5b60008060006060848603121561061c57600080fd5b833561062781610527565b9250602084013561063781610527565b9150604084013567ffffffffffffffff8082111561065457600080fd5b818601915086601f83011261066857600080fd5b81358181111561067a5761067a6105f1565b604051601f8201601f19908116603f011681019083821181831017156106a2576106a26105f1565b816040528281528960208487010111156106bb57600080fd5b8260208601602083013760006020848301015280955050505050509250925092565b6000602082840312156106ef57600080fd5b815161055981610527565b60018060a01b038316815260006020604081840152835180604085015260005b818110156107365785810183015185820160600152820161071a565b81811115610748576000606083870101525b50601f01601f19169290920160600194935050505056fea2646970667358221220cb7c54357b9949405ba1c1dd5524970929b05b55a18f5bdad881a6e4376c41e964736f6c634300080b0033"
    };

    public static final String BINARY =
            org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {
        "608060405234801561001057600080fd5b5061001a3361001f565b61006f565b600080546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f5c7c30d4a0f08950cb23be4132957b357fa5dfdb0fcf218f81b86a1c036e47d09190a35050565b6107938061007e6000396000f3fe6080604052600436106100865760003560e01c8063d366253d11610059578063d366253d14610123578063d42d0f4f14610143578063d86e29e214610163578063db1fc60c14610178578063e6495f691461018b57600080fd5b806316cad12a1461008b5780635089e2c8146100ad5780635defeee3146100e35780636c57245d14610103575b600080fd5b34801561009757600080fd5b506100ab6100a636600461053a565b6101ab565b005b3480156100b957600080fd5b506000546001600160a01b03165b6040516001600160a01b03909116815260200160405180910390f35b3480156100ef57600080fd5b506100c76100fe36600461053a565b61022a565b34801561010f57600080fd5b506100c761011e36600461053a565b6102bb565b34801561012f57600080fd5b506100ab61013e36600461055e565b6102e1565b34801561014f57600080fd5b506100ab61015e36600461055e565b610349565b34801561016f57600080fd5b506100ab61037f565b6100ab6101863660046105ad565b610393565b34801561019757600080fd5b506100ab6101a6366004610683565b610402565b6101b361047a565b6001600160a01b03811661021e57604051636381e58960e11b815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201526564647265737360d01b60648201526084015b60405180910390fd5b610227816104d5565b50565b6000806000836001600160a01b031660405161025090635c60da1b60e01b815260040190565b600060405180830381855afa9150503d806000811461028b576040519150601f19603f3d011682016040523d82523d6000602084013e610290565b606091505b50915091508161029f57600080fd5b808060200190518101906102b391906106db565b949350505050565b6000806000836001600160a01b0316604051610250906303e1469160e61b815260040190565b6102e961047a565b604051633a2a322560e21b81526001600160a01b03828116600483015283169063e8a8c894906024015b600060405180830381600087803b15801561032d57600080fd5b505af1158015610341573d6000803e3d6000fd5b505050505050565b61035161047a565b60405163333b0add60e11b81526001600160a01b03828116600483015283169063667615ba90602401610313565b61038761047a565b61039160006104d5565b565b61039b61047a565b60405163207fa8b960e01b81526001600160a01b0384169063207fa8b99034906103cb90869086906004016106f8565b6000604051808303818588803b1580156103e457600080fd5b505af11580156103f8573d6000803e3d6000fd5b5050505050505050565b61040a61047a565b60405163b943fb0d60e01b81526001600160e01b0319831660048201526001600160a01b03828116602483015284169063b943fb0d90604401600060405180830381600087803b15801561045d57600080fd5b505af1158015610471573d6000803e3d6000fd5b50505050505050565b6000546001600160a01b0316331461039157604051636381e58960e11b815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e65726044820152606401610215565b600080546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f5c7c30d4a0f08950cb23be4132957b357fa5dfdb0fcf218f81b86a1c036e47d09190a35050565b6001600160a01b038116811461022757600080fd5b60006020828403121561054c57600080fd5b813561055781610525565b9392505050565b6000806040838503121561057157600080fd5b823561057c81610525565b9150602083013561058c81610525565b809150509250929050565b63b95aa35560e01b600052604160045260246000fd5b6000806000606084860312156105c257600080fd5b83356105cd81610525565b925060208401356105dd81610525565b9150604084013567ffffffffffffffff808211156105fa57600080fd5b818601915086601f83011261060e57600080fd5b81358181111561062057610620610597565b604051601f8201601f19908116603f0116810190838211818310171561064857610648610597565b8160405282815289602084870101111561066157600080fd5b8260208601602083013760006020848301015280955050505050509250925092565b60008060006060848603121561069857600080fd5b83356106a381610525565b925060208401356001600160e01b0319811681146106c057600080fd5b915060408401356106d081610525565b809150509250925092565b6000602082840312156106ed57600080fd5b815161055781610525565b60018060a01b038316815260006020604081840152835180604085015260005b8181101561073457858101830151858201606001528201610718565b81811115610746576000606083870101525b50601f01601f19169290920160600194935050505056fea2646970667358221220c0ec8c01a10283d3e5b0f3617673f99d5470ad7863b1cf663eb637d1285c83e064736f6c634300080b0033"
    };

    public static final String SM_BINARY =
            org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {
        "[{\"inputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"previousOwner\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"newOwner\",\"type\":\"address\"}],\"name\":\"OwnershipTransferred\",\"type\":\"event\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"contract ITransparentUpgradeableProxy\",\"name\":\"proxy\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"newAdmin\",\"type\":\"address\"}],\"name\":\"changeProxyAdmin\",\"outputs\":[],\"selector\":[2130650974,3559722831],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"contract ITransparentUpgradeableProxy\",\"name\":\"proxy\",\"type\":\"address\"}],\"name\":\"getProxyAdmin\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"selector\":[4088913581,1817650269],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"contract ITransparentUpgradeableProxy\",\"name\":\"proxy\",\"type\":\"address\"}],\"name\":\"getProxyImplementation\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"selector\":[541990010,1576005347],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[],\"name\":\"owner\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"selector\":[2376452955,1351213768],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[],\"name\":\"renounceOwnership\",\"outputs\":[],\"selector\":[1901074598,3631098338],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"contract IDREvidenceLogicMan\",\"name\":\"proxy\",\"type\":\"address\"},{\"internalType\":\"bytes4\",\"name\":\"selector\",\"type\":\"bytes4\"},{\"internalType\":\"address\",\"name\":\"logicAddress\",\"type\":\"address\"}],\"name\":\"setSelector\",\"outputs\":[],\"selector\":[1392951334,3863568233],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[{\"internalType\":\"address\",\"name\":\"newOwner\",\"type\":\"address\"}],\"name\":\"transferOwnership\",\"outputs\":[],\"selector\":[4076725131,382390570],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"contract ITransparentUpgradeableProxy\",\"name\":\"proxy\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"implementation\",\"type\":\"address\"}],\"name\":\"upgrade\",\"outputs\":[],\"selector\":[2577960644,3546686781],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"contract ITransparentUpgradeableProxy\",\"name\":\"proxy\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"implementation\",\"type\":\"address\"},{\"internalType\":\"bytes\",\"name\":\"data\",\"type\":\"bytes\"}],\"name\":\"upgradeAndCall\",\"outputs\":[],\"selector\":[2518900893,3676292620],\"stateMutability\":\"payable\",\"type\":\"function\"}]"
    };

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_CHANGEPROXYADMIN = "changeProxyAdmin";

    public static final String FUNC_GETPROXYADMIN = "getProxyAdmin";

    public static final String FUNC_GETPROXYIMPLEMENTATION = "getProxyImplementation";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_SETSELECTOR = "setSelector";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_UPGRADE = "upgrade";

    public static final String FUNC_UPGRADEANDCALL = "upgradeAndCall";

    public static final Event OWNERSHIPTRANSFERRED_EVENT =
            new Event(
                    "OwnershipTransferred",
                    Arrays.<TypeReference<?>>asList(
                            new TypeReference<Address>(true) {},
                            new TypeReference<Address>(true) {}));;

    protected DAEvProxyAdmin(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static String getABI() {
        return ABI;
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList =
                extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses =
                new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse =
                    new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public TransactionReceipt changeProxyAdmin(String proxy, String newAdmin) {
        final Function function =
                new Function(
                        FUNC_CHANGEPROXYADMIN,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(proxy),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(newAdmin)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForChangeProxyAdmin(String proxy, String newAdmin) {
        final Function function =
                new Function(
                        FUNC_CHANGEPROXYADMIN,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(proxy),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(newAdmin)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String changeProxyAdmin(String proxy, String newAdmin, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_CHANGEPROXYADMIN,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(proxy),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(newAdmin)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple2<String, String> getChangeProxyAdminInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_CHANGEPROXYADMIN,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, String>(
                (String) results.get(0).getValue(), (String) results.get(1).getValue());
    }

    public String getProxyAdmin(String proxy) throws ContractException {
        final Function function =
                new Function(
                        FUNC_GETPROXYADMIN,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(proxy)),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public String getProxyImplementation(String proxy) throws ContractException {
        final Function function =
                new Function(
                        FUNC_GETPROXYIMPLEMENTATION,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(proxy)),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public String owner() throws ContractException {
        final Function function =
                new Function(
                        FUNC_OWNER,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeCallWithSingleValueReturn(function, String.class);
    }

    public TransactionReceipt renounceOwnership() {
        final Function function =
                new Function(
                        FUNC_RENOUNCEOWNERSHIP,
                        Arrays.<Type>asList(),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForRenounceOwnership() {
        final Function function =
                new Function(
                        FUNC_RENOUNCEOWNERSHIP,
                        Arrays.<Type>asList(),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String renounceOwnership(TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_RENOUNCEOWNERSHIP,
                        Arrays.<Type>asList(),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public TransactionReceipt setSelector(String proxy, byte[] selector, String logicAddress) {
        final Function function =
                new Function(
                        FUNC_SETSELECTOR,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(proxy),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes4(
                                        selector),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(logicAddress)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForSetSelector(
            String proxy, byte[] selector, String logicAddress) {
        final Function function =
                new Function(
                        FUNC_SETSELECTOR,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(proxy),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes4(
                                        selector),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(logicAddress)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String setSelector(
            String proxy, byte[] selector, String logicAddress, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_SETSELECTOR,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(proxy),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes4(
                                        selector),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(logicAddress)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple3<String, byte[], String> getSetSelectorInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_SETSELECTOR,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Address>() {},
                                new TypeReference<Bytes4>() {},
                                new TypeReference<Address>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple3<String, byte[], String>(
                (String) results.get(0).getValue(),
                (byte[]) results.get(1).getValue(),
                (String) results.get(2).getValue());
    }

    public TransactionReceipt transferOwnership(String newOwner) {
        final Function function =
                new Function(
                        FUNC_TRANSFEROWNERSHIP,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(newOwner)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForTransferOwnership(String newOwner) {
        final Function function =
                new Function(
                        FUNC_TRANSFEROWNERSHIP,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(newOwner)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String transferOwnership(String newOwner, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_TRANSFEROWNERSHIP,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(newOwner)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple1<String> getTransferOwnershipInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_TRANSFEROWNERSHIP,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>((String) results.get(0).getValue());
    }

    public TransactionReceipt upgrade(String proxy, String implementation) {
        final Function function =
                new Function(
                        FUNC_UPGRADE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(proxy),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(implementation)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForUpgrade(String proxy, String implementation) {
        final Function function =
                new Function(
                        FUNC_UPGRADE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(proxy),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(implementation)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String upgrade(String proxy, String implementation, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_UPGRADE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(proxy),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(implementation)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple2<String, String> getUpgradeInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_UPGRADE,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Address>() {}, new TypeReference<Address>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, String>(
                (String) results.get(0).getValue(), (String) results.get(1).getValue());
    }

    public TransactionReceipt upgradeAndCall(String proxy, String implementation, byte[] data) {
        final Function function =
                new Function(
                        FUNC_UPGRADEANDCALL,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(proxy),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(implementation),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicBytes(data)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForUpgradeAndCall(
            String proxy, String implementation, byte[] data) {
        final Function function =
                new Function(
                        FUNC_UPGRADEANDCALL,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(proxy),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(implementation),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicBytes(data)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String upgradeAndCall(
            String proxy, String implementation, byte[] data, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_UPGRADEANDCALL,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(proxy),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(implementation),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicBytes(data)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple3<String, String, byte[]> getUpgradeAndCallInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_UPGRADEANDCALL,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Address>() {},
                                new TypeReference<Address>() {},
                                new TypeReference<DynamicBytes>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple3<String, String, byte[]>(
                (String) results.get(0).getValue(),
                (String) results.get(1).getValue(),
                (byte[]) results.get(2).getValue());
    }

    public static DAEvProxyAdmin load(
            String contractAddress, Client client, CryptoKeyPair credential) {
        return new DAEvProxyAdmin(contractAddress, client, credential);
    }

    public static DAEvProxyAdmin deploy(Client client, CryptoKeyPair credential)
            throws ContractException {
        return deploy(
                DAEvProxyAdmin.class,
                client,
                credential,
                getBinary(client.getCryptoSuite()),
                getABI(),
                null,
                null);
    }

    public static class OwnershipTransferredEventResponse {
        public TransactionReceipt.Logs log;

        public String previousOwner;

        public String newOwner;
    }
}
