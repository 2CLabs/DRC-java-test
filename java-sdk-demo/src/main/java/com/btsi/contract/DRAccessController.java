package com.btsi.contract;

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
public class DRAccessController extends Contract {
    public static final String[] BINARY_ARRAY = {
        "608060405234801561001057600080fd5b50610b48806100206000396000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c80633dd296b5116100715780633dd296b5146101445780634cea35a61461015757806391d148541461016a578063a217fddf1461017d578063d547741f14610185578063fc4eb4821461019857600080fd5b806301ffc9a7146100ae578063248a9ca3146100d65780632f2ff15d1461010757806332d743271461011c57806336568abe14610131575b600080fd5b6100c16100bc3660046107d9565b6101ab565b60405190151581526020015b60405180910390f35b6100f96100e4366004610803565b60009081526065602052604090206001015490565b6040519081526020016100cd565b61011a610115366004610833565b6101e2565b005b6100f9600080516020610af383398151915281565b61011a61013f366004610833565b61020c565b61011a610152366004610902565b61028f565b6100c1610165366004610947565b610331565b6100c1610178366004610833565b6103b8565b6100f9600081565b61011a610193366004610833565b6103e3565b61011a6101a6366004610947565b610408565b60006001600160e01b03198216637965db0b60e01b14806101dc57506301ffc9a760e01b6001600160e01b03198316145b92915050565b6000828152606560205260409020600101546101fd816104d1565b61020783836104de565b505050565b6001600160a01b03811633146102815760405162461bcd60e51b815260206004820152602f60248201527f416363657373436f6e74726f6c3a2063616e206f6e6c792072656e6f756e636560448201526e103937b632b9903337b91039b2b63360891b60648201526084015b60405180910390fd5b61028b8282610564565b5050565b600061029a816104d1565b60006001600160a01b03166097846040516102b591906109b4565b908152604051908190036020019020546001600160a01b03161415610207576102ec600080516020610af3833981519152836104de565b816097846040516102fd91906109b4565b90815260405190819003602001902080546001600160a01b03929092166001600160a01b0319909216919091179055505050565b6000806001600160a01b031660978360405161034d91906109b4565b908152604051908190036020019020546001600160a01b0316146103ab576101dc600080516020610af383398151915260978460405161038d91906109b4565b908152604051908190036020019020546001600160a01b03166103b8565b506000919050565b919050565b60009182526065602090815260408084206001600160a01b0393909316845291905290205460ff1690565b6000828152606560205260409020600101546103fe816104d1565b6102078383610564565b6000610413816104d1565b60006001600160a01b031660978360405161042e91906109b4565b908152604051908190036020019020546001600160a01b03161461028b5761048c600080516020610af383398151915260978460405161046e91906109b4565b908152604051908190036020019020546001600160a01b0316610564565b600060978360405161049e91906109b4565b90815260405190819003602001902080546001600160a01b03929092166001600160a01b03199092169190911790555050565b6104db81336105cb565b50565b6104e882826103b8565b61028b5760008281526065602090815260408083206001600160a01b03851684529091529020805460ff191660011790556105203390565b6001600160a01b0316816001600160a01b0316837f2f8788117e7eff1d82e926ec794901d17c78024a50270940304540a733656f0d60405160405180910390a45050565b61056e82826103b8565b1561028b5760008281526065602090815260408083206001600160a01b0385168085529252808320805460ff1916905551339285917ff6391f5c32d9c69d2a47ea670b442974b53935d1edc7fd64eb21e047a839171b9190a45050565b6105d582826103b8565b61028b576105e281610624565b6105ed836020610636565b6040516020016105fe9291906109d0565b60408051601f198184030181529082905262461bcd60e51b825261027891600401610a45565b60606101dc6001600160a01b03831660145b60606000610645836002610a8e565b610650906002610aad565b67ffffffffffffffff8111156106685761066861085f565b6040519080825280601f01601f191660200182016040528015610692576020820181803683370190505b509050600360fc1b816000815181106106ad576106ad610ac5565b60200101906001600160f81b031916908160001a905350600f60fb1b816001815181106106dc576106dc610ac5565b60200101906001600160f81b031916908160001a9053506000610700846002610a8e565b61070b906001610aad565b90505b6001811115610783576f181899199a1a9b1b9c1cb0b131b232b360811b85600f166010811061073f5761073f610ac5565b1a60f81b82828151811061075557610755610ac5565b60200101906001600160f81b031916908160001a90535060049490941c9361077c81610adb565b905061070e565b5083156107d25760405162461bcd60e51b815260206004820181905260248201527f537472696e67733a20686578206c656e67746820696e73756666696369656e746044820152606401610278565b9392505050565b6000602082840312156107eb57600080fd5b81356001600160e01b0319811681146107d257600080fd5b60006020828403121561081557600080fd5b5035919050565b80356001600160a01b03811681146103b357600080fd5b6000806040838503121561084657600080fd5b823591506108566020840161081c565b90509250929050565b634e487b7160e01b600052604160045260246000fd5b600082601f83011261088657600080fd5b813567ffffffffffffffff808211156108a1576108a161085f565b604051601f8301601f19908116603f011681019082821181831017156108c9576108c961085f565b816040528381528660208588010111156108e257600080fd5b836020870160208301376000602085830101528094505050505092915050565b6000806040838503121561091557600080fd5b823567ffffffffffffffff81111561092c57600080fd5b61093885828601610875565b9250506108566020840161081c565b60006020828403121561095957600080fd5b813567ffffffffffffffff81111561097057600080fd5b61097c84828501610875565b949350505050565b60005b8381101561099f578181015183820152602001610987565b838111156109ae576000848401525b50505050565b600082516109c6818460208701610984565b9190910192915050565b7f416363657373436f6e74726f6c3a206163636f756e7420000000000000000000815260008351610a08816017850160208801610984565b7001034b99036b4b9b9b4b733903937b6329607d1b6017918401918201528351610a39816028840160208801610984565b01602801949350505050565b6020815260008251806020840152610a64816040850160208701610984565b601f01601f19169190910160400192915050565b634e487b7160e01b600052601160045260246000fd5b6000816000190483118215151615610aa857610aa8610a78565b500290565b60008219821115610ac057610ac0610a78565b500190565b634e487b7160e01b600052603260045260246000fd5b600081610aea57610aea610a78565b50600019019056fe22f59c2f02edbae5c421247f029fe3112c5a85f70b4a48f5d93b22ada9ea772ea264697066735822122099ed9f12dbf56a9767e55fab778326ad0dc2beb88f7ad2d57fdad074631ed81e64736f6c634300080b0033"
    };

