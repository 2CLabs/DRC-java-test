// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;


/**
 * @dev String operations.
 */
library DREvidenceBytes {
    function concat(bytes memory a, bytes memory b) public pure returns (bytes memory) {  
        return abi.encodePacked(a, b);  
    }
}