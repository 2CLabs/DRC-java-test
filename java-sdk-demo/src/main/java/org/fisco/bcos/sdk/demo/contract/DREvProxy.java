package org.fisco.bcos.sdk.demo.contract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.abi.FunctionEncoder;
import org.fisco.bcos.sdk.v3.codec.datatypes.Address;
import org.fisco.bcos.sdk.v3.codec.datatypes.Event;
import org.fisco.bcos.sdk.v3.codec.datatypes.Type;
import org.fisco.bcos.sdk.v3.codec.datatypes.TypeReference;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class DREvProxy extends Contract {
    public static final String[] BINARY_ARRAY = {
        "60806040523480156200001157600080fd5b506040516200139e3803806200139e8339810160408190526200003491620004b1565b8282828281620000478282600062000061565b50620000559050826200009e565b505050505050620005e4565b6200006c83620000f9565b6000825111806200007a5750805b1562000099576200009783836200013b60201b6200009e1760201c565b505b505050565b7f7e644d79422f17c01e4894b5f4f588d331ebfa28653d42ae832dc59e38c9798f620000c96200016a565b604080516001600160a01b03928316815291841660208301520160405180910390a1620000f681620001a3565b50565b620001048162000258565b6040516001600160a01b038216907fbc7cd75a20ee27fd9adebab32041f755214dbc6bffa90cc0225b39da2e5c2d3b90600090a250565b606062000163838360405180606001604052806027815260200162001377602791396200030c565b9392505050565b6000620001946000805160206200135783398151915260001b6200038b60201b620000ca1760201c565b546001600160a01b0316919050565b6001600160a01b0381166200020e5760405162461bcd60e51b815260206004820152602660248201527f455243313936373a206e65772061646d696e20697320746865207a65726f206160448201526564647265737360d01b60648201526084015b60405180910390fd5b80620002376000805160206200135783398151915260001b6200038b60201b620000ca1760201c565b80546001600160a01b0319166001600160a01b039290921691909117905550565b6200026e816200038e60201b620000cd1760201c565b620002d25760405162461bcd60e51b815260206004820152602d60248201527f455243313936373a206e657720696d706c656d656e746174696f6e206973206e60448201526c1bdd08184818dbdb9d1c9858dd609a1b606482015260840162000205565b80620002377f360894a13ba1a3210667c828492db98dca3e2076cc3735a920a3ca505d382bbc60001b6200038b60201b620000ca1760201c565b6060600080856001600160a01b0316856040516200032b919062000591565b600060405180830381855af49150503d806000811462000368576040519150601f19603f3d011682016040523d82523d6000602084013e6200036d565b606091505b50909250905062000381868383876200039d565b9695505050505050565b90565b6001600160a01b03163b151590565b606083156200040e57825162000406576001600160a01b0385163b620004065760405162461bcd60e51b815260206004820152601d60248201527f416464726573733a2063616c6c20746f206e6f6e2d636f6e7472616374000000604482015260640162000205565b50816200041a565b6200041a838362000422565b949350505050565b815115620004335781518083602001fd5b8060405162461bcd60e51b8152600401620002059190620005af565b80516001600160a01b03811681146200046757600080fd5b919050565b634e487b7160e01b600052604160045260246000fd5b60005b838110156200049f57818101518382015260200162000485565b83811115620000975750506000910152565b600080600060608486031215620004c757600080fd5b620004d2846200044f565b9250620004e2602085016200044f565b60408501519092506001600160401b03808211156200050057600080fd5b818601915086601f8301126200051557600080fd5b8151818111156200052a576200052a6200046c565b604051601f8201601f19908116603f011681019083821181831017156200055557620005556200046c565b816040528281528960208487010111156200056f57600080fd5b6200058283602083016020880162000482565b80955050505050509250925092565b60008251620005a581846020870162000482565b9190910192915050565b6020815260008251806020840152620005d081604085016020870162000482565b601f01601f19169190910160400192915050565b610d6380620005f46000396000f3fe60806040523661001357610011610017565b005b6100115b61001f6100dc565b6001600160a01b0316336001600160a01b031614156100945760606001600160e01b03196000351663215c7ded60e01b8114156100685761005e61010a565b9150815160208301f35b6001600160e01b0319811662c0585960e21b14156100885761005e610174565b61009061034a565b5050565b61009c61034a565b565b60606100c38383604051806060016040528060278152602001610d07602791396104a5565b9392505050565b90565b6001600160a01b03163b151590565b7fb53127684a568b3173ae13b9f8a6016e243e63b6e8ee1178d6a717850b5d6103546001600160a01b031690565b606060008061011c366004818461094c565b81019061012991906109a8565b6001600160e01b03199190911660009081526020818152604080832080546001600160a01b0319166001600160a01b0390951694909417909355825190810190925281529392505050565b6060600080610186366004818461094c565b8101906101939190610abe565b91509150600082511180156101a9575060008151115b6102125760405162461bcd60e51b815260206004820152602f60248201527f73656c6563746f72732026206c6f676963416464726573736573206c656e677460448201526e0682073686f756c64206265203e203608c1b60648201526084015b60405180910390fd5b80518251146102895760405162461bcd60e51b815260206004820152603960248201527f73656c6563746f7273206c656e6774682073686f756c6420626520657175616c60448201527f20746f206c6f676963416464726573736573206c656e677468000000000000006064820152608401610209565b815160005b8163ffffffff168163ffffffff16101561033157828163ffffffff16815181106102ba576102ba610b7e565b6020026020010151600080868463ffffffff16815181106102dd576102dd610b7e565b6020908102919091018101516001600160e01b031916825281019190915260400160002080546001600160a01b0319166001600160a01b03929092169190911790558061032981610b94565b91505061028e565b5060405180602001604052806000815250935050505090565b6103526100dc565b6001600160a01b0316336001600160a01b0316141561049d5760606001600160e01b031960003516631b2ce7f360e11b8114156103985761039161051d565b9150610495565b6001600160e01b0319811663278f794360e11b14156103b957610391610574565b6001600160e01b031981166308f2839760e41b14156103da576103916105ba565b6001600160e01b031981166303e1469160e61b14156103fb576103916105eb565b6001600160e01b03198116635c60da1b60e01b141561041c5761039161062b565b60405162461bcd60e51b815260206004820152604260248201527f5472616e73706172656e745570677261646561626c6550726f78793a2061646d60448201527f696e2063616e6e6f742066616c6c6261636b20746f2070726f78792074617267606482015261195d60f21b608482015260a401610209565b815160208301f35b61009c61063f565b6060600080856001600160a01b0316856040516104c29190610bf2565b600060405180830381855af49150503d80600081146104fd576040519150601f19603f3d011682016040523d82523d6000602084013e610502565b606091505b50915091506105138683838761064f565b9695505050505050565b60606105276106cd565b6000610536366004818461094c565b8101906105439190610c0e565b9050610560816040518060200160405280600081525060006106d8565b505060408051602081019091526000815290565b6060600080610586366004818461094c565b8101906105939190610c2b565b915091506105a3828260016106d8565b604051806020016040528060008152509250505090565b60606105c46106cd565b60006105d3366004818461094c565b8101906105e09190610c0e565b905061056081610704565b60606105f56106cd565b60006105ff6100dc565b604080516001600160a01b03831660208201529192500160405160208183030381529060405291505090565b60606106356106cd565b60006105ff61075b565b61009c61064a61075b565b610781565b606083156106bb5782516106b4576001600160a01b0385163b6106b45760405162461bcd60e51b815260206004820152601d60248201527f416464726573733a2063616c6c20746f206e6f6e2d636f6e74726163740000006044820152606401610209565b50816106c5565b6106c583836107a5565b949350505050565b341561009c57600080fd5b6106e1836107cf565b6000825111806106ee5750805b156106ff576106fd838361009e565b505b505050565b7f7e644d79422f17c01e4894b5f4f588d331ebfa28653d42ae832dc59e38c9798f61072d6100dc565b604080516001600160a01b03928316815291841660208301520160405180910390a16107588161080f565b50565b600080356001600160e01b0319168152602081905260409020546001600160a01b031690565b3660008037600080366000845af43d6000803e8080156107a0573d6000f35b3d6000fd5b8151156107b55781518083602001fd5b8060405162461bcd60e51b81526004016102099190610cd3565b6107d8816108b8565b6040516001600160a01b038216907fbc7cd75a20ee27fd9adebab32041f755214dbc6bffa90cc0225b39da2e5c2d3b90600090a250565b6001600160a01b0381166108745760405162461bcd60e51b815260206004820152602660248201527f455243313936373a206e65772061646d696e20697320746865207a65726f206160448201526564647265737360d01b6064820152608401610209565b807fb53127684a568b3173ae13b9f8a6016e243e63b6e8ee1178d6a717850b5d61035b80546001600160a01b0319166001600160a01b039290921691909117905550565b6001600160a01b0381163b6109255760405162461bcd60e51b815260206004820152602d60248201527f455243313936373a206e657720696d706c656d656e746174696f6e206973206e60448201526c1bdd08184818dbdb9d1c9858dd609a1b6064820152608401610209565b807f360894a13ba1a3210667c828492db98dca3e2076cc3735a920a3ca505d382bbc610897565b6000808585111561095c57600080fd5b8386111561096957600080fd5b5050820193919092039150565b80356001600160e01b03198116811461098e57600080fd5b919050565b6001600160a01b038116811461075857600080fd5b600080604083850312156109bb57600080fd5b6109c483610976565b915060208301356109d481610993565b809150509250929050565b634e487b7160e01b600052604160045260246000fd5b604051601f8201601f1916810167ffffffffffffffff",
        "81118282101715610a1e57610a1e6109df565b604052919050565b600067ffffffffffffffff821115610a4057610a406109df565b5060051b60200190565b600082601f830112610a5b57600080fd5b81356020610a70610a6b83610a26565b6109f5565b82815260059290921b84018101918181019086841115610a8f57600080fd5b8286015b84811015610ab3578035610aa681610993565b8352918301918301610a93565b509695505050505050565b60008060408385031215610ad157600080fd5b823567ffffffffffffffff80821115610ae957600080fd5b818501915085601f830112610afd57600080fd5b81356020610b0d610a6b83610a26565b82815260059290921b84018101918181019089841115610b2c57600080fd5b948201945b83861015610b5157610b4286610976565b82529482019490820190610b31565b96505086013592505080821115610b6757600080fd5b50610b7485828601610a4a565b9150509250929050565b634e487b7160e01b600052603260045260246000fd5b600063ffffffff80831681811415610bbc57634e487b7160e01b600052601160045260246000fd5b6001019392505050565b60005b83811015610be1578181015183820152602001610bc9565b838111156106fd5750506000910152565b60008251610c04818460208701610bc6565b9190910192915050565b600060208284031215610c2057600080fd5b81356100c381610993565b60008060408385031215610c3e57600080fd5b8235610c4981610993565b915060208381013567ffffffffffffffff80821115610c6757600080fd5b818601915086601f830112610c7b57600080fd5b813581811115610c8d57610c8d6109df565b610c9f601f8201601f191685016109f5565b91508082528784828501011115610cb557600080fd5b80848401858401376000848284010152508093505050509250929050565b6020815260008251806020840152610cf2816040850160208701610bc6565b601f01601f1916919091016040019291505056fe416464726573733a206c6f772d6c6576656c2064656c65676174652063616c6c206661696c6564a264697066735822122028237c4ccc33a8efc03f8422e6a1e00c1be81e10793af9c6b8283441559f91a964736f6c634300080b0033b53127684a568b3173ae13b9f8a6016e243e63b6e8ee1178d6a717850b5d6103416464726573733a206c6f772d6c6576656c2064656c65676174652063616c6c206661696c6564"
    };

    public static final String BINARY =
            org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {
        "60806040523480156200001157600080fd5b50604051620013a9380380620013a98339810160408190526200003491620004b5565b8282828281620000478282600062000061565b50620000559050826200009e565b505050505050620005e8565b6200006c83620000f9565b6000825111806200007a5750805b1562000099576200009783836200013b60201b6200009f1760201c565b505b505050565b7f7fff140a2090a44f84000f793b3e2c26fd84de1b51418e5734481ecebf9fa419620000c96200016a565b604080516001600160a01b03928316815291841660208301520160405180910390a1620000f681620001a3565b50565b620001048162000259565b6040516001600160a01b038216907f35c786da035a401a18e1a5dade4bdb4c07d82bdd5c15bef8561dbf76ad4db25d90600090a250565b606062000163838360405180606001604052806027815260200162001362602791396200030e565b9392505050565b6000620001946000805160206200138983398151915260001b6200038d60201b620000cb1760201c565b546001600160a01b0316919050565b6001600160a01b0381166200020f57604051636381e58960e11b815260206004820152602660248201527f455243313936373a206e65772061646d696e20697320746865207a65726f206160448201526564647265737360d01b60648201526084015b60405180910390fd5b80620002386000805160206200138983398151915260001b6200038d60201b620000cb1760201c565b80546001600160a01b0319166001600160a01b039290921691909117905550565b6200026f816200039060201b620000ce1760201c565b620002d457604051636381e58960e11b815260206004820152602d60248201527f455243313936373a206e657720696d706c656d656e746174696f6e206973206e60448201526c1bdd08184818dbdb9d1c9858dd609a1b606482015260840162000206565b80620002387f360894a13ba1a3210667c828492db98dca3e2076cc3735a920a3ca505d382bbc60001b6200038d60201b620000cb1760201c565b6060600080856001600160a01b0316856040516200032d919062000595565b600060405180830381855af49150503d80600081146200036a576040519150601f19603f3d011682016040523d82523d6000602084013e6200036f565b606091505b50909250905062000383868383876200039f565b9695505050505050565b90565b6001600160a01b03163b151590565b606083156200041157825162000409576001600160a01b0385163b6200040957604051636381e58960e11b815260206004820152601d60248201527f416464726573733a2063616c6c20746f206e6f6e2d636f6e7472616374000000604482015260640162000206565b50816200041d565b6200041d838362000425565b949350505050565b815115620004365781518083602001fd5b80604051636381e58960e11b8152600401620002069190620005b3565b80516001600160a01b03811681146200046b57600080fd5b919050565b63b95aa35560e01b600052604160045260246000fd5b60005b83811015620004a357818101518382015260200162000489565b83811115620000975750506000910152565b600080600060608486031215620004cb57600080fd5b620004d68462000453565b9250620004e66020850162000453565b60408501519092506001600160401b03808211156200050457600080fd5b818601915086601f8301126200051957600080fd5b8151818111156200052e576200052e62000470565b604051601f8201601f19908116603f0116810190838211818310171562000559576200055962000470565b816040528281528960208487010111156200057357600080fd5b6200058683602083016020880162000486565b80955050505050509250925092565b60008251620005a981846020870162000486565b9190910192915050565b6020815260008251806020840152620005d481604085016020870162000486565b601f01601f19169190910160400192915050565b610d6a80620005f86000396000f3fe60806040523661001357610011610017565b005b6100115b61001f6100dd565b6001600160a01b0316336001600160a01b031614156100955760606001600160e01b03196000351663b943fb0d60e01b8114156100685761005e61010b565b9150815160208301f35b6001600160e01b03198116636398de5560e11b14156100895761005e610175565b61009161034d565b5050565b61009d61034d565b565b60606100c48383604051806060016040528060278152602001610d0e602791396104a8565b9392505050565b90565b6001600160a01b03163b151590565b7fb53127684a568b3173ae13b9f8a6016e243e63b6e8ee1178d6a717850b5d6103546001600160a01b031690565b606060008061011d3660048184610953565b81019061012a91906109af565b6001600160e01b03199190911660009081526020818152604080832080546001600160a01b0319166001600160a01b0390951694909417909355825190810190925281529392505050565b60606000806101873660048184610953565b8101906101949190610ac5565b91509150600082511180156101aa575060008151115b61021457604051636381e58960e11b815260206004820152602f60248201527f73656c6563746f72732026206c6f676963416464726573736573206c656e677460448201526e0682073686f756c64206265203e203608c1b60648201526084015b60405180910390fd5b805182511461028c57604051636381e58960e11b815260206004820152603960248201527f73656c6563746f7273206c656e6774682073686f756c6420626520657175616c60448201527f20746f206c6f676963416464726573736573206c656e67746800000000000000606482015260840161020b565b815160005b8163ffffffff168163ffffffff16101561033457828163ffffffff16815181106102bd576102bd610b85565b6020026020010151600080868463ffffffff16815181106102e0576102e0610b85565b6020908102919091018101516001600160e01b031916825281019190915260400160002080546001600160a01b0319166001600160a01b03929092169190911790558061032c81610b9b565b915050610291565b5060405180602001604052806000815250935050505090565b6103556100dd565b6001600160a01b0316336001600160a01b031614156104a05760606001600160e01b031960003516633a2a322560e21b81141561039b57610394610520565b9150610498565b6001600160e01b0319811663207fa8b960e01b14156103bc57610394610577565b6001600160e01b0319811663333b0add60e11b14156103dd576103946105bd565b6001600160e01b03198116621e2a4560eb1b14156103fd576103946105ee565b6001600160e01b0319811663d5696bb160e01b141561041e5761039461062e565b604051636381e58960e11b815260206004820152604260248201527f5472616e73706172656e745570677261646561626c6550726f78793a2061646d60448201527f696e2063616e6e6f742066616c6c6261636b20746f2070726f78792074617267606482015261195d60f21b608482015260a40161020b565b815160208301f35b61009d610642565b6060600080856001600160a01b0316856040516104c59190610bf9565b600060405180830381855af49150503d8060008114610500576040519150601f19603f3d011682016040523d82523d6000602084013e610505565b606091505b509150915061051686838387610652565b9695505050505050565b606061052a6106d1565b60006105393660048184610953565b8101906105469190610c15565b9050610563816040518060200160405280600081525060006106dc565b505060408051602081019091526000815290565b60606000806105893660048184610953565b8101906105969190610c32565b915091506105a6828260016106dc565b604051806020016040528060008152509250505090565b60606105c76106d1565b60006105d63660048184610953565b8101906105e39190610c15565b905061056381610708565b60606105f86106d1565b60006106026100dd565b604080516001600160a01b03831660208201529192500160405160208183030381529060405291505090565b60606106386106d1565b600061060261075f565b61009d61064d61075f565b610785565b606083156106bf5782516106b8576001600160a01b0385163b6106b857604051636381e58960e11b815260206004820152601d60248201527f416464726573733a2063616c6c20746f206e6f6e2d636f6e7472616374000000604482015260640161020b565b50816106c9565b6106c983836107a9565b949350505050565b341561009d57600080fd5b6106e5836107d4565b6000825111806106f25750805b1561070357610701838361009f565b505b505050565b7f7fff140a2090a44f84000f793b3e2c26fd84de1b51418e5734481ecebf9fa4196107316100dd565b604080516001600160a01b03928316815291841660208301520160405180910390a161075c81610814565b50565b600080356001600160e01b0319168152602081905260409020546001600160a01b031690565b3660008037600080366000845af43d6000803e8080156107a4573d6000f35b3d6000fd5b8151156107b95781518083602001fd5b80604051636381e58960e11b815260040161020b9190610cda565b6107dd816108be565b6040516001600160a01b038216907f35c786da035a401a18e1a5dade4bdb4c07d82bdd5c15bef8561dbf76ad4db25d90600090a250565b6001600160a01b03811661087a57604051636381e58960e11b815260206004820152602660248201527f455243313936373a206e65772061646d696e20697320746865207a65726f206160448201526564647265737360d01b606482015260840161020b565b807fb53127684a568b3173ae13b9f8a6016e243e63b6e8ee1178d6a717850b5d61035b80546001600160a01b0319166001600160a01b039290921691909117905550565b6001600160a01b0381163b61092c57604051636381e58960e11b815260206004820152602d60248201527f455243313936373a206e657720696d706c656d656e746174696f6e206973206e60448201526c1bdd08184818dbdb9d1c9858dd609a1b606482015260840161020b565b807f360894a13ba1a3210667c828492db98dca3e2076cc3735a920a3ca505d382bbc61089d565b6000808585111561096357600080fd5b8386111561097057600080fd5b5050820193919092039150565b80356001600160e01b03198116811461099557600080fd5b919050565b6001600160a01b038116811461075c57600080fd5b600080604083850312156109c257600080fd5b6109cb8361097d565b915060208301356109db8161099a565b809150509250929050565b63b95aa35560e01b600052604160045260246000fd5b604051601f8201601f1916",
        "810167ffffffffffffffff81118282101715610a2557610a256109e6565b604052919050565b600067ffffffffffffffff821115610a4757610a476109e6565b5060051b60200190565b600082601f830112610a6257600080fd5b81356020610a77610a7283610a2d565b6109fc565b82815260059290921b84018101918181019086841115610a9657600080fd5b8286015b84811015610aba578035610aad8161099a565b8352918301918301610a9a565b509695505050505050565b60008060408385031215610ad857600080fd5b823567ffffffffffffffff80821115610af057600080fd5b818501915085601f830112610b0457600080fd5b81356020610b14610a7283610a2d565b82815260059290921b84018101918181019089841115610b3357600080fd5b948201945b83861015610b5857610b498661097d565b82529482019490820190610b38565b96505086013592505080821115610b6e57600080fd5b50610b7b85828601610a51565b9150509250929050565b63b95aa35560e01b600052603260045260246000fd5b600063ffffffff80831681811415610bc35763b95aa35560e01b600052601160045260246000fd5b6001019392505050565b60005b83811015610be8578181015183820152602001610bd0565b838111156107015750506000910152565b60008251610c0b818460208701610bcd565b9190910192915050565b600060208284031215610c2757600080fd5b81356100c48161099a565b60008060408385031215610c4557600080fd5b8235610c508161099a565b915060208381013567ffffffffffffffff80821115610c6e57600080fd5b818601915086601f830112610c8257600080fd5b813581811115610c9457610c946109e6565b610ca6601f8201601f191685016109fc565b91508082528784828501011115610cbc57600080fd5b80848401858401376000848284010152508093505050509250929050565b6020815260008251806020840152610cf9816040850160208701610bcd565b601f01601f1916919091016040019291505056fe416464726573733a206c6f772d6c6576656c2064656c65676174652063616c6c206661696c6564a26469706673582212205c4c7f90f1db2c8f42dba48f993b29e8dbe31ac2dca9f23b2ff4587be819358f64736f6c634300080b0033416464726573733a206c6f772d6c6576656c2064656c65676174652063616c6c206661696c6564b53127684a568b3173ae13b9f8a6016e243e63b6e8ee1178d6a717850b5d6103"
    };

    public static final String SM_BINARY =
            org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {
        "[{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_logic\",\"type\":\"address\"},{\"internalType\":\"address\",\"name\":\"admin_\",\"type\":\"address\"},{\"internalType\":\"bytes\",\"name\":\"_data\",\"type\":\"bytes\"}],\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"address\",\"name\":\"previousAdmin\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"newAdmin\",\"type\":\"address\"}],\"name\":\"AdminChanged\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"beacon\",\"type\":\"address\"}],\"name\":\"BeaconUpgraded\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"implementation\",\"type\":\"address\"}],\"name\":\"Upgraded\",\"type\":\"event\"},{\"stateMutability\":\"payable\",\"type\":\"fallback\"},{\"stateMutability\":\"payable\",\"type\":\"receive\"}]"
    };

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final Event ADMINCHANGED_EVENT =
            new Event(
                    "AdminChanged",
                    Arrays.<TypeReference<?>>asList(
                            new TypeReference<Address>() {}, new TypeReference<Address>() {}));;

    public static final Event BEACONUPGRADED_EVENT =
            new Event(
                    "BeaconUpgraded",
                    Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));;

    public static final Event UPGRADED_EVENT =
            new Event(
                    "Upgraded",
                    Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));;

    protected DREvProxy(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static String getABI() {
        return ABI;
    }

    public List<AdminChangedEventResponse> getAdminChangedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList =
                extractEventParametersWithLog(ADMINCHANGED_EVENT, transactionReceipt);
        ArrayList<AdminChangedEventResponse> responses =
                new ArrayList<AdminChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AdminChangedEventResponse typedResponse = new AdminChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousAdmin =
                    (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newAdmin = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public List<BeaconUpgradedEventResponse> getBeaconUpgradedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList =
                extractEventParametersWithLog(BEACONUPGRADED_EVENT, transactionReceipt);
        ArrayList<BeaconUpgradedEventResponse> responses =
                new ArrayList<BeaconUpgradedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BeaconUpgradedEventResponse typedResponse = new BeaconUpgradedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.beacon = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public List<UpgradedEventResponse> getUpgradedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList =
                extractEventParametersWithLog(UPGRADED_EVENT, transactionReceipt);
        ArrayList<UpgradedEventResponse> responses =
                new ArrayList<UpgradedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UpgradedEventResponse typedResponse = new UpgradedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.implementation =
                    (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static DREvProxy load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new DREvProxy(contractAddress, client, credential);
    }

    public static DREvProxy deploy(
            Client client, CryptoKeyPair credential, String _logic, String admin_, byte[] _data)
            throws ContractException {
        byte[] encodedConstructor =
                FunctionEncoder.encodeConstructor(
                        Arrays.<Type>asList(
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(_logic),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.Address(admin_),
                                new org.fisco.bcos.sdk.v3.codec.datatypes.DynamicBytes(_data)));
        return deploy(
                DREvProxy.class,
                client,
                credential,
                getBinary(client.getCryptoSuite()),
                getABI(),
                encodedConstructor,
                null);
    }

    public static class AdminChangedEventResponse {
        public TransactionReceipt.Logs log;

        public String previousAdmin;

        public String newAdmin;
    }

    public static class BeaconUpgradedEventResponse {
        public TransactionReceipt.Logs log;

        public String beacon;
    }

    public static class UpgradedEventResponse {
        public TransactionReceipt.Logs log;

        public String implementation;
    }
}
