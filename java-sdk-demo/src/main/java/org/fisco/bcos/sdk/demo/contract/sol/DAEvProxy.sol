// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "@openzeppelin/contracts/proxy/transparent/TransparentUpgradeableProxy.sol";

interface IDREvidenceLogicMan {
    function setSelector(bytes4 selector, address logicAddress)  external;
}

contract DAEvProxy is TransparentUpgradeableProxy {
    mapping (bytes4 => address) private controllerSlector;

    constructor(address _logic, address admin_, bytes memory _data) TransparentUpgradeableProxy(_logic, admin_, _data) {}

    /**
     * @dev Returns the current implementation address.
     */
    function _implementation() internal view virtual override returns (address impl) {
        return controllerSlector[msg.sig];
    }

    function _dispatchSetSelector() private returns (bytes memory)  {
        (bytes4 selector, address logicAddress) = abi.decode(msg.data[4:], (bytes4, address));
        controllerSlector[selector] = logicAddress;
        return "";
    }

    /**
     * @dev If caller is the admin process the call internally, otherwise transparently fallback to the proxy behavior
     */
    function _fallback() internal virtual override {
        if (msg.sender == _getAdmin()) {
            bytes memory ret;
            bytes4 selector = msg.sig;
            if (selector == IDREvidenceLogicMan.setSelector.selector) {
                ret = _dispatchSetSelector();
                assembly {
                return(add(ret, 0x20), mload(ret))
            }
            } else {
                super._fallback();
            }
        } else {
            super._fallback();
        }
    }
}