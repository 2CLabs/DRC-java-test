package org.fisco.bcos.sdk.demo.contractTest;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import org.fisco.bcos.sdk.demo.contract.DAEvProxy;
import org.fisco.bcos.sdk.demo.contract.DAEvProxyAdmin;
import org.fisco.bcos.sdk.demo.contract.DAEvidenceController;
import org.fisco.bcos.sdk.v3.BcosSDK;
import org.fisco.bcos.sdk.v3.client.Client;
import org.fisco.bcos.sdk.v3.client.protocol.response.BlockNumber;
import org.fisco.bcos.sdk.v3.codec.datatypes.generated.tuples.generated.Tuple6;
import org.fisco.bcos.sdk.v3.crypto.CryptoSuite;
import org.fisco.bcos.sdk.v3.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.v3.model.ConstantConfig;
import org.fisco.bcos.sdk.v3.model.CryptoType;
import org.fisco.bcos.sdk.v3.transaction.model.exception.ContractException;

public class DAEvTestGet {
    private static Client client;

    public static void Usage() {
        System.out.println(" Usage:");
        System.out.println("===== DAEvidenceController.sol test===========");
        System.out.println(
                " \t java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DAEvTestGet [groupId] [committeeAddr].");
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
            URL configUrl = DAEvTestGet.class.getClassLoader().getResource(configFileName);
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
            // DAEvidenceController.load("0x36f1084cc0458479d1f2ef0953c91c4399887470", client,
            // committee);
            DAEvProxyAdmin yy =
                    DAEvProxyAdmin.load(
                            "0x7c576617f7859d9213a05d32c1c55c525018282b", client, committee);
            DAEvProxy zz =
                    DAEvProxy.load("0x66a9ca0c68bb5ddc2bf5efc07d896fda0d48b89c", client, committee);
            String strzzaddr = zz.getContractAddress();
            System.out.println("Load DAEvProxy finish: " + strzzaddr);
            // String strlogicaddr = yy.getProxyImplementation(strzzaddr); //error report , why ?
            // System.out.println("Logic contract address: " + strlogicaddr);

            byte[] gmbytes =
                    hexStringToByteArray("ea605f3d"); // 0xea605f3d为国密版本下initialize()函数的方法签名

            String strdata = "530EE72E9ED0E80125F4A9C6CE2DB1061502B9CE760DA578B79566D8AE28816F";
            byte[] evid = hexStringToByteArray(strdata);
            String text = "this is a test";

            DAEvidenceController xx_2 = DAEvidenceController.load(strzzaddr, client, committee);

            System.out.println("---------------------------------------");
            // String strChainName = xx_2.getChainName(); // 成功
            // System.out.println("strChainName: " + strChainName);

            System.out.println("---------------------------------------");
            Tuple6<String, String, List<String>, List<String>, List<String>, Boolean> resultnew =
                    xx_2.getRegisteredData(text); // 成功
            System.out.println("result new 1: " + resultnew.getValue1());
            System.out.println("result new 2: " + resultnew.getValue2());
            System.out.println("result new 3: " + resultnew.getValue3());
            System.out.println("result new 4: " + resultnew.getValue4());
            System.out.println("result new 5: " + resultnew.getValue5());
            System.out.println("result new 6: " + resultnew.getValue6());

            String strdata1 = "640EE72E9ED0E80125F4A9C6CE2DB1061502B9CE760DA578B79566D8AE28816F";
            byte[] evid1 = hexStringToByteArray(strdata1);
            String text1 = "this is a test1";

            System.out.println("---------------------------------------");
            Tuple6<String, String, List<String>, List<String>, List<String>, Boolean> resultnew2 =
                    xx_2.getRegisteredData(text1); // 成功
            System.out.println("result new for get evid1 1: " + resultnew2.getValue1());
            System.out.println("result new for get evid1 2: " + resultnew2.getValue2());
            System.out.println("result new for get evid1 3: " + resultnew2.getValue3());
            System.out.println("result new for get evid1 4: " + resultnew2.getValue4());
            System.out.println("result new for get evid1 5: " + resultnew2.getValue5());
            System.out.println("result new for get evid1 6: " + resultnew2.getValue6());

            blockNumber = client.getBlockNumber();
            System.out.println("Current BlockNumber : " + blockNumber.getBlockNumber());

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
