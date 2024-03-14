// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

library DAEvidenceMap {

    // DAMappingString
    struct DAMappingString {
        mapping(bytes32 => string) _kv;
    }

    function contains(DAMappingString storage set, bytes32 key) internal view returns (bool) {
        return bytes(set._kv[key]).length != 0;
    }

    function add(DAMappingString storage set, bytes32 key, string memory value) internal returns (bool) {
        if (!contains(set, key)) {
            set._kv[key] = value;
            return true;
        } else {
            return false;
        }
    }

    function update(DAMappingString storage set, bytes32 key, string memory value) internal returns (bool) {
        set._kv[key] = value;
        return true;
    }

    function get(DAMappingString storage set, bytes32 key) internal view returns (string memory) {
        return set._kv[key];
    }

    // DAMappingStringArray
    struct DAMappingStringArray {
        mapping(bytes32 => string[] ) _kv;
    }

    function contains(DAMappingStringArray storage set, bytes32 key) internal view returns (bool) {
        return set._kv[key].length != 0;
    }

    function add(DAMappingStringArray storage set, bytes32 key, string[] memory value) internal returns (bool) {
        if (!contains(set, key)) {
            set._kv[key] = value;
            return true;
        } else {
            return false;
        }
    }

    function update(DAMappingStringArray storage set, bytes32 key, string[] memory value) internal returns (bool) {
        set._kv[key] = value;
        return true;
    }

    function get(DAMappingStringArray storage set, bytes32 key) internal view returns (string[] memory) {
        return set._kv[key];
    }

    // DAMappingUint32
    struct DAMappingUint32 {
        mapping(bytes32 => uint32) _kv;
    }

    function update(DAMappingUint32 storage set, bytes32 key, uint32 value) internal returns (bool) {
        set._kv[key] = value;
        return true;
    }

    function add(DAMappingUint32 storage set, bytes32 key, uint32 value) internal returns (bool) {
        return update(set, key, value);
    }

    function get(DAMappingUint32 storage set, bytes32 key) internal view returns (uint32) {
        return set._kv[key];
    }

    // DAMappingBytes32
    struct DAMappingBytes32 {
        mapping(bytes32 => bytes32) _kv;
    }

    function update(DAMappingBytes32 storage set, bytes32 key, bytes32 value) internal returns (bool) {
        set._kv[key] = value;
        return true;
    }

    function add(DAMappingBytes32 storage set, bytes32 key, bytes32 value) internal returns (bool) {
        return update(set, key, value);
    }

    function get(DAMappingBytes32 storage set, bytes32 key) internal view returns (bytes32) {
        return set._kv[key];
    }
}

