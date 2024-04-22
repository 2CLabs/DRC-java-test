// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;


import "@openzeppelin/contracts-upgradeable/proxy/utils/Initializable.sol";
import "../utils/DREvidenceMap.sol";
import "../utils/DREvidenceString.sol";
import "./DREvidenceStorageDefine.sol";
import "./DREvidenceStorageConstant.sol";
import "@openzeppelin/contracts/utils/Strings.sol";

library DREvidenceStorageLib {

    using Strings for uint32;
    using DREvidenceString for string;
    using DREvidenceString for bytes32;
    using DREvidenceString for uint256;

    using DREvidenceMap for DREvidenceMap.DRMappingString;
    using DREvidenceMap for DREvidenceMap.DRMappingUint32;
    using DREvidenceMap for DREvidenceMap.DRMappingStringArray;
    using DREvidenceMap for DREvidenceMap.DRMappingBytes32;
    using DREvidenceMap for DREvidenceMap.DRMappingUint256;


    function genEidViaUrdi(string memory udri, string memory method, string memory category) internal pure returns (bytes32 innerEid, string memory outerEidString) {
        innerEid = keccak256(bytes(category.concat(udri)));
        outerEidString = DREvidenceStorageConstant.EVIDENCE_ID_PREFIX_NEW.concat(method).concat(
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
    function _storeCommEvidence(DREvidenceStorageDefine.DREStorage storage sto, bytes32 innerEid, string memory category, string[] memory metaData) internal {
        require(sto._commEvidences[innerEid].timestamp == 0, "EvidenceStorage: udri already exists");
        sto._commEvidences[innerEid].category = category;
        sto._commEvidences[innerEid].metaData = metaData;
        sto._commEvidences[innerEid].timestamp = block.timestamp;
    }

    function _setCommEvidenceIndefiniteStringArray(DREvidenceStorageDefine.DREStorage storage sto, bytes32 innerEid, string memory key, string[] memory value) internal {
        sto._commEvidences[innerEid].indefiniteStringArray.update(keccak256(bytes(key)), value);
    }

    function _setCommEvidenceIndefiniteString(DREvidenceStorageDefine.DREStorage storage sto, bytes32 innerEid, string memory key, string memory value) internal {
        sto._commEvidences[innerEid].indefiniteString.update(keccak256(bytes(key)), value);
    }

    function _setCommEvidencevariableData(DREvidenceStorageDefine.DREStorage storage sto, bytes32 innerEid, string memory key, string memory value) internal {
        sto._commEvidences[innerEid].variableDataMap.update(keccak256(bytes(key)), value);
    }

    function _setCommEvidenceIndefiniteUint32(DREvidenceStorageDefine.DREStorage storage sto, bytes32 innerEid, string memory key, uint32 value) internal {
        sto._commEvidences[innerEid].indefiniteUint32.update(keccak256(bytes(key)), value);
    }

    function _setCommEvidenceIndefiniteBytes32(DREvidenceStorageDefine.DREStorage storage sto, bytes32 innerEid, string memory key, bytes32 value) internal {
        sto._commEvidences[innerEid].indefiniteBytes32.update(keccak256(bytes(key)), value);
    }

    // function _emitNewEvidence(DREvidenceStorageDefine.DREStorage storage sto, string memory outerEid) internal {
    //     emit EvidenceCommStored(msg.sender, outerEid);
    // }

    function _evidenceIsExist(DREvidenceStorageDefine.DREStorage storage sto, bytes32 innerEid) internal view returns (bool) {
        DREvidenceStorageDefine.CommEvidence storage evidence = sto._commEvidences[innerEid];
        return (evidence.timestamp != 0);
    }

    function _getCommEvidenceById(DREvidenceStorageDefine.DREStorage storage sto, bytes32 innerEid) internal view returns (bool, uint256 timestamp, string memory category, string[] memory metaData) {
        DREvidenceStorageDefine.CommEvidence storage evidence = sto._commEvidences[innerEid];
        return (evidence.timestamp != 0, evidence.timestamp, evidence.category, evidence.metaData);
    }

    function _getCommEvidenceStorageById(DREvidenceStorageDefine.DREStorage storage sto, bytes32 innerEid) internal view returns (DREvidenceStorageDefine.CommEvidence storage evidence) {
        return sto._commEvidences[innerEid];
    }

    function _getCommEvidenceIndefiniteStringArray(DREvidenceStorageDefine.DREStorage storage sto, bytes32 innerEid, string memory key) internal view returns(string[] memory value) {
        require(sto._commEvidences[innerEid].timestamp != 0, "EvidenceStorage: Evidence not exists");
        return sto._commEvidences[innerEid].indefiniteStringArray.get(keccak256(bytes(key)));
    }

    function _getCommEvidenceIndefiniteString(DREvidenceStorageDefine.DREStorage storage sto, bytes32 innerEid, string memory key) internal view returns(string memory value) {
        require(sto._commEvidences[innerEid].timestamp != 0, "EvidenceStorage: Evidence not exists");
        return sto._commEvidences[innerEid].indefiniteString.get(keccak256(bytes(key)));
    }

    function _getCommEvidenceIndefiniteUint32(DREvidenceStorageDefine.DREStorage storage sto, bytes32 innerEid, string memory key) internal view returns(uint32 value) {
        require(sto._commEvidences[innerEid].timestamp != 0, "EvidenceStorage: Evidence not exists");
        return sto._commEvidences[innerEid].indefiniteUint32.get(keccak256(bytes(key)));
    }

    function _getCommEvidenceIndefiniteBytes32(DREvidenceStorageDefine.DREStorage storage sto, bytes32 innerEid, string memory key) internal view returns(bytes32 value) {
        require(sto._commEvidences[innerEid].timestamp != 0, "EvidenceStorage: Evidence not exists");
        return sto._commEvidences[innerEid].indefiniteBytes32.get(keccak256(bytes(key)));
    }

    function _getCommEvidencevariableDataMap(DREvidenceStorageDefine.DREStorage storage sto, bytes32 innerEid, string memory key) internal view returns(string memory value) {
        require(sto._commEvidences[innerEid].timestamp != 0, "EvidenceStorage: Evidence not exists");
        return sto._commEvidences[innerEid].variableDataMap.get(keccak256(bytes(key)));
    }

    function _getCommEvidence(DREvidenceStorageDefine.DREStorage storage sto, bytes32 innerEid) internal view returns(DREvidenceStorageDefine.CommEvidence storage value) {
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
    function _storeUser(DREvidenceStorageDefine.DREStorage storage sto, string memory bid, string memory usci, string memory name, address account) internal {
        require(sto._usersV1[bid].timestamp == 0, "UserStorage: bid already exists");
        require(bytes(sto._userAddressToBid[account]).length == 0, "EvidenceStorage: account already exists");
        DREvidenceStorageDefine.UserInfoV1 storage user = sto._usersV1[bid];
        user.bid = bid;
        user.usci = usci;
        user.name = name;
        user.evidenceCount = 0;
        user.account = account;
        user.timestamp = block.timestamp;
        sto._userAddressToBid[account] = bid;
        // emit UserCreated(msg.sender, bid, usci);
    }

    // function _emitNewUser(DREvidenceStorageDefine.DREStorage storage sto, string memory bid, string memory usci) internal {
    //     emit UserCreated(msg.sender, bid, usci);
    // }

    // function _emitUserRoleChanged(DREvidenceStorageDefine.DREStorage storage sto, string memory bid) internal {
    //     emit UserRoleChanged(msg.sender, bid);
    // }

    /* 获取用户信息 */
    function _getUserByBid(DREvidenceStorageDefine.DREStorage storage sto, string memory bid) internal view returns (bool, string memory, string memory, uint256, uint256) {
        DREvidenceStorageDefine.UserInfoV1 storage user = sto._usersV1[bid];
        // require(bytes(usci).length > 0, "EvidenceStorage: Account not exists");
        return (user.timestamp != 0, user.usci, user.name, user.evidenceCount, user.timestamp);
    }

    function _getUseStoragerByBid(DREvidenceStorageDefine.DREStorage storage sto, string memory bid) internal view returns (DREvidenceStorageDefine.UserInfoV1 storage) {
        return sto._usersV1[bid];
    }

    /* 新加用户确权存证数目 */
    function _addEvidenceInUser(DREvidenceStorageDefine.DREStorage storage sto, string memory bid, bytes32 value) internal {
        DREvidenceStorageDefine.UserInfoV1 storage user = sto._usersV1[bid];
        // require(bytes(usci).length > 0, "EvidenceStorage: Account not exists");
        bytes32 key = DREvidenceStorageConstant.EVIDENCE_RIGHT_EID_WITH_INDEX.concat(user.evidenceCount.toHexStringWithoutPrefix()).hash();
        user.indefiniteBytes32.update(key, value);
        user.evidenceCount = user.evidenceCount + 1;
    }

    /* 新加用户审核存证数目 */
    function _addReviewEvidenceInUser(DREvidenceStorageDefine.DREStorage storage sto, string memory bid, bytes32 value) internal {
        DREvidenceStorageDefine.UserInfoV1 storage user = sto._usersV1[bid];
        // 获取当前用户 审核存证数目
        uint256 count = user.indefiniteUint256.get(DREvidenceStorageConstant.USER_REVIEW_COUNT.hash());
        // require(bytes(usci).length > 0, "EvidenceStorage: Account not exists");
        bytes32 key = DREvidenceStorageConstant.EVIDENCE_REVIEW_EID_WITH_INDEX.concat(count.toHexStringWithoutPrefix()).hash();
        user.indefiniteBytes32.update(key, value);
        user.indefiniteUint256.update(DREvidenceStorageConstant.USER_REVIEW_COUNT.hash(), count + 1);
    }

    /* 获取用户某个角色的状态 */
    function _getUserRoleStatus(DREvidenceStorageDefine.DREStorage storage sto, string memory bid, string memory role) internal view returns (uint32) {
        require(sto._usersV1[bid].timestamp != 0, "UserStorage: bid not exists");
        DREvidenceStorageDefine.UserInfoV1 storage user = sto._usersV1[bid];
        return user.role[keccak256(bytes(role))];
    }

    /* 获取用户信息 */
    function _getUserByAccount(DREvidenceStorageDefine.DREStorage storage sto, address account) internal view returns (bool, string memory, string memory, uint256, uint256) {
        string memory bid = sto._userAddressToBid[account];
        // require(bytes(usci).length > 0, "EvidenceStorage: Account not exists");
        return _getUserByBid(sto, bid);
    }

    /* 通过用户account获取用户bid */
    function _getUserBidByAccount(DREvidenceStorageDefine.DREStorage storage sto, address account) internal view returns (bool, string memory) {
        string memory bid = sto._userAddressToBid[account];
        return (bytes(bid).length > 0, bid);
    }

    /* 判断用户存在 */
    function _userExist(DREvidenceStorageDefine.DREStorage storage sto, string memory bid) internal view returns (bool) {
        return sto._usersV1[bid].timestamp != 0;
    }

    //
    function _bindOuterInnerEid(DREvidenceStorageDefine.DREStorage storage sto, bytes32 outer, bytes32 inner) internal {
        require(sto._outerEidToInnerEid[outer] == 0, "EvidenceStorage: bid already exists");
        sto._outerEidToInnerEid[outer] = inner;
    }

    function _GetInnerEidFromOuterEid(DREvidenceStorageDefine.DREStorage storage sto, bytes32 outer) internal view returns (bytes32){
        require(sto._outerEidToInnerEid[outer] != 0, "EvidenceStorage: bid not exists");
        return sto._outerEidToInnerEid[outer];
    }

    /********************************************************************************/
    // 验证bid 是否合法 以及 是否登记
    function verifyBid(DREvidenceStorageDefine.DREStorage storage sto, string memory bid) internal view {
        require(bytes(bid).length > 0, "bid error");
        require(_userExist(sto, bid) == true, "bit not register on this chain");
    }

    
    // 检查某种存证variableData中是否支持某个字段
    function isSupportedInVariableDataFields(DREvidenceStorageDefine.DREStorage storage sto, string memory category, string memory field) internal view returns(bool) {
        string memory vbFieldKeyStr = category.concat(DREvidenceStorageConstant.EVIDENCE_RIGHT_VARIABLE_DATA_MIDDLE).concat(field);
        bytes32 key = keccak256(bytes(vbFieldKeyStr));
        return sto.commData.byte32ToUint32.get(key) == 1;
    }

    // 设置某种存证的 variableData 数据
    function _setVariableData(DREvidenceStorageDefine.DREStorage storage sto, bytes32 innerEid, string memory category, string[] memory variableData) internal{
        require(variableData.length % 2 == 0, "variableData length error");
        DREvidenceStorageDefine.CommEvidence storage evidence = _getCommEvidence(sto, innerEid);

        for (uint32 i = 0; i < variableData.length; i += 2) {
            string memory key = variableData[i];
            string memory value = variableData[i + 1];
            require(isSupportedInVariableDataFields(sto, category, key) == true, "Part of variableData field is not allown");
            evidence.variableDataMap.update(keccak256(bytes(key)), value);
        }
    }

    // 获取存证的 variableData 数据
    function _getVariableData(DREvidenceStorageDefine.DREStorage storage sto, bytes32 innerEid, string memory category) internal view returns(string[] memory variableData) {
        require(variableData.length % 2 == 0, "variableData length error");
        DREvidenceStorageDefine.CommEvidence storage evidence = _getCommEvidence(sto, innerEid);
        string[] memory variableDataFields = getSupportVariableDataFields(sto, category);

        uint32 len = 0;
        for(uint32 i = 0; i < variableDataFields.length; i++) {
            string memory key = variableDataFields[i];
            string memory value = evidence.variableDataMap.get(keccak256(bytes(key)));
            if (bytes(value).length > 0) {
                len++;
            }
        }

        if (len > 0) {
            uint32 j = 0;
            string[] memory variableDataTmp = new string[](len * 2);
            for(uint32 i = 0; i < variableDataFields.length; i++) {
                string memory key = variableDataFields[i];
                string memory value = evidence.variableDataMap.get(keccak256(bytes(key)));
                if (bytes(value).length > 0) {
                    variableDataTmp[j] = key;
                    variableDataTmp[j + 1] = value;
                    len++;
                    j = j + 2;
                }
            }
            variableData = variableDataTmp;
        }
    }

    // 获取当前某种存证支持的 variableData 字段
    function getSupportVariableDataFields(DREvidenceStorageDefine.DREStorage storage sto, string memory category) internal view returns (string[] memory) {
        string memory keyStr = category.concat(":variable_data:supported_fileds=");
        bytes32 key = keccak256(bytes(keyStr));
        return sto.commData.byte32ToStringArrary.get(key);
    }

    // 设置某种存证 variableData 支持的字段，实现逻辑是：先将之前的所有fields都清除，再重新设置，原则上是只能在原有基础上新加字段，减少字段会导致之前保存的字段不能获取到
    function setDataRightSupportVariableDataFields(DREvidenceStorageDefine.DREStorage storage sto, string memory category, string[] memory fields) internal {
        // TODO: 限制合约管理员可调用
        // Clean current
        string memory keyStr = category.concat(":variable_data:supported_fileds=");
        bytes32 vbKey = keccak256(bytes(keyStr));
        string[] memory variableDataFields = sto.commData.byte32ToStringArrary.get(vbKey);
        for (uint32 i = 0; i < variableDataFields.length; i++) {
            string memory vbFieldKeyStr = category.concat(DREvidenceStorageConstant.EVIDENCE_RIGHT_VARIABLE_DATA_MIDDLE);
            bytes32 key = keccak256(bytes(vbFieldKeyStr.concat(variableDataFields[i])));
            sto.commData.byte32ToUint32.update(key, 0);
        }
        // Set
        for (uint32 i = 0; i < fields.length; i++) {
            string memory vbFieldKeyStr = category.concat(DREvidenceStorageConstant.EVIDENCE_RIGHT_VARIABLE_DATA_MIDDLE);
            bytes32 key = keccak256(bytes(vbFieldKeyStr.concat(fields[i])));
            sto.commData.byte32ToUint32.update(key, 1);
        }
        sto.commData.byte32ToStringArrary.update(vbKey, fields);
    }

    // 数据权限管理--- 设置分类 TODO: 限制合约管理员可调用
    // function setDataRightCategory(DREvidenceStorageDefine.DREStorage storage sto, string[] memory fields) internal {
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
    function getDataRightCategory(DREvidenceStorageDefine.DREStorage storage sto) internal view returns (string[] memory) {
        bytes32 vbKey = keccak256(bytes("data_right_category:"));
        return sto.commData.byte32ToStringArrary.get(vbKey);
    }

    // 检查数据Hash是否已经存证
    function checkUdriOnChain(DREvidenceStorageDefine.DREStorage storage sto, string memory udri) internal view returns (bool) {
        bytes32 innerEid = sto.commData.byte32ToBytes32.get(keccak256(bytes(DREvidenceStorageConstant.EVIDENCE_UDRI.concat(udri))));
        return  innerEid != bytes32(0);
    }
    // 根据udri获取 innerEid
    function _getInnerEidByUdri(DREvidenceStorageDefine.DREStorage storage sto, string memory udri) internal view returns (bytes32) {
        return sto.commData.byte32ToBytes32.get(keccak256(bytes(DREvidenceStorageConstant.EVIDENCE_UDRI.concat(udri))));
    }

    // 关联数据Hash 以及 innerEid
    function _setUdriOnChain(DREvidenceStorageDefine.DREStorage storage sto, string memory udri, bytes32 innerEid) internal {
        sto.commData.byte32ToBytes32.update(keccak256(bytes(DREvidenceStorageConstant.EVIDENCE_UDRI.concat(udri))), innerEid);
    }
    
    // 检查数据Hash是否已经存证
    function checkDataHashOnChain(DREvidenceStorageDefine.DREStorage storage sto, string memory dataHash) internal view returns (bool) {
        bytes32 innerEid = sto.commData.byte32ToBytes32.get(keccak256(bytes(dataHash)));
        return  innerEid != bytes32(0);
    }

    function _getInnerEidByDataHash(DREvidenceStorageDefine.DREStorage storage sto, string memory dataHash) internal view returns (bytes32) {
        return sto.commData.byte32ToBytes32.get(keccak256(bytes(dataHash)));
    }

    // 检查数据Hash是否已经存证, 任意一个dataHash存在都算存在
    function checkDataHashOnChain(DREvidenceStorageDefine.DREStorage storage sto, string[] memory dataHash) internal view returns (bool) {
        for (uint32 i = 0; i < dataHash.length; i++) {
            bytes32 innerEid = sto.commData.byte32ToBytes32.get(keccak256(bytes(dataHash[i])));
            if  (innerEid != bytes32(0)) return true;
        }
        return false;
    }

    // 关联数据Hash 以及 innerEid
    function _setDataHashOnChain(DREvidenceStorageDefine.DREStorage storage sto, string[] memory dataHash, bytes32 innerEid) internal {
        for (uint32 i = 0; i < dataHash.length; i++) {
            sto.commData.byte32ToBytes32.update(keccak256(bytes(dataHash[i])), innerEid);
        }
    }

    function _withAnyRightInDataRightEvidence(DREvidenceStorageDefine.DREStorage storage sto, bytes32 innerEid, string memory bid) internal view returns(bool) {
        string[] memory rights = getDataRightCategory(sto);
        uint32 len = 0;
        for (uint32 i = 0; i < rights.length; i++) {
            if (_userWithDataRight(sto, innerEid, bid, rights[i]) == true) {
                return true;
            }
        }
        return false;
    }

    function _withRightInDataRightEvidence(DREvidenceStorageDefine.DREStorage storage sto, bytes32 innerEid, string memory bid, string memory right) internal view returns(bool) {
        string[] memory rights = getDataRightCategory(sto);
        uint32 len = 0;
        for (uint32 i = 0; i < rights.length; i++) {
            if (_userWithDataRight(sto, innerEid, bid, rights[i]) == true) {
                return true;
            }
        }
        return false;
    }

    // 检查sender是 存证 owner
    // function _requireDataRightEvidenceOwner(DREvidenceStorageDefine.DREStorage storage sto, bytes32 innerEid internal view{
    //     (bool isExist, string memory bid) = _getUserBidByAccount(sto, msg.sender);
    //     require(isExist == true, "Sender is not registered.");

    //     DREvidenceStorageDefine.CommEvidence storage evidence = _getCommEvidence(sto, innerEid);
    //     require(evidence.timestamp != 0, "evidence is not exist.");

    //     string memory bidInEvidence = _getCommEvidenceIndefiniteString(sto, innerEid, "bid");

    //     require(bid.equal(bidInEvidence) == true, "Sender is not evidence owner.");
    // }

    // 检查sender是否是某个角色
    // function _checkUserRole(DREvidenceStorageDefine.DREStorage storage sto, string memory role) internal view{
    //     (bool isExist, string memory bid) = _getUserBidByAccount(sto, msg.sender);
    //     require(isExist == true, "Sender is not registered.");
    //     DREvidenceStorageDefine.UserInfoV1 storage user = _getUseStoragerByBid(sto, bid);
    //     require((user.indefiniteString.get(DREvidenceStorageConstant.USER_ROLE_PREFIX.concat(role).hash()).equal("exist")) == true, "User without corresponding role permissions.");
    // }

    function getAccessControl(DREvidenceStorageDefine.DREStorage storage sto) internal view returns(uint32 status) {
        string memory keyStr = DREvidenceStorageConstant.ADMIN_PARAM_CONTRACT_ACCESS;
        return sto.commData.byte32ToUint32.get(keyStr.hash());
    }

    /******************************************** 用户 **************************************************/
    function queryUserRole() internal pure returns (string[] memory) {
        // return ["dataRightOwner", "reviewer", "registry", "platform"];
        string[] memory roles = new string[](4);
        roles[0] = DREvidenceStorageConstant.USER_ROLE_DATA_HOLDER;
        roles[1] = DREvidenceStorageConstant.USER_ROLE_REVIEWER;
        roles[2] = DREvidenceStorageConstant.USER_ROLE_REGISTRY;
        roles[3] = DREvidenceStorageConstant.USER_ROLE_ELSE;
        return roles;
    }
    /*
    3.2 用户角色管理
    3.2.1 添加用户
    由用户管理员在链上添加用户信息，同时可以绑定用户角色。
    */
    function addUser(DREvidenceStorageDefine.DREStorage storage sto, string memory bid, string memory usci, string memory name,address account, string[] memory roles) internal{
        require(_userExist(sto, bid) == false, "User already exist.");
        // string memory bid, string memory usci, string memory name, address account
        _storeUser(sto, bid, usci, name, account);
        DREvidenceStorageDefine.UserInfoV1 storage user = _getUseStoragerByBid(sto, bid);
        for(uint32 i = 0; i < roles.length; i++) {
            user.indefiniteString.update(DREvidenceStorageConstant.USER_ROLE_PREFIX.concat(roles[i]).hash(), "exist");
        }
    }

    /*
    3.2.2  查询用户
    根据用户bid查询当前用户信息及其绑定的角色。
    */
    function getUserRoles(DREvidenceStorageDefine.DREStorage storage sto, string memory bid) internal view returns(string memory usci, string memory name, string[] memory roles){
        require(_userExist(sto, bid) == true, "User not exist.");
        DREvidenceStorageDefine.UserInfoV1 storage user = _getUseStoragerByBid(sto, bid);
        usci = user.usci;
        name = user.name;
        string[] memory supporedRoles = queryUserRole();
        uint32 len = 0;
        for(uint32 i = 0; i < supporedRoles.length; i++) {
            if (user.indefiniteString.get(DREvidenceStorageConstant.USER_ROLE_PREFIX.concat(supporedRoles[i]).hash()).equal("exist")) {
                len = len + 1;
            }
        }
        if (len > 0) {
            uint32 j = 0;
            string[] memory rolesTmp = new string[](len);
            for(uint32 i = 0; i < supporedRoles.length; i++) {
                if (user.indefiniteString.get(DREvidenceStorageConstant.USER_ROLE_PREFIX.concat(supporedRoles[i]).hash()).equal("exist")) {
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
    function grantUserRoles(DREvidenceStorageDefine.DREStorage storage sto, string memory bid, string[] memory roles) internal {
        require(_userExist(sto, bid) == true, "User not exist.");
        DREvidenceStorageDefine.UserInfoV1 storage user = _getUseStoragerByBid(sto, bid);
        for(uint32 i = 0; i < roles.length; i++) {
            user.indefiniteString.update(DREvidenceStorageConstant.USER_ROLE_PREFIX.concat(roles[i]).hash(), "exist");
        }
    }

    /*
    3.2.4 回收用户角色
    由用户管理员回收用户的一或多个角色。
    */
    function revokeUserRoles(DREvidenceStorageDefine.DREStorage storage sto, string memory bid, string[] memory roles) internal {
        require(_userExist(sto, bid) == true, "User not exist.");
        DREvidenceStorageDefine.UserInfoV1 storage user = _getUseStoragerByBid(sto, bid);
        for(uint32 i = 0; i < roles.length; i++) {
            user.indefiniteString.update(DREvidenceStorageConstant.USER_ROLE_PREFIX.concat(roles[i]).hash(), "");
        }
    }

    /* 4.3 登记存证（二阶段）*/

    /* 5. 查询相关接口 */
    /* 5.1  查询用户数据信息 */
    /* 5.1.1 查询用户数据数量 */
    function getDataCount(DREvidenceStorageDefine.DREStorage storage sto, string calldata bid) internal view returns (uint256 dataCount) {
        require(_userExist(sto, bid) == true, "User already exist.");
        DREvidenceStorageDefine.UserInfoV1 storage user = _getUseStoragerByBid(sto, bid);
        dataCount = user.evidenceCount;
    }

    /* 5.1.2  查询用户数据列表 */
    function getDataList(DREvidenceStorageDefine.DREStorage storage sto, string memory bid, uint256 start, uint256 count) internal view returns (string[] memory udriArray) {
        require(_userExist(sto, bid) == true, "User already exist.");
        require(count > 0, "count is invalid.");
        DREvidenceStorageDefine.UserInfoV1 storage user = _getUseStoragerByBid(sto, bid);
        uint256 j = 0;
        string[] memory udriArrayTmp = new string[](count);
        for(uint256 i = start; i < start + count; i++) {
            bytes32 key = DREvidenceStorageConstant.EVIDENCE_RIGHT_EID_WITH_INDEX.concat(i.toHexStringWithoutPrefix()).hash();
            bytes32 innerEid = user.indefiniteBytes32.get(key);
            udriArrayTmp[j] = _getCommEvidenceIndefiniteString(sto, innerEid, "udri");
            j++;
        }
        udriArray = udriArrayTmp;
    }

    /* 查询用户审核存证数量 */
    function getUserReviewCount(DREvidenceStorageDefine.DREStorage storage sto, string calldata bid) internal view returns (uint256 dataCount) {
        require(_userExist(sto, bid) == true, "User already exist.");
        DREvidenceStorageDefine.UserInfoV1 storage user = _getUseStoragerByBid(sto, bid);
        dataCount = user.indefiniteUint256.get(DREvidenceStorageConstant.USER_REVIEW_COUNT.hash());
    }

    /* 查询用户审核存证列表 */
    function getUserReviewList(DREvidenceStorageDefine.DREStorage storage sto, string memory bid, uint256 start, uint256 count) internal view returns (string[] memory hashArray) {
        require(_userExist(sto, bid) == true, "User already exist.");
        require(count > 0, "count is invalid.");
        DREvidenceStorageDefine.UserInfoV1 storage user = _getUseStoragerByBid(sto, bid);
        uint256 j = 0;
        string[] memory hashArrayTmp = new string[](count);
        for(uint256 i = start; i < start + count; i++) {
            bytes32 key = DREvidenceStorageConstant.EVIDENCE_REVIEW_EID_WITH_INDEX.concat(i.toHexStringWithoutPrefix()).hash();
            bytes32 innerEid = user.indefiniteBytes32.get(key);
            string [] memory reviewDataHash = _getCommEvidenceIndefiniteStringArray(sto, innerEid, "reviewDataHash");
            hashArrayTmp[j] = reviewDataHash[0];
            j++;
        }
        hashArray = hashArrayTmp;
    }

    // 不安全的接口，确保调用前已经做了 udri 检查
    function getEvidenceReviewCount(DREvidenceStorageDefine.DREStorage storage sto, string memory udri)internal view returns (uint32 count) {
        bytes32 rightInnerEid = keccak256(bytes(DREvidenceStorageConstant.EVIDENCE_CATEGORY_RIGHT.concat(udri))); //确权存证 内部eid
        count = _getCommEvidenceIndefiniteUint32(sto, rightInnerEid, DREvidenceStorageConstant.EVIDENCE_DATA_REVIEW_COUNT);
    }

    /******************************************** 审查存证 **************************************************/
    /* 4.2 审查存证 */
    /* 4.2.1 新增审查存证 */
    function addReviewEvidence(DREvidenceStorageDefine.DREStorage storage sto, string memory udri,string memory reviewerBid, string[] memory reviewDataHash, string[] memory metaData, string[] memory variableData) internal {
        // TODO: 检查该用户是否有审核角色
        // _checkUserRole(DREvidenceStorageConstant.USER_ROLE_REVIEWER);
        // TODO： 确保reviewDataHash没有被添加过
        verifyBid(sto, reviewerBid);

        // 确保udri对应的数据存证已经存在
        require(checkUdriOnChain(sto, udri) == true, "udri not on chain.");

        bytes32 rightInnerEid = keccak256(bytes(DREvidenceStorageConstant.EVIDENCE_CATEGORY_RIGHT.concat(udri))); //确权存证 内部eid

        string memory str = DREvidenceStorageConstant.EVIDENCE_CATEGORY_REVIEW.concat(udri);
        for (uint32 i = 0; i < reviewDataHash.length; i++) {
            str = str.concat(reviewDataHash[i]);
        }
        bytes32 innerEid = keccak256(bytes(str)); //审核存证 内部eid

        _storeCommEvidence(sto, innerEid, DREvidenceStorageConstant.EVIDENCE_CATEGORY_REVIEW, metaData);
        _setCommEvidenceIndefiniteString(sto, innerEid, "version", "DREvidenceStorageConstant.EVIDENCE_CONTRACT_VERSION_V1");
        _setCommEvidenceIndefiniteString(sto, innerEid, "udri", udri);
        _setCommEvidenceIndefiniteString(sto, innerEid, "reviewerBid", reviewerBid);
        _setCommEvidenceIndefiniteStringArray(sto, innerEid, "reviewDataHash", reviewDataHash);

        // 审核数目信息需要 放到 确权存证 相关的数据中
        string memory key = reviewerBid.concat(DREvidenceStorageConstant.EVIDENCE_DATA_USER_REVIEW_COUNT); // 某个审查机构review数目
        uint32 current_number = _getCommEvidenceIndefiniteUint32(sto, rightInnerEid, key);
        _setCommEvidenceIndefiniteUint32(sto, rightInnerEid, key, current_number + 1);

        _setCommEvidenceIndefiniteBytes32(sto, rightInnerEid, reviewerBid.concat(DREvidenceStorageConstant.EVIDENCE_DATA_USER_REVIEW_INDEX).concat(current_number.toString()), innerEid);

        // 该 udri 数据，所有审查机构的review的数目
        current_number = _getCommEvidenceIndefiniteUint32(sto, rightInnerEid, DREvidenceStorageConstant.EVIDENCE_DATA_REVIEW_COUNT);
        _setCommEvidenceIndefiniteUint32(sto, rightInnerEid, DREvidenceStorageConstant.EVIDENCE_DATA_REVIEW_COUNT, current_number + 1);
        _setCommEvidenceIndefiniteBytes32(sto, rightInnerEid, DREvidenceStorageConstant.EVIDENCE_DATA_REVIEW_INDEX.concat(current_number.toString()), innerEid);

        // 添加关联到user，这样可以遍历用户所有审核存证
        _addReviewEvidenceInUser(sto, reviewerBid, innerEid);

        _setVariableData(sto, innerEid, DREvidenceStorageConstant.EVIDENCE_CATEGORY_REVIEW, variableData);

        // Generate outerEid
        string memory outerEid = DREvidenceStorageConstant.EVIDENCE_ID_PREFIX_REVIEW.concat(innerEid.toHexStringWithoutPrefix());
        _bindOuterInnerEid(sto, keccak256(bytes(outerEid)), innerEid);
        // _emitNewEvidence(outerEid);
    }


    /* 4.2.2 撤回审查存证 */
    function withdrawReviewEvidence(DREvidenceStorageDefine.DREStorage storage sto, string memory udri, string memory reviewerBid) internal {
        require(checkUdriOnChain(sto, udri) == true, "udri not on chain.");
        // _checkUserRole(DREvidenceStorageConstant.USER_ROLE_REVIEWER);

        // TODO: 确认是否有必要传入reviewerBid，应为这样会出现reviewer 传入其他人的 bid，去撤销他人的bid，
        bytes32 rightInnerEid = keccak256(bytes(DREvidenceStorageConstant.EVIDENCE_CATEGORY_RIGHT.concat(udri))); //确权存证 内部eid

        string memory reviewCountKey = reviewerBid.concat(DREvidenceStorageConstant.EVIDENCE_DATA_USER_REVIEW_COUNT); // 某个审查机构review数目
        uint32 current_number = _getCommEvidenceIndefiniteUint32(sto, rightInnerEid, reviewCountKey);
        for (uint32 i = 0; i < current_number; i++) {
            string memory key = reviewerBid.concat(DREvidenceStorageConstant.EVIDENCE_DATA_USER_REVIEW_INDEX).concat(i.toString());
            bytes32 innerEid = _getCommEvidenceIndefiniteBytes32(sto, rightInnerEid, key);
            _setCommEvidenceIndefiniteString(sto, innerEid, "status", DREvidenceStorageConstant.EVIDENCE_STATUS_DISABLED);
        }
    }

    /* 5.3  查询审查存证信息 */
    /* 5.3.1 查询审查存证数量 */
    function getReviewCount(DREvidenceStorageDefine.DREStorage storage sto, string calldata udri) internal view returns (uint256) {
        // TODO: 修改文档那边，方法定义不一致
        require(checkUdriOnChain(sto, udri) == true, "udri not on chain.");

        bytes32 rightInnerEid = keccak256(bytes(DREvidenceStorageConstant.EVIDENCE_CATEGORY_RIGHT.concat(udri))); //确权存证 内部eid
        return _getCommEvidenceIndefiniteUint32(sto, rightInnerEid, DREvidenceStorageConstant.EVIDENCE_DATA_REVIEW_COUNT);
    }

    /* 5.3.2 查询审查存证信息 */
    function getVerifyEvidence(DREvidenceStorageDefine.DREStorage storage sto, string calldata udri, uint32 index) internal view returns (bool isWithdraw, string memory reviewerBid, string[] memory metaData, string[] memory variableData)  {
        // TODO
        require(checkUdriOnChain(sto, udri) == true, "udri not on chain.");

        bytes32 rightInnerEid = keccak256(bytes(DREvidenceStorageConstant.EVIDENCE_CATEGORY_RIGHT.concat(udri))); //确权存证 内部eid
        uint32 count = _getCommEvidenceIndefiniteUint32(sto, rightInnerEid, DREvidenceStorageConstant.EVIDENCE_DATA_REVIEW_COUNT);
        require(index < count, "iinvalid index");
        bytes32 innerEid = _getCommEvidenceIndefiniteBytes32(sto, rightInnerEid, DREvidenceStorageConstant.EVIDENCE_DATA_REVIEW_INDEX.concat(index.toString()));
        reviewerBid = _getCommEvidenceIndefiniteString(sto, innerEid, "reviewerBid");
        (
            ,
            ,
            string memory _category,
            string[] memory _metaData
        ) = _getCommEvidenceById(sto, innerEid);
        string memory status = _getCommEvidenceIndefiniteString(sto, innerEid, "status");
        isWithdraw = status.equal(DREvidenceStorageConstant.EVIDENCE_STATUS_DISABLED);
        metaData = _metaData;
        variableData = _getVariableData(sto, innerEid, _category);
    }

    // 获取某个审核机构对某个数据存证的 审核次数
    function getReviewCountOfReviewer(DREvidenceStorageDefine.DREStorage storage sto, string calldata udri, string calldata reviewerBid) internal view returns (uint256) {
        // TODO: 修改文档那边，方法定义不一致
        require(checkUdriOnChain(sto, udri) == true, "udri not on chain.");
 
        bytes32 rightInnerEid = keccak256(bytes(DREvidenceStorageConstant.EVIDENCE_CATEGORY_RIGHT.concat(udri))); //确权存证 内部eid
        string memory key = reviewerBid.concat(":review_count="); // 某个审查机构review数目
        return _getCommEvidenceIndefiniteUint32(sto, rightInnerEid, key);
    }

    /* 查询某个审核机构某次审查存证信息 */
    function getVerifyEvidenceOfReviewer(DREvidenceStorageDefine.DREStorage storage sto, string calldata udri, string calldata reviewerBid, uint32 index) internal view returns (bool isWithdraw, string[] memory metaData, string[] memory variableData)  {
        require(checkUdriOnChain(sto, udri) == true, "udri not on chain.");

        bytes32 rightInnerEid = keccak256(bytes(DREvidenceStorageConstant.EVIDENCE_CATEGORY_RIGHT.concat(udri))); //确权存证 内部eid
        uint32 count = _getCommEvidenceIndefiniteUint32(sto, rightInnerEid, reviewerBid.concat(DREvidenceStorageConstant.EVIDENCE_DATA_USER_REVIEW_COUNT));// 审查机构review数目
        require(index < count, "invalid index");
        bytes32 innerEid = _getCommEvidenceIndefiniteBytes32(sto, rightInnerEid, reviewerBid.concat(DREvidenceStorageConstant.EVIDENCE_DATA_USER_REVIEW_INDEX).concat(index.toString()));
        (
            ,
            ,
            string memory _category,
            string[] memory _metaData
        ) = _getCommEvidenceById(sto, innerEid);
        string memory status = _getCommEvidenceIndefiniteString(sto, innerEid, "status");
        isWithdraw = status.equal(DREvidenceStorageConstant.EVIDENCE_STATUS_DISABLED);
        metaData = _metaData;
        variableData = _getVariableData(sto, innerEid, _category);
    }

    /******************************************** 确权存证**************************************************/
    
    function _addDataRight(
        DREvidenceStorageDefine.DREStorage storage sto,
        bytes32 innerEid,
        string memory bid,
        string[] memory dataRight
    ) internal{
        string memory prefix = "dataRight:";
        for (uint32 i = 0; i < dataRight.length; i++) {
            string memory key = prefix.concat(bid).concat(dataRight[i]);
            _setCommEvidenceIndefiniteString(sto, innerEid, key, "exist");
        }

        // 不使用StringArray类型存放 dataRight. (2024-03-25)
        // _setCommEvidenceIndefiniteStringArray(
        //     sto,
        //     innerEid,
        //     prefix.concat(bid),
        //     dataRight
        // );
    }

    // 不使用StringArray类型存放 dataRight. (2024-03-25)
    function _getDataRight(DREvidenceStorageDefine.DREStorage storage sto, bytes32 innerEid, string memory bid)
        internal
        view
        returns (string[] memory)
    {
        string memory prefix = "dataRight:";
        return
            _getCommEvidenceIndefiniteStringArray(sto, innerEid, prefix.concat(bid));
    }

    function _removeDataRight(
        DREvidenceStorageDefine.DREStorage storage sto,
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
        DREvidenceStorageDefine.DREStorage storage sto,
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
        DREvidenceStorageDefine.DREStorage storage sto,
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
        // _checkUserRole(DREvidenceStorageConstant.USER_ROLE_DATA_HOLDER);
        // 确保udri没有被添加过
        require(checkUdriOnChain(sto, udri) == false, "udri already on chain.");
        // TODO: 是否应该使用 msg.sender 对应的bid 呢？是否msg.sender 和传入的bid必需对应一致呢？
        require(_userExist(sto, bid) == true, "User not exist.");
        DREvidenceStorageDefine.UserInfoV1 storage user = _getUseStoragerByBid(sto, bid);
        verifyBid(sto, bid);

        bytes32 innerEid = keccak256(
            bytes(DREvidenceStorageConstant.EVIDENCE_CATEGORY_RIGHT.concat(udri))
        );
 
        _storeCommEvidence(
            sto,
            innerEid,
            DREvidenceStorageConstant.EVIDENCE_CATEGORY_RIGHT,
            metaData
        );
        _setCommEvidenceIndefiniteString(
            sto,
            innerEid,
            "version",
            "DREvidenceStorageConstant.EVIDENCE_CONTRACT_VERSION_V1"
        );
        _setCommEvidenceIndefiniteString(sto, innerEid, "udri", udri);
        _setCommEvidenceIndefiniteString(sto, innerEid, "bid", bid);
        _setCommEvidenceIndefiniteString(
            sto,
            innerEid,
            "status",
            DREvidenceStorageConstant.EVIDENCE_STATUS_ACTIVE
        );
        _setCommEvidenceIndefiniteStringArray(sto, innerEid, "dataHash", dataHash);

        _setDataHashOnChain(sto, dataHash, innerEid);

        // _setCommEvidenceIndefiniteStringArray(sto, innerEid, "dataRight", dataRight);
        _addDataRight(sto, innerEid, bid, dataRight);
        require(_userWithDataRight(sto, innerEid, bid, "hold") == true, "The data right [hold] is required.");

        _setUdriOnChain(sto, udri, innerEid);
        // 添加关联到user，这样可以遍历用户所有确权存证
        _addEvidenceInUser(sto, bid, innerEid);

        // save variableData
        _setVariableData(sto, innerEid, DREvidenceStorageConstant.EVIDENCE_CATEGORY_RIGHT, variableData);

        // Generate outerEid
        string memory outerEid = DREvidenceStorageConstant.EVIDENCE_ID_PREFIX_NEW.concat(
            innerEid.toHexStringWithoutPrefix()
        );
        _bindOuterInnerEid(sto, keccak256(bytes(outerEid)), innerEid);
        // _emitNewEvidence(outerEid); // Do it out of this function
    }

    /* 4.1.2  追加可变数据存证 */
    function appendVariableData(
        DREvidenceStorageDefine.DREStorage storage sto,
        string memory udri,
        string[] memory variableData
    ) internal {
        // _checkUserRole(DREvidenceStorageConstant.USER_ROLE_DATA_HOLDER);
        require(variableData.length > 0, "variableData is empty");
        require(variableData.length % 2 == 0, "variableData length error");
        bytes32 innerEid = keccak256(
            bytes(DREvidenceStorageConstant.EVIDENCE_CATEGORY_RIGHT.concat(udri))
        );
        // _requireDataRightEvidenceOwner(innerEid);
        _setVariableData(sto, innerEid, DREvidenceStorageConstant.EVIDENCE_CATEGORY_RIGHT, variableData);
    }

    /* 4.1.3 撤回确权存证 */
    function withdrawDataRightRegister(
        DREvidenceStorageDefine.DREStorage storage sto,
        string memory udri,
        /*string memory dataRightOwnerBid, */
        string[] memory dataRight
    ) internal {
        // _checkUserRole(DREvidenceStorageConstant.USER_ROLE_DATA_HOLDER);
        bytes32 innerEid = keccak256(
            bytes(DREvidenceStorageConstant.EVIDENCE_CATEGORY_RIGHT.concat(udri))
        );
        // _requireDataRightEvidenceOwner(innerEid);
        // TODO: dataRightOwnerBid 似乎可以不需要填入
        string memory bid = _getCommEvidenceIndefiniteString(sto, innerEid, "bid");
        _removeDataRight(sto, innerEid, bid, dataRight);
    }

    /* 4.1.4 新增授权存证 */
    function grantUserDataRight(
        DREvidenceStorageDefine.DREStorage storage sto,
        string memory udri,
        string memory bid,
        string[] memory dataRight
    ) internal {
        // _checkUserRole(DREvidenceStorageConstant.USER_ROLE_DATA_HOLDER);
        verifyBid(sto, bid);
        bytes32 innerEid = keccak256(
            bytes(DREvidenceStorageConstant.EVIDENCE_CATEGORY_RIGHT.concat(udri))
        );
        // _requireDataRightEvidenceOwner(innerEid);
        _addDataRight(sto, innerEid, bid, dataRight);
    }

    /* 4.1.5 撤回授权存证 */
    function withdrawUserDataRight(
        DREvidenceStorageDefine.DREStorage storage sto,
        string memory udri,
        string memory bid,
        string[] memory dataRight
    ) internal {
        // _checkUserRole(DREvidenceStorageConstant.USER_ROLE_DATA_HOLDER);
        verifyBid(sto, bid);
        bytes32 innerEid = keccak256(
            bytes(DREvidenceStorageConstant.EVIDENCE_CATEGORY_RIGHT.concat(udri))
        );
        // _requireDataRightEvidenceOwner(innerEid);
        _removeDataRight(sto, innerEid, bid, dataRight);
    }

    /* 5.2 查询确权存证信息 */
    /* 5.2.1 查询存证数据标识 */
    function getUdriByDatahash(DREvidenceStorageDefine.DREStorage storage sto, string calldata dataHash)
        internal
        view
        returns (string memory)
    {
        bytes32 innerEid = _getInnerEidByDataHash(sto, dataHash);
        return _getCommEvidenceIndefiniteString(sto, innerEid, "udri");
    }

    /* 5.2.2 查询确权存证信息 */
    function getRegisteredData(DREvidenceStorageDefine.DREStorage storage sto, string memory udri)
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
        // isWithdraw =
        //     keccak256(
        //         bytes(_getCommEvidenceIndefiniteString(sto, innerEid, "status"))
        //     ) !=
        //     keccak256(bytes(DREvidenceStorageConstant.EVIDENCE_STATUS_ACTIVE));
        isWithdraw = _withAnyRightInDataRightEvidence(sto, innerEid, bid) == false;

        (
            ,
            ,
            string memory _category,
            string[] memory _metaData
        ) = _getCommEvidenceById(sto, innerEid);

        dataHashSM = dataHash[0];
        dataHashSHA = dataHash[1];
        dataRight = getUserDataRight(sto, udri, bid);
        metadata = _metaData;
        variabledata = _getVariableData(sto, innerEid, _category);
    }

    /* 5.2.3 查询授权信息 */
    function getUserDataRight(DREvidenceStorageDefine.DREStorage storage sto, string memory udri, string memory bid)
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
                    outRightsTmp[j] = rights[i];
                    j += 1;
                }
            }
            outRights = outRightsTmp;
        }
    }
}