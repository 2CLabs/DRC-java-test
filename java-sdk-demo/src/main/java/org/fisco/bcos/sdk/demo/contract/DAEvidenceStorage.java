package org.fisco.bcos.sdk.demo.contract;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.codec.datatypes.Address;
import org.fisco.bcos.sdk.v3.codec.datatypes.Event;
import org.fisco.bcos.sdk.v3.codec.datatypes.Function;
import org.fisco.bcos.sdk.v3.codec.datatypes.Type;
import org.fisco.bcos.sdk.v3.codec.datatypes.TypeReference;
import org.fisco.bcos.sdk.v3.codec.datatypes.Utf8String;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint32;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.Uint8;
import org.fisco.bcos.sdk.v3.contract.Contract;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class DAEvidenceStorage extends Contract {
    public static final String[] BINARY_ARRAY = {
        "60c060405260646080526101f460a05234801561001b57600080fd5b5060805160a05160d261003d6000396000607a01526000603b015260d26000f3fe6080604052348015600f57600080fd5b506004361060325760003560e01c8063679eb0f71460375780638575799e146076575b600080fd5b605d7f000000000000000000000000000000000000000000000000000000000000000081565b60405163ffffffff909116815260200160405180910390f35b605d7f00000000000000000000000000000000000000000000000000000000000000008156fea2646970667358221220f7e7422937e814b654909e2600cb825560badae55251f4f729f52d2daad68d8164736f6c634300080b0033"
    };

    public static final String BINARY =
            org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {
        "60c060405260646080526101f460a05234801561001b57600080fd5b5060805160a05160d261003d6000396000607a01526000603b015260d26000f3fe6080604052348015600f57600080fd5b506004361060325760003560e01c8063679916d1146037578063795364b2146076575b600080fd5b605d7f000000000000000000000000000000000000000000000000000000000000000081565b60405163ffffffff909116815260200160405180910390f35b605d7f00000000000000000000000000000000000000000000000000000000000000008156fea264697066735822122059a752b370e438e9425eafd25d8e4ce599967a751bc1682ba7844236d11e621b64736f6c634300080b0033"
    };

    public static final String SM_BINARY =
            org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {
        "[{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"sender\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"string\",\"name\":\"eid\",\"type\":\"string\"}],\"name\":\"EvidenceCommStored\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"sender\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"string\",\"name\":\"udri\",\"type\":\"string\"},{\"indexed\":true,\"internalType\":\"string\",\"name\":\"usci\",\"type\":\"string\"}],\"name\":\"EvidenceStored\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint8\",\"name\":\"version\",\"type\":\"uint8\"}],\"name\":\"Initialized\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"sender\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"string\",\"name\":\"bid\",\"type\":\"string\"},{\"indexed\":true,\"internalType\":\"string\",\"name\":\"usci\",\"type\":\"string\"}],\"name\":\"UserCreated\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"sender\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"string\",\"name\":\"bid\",\"type\":\"string\"}],\"name\":\"UserRoleChanged\",\"type\":\"event\"},{\"inputs\":[],\"name\":\"USER_STATUS_ACTIVE\",\"outputs\":[{\"internalType\":\"uint32\",\"name\":\"\",\"type\":\"uint32\"}],\"selector\":[1738453239,1738086097],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"USER_STATUS_DISABLED\",\"outputs\":[{\"internalType\":\"uint32\",\"name\":\"\",\"type\":\"uint32\"}],\"selector\":[2239068574,2035508402],\"stateMutability\":\"view\",\"type\":\"function\"}]"
    };

    public static final String ABI = org.fisco.bcos.sdk.v3.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_USER_STATUS_ACTIVE = "USER_STATUS_ACTIVE";

    public static final String FUNC_USER_STATUS_DISABLED = "USER_STATUS_DISABLED";

    public static final Event EVIDENCECOMMSTORED_EVENT =
            new Event(
                    "EvidenceCommStored",
                    Arrays.<TypeReference<?>>asList(
                            new TypeReference<Address>(true) {},
                            new TypeReference<Utf8String>(true) {}));;

    public static final Event EVIDENCESTORED_EVENT =
            new Event(
                    "EvidenceStored",
                    Arrays.<TypeReference<?>>asList(
                            new TypeReference<Address>(true) {},
                            new TypeReference<Utf8String>(true) {},
                            new TypeReference<Utf8String>(true) {}));;

    public static final Event INITIALIZED_EVENT =
            new Event(
                    "Initialized", Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));;

    public static final Event USERCREATED_EVENT =
            new Event(
                    "UserCreated",
                    Arrays.<TypeReference<?>>asList(
                            new TypeReference<Address>(true) {},
                            new TypeReference<Utf8String>(true) {},
                            new TypeReference<Utf8String>(true) {}));;

    public static final Event USERROLECHANGED_EVENT =
            new Event(
                    "UserRoleChanged",
                    Arrays.<TypeReference<?>>asList(
                            new TypeReference<Address>(true) {},
                            new TypeReference<Utf8String>(true) {}));;

    protected DAEvidenceStorage(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static String getABI() {
        return ABI;
    }

    public List<EvidenceCommStoredEventResponse> getEvidenceCommStoredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList =
                extractEventParametersWithLog(EVIDENCECOMMSTORED_EVENT, transactionReceipt);
        ArrayList<EvidenceCommStoredEventResponse> responses =
                new ArrayList<EvidenceCommStoredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            EvidenceCommStoredEventResponse typedResponse = new EvidenceCommStoredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.eid = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public List<EvidenceStoredEventResponse> getEvidenceStoredEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList =
                extractEventParametersWithLog(EVIDENCESTORED_EVENT, transactionReceipt);
        ArrayList<EvidenceStoredEventResponse> responses =
                new ArrayList<EvidenceStoredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            EvidenceStoredEventResponse typedResponse = new EvidenceStoredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.udri = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.usci = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
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

    public List<UserCreatedEventResponse> getUserCreatedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList =
                extractEventParametersWithLog(USERCREATED_EVENT, transactionReceipt);
        ArrayList<UserCreatedEventResponse> responses =
                new ArrayList<UserCreatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UserCreatedEventResponse typedResponse = new UserCreatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.bid = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.usci = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public List<UserRoleChangedEventResponse> getUserRoleChangedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList =
                extractEventParametersWithLog(USERROLECHANGED_EVENT, transactionReceipt);
        ArrayList<UserRoleChangedEventResponse> responses =
                new ArrayList<UserRoleChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            UserRoleChangedEventResponse typedResponse = new UserRoleChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.bid = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public BigInteger USER_STATUS_ACTIVE() throws ContractException {
        final Function function =
                new Function(
                        FUNC_USER_STATUS_ACTIVE,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public BigInteger USER_STATUS_DISABLED() throws ContractException {
        final Function function =
                new Function(
                        FUNC_USER_STATUS_DISABLED,
                        Arrays.<Type>asList(),
                        Arrays.<TypeReference<?>>asList(new TypeReference<Uint32>() {}));
        return executeCallWithSingleValueReturn(function, BigInteger.class);
    }

    public static DAEvidenceStorage load(
            String contractAddress, Client client, CryptoKeyPair credential) {
        return new DAEvidenceStorage(contractAddress, client, credential);
    }

    public static DAEvidenceStorage deploy(Client client, CryptoKeyPair credential)
            throws ContractException {
        return deploy(
                DAEvidenceStorage.class,
                client,
                credential,
                getBinary(client.getCryptoSuite()),
                getABI(),
                null,
                null);
    }

    public static class EvidenceCommStoredEventResponse {
        public TransactionReceipt.Logs log;

        public String sender;

        public byte[] eid;
    }

    public static class EvidenceStoredEventResponse {
        public TransactionReceipt.Logs log;

        public String sender;

        public byte[] udri;

        public byte[] usci;
    }

    public static class InitializedEventResponse {
        public TransactionReceipt.Logs log;

        public BigInteger version;
    }

    public static class UserCreatedEventResponse {
        public TransactionReceipt.Logs log;

        public String sender;

        public byte[] bid;

        public byte[] usci;
    }

    public static class UserRoleChangedEventResponse {
        public TransactionReceipt.Logs log;

        public String sender;

        public byte[] bid;
    }
}
