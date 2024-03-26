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
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple4;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.ConstantConfig;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.model.TransactionReceipt;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

public class DAEvTestUpgrade {
    private static Client client;

    public static void Usage() {
        System.out.println(" Usage:");
        System.out.println("===== DAEvidenceController.sol test===========");
        System.out.println(
                " \t java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DAEvTestUpgrade [groupId] [committeeAddr].");
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
            URL configUrl = DAEvTestUpgrade.class.getClassLoader().getResource(configFileName);
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
            // DAEvidenceController xx =
            //        DAEvidenceController.load(
            //                "0x816bc90e1d8801d35bf4c5ada3e93d1769ee32a4", client, committee);
            DAEvProxyAdmin yy =
                    DAEvProxyAdmin.load(
                            "0xa86ec4bc12d9cb96c1f9feadd31a17ea5f2765fb", client, committee);
            String stryyaddr = yy.getContractAddress();
            System.out.println("DAEvProxyAdmin Contract Address: " + stryyaddr);
            DAEvProxy zz =
                    DAEvProxy.load("0x27e94f7f7beb35e5a193a074de91fc6c26dee709", client, committee);
            String strzzaddr = zz.getContractAddress();
            System.out.println("Load DAEvProxy finish: " + strzzaddr);

            DAEvidenceController xx_2 = DAEvidenceController.load(strzzaddr, client, committee);

            System.out.println("---------------------------------------");

            TransactionReceipt setChainNameReceipt = xx_2.setChainName("elton");
            System.out.println("setChainName Tx status: " + setChainNameReceipt.isStatusOK());
            System.out.println("setChainName TX hash: " + setChainNameReceipt.getTransactionHash());

            List<String> strArrQueryRole = new ArrayList<>();
            strArrQueryRole = xx_2.queryUserRole();
            System.out.println("strArrQueryRole: " + strArrQueryRole.size());
            for (int i = 0; i < strArrQueryRole.size(); i++) {
                String element = strArrQueryRole.get(i);
                System.out.println("strArrQueryRole name: " + element);
            }

            Tuple3<String, String, List<String>> getResult = xx_2.getUserRoles("bid");
            // Tuple3<String, String, List<String>> getResult = xx.getUserRoles("bid");
            System.out.println("getResult before upgrade 1: " + getResult.getValue1());
            System.out.println("getResult before upgrade 2: " + getResult.getValue2());
            System.out.println("getResult before upgrade 3: " + getResult.getValue3().size());

            System.out.println("---------------------------------------");
            // String strChainName = xx_2.getChainName(); // 成功
            // System.out.println("strChainName: " + strChainName);

            System.out.println("---------------upgrade-------------------");
            TransactionReceipt upgradeReceipt =
                    yy.upgrade(
                            strzzaddr,
                            "0x987daf61a4d86a85d55613ba43cd97b5d587d172"); // 这个实际会调用 strzzaddr 的
            // upgradeTo
            System.out.println("upgrade Tx status: " + upgradeReceipt.isStatusOK());
            System.out.println("upgrade TX hash: " + upgradeReceipt.getTransactionHash());

            System.out.println("---------------------------------------");
            Tuple3<String, String, List<String>> getResult1 = xx_2.getUserRoles("bid");
            // Tuple3<String, String, List<String>> getResult = xx.getUserRoles("bid");
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
                    xx_2.getVerifyDAEvidence("urd:001", new BigInteger("0"));
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
