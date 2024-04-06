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

public class DAEvTestUpgradeAdminController {
    private static Client client;

    public static void Usage() {
        System.out.println(" Usage:");
        System.out.println("===== DAEvTestUpgradeAdminController test===========");
        System.out.println(
                " \t java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DAEvTestUpgradeAdminController [groupId] [committeeAddr].");
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

            String strAdminAddr = "0x42efc7f5d5c30d2ad43fb28e97bbb6044a2cc3f3";
            String strProxyAdminaddr = "0x393a661b853e6fa1033fd28d7742f6758e8abd38";
            String strProxyaddr = "0x80cffaca93307b7d20ee738e118512297bf06c3c";
            String strNewAdminaddr = "0x20f799d031f2414da8d190172131ad6140097bc9";

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
            // TransactionReceipt upgradeReceipt =
            //        yy.upgrade(strProxyaddr, strNewAdminaddr); // 这个实际会调用 strProxyaddr 的
            // System.out.println("upgrade Tx status: " + upgradeReceipt.isStatusOK());
            // System.out.println("upgrade TX hash: " + upgradeReceipt.getTransactionHash());
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
                logicAddresses.add(strNewAdminaddr);
            }

            TransactionReceipt setSelectors =
                    yy.setSelectors(strProxyaddr, selectors, logicAddresses);
            System.out.println("setSelectors admin Tx status: " + setSelectors.isStatusOK());
            System.out.println("setSelectors admin TX hash: " + setSelectors.getTransactionHash());

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
