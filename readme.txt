1 � FISCO BCOS 3.0.4 ��Ⱥ�����4�ڵ���

2 ��װ������ fisco ����̨

3 ����FISCO BCOS v3 : ʹ��FiscoBcos����̨�ֶ������Լ
  (1) �������ܺ�Լ�ļ���FiscoBcos��console/contracts/solidityĿ¼��
   cd ~
   cd fisco
   git clone git@github.com:2CLabs/DA-java-test.git
   cp -fr DA-java-test/java-sdk-demo/contracts/*.sol console/contracts/solidity/
   
   git clone git@github.com:2CLabs/DA-contract-standard.git
   cd DA-contract-standard
   nvm install v14.17.0
   nvm use v14.17.0
   cd evidence
   npm install
   cp -rf node_modules/@openzeppelin console/contracts/solidity/

 (2)�����Լ��java�ļ�
   �� ����̨������Java��Լ���� ����
        bash contract2java.sh solidity -p org.fisco.bcos.sdk.demo.contract
       ���гɹ�֮�󣬽�����console/contracts/sdkĿ¼����java��abi��binĿ¼ �ҵ���ص�java �ļ� ������ DA-java-test/java-sdk-demo/src org.fisco.bcos.sdk.demo.contract Ŀ¼��
   
 (3) ��console ���� ��Լ
   ����FiscoBcos console,�������̨
       bash console/start.sh
     deploy DAEvidenceController 
     deploy DAEvProxyAdmin 
     deploy DAEvProxy DAEvidenceController��ַ DAEvProxyAdmin��ַ 0xea605f3d  ���ܰ汾
       ��¼��������Լ�ĵ�ַ(������ DAEvTestSet DAEvTestGet DAEvTestUpgrade ʹ��)

4 �������� java-sdk-demo
  (1) ����
     ����֤��
      cd dist
      cp -r ~/fisco/gm_nodes/127.0.0.1/sdk/* conf
     ���������ļ�
      cp conf\config-example.toml conf\config.toml
       �޸� useSMCrypto = "true"
       peers=["127.0.0.1:40200", "127.0.0.1:40201"]
       keyStoreDir = "/home/duvon/fisco/gm_nodes/ca/accounts_gm"
  (2) ����
     export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
     bash gradlew googleJavaFormat
     bash gradlew build
  (3) ���� (����� group �� ��Կ�ļ�����ʵ���������)
     cd dist
     java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DAEvTestSet group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem
     java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DAEvTestGet group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem
     java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DAEvTestUpgrade group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem
 