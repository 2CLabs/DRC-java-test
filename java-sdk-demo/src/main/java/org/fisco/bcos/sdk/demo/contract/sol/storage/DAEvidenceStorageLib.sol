// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;


import "@openzeppelin/contracts-upgradeable/proxy/utils/Initializable.sol";
import "../utils/DAEvidenceMap.sol";

contract DAEvidenceStorageLib {

    using DAEvidenceMap for DAEvidenceMap.DAMappingString;
    using DAEvidenceMap for DAEvidenceMap.DAMappingUint32;
    using DAEvidenceMap for DAEvidenceMap.DAMappingStringArray;
    using DAEvidenceMap for DAEvidenceMap.DAMappingBytes32;

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

}