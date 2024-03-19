package org.fisco.bcos.sdk.demo.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.datatypes.Address;
import org.fisco.bcos.sdk.v3.codec.datatypes.Bool;
import org.fisco.bcos.sdk.v3.codec.datatypes.Event;
import org.fisco.bcos.sdk.v3.codec.datatypes.Function;
import org.fisco.bcos.sdk.v3.codec.datatypes.Type;
import org.fisco.bcos.sdk.v3.codec.datatypes.TypeReference;
import org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint8;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class DAAccessController extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b50610b03806100206000396000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c80633dd296b5116100715780633dd296b51461014457806347496cbd1461015757806391d148541461016a578063a217fddf1461017d578063d547741f14610185578063fc4eb4821461019857600080fd5b806301ffc9a7146100ae578063248a9ca3146100d65780632f2ff15d1461010757806332d743271461011c57806336568abe14610131575b600080fd5b6100c16100bc366004610794565b6101ab565b60405190151581526020015b60405180910390f35b6100f96100e43660046107be565b60009081526065602052604090206001015490565b6040519081526020016100cd565b61011a6101153660046107ee565b6101e2565b005b6100f9600080516020610aae83398151915281565b61011a61013f3660046107ee565b61020c565b61011a6101523660046108bd565b61028f565b6100c1610165366004610902565b610331565b6100c16101783660046107ee565b6103b8565b6100f9600081565b61011a6101933660046107ee565b6103e3565b61011a6101a6366004610902565b610408565b60006001600160e01b03198216637965db0b60e01b14806101dc57506301ffc9a760e01b6001600160e01b03198316145b92915050565b6000828152606560205260409020600101546101fd8161048c565b6102078383610499565b505050565b6001600160a01b03811633146102815760405162461bcd60e51b815260206004820152602f60248201527f416363657373436f6e74726f6c3a2063616e206f6e6c792072656e6f756e636560448201526e103937b632b9903337b91039b2b63360891b60648201526084015b60405180910390fd5b61028b828261051f565b5050565b600061029a8161048c565b60006001600160a01b03166097846040516102b5919061096f565b908152604051908190036020019020546001600160a01b03161415610207576102ec600080516020610aae83398151915283610499565b816097846040516102fd919061096f565b90815260405190819003602001902080546001600160a01b03929092166001600160a01b0319909216919091179055505050565b6000806001600160a01b031660978360405161034d919061096f565b908152604051908190036020019020546001600160a01b0316146103ab576101dc600080516020610aae83398151915260978460405161038d919061096f565b908152604051908190036020019020546001600160a01b03166103b8565b506000919050565b919050565b60009182526065602090815260408084206001600160a01b0393909316845291905290205460ff1690565b6000828152606560205260409020600101546103fe8161048c565b610207838361051f565b60006104138161048c565b60006001600160a01b031660978360405161042e919061096f565b908152604051908190036020019020546001600160a01b03161461028b5761028b600080516020610aae83398151915260978460405161046e919061096f565b908152604051908190036020019020546001600160a01b031661051f565b6104968133610586565b50565b6104a382826103b8565b61028b5760008281526065602090815260408083206001600160a01b03851684529091529020805460ff191660011790556104db3390565b6001600160a01b0316816001600160a01b0316837f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d60405160405180910390a45050565b61052982826103b8565b1561028b5760008281526065602090815260408083206001600160a01b0385168085529252808320805460ff1916905551339285917ff6391f5c32d9c69d2a47ea670b442974b53935d1edc7fd64eb21e047a839171b9190a45050565b61059082826103b8565b61028b5761059d816105df565b6105a88360206105f1565b6040516020016105b992919061098b565b60408051601f198184030181529082905262461bcd60e51b825261027891600401610a00565b60606101dc6001600160a01b03831660145b60606000610600836002610a49565b61060b906002610a68565b67ffffffffffffffff8111156106235761062361081a565b6040519080825280601f01601f19166020018201604052801561064d576020820181803683370190505b509050600360fc1b8160008151811061066857610668610a80565b60200101906001600160f81b031916908160001a905350600f60fb1b8160018151811061069757610697610a80565b60200101906001600160f81b031916908160001a90535060006106bb846002610a49565b6106c6906001610a68565b90505b600181111561073e576f181899199a1a9b1b9c1cb0b131b232b360811b85600f16601081106106fa576106fa610a80565b1a60f81b82828151811061071057610710610a80565b60200101906001600160f81b031916908160001a90535060049490941c9361073781610a96565b90506106c9565b50831561078d5760405162461bcd60e51b815260206004820181905260248201527f537472696e67733a20686578206c656e67746820696e73756666696369656e746044820152606401610278565b9392505050565b6000602082840312156107a657600080fd5b81356001600160e01b03198116811461078d57600080fd5b6000602082840312156107d057600080fd5b5035919050565b80356001600160a01b03811681146103b357600080fd5b6000806040838503121561080157600080fd5b82359150610811602084016107d7565b90509250929050565b634e487b7160e01b600052604160045260246000fd5b600082601f83011261084157600080fd5b813567ffffffffffffffff8082111561085c5761085c61081a565b604051601f8301601f19908116603f011681019082821181831017156108845761088461081a565b8160405283815286602085880101111561089d57600080fd5b836020870160208301376000602085830101528094505050505092915050565b600080604083850312156108d057600080fd5b823567ffffffffffffffff8111156108e757600080fd5b6108f385828601610830565b925050610811602084016107d7565b60006020828403121561091457600080fd5b813567ffffffffffffffff81111561092b57600080fd5b61093784828501610830565b949350505050565b60005b8381101561095a578181015183820152602001610942565b83811115610969576000848401525b50505050565b6000825161098181846020870161093f565b9190910192915050565b7f416363657373436f6e74726f6c3a206163636f756e74200000000000000000008152600083516109c381601785016020880161093f565b7001034b99036b4b9b9b4b733903937b6329607d1b60179184019182015283516109f481602884016020880161093f565b01602801949350505050565b6020815260008251806020840152610a1f81604085016020870161093f565b601f01601f19169190910160400192915050565b634e487b7160e01b600052601160045260246000fd5b6000816000190483118215151615610a6357610a63610a33565b500290565b60008219821115610a7b57610a7b610a33565b500190565b634e487b7160e01b600052603260045260246000fd5b600081610aa557610aa5610a33565b50600019019056fe22f59c2f02edbae5c421247f029fe3112c5a85f70b4a48f5d93b22ada9ea772ea26469706673582212202a310940ed2f54394a5533e91314b46e1d86c00cad1dd20545c96e292116b2b064736f6c634300080b0033"};

    public static final String BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b50610b02806100206000396000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c8063b70b2fbc11610071578063b70b2fbc14610119578063de92755c1461012c578063ea7eb7981461014f578063f53c381314610162578063fbd3f0bf14610175578063fd09e7d61461019857600080fd5b806317d66dc8146100ae5780631b3240b8146100c35780635472010b146100de578063a4ff64c2146100f1578063b6773ad814610104575b600080fd5b6100c16100bc3660046107aa565b6101ab565b005b6100cb600081565b6040519081526020015b60405180910390f35b6100c16100ec3660046107aa565b61022f565b6100c16100ff366004610879565b610259565b6100cb600080516020610aad83398151915281565b6100c16101273660046108b6565b6102dd565b61013f61013a3660046107aa565b61037f565b60405190151581526020016100d5565b61013f61015d3660046108fb565b6103aa565b61013f610170366004610879565b6103e1565b6100cb610183366004610925565b60009081526065602052604090206001015490565b6100c16101a63660046107aa565b610468565b6001600160a01b038116331461022157604051636381e58960e11b815260206004820152602f60248201527f416363657373436f6e74726f6c3a2063616e206f6e6c792072656e6f756e636560448201526e103937b632b9903337b91039b2b63360891b60648201526084015b60405180910390fd5b61022b8282610489565b5050565b60008281526065602052604090206001015461024a816104f0565b61025483836104fd565b505050565b6000610264816104f0565b60006001600160a01b031660978360405161027f919061096e565b908152604051908190036020019020546001600160a01b03161461022b5761022b600080516020610aad8339815191526097846040516102bf919061096e565b908152604051908190036020019020546001600160a01b0316610489565b60006102e8816104f0565b60006001600160a01b0316609784604051610303919061096e565b908152604051908190036020019020546001600160a01b031614156102545761033a600080516020610aad833981519152836104fd565b8160978460405161034b919061096e565b90815260405190819003602001902080546001600160a01b03929092166001600160a01b0319909216919091179055505050565b60009182526065602090815260408084206001600160a01b0393909316845291905290205460ff1690565b60006001600160e01b03198216634df6077b60e11b14806103db5750631d4fd6f360e31b6001600160e01b03198316145b92915050565b6000806001600160a01b03166097836040516103fd919061096e565b908152604051908190036020019020546001600160a01b03161461045b576103db600080516020610aad83398151915260978460405161043d919061096e565b908152604051908190036020019020546001600160a01b031661037f565b506000919050565b919050565b600082815260656020526040902060010154610483816104f0565b61025483835b610493828261037f565b1561022b5760008281526065602090815260408083206001600160a01b0385168085529252808320805460ff1916905551339285917fddf24a0d777ab37f0cd0acf6e2b0a75570d42c6500405e008f7f9a61836cf2a39190a45050565b6104fa8133610583565b50565b610507828261037f565b61022b5760008281526065602090815260408083206001600160a01b03851684529091529020805460ff1916600117905561053f3390565b6001600160a01b0316816001600160a01b0316837f3a8c19c5eddb5ca7fdd091f00e2b3978b86344c928af4d2d7f6733572cc06b4c60405160405180910390a45050565b61058d828261037f565b61022b5761059a816105dd565b6105a58360206105ef565b6040516020016105b692919061098a565b60408051601f1981840301815290829052636381e58960e11b8252610218916004016109ff565b60606103db6001600160a01b03831660145b606060006105fe836002610a48565b610609906002610a67565b67ffffffffffffffff811115610621576106216107d6565b6040519080825280601f01601f19166020018201604052801561064b576020820181803683370190505b509050600360fc1b8160008151811061066657610666610a7f565b60200101906001600160f81b031916908160001a905350600f60fb1b8160018151811061069557610695610a7f565b60200101906001600160f81b031916908160001a90535060006106b9846002610a48565b6106c4906001610a67565b90505b600181111561073c576f181899199a1a9b1b9c1cb0b131b232b360811b85600f16601081106106f8576106f8610a7f565b1a60f81b82828151811061070e5761070e610a7f565b60200101906001600160f81b031916908160001a90535060049490941c9361073581610a95565b90506106c7565b50831561078c57604051636381e58960e11b815260206004820181905260248201527f537472696e67733a20686578206c656e67746820696e73756666696369656e746044820152606401610218565b9392505050565b80356001600160a01b038116811461046357600080fd5b600080604083850312156107bd57600080fd5b823591506107cd60208401610793565b90509250929050565b63b95aa35560e01b600052604160045260246000fd5b600082601f8301126107fd57600080fd5b813567ffffffffffffffff80821115610818576108186107d6565b604051601f8301601f19908116603f01168101908282118183101715610840576108406107d6565b8160405283815286602085880101111561085957600080fd5b836020870160208301376000602085830101528094505050505092915050565b60006020828403121561088b57600080fd5b813567ffffffffffffffff8111156108a257600080fd5b6108ae848285016107ec565b949350505050565b600080604083850312156108c957600080fd5b823567ffffffffffffffff8111156108e057600080fd5b6108ec858286016107ec565b9250506107cd60208401610793565b60006020828403121561090d57600080fd5b81356001600160e01b03198116811461078c57600080fd5b60006020828403121561093757600080fd5b5035919050565b60005b83811015610959578181015183820152602001610941565b83811115610968576000848401525b50505050565b6000825161098081846020870161093e565b9190910192915050565b7f416363657373436f6e74726f6c3a206163636f756e74200000000000000000008152600083516109c281601785016020880161093e565b7001034b99036b4b9b9b4b733903937b6329607d1b60179184019182015283516109f381602884016020880161093e565b01602801949350505050565b6020815260008251806020840152610a1e81604085016020870161093e565b601f01601f19169190910160400192915050565b63b95aa35560e01b600052601160045260246000fd5b6000816000190483118215151615610a6257610a62610a32565b500290565b60008219821115610a7a57610a7a610a32565b500190565b63b95aa35560e01b600052603260045260246000fd5b600081610aa457610aa4610a32565b50600019019056fecda5c054d055f48a2866e1a28d72f8145e422f1db1872bc4c9fe2888702c9f8da264697066735822122080cf52fdda1ae9c246ec9f137eb267efcf30bb6d0ead77f64f646dec577620e664736f6c634300080b0033"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint8\",\"name\":\"version\",\"type\":\"uint8\"}],\"name\":\"Initialized\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"previousAdminRole\",\"type\":\"bytes32\"},{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"newAdminRole\",\"type\":\"bytes32\"}],\"name\":\"RoleAdminChanged\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"sender\",\"type\":\"address\"}],\"name\":\"RoleGranted\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"sender\",\"type\":\"address\"}],\"name\":\"RoleRevoked\",\"type\":\"event\"},{\"conflictFields\":[{\"kind\":5}],\"inputs\":[],\"name\":\"DEFAULT_ADMIN_ROLE\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"selector\":[2719481311,456278200],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":5}],\"inputs\":[],\"name\":\"USERMANAGE_ROLE\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"selector\":[852968231,3061267160],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":3,\"slot\":101,\"value\":[0]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"}],\"name\":\"getRoleAdmin\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"selector\":[613063843,4224970943],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":2,\"slot\":101,\"value\":[0]},{\"kind\":3,\"slot\":101,\"value\":[0]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"grantRole\",\"outputs\":[],\"selector\":[791671133,1416757515],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":2,\"slot\":101,\"value\":[0]},{\"kind\":3,\"slot\":151,\"value\":[0]}],\"inputs\":[{\"internalType\":\"string\",\"name\":\"bid\",\"type\":\"string\"},{\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"}],\"name\":\"grantUserManagePermission\",\"outputs\":[],\"selector\":[1037211317,3070963644],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":4,\"slot\":101,\"value\":[34,245,156,47,2,237,186,229,196,33,36,127,2,159,227,17,44,90,133,247,11,74,72,245,217,59,34,173,169,234,119,46]}],\"inputs\":[{\"internalType\":\"string\",\"name\":\"bid\",\"type\":\"string\"}],\"name\":\"hasDAUserManageRole\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"selector\":[1195994301,4114364435],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":3,\"slot\":101,\"value\":[0]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"hasRole\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"selector\":[2446411860,3734140252],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":3,\"slot\":101,\"value\":[0]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"renounceRole\",\"outputs\":[],\"selector\":[911641278,399928776],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":2,\"slot\":101,\"value\":[0]},{\"kind\":3,\"slot\":101,\"value\":[0]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"revokeRole\",\"outputs\":[],\"selector\":[3578229791,4245284822],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":2,\"slot\":101,\"value\":[0]},{\"kind\":3,\"slot\":151,\"value\":[0]}],\"inputs\":[{\"internalType\":\"string\",\"name\":\"bid\",\"type\":\"string\"}],\"name\":\"revokeUserManagePermission\",\"outputs\":[],\"selector\":[4233016450,2768200898],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":5}],\"inputs\":[{\"internalType\":\"bytes4\",\"name\":\"interfaceId\",\"type\":\"bytes4\"}],\"name\":\"supportsInterface\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"selector\":[33540519,3934173080],\"stateMutability\":\"view\",\"type\":\"function\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_DEFAULT_ADMIN_ROLE = "DEFAULT_ADMIN_ROLE";

    public static final String FUNC_USERMANAGE_ROLE = "USERMANAGE_ROLE";

    public static final String FUNC_GETROLEADMIN = "getRoleAdmin";

    public static final String FUNC_GRANTROLE = "grantRole";

    public static final String FUNC_GRANTUSERMANAGEPERMISSION = "grantUserManagePermission";

    public static final String FUNC_HASDAUSERMANAGEROLE = "hasDAUserManageRole";

    public static final String FUNC_HASROLE = "hasRole";

    public static final String FUNC_RENOUNCEROLE = "renounceRole";

    public static final String FUNC_REVOKEROLE = "revokeRole";

    public static final String FUNC_REVOKEUSERMANAGEPERMISSION = "revokeUserManagePermission";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final Event INITIALIZED_EVENT = new Event("Initialized", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
    ;

    public static final Event ROLEADMINCHANGED_EVENT = new Event("RoleAdminChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event ROLEGRANTED_EVENT = new Event("RoleGranted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event ROLEREVOKED_EVENT = new Event("RoleRevoked", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    protected DAAccessController(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static String getABI() {
        return ABI;
    }

    public List<InitializedEventResponse> getInitializedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(INITIALIZED_EVENT, transactionReceipt);
        ArrayList<InitializedEventResponse> responses = new ArrayList<InitializedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            InitializedEventResponse typedResponse = new InitializedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.version = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public List<RoleAdminChangedEventResponse> getRoleAdminChangedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ROLEADMINCHANGED_EVENT, transactionReceipt);
        ArrayList<RoleAdminChangedEventResponse> responses = new ArrayList<RoleAdminChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RoleAdminChangedEventResponse typedResponse = new RoleAdminChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.previousAdminRole = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.newAdminRole = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public List<RoleGrantedEventResponse> getRoleGrantedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ROLEGRANTED_EVENT, transactionReceipt);
        ArrayList<RoleGrantedEventResponse> responses = new ArrayList<RoleGrantedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RoleGrantedEventResponse typedResponse = new RoleGrantedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public List<RoleRevokedEventResponse> getRoleRevokedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ROLEREVOKED_EVENT, transactionReceipt);
        ArrayList<RoleRevokedEventResponse> responses = new ArrayList<RoleRevokedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RoleRevokedEventResponse typedResponse = new RoleRevokedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.account = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public byte[] DEFAULT_ADMIN_ROLE() throws ContractException {
        final Function function = new Function(FUNC_DEFAULT_ADMIN_ROLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeCallWithSingleValueReturn(function, byte[].class);
    }

    public byte[] USERMANAGE_ROLE() throws ContractException {
        final Function function = new Function(FUNC_USERMANAGE_ROLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeCallWithSingleValueReturn(function, byte[].class);
    }

    public byte[] getRoleAdmin(byte[] role) throws ContractException {
        final Function function = new Function(FUNC_GETROLEADMIN, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(role)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeCallWithSingleValueReturn(function, byte[].class);
    }

    public TransactionReceipt grantRole(byte[] role, String account) {
        final Function function = new Function(
                FUNC_GRANTROLE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(role), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForGrantRole(byte[] role, String account) {
        final Function function = new Function(
                FUNC_GRANTROLE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(role), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public String grantRole(byte[] role, String account, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_GRANTROLE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(role), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple2<byte[], String> getGrantRoleInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_GRANTROLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<byte[], String>(

                (byte[]) results.get(0).getValue(), 
                (String) results.get(1).getValue()
                );
    }

    public TransactionReceipt grantUserManagePermission(String bid, String user) {
        final Function function = new Function(
                FUNC_GRANTUSERMANAGEPERMISSION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(user)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForGrantUserManagePermission(String bid, String user) {
        final Function function = new Function(
                FUNC_GRANTUSERMANAGEPERMISSION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(user)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public String grantUserManagePermission(String bid, String user, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_GRANTUSERMANAGEPERMISSION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(user)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple2<String, String> getGrantUserManagePermissionInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_GRANTUSERMANAGEPERMISSION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, String>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue()
                );
    }

    public Boolean hasDAUserManageRole(String bid) throws ContractException {
        final Function function = new Function(FUNC_HASDAUSERMANAGEROLE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public Boolean hasRole(byte[] role, String account) throws ContractException {
        final Function function = new Function(FUNC_HASROLE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(role), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public TransactionReceipt renounceRole(byte[] role, String account) {
        final Function function = new Function(
                FUNC_RENOUNCEROLE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(role), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForRenounceRole(byte[] role, String account) {
        final Function function = new Function(
                FUNC_RENOUNCEROLE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(role), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public String renounceRole(byte[] role, String account, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_RENOUNCEROLE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(role), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple2<byte[], String> getRenounceRoleInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_RENOUNCEROLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<byte[], String>(

                (byte[]) results.get(0).getValue(), 
                (String) results.get(1).getValue()
                );
    }

    public TransactionReceipt revokeRole(byte[] role, String account) {
        final Function function = new Function(
                FUNC_REVOKEROLE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(role), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForRevokeRole(byte[] role, String account) {
        final Function function = new Function(
                FUNC_REVOKEROLE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(role), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public String revokeRole(byte[] role, String account, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_REVOKEROLE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(role), 
                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple2<byte[], String> getRevokeRoleInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_REVOKEROLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<byte[], String>(

                (byte[]) results.get(0).getValue(), 
                (String) results.get(1).getValue()
                );
    }

    public TransactionReceipt revokeUserManagePermission(String bid) {
        final Function function = new Function(
                FUNC_REVOKEUSERMANAGEPERMISSION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForRevokeUserManagePermission(String bid) {
        final Function function = new Function(
                FUNC_REVOKEUSERMANAGEPERMISSION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return createSignedTransaction(function);
    }

    public String revokeUserManagePermission(String bid, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_REVOKEUSERMANAGEPERMISSION, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid)), 
                Collections.<TypeReference<?>>emptyList(), 0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple1<String> getRevokeUserManagePermissionInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_REVOKEUSERMANAGEPERMISSION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Boolean supportsInterface(byte[] interfaceId) throws ContractException {
        final Function function = new Function(FUNC_SUPPORTSINTERFACE, 
                Arrays.<Type>asList(new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes4(interfaceId)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public static DAAccessController load(String contractAddress, Client client,
            CryptoKeyPair credential) {
        return new DAAccessController(contractAddress, client, credential);
    }

    public static DAAccessController deploy(Client client, CryptoKeyPair credential) throws
            ContractException {
        return deploy(DAAccessController.class, client, credential, getBinary(client.getCryptoSuite()), getABI(), null, null);
    }

    public static class InitializedEventResponse {
        public TransactionReceipt.Logs log;

        public BigInteger version;
    }

    public static class RoleAdminChangedEventResponse {
        public TransactionReceipt.Logs log;

        public byte[] role;

        public byte[] previousAdminRole;

        public byte[] newAdminRole;
    }

    public static class RoleGrantedEventResponse {
        public TransactionReceipt.Logs log;

        public byte[] role;

        public String account;

        public String sender;
    }

    public static class RoleRevokedEventResponse {
        public TransactionReceipt.Logs log;

        public byte[] role;

        public String account;

        public String sender;
    }
}
