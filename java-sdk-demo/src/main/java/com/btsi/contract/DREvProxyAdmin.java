package com.btsi.contract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.datatypes.Address;
import org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray;
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
public class DREvProxyAdmin extends Contract {
    public static final String[] BINARY_ARRAY = {
        "608060405234801561001057600080fd5b5061001a3361001f565b61006f565b600080546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b610a178061007e6000396000f3fe6080604052600436106100915760003560e01c80638da5cb5b116100595780638da5cb5b146101495780639623609d1461016757806399a88ec41461017a578063f2fde38b1461019a578063f3b7dead146101ba57600080fd5b80630e85e7bc14610096578063204e1c7a146100b85780635306c026146100f4578063715018a6146101145780637eff275e14610129575b600080fd5b3480156100a257600080fd5b506100b66100b136600461069d565b6101da565b005b3480156100c457600080fd5b506100d86100d3366004610771565b610246565b6040516001600160a01b03909116815260200160405180910390f35b34801561010057600080fd5b506100b661010f366004610795565b6102d7565b34801561012057600080fd5b506100b661031d565b34801561013557600080fd5b506100b66101443660046107de565b610331565b34801561015557600080fd5b506000546001600160a01b03166100d8565b6100b6610175366004610817565b610399565b34801561018657600080fd5b506100b66101953660046107de565b610408565b3480156101a657600080fd5b506100b66101b5366004610771565b61043e565b3480156101c657600080fd5b506100d86101d5366004610771565b6104bc565b6101e26104e2565b60405162c0585960e21b81526001600160a01b0384169063030161649061020f90859085906004016108d1565b600060405180830381600087803b15801561022957600080fd5b505af115801561023d573d6000803e3d6000fd5b50505050505050565b6000806000836001600160a01b031660405161026c90635c60da1b60e01b815260040190565b600060405180830381855afa9150503d80600081146102a7576040519150601f19603f3d011682016040523d82523d6000602084013e6102ac565b606091505b5091509150816102bb57600080fd5b808060200190518101906102cf919061095f565b949350505050565b6102df6104e2565b60405163215c7ded60e01b81526001600160e01b0319831660048201526001600160a01b03828116602483015284169063215c7ded9060440161020f565b6103256104e2565b61032f600061053c565b565b6103396104e2565b6040516308f2839760e41b81526001600160a01b038281166004830152831690638f283970906024015b600060405180830381600087803b15801561037d57600080fd5b505af1158015610391573d6000803e3d6000fd5b505050505050565b6103a16104e2565b60405163278f794360e11b81526001600160a01b03841690634f1ef2869034906103d1908690869060040161097c565b6000604051808303818588803b1580156103ea57600080fd5b505af11580156103fe573d6000803e3d6000fd5b5050505050505050565b6104106104e2565b604051631b2ce7f360e11b81526001600160a01b038281166004830152831690633659cfe690602401610363565b6104466104e2565b6001600160a01b0381166104b05760405162461bcd60e51b815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201526564647265737360d01b60648201526084015b60405180910390fd5b6104b98161053c565b50565b6000806000836001600160a01b031660405161026c906303e1469160e61b815260040190565b6000546001600160a01b0316331461032f5760405162461bcd60e51b815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e657260448201526064016104a7565b600080546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e09190a35050565b6001600160a01b03811681146104b957600080fd5b634e487b7160e01b600052604160045260246000fd5b604051601f8201601f1916810167ffffffffffffffff811182821017156105e0576105e06105a1565b604052919050565b600067ffffffffffffffff821115610602576106026105a1565b5060051b60200190565b80356001600160e01b03198116811461062457600080fd5b919050565b600082601f83011261063a57600080fd5b8135602061064f61064a836105e8565b6105b7565b82815260059290921b8401810191818101908684111561066e57600080fd5b8286015b848110156106925780356106858161058c565b8352918301918301610672565b509695505050505050565b6000806000606084860312156106b257600080fd5b83356106bd8161058c565b925060208481013567ffffffffffffffff808211156106db57600080fd5b818701915087601f8301126106ef57600080fd5b81356106fd61064a826105e8565b81815260059190911b8301840190848101908a83111561071c57600080fd5b938501935b82851015610741576107328561060c565b82529385019390850190610721565b96505050604087013592508083111561075957600080fd5b505061076786828701610629565b9150509250925092565b60006020828403121561078357600080fd5b813561078e8161058c565b9392505050565b6000806000606084860312156107aa57600080fd5b83356107b58161058c565b92506107c36020850161060c565b915060408401356107d38161058c565b809150509250925092565b600080604083850312156107f157600080fd5b82356107fc8161058c565b9150602083013561080c8161058c565b809150509250929050565b60008060006060848603121561082c57600080fd5b83356108378161058c565b92506020848101356108488161058c565b9250604085013567ffffffffffffffff8082111561086557600080fd5b818701915087601f83011261087957600080fd5b81358181111561088b5761088b6105a1565b61089d601f8201601f191685016105b7565b915080825288848285010111156108b357600080fd5b80848401858401376000848284010152508093505050509250925092565b604080825283519082018190526000906020906060840190828701845b828110156109145781516001600160e01b031916845292840192908401906001016108ee565b5050508381038285015284518082528583019183019060005b818110156109525783516001600160a01b03168352928401929184019160010161092d565b5090979650505050505050565b60006020828403121561097157600080fd5b815161078e8161058c565b60018060a01b038316815260006020604081840152835180604085015260005b818110156109b85785810183015185820160600152820161099c565b818111156109ca576000606083870101525b50601f01601f19169290920160600194935050505056fea2646970667358221220bf86528dd75786dbe4621f5e5478c54a814a325f05a859cf935f66c824c2460c64736f6c634300080b0033"
    };

