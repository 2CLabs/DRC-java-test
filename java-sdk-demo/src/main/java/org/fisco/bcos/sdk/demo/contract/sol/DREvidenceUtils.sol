// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

library DREvidenceUtils {

    // DRMappingString
    struct DRMappingString {
        mapping(bytes32 => string) _kv;
    }

    function contains(DRMappingString storage set, bytes32 key) internal view returns (bool) {
        return bytes(set._kv[key]).length != 0;
    }

    function add(DRMappingString storage set, bytes32 key, string memory value) internal returns (bool) {
        if (!contains(set, key)) {
            set._kv[key] = value;
            return true;
        } else {
            return false;
        }
    }

    function update(DRMappingString storage set, bytes32 key, string memory value) internal returns (bool) {
        set._kv[key] = value;
        return true;
    }

    function get(DRMappingString storage set, bytes32 key) internal view returns (string memory) {
        return set._kv[key];
    }

    // DRMappingStringArray
    struct DRMappingStringArray {
        mapping(bytes32 => string[] ) _kv;
    }

    function contains(DRMappingStringArray storage set, bytes32 key) internal view returns (bool) {
        return set._kv[key].length != 0;
    }

    function add(DRMappingStringArray storage set, bytes32 key, string[] memory value) internal returns (bool) {
        if (!contains(set, key)) {
            set._kv[key] = value;
            return true;
        } else {
            return false;
        }
    }

    function update(DRMappingStringArray storage set, bytes32 key, string[] memory value) internal returns (bool) {
        set._kv[key] = value;
        return true;
    }

    function get(DRMappingStringArray storage set, bytes32 key) internal view returns (string[] memory) {
        return set._kv[key];
    }

    // DRMappingUint32
    struct DRMappingUint32 {
        mapping(bytes32 => uint32) _kv;
    }

    function update(DRMappingUint32 storage set, bytes32 key, uint32 value) internal returns (bool) {
        set._kv[key] = value;
        return true;
    }

    function add(DRMappingUint32 storage set, bytes32 key, uint32 value) internal returns (bool) {
        return update(set, key, value);
    }

    function get(DRMappingUint32 storage set, bytes32 key) internal view returns (uint32) {
        return set._kv[key];
    }
}
