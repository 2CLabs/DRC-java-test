// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./utils/DAEvidenceString.sol";
import "./utils/DAEvidenceMap.sol";
import "./DAAccessController.sol";
import "./storage/DAEvidenceStorage.sol";
import "./storage/DAEvidenceStorageLib.sol";
import "@openzeppelin/contracts/utils/Strings.sol";
import "@openzeppelin/contracts-upgradeable/proxy/utils/Initializable.sol";

contract DAEvidenceController is Initializable, DAAccessController, DAEvidenceStorage {

    using DAEvidenceMap for DAEvidenceMap.DAMappingString;
    using DAEvidenceMap for DAEvidenceMap.DAMappingUint32;
    using DAEvidenceMap for DAEvidenceMap.DAMappingStringArray;
    using DAEvidenceMap for DAEvidenceMap.DAMappingBytes32;
    using Strings for uint32;
    using DAEvidenceString for string;
    using DAEvidenceString for bytes32;
    using DAEvidenceString for uint256;
    // using DAEvidenceString for uint32;

    using DAEvidenceStorageLib for DAEvidenceStorageLib.DAEStorage;




    /******************************************** 变量 **************************************************/
    string private _chainName;
    uint32 private _textMaxLen;
    uint32 private _strArrayMaxLen;
    bool private _isAccessControlEnabled;

    function initialize() public initializer {
        _setupRole(DEFAULT_ADMIN_ROLE, msg.sender);
        // _setRoleAdmin(DAEVIDENCE_ROLE, APPROVER_ROLE);
        grantUserManagePermission("bid:0", msg.sender);
        _chainName = "ShuXin";
        _textMaxLen = 5000;
        _strArrayMaxLen = 1000;
        _isAccessControlEnabled = false;
    }

    function setChainName(string memory name) public onlyRole(DEFAULT_ADMIN_ROLE) {
        _chainName = name;
    }

    function setTextMaxLen(uint32 len) public onlyRole(DEFAULT_ADMIN_ROLE) {
        _textMaxLen = len;
    }

    function setstrArrayMaxLen(uint32 len) public onlyRole(DEFAULT_ADMIN_ROLE) {
        _strArrayMaxLen = len;
    }

    //set isAccessControlEnabled to true to enable access control
    function enableAccessControl() public onlyRole(DEFAULT_ADMIN_ROLE) {
        _isAccessControlEnabled = true;
    }

    //set isAccessControlEnabled to false to disable access control
    function disableAccessControl() public onlyRole(DEFAULT_ADMIN_ROLE) {
        _isAccessControlEnabled = false;
    }

    /*
        可以以后添加该接口
        eid: it is outer Eid
        category: ""
        fixData: ["urid", "", "bid", "", "dataHash_1": "", "dataHash_2": "", ""]
    */
    // function getDAEvidenceById(string memory eid) public view returns (string memory category, string[] memory fixData, string[] memory metaData, string[] memory variableData, uint256 timestamp) {
    //     // TODO
    //     bytes32 innerEid = dataStorage._GetInnerEidFromOuterEid(eid.hash());
    //     DAEvidenceStorageLib.CommEvidence storage evidence = dataStorage._getCommEvidence(innerEid);
    //     category = evidence.category;
    //     metaData = evidence.metaData;
    //     variableData = evidence.variableData;
    //     timestamp = evidence.timestamp;

    //     // TODO: 不同类型的存证， fixData 数据字段不一样
    // }

    /******************************************** 内部接口 **************************************************/
    

    // 获取当前某种存证支持的 variableData 字段
    function getSupportVariableDataFields(string memory category) public view returns (string[] memory fileds) {
        string memory keyStr = category.concat(":variable_data:supported_fileds=");
        bytes32 key = keccak256(bytes(keyStr));
        return dataStorage.commData.byte32ToStringArrary.get(key);
    }

    // 设置某种存证 variableData 支持的字段，实现逻辑是：先将之前的所有fields都清除，再重新设置，原则上是只能在原有基础上新加字段，减少字段会导致之前保存的字段不能获取到
    function setDataRightSupportVariableDataFields(string memory category, string[] memory fields) public onlyRole(DEFAULT_ADMIN_ROLE) {
        // TODO: 可以设置成开关，支持验证或则不验证这些字段
        // Clean current
        string memory keyStr = category.concat(":variable_data:supported_fileds=");
        bytes32 vbKey = keccak256(bytes(keyStr));
        string[] memory variableDataFields = dataStorage.commData.byte32ToStringArrary.get(vbKey);
        for (uint32 i = 0; i < variableDataFields.length; i++) {
            string memory vbFieldKeyStr = category.concat("variable_data:key=");
            bytes32 key = keccak256(bytes(vbFieldKeyStr.concat(fields[i])));
            dataStorage.commData.byte32ToUint32.update(key, 0);
        }
        // Set
        for (uint32 i = 0; i < fields.length; i++) {
            string memory vbFieldKeyStr = category.concat("variable_data:key=");
            bytes32 key = keccak256(bytes(vbFieldKeyStr.concat(fields[i])));
            dataStorage.commData.byte32ToUint32.update(key, 1);
        }
    }

    // 数据权限管理--- 设置分类
    function setDataRightCategory(string[] memory fields) public onlyRole(DEFAULT_ADMIN_ROLE) {
        // Clean current
        bytes32 vbKey = keccak256(bytes("data_right_category:"));
        string[] memory variableDataFields = dataStorage.commData.byte32ToStringArrary.get(vbKey);
        for (uint32 i = 0; i < variableDataFields.length; i++) {
            string memory vbFieldKeyStr = "data_right_category:key=";
            bytes32 key = keccak256(bytes(vbFieldKeyStr.concat(fields[i])));
            dataStorage.commData.byte32ToUint32.update(key, 0);
        }
        // Set
        for (uint32 i = 0; i < fields.length; i++) {
            string memory vbFieldKeyStr = "data_right_category:key=";
            bytes32 key = keccak256(bytes(vbFieldKeyStr.concat(fields[i])));
            dataStorage.commData.byte32ToUint32.update(key, 1);
        }
        dataStorage.commData.byte32ToStringArrary.update(vbKey, fields);
    }

    // 检查sender是 存证 owner
    function _requireDataRightEvidenceOwnerByUrid(string memory udri) internal view{
        bytes32 innerEid = keccak256(
            bytes(DAEvidenceStorageLib.EVIDENCE_CATEGORY_RIGHT.concat(udri))
        );
        (bool isExist, string memory bid) = dataStorage._getUserBidByAccount(msg.sender);
        require(isExist == true, "Sender is not registered.");

        DAEvidenceStorageLib.CommEvidence storage evidence = dataStorage._getCommEvidence(innerEid);
        require(evidence.timestamp != 0, "evidence is not exist.");

        string memory bidInEvidence = dataStorage._getCommEvidenceIndefiniteString(innerEid, "bid");

        require(bid.equal(bidInEvidence) == true, "Sender is not evidence owner.");
    }

    // 检查sender是否是某个角色
    function _checkUserRole(string memory role) internal view{
        (bool isExist, string memory bid) = dataStorage._getUserBidByAccount(msg.sender);
        require(isExist == true, "Sender is not registered.");
        DAEvidenceStorageLib.UserInfoV1 storage user = dataStorage._getUseStoragerByBid(bid);
        require((user.indefiniteString.get(DAEvidenceStorageLib.USER_ROLE_PREFIX.concat(role).hash()).equal("exist")) == true, "User without corresponding role permissions.");
    }

    /******************************************** 用户 **************************************************/
    function queryUserRole() public view returns (string[] memory) {
        return DAEvidenceStorageLib.queryUserRole();
    }
    
    // 3.1 配置管理权限
    /*
    3.1.1 配置管理权限
    - 功能定位
    由运营方(合约所有者)在链上给某个机构帐号地址赋予用户管理权限。
    - 调用限制
    该接口只有运营方(合约所有者)能调用。

    DAAccessController 中已经定义
    */
    // function grantUserManagePermission(string memory bid, address account) public {
    // }

    /*
    3.1.2 回收管理权限
    由运营方(合约所有者)在链上收回某个机构帐号地址的用户管理权限。
    - 调用限制
    该接口只有运营方(合约所有者)能调用。

    DAAccessController 中已经定义
    */
    // function revokeUserManagePermission(string memory bid) public {
    // }

    /*
    3.2 用户角色管理
    3.2.1 添加用户
    由用户管理员在链上添加用户信息，同时可以绑定用户角色。
    */
    function addUser(string memory bid, string memory usci, string memory name,address account, string[] memory roles) public onlyRole(USERMANAGE_ROLE) {
        dataStorage.addUser(bid, usci, name, account, roles);
    }

    /*
    3.2.2  查询用户
    根据用户bid查询当前用户信息及其绑定的角色。
    */
    function getUserRoles(string memory bid) public view returns(string memory usci, string memory name, string[] memory roles) {
        return dataStorage.getUserRoles(bid);
    }

    /*
    3.2.3 添加用户角色
    为用户添加新的角色。
    */
    function grantUserRoles(string memory bid, string[] memory roles) public onlyRole(USERMANAGE_ROLE) {
        dataStorage.grantUserRoles(bid, roles);
    }

    /*
    3.2.4 回收用户角色
    由用户管理员回收用户的一或多个角色。
    */
    function revokeUserRoles(string memory bid, string[] memory roles) public onlyRole(USERMANAGE_ROLE) {
        dataStorage.revokeUserRoles(bid, roles);
    }

    /* 4.3 登记存证（二阶段）*/

    /* 5. 查询相关接口 */
    /* 5.1  查询用户数据信息 */
    /* 5.1.1 查询用户数据数量 */
    function getDataCount(string calldata bid) public view returns (uint256 count) {
        return dataStorage.getDataCount(bid);
    }

    /* 5.1.2  查询用户数据列表 */
    function getDataList(string memory bid, uint256 start, uint256 count) public view returns (string[] memory udriArray) {
        return dataStorage.getDataList(bid, start, count);
    }

    /******************************************** 审查存证 **************************************************/
    /* 4.2 审查存证 */
    /* 4.2.1 新增审查存证 */
    function addReviewEvidence(string memory udri, string memory reviewerBid, string[] memory reviewDataHash, string[] memory metaData, string[] memory variableData) public {
        _checkUserRole(DAEvidenceStorageLib.USER_ROLE_REVIEWER);
        dataStorage.addReviewEvidence(udri, reviewerBid, reviewDataHash, metaData, variableData);
        (, string memory outerEid) = DAEvidenceStorageLib.genEidViaUrdi(udri, DAEvidenceStorageLib.EVIDENCE_CATEGORY_REVIEW);
        _emitNewEvidence(outerEid);
    }

    /* 4.2.2 撤回审查存证 */
    function withdrawReviewEvidence(string memory udri, string memory reviewerBid) public {
        _checkUserRole(DAEvidenceStorageLib.USER_ROLE_REVIEWER);
        dataStorage.withdrawReviewEvidence(udri, reviewerBid);
    }

    /* 5.3  查询审查存证信息 */
    /* 5.3.1 查询审查存证数量 */
    function getReviewCount(string calldata udri) public view returns (uint256 count) {
        // TODO: 修改文档那边，方法定义不一致
        return dataStorage.getReviewCount(udri);
    }

    /* 5.3.2 查询审查存证信息 */
    function getVerifyDAEvidence(string calldata udri, uint32 index) public view returns (string memory reviewerBid, string[] memory metaData, string[] memory variableData)  {
        return dataStorage.getVerifyDAEvidence(udri, index);
    }

    // 获取某个审核机构对某个数据存证的 审核次数
    function getReviewCountOfUser(string calldata udri, string calldata reviewerBid) public view returns (uint256 count) {
        // TODO: 修改文档那边，方法定义不一致
        return dataStorage.getReviewCountOfUser(udri, reviewerBid);
    }

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
    ) public {
        _checkUserRole(DAEvidenceStorageLib.USER_ROLE_DATA_HOLDER);
        dataStorage.addDataRightEvidence(udri, bid, dataHash, dataRight, metaData, variableData);
        (, string memory outerEid) = DAEvidenceStorageLib.genEidViaUrdi(udri, DAEvidenceStorageLib.EVIDENCE_CATEGORY_RIGHT);
        _emitNewEvidence(outerEid);
    }

    /* 4.1.2  追加可变数据存证 */
    function appendVariableData(
        string memory udri,
        string[] memory variableData
    ) public {
        _checkUserRole(DAEvidenceStorageLib.USER_ROLE_DATA_HOLDER);
        _requireDataRightEvidenceOwnerByUrid(udri);
        dataStorage.appendVariableData(udri, variableData);
    }

    /* 4.1.3 撤回确权存证 */
    function withdrawDataRightRegister(
        string memory udri,
        /*string memory dataRightOwnerBid, */
        string[] memory dataRight
    ) public {
        _checkUserRole(DAEvidenceStorageLib.USER_ROLE_DATA_HOLDER);
        _requireDataRightEvidenceOwnerByUrid(udri);
        dataStorage.withdrawDataRightRegister(udri, dataRight);
    }

    /* 4.1.4 新增授权存证 */
    function grantUserDataRight(
        string memory udri,
        string memory bid,
        string[] memory dataRight
    ) public {
        _checkUserRole(DAEvidenceStorageLib.USER_ROLE_DATA_HOLDER);
        _requireDataRightEvidenceOwnerByUrid(udri);
        dataStorage.grantUserDataRight(udri, bid, dataRight);
    }

    /* 4.1.5 撤回授权存证 */
    function withdrawUserDataRight(
        string memory udri,
        string memory bid,
        string[] memory dataRight
    ) public {
        _checkUserRole(DAEvidenceStorageLib.USER_ROLE_DATA_HOLDER);
        _requireDataRightEvidenceOwnerByUrid(udri);
        dataStorage.withdrawUserDataRight(udri, bid, dataRight);
    }

    /* 5.2 查询确权存证信息 */
    /* 5.2.1 查询存证数据标识 */
    function getUdriByDatahash(string calldata dataHash)
        public
        view
        returns (string memory udri)
    {
        return dataStorage.getUdriByDatahash(dataHash);
    }

    /* 5.2.2 查询确权存证信息 */
    function getRegisteredData(string memory udri)
        public
        view
        returns (
            string memory dataHashSM,
            string memory dataHashSHA,
            string[] memory dataRight,
            string[] memory metadata,
            string[] memory variabledata,
            bool isWithdraw
        )
    {
        return dataStorage.getRegisteredData(udri);
    }

    /* 5.2.3 查询授权信息 */
    function getUserDataRight(string memory udri, string memory bid)
        public
        view
        returns (string[] memory outRights)
    {
        return dataStorage.getUserDataRight(udri, bid);
    }
}
