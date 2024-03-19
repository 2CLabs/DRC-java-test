package org.fisco.bcos.sdk.demo.contractTest;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.fisco.bcos.sdk.demo.contract.DAEvProxy;
import org.fisco.bcos.sdk.demo.contract.DAEvProxyAdmin;
import org.fisco.bcos.sdk.demo.contract.DAEvidenceController;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.client.protocol.response.BlockNumber;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple6;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.ConstantConfig;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

public class DAEvTestSet {
    private static Client client;

    public static void Usage() {
        System.out.println(" Usage:");
        System.out.println("===== DAEvidenceController.sol test===========");
        System.out.println(
                " \t java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DAEvTestSet [groupId] [committeeAddr].");
    }

    public static byte[] hexStringToByteArray(String hex) {
        int len = hex.length();
        if (len % 2 != 0) {
            throw new IllegalArgumentException("Invalid hex string");
        }

        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] =
                    (byte)
                            ((Character.digit(hex.charAt(i), 16) << 4)
                                    + Character.digit(hex.charAt(i + 1), 16));
        }

        return data;
    }

    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bytes.length; ++i) {
            sb.append(String.format("%02X", bytes[i])); // %02X表示输出两位十六进制值

            if (i != bytes.length - 1) {
                sb.append(" ");
            }
        }

        return sb.toString().trim();
    }

    public static void main(String[] args)
            throws ContractException, IOException, InterruptedException {
        try {
            String configFileName = ConstantConfig.CONFIG_FILE_NAME;
            URL configUrl = DAEvTestSet.class.getClassLoader().getResource(configFileName);
            if (configUrl == null) {
                throw new IOException("The configFile " + configFileName + " doesn't exist!");
            }

            if (args.length < 2) {
                Usage();
                return;
            }
            String groupId = args[0];
            String committeePath = args[1];

            String configFile = configUrl.getPath();
            BcosSDK sdk = BcosSDK.build(configFile);
            client = sdk.getClient(groupId);

            BlockNumber blockNumber = client.getBlockNumber();
            System.out.println("Current BlockNumber : " + blockNumber.getBlockNumber());

            // CryptoSuite cryptoSuite = new CryptoSuite(client.getCryptoType());
            CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.SM_TYPE);
            System.out.println("CryptoType.SM_TYPE valuse is: " + CryptoType.SM_TYPE);
            System.out.println("client.getCryptoType: " + client.getCryptoType());
            System.out.println("cryptoSuite Type: " + cryptoSuite.getCryptoTypeConfig());

            // load from existing contract address
            cryptoSuite.loadAccount("pem", committeePath, "");
            CryptoKeyPair committee = cryptoSuite.getCryptoKeyPair();
            String strAccount = committee.getAddress();
            System.out.println("Account: " + strAccount);
            client.getCryptoSuite().setCryptoKeyPair(committee);
            DAEvidenceController xx =
                    DAEvidenceController.load(
                            "0x2d877d67126d65e0090e0fd3f7b1de32684f8ad0", client, committee);
            DAEvProxyAdmin yy =
                    DAEvProxyAdmin.load(
                            "0x543468c4f9e9ebe4ac0305dfa65aaf511dde39f9", client, committee);
            DAEvProxy zz =
                    DAEvProxy.load("0xad2879be1a8e9d7f3a14d322d723445721c485c8", client, committee);
            String strzzaddr = zz.getContractAddress();
            System.out.println("Load DAEvProxy finish: " + strzzaddr);
            // String strlogicaddr = yy.getProxyImplementation(strzzaddr); //error report , why ?
            // System.out.println("Logic contract address: " + strlogicaddr);

            byte[] gmbytes =
                    hexStringToByteArray("ea605f3d"); // 0xea605f3d为国密版本下initialize()函数的方法签名

            DAEvidenceController xx_2 = DAEvidenceController.load(strzzaddr, client, committee);

            System.out.println("---------------------------------------");

            TransactionReceipt setChainNameReceipt = xx_2.setChainName("elton");
            System.out.println("setChainName Tx status: " + setChainNameReceipt.isStatusOK());
            System.out.println("setChainName TX hash: " + setChainNameReceipt.getTransactionHash());

            List<String> strArrQueryRole = new ArrayList<>();
            // strArrQueryRole = xx_2.queryUserRole1();
            strArrQueryRole = xx_2.queryUserRole();
            // String txt = xx.USER_ROLE_DATA_HOLDER();
            // System.out.println("USER_ROLE_DATA_HOLDER: " + txt);

            System.out.println("strArrQueryRole: " + strArrQueryRole.size());
            for (int i = 0; i < strArrQueryRole.size(); i++) {
                String element = strArrQueryRole.get(i);
                System.out.println("strArrQueryRole name: " + element);
            }

            List<String> strArrRole =
                    new ArrayList<String>() {
                        {
                            add("data_holder");
                            add("reviewer");
                            add("registry");
                            add("platform");
                        }
                    };
            TransactionReceipt addUserReceipt =
                    xx_2.addUser("bid", "usci", "elton", strAccount, strArrRole);
            // xx.addUser("bid", "usci", "elton", strAccount, strArrRole);
            System.out.println("addUser Tx status: " + addUserReceipt.isStatusOK());
            System.out.println("addUser TX hash: " + addUserReceipt.getTransactionHash());

            Tuple3<String, String, List<String>> getResult = xx_2.getUserRoles("bid");
            // Tuple3<String, String, List<String>> getResult = xx.getUserRoles("bid");
            System.out.println("getResult 1: " + getResult.getValue1());
            System.out.println("getResult 2: " + getResult.getValue2());
            System.out.println("getResult 3: " + getResult.getValue3().size());
            for (int i = 0; i < getResult.getValue3().size(); i++) {
                String element = getResult.getValue3().get(i);
                System.out.println("getResult 3 Roles name: " + element);
            }

            System.out.println("---------------------------------------");
            // String strChainName = xx_2.getChainName(); // 成功
            // System.out.println("strChainName: " + strChainName);

            List<String> strArrDataHash =
                    new ArrayList<String>() {
                        {
                            add(
                                    "0xf09bcbafc57fd3efde5c6c56bb5c47766a3de2a631d051556b4a25c0ae343e53");
                            add(
                                    "0xf2ec46c71939ff7a077ba23ee88778cda8ed8a1d83195d112094725234d5a3e6");
                        }
                    };
            List<String> strArrDataRight =
                    new ArrayList<String>() {
                        {
                            add("b");
                            add("data_holder");
                        }
                    };
            List<String> strArrMetaData =
                    new ArrayList<String>() {
                        {
                            add("c");
                            add("cc");
                        }
                    };
            List<String> strArrvariableData =
                    new ArrayList<String>() {
                        {
                            add("d");
                            add("dd");
                        }
                    };
            TransactionReceipt storeReceipt1 =
                    xx_2.addDataRightEvidence(
                            "4",
                            "bid",
                            strArrDataHash,
                            strArrDataRight,
                            strArrMetaData,
                            strArrvariableData);
            System.out.println("addDataRightEvidence Tx status: " + storeReceipt1.isStatusOK());
            System.out.println(
                    "addDataRightEvidence TX hash: " + storeReceipt1.getTransactionHash());

            System.out.println("---------------------------------------");
            Tuple6<String, String, List<String>, List<String>, List<String>, Boolean> resultnew =
                    xx_2.getRegisteredData("4"); // 成功
            System.out.println("result new 1: " + resultnew.getValue1());
            System.out.println("result new 2: " + resultnew.getValue2());
            System.out.println("result new 3: " + resultnew.getValue3());
            System.out.println("result new 4: " + resultnew.getValue4());
            System.out.println("result new 5: " + resultnew.getValue5());
            System.out.println("result new 6: " + resultnew.getValue6());

            System.out.println("---------------------------------------");

            blockNumber = client.getBlockNumber();
            System.out.println("Current BlockNumber : " + blockNumber.getBlockNumber());

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
