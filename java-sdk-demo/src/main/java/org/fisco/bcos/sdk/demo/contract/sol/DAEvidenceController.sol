// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./controller/DARightEvidenceController.sol";
import "./controller/DAReviewEvidenceController.sol";
import "./controller/DAEvidenceUserController.sol";

import "./utils/DAEvidenceString.sol";
import "@openzeppelin/contracts/utils/Strings.sol";

contract DAEvidenceController is Initializable, DAEvidenceUserController, DARightEvidenceController, DAReviewEvidenceController {

    using DAEvidenceMap for DAEvidenceMap.DAMappingString;
    using DAEvidenceMap for DAEvidenceMap.DAMappingUint32;
    using DAEvidenceMap for DAEvidenceMap.DAMappingStringArray;
    using Strings for uint32;
    using DAEvidenceString for string;
    using DAEvidenceString for bytes32;
    using DAEvidenceString for uint256;

    function initialize() public initializer {
        _setupRole(DEFAULT_ADMIN_ROLE, msg.sender);
        // _setRoleAdmin(DAEVIDENCE_ROLE, APPROVER_ROLE);
        grantUserManagePermission("bid:0", msg.sender);
        _chainName = "ShuXin";
        _textMaxLen = 5000;
        _strArrayMaxLen = 1000;
        _isAccessControlEnabled = false;
    }

    string private _chainName;
    uint32 private _textMaxLen;
    uint32 private _strArrayMaxLen;
    bool private _isAccessControlEnabled;

    function setChainName(string memory name) public onlyRole(DEFAULT_ADMIN_ROLE) {
        _chainName = name;
    }

    function setTextMaxLen(uint32 len) public onlyRole(DEFAULT_ADMIN_ROLE) {
        _textMaxLen = len;
    }

    function setstrArrayMaxLen(uint32 len) public onlyRole(DEFAULT_ADMIN_ROLE) {
        _strArrayMaxLen = len;
    }

    //set isAccessControlEnabled to true to enable access control
    function enableAccessControl() public onlyRole(DEFAULT_ADMIN_ROLE) {
        _isAccessControlEnabled = true;
    }

    //set isAccessControlEnabled to false to disable access control
    function disableAccessControl() public onlyRole(DEFAULT_ADMIN_ROLE) {
        _isAccessControlEnabled = false;
    }

    /*
        eType: ""
        fixData: ["urid", "", "bid", "", "dataHash_1": "", "dataHash_2": "", ""]
    */
    function getDAEvidenceById(bytes32 daevid) public view returns (bool, string memory eType, string[] memory fixData, string[] memory metaData, string[] memory variableData, uint256 timestamp) {
        // TODO
    }

}
