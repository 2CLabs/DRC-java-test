package org.fisco.bcos.sdk.demo.contract;

import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class DREvidenceUtils extends Contract {
    public static final String[] BINARY_ARRAY = {
        "60566037600b82828239805160001a607314602a57634e487b7160e01b600052600060045260246000fd5b30600052607381538281f3fe73000000000000000000000000000000000000000030146080604052600080fdfea2646970667358221220bee5805a79b2eb63be9c8e900ef47681675505d4c69b1148d1e1fd417ad4552f64736f6c634300080b0033"
    };

    public static final String BINARY =
            org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {
        "60566037600b82828239805160001a607314602a5763b95aa35560e01b600052600060045260246000fd5b30600052607381538281f3fe73000000000000000000000000000000000000000030146080604052600080fdfea26469706673582212203fbbaa7232f1c9310aa0b2a76c041f8aa11ceffc61c5fec83c96d5e785e9735664736f6c634300080b0033"
    };

    public static final String SM_BINARY =
            org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[]"};

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    protected DREvidenceUtils(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static String getABI() {
        return ABI;
    }

    public static DREvidenceUtils load(
            String contractAddress, Client client, CryptoKeyPair credential) {
        return new DREvidenceUtils(contractAddress, client, credential);
    }

    public static DREvidenceUtils deploy(Client client, CryptoKeyPair credential)
            throws ContractException {
        return deploy(
                DREvidenceUtils.class,
                client,
                credential,
                getBinary(client.getCryptoSuite()),
                getABI(),
                null,
                null);
    }
}
