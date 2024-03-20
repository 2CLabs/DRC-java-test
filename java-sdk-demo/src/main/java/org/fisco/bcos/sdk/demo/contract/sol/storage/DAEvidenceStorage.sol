// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "./DAEvidenceStorageLib.sol";
import "../utils/DAEvidenceMap.sol";
import "@openzeppelin/contracts-upgradeable/proxy/utils/Initializable.sol";

contract DAEvidenceStorage is Initializable {

    DAEvidenceStorageLib.DAEStorage internal dataStorage;

    event UserCreated(address indexed sender, string indexed bid, string indexed usci);
    event UserRoleChanged(address indexed sender, string indexed bid);
    event EvidenceStored(address indexed sender, string indexed udri, string indexed usci);
    event EvidenceCommStored(address indexed sender, string indexed eid);

    function _emitNewEvidence(string memory outerEid) internal {
        emit EvidenceCommStored(msg.sender, outerEid);
    }

    function _emitNewUser(string memory bid, string memory usci) internal {
        emit UserCreated(msg.sender, bid, usci);
    }

    function _emitUserRoleChanged(string memory bid) internal {
        emit UserRoleChanged(msg.sender, bid);
    }

    //empty reserved space for future to add new variables
    uint256[49] private __gap;
}