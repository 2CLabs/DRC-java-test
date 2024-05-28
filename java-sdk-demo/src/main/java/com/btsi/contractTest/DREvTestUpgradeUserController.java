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
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.ConstantConfig;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

public class DREvTestUpgradeUserController {
    private static Client client;

    public static void Usage() {
        System.out.println(" Usage:");
        System.out.println("===== DREvTestUpgradeUserController test===========");
        System.out.println(
                " \t java -cp 'conf/:lib/*:apps/*' com.btsi.contractTest.DREvTestUpgradeUserController [groupId] [committeeAddr].");
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
            URL configUrl =
                    DREvTestUpgradeAdminController.class
                            .getClassLoader()
                            .getResource(configFileName);
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
            System.out.println("Account: " + committee.getAddress());
            client.getCryptoSuite().setCryptoKeyPair(committee);

            String strUserAddr = "0x03ae40c699c07e2442747739f2ad0ef9c44b709f";
            String strProxyAdminaddr = "0xa5d00f85d874d6c9f7c3d6c7327c25155979fec5";
            String strProxyaddr = "0xe1b5a60402464a8c1fe6ad1ea9d805cfe440b0d7";
            String strNewUseraddr = "0x04632c10bb380e082586a68d8f65d82fc6d6605d";

            DREvProxyAdmin yy = DREvProxyAdmin.load(strProxyAdminaddr, client, committee);
            System.out.println("Load DREvProxyAdmin finish: " + strProxyAdminaddr);
            DREvProxy zz = DREvProxy.load(strProxyaddr, client, committee);
            System.out.println("Load DREvProxy finish: " + strProxyaddr);

            IDREvidence xx_2 = IDREvidence.load(strProxyaddr, client, committee);
            System.out.println("Load DREvProxy as IDREvidence finish");

            System.out.println("---------------------------------------");

            List<String> strArrQueryRole = new ArrayList<>();
            strArrQueryRole = xx_2.queryUserRole();
            System.out.println("strArrQueryRole: " + strArrQueryRole.size());
            for (int i = 0; i < strArrQueryRole.size(); i++) {
                String element = strArrQueryRole.get(i);
                System.out.println("strArrQueryRole name: " + element);
            }

            System.out.println("---------------upgrade-------------------");
            // 不再使用 DREvProxyAdmin.upgrade这种模式,而是采用 DREvProxyAdmin.setSelectors 这种模式升级
            /*List<String> struserSelector =
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
            };*/

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

            List<byte[]> selectors = new ArrayList<byte[]>();
            List<String> logicAddresses = new ArrayList<String>();

            for (int i = 0; i < struserSMSelector.size(); i++) {
                String element = struserSMSelector.get(i);
                String tmp = toHexStringWithPadding(new BigInteger(element));
                // System.out.println("after toHexStringWithPadding: " + tmp);
                byte[] selector = hexStringToByteArray(tmp);
                System.out.println(
                        "struserSMSelector after hexStringToByteArray: "
                                + byteArrayToHexString(selector));
                selectors.add(selector);
                logicAddresses.add(strNewUseraddr);
            }

            TransactionReceipt setSelectors =
                    yy.setSelectors(strProxyaddr, selectors, logicAddresses);
            System.out.println("setSelectors user Tx status: " + setSelectors.isStatusOK());
            System.out.println("setSelectors user TX hash: " + setSelectors.getTransactionHash());

            System.out.println("---------------------------------------");
            Tuple3<String, String, List<String>> getResult1 = xx_2.getUserRoles("bid");
            System.out.println("getResult after upgrade 1: " + getResult1.getValue1());
            System.out.println("getResult after upgrade 2: " + getResult1.getValue2());
            System.out.println("getResult after upgrade 3: " + getResult1.getValue3().size());

            System.out.println("---------------------------------------");
            List<String> strArrUserDataRight = new ArrayList<>();
            strArrUserDataRight = xx_2.getUserDataRight("urd:001", "bid");
            System.out.println("strArrUserDataRight: " + strArrUserDataRight.size());
            for (int i = 0; i < strArrUserDataRight.size(); i++) {
                String element = strArrUserDataRight.get(i);
                System.out.println("strArrUserDataRight name: " + element);
            }
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
                    xx_2.getVerifyEvidence("urd:001", new BigInteger("1"));
            System.out.println("VerifyDataGetResult 1: " + VerifyDataGetResult.getValue1());
            System.out.println("VerifyDataGetResult 2: " + VerifyDataGetResult.getValue2());
            System.out.println("VerifyDataGetResult 3: " + VerifyDataGetResult.getValue3());
            System.out.println("VerifyDataGetResult 4: " + VerifyDataGetResult.getValue4());

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
