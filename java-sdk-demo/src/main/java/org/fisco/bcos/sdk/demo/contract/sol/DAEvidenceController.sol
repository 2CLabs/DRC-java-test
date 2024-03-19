// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./utils/DAEvidenceString.sol";
import "./utils/DAEvidenceMap.sol";
import "./DAAccessController.sol";
import "./storage/DAEvidenceStorage.sol";
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

    /******************************************** 常量 **************************************************/
    string public constant USER_ROLE_PREFIX = "user_role:";

    // 用户角色
    string public constant USER_ROLE_DATA_HOLDER = "data_holder";
    string public constant USER_ROLE_REVIEWER = "reviewer";
    string public constant USER_ROLE_REGISTRY = "registry";
    string public constant USER_ROLE_ELSE = "platform";

    // 存证类型名, 确权存证、审核存证、登记存证 等
    string public constant EVIDENCE_CATEGORY_RIGHT = "right";
    string public constant EVIDENCE_CATEGORY_REVIEW = "review";

    // 存证状态
    string public constant EVIDENCE_STATUS_ACTIVE = "active";
    string public constant EVIDENCE_STATUS_DISABLED = "disabled";

    string public constant EVIDENCE_CONTRACT_VERSION_V1 = "V1";

    string public constant EVIDENCE_UDRI = "udri";
    string public constant EVIDENCE_BID = "bid";

    // 不同类型存证eid的前缀
    string public constant EVIDENCE_ID_PREFIX_NEW = "eid:new:";// 确权存证 eid 前缀
    string public constant EVIDENCE_ID_PREFIX_REVIEW = "eid:review:";// 审核存证 eid 前缀

    string public constant EVIDENCE_DATA_REVIEW_COUNT = "review_count="; // 某个确权存证数据，所有审核数据总数
    string public constant EVIDENCE_DATA_USER_REVIEW_COUNT = ":review_count="; // 某个机构对某个确权存证数据，上传的审核次数 `${reviewer_bid}:review_count=`
    string public constant EVIDENCE_DATA_USER_REVIEW_INDEX = ":review:index:"; // 该KEY用于查询某个机构对某个确权存证数据第N个审核存证， `${reviewer_bid}:review:index:${index}`
    string public constant EVIDENCE_DATA_REVIEW_INDEX = "review:index:"; // 该KEY用于查询某个确权存证数据第N个审核存证， `review:index:${index}`

    // 数据权限前缀，用于构建一个key 映射 某个用户对数据某个操作权限
    string public constant EVIDENCE_DATA_RIGHT_PREFIX = "data_right:";// 数据权限前缀，`data_right:${bid}${right_name}`

    // 用于公共数据
    string public constant EVIDENCE_RIGHT_EID_WITH_INDEX = "e_right:index:"; // 该KEY用于查询用户第N个确权存证 `e_right:index:${index}`
    string public constant EVIDENCE_RIGHT_VARIABLE_DATA_KEY = "e_right:variable_data";// 该KEY勇于查询 确权存证中 可变数据 允许的字段
    string public constant EVIDENCE_REVIEW_VARIABLE_DATA_KEY = "e_review:variable_data";// 该KEY勇于查询 确权存证中 可变数据 允许的字段

    string public constant EVIDENCE_RIGHT_VARIABLE_DATA_MIDDLE = "variable_data:key=";// `${CATEGORY}:variable_data:key=${key}`


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
    function getDAEvidenceById(string memory eid) public view returns (string memory category, string[] memory fixData, string[] memory metaData, string[] memory variableData, uint256 timestamp) {
        // TODO
        bytes32 innerEid = _GetInnerEidFromOuterEid(eid.hash());
        CommEvidence storage evidence = _getCommEvidence(innerEid);
        category = evidence.category;
        metaData = evidence.metaData;
        variableData = evidence.variableData;
        timestamp = evidence.timestamp;

        // TODO: 不同类型的存证， fixData 数据字段不一样
    }

    /******************************************** 内部接口 **************************************************/
    
    // 验证bid 是否合法 以及 是否登记
    function _verifyBid(string memory bid) internal view {
        require(bytes(bid).length > 0, "bid error");
        require(_userExist(bid) == true, "bit not register on this chain");
    }

    // 检查某种存证variableData中是否支持某个字段
    function isSupportedInVariableDataFields(string memory category, string memory field) public view returns(bool) {
        string memory vbFieldKeyStr = category.concat(EVIDENCE_RIGHT_VARIABLE_DATA_MIDDLE).concat(field);
        bytes32 key = keccak256(bytes(vbFieldKeyStr));
        return commData.byte32ToUint32.get(key) == 1;
    }

    // 设置某种存证的 variableData 数据
    function _setVariableData(bytes32 innerEid, string memory category, string[] memory variableData) internal{
        require(variableData.length % 2 == 0, "variableData length error");
        CommEvidence storage evidence = _getCommEvidence(innerEid);

        for (uint32 i = 0; i < variableData.length; i += 2) {
            string memory key = variableData[i];
            string memory value = variableData[i + 1];
            require(isSupportedInVariableDataFields(category, key) == true, "part of variableData field is not allown");
            evidence.variableDataMap.update(keccak256(bytes(key)), value);
        }
    }

    // 获取当前某种存证支持的 variableData 字段
    function getSupportVariableDataFields(string memory category) public view returns (string[] memory) {
        string memory keyStr = category.concat(":variable_data:supported_fileds=");
        bytes32 key = keccak256(bytes(keyStr));
        return commData.byte32ToStringArrary.get(key);
    }

    // 设置某种存证 variableData 支持的字段，实现逻辑是：先将之前的所有fields都清除，再重新设置，原则上是只能在原有基础上新加字段，减少字段会导致之前保存的字段不能获取到
    function setDataRightSupportVariableDataFields(string memory category, string[] memory fields) public {
        // TODO: 限制合约管理员可调用
        // Clean current
        string memory keyStr = category.concat(":variable_data:supported_fileds=");
        bytes32 vbKey = keccak256(bytes(keyStr));
        string[] memory variableDataFields = commData.byte32ToStringArrary.get(vbKey);
        for (uint32 i = 0; i < variableDataFields.length; i++) {
            string memory vbFieldKeyStr = category.concat("variable_data:key=");
            bytes32 key = keccak256(bytes(vbFieldKeyStr.concat(fields[i])));
            commData.byte32ToUint32.update(key, 0);
        }
        // Set
        for (uint32 i = 0; i < fields.length; i++) {
            string memory vbFieldKeyStr = category.concat("variable_data:key=");
            bytes32 key = keccak256(bytes(vbFieldKeyStr.concat(fields[i])));
            commData.byte32ToUint32.update(key, 1);
        }
        commData.byte32ToStringArrary.update(vbKey, variableDataFields);
    }

    // 数据权限管理--- 设置分类 TODO: 限制合约管理员可调用
    function setDataRightCategory(string[] memory fields) public {
        // Clean current
        bytes32 vbKey = keccak256(bytes("data_right_category:"));
        string[] memory variableDataFields = commData.byte32ToStringArrary.get(vbKey);
        for (uint32 i = 0; i < variableDataFields.length; i++) {
            string memory vbFieldKeyStr = "data_right_category:key=";
            bytes32 key = keccak256(bytes(vbFieldKeyStr.concat(fields[i])));
            commData.byte32ToUint32.update(key, 0);
        }
        // Set
        for (uint32 i = 0; i < fields.length; i++) {
            string memory vbFieldKeyStr = "data_right_category:key=";
            bytes32 key = keccak256(bytes(vbFieldKeyStr.concat(fields[i])));
            commData.byte32ToUint32.update(key, 1);
        }
        commData.byte32ToStringArrary.update(vbKey, fields);
    }

    // 数据权限管理--- 获取数据权限当前分类
    function getDataRightCategory() public view returns (string[] memory) {
        bytes32 vbKey = keccak256(bytes("data_right_category:"));
        return commData.byte32ToStringArrary.get(vbKey);
    }

    // 检查数据Hash是否已经存证
    function checkUdriOnChain(string memory udri) public view returns (bool) {
        bytes32 innerEid = commData.byte32ToBytes32.get(keccak256(bytes(EVIDENCE_UDRI.concat(udri))));
        return  innerEid != bytes32(0);
    }
    // 根据udri获取 innerEid
    function _getInnerEidByUdri(string memory udri) internal view returns (bytes32) {
        return commData.byte32ToBytes32.get(keccak256(bytes(EVIDENCE_UDRI.concat(udri))));
    }

    // 关联数据Hash 以及 innerEid
    function _setUdriOnChain(string memory udri, bytes32 innerEid) internal {
        commData.byte32ToBytes32.update(keccak256(bytes(EVIDENCE_UDRI.concat(udri))), innerEid);
    }
    
    // 检查数据Hash是否已经存证
    function checkDataHashOnChain(string memory dataHash) public view returns (bool) {
        bytes32 innerEid = commData.byte32ToBytes32.get(keccak256(bytes(dataHash)));
        return  innerEid != bytes32(0);
    }

    function _getInnerEidByDataHash(string memory dataHash) internal view returns (bytes32) {
        return commData.byte32ToBytes32.get(keccak256(bytes(dataHash)));
    }

    // 检查数据Hash是否已经存证, 任意一个dataHash存在都算存在
    function checkDataHashOnChain(string[] memory dataHash) public view returns (bool) {
        for (uint32 i = 0; i < dataHash.length; i++) {
            bytes32 innerEid = commData.byte32ToBytes32.get(keccak256(bytes(dataHash[i])));
            if  (innerEid != bytes32(0)) return true;
        }
        return false;
    }

    // 关联数据Hash 以及 innerEid
    function _setDataHashOnChain(string[] memory dataHash, bytes32 innerEid) internal {
        for (uint32 i = 0; i < dataHash.length; i++) {
            commData.byte32ToBytes32.update(keccak256(bytes(dataHash[i])), innerEid);
        }
    }

    // 检查sender是 存证 owner
    function _requireDataRightEvidenceOwner(bytes32 innerEid) internal view{
        (bool isExist, string memory bid) = _getUserBidByAccount(msg.sender);
        require(isExist == true, "Sender is not registered.");

        CommEvidence storage evidence = _getCommEvidence(innerEid);
        require(evidence.timestamp != 0, "evidence is not exist.");

        string memory bidInEvidence = _getCommEvidenceIndefiniteString(innerEid, "bid");

        require(bid.equal(bidInEvidence) == true, "Sender is not evidence owner.");
    }

    // 检查sender是否是某个角色
    function _checkUserRole(string memory role) internal view{
        (bool isExist, string memory bid) = _getUserBidByAccount(msg.sender);
        require(isExist == true, "Sender is not registered.");
        UserInfoV1 storage user = _getUseStoragerByBid(bid);
        require((user.indefiniteString.get(USER_ROLE_PREFIX.concat(role).hash()).equal("exist")) == true, "User without corresponding role permissions.");
    }

    /******************************************** 用户 **************************************************/
    function revokeUserRole(string memory bid, string[] memory roles) public {
        for(uint i = 0; i < roles.length; i++)
          _removeUserRole(bid, roles[i]);
    }


    function queryUserRole() public view returns (string[] memory) {
        // return ["dataRightOwner", "reviewer", "registry", "platform"];
        string[] memory roles = new string[](4);
        roles[0] = USER_ROLE_DATA_HOLDER;
        roles[1] = USER_ROLE_REVIEWER;
        roles[2] = USER_ROLE_REGISTRY;
        roles[3] = USER_ROLE_ELSE;
        return roles;
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
        // TODO: 检查用户是否已经存在
        require(_userExist(bid) == false, "User already exist.");
        // string memory bid, string memory usci, string memory name, address account
        _storeUser(bid, usci, name, account);
        UserInfoV1 storage user = _getUseStoragerByBid(bid);
        for(uint32 i = 0; i < roles.length; i++) {
            user.indefiniteString.update(USER_ROLE_PREFIX.concat(roles[i]).hash(), "exist");
        }
    }

    /*
    3.2.2  查询用户
    根据用户bid查询当前用户信息及其绑定的角色。
    */
    function getUserRoles(string memory bid) public view returns(string memory usci, string memory name, string[] memory roles) {
        require(_userExist(bid) == true, "User not exist.");
        UserInfoV1 storage user = _getUseStoragerByBid(bid);
        usci = user.usci;
        name = user.name;
        string[] memory supporedRoles = queryUserRole();
        uint32 len = 0;
        for(uint32 i = 0; i < supporedRoles.length; i++) {
            if (user.indefiniteString.get(USER_ROLE_PREFIX.concat(supporedRoles[i]).hash()).equal("exist")) {
                len = len + 1;
            }
        }
        if (len > 0) {
            uint32 j = 0;
            string[] memory rolesTmp = new string[](len);
            for(uint32 i = 0; i < supporedRoles.length; i++) {
                if (user.indefiniteString.get(USER_ROLE_PREFIX.concat(supporedRoles[i]).hash()).equal("exist")) {
                    // roles[j] = supporedRoles[i];
                    rolesTmp[j] = supporedRoles[i];
                    j = j + 1;
                }
                
            }
            roles = rolesTmp;
        }
    }

    /*
    3.2.3 添加用户角色
    为用户添加新的角色。
    */
    function grantUserRoles(string memory bid, string[] memory roles) public onlyRole(USERMANAGE_ROLE) {
        require(_userExist(bid) == true, "User not exist.");
        UserInfoV1 storage user = _getUseStoragerByBid(bid);
        for(uint32 i = 0; i < roles.length; i++) {
            user.indefiniteString.update(roles[i].hash(), "exist");
        }
    }

    /*
    3.2.4 回收用户角色
    由用户管理员回收用户的一或多个角色。
    */
    function revokeUserRoles(string memory bid, string[] memory roles) public onlyRole(USERMANAGE_ROLE) {
        require(_userExist(bid) == true, "User not exist.");
        UserInfoV1 storage user = _getUseStoragerByBid(bid);
        for(uint32 i = 0; i < roles.length; i++) {
            user.indefiniteString.update(roles[i].hash(), "");
        }
    }

    /* 4.3 登记存证（二阶段）*/

    /* 5. 查询相关接口 */
    /* 5.1  查询用户数据信息 */
    /* 5.1.1 查询用户数据数量 */
    function getDataCount(string calldata bid) public view returns (uint256 dataCount) {
        require(_userExist(bid) == true, "User already exist.");
        UserInfoV1 storage user = _getUseStoragerByBid(bid);
        dataCount = user.evidenceCount;
    }

    /* 5.1.2  查询用户数据列表 */
    function getDataList(string memory bid, uint256 start, uint256 count) public view returns (string[] memory udriArray) {
        require(_userExist(bid) == true, "User already exist.");
        require(count > 0, "count is invalid.");
        UserInfoV1 storage user = _getUseStoragerByBid(bid);
        uint256 j = 0;
        string[] memory udriArrayTmp = new string[](count);
        for(uint256 i = start; i < start + count; i++) {
            bytes32 key = EVIDENCE_RIGHT_EID_WITH_INDEX.concat(i.toHexStringWithoutPrefix()).hash();
            bytes32 innerEid = user.indefiniteBytes32.get(key);
            udriArrayTmp[j] = _getCommEvidenceIndefiniteString(innerEid, "udri");
            j++;
        }
        udriArray = udriArrayTmp;
    }

    /******************************************** 审查存证 **************************************************/
    /* 4.2 审查存证 */
    /* 4.2.1 新增审查存证 */
    function addReviewEvidence(string memory udri,string memory reviewerBid, string[] memory reviewDataHash, string[] memory metaData, string[] memory variableData) public {
        // TODO: 检查该用户是否有审核角色
        _checkUserRole(USER_ROLE_REVIEWER);
        // TODO： 确保reviewDataHash没有被添加过
        _verifyBid(reviewerBid);

        // 确保udri对应的数据存证已经存在
        require(checkUdriOnChain(udri) == true, "udri not on chain.");

        bytes32 rightInnerEid = keccak256(bytes(EVIDENCE_CATEGORY_RIGHT.concat(udri))); //确权存证 内部eid

        string memory str = EVIDENCE_CATEGORY_REVIEW.concat(udri);
        for (uint32 i = 0; i < reviewDataHash.length; i++) {
            str = str.concat(reviewDataHash[i]);
        }
        bytes32 innerEid = keccak256(bytes(str)); //审核存证 内部eid

        _storeCommEvidence(innerEid, EVIDENCE_CATEGORY_REVIEW, metaData, variableData);
        _setCommEvidenceIndefiniteString(innerEid, "version", "EVIDENCE_CONTRACT_VERSION_V1");
        _setCommEvidenceIndefiniteString(innerEid, "udri", udri);
        _setCommEvidenceIndefiniteString(innerEid, "reviewerBid", reviewerBid);
        _setCommEvidenceIndefiniteStringArray(innerEid, "reviewDataHash", reviewDataHash);

        // 审核数目信息需要 放到 确权存证 相关的数据中
        string memory key = reviewerBid.concat(EVIDENCE_DATA_USER_REVIEW_COUNT); // 某个审查机构review数目
        uint32 current_number = _getCommEvidenceIndefiniteUint32(rightInnerEid, key);
        _setCommEvidenceIndefiniteUint32(rightInnerEid, key, current_number + 1);

        _setCommEvidenceIndefiniteBytes32(rightInnerEid, reviewerBid.concat(EVIDENCE_DATA_USER_REVIEW_INDEX).concat(current_number.toString()), innerEid);

        // 该 udri 数据，所有审查机构的review的数目
        current_number = _getCommEvidenceIndefiniteUint32(rightInnerEid, EVIDENCE_DATA_REVIEW_COUNT);
        _setCommEvidenceIndefiniteUint32(rightInnerEid, EVIDENCE_DATA_REVIEW_COUNT, current_number + 1);
        _setCommEvidenceIndefiniteBytes32(rightInnerEid, EVIDENCE_DATA_REVIEW_INDEX.concat(current_number.toString()), innerEid);

        _setVariableData(innerEid, EVIDENCE_CATEGORY_REVIEW, variableData);

        // Generate outerEid
        string memory outerEid = EVIDENCE_ID_PREFIX_REVIEW.concat(innerEid.toHexStringWithoutPrefix());
        _bindOuterInnerEid(keccak256(bytes(outerEid)), innerEid);
        _emitNewEvidence(outerEid);
    }


    /* 4.2.2 撤回审查存证 */
    function withdrawReviewEvidence(string memory udri, string memory reviewerBid) public {
        require(checkUdriOnChain(udri) == true, "udri not on chain.");
        _checkUserRole(USER_ROLE_REVIEWER);

        bytes32 rightInnerEid = keccak256(bytes(EVIDENCE_CATEGORY_RIGHT.concat(udri))); //确权存证 内部eid

        string memory reviewCountKey = reviewerBid.concat(EVIDENCE_DATA_USER_REVIEW_COUNT); // 某个审查机构review数目
        uint32 current_number = _getCommEvidenceIndefiniteUint32(rightInnerEid, reviewCountKey);
        for (uint32 i = 0; i < current_number; i++) {
            string memory key = reviewerBid.concat(EVIDENCE_DATA_USER_REVIEW_INDEX).concat(i.toString());
            bytes32 innerEid = _getCommEvidenceIndefiniteBytes32(rightInnerEid, key);
            _setCommEvidenceIndefiniteString(innerEid, "status", EVIDENCE_STATUS_DISABLED);
        }
    }

    /* 5.3  查询审查存证信息 */
    /* 5.3.1 查询审查存证数量 */
    function getReviewCount(string calldata udri) public view returns (uint256) {
        // TODO: 修改文档那边，方法定义不一致
        require(checkUdriOnChain(udri) == true, "udri not on chain.");

        bytes32 rightInnerEid = keccak256(bytes(EVIDENCE_CATEGORY_RIGHT.concat(udri))); //确权存证 内部eid
        return _getCommEvidenceIndefiniteUint32(rightInnerEid, EVIDENCE_DATA_REVIEW_COUNT);
    }

    /* 5.3.2 查询审查存证信息 */
    function getVerifyDAEvidence(string calldata udri, uint32 index) public view returns (string memory reviewerBid, string[] memory metaData, string[] memory variableData)  {
        // TODO
        require(checkUdriOnChain(udri) == true, "udri not on chain.");

        bytes32 rightInnerEid = keccak256(bytes(EVIDENCE_CATEGORY_RIGHT.concat(udri))); //确权存证 内部eid
        bytes32 innerEid = _getCommEvidenceIndefiniteBytes32(rightInnerEid, EVIDENCE_DATA_REVIEW_INDEX.concat(index.toString()));
        reviewerBid = _getCommEvidenceIndefiniteString(innerEid, "reviewerBid");
        (
            ,
            ,
            ,
            string[] memory _metaData,
            string[] memory _variableData
        ) = _getCommEvidenceById(innerEid);
        metaData = _metaData;
        variableData = _variableData;
    }

    // 获取某个审核机构对某个数据存证的 审核次数
    function getReviewCountOfUser(string calldata udri, string calldata reviewerBid) public view returns (uint256) {
        // TODO: 修改文档那边，方法定义不一致
        require(checkUdriOnChain(udri) == true, "udri not on chain.");
 
        bytes32 rightInnerEid = keccak256(bytes(EVIDENCE_CATEGORY_RIGHT.concat(udri))); //确权存证 内部eid
        string memory key = reviewerBid.concat(":review_count="); // 某个审查机构review数目
        return _getCommEvidenceIndefiniteUint32(rightInnerEid, key);
    }

    /******************************************** 确权存证**************************************************/
    
    function _addDataRight(
        bytes32 innerEid,
        string memory bid,
        string[] memory dataRight
    ) internal onlyRole(DEFAULT_ADMIN_ROLE){
        string memory prefix = "dataRight:";
        for (uint32 i = 0; i < dataRight.length; i++) {
            string memory key = prefix.concat(bid).concat(dataRight[i]);
            _setCommEvidenceIndefiniteString(innerEid, key, "exist");
        }
        _setCommEvidenceIndefiniteStringArray(
            innerEid,
            prefix.concat(bid),
            dataRight
        );
    }

    function _getDataRight(bytes32 innerEid, string memory bid)
        internal
        view
        returns (string[] memory)
    {
        string memory prefix = "dataRight:";
        return
            _getCommEvidenceIndefiniteStringArray(innerEid, prefix.concat(bid));
    }

    function _removeDataRight(
        bytes32 innerEid,
        string memory bid,
        string[] memory dataRight
    ) internal onlyRole(DEFAULT_ADMIN_ROLE){
        string memory prefix = "dataRight:";
        for (uint32 i = 0; i < dataRight.length; i++) {
            string memory key = prefix.concat(bid).concat(dataRight[i]);
            _setCommEvidenceIndefiniteString(innerEid, key, "");
        }
    }

    function _userWithDataRight(
        bytes32 innerEid,
        string memory bid,
        string memory dataRight
    ) internal view returns (bool) {
        string memory prefix = "dataRight:";
        string memory key = prefix.concat(bid).concat(dataRight);
        string memory value = _getCommEvidenceIndefiniteString(innerEid, key);
        return keccak256(bytes(value)) == keccak256(bytes("exist"));
    }

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
        require(dataHash.length == 2, "There should be 2 Data Hash.");
        // 确保dataHash没有被添加过
        require(
            checkDataHashOnChain(dataHash) == false,
            "Data Hash already on chain."
        );
        _checkUserRole(USER_ROLE_DATA_HOLDER);
        // 确保udri没有被添加过
        require(checkUdriOnChain(udri) == false, "udri already on chain.");
        // TODO: 是否应该使用 msg.sender 对应的bid 呢？是否msg.sender 和传入的bid必需对应一致呢？
        require(_userExist(bid) == true, "User not exist.");
        UserInfoV1 storage user = _getUseStoragerByBid(bid);
        _verifyBid(bid);

        bytes32 innerEid = keccak256(
            bytes(EVIDENCE_CATEGORY_RIGHT.concat(udri))
        );
 
        _storeCommEvidence(
            innerEid,
            EVIDENCE_CATEGORY_RIGHT,
            metaData,
            variableData
        );
        _setCommEvidenceIndefiniteString(
            innerEid,
            "version",
            "EVIDENCE_CONTRACT_VERSION_V1"
        );
        _setCommEvidenceIndefiniteString(innerEid, "udri", udri);
        _setCommEvidenceIndefiniteString(innerEid, "bid", bid);
        _setCommEvidenceIndefiniteString(
            innerEid,
            "status",
            EVIDENCE_STATUS_ACTIVE
        );
        _setCommEvidenceIndefiniteStringArray(innerEid, "dataHash", dataHash);

        _setDataHashOnChain(dataHash, innerEid);

        // _setCommEvidenceIndefiniteStringArray(innerEid, "dataRight", dataRight);
        _addDataRight(innerEid, bid, dataRight);

        // 添加关联到user，这样可以遍历用户所有确权存证
        _addEvidenceInUser(bid, EVIDENCE_RIGHT_EID_WITH_INDEX.concat(user.evidenceCount.toHexStringWithoutPrefix()).hash(), innerEid);

        // save variableData
        _setVariableData(innerEid, EVIDENCE_CATEGORY_RIGHT, variableData);

        // Generate outerEid
        string memory outerEid = EVIDENCE_ID_PREFIX_NEW.concat(
            innerEid.toHexStringWithoutPrefix()
        );
        _bindOuterInnerEid(keccak256(bytes(outerEid)), innerEid);
        _emitNewEvidence(outerEid);
    }

    /* 4.1.2  追加可变数据存证 */
    function appendVariableData(
        string memory udri,
        string[] memory variableData
    ) public {
        _checkUserRole(USER_ROLE_DATA_HOLDER);
        require(variableData.length > 0, "variableData is empty");
        require(variableData.length % 2 == 0, "variableData length error");
        bytes32 innerEid = keccak256(
            bytes(EVIDENCE_CATEGORY_RIGHT.concat(udri))
        );
        _requireDataRightEvidenceOwner(innerEid);
        _setVariableData(innerEid, EVIDENCE_CATEGORY_RIGHT, variableData);
    }

    /* 4.1.3 撤回确权存证 */
    function withdrawDataRightRegister(
        string memory udri,
        /*string memory dataRightOwnerBid, */
        string[] memory dataRight
    ) public {
        _checkUserRole(USER_ROLE_DATA_HOLDER);
        bytes32 innerEid = keccak256(
            bytes(EVIDENCE_CATEGORY_RIGHT.concat(udri))
        );
        _requireDataRightEvidenceOwner(innerEid);
        // TODO: dataRightOwnerBid 似乎可以不需要填入
        string memory bid = _getCommEvidenceIndefiniteString(innerEid, "bid");
        _removeDataRight(innerEid, bid, dataRight);
    }

    /* 4.1.4 新增授权存证 */
    function grantUserDataRight(
        string memory udri,
        string memory bid,
        string[] memory dataRight
    ) public {
        _checkUserRole(USER_ROLE_DATA_HOLDER);
        _verifyBid(bid);
        bytes32 innerEid = keccak256(
            bytes(EVIDENCE_CATEGORY_RIGHT.concat(udri))
        );
        _requireDataRightEvidenceOwner(innerEid);
        _addDataRight(innerEid, bid, dataRight);
    }

    /* 4.1.5 撤回授权存证 */
    function withdrawUserDataRight(
        string memory udri,
        string memory bid,
        string[] memory dataRight
    ) public {
        _checkUserRole(USER_ROLE_DATA_HOLDER);
        _verifyBid(bid);
        bytes32 innerEid = keccak256(
            bytes(EVIDENCE_CATEGORY_RIGHT.concat(udri))
        );
        _requireDataRightEvidenceOwner(innerEid);
        _removeDataRight(innerEid, bid, dataRight);
    }

    /* 5.2 查询确权存证信息 */
    /* 5.2.1 查询存证数据标识 */
    function getUdriByDatahash(string calldata dataHash)
        public
        view
        returns (string memory)
    {
        bytes32 innerEid = _getInnerEidByDataHash(dataHash);
        return _getCommEvidenceIndefiniteString(innerEid, "udri");
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
        // TODO
        require(checkUdriOnChain(udri) == true, "udri not exist on chain.");
        bytes32 innerEid = _getInnerEidByUdri(udri);

        string memory bid = _getCommEvidenceIndefiniteString(innerEid, "bid");
        string[] memory dataHash = _getCommEvidenceIndefiniteStringArray(
            innerEid,
            "dataHash"
        );
        isWithdraw =
            keccak256(
                bytes(_getCommEvidenceIndefiniteString(innerEid, "status"))
            ) !=
            keccak256(bytes(EVIDENCE_STATUS_ACTIVE));

        (
            ,
            ,
            ,
            string[] memory _metaData,
            string[] memory _variableData
        ) = _getCommEvidenceById(innerEid);

        dataHashSM = dataHash[0];
        dataHashSHA = dataHash[1];
        dataRight = _getDataRight(innerEid, bid);
        metadata = _metaData;
        variabledata = _variableData;
    }

    /* 5.2.3 查询授权信息 */
    function getUserDataRight(string memory udri, string memory bid)
        public
        view
        returns (string[] memory outRights)
    {
        require(checkUdriOnChain(udri) == true, "udri not exist on chain.");
        bytes32 innerEid = _getInnerEidByUdri(udri);
        string[] memory rights = getDataRightCategory();
        uint32 len = 0;
        for (uint32 i = 0; i < rights.length; i++) {
            if (_userWithDataRight(innerEid, bid, rights[i]) == true) {
                len += 1;
            }
        }
        if (len > 0) {
            string[] memory outRightsTmp = new string[](len);
            uint32 j = 0;
            for (uint32 i = 0; i < rights.length; i++) {
                if (_userWithDataRight(innerEid, bid, rights[i]) == true) {
                    outRights[j] = rights[i];
                    j += 1;
                }
            }
            outRights = outRightsTmp;
        }
        
    }
}
