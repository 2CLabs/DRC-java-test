// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "@openzeppelin/contracts/proxy/transparent/ProxyAdmin.sol";
import "./DAEvProxy.sol";

contract DAEvProxyAdmin is ProxyAdmin {
    constructor() {}

    /**
     * @dev Set contract address to handle  function
     *
     * Requirements:
     *
     * - This contract must be the current admin of `proxy`.
     */
    function setSelector(IDREvidenceLogicMan proxy, bytes4 selector, address logicAddress) public virtual onlyOwner {
        proxy.setSelector(selector, logicAddress);
    }

}