    public static final String BINARY =
            org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {
        "608060405234801561001057600080fd5b50610b4b806100206000396000f3fe608060405234801561001057600080fd5b50600436106100a95760003560e01c8063b6773ad811610071578063b6773ad814610127578063b70b2fbc1461013c578063de92755c1461014f578063ea7eb79814610162578063fbd3f0bf14610175578063fd09e7d61461019857600080fd5b806317d66dc8146100ae5780631b3240b8146100c35780635472010b146100de578063a4ff64c2146100f1578063b5aed06f14610104575b600080fd5b6100c16100bc3660046107f3565b6101ab565b005b6100cb600081565b6040519081526020015b60405180910390f35b6100c16100ec3660046107f3565b61022f565b6100c16100ff3660046108c2565b610259565b6101176101123660046108c2565b610322565b60405190151581526020016100d5565b6100cb600080516020610af683398151915281565b6100c161014a3660046108ff565b6103af565b61011761015d3660046107f3565b610451565b610117610170366004610944565b61047c565b6100cb61018336600461096e565b60009081526065602052604090206001015490565b6100c16101a63660046107f3565b6104b1565b6001600160a01b038116331461022157604051636381e58960e11b815260206004820152602f60248201527f416363657373436f6e74726f6c3a2063616e206f6e6c792072656e6f756e636560448201526e103937b632b9903337b91039b2b63360891b60648201526084015b60405180910390fd5b61022b82826104d2565b5050565b60008281526065602052604090206001015461024a81610539565b6102548383610546565b505050565b600061026481610539565b60006001600160a01b031660978360405161027f91906109b7565b908152604051908190036020019020546001600160a01b03161461022b576102dd600080516020610af68339815191526097846040516102bf91906109b7565b908152604051908190036020019020546001600160a01b03166104d2565b60006097836040516102ef91906109b7565b90815260405190819003602001902080546001600160a01b03929092166001600160a01b03199092169190911790555050565b6000806001600160a01b031660978360405161033e91906109b7565b908152604051908190036020019020546001600160a01b0316146103a25761039c600080516020610af683398151915260978460405161037e91906109b7565b908152604051908190036020019020546001600160a01b0316610451565b92915050565b506000919050565b919050565b60006103ba81610539565b60006001600160a01b03166097846040516103d591906109b7565b908152604051908190036020019020546001600160a01b031614156102545761040c600080516020610af683398151915283610546565b8160978460405161041d91906109b7565b90815260405190819003602001902080546001600160a01b03929092166001600160a01b0319909216919091179055505050565b60009182526065602090815260408084206001600160a01b0393909316845291905290205460ff1690565b60006001600160e01b03198216634df6077b60e11b148061039c5750631d4fd6f360e31b6001600160e01b031983161461039c565b6000828152606560205260409020600101546104cc81610539565b61025483835b6104dc8282610451565b1561022b5760008281526065602090815260408083206001600160a01b0385168085529252808320805460ff1916905551339285917fddf24a0d777ab37f0cd0acf6e2b0a75570d42c6500405e008f7f9a61836cf2a39190a45050565b61054381336105cc565b50565b6105508282610451565b61022b5760008281526065602090815260408083206001600160a01b03851684529091529020805460ff191660011790556105883390565b6001600160a01b0316816001600160a01b0316837f3a8c19c5eddb5ca7fdd091f00e2b3978b86344c928af4d2d7f6733572cc06b4c60405160405180910390a45050565b6105d68282610451565b61022b576105e381610626565b6105ee836020610638565b6040516020016105ff9291906109d3565b60408051601f1981840301815290829052636381e58960e11b825261021891600401610a48565b606061039c6001600160a01b03831660145b60606000610647836002610a91565b610652906002610ab0565b67ffffffffffffffff81111561066a5761066a61081f565b6040519080825280601f01601f191660200182016040528015610694576020820181803683370190505b509050600360fc1b816000815181106106af576106af610ac8565b60200101906001600160f81b031916908160001a905350600f60fb1b816001815181106106de576106de610ac8565b60200101906001600160f81b031916908160001a9053506000610702846002610a91565b61070d906001610ab0565b90505b6001811115610785576f181899199a1a9b1b9c1cb0b131b232b360811b85600f166010811061074157610741610ac8565b1a60f81b82828151811061075757610757610ac8565b60200101906001600160f81b031916908160001a90535060049490941c9361077e81610ade565b9050610710565b5083156107d557604051636381e58960e11b815260206004820181905260248201527f537472696e67733a20686578206c656e67746820696e73756666696369656e746044820152606401610218565b9392505050565b80356001600160a01b03811681146103aa57600080fd5b6000806040838503121561080657600080fd5b82359150610816602084016107dc565b90509250929050565b63b95aa35560e01b600052604160045260246000fd5b600082601f83011261084657600080fd5b813567ffffffffffffffff808211156108615761086161081f565b604051601f8301601f19908116603f011681019082821181831017156108895761088961081f565b816040528381528660208588010111156108a257600080fd5b836020870160208301376000602085830101528094505050505092915050565b6000602082840312156108d457600080fd5b813567ffffffffffffffff8111156108eb57600080fd5b6108f784828501610835565b949350505050565b6000806040838503121561091257600080fd5b823567ffffffffffffffff81111561092957600080fd5b61093585828601610835565b925050610816602084016107dc565b60006020828403121561095657600080fd5b81356001600160e01b0319811681146107d557600080fd5b60006020828403121561098057600080fd5b5035919050565b60005b838110156109a257818101518382015260200161098a565b838111156109b1576000848401525b50505050565b600082516109c9818460208701610987565b9190910192915050565b7f416363657373436f6e74726f6c3a206163636f756e7420000000000000000000815260008351610a0b816017850160208801610987565b7001034b99036b4b9b9b4b733903937b6329607d1b6017918401918201528351610a3c816028840160208801610987565b01602801949350505050565b6020815260008251806020840152610a67816040850160208701610987565b601f01601f19169190910160400192915050565b63b95aa35560e01b600052601160045260246000fd5b6000816000190483118215151615610aab57610aab610a7b565b500290565b60008219821115610ac357610ac3610a7b565b500190565b63b95aa35560e01b600052603260045260246000fd5b600081610aed57610aed610a7b565b50600019019056fecda5c054d055f48a2866e1a28d72f8145e422f1db1872bc4c9fe2888702c9f8da2646970667358221220cb9f0a719a830b662cb25650623d178d47ecc8b6f0975744360869fb23b104ca64736f6c634300080b0033"
    };

