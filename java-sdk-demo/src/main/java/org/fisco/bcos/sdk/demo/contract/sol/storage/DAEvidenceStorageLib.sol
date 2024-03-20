// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;


import "@openzeppelin/contracts-upgradeable/proxy/utils/Initializable.sol";
import "../utils/DAEvidenceMap.sol";
import "../utils/DAEvidenceString.sol";
import "@openzeppelin/contracts/utils/Strings.sol";

library DAEvidenceStorageLib {

    using Strings for uint32;
    using DAEvidenceString for string;
    using DAEvidenceString for bytes32;
    using DAEvidenceString for uint256;

    uint32 public constant USER_STATUS_ACTIVE = 100;
    uint32 public constant USER_STATUS_DISABLED = 500;
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

    using DAEvidenceMap for DAEvidenceMap.DAMappingString;
    using DAEvidenceMap for DAEvidenceMap.DAMappingUint32;
    using DAEvidenceMap for DAEvidenceMap.DAMappingStringArray;
    using DAEvidenceMap for DAEvidenceMap.DAMappingBytes32;

    using DAEvidenceString for string;
    using DAEvidenceString for bytes32;
    using DAEvidenceString for uint256;

    struct UserInfoV1 {
        string bid; // 链上身份标识
        string usci;
        string name;
        uint256 evidenceCount;
        address account;
        DAEvidenceMap.DAMappingString indefiniteString;
        DAEvidenceMap.DAMappingBytes32 indefiniteBytes32;
        mapping(bytes32 => uint32) role; // 0: 无； USER_STATUS_ACTIVE：正常； USER_STATUS_DISABLED：注销

        uint256 timestamp;
    }

    struct CommEvidence {
        string category;
        DAEvidenceMap.DAMappingStringArray indefiniteStringArray;
        DAEvidenceMap.DAMappingString indefiniteString;
        DAEvidenceMap.DAMappingUint32 indefiniteUint32;
        DAEvidenceMap.DAMappingBytes32 indefiniteBytes32;

        string[] metaData;
        string[] variableData;
        DAEvidenceMap.DAMappingString variableDataMap;

        uint256 timestamp;
    }

    struct CommData {
        DAEvidenceMap.DAMappingStringArray byte32ToStringArrary;
        DAEvidenceMap.DAMappingString byte32ToString;
        DAEvidenceMap.DAMappingUint32 byte32ToUint32;
        DAEvidenceMap.DAMappingBytes32 byte32ToBytes32;
    }

    struct DAEStorage {
        mapping(address => string) _userAddressToBid;

        mapping(string => UserInfoV1) _usersV1; // usci => UserInfoV1
        mapping(bytes32 => CommEvidence) _commEvidences; // innerEid = CommEvidence

        mapping(bytes32 => bytes32) _outerEidToInnerEid; // Hash(outerEid) = innerEid

        CommData commData; // 存放合约管理相关的数据信息
    }

    function genEidViaUrdi(string memory udri, string memory category) internal pure returns (bytes32 innerEid, string memory outerEidString) {
        innerEid = keccak256(bytes(category.concat(udri)));
        outerEidString = DAEvidenceStorageLib.EVIDENCE_ID_PREFIX_NEW.concat(
            innerEid.toHexStringWithoutPrefix()
        );
    }

    /*
     存证设置获取
     调用顺序：  _storeCommEvidence 
                _setCommEvidenceIndefiniteStringArray
                _setCommEvidenceIndefiniteString
                _bindOuterInnerEid
                _emitEvidence
    */
    function _storeCommEvidence(DAEStorage storage sto, bytes32 innerEid, string memory category, string[] memory metaData, string[] memory variableData) internal {
        require(sto._commEvidences[innerEid].timestamp == 0, "EvidenceStorage: udri already exists");
        sto._commEvidences[innerEid].category = category;
        sto._commEvidences[innerEid].metaData = metaData;
        sto._commEvidences[innerEid].variableData = variableData;
        sto._commEvidences[innerEid].timestamp = block.timestamp;
    }

    function _setCommEvidenceIndefiniteStringArray(DAEStorage storage sto, bytes32 innerEid, string memory key, string[] memory value) internal {
        sto._commEvidences[innerEid].indefiniteStringArray.add(keccak256(bytes(key)), value);
    }

    function _setCommEvidenceIndefiniteString(DAEStorage storage sto, bytes32 innerEid, string memory key, string memory value) internal {
        sto._commEvidences[innerEid].indefiniteString.add(keccak256(bytes(key)), value);
    }

    function _setCommEvidencevariableData(DAEStorage storage sto, bytes32 innerEid, string memory key, string memory value) internal {
        sto._commEvidences[innerEid].variableDataMap.add(keccak256(bytes(key)), value);
    }

    function _setCommEvidenceIndefiniteUint32(DAEStorage storage sto, bytes32 innerEid, string memory key, uint32 value) internal {
        sto._commEvidences[innerEid].indefiniteUint32.add(keccak256(bytes(key)), value);
    }

    function _setCommEvidenceIndefiniteBytes32(DAEStorage storage sto, bytes32 innerEid, string memory key, bytes32 value) internal {
        sto._commEvidences[innerEid].indefiniteBytes32.add(keccak256(bytes(key)), value);
    }

    // function _emitNewEvidence(DAEStorage storage sto, string memory outerEid) internal {
    //     emit EvidenceCommStored(msg.sender, outerEid);
    // }

    function _evidenceIsExist(DAEStorage storage sto, bytes32 innerEid) internal view returns (bool) {
        CommEvidence storage evidence = sto._commEvidences[innerEid];
        return (evidence.timestamp != 0);
    }

    function _getCommEvidenceById(DAEStorage storage sto, bytes32 innerEid) internal view returns (bool, uint256 timestamp, string memory category, string[] memory metaData, string[] memory variableData ) {
        CommEvidence storage evidence = sto._commEvidences[innerEid];
        return (evidence.timestamp != 0, evidence.timestamp, evidence.category, evidence.metaData, evidence.variableData);
    }

    function _getCommEvidenceStorageById(DAEStorage storage sto, bytes32 innerEid) internal view returns (CommEvidence storage evidence) {
        return sto._commEvidences[innerEid];
    }

    function _getCommEvidenceIndefiniteStringArray(DAEStorage storage sto, bytes32 innerEid, string memory key) internal view returns(string[] memory value) {
        require(sto._commEvidences[innerEid].timestamp != 0, "EvidenceStorage: Evidence not exists");
        return sto._commEvidences[innerEid].indefiniteStringArray.get(keccak256(bytes(key)));
    }

    function _getCommEvidenceIndefiniteString(DAEStorage storage sto, bytes32 innerEid, string memory key) internal view returns(string memory value) {
        require(sto._commEvidences[innerEid].timestamp != 0, "EvidenceStorage: Evidence not exists");
        return sto._commEvidences[innerEid].indefiniteString.get(keccak256(bytes(key)));
    }

    function _getCommEvidenceIndefiniteUint32(DAEStorage storage sto, bytes32 innerEid, string memory key) internal view returns(uint32 value) {
        require(sto._commEvidences[innerEid].timestamp != 0, "EvidenceStorage: Evidence not exists");
        return sto._commEvidences[innerEid].indefiniteUint32.get(keccak256(bytes(key)));
    }

    function _getCommEvidenceIndefiniteBytes32(DAEStorage storage sto, bytes32 innerEid, string memory key) internal view returns(bytes32 value) {
        require(sto._commEvidences[innerEid].timestamp != 0, "EvidenceStorage: Evidence not exists");
        return sto._commEvidences[innerEid].indefiniteBytes32.get(keccak256(bytes(key)));
    }

    function _getCommEvidencevariableDataMap(DAEStorage storage sto, bytes32 innerEid, string memory key) internal view returns(string memory value) {
        require(sto._commEvidences[innerEid].timestamp != 0, "EvidenceStorage: Evidence not exists");
        return sto._commEvidences[innerEid].variableDataMap.get(keccak256(bytes(key)));
    }

    function _getCommEvidence(DAEStorage storage sto, bytes32 innerEid) internal view returns(CommEvidence storage value) {
        require(sto._commEvidences[innerEid].timestamp != 0, "EvidenceStorage: Evidence not exists");
        return sto._commEvidences[innerEid];
    }


    // 用户 & 用户权限 设置获取

    /*
    添加用户
        _storeUser
        ... 通用数据设置
        _emitNewUser
    */
    function _storeUser(DAEStorage storage sto, string memory bid, string memory usci, string memory name, address account) internal {
        require(sto._usersV1[bid].timestamp == 0, "UserStorage: bid already exists");
        require(bytes(sto._userAddressToBid[account]).length == 0, "EvidenceStorage: account already exists");
        UserInfoV1 storage user = sto._usersV1[bid];
        user.bid = bid;
        user.usci = usci;
        user.name = name;
        user.evidenceCount = 0;
        user.account = account;
        user.timestamp = block.timestamp;
        sto._userAddressToBid[account] = bid;
        // emit UserCreated(msg.sender, bid, usci);
    }

    // function _emitNewUser(DAEStorage storage sto, string memory bid, string memory usci) internal {
    //     emit UserCreated(msg.sender, bid, usci);
    // }

    // function _emitUserRoleChanged(DAEStorage storage sto, string memory bid) internal {
    //     emit UserRoleChanged(msg.sender, bid);
    // }

    /* 获取用户信息 */
    function _getUserByBid(DAEStorage storage sto, string memory bid) internal view returns (bool, string memory, string memory, uint256, uint256) {
        UserInfoV1 storage user = sto._usersV1[bid];
        // require(bytes(usci).length > 0, "EvidenceStorage: Account not exists");
        return (user.timestamp != 0, user.usci, user.name, user.evidenceCount, user.timestamp);
    }

    function _getUseStoragerByBid(DAEStorage storage sto, string memory bid) internal view returns (UserInfoV1 storage) {
        return sto._usersV1[bid];
    }

    /* 新加用户确权存证数目 */
    function _addEvidenceInUser(DAEStorage storage sto, string memory bid, bytes32 key, bytes32 value) internal {
        UserInfoV1 storage user = sto._usersV1[bid];
        // require(bytes(usci).length > 0, "EvidenceStorage: Account not exists");
        user.indefiniteBytes32.update(key, value);
        user.evidenceCount = user.evidenceCount + 1;
    }

    /* 获取用户某个角色的状态 */
    function _getUserRoleStatus(DAEStorage storage sto, string memory bid, string memory role) internal view returns (uint32) {
        require(sto._usersV1[bid].timestamp != 0, "UserStorage: bid not exists");
        UserInfoV1 storage user = sto._usersV1[bid];
        return user.role[keccak256(bytes(role))];
    }

    /* 获取用户信息 */
    function _getUserByAccount(DAEStorage storage sto, address account) internal view returns (bool, string memory, string memory, uint256, uint256) {
        string memory bid = sto._userAddressToBid[account];
        // require(bytes(usci).length > 0, "EvidenceStorage: Account not exists");
        return _getUserByBid(sto, bid);
    }

    /* 通过用户account获取用户bid */
    function _getUserBidByAccount(DAEStorage storage sto, address account) internal view returns (bool, string memory) {
        string memory bid = sto._userAddressToBid[account];
        return (bytes(bid).length > 0, bid);
    }

    /* 判断用户存在 */
    function _userExist(DAEStorage storage sto, string memory bid) internal view returns (bool) {
        return sto._usersV1[bid].timestamp != 0;
    }

    //
    function _bindOuterInnerEid(DAEStorage storage sto, bytes32 outer, bytes32 inner) internal {
        require(sto._outerEidToInnerEid[outer] == 0, "EvidenceStorage: bid already exists");
        sto._outerEidToInnerEid[outer] = inner;
    }

    function _GetInnerEidFromOuterEid(DAEStorage storage sto, bytes32 outer) internal view returns (bytes32){
        require(sto._outerEidToInnerEid[outer] != 0, "EvidenceStorage: bid not exists");
        return sto._outerEidToInnerEid[outer];
    }

    /********************************************************************************/
    // 验证bid 是否合法 以及 是否登记
    function verifyBid(DAEStorage storage sto, string memory bid) internal view {
        require(bytes(bid).length > 0, "bid error");
        require(_userExist(sto, bid) == true, "bit not register on this chain");
    }

    
    // 检查某种存证variableData中是否支持某个字段
    function isSupportedInVariableDataFields(DAEStorage storage sto, string memory category, string memory field) internal view returns(bool) {
        string memory vbFieldKeyStr = category.concat(EVIDENCE_RIGHT_VARIABLE_DATA_MIDDLE).concat(field);
        bytes32 key = keccak256(bytes(vbFieldKeyStr));
        return sto.commData.byte32ToUint32.get(key) == 1;
    }

    // 设置某种存证的 variableData 数据
    function _setVariableData(DAEStorage storage sto, bytes32 innerEid, string memory category, string[] memory variableData) internal{
        require(variableData.length % 2 == 0, "variableData length error");
        DAEvidenceStorageLib.CommEvidence storage evidence = _getCommEvidence(sto, innerEid);

        for (uint32 i = 0; i < variableData.length; i += 2) {
            string memory key = variableData[i];
            string memory value = variableData[i + 1];
            require(isSupportedInVariableDataFields(sto, category, key) == true, "part of variableData field is not allown");
            evidence.variableDataMap.update(keccak256(bytes(key)), value);
        }
    }

    // 获取当前某种存证支持的 variableData 字段
    // function getSupportVariableDataFields(DAEStorage storage sto, string memory category) internal view returns (string[] memory) {
    //     string memory keyStr = category.concat(":variable_data:supported_fileds=");
    //     bytes32 key = keccak256(bytes(keyStr));
    //     return sto.commData.byte32ToStringArrary.get(key);
    // }

    // 设置某种存证 variableData 支持的字段，实现逻辑是：先将之前的所有fields都清除，再重新设置，原则上是只能在原有基础上新加字段，减少字段会导致之前保存的字段不能获取到
    // function setDataRightSupportVariableDataFields(DAEStorage storage sto, string memory category, string[] memory fields) internal {
    //     // TODO: 限制合约管理员可调用
    //     // Clean current
    //     string memory keyStr = category.concat(":variable_data:supported_fileds=");
    //     bytes32 vbKey = keccak256(bytes(keyStr));
    //     string[] memory variableDataFields = sto.commData.byte32ToStringArrary.get(vbKey);
    //     for (uint32 i = 0; i < variableDataFields.length; i++) {
    //         string memory vbFieldKeyStr = category.concat("variable_data:key=");
    //         bytes32 key = keccak256(bytes(vbFieldKeyStr.concat(fields[i])));
    //         sto.commData.byte32ToUint32.update(key, 0);
    //     }
    //     // Set
    //     for (uint32 i = 0; i < fields.length; i++) {
    //         string memory vbFieldKeyStr = category.concat("variable_data:key=");
    //         bytes32 key = keccak256(bytes(vbFieldKeyStr.concat(fields[i])));
    //         sto.commData.byte32ToUint32.update(key, 1);
    //     }
    //     sto.commData.byte32ToStringArrary.update(vbKey, variableDataFields);
    // }

    // 数据权限管理--- 设置分类 TODO: 限制合约管理员可调用
    // function setDataRightCategory(DAEStorage storage sto, string[] memory fields) internal {
    //     // Clean current
    //     bytes32 vbKey = keccak256(bytes("data_right_category:"));
    //     string[] memory variableDataFields = sto.commData.byte32ToStringArrary.get(vbKey);
    //     for (uint32 i = 0; i < variableDataFields.length; i++) {
    //         string memory vbFieldKeyStr = "data_right_category:key=";
    //         bytes32 key = keccak256(bytes(vbFieldKeyStr.concat(fields[i])));
    //         sto.commData.byte32ToUint32.update(key, 0);
    //     }
    //     // Set
    //     for (uint32 i = 0; i < fields.length; i++) {
    //         string memory vbFieldKeyStr = "data_right_category:key=";
    //         bytes32 key = keccak256(bytes(vbFieldKeyStr.concat(fields[i])));
    //         sto.commData.byte32ToUint32.update(key, 1);
    //     }
    //     sto.commData.byte32ToStringArrary.update(vbKey, fields);
    // }

    // 数据权限管理--- 获取数据权限当前分类
    function getDataRightCategory(DAEStorage storage sto) internal view returns (string[] memory) {
        bytes32 vbKey = keccak256(bytes("data_right_category:"));
        return sto.commData.byte32ToStringArrary.get(vbKey);
    }

    // 检查数据Hash是否已经存证
    function checkUdriOnChain(DAEStorage storage sto, string memory udri) internal view returns (bool) {
        bytes32 innerEid = sto.commData.byte32ToBytes32.get(keccak256(bytes(EVIDENCE_UDRI.concat(udri))));
        return  innerEid != bytes32(0);
    }
    // 根据udri获取 innerEid
    function _getInnerEidByUdri(DAEStorage storage sto, string memory udri) internal view returns (bytes32) {
        return sto.commData.byte32ToBytes32.get(keccak256(bytes(EVIDENCE_UDRI.concat(udri))));
    }

    // 关联数据Hash 以及 innerEid
    function _setUdriOnChain(DAEStorage storage sto, string memory udri, bytes32 innerEid) internal {
        sto.commData.byte32ToBytes32.update(keccak256(bytes(EVIDENCE_UDRI.concat(udri))), innerEid);
    }
    
    // 检查数据Hash是否已经存证
    function checkDataHashOnChain(DAEStorage storage sto, string memory dataHash) internal view returns (bool) {
        bytes32 innerEid = sto.commData.byte32ToBytes32.get(keccak256(bytes(dataHash)));
        return  innerEid != bytes32(0);
    }

    function _getInnerEidByDataHash(DAEStorage storage sto, string memory dataHash) internal view returns (bytes32) {
        return sto.commData.byte32ToBytes32.get(keccak256(bytes(dataHash)));
    }

    // 检查数据Hash是否已经存证, 任意一个dataHash存在都算存在
    function checkDataHashOnChain(DAEStorage storage sto, string[] memory dataHash) internal view returns (bool) {
        for (uint32 i = 0; i < dataHash.length; i++) {
            bytes32 innerEid = sto.commData.byte32ToBytes32.get(keccak256(bytes(dataHash[i])));
            if  (innerEid != bytes32(0)) return true;
        }
        return false;
    }

    // 关联数据Hash 以及 innerEid
    function _setDataHashOnChain(DAEStorage storage sto, string[] memory dataHash, bytes32 innerEid) internal {
        for (uint32 i = 0; i < dataHash.length; i++) {
            sto.commData.byte32ToBytes32.update(keccak256(bytes(dataHash[i])), innerEid);
        }
    }

    // 检查sender是 存证 owner
    // function _requireDataRightEvidenceOwner(DAEStorage storage sto, bytes32 innerEid internal view{
    //     (bool isExist, string memory bid) = _getUserBidByAccount(sto, msg.sender);
    //     require(isExist == true, "Sender is not registered.");

    //     CommEvidence storage evidence = _getCommEvidence(sto, innerEid);
    //     require(evidence.timestamp != 0, "evidence is not exist.");

    //     string memory bidInEvidence = _getCommEvidenceIndefiniteString(sto, innerEid, "bid");

    //     require(bid.equal(bidInEvidence) == true, "Sender is not evidence owner.");
    // }

    // 检查sender是否是某个角色
    // function _checkUserRole(DAEStorage storage sto, string memory role) internal view{
    //     (bool isExist, string memory bid) = _getUserBidByAccount(sto, msg.sender);
    //     require(isExist == true, "Sender is not registered.");
    //     UserInfoV1 storage user = _getUseStoragerByBid(sto, bid);
    //     require((user.indefiniteString.get(USER_ROLE_PREFIX.concat(role).hash()).equal("exist")) == true, "User without corresponding role permissions.");
    // }

    /******************************************** 用户 **************************************************/
    function queryUserRole() internal pure returns (string[] memory) {
        // return ["dataRightOwner", "reviewer", "registry", "platform"];
        string[] memory roles = new string[](4);
        roles[0] = DAEvidenceStorageLib.USER_ROLE_DATA_HOLDER;
        roles[1] = DAEvidenceStorageLib.USER_ROLE_REVIEWER;
        roles[2] = DAEvidenceStorageLib.USER_ROLE_REGISTRY;
        roles[3] = DAEvidenceStorageLib.USER_ROLE_ELSE;
        return roles;
    }
    /*
    3.2 用户角色管理
    3.2.1 添加用户
    由用户管理员在链上添加用户信息，同时可以绑定用户角色。
    */
    function addUser(DAEStorage storage sto, string memory bid, string memory usci, string memory name,address account, string[] memory roles) internal{
        require(_userExist(sto, bid) == false, "User already exist.");
        // string memory bid, string memory usci, string memory name, address account
        _storeUser(sto, bid, usci, name, account);
        DAEvidenceStorageLib.UserInfoV1 storage user = _getUseStoragerByBid(sto, bid);
        for(uint32 i = 0; i < roles.length; i++) {
            user.indefiniteString.update(DAEvidenceStorageLib.USER_ROLE_PREFIX.concat(roles[i]).hash(), "exist");
        }
    }

    /*
    3.2.2  查询用户
    根据用户bid查询当前用户信息及其绑定的角色。
    */
    function getUserRoles(DAEStorage storage sto, string memory bid) internal view returns(string memory usci, string memory name, string[] memory roles){
        require(_userExist(sto, bid) == true, "User not exist.");
        DAEvidenceStorageLib.UserInfoV1 storage user = _getUseStoragerByBid(sto, bid);
        usci = user.usci;
        name = user.name;
        string[] memory supporedRoles = queryUserRole();
        uint32 len = 0;
        for(uint32 i = 0; i < supporedRoles.length; i++) {
            if (user.indefiniteString.get(DAEvidenceStorageLib.USER_ROLE_PREFIX.concat(supporedRoles[i]).hash()).equal("exist")) {
                len = len + 1;
            }
        }
        if (len > 0) {
            uint32 j = 0;
            string[] memory rolesTmp = new string[](len);
            for(uint32 i = 0; i < supporedRoles.length; i++) {
                if (user.indefiniteString.get(DAEvidenceStorageLib.USER_ROLE_PREFIX.concat(supporedRoles[i]).hash()).equal("exist")) {
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
    function grantUserRoles(DAEStorage storage sto, string memory bid, string[] memory roles) internal {
        require(_userExist(sto, bid) == true, "User not exist.");
        DAEvidenceStorageLib.UserInfoV1 storage user = _getUseStoragerByBid(sto, bid);
        for(uint32 i = 0; i < roles.length; i++) {
            user.indefiniteString.update(DAEvidenceStorageLib.USER_ROLE_PREFIX.concat(roles[i]).hash(), "exist");
        }
    }

    /*
    3.2.4 回收用户角色
    由用户管理员回收用户的一或多个角色。
    */
    function revokeUserRoles(DAEStorage storage sto, string memory bid, string[] memory roles) internal {
        require(_userExist(sto, bid) == true, "User not exist.");
        DAEvidenceStorageLib.UserInfoV1 storage user = _getUseStoragerByBid(sto, bid);
        for(uint32 i = 0; i < roles.length; i++) {
            user.indefiniteString.update(DAEvidenceStorageLib.USER_ROLE_PREFIX.concat(roles[i]).hash(), "");
        }
    }

    /* 4.3 登记存证（二阶段）*/

    /* 5. 查询相关接口 */
    /* 5.1  查询用户数据信息 */
    /* 5.1.1 查询用户数据数量 */
    function getDataCount(DAEStorage storage sto, string calldata bid) internal view returns (uint256 dataCount) {
        require(_userExist(sto, bid) == true, "User already exist.");
        DAEvidenceStorageLib.UserInfoV1 storage user = _getUseStoragerByBid(sto, bid);
        dataCount = user.evidenceCount;
    }

    /* 5.1.2  查询用户数据列表 */
    function getDataList(DAEStorage storage sto, string memory bid, uint256 start, uint256 count) internal view returns (string[] memory udriArray) {
        require(_userExist(sto, bid) == true, "User already exist.");
        require(count > 0, "count is invalid.");
        DAEvidenceStorageLib.UserInfoV1 storage user = _getUseStoragerByBid(sto, bid);
        uint256 j = 0;
        string[] memory udriArrayTmp = new string[](count);
        for(uint256 i = start; i < start + count; i++) {
            bytes32 key = DAEvidenceStorageLib.EVIDENCE_RIGHT_EID_WITH_INDEX.concat(i.toHexStringWithoutPrefix()).hash();
            bytes32 innerEid = user.indefiniteBytes32.get(key);
            udriArrayTmp[j] = _getCommEvidenceIndefiniteString(sto, innerEid, "udri");
            j++;
        }
        udriArray = udriArrayTmp;
    }


    /******************************************** 审查存证 **************************************************/
    /* 4.2 审查存证 */
    /* 4.2.1 新增审查存证 */
    function addReviewEvidence(DAEStorage storage sto, string memory udri,string memory reviewerBid, string[] memory reviewDataHash, string[] memory metaData, string[] memory variableData) internal {
        // TODO: 检查该用户是否有审核角色
        // _checkUserRole(DAEvidenceStorageLib.USER_ROLE_REVIEWER);
        // TODO： 确保reviewDataHash没有被添加过
        verifyBid(sto, reviewerBid);

        // 确保udri对应的数据存证已经存在
        require(checkUdriOnChain(sto, udri) == true, "udri not on chain.");

        bytes32 rightInnerEid = keccak256(bytes(DAEvidenceStorageLib.EVIDENCE_CATEGORY_RIGHT.concat(udri))); //确权存证 内部eid

        string memory str = DAEvidenceStorageLib.EVIDENCE_CATEGORY_REVIEW.concat(udri);
        for (uint32 i = 0; i < reviewDataHash.length; i++) {
            str = str.concat(reviewDataHash[i]);
        }
        bytes32 innerEid = keccak256(bytes(str)); //审核存证 内部eid

        _storeCommEvidence(sto, innerEid, DAEvidenceStorageLib.EVIDENCE_CATEGORY_REVIEW, metaData, variableData);
        _setCommEvidenceIndefiniteString(sto, innerEid, "version", "EVIDENCE_CONTRACT_VERSION_V1");
        _setCommEvidenceIndefiniteString(sto, innerEid, "udri", udri);
        _setCommEvidenceIndefiniteString(sto, innerEid, "reviewerBid", reviewerBid);
        _setCommEvidenceIndefiniteStringArray(sto, innerEid, "reviewDataHash", reviewDataHash);

        // 审核数目信息需要 放到 确权存证 相关的数据中
        string memory key = reviewerBid.concat(DAEvidenceStorageLib.EVIDENCE_DATA_USER_REVIEW_COUNT); // 某个审查机构review数目
        uint32 current_number = _getCommEvidenceIndefiniteUint32(sto, rightInnerEid, key);
        _setCommEvidenceIndefiniteUint32(sto, rightInnerEid, key, current_number + 1);

        _setCommEvidenceIndefiniteBytes32(sto, rightInnerEid, reviewerBid.concat(DAEvidenceStorageLib.EVIDENCE_DATA_USER_REVIEW_INDEX).concat(current_number.toString()), innerEid);

        // 该 udri 数据，所有审查机构的review的数目
        current_number = _getCommEvidenceIndefiniteUint32(sto, rightInnerEid, DAEvidenceStorageLib.EVIDENCE_DATA_REVIEW_COUNT);
        _setCommEvidenceIndefiniteUint32(sto, rightInnerEid, DAEvidenceStorageLib.EVIDENCE_DATA_REVIEW_COUNT, current_number + 1);
        _setCommEvidenceIndefiniteBytes32(sto, rightInnerEid, DAEvidenceStorageLib.EVIDENCE_DATA_REVIEW_INDEX.concat(current_number.toString()), innerEid);

        _setVariableData(sto, innerEid, DAEvidenceStorageLib.EVIDENCE_CATEGORY_REVIEW, variableData);

        // Generate outerEid
        string memory outerEid = DAEvidenceStorageLib.EVIDENCE_ID_PREFIX_REVIEW.concat(innerEid.toHexStringWithoutPrefix());
        _bindOuterInnerEid(sto, keccak256(bytes(outerEid)), innerEid);
        // _emitNewEvidence(outerEid);
    }


    /* 4.2.2 撤回审查存证 */
    function withdrawReviewEvidence(DAEStorage storage sto, string memory udri, string memory reviewerBid) internal {
        require(checkUdriOnChain(sto, udri) == true, "udri not on chain.");
        // _checkUserRole(DAEvidenceStorageLib.USER_ROLE_REVIEWER);

        bytes32 rightInnerEid = keccak256(bytes(DAEvidenceStorageLib.EVIDENCE_CATEGORY_RIGHT.concat(udri))); //确权存证 内部eid

        string memory reviewCountKey = reviewerBid.concat(DAEvidenceStorageLib.EVIDENCE_DATA_USER_REVIEW_COUNT); // 某个审查机构review数目
        uint32 current_number = _getCommEvidenceIndefiniteUint32(sto, rightInnerEid, reviewCountKey);
        for (uint32 i = 0; i < current_number; i++) {
            string memory key = reviewerBid.concat(DAEvidenceStorageLib.EVIDENCE_DATA_USER_REVIEW_INDEX).concat(i.toString());
            bytes32 innerEid = _getCommEvidenceIndefiniteBytes32(sto, rightInnerEid, key);
            _setCommEvidenceIndefiniteString(sto, innerEid, "status", DAEvidenceStorageLib.EVIDENCE_STATUS_DISABLED);
        }
    }

    /* 5.3  查询审查存证信息 */
    /* 5.3.1 查询审查存证数量 */
    function getReviewCount(DAEStorage storage sto, string calldata udri) internal view returns (uint256) {
        // TODO: 修改文档那边，方法定义不一致
        require(checkUdriOnChain(sto, udri) == true, "udri not on chain.");

        bytes32 rightInnerEid = keccak256(bytes(DAEvidenceStorageLib.EVIDENCE_CATEGORY_RIGHT.concat(udri))); //确权存证 内部eid
        return _getCommEvidenceIndefiniteUint32(sto, rightInnerEid, DAEvidenceStorageLib.EVIDENCE_DATA_REVIEW_COUNT);
    }

    /* 5.3.2 查询审查存证信息 */
    function getVerifyDAEvidence(DAEStorage storage sto, string calldata udri, uint32 index) internal view returns (string memory reviewerBid, string[] memory metaData, string[] memory variableData)  {
        // TODO
        require(checkUdriOnChain(sto, udri) == true, "udri not on chain.");

        bytes32 rightInnerEid = keccak256(bytes(DAEvidenceStorageLib.EVIDENCE_CATEGORY_RIGHT.concat(udri))); //确权存证 内部eid
        bytes32 innerEid = _getCommEvidenceIndefiniteBytes32(sto, rightInnerEid, DAEvidenceStorageLib.EVIDENCE_DATA_REVIEW_INDEX.concat(index.toString()));
        reviewerBid = _getCommEvidenceIndefiniteString(sto, innerEid, "reviewerBid");
        (
            ,
            ,
            ,
            string[] memory _metaData,
            string[] memory _variableData
        ) = _getCommEvidenceById(sto, innerEid);
        metaData = _metaData;
        variableData = _variableData;
    }

    // 获取某个审核机构对某个数据存证的 审核次数
    function getReviewCountOfUser(DAEStorage storage sto, string calldata udri, string calldata reviewerBid) internal view returns (uint256) {
        // TODO: 修改文档那边，方法定义不一致
        require(checkUdriOnChain(sto, udri) == true, "udri not on chain.");
 
        bytes32 rightInnerEid = keccak256(bytes(DAEvidenceStorageLib.EVIDENCE_CATEGORY_RIGHT.concat(udri))); //确权存证 内部eid
        string memory key = reviewerBid.concat(":review_count="); // 某个审查机构review数目
        return _getCommEvidenceIndefiniteUint32(sto, rightInnerEid, key);
    }

    /******************************************** 确权存证**************************************************/
    
    function _addDataRight(
        DAEStorage storage sto,
        bytes32 innerEid,
        string memory bid,
        string[] memory dataRight
    ) internal{
        string memory prefix = "dataRight:";
        for (uint32 i = 0; i < dataRight.length; i++) {
            string memory key = prefix.concat(bid).concat(dataRight[i]);
            _setCommEvidenceIndefiniteString(sto, innerEid, key, "exist");
        }
        _setCommEvidenceIndefiniteStringArray(
            sto,
            innerEid,
            prefix.concat(bid),
            dataRight
        );
    }

    function _getDataRight(DAEStorage storage sto, bytes32 innerEid, string memory bid)
        internal
        view
        returns (string[] memory)
    {
        string memory prefix = "dataRight:";
        return
            _getCommEvidenceIndefiniteStringArray(sto, innerEid, prefix.concat(bid));
    }

    function _removeDataRight(
        DAEStorage storage sto,
        bytes32 innerEid,
        string memory bid,
        string[] memory dataRight
    ) internal{
        string memory prefix = "dataRight:";
        for (uint32 i = 0; i < dataRight.length; i++) {
            string memory key = prefix.concat(bid).concat(dataRight[i]);
            _setCommEvidenceIndefiniteString(sto, innerEid, key, "");
        }
    }

    function _userWithDataRight(
        DAEStorage storage sto,
        bytes32 innerEid,
        string memory bid,
        string memory dataRight
    ) internal view returns (bool) {
        string memory prefix = "dataRight:";
        string memory key = prefix.concat(bid).concat(dataRight);
        string memory value = _getCommEvidenceIndefiniteString(sto, innerEid, key);
        return keccak256(bytes(value)) == keccak256(bytes("exist"));
    }

    /*4. 存证 */
    /* 4.1.1 新增确权存证 */
    function addDataRightEvidence(
        DAEStorage storage sto,
        string memory udri,
        string memory bid,
        string[] memory dataHash,
        string[] memory dataRight,
        string[] memory metaData,
        string[] memory variableData
    ) internal {
        require(dataHash.length == 2, "There should be 2 Data Hash.");
        // 确保dataHash没有被添加过
        require(
            checkDataHashOnChain(sto, dataHash) == false,
            "Data Hash already on chain."
        );
        // _checkUserRole(DAEvidenceStorageLib.USER_ROLE_DATA_HOLDER);
        // 确保udri没有被添加过
        require(checkUdriOnChain(sto, udri) == false, "udri already on chain.");
        // TODO: 是否应该使用 msg.sender 对应的bid 呢？是否msg.sender 和传入的bid必需对应一致呢？
        require(_userExist(sto, bid) == true, "User not exist.");
        DAEvidenceStorageLib.UserInfoV1 storage user = _getUseStoragerByBid(sto, bid);
        verifyBid(sto, bid);

        bytes32 innerEid = keccak256(
            bytes(DAEvidenceStorageLib.EVIDENCE_CATEGORY_RIGHT.concat(udri))
        );
 
        _storeCommEvidence(
            sto,
            innerEid,
            DAEvidenceStorageLib.EVIDENCE_CATEGORY_RIGHT,
            metaData,
            variableData
        );
        _setCommEvidenceIndefiniteString(
            sto,
            innerEid,
            "version",
            "EVIDENCE_CONTRACT_VERSION_V1"
        );
        _setCommEvidenceIndefiniteString(sto, innerEid, "udri", udri);
        _setCommEvidenceIndefiniteString(sto, innerEid, "bid", bid);
        _setCommEvidenceIndefiniteString(
            sto,
            innerEid,
            "status",
            DAEvidenceStorageLib.EVIDENCE_STATUS_ACTIVE
        );
        _setCommEvidenceIndefiniteStringArray(sto, innerEid, "dataHash", dataHash);

        _setDataHashOnChain(sto, dataHash, innerEid);

        // _setCommEvidenceIndefiniteStringArray(sto, innerEid, "dataRight", dataRight);
        _addDataRight(sto, innerEid, bid, dataRight);

        // 添加关联到user，这样可以遍历用户所有确权存证
        _addEvidenceInUser(sto, bid, DAEvidenceStorageLib.EVIDENCE_RIGHT_EID_WITH_INDEX.concat(user.evidenceCount.toHexStringWithoutPrefix()).hash(), innerEid);

        // save variableData
        _setVariableData(sto, innerEid, DAEvidenceStorageLib.EVIDENCE_CATEGORY_RIGHT, variableData);

        // Generate outerEid
        string memory outerEid = DAEvidenceStorageLib.EVIDENCE_ID_PREFIX_NEW.concat(
            innerEid.toHexStringWithoutPrefix()
        );
        _bindOuterInnerEid(sto, keccak256(bytes(outerEid)), innerEid);
        // _emitNewEvidence(outerEid); // Do it out of this function
    }

    /* 4.1.2  追加可变数据存证 */
    function appendVariableData(
        DAEStorage storage sto,
        string memory udri,
        string[] memory variableData
    ) internal {
        // _checkUserRole(DAEvidenceStorageLib.USER_ROLE_DATA_HOLDER);
        require(variableData.length > 0, "variableData is empty");
        require(variableData.length % 2 == 0, "variableData length error");
        bytes32 innerEid = keccak256(
            bytes(DAEvidenceStorageLib.EVIDENCE_CATEGORY_RIGHT.concat(udri))
        );
        // _requireDataRightEvidenceOwner(innerEid);
        _setVariableData(sto, innerEid, DAEvidenceStorageLib.EVIDENCE_CATEGORY_RIGHT, variableData);
    }

    /* 4.1.3 撤回确权存证 */
    function withdrawDataRightRegister(
        DAEStorage storage sto,
        string memory udri,
        /*string memory dataRightOwnerBid, */
        string[] memory dataRight
    ) internal {
        // _checkUserRole(DAEvidenceStorageLib.USER_ROLE_DATA_HOLDER);
        bytes32 innerEid = keccak256(
            bytes(DAEvidenceStorageLib.EVIDENCE_CATEGORY_RIGHT.concat(udri))
        );
        // _requireDataRightEvidenceOwner(innerEid);
        // TODO: dataRightOwnerBid 似乎可以不需要填入
        string memory bid = _getCommEvidenceIndefiniteString(sto, innerEid, "bid");
        _removeDataRight(sto, innerEid, bid, dataRight);
    }

    /* 4.1.4 新增授权存证 */
    function grantUserDataRight(
        DAEStorage storage sto,
        string memory udri,
        string memory bid,
        string[] memory dataRight
    ) internal {
        // _checkUserRole(DAEvidenceStorageLib.USER_ROLE_DATA_HOLDER);
        verifyBid(sto, bid);
        bytes32 innerEid = keccak256(
            bytes(DAEvidenceStorageLib.EVIDENCE_CATEGORY_RIGHT.concat(udri))
        );
        // _requireDataRightEvidenceOwner(innerEid);
        _addDataRight(sto, innerEid, bid, dataRight);
    }

    /* 4.1.5 撤回授权存证 */
    function withdrawUserDataRight(
        DAEStorage storage sto,
        string memory udri,
        string memory bid,
        string[] memory dataRight
    ) internal {
        // _checkUserRole(DAEvidenceStorageLib.USER_ROLE_DATA_HOLDER);
        verifyBid(sto, bid);
        bytes32 innerEid = keccak256(
            bytes(DAEvidenceStorageLib.EVIDENCE_CATEGORY_RIGHT.concat(udri))
        );
        // _requireDataRightEvidenceOwner(innerEid);
        _removeDataRight(sto, innerEid, bid, dataRight);
    }

    /* 5.2 查询确权存证信息 */
    /* 5.2.1 查询存证数据标识 */
    function getUdriByDatahash(DAEStorage storage sto, string calldata dataHash)
        internal
        view
        returns (string memory)
    {
        bytes32 innerEid = _getInnerEidByDataHash(sto, dataHash);
        return _getCommEvidenceIndefiniteString(sto, innerEid, "udri");
    }

    /* 5.2.2 查询确权存证信息 */
    function getRegisteredData(DAEStorage storage sto, string memory udri)
        internal
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
        require(checkUdriOnChain(sto, udri) == true, "udri not exist on chain.");
        bytes32 innerEid = _getInnerEidByUdri(sto, udri);

        string memory bid = _getCommEvidenceIndefiniteString(sto, innerEid, "bid");
        string[] memory dataHash = _getCommEvidenceIndefiniteStringArray(
            sto,
            innerEid,
            "dataHash"
        );
        isWithdraw =
            keccak256(
                bytes(_getCommEvidenceIndefiniteString(sto, innerEid, "status"))
            ) !=
            keccak256(bytes(DAEvidenceStorageLib.EVIDENCE_STATUS_ACTIVE));

        (
            ,
            ,
            ,
            string[] memory _metaData,
            string[] memory _variableData
        ) = _getCommEvidenceById(sto, innerEid);

        dataHashSM = dataHash[0];
        dataHashSHA = dataHash[1];
        dataRight = _getDataRight(sto, innerEid, bid);
        metadata = _metaData;
        variabledata = _variableData;
    }

    /* 5.2.3 查询授权信息 */
    function getUserDataRight(DAEStorage storage sto, string memory udri, string memory bid)
        internal
        view
        returns (string[] memory outRights)
    {
        require(checkUdriOnChain(sto, udri) == true, "udri not exist on chain.");
        bytes32 innerEid = _getInnerEidByUdri(sto, udri);
        string[] memory rights = getDataRightCategory(sto);
        uint32 len = 0;
        for (uint32 i = 0; i < rights.length; i++) {
            if (_userWithDataRight(sto, innerEid, bid, rights[i]) == true) {
                len += 1;
            }
        }
        if (len > 0) {
            string[] memory outRightsTmp = new string[](len);
            uint32 j = 0;
            for (uint32 i = 0; i < rights.length; i++) {
                if (_userWithDataRight(sto, innerEid, bid, rights[i]) == true) {
                    outRights[j] = rights[i];
                    j += 1;
                }
            }
            outRights = outRightsTmp;
        }
    }
}