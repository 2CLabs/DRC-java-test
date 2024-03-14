// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./DAEvidenceConstants.sol";
import "../DAAccessController.sol";
import "../DAEvidenceStorage.sol";
import "../utils/DAEvidenceString.sol";
import "@openzeppelin/contracts/utils/Strings.sol";

contract DAEvidenceBasicController is DAEvidenceConstants, DAAccessController, DAEvidenceStorage {

    using DAEvidenceMap for DAEvidenceMap.DAMappingString;
    using DAEvidenceMap for DAEvidenceMap.DAMappingUint32;
    using DAEvidenceMap for DAEvidenceMap.DAMappingStringArray;
    using Strings for uint32;
    using DAEvidenceString for string;
    using DAEvidenceString for bytes32;
    using DAEvidenceString for uint256;


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
    function getDataRightSupportVariableDataFields() public view returns (string[] memory) {
        bytes32 key = keccak256(bytes("data_right:variable_data="));
        return commData.byte32ToStringArrary.get(key);
    }

    // 设置某种存证 variableData 支持的字段，实现逻辑是：先将之前的所有fields都清除，再重新设置，原则上是只能在原有基础上新加字段，减少字段会导致之前保存的字段不能获取到
    function setDataRightSupportVariableDataFields(string memory category, string[] memory fields) public {
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
    }
}
