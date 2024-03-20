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
        "608060405234801561001057600080fd5b5061001a3361001f565b61006f565b600080546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b61069a8061007e6000396000f3fe60806040526004361061007b5760003560e01c80639623609d1161004e5780639623609d1461011157806399a88ec414610124578063f2fde38b14610144578063f3b7dead1461016457600080fd5b8063204e1c7a14610080578063715018a6146100bc5780637eff275e146100d35780638da5cb5b146100f3575b600080fd5b34801561008c57600080fd5b506100a061009b366004610499565b610184565b6040516001600160a01b03909116815260200160405180910390f35b3480156100c857600080fd5b506100d1610215565b005b3480156100df57600080fd5b506100d16100ee3660046104bd565b610229565b3480156100ff57600080fd5b506000546001600160a01b03166100a0565b6100d161011f36600461050c565b610291565b34801561013057600080fd5b506100d161013f3660046104bd565b610300565b34801561015057600080fd5b506100d161015f366004610499565b610336565b34801561017057600080fd5b506100a061017f366004610499565b6103b4565b6000806000836001600160a01b03166040516101aa90635c60da1b60e01b815260040190565b600060405180830381855afa9150503d80600081146101e5576040519150601f19603f3d011682016040523d82523d6000602084013e6101ea565b606091505b5091509150816101f957600080fd5b8080602001905181019061020d91906105e2565b949350505050565b61021d6103da565b6102276000610434565b565b6102316103da565b6040516308f2839760e41b81526001600160a01b038281166004830152831690638f283970906024015b600060405180830381600087803b15801561027557600080fd5b505af1158015610289573d6000803e3d6000fd5b505050505050565b6102996103da565b60405163278f794360e11b81526001600160a01b03841690634f1ef2869034906102c990869086906004016105ff565b6000604051808303818588803b1580156102e257600080fd5b505af11580156102f6573d6000803e3d6000fd5b5050505050505050565b6103086103da565b604051631b2ce7f360e11b81526001600160a01b038281166004830152831690633659cfe69060240161025b565b61033e6103da565b6001600160a01b0381166103a85760405162461bcd60e51b815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201526564647265737360d01b60648201526084015b60405180910390fd5b6103b181610434565b50565b6000806000836001600160a01b03166040516101aa906303e1469160e61b815260040190565b6000546001600160a01b031633146102275760405162461bcd60e51b815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604482015260640161039f565b600080546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b6001600160a01b03811681146103b157600080fd5b6000602082840312156104ab57600080fd5b81356104b681610484565b9392505050565b600080604083850312156104d057600080fd5b82356104db81610484565b915060208301356104eb81610484565b809150509250929050565b634e487b7160e01b600052604160045260246000fd5b60008060006060848603121561052157600080fd5b833561052c81610484565b9250602084013561053c81610484565b9150604084013567ffffffffffffffff8082111561055957600080fd5b818601915086601f83011261056d57600080fd5b81358181111561057f5761057f6104f6565b604051601f8201601f19908116603f011681019083821181831017156105a7576105a76104f6565b816040528281528960208487010111156105c057600080fd5b8260208601602083013760006020848301015280955050505050509250925092565b6000602082840312156105f457600080fd5b81516104b681610484565b60018060a01b038316815260006020604081840152835180604085015260005b8181101561063b5785810183015185820160600152820161061f565b8181111561064d576000606083870101525b50601f01601f19169290920160600194935050505056fea264697066735822122000646565c1f556cdc7b79b99b0a0db02ef1fd7d451c919e0881fc294a72172f564736f6c634300080b0033"
    };

    public static final String BINARY =
            org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {
        "608060405234801561001057600080fd5b5061001a3361001f565b61006f565b600080546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f5c7c30d4a0f08950cb23be4132957b357fa5dfdb0fcf218f81b86a1c036e47d09190a35050565b6106988061007e6000396000f3fe60806040526004361061007b5760003560e01c8063d366253d1161004e578063d366253d14610118578063d42d0f4f14610138578063d86e29e214610158578063db1fc60c1461016d57600080fd5b806316cad12a146100805780635089e2c8146100a25780635defeee3146100d85780636c57245d146100f8575b600080fd5b34801561008c57600080fd5b506100a061009b366004610497565b610180565b005b3480156100ae57600080fd5b506000546001600160a01b03165b6040516001600160a01b03909116815260200160405180910390f35b3480156100e457600080fd5b506100bc6100f3366004610497565b6101ff565b34801561010457600080fd5b506100bc610113366004610497565b610290565b34801561012457600080fd5b506100a06101333660046104bb565b6102b6565b34801561014457600080fd5b506100a06101533660046104bb565b61031e565b34801561016457600080fd5b506100a0610354565b6100a061017b36600461050a565b610368565b6101886103d7565b6001600160a01b0381166101f357604051636381e58960e11b815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201526564647265737360d01b60648201526084015b60405180910390fd5b6101fc81610432565b50565b6000806000836001600160a01b031660405161022590635c60da1b60e01b815260040190565b600060405180830381855afa9150503d8060008114610260576040519150601f19603f3d011682016040523d82523d6000602084013e610265565b606091505b50915091508161027457600080fd5b8080602001905181019061028891906105e0565b949350505050565b6000806000836001600160a01b0316604051610225906303e1469160e61b815260040190565b6102be6103d7565b604051633a2a322560e21b81526001600160a01b03828116600483015283169063e8a8c894906024015b600060405180830381600087803b15801561030257600080fd5b505af1158015610316573d6000803e3d6000fd5b505050505050565b6103266103d7565b60405163333b0add60e11b81526001600160a01b03828116600483015283169063667615ba906024016102e8565b61035c6103d7565b6103666000610432565b565b6103706103d7565b60405163207fa8b960e01b81526001600160a01b0384169063207fa8b99034906103a090869086906004016105fd565b6000604051808303818588803b1580156103b957600080fd5b505af11580156103cd573d6000803e3d6000fd5b5050505050505050565b6000546001600160a01b0316331461036657604051636381e58960e11b815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e657260448201526064016101ea565b600080546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f5c7c30d4a0f08950cb23be4132957b357fa5dfdb0fcf218f81b86a1c036e47d09190a35050565b6001600160a01b03811681146101fc57600080fd5b6000602082840312156104a957600080fd5b81356104b481610482565b9392505050565b600080604083850312156104ce57600080fd5b82356104d981610482565b915060208301356104e981610482565b809150509250929050565b63b95aa35560e01b600052604160045260246000fd5b60008060006060848603121561051f57600080fd5b833561052a81610482565b9250602084013561053a81610482565b9150604084013567ffffffffffffffff8082111561055757600080fd5b818601915086601f83011261056b57600080fd5b81358181111561057d5761057d6104f4565b604051601f8201601f19908116603f011681019083821181831017156105a5576105a56104f4565b816040528281528960208487010111156105be57600080fd5b8260208601602083013760006020848301015280955050505050509250925092565b6000602082840312156105f257600080fd5b81516104b481610482565b60018060a01b038316815260006020604081840152835180604085015260005b818110156106395785810183015185820160600152820161061d565b8181111561064b576000606083870101525b50601f01601f19169290920160600194935050505056fea264697066735822122080043878277145e7a7603483946cbd94e2cdec95cfbf91154846a6347f24faff64736f6c634300080b0033"
    };

    public static final String SM_BINARY =
            org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {
        "[{\"inputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"previousOwner\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"newOwner\",\"type\":\"address\"}],\"name\":\"OwnershipTransferred\",\"type\":\"event\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"contract ITransparentUpgradeableProxy\",\"name\":\"proxy\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"newAdmin\",\"type\":\"address\"}],\"name\":\"changeProxyAdmin\",\"outputs\":[],\"selector\":[2130650974,3559722831],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"contract ITransparentUpgradeableProxy\",\"name\":\"proxy\",\"type\":\"address\"}],\"name\":\"getProxyAdmin\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"selector\":[4088913581,1817650269],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"contract ITransparentUpgradeableProxy\",\"name\":\"proxy\",\"type\":\"address\"}],\"name\":\"getProxyImplementation\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"selector\":[541990010,1576005347],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[],\"name\":\"owner\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"selector\":[2376452955,1351213768],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[],\"name\":\"renounceOwnership\",\"outputs\":[],\"selector\":[1901074598,3631098338],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[{\"internalType\":\"address\",\"name\":\"newOwner\",\"type\":\"address\"}],\"name\":\"transferOwnership\",\"outputs\":[],\"selector\":[4076725131,382390570],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"contract ITransparentUpgradeableProxy\",\"name\":\"proxy\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"implementation\",\"type\":\"address\"}],\"name\":\"upgrade\",\"outputs\":[],\"selector\":[2577960644,3546686781],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"contract ITransparentUpgradeableProxy\",\"name\":\"proxy\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"implementation\",\"type\":\"address\"},{\"internalType\":\"bytes\",\"name\":\"data\",\"type\":\"bytes\"}],\"name\":\"upgradeAndCall\",\"outputs\":[],\"selector\":[2518900893,3676292620],\"stateMutability\":\"payable\",\"type\":\"function\"}]"
    };

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_CHANGEPROXYADMIN = "changeProxyAdmin";

    public static final String FUNC_GETPROXYADMIN = "getProxyAdmin";

    public static final String FUNC_GETPROXYIMPLEMENTATION = "getProxyImplementation";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

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
