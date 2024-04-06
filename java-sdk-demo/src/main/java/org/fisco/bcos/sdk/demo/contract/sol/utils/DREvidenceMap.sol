// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

library DREvidenceMap {

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

    // DRMappingBytes32
    struct DRMappingBytes32 {
        mapping(bytes32 => bytes32) _kv;
    }

    function update(DRMappingBytes32 storage set, bytes32 key, bytes32 value) internal returns (bool) {
        set._kv[key] = value;
        return true;
    }

    function add(DRMappingBytes32 storage set, bytes32 key, bytes32 value) internal returns (bool) {
        return update(set, key, value);
    }

    function get(DRMappingBytes32 storage set, bytes32 key) internal view returns (bytes32) {
        return set._kv[key];
    }

    // DRMappingAddress
    struct DRMappingAddress {
        mapping(bytes32 => address) _kv;
    }

    function update(DRMappingAddress storage set, bytes32 key, address value) internal returns (bool) {
        set._kv[key] = value;
        return true;
    }

    function add(DRMappingAddress storage set, bytes32 key, address value) internal returns (bool) {
        return update(set, key, value);
    }

    function get(DRMappingAddress storage set, bytes32 key) internal view returns (address) {
        return set._kv[key];
    }
}
