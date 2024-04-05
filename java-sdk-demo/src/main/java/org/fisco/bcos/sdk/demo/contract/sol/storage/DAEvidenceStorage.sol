// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "./DAEvidenceStorageLib.sol";
import "../utils/DAEvidenceMap.sol";
import "./DAEvidenceStorageDefine.sol";
import "@openzeppelin/contracts-upgradeable/proxy/utils/Initializable.sol";

contract DAEvidenceStorage is Initializable {

    DAEvidenceStorageDefine.DAEStorage internal dataStorage;

    event UserCreated(address indexed sender, string indexed bid, string indexed usci);
    event UserRoleChanged(address indexed sender, string indexed bid);
    event NewRightEvidenceStored(address indexed sender, string indexed udri);
    event NewReviewEvidenceStored(address indexed sender, string indexed udri, uint32 indexed index);


    function _emitNewRightEvidence(string memory udri) internal {
        emit NewRightEvidenceStored(msg.sender, udri);
    }

    function _emitNewReviewEvidence(string memory udri, uint32 index) internal {
        emit NewReviewEvidenceStored(msg.sender, udri, index);
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