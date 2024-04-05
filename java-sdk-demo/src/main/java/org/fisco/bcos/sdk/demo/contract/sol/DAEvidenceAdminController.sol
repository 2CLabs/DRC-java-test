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

contract DAEvidenceAdminController is Initializable, DAAccessController, DAEvidenceStorage {

    using DAEvidenceMap for DAEvidenceMap.DAMappingString;
    using DAEvidenceMap for DAEvidenceMap.DAMappingUint32;
    using DAEvidenceMap for DAEvidenceMap.DAMappingStringArray;
    using DAEvidenceMap for DAEvidenceMap.DAMappingBytes32;
    using DAEvidenceMap for DAEvidenceMap.DAMappingAddress;
    using Strings for uint32;
    using DAEvidenceString for string;
    using DAEvidenceString for bytes32;
    using DAEvidenceString for uint256;
    // using DAEvidenceString for uint32;

    using DAEvidenceStorageLib for DAEvidenceStorageDefine.DAEStorage;


    function initialize() public initializer {
        _setupRole(DEFAULT_ADMIN_ROLE, msg.sender);
        // _setRoleAdmin(DAEVIDENCE_ROLE, APPROVER_ROLE);
        grantUserManagePermission("bid:0", msg.sender);

        string memory keyStr = "chain_name=";
        dataStorage.commData.byte32ToString.update(keyStr.hash(), "ShuXin");

        keyStr = "text_max_len=";
        dataStorage.commData.byte32ToUint32.update(keyStr.hash(), 5000);

        keyStr = "str_array_max_len=";
        dataStorage.commData.byte32ToUint32.update(keyStr.hash(), 1000);

        keyStr = "control_enabled=";
        dataStorage.commData.byte32ToUint32.update(keyStr.hash(), 0);
    }

    function setChainName(string memory name) public onlyRole(DEFAULT_ADMIN_ROLE) {
        string memory keyStr = "chain_name=";
        dataStorage.commData.byte32ToString.update(keyStr.hash(), name);
    }

    function setTextMaxLen(uint32 len) public onlyRole(DEFAULT_ADMIN_ROLE) {
        string memory keyStr = "text_max_len=";
        dataStorage.commData.byte32ToUint32.update(keyStr.hash(), len);
    }

    function setstrArrayMaxLen(uint32 len) public onlyRole(DEFAULT_ADMIN_ROLE) {
        string memory keyStr = "str_array_max_len=";
        dataStorage.commData.byte32ToUint32.update(keyStr.hash(), len);
    }

    //set isAccessControlEnabled to true to enable access control
    function enableAccessControl() public onlyRole(DEFAULT_ADMIN_ROLE) {
        string memory keyStr = "control_enabled=";
        dataStorage.commData.byte32ToUint32.update(keyStr.hash(), 1);
    }

    //set isAccessControlEnabled to false to disable access control
    function disableAccessControl() public onlyRole(DEFAULT_ADMIN_ROLE) {
        string memory keyStr = "control_enabled=";
        dataStorage.commData.byte32ToUint32.update(keyStr.hash(), 0);
    }

    function getChainName() public view returns(string memory name) {
        string memory keyStr = "chain_name=";
        return dataStorage.commData.byte32ToString.get(keyStr.hash());
    }

    function getTextMaxLen() public view returns(uint32 len) {
        string memory keyStr = "text_max_len=";
        return dataStorage.commData.byte32ToUint32.get(keyStr.hash());
    }

    function getstrArrayMaxLen() public view returns(uint32 len) {
        // _strArrayMaxLen = len;
        string memory keyStr = "str_array_max_len=";
        return dataStorage.commData.byte32ToUint32.get(keyStr.hash());
    }

    function getAccessControl() public view returns(uint32 status) {
        string memory keyStr = "control_enabled=";
        return dataStorage.commData.byte32ToUint32.get(keyStr.hash());
    }

    function setContract(string memory logicName, address _LogicContract) public onlyRole(DEFAULT_ADMIN_ROLE) {
        dataStorage.LogicAddress[logicName] = _LogicContract;
    }

    function getContract(string memory logicName) public view returns(address _LogicContract) {
        return dataStorage.LogicAddress[logicName];
    }

    // 获取当前某种存证支持的 variableData 字段
    function getSupportVariableDataFields(string memory category) public view returns (string[] memory fileds) {
        return dataStorage.getSupportVariableDataFields(category);
    }

    // 设置某种存证 variableData 支持的字段，实现逻辑是：先将之前的所有fields都清除，再重新设置，原则上是只能在原有基础上新加字段，减少字段会导致之前保存的字段不能获取到
    function setDataRightSupportVariableDataFields(string memory category, string[] memory fields) public onlyRole(DEFAULT_ADMIN_ROLE) {
        // TODO: 可以设置成开关，支持验证或则不验证这些字段
        dataStorage.setDataRightSupportVariableDataFields(category, fields);
    }

    // 数据权限管理--- 设置分类 （1.数据资源持有权; 2.数据加工使用权; 3.数据产品经营权。）
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

    // 数据权限管理--- 获取分类
    function getDataRightCategory() public view returns(string[] memory fields) {
        return dataStorage.getDataRightCategory();
    }

    // function _implementation() internal view virtual override returns (address) {
    //     address nextLogicContract = dataStorage.LogicAddress["next_logic_of_admin"];
    //     require(nextLogicContract != address(0), "Unknown function.");
    //     return nextLogicContract;
    // }
}
