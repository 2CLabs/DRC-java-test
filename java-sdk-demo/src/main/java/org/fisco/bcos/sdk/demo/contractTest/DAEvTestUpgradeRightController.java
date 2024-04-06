package org.fisco.bcos.sdk.demo.contractTest;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.fisco.bcos.sdk.demo.contract.DREvProxy;
import org.fisco.bcos.sdk.demo.contract.DREvProxyAdmin;
import org.fisco.bcos.sdk.demo.contract.IDREvidence;
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

public class DAEvTestUpgradeRightController {
    private static Client client;

    public static void Usage() {
        System.out.println(" Usage:");
        System.out.println("===== DAEvTestUpgradeRightController test===========");
        System.out.println(
                " \t java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DAEvTestUpgradeRightController [groupId] [committeeAddr].");
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
                    DAEvTestUpgradeAdminController.class
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

            String strRightAddr = "0x092e294eb1288ff0e4c55631199a5aaebda0209a";
            String strProxyAdminaddr = "0x393a661b853e6fa1033fd28d7742f6758e8abd38";
            String strProxyaddr = "0x80cffaca93307b7d20ee738e118512297bf06c3c";
            String strNewRightaddr = "0xc912b8cb70fe9b792ea6c5117369526e1d8d5bf4";

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
            /*List<String> strrightSelector =
            new ArrayList<String>() {
                {
                    add("2635209621"); // addDataRightEvidence
                    add("2138450037"); // appendVariableData
                    add("208288129"); // getRegisteredData

                    add("633708824"); // getUdriByDatahash
                    add("1105228669"); // getUserDataRight
                    add("2392051885"); // withdrawDataRightRegister

                    add("4211774851"); // withdrawUserDataRight
                    add("3258251539"); // grantUserDataRight
                }
            };*/

            List<String> strrightSMSelector =
                    new ArrayList<String>() {
                        {
                            add("3623663505"); // addDataRightEvidence
                            add("1301797456"); // appendVariableData
                            add("3936625741"); // getRegisteredData

                            add("3726531728"); // getUdriByDatahash
                            add("1195862205"); // getUserDataRight
                            add("1179902888"); // withdrawDataRightRegister

                            add("2825282343"); // withdrawUserDataRight
                            add("3358242835"); // grantUserDataRight
                        }
                    };

            List<byte[]> selectors = new ArrayList<byte[]>();
            List<String> logicAddresses = new ArrayList<String>();

            for (int i = 0; i < strrightSMSelector.size(); i++) {
                String element = strrightSMSelector.get(i);
                String tmp = toHexStringWithPadding(new BigInteger(element));
                // System.out.println("after toHexStringWithPadding: " + tmp);
                byte[] selector = hexStringToByteArray(tmp);
                System.out.println(
                        "strrightSMSelector after hexStringToByteArray: "
                                + byteArrayToHexString(selector));
                selectors.add(selector);
                logicAddresses.add(strNewRightaddr);
            }

            TransactionReceipt setSelectors =
                    yy.setSelectors(strProxyaddr, selectors, logicAddresses);
            System.out.println("setSelectors right Tx status: " + setSelectors.isStatusOK());
            System.out.println("setSelectors right TX hash: " + setSelectors.getTransactionHash());

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