    public static final String BINARY =
            org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {
        "608060405234801561001057600080fd5b5061001a3361001f565b61006f565b600080546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f5c7c30d4a0f08950cb23be4132957b357fa5dfdb0fcf218f81b86a1c036e47d09190a35050565b610a168061007e6000396000f3fe6080604052600436106100915760003560e01c8063d366253d11610059578063d366253d1461014e578063d42d0f4f1461016e578063d86e29e21461018e578063db1fc60c146101a3578063e6495f69146101b657600080fd5b806316cad12a146100965780635089e2c8146100b85780635defeee3146100ee5780636c57245d1461010e578063bdec0a0b1461012e575b600080fd5b3480156100a257600080fd5b506100b66100b13660046105a0565b6101d6565b005b3480156100c457600080fd5b506000546001600160a01b03165b6040516001600160a01b03909116815260200160405180910390f35b3480156100fa57600080fd5b506100d26101093660046105a0565b610255565b34801561011a57600080fd5b506100d26101293660046105a0565b6102e6565b34801561013a57600080fd5b506100b66101493660046106c0565b61030c565b34801561015a57600080fd5b506100b6610169366004610794565b610379565b34801561017a57600080fd5b506100b6610189366004610794565b6103e1565b34801561019a57600080fd5b506100b6610417565b6100b66101b13660046107cd565b61042b565b3480156101c257600080fd5b506100b66101d1366004610887565b61049a565b6101de6104e0565b6001600160a01b03811661024957604051636381e58960e11b815260206004820152602660248201527f4f776e61626c653a206e6577206f776e657220697320746865207a65726f206160448201526564647265737360d01b60648201526084015b60405180910390fd5b6102528161053b565b50565b6000806000836001600160a01b031660405161027b90635c60da1b60e01b815260040190565b600060405180830381855afa9150503d80600081146102b6576040519150601f19603f3d011682016040523d82523d6000602084013e6102bb565b606091505b5091509150816102ca57600080fd5b808060200190518101906102de91906108d0565b949350505050565b6000806000836001600160a01b031660405161027b906303e1469160e61b815260040190565b6103146104e0565b604051636398de5560e11b81526001600160a01b0384169063c731bcaa9061034290859085906004016108ed565b600060405180830381600087803b15801561035c57600080fd5b505af1158015610370573d6000803e3d6000fd5b50505050505050565b6103816104e0565b604051633a2a322560e21b81526001600160a01b03828116600483015283169063e8a8c894906024015b600060405180830381600087803b1580156103c557600080fd5b505af11580156103d9573d6000803e3d6000fd5b505050505050565b6103e96104e0565b60405163333b0add60e11b81526001600160a01b03828116600483015283169063667615ba906024016103ab565b61041f6104e0565b610429600061053b565b565b6104336104e0565b60405163207fa8b960e01b81526001600160a01b0384169063207fa8b9903490610463908690869060040161097b565b6000604051808303818588803b15801561047c57600080fd5b505af1158015610490573d6000803e3d6000fd5b5050505050505050565b6104a26104e0565b60405163b943fb0d60e01b81526001600160e01b0319831660048201526001600160a01b03828116602483015284169063b943fb0d90604401610342565b6000546001600160a01b0316331461042957604051636381e58960e11b815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e65726044820152606401610240565b600080546001600160a01b038381166001600160a01b0319831681178455604051919092169283917f5c7c30d4a0f08950cb23be4132957b357fa5dfdb0fcf218f81b86a1c036e47d09190a35050565b6001600160a01b038116811461025257600080fd5b6000602082840312156105b257600080fd5b81356105bd8161058b565b9392505050565b63b95aa35560e01b600052604160045260246000fd5b604051601f8201601f1916810167ffffffffffffffff81118282101715610603576106036105c4565b604052919050565b600067ffffffffffffffff821115610625576106256105c4565b5060051b60200190565b80356001600160e01b03198116811461064757600080fd5b919050565b600082601f83011261065d57600080fd5b8135602061067261066d8361060b565b6105da565b82815260059290921b8401810191818101908684111561069157600080fd5b8286015b848110156106b55780356106a88161058b565b8352918301918301610695565b509695505050505050565b6000806000606084860312156106d557600080fd5b83356106e08161058b565b925060208481013567ffffffffffffffff808211156106fe57600080fd5b818701915087601f83011261071257600080fd5b813561072061066d8261060b565b81815260059190911b8301840190848101908a83111561073f57600080fd5b938501935b82851015610764576107558561062f565b82529385019390850190610744565b96505050604087013592508083111561077c57600080fd5b505061078a8682870161064c565b9150509250925092565b600080604083850312156107a757600080fd5b82356107b28161058b565b915060208301356107c28161058b565b809150509250929050565b6000806000606084860312156107e257600080fd5b83356107ed8161058b565b92506020848101356107fe8161058b565b9250604085013567ffffffffffffffff8082111561081b57600080fd5b818701915087601f83011261082f57600080fd5b813581811115610841576108416105c4565b610853601f8201601f191685016105da565b9150808252888482850101111561086957600080fd5b80848401858401376000848284010152508093505050509250925092565b60008060006060848603121561089c57600080fd5b83356108a78161058b565b92506108b56020850161062f565b915060408401356108c58161058b565b809150509250925092565b6000602082840312156108e257600080fd5b81516105bd8161058b565b604080825283519082018190526000906020906060840190828701845b828110156109305781516001600160e01b0319168452928401929084019060010161090a565b5050508381038285015284518082528583019183019060005b8181101561096e5783516001600160a01b031683529284019291840191600101610949565b5090979650505050505050565b60018060a01b038316815260006020604081840152835180604085015260005b818110156109b75785810183015185820160600152820161099b565b818111156109c9576000606083870101525b50601f01601f19169290920160600194935050505056fea264697066735822122075007fe7242b4f4d2022cacbaea1a174fc9a91a5a4a020f9ce1fbeea014fa5b864736f6c634300080b0033"
    };

