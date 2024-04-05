// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "../utils/DAEvidenceMap.sol";
import "../utils/DAEvidenceString.sol";

library DAEvidenceStorageDefine {
    struct UserInfoV1 {
        string bid; // 链上身份标识
        string usci;
        string name;
        uint256 evidenceCount;
        address account;
        DAEvidenceMap.DAMappingStringArray indefiniteStringArray;
        DAEvidenceMap.DAMappingString indefiniteString;
        DAEvidenceMap.DAMappingUint32 indefiniteUint32;
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
        DAEvidenceMap.DAMappingString variableDataMap;

        uint256 timestamp;
    }

    struct CommData {
        DAEvidenceMap.DAMappingStringArray byte32ToStringArrary;
        DAEvidenceMap.DAMappingString byte32ToString;
        DAEvidenceMap.DAMappingUint32 byte32ToUint32;
        DAEvidenceMap.DAMappingBytes32 byte32ToBytes32;
        DAEvidenceMap.DAMappingAddress byte32ToAddress;
    }

    struct DAEStorage {
        mapping(address => string) _userAddressToBid;

        mapping(string => UserInfoV1) _usersV1; // usci => UserInfoV1
        mapping(bytes32 => CommEvidence) _commEvidences; // innerEid = CommEvidence

        mapping(bytes32 => bytes32) _outerEidToInnerEid; // Hash(outerEid) = innerEid

        CommData commData; // 存放合约管理相关的数据信息

        mapping(string => address) LogicAddress; // controller中 下一个合约的地址
    }

}