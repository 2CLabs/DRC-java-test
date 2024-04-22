1 搭建 FISCO BCOS 3.0.4 单群组国密4节点链

2 安装和配置 fisco 控制台

3 部署到FISCO BCOS v3 : 使用FiscoBcos控制台手动部署合约
  (1) 拷贝智能合约文件到FiscoBcos的console/contracts/solidity目录下
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

 (2)编译合约的java文件
   用 控制台下生成Java合约工具 运行
        bash contract2java.sh solidity -p org.fisco.bcos.sdk.demo.contract
       运行成功之后，将会在console/contracts/sdk目录生成java、abi和bin目录 找到相关的java 文件 拷贝到 DRC-java-test/java-sdk-demo/src/org.fisco.bcos.sdk.demo.contract 目录下
   
   编译合约调用的interface IDREvidence 对应的java文件
   用 控制台下生成Java合约工具 运行
        bash contract2java.sh solidity -s contracts/solidity/interfaces -p org.fisco.bcos.sdk.demo.contract
       运行成功之后，在console/contracts/sdk/java 目录下 找到相关的 IDREvidence.java 文件 拷贝到 DRC-java-test/java-sdk-demo/src/org.fisco.bcos.sdk.demo.contract 目录下
   
 (3) 在console 部署 合约
   启动FiscoBcos console,进入控制台
       bash console/start.sh
     deploy DREvidenceAdminController
     deploy DREvidenceUserController
     deploy DREvidenceRightController     
     deploy DREvidenceReviewController
     deploy DREvProxyAdmin
     deploy DREvProxy DAEvidenceAdminController地址 DAEvProxyAdmin地址 0xea605f3d  国密版本
       记录下六个合约的地址(后续在 DREvTestSet DREvTestGet DREvTestUpgradeAdminController DREvTestUpgradeUserController DREvTestUpgradeRightController DREvTestUpgradeReviewController 将会使用)

4 编译运行 java-sdk-demo
  (1) 配置
     拷贝证书
      cd dist
      cp -r ~/fisco/gm_nodes/127.0.0.1/sdk/* conf
     调整配置文件
      cp conf\config-example.toml conf\config.toml
       修改 useSMCrypto = "true"
       peers=["127.0.0.1:40200", "127.0.0.1:40201"]
       keyStoreDir = "/home/duvon/fisco/gm_nodes/ca/accounts_gm"
  (2) 编译
     export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
     bash gradlew googleJavaFormat
     bash gradlew build
  (3) 运行 (后面的 group 和 密钥文件根据实际情况调整)
     cd dist
     (3.1)测试各种设置功能
     java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DREvTestSet group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem
     
     (3.2)测试各种获取功能
     java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DREvTestGet group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem
     
     (3.3)测试升級 admincontroller 合约功能
         重新在 console中部署一个新的 DREvidenceAdminController
         deploy DAEvidenceAdminController
       	 记录下新地址, 修改 DAEvTestUpgradeAdminController.java 中的新 strNewAdminaddr 地址为新地址,重新 bash gradlew build
	 运行升级
         java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DREvTestUpgradeAdminController group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem
     
       测试升級 admincontroller 合约 后原来的数据是否还在
       java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DREvTestGet group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem

     (3.4)测试升級 usercontroller 合约功能
         重新在 console中部署一个新的 DREvidenceUserController
         deploy DREvidenceUserController
       	 记录下新地址, 修改 DREvTestUpgradeUserController.java 中的新 strNewUseraddr 地址为新地址,重新 bash gradlew build
	 运行升级
         java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DREvTestUpgradeUserController group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem
     
       测试升級 usercontroller 合约 后原来的数据是否还在
       java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DREvTestGet group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem

     (3.5)测试升級 rightcontroller 合约功能
         重新在 console中部署一个新的 DREvidenceRightController
         deploy DREvidenceRightController
       	 记录下新地址, 修改 DREvTestUpgradeRightController.java 中的新 strNewRightaddr 地址为新地址,重新 bash gradlew build
	 运行升级
         java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DREvTestUpgradeRightController group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem
     
       测试升級 rightcontroller 合约 后原来的数据是否还在
       java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DREvTestGet group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem

     (3.6)测试升級 reviewcontroller 合约功能
         重新在 console中部署一个新的 DREvidenceReviewController
         deploy DREvidenceReviewController
       	 记录下新地址, 修改 DREvTestUpgradeReivewController.java 中的新 strNewReviewaddr 地址为新地址,重新 bash gradlew build
	 运行升级
         java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DREvTestUpgradeReviewController group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem
     
       测试升級 reviewcontroller 合约 后原来的数据是否还在
       java -cp 'conf/:lib/*:apps/*' org.fisco.bcos.sdk.demo.contractTest.DREvTestGet group0 /home/duvon/fisco/console/account/gm/0xf0a27ef37e77f6b9cfa8e3592172c9844d32975f.pem


