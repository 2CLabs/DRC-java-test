// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

interface IDREvidence  {
    /******************************************** Admin **************************************************/

    function setChainName(string memory name) external;

    function setTextMaxLen(uint32 len) external;

    function setstrArrayMaxLen(uint32 len) external;

    //set isAccessControlEnabled to true to enable access control
    function enableAccessControl() external;

    //set isAccessControlEnabled to false to disable access control
    function disableAccessControl() external;

    function getChainName() external view returns(string memory name);

    function getTextMaxLen() external view returns(uint32 len);

    function getstrArrayMaxLen() external view returns(uint32 len);

    function getAccessControl() external view returns(uint32 status);
    
    // 获取当前某种存证支持的 variableData 字段
    function getSupportVariableDataFields(string memory category) external view returns (string[] memory fileds);

    // 设置某种存证 variableData 支持的字段，实现逻辑是：先将之前的所有fields都清除，再重新设置，原则上是只能在原有基础上新加字段，减少字段会导致之前保存的字段不能获取到
    function setDataRightSupportVariableDataFields(string memory category, string[] memory fields) external;

    // 数据权限管理--- 设置分类 （1.数据资源持有权; 2.数据加工使用权; 3.数据产品经营权。）
    function setDataRightCategory(string[] memory fields) external;

    // 数据权限管理--- 获取分类
    function getDataRightCategory() external view returns(string[] memory fields);

    /******************************************** 用户 **************************************************/
    function queryUserRole() external view returns (string[] memory);
    
    // 3.1 配置管理权限
    /*
    3.1.1 配置管理权限
    - 功能定位
    由运营方(合约所有者)在链上给某个机构帐号地址赋予用户管理权限。
    - 调用限制
    该接口只有运营方(合约所有者)能调用。

    DRAccessController 中已经定义
    */
    function grantUserManagePermission(string memory bid, address account) external;

    /*
    3.1.2 回收管理权限
    由运营方(合约所有者)在链上收回某个机构帐号地址的用户管理权限。
    - 调用限制
    该接口只有运营方(合约所有者)能调用。

    DRAccessController 中已经定义
    */
    function revokeUserManagePermission(string memory bid) external;

    function hasUserManageRole(string memory bid) external view returns (bool);

    /*
    3.2 用户角色管理
    3.2.1 添加用户
    由用户管理员在链上添加用户信息，同时可以绑定用户角色。
    */
    function addUser(string memory bid, string memory usci, string memory name,address account, string[] memory roles) external;

    /*
    3.2.2  查询用户
    根据用户bid查询当前用户信息及其绑定的角色。
    */
    function getUserRoles(string memory bid) external view returns(string memory usci, string memory name, string[] memory roles);

    /*
    3.2.3 添加用户角色
    为用户添加新的角色。
    */
    function grantUserRoles(string memory bid, string[] memory roles) external;

    /*
    3.2.4 回收用户角色
    由用户管理员回收用户的一或多个角色。
    */
    function revokeUserRoles(string memory bid, string[] memory roles) external;

    /* 4.3 登记存证（二阶段）*/

    /* 5. 查询相关接口 */
    /* 5.1  查询用户数据信息 */
    /* 5.1.1 查询用户数据数量 */
    function getDataCount(string calldata bid) external view returns (uint256 dataCount);

    /* 5.1.2  查询用户数据列表 */
    function getDataList(string memory bid, uint256 start, uint256 count) external view returns (string[] memory udriArray);

    /******************************************** 审查存证 **************************************************/
    /* 4.2 审查存证 */
    /* 4.2.1 新增审查存证 */
    function addReviewEvidence(string memory udri, string memory reviewerBid, string[] memory reviewDataHash, string[] memory metaData, string[] memory variableData) external;

    /* 4.2.2 撤回审查存证 */
    function withdrawReviewEvidence(string memory udri, string memory reviewerBid) external;

    /* 5.3  查询审查存证信息 */
    /* 5.3.1 查询审查存证数量 */
    function getReviewCount(string calldata udri) external view returns (uint256 reviewCount);

    /* 5.3.2 查询审查存证信息 */
    function getVerifyEvidence(string calldata udri, uint32 index) external view returns (bool isWithdraw, string memory reviewerBid, string[] memory metaData, string[] memory variableData);

    // 获取某个审核机构对某个数据存证的 审核次数
    function getReviewCountOfReviewer(string calldata udri, string calldata reviewerBid) external view returns (uint256 count);

    // 获取某个审核机构对某个数据存证的 某次审核信息
    function getVerifyEvidenceOfReviewer(string calldata udri, string calldata reviewerBid, uint32 index) external view returns (bool isWithdraw, string[] memory metaData, string[] memory variableData);
    /******************************************** 确权存证**************************************************/
    /*4. 存证 */
    /* 4.1.1 新增确权存证 */
    function addDataRightEvidence(
        string memory udri,
        string memory bid,
        string[] memory dataHash,
        string[] memory dataRight,
        string[] memory metaData,
        string[] memory variableData
    ) external;

    /* 4.1.2  追加可变数据存证 */
    function appendVariableData(
        string memory udri,
        string[] memory variableData
    ) external;

    /* 4.1.3 撤回确权存证 */
    function withdrawDataRightRegister(
        string memory udri,
        /*string memory dataRightOwnerBid, */
        string[] memory dataRight
    ) external;

    /* 4.1.4 新增授权存证 */
    function grantUserDataRight(
        string memory udri,
        string memory bid,
        string[] memory dataRight
    ) external;

    /* 4.1.5 撤回授权存证 */
    function withdrawUserDataRight(
        string memory udri,
        string memory bid,
        string[] memory dataRight
    ) external;
    /* 5.2 查询确权存证信息 */
    /* 5.2.1 查询存证数据标识 */
    function getUdriByDatahash(string calldata dataHash)
        external
        view
        returns (string memory udri);

    /* 5.2.2 查询确权存证信息 */
    function getRegisteredData(string memory udri)
        external
        view
        returns (
            string memory dataHashSM,
            string memory dataHashSHA,
            string[] memory dataRight,
            string[] memory metaData,
            string[] memory variableData,
            bool isWithdraw
        );

    /* 5.2.3 查询授权信息 */
    function getUserDataRight(string memory udri, string memory bid)
        external
        view
        returns (string[] memory dataRight);
}
