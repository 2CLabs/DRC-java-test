1 � FISCO BCOS 3.0.4 ��Ⱥ�����4�ڵ���

2 ��װ������ fisco ����̨

3 ����FISCO BCOS v3 : ʹ��FiscoBcos����̨�ֶ������Լ
  (1) �������ܺ�Լ�ļ���FiscoBcos��console/contracts/solidityĿ¼��
   cd ~
   cd fisco
   git clone git@github.com:2CLabs/DRC-java-test.git
   cp -fr DRC-java-test/java-sdk-demo/contracts/*.sol console/contracts/solidity/
   
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
       ���гɹ�֮�󣬽�����console/contracts/sdkĿ¼����java��abi��binĿ¼ �ҵ���ص�java �ļ� ������ DRC-java-test/java-sdk-demo/src/org.fisco.bcos.sdk.demo.contract Ŀ¼��
   
   �����Լ���õ�interface IDREvidence ��Ӧ��java�ļ�
   �� ����̨������Java��Լ���� ����
        bash contract2java.sh solidity -s contracts/solidity/interfaces -p org.fisco.bcos.sdk.demo.contract
       ���гɹ�֮����console/contracts/sdk/java Ŀ¼�� �ҵ���ص� IDREvidence.java �ļ� ������ DRC-java-test/java-sdk-demo/src/org.fisco.bcos.sdk.demo.contract Ŀ¼��
   
 (3) ��console ���� ��Լ
   ����FiscoBcos console,�������̨
       bash console/start.sh
     deploy DREvidenceAdminController
     deploy DREvidenceUserController
     deploy DREvidenceRightController     
     deploy DREvidenceReviewController
     deploy DREvProxyAdmin
     deploy DREvProxy DAEvidenceAdminController��ַ DAEvProxyAdmin��ַ 0xea605f3d  ���ܰ汾
       ��¼��������Լ�ĵ�ַ(������ DREvTestSet DREvTestGet DREvTestUpgradeAdminController DREvTestUpgradeUserController DREvTestUpgradeRightController DREvTestUpgradeReviewController ����ʹ��)

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
     (3.1)���Ը������ù���
     java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DREvTestSet group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem
     
     (3.2)���Ը��ֻ�ȡ����
     java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DREvTestGet group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem
     
     (3.3)�������� admincontroller ��Լ����
         ������ console�в���һ���µ� DREvidenceAdminController
         deploy DAEvidenceAdminController
       	 ��¼���µ�ַ, �޸� DAEvTestUpgradeAdminController.java �е��� strNewAdminaddr ��ַΪ�µ�ַ,���� bash gradlew build
	 ��������
         java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DREvTestUpgradeAdminController group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem
     
       �������� admincontroller ��Լ ��ԭ���������Ƿ���
       java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DREvTestGet group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem

     (3.4)�������� usercontroller ��Լ����
         ������ console�в���һ���µ� DREvidenceUserController
         deploy DREvidenceUserController
       	 ��¼���µ�ַ, �޸� DREvTestUpgradeUserController.java �е��� strNewUseraddr ��ַΪ�µ�ַ,���� bash gradlew build
	 ��������
         java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DREvTestUpgradeUserController group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem
     
       �������� usercontroller ��Լ ��ԭ���������Ƿ���
       java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DREvTestGet group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem

     (3.5)�������� rightcontroller ��Լ����
         ������ console�в���һ���µ� DREvidenceRightController
         deploy DREvidenceRightController
       	 ��¼���µ�ַ, �޸� DREvTestUpgradeRightController.java �е��� strNewRightaddr ��ַΪ�µ�ַ,���� bash gradlew build
	 ��������
         java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DREvTestUpgradeRightController group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem
     
       �������� rightcontroller ��Լ ��ԭ���������Ƿ���
       java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DREvTestGet group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem

     (3.6)�������� reviewcontroller ��Լ����
         ������ console�в���һ���µ� DREvidenceReviewController
         deploy DREvidenceReviewController
       	 ��¼���µ�ַ, �޸� DREvTestUpgradeReivewController.java �е��� strNewReviewaddr ��ַΪ�µ�ַ,���� bash gradlew build
	 ��������
         java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DREvTestUpgradeReviewController group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem
     
       �������� reviewcontroller ��Լ ��ԭ���������Ƿ���
       java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DREvTestGet group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem


