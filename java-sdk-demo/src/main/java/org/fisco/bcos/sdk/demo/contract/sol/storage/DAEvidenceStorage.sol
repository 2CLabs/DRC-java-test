// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "./DAEvidenceStorageLib.sol";
import "../utils/DAEvidenceMap.sol";
import "@openzeppelin/contracts-upgradeable/proxy/utils/Initializable.sol";

contract DAEvidenceStorage is Initializable, DAEvidenceStorageLib {
    uint32 public immutable USER_STATUS_ACTIVE = 100;
    uint32 public immutable USER_STATUS_DISABLED = 500;

    using DAEvidenceMap for DAEvidenceMap.DAMappingString;
    using DAEvidenceMap for DAEvidenceMap.DAMappingUint32;
    using DAEvidenceMap for DAEvidenceMap.DAMappingStringArray;
    using DAEvidenceMap for DAEvidenceMap.DAMappingBytes32;

    mapping(address => string) private _userAddressToBid;

    mapping(string => UserInfoV1) private _usersV1; // usci => UserInfoV1
    mapping(bytes32 => CommEvidence) private _commEvidences; // innerEid = CommEvidence

    mapping(bytes32 => bytes32) private _outerEidToInnerEid; // Hash(outerEid) = innerEid

    CommData internal commData; // 存放合约管理相关的数据信息


    event UserCreated(address indexed sender, string indexed bid, string indexed usci);
    event UserRoleChanged(address indexed sender, string indexed bid);
    event EvidenceStored(address indexed sender, string indexed udri, string indexed usci);
    event EvidenceCommStored(address indexed sender, string indexed eid);


    /*
     存证设置获取
     调用顺序：  _storeCommEvidence 
                _setCommEvidenceIndefiniteStringArray
                _setCommEvidenceIndefiniteString
                _bindOuterInnerEid
                _emitEvidence
    */
    function _storeCommEvidence(bytes32 innerEid, string memory category, string[] memory metaData, string[] memory variableData) internal {
        require(_commEvidences[innerEid].timestamp == 0, "EvidenceStorage: udri already exists");
        _commEvidences[innerEid].category = category;
        _commEvidences[innerEid].metaData = metaData;
        _commEvidences[innerEid].variableData = variableData;
        _commEvidences[innerEid].timestamp = block.timestamp;
        // _commEvidences[innerEid] = CommEvidence({
        //    category: category,
        //    indefinite: indefinite,
        //    metaData: metaData,
        //    variableData: variableData,
        //    timestamp: block.timestamp
        // });
        // emit EvidenceCommStored(msg.sender, outerEid);
    }

    function _setCommEvidenceIndefiniteStringArray(bytes32 innerEid, string memory key, string[] memory value) internal {
        _commEvidences[innerEid].indefiniteStringArray.add(keccak256(bytes(key)), value);
    }

    function _setCommEvidenceIndefiniteString(bytes32 innerEid, string memory key, string memory value) internal {
        _commEvidences[innerEid].indefiniteString.add(keccak256(bytes(key)), value);
    }

    function _setCommEvidencevariableData(bytes32 innerEid, string memory key, string memory value) internal {
        _commEvidences[innerEid].variableDataMap.add(keccak256(bytes(key)), value);
    }

    function _setCommEvidenceIndefiniteUint32(bytes32 innerEid, string memory key, uint32 value) internal {
        _commEvidences[innerEid].indefiniteUint32.add(keccak256(bytes(key)), value);
    }

    function _setCommEvidenceIndefiniteBytes32(bytes32 innerEid, string memory key, bytes32 value) internal {
        _commEvidences[innerEid].indefiniteBytes32.add(keccak256(bytes(key)), value);
    }

    function _emitNewEvidence(string memory outerEid) internal {
        emit EvidenceCommStored(msg.sender, outerEid);
    }

    function _evidenceIsExist(bytes32 innerEid) internal view returns (bool) {
        CommEvidence storage evidence = _commEvidences[innerEid];
        return (evidence.timestamp != 0);
    }

    function _getCommEvidenceById(bytes32 innerEid) internal view returns (bool, uint256 timestamp, string memory category, string[] memory metaData, string[] memory variableData ) {
        CommEvidence storage evidence = _commEvidences[innerEid];
        return (evidence.timestamp != 0, evidence.timestamp, evidence.category, evidence.metaData, evidence.variableData);
    }

    function _getCommEvidenceStorageById(bytes32 innerEid) internal view returns (CommEvidence storage evidence) {
        return _commEvidences[innerEid];
    }

    function _getCommEvidenceIndefiniteStringArray(bytes32 innerEid, string memory key) internal view returns(string[] memory value) {
        require(_commEvidences[innerEid].timestamp != 0, "EvidenceStorage: Evidence not exists");
        return _commEvidences[innerEid].indefiniteStringArray.get(keccak256(bytes(key)));
    }

    function _getCommEvidenceIndefiniteString(bytes32 innerEid, string memory key) internal view returns(string memory value) {
        require(_commEvidences[innerEid].timestamp != 0, "EvidenceStorage: Evidence not exists");
        return _commEvidences[innerEid].indefiniteString.get(keccak256(bytes(key)));
    }

    function _getCommEvidenceIndefiniteUint32(bytes32 innerEid, string memory key) internal view returns(uint32 value) {
        require(_commEvidences[innerEid].timestamp != 0, "EvidenceStorage: Evidence not exists");
        return _commEvidences[innerEid].indefiniteUint32.get(keccak256(bytes(key)));
    }

    function _getCommEvidenceIndefiniteBytes32(bytes32 innerEid, string memory key) internal view returns(bytes32 value) {
        require(_commEvidences[innerEid].timestamp != 0, "EvidenceStorage: Evidence not exists");
        return _commEvidences[innerEid].indefiniteBytes32.get(keccak256(bytes(key)));
    }

    function _getCommEvidencevariableDataMap(bytes32 innerEid, string memory key) internal view returns(string memory value) {
        require(_commEvidences[innerEid].timestamp != 0, "EvidenceStorage: Evidence not exists");
        return _commEvidences[innerEid].variableDataMap.get(keccak256(bytes(key)));
    }

    function _getCommEvidence(bytes32 innerEid) internal view returns(CommEvidence storage value) {
        require(_commEvidences[innerEid].timestamp != 0, "EvidenceStorage: Evidence not exists");
        return _commEvidences[innerEid];
    }


    // 用户 & 用户权限 设置获取

    /*
    添加用户
        _storeUser
        ... 通用数据设置
        _emitNewUser
    */
    function _storeUser(string memory bid, string memory usci, string memory name, address account) internal {
        require(_usersV1[bid].timestamp == 0, "UserStorage: bid already exists");
        require(bytes(_userAddressToBid[account]).length == 0, "EvidenceStorage: account already exists");
        UserInfoV1 storage user = _usersV1[bid];
        user.bid = bid;
        user.usci = usci;
        user.name = name;
        user.evidenceCount = 0;
        user.account = account;
        user.timestamp = block.timestamp;
        _userAddressToBid[account] = bid;
        // emit UserCreated(msg.sender, bid, usci);
    }

    function _emitNewUser(string memory bid, string memory usci) internal {
        emit UserCreated(msg.sender, bid, usci);
    }

    /* 添加用户角色 */
    function _addUserRole(string memory bid, string memory role) internal {
        require(_usersV1[bid].timestamp != 0, "UserStorage: bid not exists");
        UserInfoV1 storage user = _usersV1[bid];
        user.role[keccak256(bytes(role))] = USER_STATUS_ACTIVE;
    }

    /* 移除用户角色 */
    function _removeUserRole(string memory bid, string memory role) internal {
        require(_usersV1[bid].timestamp != 0, "UserStorage: bid not exists");
        UserInfoV1 storage user = _usersV1[bid];
        if (user.role[keccak256(bytes(role))] == USER_STATUS_ACTIVE) {
            user.role[keccak256(bytes(role))] = USER_STATUS_DISABLED;
        }
    }

    function _emitUserRoleChanged(string memory bid) internal {
        emit UserRoleChanged(msg.sender, bid);
    }

    /* 获取用户信息 */
    function _getUserByBid(string memory bid) internal view returns (bool, string memory, string memory, uint256, uint256) {
        UserInfoV1 storage user = _usersV1[bid];
        // require(bytes(usci).length > 0, "EvidenceStorage: Account not exists");
        return (user.timestamp != 0, user.usci, user.name, user.evidenceCount, user.timestamp);
    }

    function _getUseStoragerByBid(string memory bid) internal view returns (UserInfoV1 storage) {
        return _usersV1[bid];
    }

    /* 新加用户确权存证数目 */
    function _addEvidenceInUser(string memory bid, bytes32 key, bytes32 value) internal {
        UserInfoV1 storage user = _usersV1[bid];
        // require(bytes(usci).length > 0, "EvidenceStorage: Account not exists");
        user.indefiniteBytes32.update(key, value);
        user.evidenceCount = user.evidenceCount + 1;
    }

    /* 获取用户某个角色的状态 */
    function _getUserRoleStatus(string memory bid, string memory role) internal view returns (uint32) {
        require(_usersV1[bid].timestamp != 0, "UserStorage: bid not exists");
        UserInfoV1 storage user = _usersV1[bid];
        return user.role[keccak256(bytes(role))];
    }

    /* 获取用户信息 */
    function _getUserByAccount(address account) internal view returns (bool, string memory, string memory, uint256, uint256) {
        string memory bid = _userAddressToBid[account];
        // require(bytes(usci).length > 0, "EvidenceStorage: Account not exists");
        return _getUserByBid(bid);
    }

    /* 通过用户account获取用户bid */
    function _getUserBidByAccount(address account) internal view returns (bool, string memory) {
        string memory bid = _userAddressToBid[account];
        return (bytes(bid).length > 0, bid);
    }

    /* 判断用户存在 */
    function _userExist(string memory bid) internal view returns (bool) {
        return _usersV1[bid].timestamp != 0;
    }

    //
    function _bindOuterInnerEid(bytes32 outer, bytes32 inner) internal {
        require(_outerEidToInnerEid[outer] == 0, "EvidenceStorage: bid already exists");
        _outerEidToInnerEid[outer] = inner;
    }

    function _GetInnerEidFromOuterEid(bytes32 outer) internal view returns (bytes32){
        require(_outerEidToInnerEid[outer] != 0, "EvidenceStorage: bid not exists");
        return _outerEidToInnerEid[outer];
    }

    //empty reserved space for future to add new variables
    uint256[49] private __gap;
}