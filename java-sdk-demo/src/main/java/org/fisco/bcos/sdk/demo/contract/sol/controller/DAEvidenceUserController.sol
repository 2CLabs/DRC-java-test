// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./DAEvidenceBasicController.sol";
import "./DAEvidenceConstants.sol";
import "../DAAccessController.sol";
import "../DAEvidenceStorage.sol";
import "../utils/DAEvidenceString.sol";
import "@openzeppelin/contracts/utils/Strings.sol";

/* 用户 controller, 设置 查询*/
contract DAEvidenceUserController is DAEvidenceBasicController {

    using DAEvidenceMap for DAEvidenceMap.DAMappingString;
    using DAEvidenceMap for DAEvidenceMap.DAMappingUint32;
    using DAEvidenceMap for DAEvidenceMap.DAMappingStringArray;
    using Strings for uint32;
    using DAEvidenceString for string;
    using DAEvidenceString for bytes32;
    using DAEvidenceString for uint256;


    function revokeUserRole(string memory bid, string[] memory roles) public onlyRole(USERMANAGE_ROLE) {
        for(uint i = 0; i < roles.length; i++)
          _removeUserRole(bid, roles[i]);
    }

    function queryUserRole() public pure returns (string[] memory) {
        // return ["dataRightOwner", "reviewer", "registry", "platform"];
        string[] memory roles = new string[](4);
        roles[0] = "dataRightOwner";
        roles[1] ="reviewer";
        roles[2] ="registry";
        roles[3] ="platform";
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
    function addUser(string memory bid, string memory usci, string memory name,address account, string[] memory roles) public {
    }

    /*
    3.2.2  查询用户
    根据用户bid查询当前用户信息及其绑定的角色。
    */
    function getUserRoles(string memory bid) public view returns(string memory usci,string memory name,string[] memory roles) {
    }

    /*
    3.2.3 添加用户角色
    为用户添加新的角色。
    */
    function grantUserRoles(string memory bid, string[] memory roles) public {
    }

    /*
    3.2.4 回收用户角色
    由用户管理员回收用户的一或多个角色。
    */
    function revokeUserRoles(string memory bid, string[] memory roles) public {
    }

    /* 4.3 登记存证（二阶段）*/

    /* 5. 查询相关接口 */
    /* 5.1  查询用户数据信息 */
    /* 5.1.1 查询用户数据数量 */
    function getDataCount(string calldata bid) public view returns (uint256 dataCount) {
        // TODO
    }

    /* 5.1.2  查询用户数据列表 */
    function getDataList(string memory bid, uint256 start, uint256 count) public view returns (string[] memory udriArray) {
        // TODO
    }
}
