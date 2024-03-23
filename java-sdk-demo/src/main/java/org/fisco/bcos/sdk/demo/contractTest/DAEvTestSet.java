package org.fisco.bcos.sdk.demo.contractTest;

import java.io.IOException;
import java.math.BigInteger;
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
                            "0x51f0879017046b5ce7de59637f462a08004a7d80", client, committee);
            DAEvProxyAdmin yy =
                    DAEvProxyAdmin.load(
                            "0x74912199a652a56bf38f28da18d0013180778886", client, committee);
            DAEvProxy zz =
                    DAEvProxy.load("0x0e82c9484f095eb25ba415850d44f5d5f9d8c6cf", client, committee);
            String strzzaddr = zz.getContractAddress();
            System.out.println("Load DAEvProxy finish: " + strzzaddr);
            // String strlogicaddr = yy.getProxyImplementation(strzzaddr); //error report , why ?
            // System.out.println("Logic contract address: " + strlogicaddr);

            DAEvidenceController xx_2 = DAEvidenceController.load(strzzaddr, client, committee);

            List<String> strCategory =
                    new ArrayList<String>() {
                        {
                            add("hold");
                            add("process");
                            add("operate");
                        }
                    };
            TransactionReceipt addCategoryReceipt = xx_2.setDataRightCategory(strCategory);
            System.out.println(
                    "setDataRightCategory Tx status: " + addCategoryReceipt.isStatusOK());
            System.out.println(
                    "setDataRightCategory TX hash: " + addCategoryReceipt.getTransactionHash());

            System.out.println("---------------------------------------");
            List<String> strArrDataRightSupportVariableDataField =
                    new ArrayList<String>() {
                        {
                            add("key_001");
                            add("key_002");
                            add("key_003");
                        }
                    };

            TransactionReceipt setDataRightSupportReceipt =
                    xx_2.setDataRightSupportVariableDataFields(
                            "right", strArrDataRightSupportVariableDataField);
            System.out.println(
                    "setDataRightSupportVariableDataFields Tx status: "
                            + setDataRightSupportReceipt.isStatusOK());
            System.out.println(
                    "setDataRightSupportVariableDataFields TX hash: "
                            + setDataRightSupportReceipt.getTransactionHash());
            System.out.println("---------------------------------------");

            TransactionReceipt setChainNameReceipt = xx_2.setChainName("elton");
            System.out.println("setChainName Tx status: " + setChainNameReceipt.isStatusOK());
            System.out.println("setChainName TX hash: " + setChainNameReceipt.getTransactionHash());

            TransactionReceipt grandReceipt = xx_2.grantUserManagePermission("bid", strAccount);
            System.out.println("grantUserManagePermission Tx status: " + grandReceipt.isStatusOK());
            System.out.println(
                    "grantUserManagePermission TX hash: " + grandReceipt.getTransactionHash());

            Boolean hasif = xx_2.hasDAUserManageRole("bid");
            System.out.println("hasDAUserManageRole hasif: " + hasif);

            // TransactionReceipt revokeReceipt = xx_2.revokeUserManagePermission("bid");
            // System.out.println(
            //        "revokeUserManagePermission Tx status: " + revokeReceipt.isStatusOK());
            // System.out.println(
            //        "revokeUserManagePermission TX hash: " + revokeReceipt.getTransactionHash());
            // Boolean hasif2 = xx_2.hasDAUserManageRole("bid");
            // System.out.println("hasDAUserManageRole hasif2: " + hasif2);

            List<String> strArrQueryRole = new ArrayList<>();
            strArrQueryRole = xx_2.queryUserRole();
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
                            add("platform");
                        }
                    };
            TransactionReceipt addUserReceipt =
                    xx_2.addUser("bid", "usci", "elton", strAccount, strArrRole);
            System.out.println("addUser Tx status: " + addUserReceipt.isStatusOK());
            System.out.println("addUser TX hash: " + addUserReceipt.getTransactionHash());

            Tuple3<String, String, List<String>> getResult = xx_2.getUserRoles("bid");
            System.out.println("getResult 1: " + getResult.getValue1());
            System.out.println("getResult 2: " + getResult.getValue2());
            System.out.println("getResult 3: " + getResult.getValue3().size());
            for (int i = 0; i < getResult.getValue3().size(); i++) {
                String element = getResult.getValue3().get(i);
                System.out.println("getResult 3 Roles name: " + element);
            }

            System.out.println("---------------------------------------");
            List<String> strGrantArrRole =
                    new ArrayList<String>() {
                        {
                            add("registry");
                        }
                    };
            TransactionReceipt grantUserRoleReceipt = xx_2.grantUserRoles("bid", strGrantArrRole);
            System.out.println("grantUserRoles Tx status: " + grantUserRoleReceipt.isStatusOK());
            System.out.println(
                    "grantUserRoles TX hash: " + grantUserRoleReceipt.getTransactionHash());

            Tuple3<String, String, List<String>> getGrantResult = xx_2.getUserRoles("bid");
            System.out.println("after grantUserRoles getResult 1: " + getGrantResult.getValue1());
            System.out.println("after grantUserRoles getResult 2: " + getGrantResult.getValue2());
            System.out.println(
                    "after grantUserRoles getResult 3: " + getGrantResult.getValue3().size());
            for (int i = 0; i < getGrantResult.getValue3().size(); i++) {
                String element = getGrantResult.getValue3().get(i);
                System.out.println("after grantUserRoles getResult 3 Roles name: " + element);
            }

            System.out.println("---------------------------------------");
            List<String> strRevokeArrRole =
                    new ArrayList<String>() {
                        {
                            add("platform");
                        }
                    };
            TransactionReceipt revokeUserRoleReceipt =
                    xx_2.revokeUserRoles("bid", strRevokeArrRole);
            System.out.println("revokeUserRoles Tx status: " + revokeUserRoleReceipt.isStatusOK());
            System.out.println(
                    "revokeUserRoles TX hash: " + revokeUserRoleReceipt.getTransactionHash());

            Tuple3<String, String, List<String>> getRevokeResult = xx_2.getUserRoles("bid");
            System.out.println("after revokeUserRoles getResult 1: " + getRevokeResult.getValue1());
            System.out.println("after revokeUserRoles getResult 2: " + getRevokeResult.getValue2());
            System.out.println(
                    "after revokeUserRoles getResult 3: " + getRevokeResult.getValue3().size());
            for (int i = 0; i < getRevokeResult.getValue3().size(); i++) {
                String element = getRevokeResult.getValue3().get(i);
                System.out.println("after revokeUserRoles getResult 3 Roles name: " + element);
            }

            System.out.println("---------------------------------------");
            List<String> strArrDataHash =
                    new ArrayList<String>() {
                        {
                            add(
                                    "0xc24d340cca7669f4d8933635a0c09caa7d2ecfaba0b34053e32789168171e50a");
                            add(
                                    "0x23406f7824cf8012e6a6fef239f1bc2959254d30f1da84de2495fc96b20a9a6e");
                        }
                    };
            List<String> strArrDataRight =
                    new ArrayList<String>() {
                        {
                            add("hold");
                            add("process");
                            add("operate");
                        }
                    };
            List<String> strArrMetaData =
                    new ArrayList<String>() {
                        {
                            add("key_meata_data_001");
                            add("key_meata_value_001");
                        }
                    };

            List<String> strArrvariableData =
                    new ArrayList<String>() {
                        {
                            add("key_001");
                            add("value_001");
                        }
                    };

            System.out.println("---------------------------------------");

            // String strChainName = xx_2.getChainName(); // 成功
            // System.out.println("strChainName: " + strChainName);

            TransactionReceipt storeReceipt1 =
                    xx_2.addDataRightEvidence(
                            "urd:001",
                            "bid",
                            strArrDataHash,
                            strArrDataRight,
                            strArrMetaData,
                            strArrvariableData);
            System.out.println("addDataRightEvidence Tx status: " + storeReceipt1.isStatusOK());
            System.out.println(
                    "addDataRightEvidence TX hash: " + storeReceipt1.getTransactionHash());

            System.out.println("---------------------------------------");
            List<String> strArrvariableDataNew =
                    new ArrayList<String>() {
                        {
                            add("key_002");
                            add("value_002");
                        }
                    };

            TransactionReceipt storeReceipt2 =
                    xx_2.appendVariableData("urd:001", strArrvariableDataNew);
            System.out.println("appendVariableData Tx status: " + storeReceipt2.isStatusOK());
            System.out.println("appendVariableData TX hash: " + storeReceipt2.getTransactionHash());
            System.out.println("---------------------------------------");

            Tuple6<String, String, List<String>, List<String>, List<String>, Boolean> resultnew =
                    xx_2.getRegisteredData("urd:001"); // 成功
            System.out.println("getRegisteredData result new 1: " + resultnew.getValue1());
            System.out.println("getRegisteredData result new 2: " + resultnew.getValue2());
            System.out.println("getRegisteredData result new 3: " + resultnew.getValue3());
            System.out.println("getRegisteredData result new 4: " + resultnew.getValue4());
            System.out.println("getRegisteredData result new 5: " + resultnew.getValue5());
            System.out.println("getRegisteredData result new 6: " + resultnew.getValue6());

            System.out.println("---------------------------------------");
            List<String> strArrUserDataRight = new ArrayList<>();
            strArrUserDataRight = xx_2.getUserDataRight("urd:001", "bid");
            System.out.println("strArrUserDataRight: " + strArrUserDataRight.size());
            for (int i = 0; i < strArrUserDataRight.size(); i++) {
                String element = strArrUserDataRight.get(i);
                System.out.println("strArrUserDataRight name: " + element);
            }
            System.out.println("---------------------------------------");
            List<String> strArrWithdrawDataRight =
                    new ArrayList<String>() {
                        {
                            add("operate");
                        }
                    };
            // TransactionReceipt withdrawDataRightReceipt =
            // xx_2.withdrawDataRightRegister("urd:001","bid",strArrWithdrawDataRight);
            TransactionReceipt withdrawDataRightReceipt =
                    xx_2.withdrawDataRightRegister("urd:001", strArrWithdrawDataRight);
            System.out.println(
                    "withdrawDataRightReceipt Tx status: " + withdrawDataRightReceipt.isStatusOK());
            System.out.println(
                    "withdrawDataRightReceipt TX hash: "
                            + withdrawDataRightReceipt.getTransactionHash());

            List<String> strArrUserDataRightAfterWithdraw = new ArrayList<>();
            strArrUserDataRightAfterWithdraw = xx_2.getUserDataRight("urd:001", "bid");
            System.out.println(
                    "strArrUserDataRightAfterWithdraw: " + strArrUserDataRightAfterWithdraw.size());
            for (int i = 0; i < strArrUserDataRightAfterWithdraw.size(); i++) {
                String element = strArrUserDataRightAfterWithdraw.get(i);
                System.out.println("strArrUserDataRightAfterWithdraw name: " + element);
            }
            System.out.println("---------------------------------------");

            List<String> strArrReviewDataHash =
                    new ArrayList<String>() {
                        {
                            add(
                                    "0x91bf7d8286ee249b32fdb77245988ae6a3162042b45b7afc762ba582870d6cd3");
                            add(
                                    "0xf9e60cc56c692aaf607336546d922d740a1115a68cf7cde0286594403ad96643");
                        }
                    };
            List<String> strArrReviewMetaData =
                    new ArrayList<String>() {
                        {
                            add("key_meata_data_003");
                            add("key_meata_value_003");
                        }
                    };

            List<String> strArrvReviewAriableData =
                    new ArrayList<String>() {
                        {
                            add("key_003");
                            add("value_003");
                        }
                    };

            TransactionReceipt addReviewReceipt =
                    xx_2.addReviewEvidence(
                            "urd:001",
                            "bid-reviewer",
                            strArrReviewDataHash,
                            strArrReviewMetaData,
                            strArrvReviewAriableData);
            System.out.println("addReviewEvidence Tx status: " + addReviewReceipt.isStatusOK());
            System.out.println(
                    "addReviewEvidence TX hash: " + addReviewReceipt.getTransactionHash());

            System.out.println("---------------------------------------");

            String strUdri =
                    xx_2.getUdriByDatahash(
                            "0xc24d340cca7669f4d8933635a0c09caa7d2ecfaba0b34053e32789168171e50a");
            System.out.println("strUdri: " + strUdri);

            BigInteger datacount = xx_2.getDataCount("bid");
            System.out.println("datacount: " + datacount);

            List<String> strArrDataList = new ArrayList<>();
            strArrDataList = xx_2.getDataList("bid", new BigInteger("0"), new BigInteger("1"));
            System.out.println("strArrDataList: " + strArrDataList.size());
            for (int i = 0; i < strArrDataList.size(); i++) {
                String element = strArrDataList.get(i);
                System.out.println("strArrDataList name: " + element);
            }

            BigInteger reviewdatacount = xx_2.getReviewCount("urd:001");
            System.out.println("reviewdatacount: " + reviewdatacount);

            Tuple3<String, List<String>, List<String>> VerifyDataGetResult =
                    xx_2.getVerifyDAEvidence("urd:001", new BigInteger("0"));
            System.out.println("VerifyDataGetResult 1: " + VerifyDataGetResult.getValue1());
            System.out.println("VerifyDataGetResult 2: " + VerifyDataGetResult.getValue2());
            System.out.println("VerifyDataGetResult 3: " + VerifyDataGetResult.getValue3());

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
