// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./utils/DAEvidenceString.sol";
import "./utils/DAEvidenceMap.sol";
import "./DAAccessController.sol";
import "./storage/DAEvidenceStorage.sol";
import "./storage/DAEvidenceStorageLib.sol";
import "./storage/DAEvidenceStorageDefine.sol";
import "./storage/DAEvidenceStorageConstant.sol";
import "@openzeppelin/contracts/utils/Strings.sol";
import "@openzeppelin/contracts-upgradeable/proxy/utils/Initializable.sol";
import "@openzeppelin/contracts/proxy/Proxy.sol";

contract DAEvidenceUserController is Initializable, DAAccessController, DAEvidenceStorage {

    using DAEvidenceMap for DAEvidenceMap.DAMappingString;
    using DAEvidenceMap for DAEvidenceMap.DAMappingUint32;
    using DAEvidenceMap for DAEvidenceMap.DAMappingStringArray;
    using DAEvidenceMap for DAEvidenceMap.DAMappingBytes32;
    using Strings for uint32;
    using DAEvidenceString for string;
    using DAEvidenceString for bytes32;
    using DAEvidenceString for uint256;
    // using DAEvidenceString for uint32;

    using DAEvidenceStorageLib for DAEvidenceStorageDefine.DAEStorage;


    /******************************************** 内部接口 **************************************************/
    function _hasDAUserManageRole() internal view returns(bool) {
        (bool isExist, string memory bid) = dataStorage._getUserBidByAccount(msg.sender);
        require(isExist == true, "Sender is not registered.");
        return hasDAUserManageRole(bid);
    }

    function _isDataRightEvidenceOwner(string memory udri) internal view returns(bool) {
        bytes32 innerEid = keccak256(
            bytes(DAEvidenceStorageConstant.EVIDENCE_CATEGORY_RIGHT.concat(udri))
        );
        (bool isExist, string memory bid) = dataStorage._getUserBidByAccount(msg.sender);
        require(isExist == true, "Sender is not registered.");

        DAEvidenceStorageDefine.CommEvidence storage evidence = dataStorage._getCommEvidence(innerEid);
        require(evidence.timestamp != 0, "evidence is not exist.");

        string memory bidInEvidence = dataStorage._getCommEvidenceIndefiniteString(innerEid, "bid");

        return bid.equal(bidInEvidence);
    }

    // 检查sender是 存证 owner
    function _requireDataRightEvidenceOwnerByUrid(string memory udri) internal view{
        require(_isDataRightEvidenceOwner(udri) == true, "Sender is not evidence owner.");
    }

    // 检查sender是否是某个角色
    function _checkUserRole(string memory role) internal view{
        (bool isExist, string memory bid) = dataStorage._getUserBidByAccount(msg.sender);
        require(isExist == true, "Sender is not registered.");
        DAEvidenceStorageDefine.UserInfoV1 storage user = dataStorage._getUseStoragerByBid(bid);
        require((user.indefiniteString.get(DAEvidenceStorageConstant.USER_ROLE_PREFIX.concat(role).hash()).equal("exist")) == true, "User without corresponding role permissions.");
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
        _emitNewUser(bid, usci);
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
        _emitUserRoleChanged(bid);
    }

    /*
    3.2.4 回收用户角色
    由用户管理员回收用户的一或多个角色。
    */
    function revokeUserRoles(string memory bid, string[] memory roles) public onlyRole(USERMANAGE_ROLE) {
        dataStorage.revokeUserRoles(bid, roles);
        _emitUserRoleChanged(bid);
    }

    /* 4.3 登记存证（二阶段）*/

    /* 5. 查询相关接口 */
    /* 5.1  查询用户数据信息 */
    /* 5.1.1 查询用户数据数量 */
    function getDataCount(string calldata bid) public view returns (uint256 dataCount) {
        return dataStorage.getDataCount(bid);
    }

    /* 5.1.2  查询用户数据列表 */
    function getDataList(string memory bid, uint256 start, uint256 count) public view returns (string[] memory udriArray) {
        return dataStorage.getDataList(bid, start, count);
    }

    // function _implementation() internal view virtual override returns (address) {
    //     address nextLogicContract = dataStorage.LogicAddress["next_logic_of_user"];
    //     require(nextLogicContract != address(0), "Unknown function.");
    //     return nextLogicContract;
    // }
}