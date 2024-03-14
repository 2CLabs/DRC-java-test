// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "@openzeppelin/contracts/utils/Strings.sol";

/**
 * @dev String operations.
 */
library DAEvidenceString {
    bytes16 private constant HEX_DIGITS = "0123456789abcdef";

    function concat(string memory str1, string memory str2) internal pure returns (string memory) {
        return string(abi.encodePacked(str1, str2));  
    }

    /**
     * @dev Converts a `uint256` to its ASCII `string` hexadecimal representation with fixed length.
     */
    function toHexStringWithoutPrefix(uint256 value, uint256 length) internal pure returns (string memory) {
        uint256 localValue = value;
        bytes memory buffer = new bytes(2 * length);

        for (uint256 i = 2 * length; i > 0; --i) {
            buffer[i - 1] = HEX_DIGITS[localValue & 0xf];
            localValue >>= 4;
        }

        if (localValue != 0) {
            //revert Strings.StringsInsufficientHexLength(value, length);
        }

        return string(buffer);
    }

    /**
     * @dev Converts a `uint256` to its ASCII `string` hexadecimal representation.
     */
    function toHexStringWithoutPrefix(uint256 value) internal pure returns (string memory) {
        unchecked {
            return toHexStringWithoutPrefix(value, Math.log256(value) + 1);
        }
    }

    function toHexStringWithoutPrefix(bytes32 value) internal pure returns (string memory) {
        unchecked {
            return toHexStringWithoutPrefix(uint256(value), Math.log256(uint256(value)) + 1);
        }
    }

    function toHexString(uint8 value) private pure returns (string memory) {
        bytes16 hexadecimalRepresentation = "0123456789ABCDEF";

        uint256 highNibble = value / 16;
        uint256 lowNibble = value % 16;
        return string(
            abi.encodePacked(
                hexadecimalRepresentation[highNibble],
                hexadecimalRepresentation[lowNibble]
            )
        );
    }
}