    public static final String SM_BINARY =
            org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {
        "[{\"inputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"previousOwner\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"newOwner\",\"type\":\"address\"}],\"name\":\"OwnershipTransferred\",\"type\":\"event\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"contract ITransparentUpgradeableProxy\",\"name\":\"proxy\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"newAdmin\",\"type\":\"address\"}],\"name\":\"changeProxyAdmin\",\"outputs\":[],\"selector\":[2130650974,3559722831],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"contract ITransparentUpgradeableProxy\",\"name\":\"proxy\",\"type\":\"address\"}],\"name\":\"getProxyAdmin\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"selector\":[4088913581,1817650269],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"contract ITransparentUpgradeableProxy\",\"name\":\"proxy\",\"type\":\"address\"}],\"name\":\"getProxyImplementation\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"selector\":[541990010,1576005347],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[],\"name\":\"owner\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"selector\":[2376452955,1351213768],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[],\"name\":\"renounceOwnership\",\"outputs\":[],\"selector\":[1901074598,3631098338],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"contract IDREvidenceLogicMan\",\"name\":\"proxy\",\"type\":\"address\"},{\"internalType\":\"bytes4\",\"name\":\"selector\",\"type\":\"bytes4\"},{\"internalType\":\"address\",\"name\":\"logicAddress\",\"type\":\"address\"}],\"name\":\"setSelector\",\"outputs\":[],\"selector\":[1392951334,3863568233],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"contract IDREvidenceLogicMan\",\"name\":\"proxy\",\"type\":\"address\"},{\"internalType\":\"bytes4[]\",\"name\":\"selectors\",\"type\":\"bytes4[]\"},{\"internalType\":\"address[]\",\"name\":\"logicAddresses\",\"type\":\"address[]\"}],\"name\":\"setSelectors\",\"outputs\":[],\"selector\":[243656636,3186362891],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":4,\"value\":[0]}],\"inputs\":[{\"internalType\":\"address\",\"name\":\"newOwner\",\"type\":\"address\"}],\"name\":\"transferOwnership\",\"outputs\":[],\"selector\":[4076725131,382390570],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"contract ITransparentUpgradeableProxy\",\"name\":\"proxy\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"implementation\",\"type\":\"address\"}],\"name\":\"upgrade\",\"outputs\":[],\"selector\":[2577960644,3546686781],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0}],\"inputs\":[{\"internalType\":\"contract ITransparentUpgradeableProxy\",\"name\":\"proxy\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"implementation\",\"type\":\"address\"},{\"internalType\":\"bytes\",\"name\":\"data\",\"type\":\"bytes\"}],\"name\":\"upgradeAndCall\",\"outputs\":[],\"selector\":[2518900893,3676292620],\"stateMutability\":\"payable\",\"type\":\"function\"}]"
    };

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_CHANGEPROXYADMIN = "changeProxyAdmin";