    public static final String SM_BINARY =
            org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {
        "[{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint8\",\"name\":\"version\",\"type\":\"uint8\"}],\"name\":\"Initialized\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"previousAdminRole\",\"type\":\"bytes32\"},{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"newAdminRole\",\"type\":\"bytes32\"}],\"name\":\"RoleAdminChanged\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"sender\",\"type\":\"address\"}],\"name\":\"RoleGranted\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"sender\",\"type\":\"address\"}],\"name\":\"RoleRevoked\",\"type\":\"event\"},{\"conflictFields\":[{\"kind\":5}],\"inputs\":[],\"name\":\"DEFAULT_ADMIN_ROLE\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"selector\":[2719481311,456278200],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":5}],\"inputs\":[],\"name\":\"USERMANAGE_ROLE\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"selector\":[852968231,3061267160],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":3,\"slot\":101,\"value\":[0]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"}],\"name\":\"getRoleAdmin\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"selector\":[613063843,4224970943],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":2,\"slot\":101,\"value\":[0]},{\"kind\":3,\"slot\":101,\"value\":[0]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"grantRole\",\"outputs\":[],\"selector\":[791671133,1416757515],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":2,\"slot\":101,\"value\":[0]},{\"kind\":3,\"slot\":151,\"value\":[0]}],\"inputs\":[{\"internalType\":\"string\",\"name\":\"bid\",\"type\":\"string\"},{\"internalType\":\"address\",\"name\":\"user\",\"type\":\"address\"}],\"name\":\"grantUserManagePermission\",\"outputs\":[],\"selector\":[1037211317,3070963644],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":3,\"slot\":101,\"value\":[0]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"hasRole\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"selector\":[2446411860,3734140252],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":4,\"slot\":101,\"value\":[34,245,156,47,2,237,186,229,196,33,36,127,2,159,227,17,44,90,133,247,11,74,72,245,217,59,34,173,169,234,119,46]}],\"inputs\":[{\"internalType\":\"string\",\"name\":\"bid\",\"type\":\"string\"}],\"name\":\"hasUserManageRole\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"selector\":[1290417574,3048132719],\"stateMutability\":\"view\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":3,\"slot\":101,\"value\":[0]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"renounceRole\",\"outputs\":[],\"selector\":[911641278,399928776],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":2,\"slot\":101,\"value\":[0]},{\"kind\":3,\"slot\":101,\"value\":[0]}],\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"role\",\"type\":\"bytes32\"},{\"internalType\":\"address\",\"name\":\"account\",\"type\":\"address\"}],\"name\":\"revokeRole\",\"outputs\":[],\"selector\":[3578229791,4245284822],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":0},{\"kind\":2,\"slot\":101,\"value\":[0]},{\"kind\":3,\"slot\":151,\"value\":[0]}],\"inputs\":[{\"internalType\":\"string\",\"name\":\"bid\",\"type\":\"string\"}],\"name\":\"revokeUserManagePermission\",\"outputs\":[],\"selector\":[4233016450,2768200898],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"conflictFields\":[{\"kind\":5}],\"inputs\":[{\"internalType\":\"bytes4\",\"name\":\"interfaceId\",\"type\":\"bytes4\"}],\"name\":\"supportsInterface\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"selector\":[33540519,3934173080],\"stateMutability\":\"view\",\"type\":\"function\"}]"
    };

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_DEFAULT_ADMIN_ROLE = "DEFAULT_ADMIN_ROLE";

    public static final String FUNC_USERMANAGE_ROLE = "USERMANAGE_ROLE";

    public static final String FUNC_GETROLEADMIN = "getRoleAdmin";

    public static final String FUNC_GRANTROLE = "grantRole";

    public static final String FUNC_GRANTUSERMANAGEPERMISSION = "grantUserManagePermission";

    public static final String FUNC_HASROLE = "hasRole";

    public static final String FUNC_HASUSERMANAGEROLE = "hasUserManageRole";

    public static final String FUNC_RENOUNCEROLE = "renounceRole";

    public static final String FUNC_REVOKEROLE = "revokeRole";

    public static final String FUNC_REVOKEUSERMANAGEPERMISSION = "revokeUserManagePermission";

    public static final String FUNC_SUPPORTSINTERFACE = "supportsInterface";

    public static final Event INITIALIZED_EVENT =
            new Event(
                    "Initialized", Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));;

    public static final Event ROLEADMINCHANGED_EVENT =
            new Event(
                    "RoleAdminChanged",
                    Arrays.<TypeReference<?>>asList(
                            new TypeReference<Bytes32>(true) {},
                            new TypeReference<Bytes32>(true) {},
                            new TypeReference<Bytes32>(true) {}));;

    public static final Event ROLEGRANTED_EVENT =
            new Event(
                    "RoleGranted",
                    Arrays.<TypeReference<?>>asList(
                            new TypeReference<Bytes32>(true) {},
                            new TypeReference<Address>(true) {},
                            new TypeReference<Address>(true) {}));;

    public static final Event ROLEREVOKED_EVENT =
            new Event(
                    "RoleRevoked",
                    Arrays.<TypeReference<?>>asList(
                            new TypeReference<Bytes32>(true) {},
                            new TypeReference<Address>(true) {},
                            new TypeReference<Address>(true) {}));;

    protected DRAccessController(String contractAddress, Client client, CryptoKeyPair credential) {
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
        List<Contract.EventValuesWithLog> valueList =
                extractEventParametersWithLog(INITIALIZED_EVENT, transactionReceipt);
        ArrayList<InitializedEventResponse> responses =
                new ArrayList<InitializedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            InitializedEventResponse typedResponse = new InitializedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.version =
                    (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public List<RoleAdminChangedEventResponse> getRoleAdminChangedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList =
                extractEventParametersWithLog(ROLEADMINCHANGED_EVENT, transactionReceipt);
        ArrayList<RoleAdminChangedEventResponse> responses =
                new ArrayList<RoleAdminChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RoleAdminChangedEventResponse typedResponse = new RoleAdminChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.role = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.previousAdminRole =
                    (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.newAdminRole = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public List<RoleGrantedEventResponse> getRoleGrantedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList =
                extractEventParametersWithLog(ROLEGRANTED_EVENT, transactionReceipt);
        ArrayList<RoleGrantedEventResponse> responses =
                new ArrayList<RoleGrantedEventResponse>(valueList.size());
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
        List<Contract.EventValuesWithLog> valueList =
                extractEventParametersWithLog(ROLEREVOKED_EVENT, transactionReceipt);
        ArrayList<RoleRevokedEventResponse> responses =
                new ArrayList<RoleRevokedEventResponse>(valueList.size());
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
        final Function function =
                new Function(
                        FUNC_DEFAULT_ADMIN_ROLE,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeCallWithSingleValueReturn(function, byte[].class);
    }

    public byte[] USERMANAGE_ROLE() throws ContractException {
        final Function function =
                new Function(
                        FUNC_USERMANAGE_ROLE,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeCallWithSingleValueReturn(function, byte[].class);
    }

    public byte[] getRoleAdmin(byte[] role) throws ContractException {
        final Function function =
                new Function(
                        FUNC_GETROLEADMIN,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(role)),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeCallWithSingleValueReturn(function, byte[].class);
    }

    public TransactionReceipt grantRole(byte[] role, String account) {
        final Function function =
                new Function(
                        FUNC_GRANTROLE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(role),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForGrantRole(byte[] role, String account) {
        final Function function =
                new Function(
                        FUNC_GRANTROLE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(role),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String grantRole(byte[] role, String account, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_GRANTROLE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(role),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple2<byte[], String> getGrantRoleInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_GRANTROLE,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<byte[], String>(
                (byte[]) results.get(0).getValue(), (String) results.get(1).getValue());
    }

    public TransactionReceipt grantUserManagePermission(String bid, String user) {
        final Function function =
                new Function(
                        FUNC_GRANTUSERMANAGEPERMISSION,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(user)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForGrantUserManagePermission(String bid, String user) {
        final Function function =
                new Function(
                        FUNC_GRANTUSERMANAGEPERMISSION,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(user)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String grantUserManagePermission(String bid, String user, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_GRANTUSERMANAGEPERMISSION,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(user)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple2<String, String> getGrantUserManagePermissionInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_GRANTUSERMANAGEPERMISSION,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Utf8String>() {},
                                new TypeReference<Address>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, String>(
                (String) results.get(0).getValue(), (String) results.get(1).getValue());
    }

    public Boolean hasRole(byte[] role, String account) throws ContractException {
        final Function function =
                new Function(
                        FUNC_HASROLE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(role),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public Boolean hasUserManageRole(String bid) throws ContractException {
        final Function function =
                new Function(
                        FUNC_HASUSERMANAGEROLE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid)),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public TransactionReceipt renounceRole(byte[] role, String account) {
        final Function function =
                new Function(
                        FUNC_RENOUNCEROLE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(role),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForRenounceRole(byte[] role, String account) {
        final Function function =
                new Function(
                        FUNC_RENOUNCEROLE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(role),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String renounceRole(byte[] role, String account, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_RENOUNCEROLE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(role),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple2<byte[], String> getRenounceRoleInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_RENOUNCEROLE,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<byte[], String>(
                (byte[]) results.get(0).getValue(), (String) results.get(1).getValue());
    }

    public TransactionReceipt revokeRole(byte[] role, String account) {
        final Function function =
                new Function(
                        FUNC_REVOKEROLE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(role),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForRevokeRole(byte[] role, String account) {
        final Function function =
                new Function(
                        FUNC_REVOKEROLE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(role),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String revokeRole(byte[] role, String account, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_REVOKEROLE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes32(role),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(account)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple2<byte[], String> getRevokeRoleInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_REVOKEROLE,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(
                                new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<byte[], String>(
                (byte[]) results.get(0).getValue(), (String) results.get(1).getValue());
    }

    public TransactionReceipt revokeUserManagePermission(String bid) {
        final Function function =
                new Function(
                        FUNC_REVOKEUSERMANAGEPERMISSION,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return executeTransaction(function);
    }

    public String getSignedTransactionForRevokeUserManagePermission(String bid) {
        final Function function =
                new Function(
                        FUNC_REVOKEUSERMANAGEPERMISSION,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return createSignedTransaction(function);
    }

    public String revokeUserManagePermission(String bid, TransactionCallback callback) {
        final Function function =
                new Function(
                        FUNC_REVOKEUSERMANAGEPERMISSION,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String(bid)),
                        Collections.<TypeReference<?>>emptyList(),
                        0);
        return asyncExecuteTransaction(function, callback);
    }

    public Tuple1<String> getRevokeUserManagePermissionInput(
            TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function =
                new Function(
                        FUNC_REVOKEUSERMANAGEPERMISSION,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results =
                this.functionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>((String) results.get(0).getValue());
    }

    public Boolean supportsInterface(byte[] interfaceId) throws ContractException {
        final Function function =
                new Function(
                        FUNC_SUPPORTSINTERFACE,
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.generated.Bytes4(
                                        interfaceId)),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeCallWithSingleValueReturn(function, Boolean.class);
    }

    public static DRAccessController load(
            String contractAddress, Client client, CryptoKeyPair credential) {
        return new DRAccessController(contractAddress, client, credential);
    }

    public static DRAccessController deploy(Client client, CryptoKeyPair credential)
            throws ContractException {
        return deploy(
                DRAccessController.class,
                client,
                credential,
                getBinary(client.getCryptoSuite()),
                getABI(),
                null,
                null);
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
