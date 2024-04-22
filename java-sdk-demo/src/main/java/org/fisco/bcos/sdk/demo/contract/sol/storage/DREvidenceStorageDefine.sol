// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "../utils/DREvidenceMap.sol";
import "../utils/DREvidenceString.sol";

library DREvidenceStorageDefine {
    struct UserInfoV1 {
        string bid; // 链上身份标识
        string usci;
        string name;
        uint256 evidenceCount; //确权存证总数
        address account;
        DREvidenceMap.DRMappingStringArray indefiniteStringArray;
        DREvidenceMap.DRMappingString indefiniteString;
        DREvidenceMap.DRMappingUint32 indefiniteUint32;
        DREvidenceMap.DRMappingBytes32 indefiniteBytes32;
        DREvidenceMap.DRMappingUint256 indefiniteUint256;
        mapping(bytes32 => uint32) role; // 0: 无； USER_STATUS_ACTIVE：正常； USER_STATUS_DISABLED：注销

        uint256 timestamp;
    }

    struct CommEvidence {
        string category;
        DREvidenceMap.DRMappingStringArray indefiniteStringArray;
        DREvidenceMap.DRMappingString indefiniteString;
        DREvidenceMap.DRMappingUint32 indefiniteUint32;
        DREvidenceMap.DRMappingBytes32 indefiniteBytes32;
        DREvidenceMap.DRMappingUint256 indefiniteUint256;

        string[] metaData;
        DREvidenceMap.DRMappingString variableDataMap;

        uint256 timestamp;
    }

    struct CommData {
        DREvidenceMap.DRMappingStringArray byte32ToStringArrary;
        DREvidenceMap.DRMappingString byte32ToString;
        DREvidenceMap.DRMappingUint32 byte32ToUint32;
        DREvidenceMap.DRMappingBytes32 byte32ToBytes32;
        DREvidenceMap.DRMappingAddress byte32ToAddress;
        DREvidenceMap.DRMappingUint256 byte32ToUint256;
    }

    struct DREStorage {
        mapping(address => string) _userAddressToBid;

        mapping(string => UserInfoV1) _usersV1; // usci => UserInfoV1
        mapping(bytes32 => CommEvidence) _commEvidences; // innerEid = CommEvidence

        mapping(bytes32 => bytes32) _outerEidToInnerEid; // Hash(outerEid) = innerEid

        CommData commData; // 存放合约管理相关的数据信息
    }
}