    public static final String FUNC_GETPROXYADMIN = "getProxyAdmin";

    public static final String FUNC_GETPROXYIMPLEMENTATION = "getProxyImplementation";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_SETSELECTOR = "setSelector";

    public static final String FUNC_SETSELECTORS = "setSelectors";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_UPGRADE = "upgrade";

    public static final String FUNC_UPGRADEANDCALL = "upgradeAndCall";

    public static final Event OWNERSHIPTRANSFERRED_EVENT =
            new Event(
                    "OwnershipTransferred",
                    Arrays.<TypeReference<?>>asList(
                            new TypeReference<Address>(true) {},
                            new TypeReference<Address>(true) {}));;

    protected DREvProxyAdmin(String contractAddress, Client client, CryptoKeyPair credential) {
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

    public TransactionReceipt setSelectors(
            String proxy, List<byte[]> selectors, List<String> logicAddresses) {
        final Function function =
                new Function(
                        FUNC_SETSELECTORS,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(proxy),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes4>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes4
                                                .class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                selectors,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.generated
                                                        .Bytes4.class)),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Address>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Address.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                logicAddresses,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Address
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForSetSelectors(
            String proxy, List<byte[]> selectors, List<String> logicAddresses) {
        final Function function =
                new Function(
                        FUNC_SETSELECTORS,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(proxy),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes4>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes4
                                                .class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                selectors,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.generated
                                                        .Bytes4.class)),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Address>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Address.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                logicAddresses,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Address
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String setSelectors(
            String proxy,
            List<byte[]> selectors,
            List<String> logicAddresses,
            TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_SETSELECTORS,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(proxy),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes4>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes4
                                                .class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                selectors,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.generated
                                                        .Bytes4.class)),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicArray<
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Address>(
                                        org.fisco.bcos.sdk.v3.codec.datatypes.Address.class,
                                        org.fisco.bcos.sdk.v3.codec.Utils.typeMap(
                                                logicAddresses,
                                                org.fisco.bcos.sdk.v3.codec.datatypes.Address
                                                        .class))),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple3<String, List<byte[]>, List<String>> getSetSelectorsInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_SETSELECTORS,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Address>() {},
                                new TypeReference<DynamicArray<Bytes4>>() {},
                                new TypeReference<DynamicArray<Address>>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple3<String, List<byte[]>, List<String>>(
                (String) results.get(0).getValue(),
                convertToNative((List<Bytes4>) results.get(1).getValue()),
                convertToNative((List<Address>) results.get(2).getValue()));
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

    public static DREvProxyAdmin load(
            String contractAddress, Client client, CryptoKeyPair credential) {
        return new DREvProxyAdmin(contractAddress, client, credential);
    }

    public static DREvProxyAdmin deploy(Client client, CryptoKeyPair credential)
            throws ContractException {
        return deploy(
                DREvProxyAdmin.class,
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
