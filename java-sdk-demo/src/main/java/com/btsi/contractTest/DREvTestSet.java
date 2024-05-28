package com.btsi.contractTest;

import com.btsi.contract.DREvProxy;
import com.btsi.contract.DREvProxyAdmin;
import com.btsi.contract.IDREvidence;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.client.protocol.response.BlockNumber;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple3;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple4;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple6;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.ConstantConfig;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

public class DREvTestSet {
    private static Client client;

    public static void Usage() {
        System.out.println(" Usage:");
        System.out.println("===== DREvTestSet test===========");
        System.out.println(
                " \t java -cp 'conf/:lib/*:apps/*' com.btsi.contractTest.DREvTestSet [groupId] [committeeAddr].");
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

    public static String toHexStringWithPadding(BigInteger bigInteger) {
        String hexString = bigInteger.toString(16);

        if (hexString.length() < 8) {
            hexString = String.format("%8s", hexString).replace(' ', '0');
        }

        return hexString;
    }

    public static void main(String[] args)
            throws ContractException, IOException, InterruptedException {
        try {
            String configFileName = ConstantConfig.CONFIG_FILE_NAME;
            URL configUrl = DREvTestSet.class.getClassLoader().getResource(configFileName);
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

            String strAdminAddr = "0x86e76d2b43d9926a75684f7331edf3fbf00a6480";
            String strUserAddr = "0x03ae40c699c07e2442747739f2ad0ef9c44b709f";
            String strRightAddr = "0xba3dfb74d42a0f5f3c73140b5c26b94b4b2bcd82";
            String strReviewAddr = "0xb4553254f958771efefc29e53147e3bf0c1d0911";
            String strProxyAdminaddr = "0xa5d00f85d874d6c9f7c3d6c7327c25155979fec5";
            String strProxyaddr = "0xe1b5a60402464a8c1fe6ad1ea9d805cfe440b0d7";

            DREvProxyAdmin yy = DREvProxyAdmin.load(strProxyAdminaddr, client, committee);
            System.out.println("Load DREvProxyAdmin finish: " + strProxyAdminaddr);

            DREvProxy zz = DREvProxy.load(strProxyaddr, client, committee);
            System.out.println("Load DREvProxy finish: " + strProxyaddr);

            IDREvidence xx_2 = IDREvidence.load(strProxyaddr, client, committee);
            System.out.println("Load DREvProxy as IDREvidence finish");

            System.out.println("---------------------------------------");

            /*List<String> stradminSelector =
                    new ArrayList<String>() {
                        {
                            add("1980433629"); // disableAccessControl
                            add("922088542"); // enableAccessControl
                            add("2324699571"); // getDataRightCategory

                            add("3869446330"); // getSupportVariableDataFields
                            add("1037211317"); // grantUserManagePermission
                            add("1290417574"); // hasUserManageRole

                            add("2167012380"); // initialize
                            add("3234078498"); // setChainName
                            add("2472945857"); // setDataRightCategory

                            add("1199116808"); // setDataRightSupportVariableDataFields
                            add("1356933702"); // setTextMaxLen
                            add("3609374908"); // getChainName

                            add("3460282540"); // getTextMaxLen
                            add("905356897"); // getstrArrayMaxLen
                            add("4229803393"); // getAccessControl
                        }
                    };

            List<String> struserSelector =
                    new ArrayList<String>() {
                        {
                            add("3239424820"); // addUser
                            add("2573946725"); // getUserRoles
                            add("2322354691"); // grantUserRoles

                            add("593762734"); // revokeUserRoles
                            add("816278328"); // getDataCount
                            add("2492434596"); // getDataList

                            add("289407936"); // queryUserRole
                            add("4233016450"); // revokeUserManagePermission
                        }
                    };

            List<String> strrightSelector =
                    new ArrayList<String>() {
                        {
                            add("2635209621"); // addDataRightEvidence
                            add("2138450037"); // appendVariableData
                            add("606765522"); // getDataRightEvidence

                            add("633708824"); // getUdriByDatahash
                            add("1105228669"); // getUserDataRight
                            add("3445852971"); // withdrawDataRight

                            add("4211774851"); // withdrawUserDataRight
                            add("3258251539"); // grantUserDataRight

                            add("4068645820"); // genDataRightEidViaUrdi
                            add("2401931980"); // getDataRightEvidenceViaEid
                        }
                    };

            List<String> strreviewSelector =
                    new ArrayList<String>() {
                        {
                            add("4024293368"); // addReviewEvidence
                            add("1485793629"); // getReviewCount
                            add("3868489521"); // getReviewCountOfReviewer

                            add("1855339097"); // getVerifyEvidence
                            add("1591648918"); // getVerifyEvidenceOfReviewer
                            add("932021754"); // withdrawReviewEvidence

                            add("2101214845"); // getReviewEvidenceViaEid
                            add("2390601602"); // genReviewEid
                        }
                    };*/

            List<String> stradminSMSelector =
                    new ArrayList<String>() {
                        {
                            add("2854924890"); // disableAccessControl
                            add("1158894738"); // enableAccessControl
                            add("3796759802"); // getDataRightCategory

                            add("376683502"); // getSupportVariableDataFields
                            add("3070963644"); // grantUserManagePermission
                            add("3048132719"); // hasUserManageRole

                            add("3932184381"); // initialize
                            add("3307895570"); // setChainName
                            add("2087572871"); // setDataRightCategory

                            add("2135762870"); // setDataRightSupportVariableDataFields
                            add("1285167137"); // setTextMaxLen
                            add("728140679"); // getChainName

                            add("1588090095"); // getTextMaxLen
                            add("1921885619"); // getstrArrayMaxLen
                            add("1904329564"); // getAccessControl
                        }
                    };

            List<String> struserSMSelector =
                    new ArrayList<String>() {
                        {
                            add("3652011643"); // addUser
                            add("1585755409"); // getUserRoles
                            add("115265356"); // grantUserRoles

                            add("2271659916"); // revokeUserRoles
                            add("3023591269"); // getDataCount
                            add("1794719790"); // getDataList

                            add("1912013404"); // queryUserRole
                            add("2768200898"); // revokeUserManagePermission
                        }
                    };

            List<String> strrightSMSelector =
                    new ArrayList<String>() {
                        {
                            add("3623663505"); // addDataRightEvidence
                            add("1301797456"); // appendVariableData
                            add("519044862"); // getDataRightEvidence

                            add("3726531728"); // getUdriByDatahash
                            add("1195862205"); // getUserDataRight
                            add("3715655987"); // withdrawDataRight

                            add("2825282343"); // withdrawUserDataRight
                            add("3358242835"); // grantUserDataRight

                            add("1528694934"); // genDataRightEidViaUrdi
                            add("631971877"); // getDataRightEvidenceViaEid
                        }
                    };

            List<String> strreviewSMSelector =
                    new ArrayList<String>() {
                        {
                            add("766756406"); // addReviewEvidence
                            add("1330250274"); // getReviewCount
                            add("2826438872"); // getReviewCountOfReviewer

                            add("4061585395"); // getVerifyEvidence
                            add("3508812136"); // getVerifyEvidenceOfReviewer
                            add("2229502562"); // withdrawReviewEvidence

                            add("1635642682"); // getReviewEvidenceViaEid
                            add("4131535128"); // genReviewEid
                        }
                    };

            List<byte[]> selectors = new ArrayList<byte[]>();
            List<String> logicAddresses = new ArrayList<String>();

            for (int i = 0; i < stradminSMSelector.size(); i++) {
                String element = stradminSMSelector.get(i);
                String tmp = toHexStringWithPadding(new BigInteger(element));
                // System.out.println("after toHexStringWithPadding: " + tmp);
                byte[] selector = hexStringToByteArray(tmp);
                System.out.println(
                        "stradminSMSelector after hexStringToByteArray: "
                                + byteArrayToHexString(selector));
                selectors.add(selector);
                logicAddresses.add(strAdminAddr);
            }

            for (int i = 0; i < struserSMSelector.size(); i++) {
                String element = struserSMSelector.get(i);
                String tmp = toHexStringWithPadding(new BigInteger(element));
                // System.out.println("after toHexStringWithPadding: " + tmp);
                byte[] selector = hexStringToByteArray(tmp);
                System.out.println(
                        "struserSMSelector after hexStringToByteArray: "
                                + byteArrayToHexString(selector));
                selectors.add(selector);
                logicAddresses.add(strUserAddr);
            }

            for (int i = 0; i < strrightSMSelector.size(); i++) {
                String element = strrightSMSelector.get(i);
                String tmp = toHexStringWithPadding(new BigInteger(element));
                // System.out.println("after toHexStringWithPadding: " + tmp);
                byte[] selector = hexStringToByteArray(tmp);
                System.out.println(
                        "strrightSMSelector after hexStringToByteArray: "
                                + byteArrayToHexString(selector));
                selectors.add(selector);
                logicAddresses.add(strRightAddr);
            }

            for (int i = 0; i < strreviewSMSelector.size(); i++) {
                String element = strreviewSMSelector.get(i);
                String tmp = toHexStringWithPadding(new BigInteger(element));
                // System.out.println("after toHexStringWithPadding: " + tmp);
                byte[] selector = hexStringToByteArray(tmp);
                System.out.println(
                        "strreviewSMSelector after hexStringToByteArray: "
                                + byteArrayToHexString(selector));
                selectors.add(selector);
                logicAddresses.add(strReviewAddr);
            }

            TransactionReceipt setSelectors =
                    yy.setSelectors(strProxyaddr, selectors, logicAddresses);
            System.out.println("setSelectors all Tx status: " + setSelectors.isStatusOK());
            if (setSelectors.isStatusOK() == false) {
                String errorInfo = new String(hexStringToByteArray(setSelectors.getOutput()));
                System.out.println("setSelectors TX message: " + errorInfo);
            }
            System.out.println("setSelectors all  TX hash: " + setSelectors.getTransactionHash());

            System.out.println("---------------------------------------");
            /*TransactionReceipt setContract_1 = xx_2.setContract("next_logic_of_admin", strUserAddr);
            System.out.println("setContract_1 Tx status: " + setContract_1.isStatusOK());
            System.out.println("setContract_1 TX hash: " + setContract_1.getTransactionHash());
            String next1 = xx_2.getContract("next_logic_of_admin");
            System.out.println("next_logic_of_admin address: " + next1);

            TransactionReceipt setContract_2 = xx_2.setContract("next_logic_of_user", strRightAddr);
            System.out.println("setContract_2 Tx status: " + setContract_2.isStatusOK());
            System.out.println("setContract_2 TX hash: " + setContract_2.getTransactionHash());
            String next2 = xx_2.getContract("next_logic_of_user");
            System.out.println("next_logic_of_user address: " + next2);

            TransactionReceipt setContract_3 =
                    xx_2.setContract("next_logic_of_right_evidence", strReviewAddr);
            System.out.println("setContract_3 Tx status: " + setContract_3.isStatusOK());
            System.out.println("setContract_3 TX hash: " + setContract_3.getTransactionHash());
            String next3 = xx_2.getContract("next_logic_of_right_evidence");
            System.out.println("next_logic_of_right_evidence address: " + next3);*/

            TransactionReceipt grandReceipt = xx_2.grantUserManagePermission("bid", strAccount);
            System.out.println("grantUserManagePermission Tx status: " + grandReceipt.isStatusOK());
            if (grandReceipt.isStatusOK() == false) {
                String errorInfo = new String(hexStringToByteArray(grandReceipt.getOutput()));
                System.out.println("grantUserManagePermission TX message: " + errorInfo);
            }
            System.out.println(
                    "grantUserManagePermission TX hash: " + grandReceipt.getTransactionHash());

            System.out.println("---------------------------------------");

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
            if (addCategoryReceipt.isStatusOK() == false) {
                String errorInfo = new String(hexStringToByteArray(addCategoryReceipt.getOutput()));
                System.out.println("setDataRightCategory TX message: " + errorInfo);
            }
            System.out.println(
                    "setDataRightCategory TX hash: " + addCategoryReceipt.getTransactionHash());

            System.out.println("---------------------------------------");
            List<String> strArrDataRightSupportVariableDataField =
                    new ArrayList<String>() {
                        {
                            add("extendDataHash");
                            add("extendDataUrl");
                        }
                    };

            TransactionReceipt setDataRightSupportReceipt =
                    xx_2.setDataRightSupportVariableDataFields(
                            "right", strArrDataRightSupportVariableDataField);
            System.out.println(
                    "setDataRightSupportVariableDataFields Tx status: "
                            + setDataRightSupportReceipt.isStatusOK());
            if (setDataRightSupportReceipt.isStatusOK() == false) {
                String errorInfo =
                        new String(hexStringToByteArray(setDataRightSupportReceipt.getOutput()));
                System.out.println(
                        "setDataRightSupportVariableDataFields TX message: " + errorInfo);
            }
            System.out.println(
                    "setDataRightSupportVariableDataFields TX hash: "
                            + setDataRightSupportReceipt.getTransactionHash());

            TransactionReceipt setDataRightSupportReceipt2 =
                    xx_2.setDataRightSupportVariableDataFields(
                            "review", strArrDataRightSupportVariableDataField);
            System.out.println(
                    "setDataRightSupportVariableDataFields 2 Tx status: "
                            + setDataRightSupportReceipt2.isStatusOK());
            System.out.println(
                    "setDataRightSupportVariableDataFields 2 TX hash: "
                            + setDataRightSupportReceipt2.getTransactionHash());
            System.out.println("---------------------------------------");

            TransactionReceipt setChainNameReceipt = xx_2.setChainName("elton");
            System.out.println("setChainName Tx status: " + setChainNameReceipt.isStatusOK());
            System.out.println("setChainName TX hash: " + setChainNameReceipt.getTransactionHash());

            TransactionReceipt enableAccessControlReceipt = xx_2.enableAccessControl();
            System.out.println(
                    "enableAccessControl Tx status: " + enableAccessControlReceipt.isStatusOK());
            System.out.println(
                    "enableAccessControl TX hash: "
                            + enableAccessControlReceipt.getTransactionHash());

            Boolean hasif = xx_2.hasUserManageRole("bid");
            System.out.println("hasUserManageRole hasif: " + hasif);

            /*TransactionReceipt revokeReceipt = xx_2.revokeUserManagePermission("bid");
            System.out.println(
                    "revokeUserManagePermission Tx status: " + revokeReceipt.isStatusOK());
            System.out.println(
                    "revokeUserManagePermission TX hash: " + revokeReceipt.getTransactionHash());
            Boolean hasif2 = xx_2.hasUserManageRole("bid");
            System.out.println("hasUserManageRole hasif2: " + hasif2);*/

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
                            add("extendDataHash");
                            add("value_001");
                        }
                    };

            System.out.println("---------------------------------------");

            TransactionReceipt storeReceipt1 =
                    xx_2.addDataRightEvidence(
                            "urd:001",
                            "bid",
                            strArrDataHash,
                            strArrDataRight,
                            strArrMetaData,
                            strArrvariableData);
            System.out.println("addDataRightEvidence Tx status: " + storeReceipt1.isStatusOK());
            if (storeReceipt1.isStatusOK() == false) {
                String errorInfo = new String(hexStringToByteArray(storeReceipt1.getOutput()));
                System.out.println("addDataRightEvidence TX message: " + errorInfo);
            }
            System.out.println(
                    "addDataRightEvidence TX hash: " + storeReceipt1.getTransactionHash());

            System.out.println("---------------------------------------");
            List<String> strArrvariableDataNew =
                    new ArrayList<String>() {
                        {
                            add("extendDataUrl");
                            add("value_002");
                        }
                    };

            TransactionReceipt storeReceipt2 =
                    xx_2.appendVariableData("urd:001", strArrvariableDataNew);
            System.out.println("appendVariableData Tx status: " + storeReceipt2.isStatusOK());
            System.out.println("appendVariableData TX hash: " + storeReceipt2.getTransactionHash());
            System.out.println("---------------------------------------");

            Tuple6<String, String, List<String>, List<String>, List<String>, Boolean> resultnew =
                    xx_2.getDataRightEvidence("urd:001"); // 成功
            System.out.println("getDataRightEvidence result new 1: " + resultnew.getValue1());
            System.out.println("getDataRightEvidence result new 2: " + resultnew.getValue2());
            System.out.println("getDataRightEvidence result new 3: " + resultnew.getValue3());
            System.out.println("getDataRightEvidence result new 4: " + resultnew.getValue4());
            System.out.println("getDataRightEvidence result new 5: " + resultnew.getValue5());
            System.out.println("getDataRightEvidence result new 6: " + resultnew.getValue6());

            System.out.println("---------------------------------------");
            List<String> strArrUserDataRight = new ArrayList<>();
            strArrUserDataRight = xx_2.getUserDataRight("urd:001", "bid");
            System.out.println("strArrUserDataRight: " + strArrUserDataRight.size());
            for (int i = 0; i < strArrUserDataRight.size(); i++) {
                String element = strArrUserDataRight.get(i);
                System.out.println("strArrUserDataRight name: " + element);
            }

            System.out.println("---------------------------------------");
            List<String> strArrGrantDataRight =
                    new ArrayList<String>() {
                        {
                            add("hold");
                            add("operate");
                            add("process");
                        }
                    };
            TransactionReceipt grantDataRightReceipt =
                    xx_2.grantUserDataRight("urd:001", "bid", strArrGrantDataRight);
            System.out.println(
                    "grantDataRightReceipt Tx status: " + grantDataRightReceipt.isStatusOK());
            System.out.println(
                    "grantDataRightReceipt TX hash: " + grantDataRightReceipt.getTransactionHash());

            List<String> strArrUserDataRightAftergrant = new ArrayList<>();
            strArrUserDataRightAftergrant = xx_2.getUserDataRight("urd:001", "bid");
            System.out.println(
                    "strArrUserDataRightAftergrant: " + strArrUserDataRightAftergrant.size());
            for (int i = 0; i < strArrUserDataRightAftergrant.size(); i++) {
                String element = strArrUserDataRightAftergrant.get(i);
                System.out.println("strArrUserDataRightAftergrant name: " + element);
            }

            System.out.println("---------------withdrawUserDataRight-------------------");
            List<String> strArrWithdrawUserDataRight =
                    new ArrayList<String>() {
                        {
                            add("process");
                        }
                    };

            TransactionReceipt withdrawUserDataRightReceipt =
                    xx_2.withdrawUserDataRight("urd:001", "bid", strArrWithdrawUserDataRight);
            System.out.println(
                    "withdrawDataRightReceipt Tx status: "
                            + withdrawUserDataRightReceipt.isStatusOK());
            System.out.println(
                    "withdrawDataRightReceipt TX hash: "
                            + withdrawUserDataRightReceipt.getTransactionHash());

            List<String> strArrUserDataRightAfterWithdraw = new ArrayList<>();
            strArrUserDataRightAfterWithdraw = xx_2.getUserDataRight("urd:001", "bid");
            System.out.println(
                    "strArrUserDataRightAfterWithdraw: " + strArrUserDataRightAfterWithdraw.size());
            for (int i = 0; i < strArrUserDataRightAfterWithdraw.size(); i++) {
                String element = strArrUserDataRightAfterWithdraw.get(i);
                System.out.println("strArrUserDataRightAfterWithdraw name: " + element);
            }

            System.out.println("---------------withdrawDataRight-------------------");
            List<String> strArrwithdrawDataRight =
                    new ArrayList<String>() {
                        {
                            // add("hold");
                            add("operate");
                        }
                    };

            TransactionReceipt withdrawDataRightReceipt =
                    xx_2.withdrawDataRight("urd:001", strArrwithdrawDataRight);
            System.out.println(
                    "withdrawDataRight Tx status: " + withdrawDataRightReceipt.isStatusOK());
            System.out.println(
                    "withdrawDataRight TX hash: " + withdrawDataRightReceipt.getTransactionHash());

            List<String> strArrUserDataRightAfterWithdraw2 = new ArrayList<>();
            strArrUserDataRightAfterWithdraw2 = xx_2.getUserDataRight("urd:001", "bid");
            System.out.println(
                    "strArrUserDataRightAfterWithdraw 2 size: "
                            + strArrUserDataRightAfterWithdraw2.size());
            for (int i = 0; i < strArrUserDataRightAfterWithdraw2.size(); i++) {
                String element = strArrUserDataRightAfterWithdraw2.get(i);
                System.out.println("strArrUserDataRightAfterWithdraw 2 name: " + element);
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
            List<String> strArrReviewDataHash2 =
                    new ArrayList<String>() {
                        {
                            add(
                                    "0x11bf7d8286ee249b32fdb77245988ae6a3162042b45b7afc762ba582870d6cd3");
                            add(
                                    "0x19e60cc56c692aaf607336546d922d740a1115a68cf7cde0286594403ad96643");
                        }
                    };
            List<String> strArrReviewDataHash3 =
                    new ArrayList<String>() {
                        {
                            add(
                                    "0x12bf7d8286ee249b32fdb77245988ae6a3162042b45b7afc762ba582870d6cd3");
                            add(
                                    "0x12e60cc56c692aaf607336546d922d740a1115a68cf7cde0286594403ad96643");
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
                            add("extendDataHash");
                            add("value_003");
                        }
                    };
            List<String> strArrvNullAriableData =
                    new ArrayList<String>() {
                        {
                        }
                    };

            TransactionReceipt addReviewReceipt =
                    xx_2.addReviewEvidence(
                            "urd:001",
                            "bid",
                            strArrReviewDataHash,
                            strArrReviewMetaData,
                            strArrvReviewAriableData);
            System.out.println("addReviewEvidence Tx status: " + addReviewReceipt.isStatusOK());
            System.out.println("addReviewEvidence Tx getStatus: " + addReviewReceipt.getStatus());
            System.out.println(
                    "addReviewEvidence TX hash: " + addReviewReceipt.getTransactionHash());
            TransactionReceipt addReviewReceipt2 =
                    xx_2.addReviewEvidence(
                            "urd:001",
                            "bid",
                            strArrReviewDataHash2,
                            strArrReviewMetaData,
                            strArrvReviewAriableData);
            System.out.println("addReviewEvidence 2 Tx status: " + addReviewReceipt2.isStatusOK());
            System.out.println(
                    "addReviewEvidence 2 TX hash: " + addReviewReceipt2.getTransactionHash());

            TransactionReceipt addReviewReceipt3 =
                    xx_2.addReviewEvidence(
                            "urd:001",
                            "bid",
                            strArrReviewDataHash3,
                            strArrReviewMetaData,
                            strArrvNullAriableData);
            System.out.println("addReviewEvidence 3 Tx status: " + addReviewReceipt3.isStatusOK());
            System.out.println(
                    "addReviewEvidence 3 TX hash: " + addReviewReceipt3.getTransactionHash());

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

            Tuple4<Boolean, String, List<String>, List<String>> VerifyDataGetResult =
                    xx_2.getVerifyEvidence("urd:001", new BigInteger("2"));
            System.out.println("VerifyDataGetResult 1: " + VerifyDataGetResult.getValue1());
            System.out.println("VerifyDataGetResult 2: " + VerifyDataGetResult.getValue2());
            System.out.println("VerifyDataGetResult 3: " + VerifyDataGetResult.getValue3());
            System.out.println("VerifyDataGetResult 4: " + VerifyDataGetResult.getValue4());

            System.out.println("---------------------------------------");

            TransactionReceipt withdrawReviewReceipt =
                    xx_2.withdrawReviewEvidence("urd:001", "bid");
            System.out.println(
                    "withdrawReviewEvidence Tx status: " + withdrawReviewReceipt.isStatusOK());
            System.out.println(
                    "withdrawReviewEvidence TX hash: "
                            + withdrawReviewReceipt.getTransactionHash());

            Tuple4<Boolean, String, List<String>, List<String>> VerifyDataAfterWithdrawGetResult =
                    xx_2.getVerifyEvidence("urd:001", new BigInteger("2"));
            System.out.println(
                    "VerifyDataAfterWithdrawGetResult 1: "
                            + VerifyDataAfterWithdrawGetResult.getValue1());
            System.out.println(
                    "VerifyDataAfterWithdrawGetResult 2: "
                            + VerifyDataAfterWithdrawGetResult.getValue2());
            System.out.println(
                    "VerifyDataAfterWithdrawGetResult 3: "
                            + VerifyDataAfterWithdrawGetResult.getValue3());
            System.out.println(
                    "VerifyDataAfterWithdrawGetResult 4: "
                            + VerifyDataAfterWithdrawGetResult.getValue4());

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
