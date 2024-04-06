// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "@openzeppelin/contracts/proxy/transparent/TransparentUpgradeableProxy.sol";

interface IDREvidenceLogicMan {
    function setSelector(bytes4 selector, address logicAddress)  external;
    function setSelectors(bytes4[] memory selectors, address[] memory logicAddresses)  external;
}

contract DREvProxy is TransparentUpgradeableProxy {
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

    function _dispatchSetSelectors() private returns (bytes memory)  {
        (bytes4[] memory selectors, address[] memory logicAddresses) = abi.decode(msg.data[4:], (bytes4[], address[]));
        require(selectors.length > 0 && logicAddresses.length > 0, "selectors & logicAddresses length should be > 0");
        require(selectors.length == logicAddresses.length, "selectors length should be equal to logicAddresses length");
        uint32 len = uint32(selectors.length);
        for(uint32 i = 0; i < len; i++) {
            controllerSlector[selectors[i]] = logicAddresses[i];
        }
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
            } else if (selector == IDREvidenceLogicMan.setSelectors.selector) {
                ret = _dispatchSetSelectors();
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