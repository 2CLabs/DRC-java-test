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
     deploy DAEvidenceAdminController
     deploy DAEvidenceUserController
     deploy DAEvidenceRightController     
     deploy DAEvidenceReviewController
     deploy DAEvProxyAdmin
     deploy DAEvProxy DAEvidenceAdminController��ַ DAEvProxyAdmin��ַ 0xea605f3d  ���ܰ汾
       ��¼��������Լ�ĵ�ַ(������ DAEvTestSet DAEvTestGet DAEvTestUpgradeAdminController DAEvTestUpgradeUserController DAEvTestUpgradeRightController DAEvTestUpgradeReviewController ����ʹ��)

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
     �yԇ���N�O�ù���
     java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DAEvTestSet group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem
     
     �yԇ���N�@ȡ����
     java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DAEvTestGet group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem
     
     �yԇ���� admincontroller ��Լ����
         ������ console�в���һ���µ� DAEvidenceAdminController
         deploy DAEvidenceAdminController
       	 ��¼���µ�ַ, �޸� DAEvTestUpgradeAdminController.java �е��� strNewAdminaddr ��ַΪ�µ�ַ,���� bash gradlew build
	 ��������
         java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DAEvTestUpgradeAdminController group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem
     
     �yԇ���� admincontroller ��Լ ��ԭ���Ĕ����Ƿ�߀��
     java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DAEvTestGet group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem

     �yԇ���� usercontroller ��Լ����
         ������ console�в���һ���µ� DAEvidenceUserController
         deploy DAEvidenceUserController
       	 ��¼���µ�ַ, �޸� DAEvTestUpgradeUserController.java �е��� strNewUseraddr ��ַΪ�µ�ַ,���� bash gradlew build
	 ��������
         java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DAEvTestUpgradeUserController group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem
     
     �yԇ���� usercontroller ��Լ ��ԭ���Ĕ����Ƿ�߀��
     java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DAEvTestGet group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem

     �yԇ���� rightcontroller ��Լ����
         ������ console�в���һ���µ� DAEvidenceRightController
         deploy DAEvidenceRightController
       	 ��¼���µ�ַ, �޸� DAEvTestUpgradeRightController.java �е��� strNewRightaddr ��ַΪ�µ�ַ,���� bash gradlew build
	 ��������
         java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DAEvTestUpgradeRightController group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem
     
     �yԇ���� rightcontroller ��Լ ��ԭ���Ĕ����Ƿ�߀��
     java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DAEvTestGet group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem

     �yԇ���� reviewcontroller ��Լ����
         ������ console�в���һ���µ� DAEvidenceReviewController
         deploy DAEvidenceReviewController
       	 ��¼���µ�ַ, �޸� DAEvTestUpgradeReivewController.java �е��� strNewReviewaddr ��ַΪ�µ�ַ,���� bash gradlew build
	 ��������
         java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DAEvTestUpgradeReviewController group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem
     
     �yԇ���� reviewcontroller ��Լ ��ԭ���Ĕ����Ƿ�߀��
     java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DAEvTestGet group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem


