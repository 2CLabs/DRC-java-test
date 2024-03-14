// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./DAEvidenceConstants.sol";
import "./DAEvidenceBasicController.sol";
import "../DAAccessController.sol";
import "../DAEvidenceStorage.sol";
import "../utils/DAEvidenceString.sol";
import "@openzeppelin/contracts/utils/Strings.sol";

/* 确权存证 controller, 设置 查询*/
contract DARightEvidenceController is DAEvidenceBasicController {

    using DAEvidenceMap for DAEvidenceMap.DAMappingString;
    using DAEvidenceMap for DAEvidenceMap.DAMappingUint32;
    using DAEvidenceMap for DAEvidenceMap.DAMappingStringArray;
    using Strings for uint32;
    using DAEvidenceString for string;
    using DAEvidenceString for bytes32;
    using DAEvidenceString for uint256;

    function _addDataRight(bytes32 innerEid, string memory bid, string[] memory dataRight) internal {
        string memory prefix = "dataRight:";
        for( uint32 i = 0; i < dataRight.length; i++) {
            string memory key = prefix.concat(bid).concat(dataRight[i]);
            _setCommEvidenceIndefiniteString(innerEid, key, "exist");
        }
    }

    function _removeDataRight(bytes32 innerEid, string memory bid, string[] memory dataRight) internal {
        string memory prefix = "dataRight:";
        for( uint32 i = 0; i < dataRight.length; i++) {
            string memory key = prefix.concat(bid).concat(dataRight[i]);
            _setCommEvidenceIndefiniteString(innerEid, key, "");
        }
    }

    /*4. 存证 */
    /* 4.1.1 新增确权存证 */
    function registerDataRight(string memory udri, string memory bid, string[] memory dataHash, string[] memory dataRight, string []memory metaData, string []memory variableData) public {
        // TODO： 确保dataHash没有被添加过
        _verifyBid(bid);

        bytes32 innerEid = keccak256(bytes(EVIDENCE_CATEGORY_RIGHT.concat(udri)));
        string[] memory indefinite;
        _storeCommEvidence(innerEid, EVIDENCE_CATEGORY_RIGHT, indefinite, metaData, variableData);
        _setCommEvidenceIndefiniteString(innerEid, "version", "EVIDENCE_CONTRACT_VERSION_V1");
        _setCommEvidenceIndefiniteString(innerEid, "udri", udri);
        _setCommEvidenceIndefiniteString(innerEid, "bid", bid);
        _setCommEvidenceIndefiniteString(innerEid, "status", EVIDENCE_STATUS_ACTIVE);
        _setCommEvidenceIndefiniteStringArray(innerEid, "dataHash", dataHash);
        
        // _setCommEvidenceIndefiniteStringArray(innerEid, "dataRight", dataRight);
        _addDataRight(innerEid, bid, dataRight);

        // TODO: 设置用户当前的确权存证合约总数

        // save variableData
        _setVariableData(innerEid, EVIDENCE_CATEGORY_RIGHT, variableData);

        // Generate outerEid
        string memory outerEid = EVIDENCE_ID_PREFIX_NEW.concat(innerEid.toHexStringWithoutPrefix());
        _bindOuterInnerEid(keccak256(bytes(outerEid)), innerEid);
        _emitNewEvidence(outerEid);
    }

    /* 4.1.2  追加可变数据存证 */
    function appendVariableData(string memory udri, string[] memory variableData) public{
        // TODO: 确保是owner在调用该接口
        require(variableData.length > 0, "variableData is empty");
        require(variableData.length % 2 == 0, "variableData length error");
        bytes32 innerEid = keccak256(bytes(EVIDENCE_CATEGORY_RIGHT.concat(udri)));

        _setVariableData(innerEid, EVIDENCE_CATEGORY_RIGHT, variableData);
    }

    /* 4.1.3 撤回确权存证 */
    function withdrawDataRightRegister(string memory udri, /*string memory dataRightOwnerBid, */string[] memory dataRight) public {
        // TODO: 确保是owner在调用该接口
        bytes32 innerEid = keccak256(bytes(EVIDENCE_CATEGORY_RIGHT.concat(udri)));
        // TODO: dataRightOwnerBid 似乎可以不需要填入
        string memory bid = _getCommEvidenceIndefiniteString(innerEid, "bid");
        _removeDataRight(innerEid, bid, dataRight);
    }

    /* 4.1.4 新增授权存证 */
    function grantUserDataRight(string memory udri, string memory bid, string[] memory dataRight) public {
        _verifyBid(bid);
        // TODO: 确保是owner在调用该接口
        bytes32 innerEid = keccak256(bytes(EVIDENCE_CATEGORY_RIGHT.concat(udri)));
        _addDataRight(innerEid, bid, dataRight);
    }

    /* 4.1.5 撤回授权存证 */
    function withdrawUserDataRight(string memory udri, string memory bid, string[] memory dataRight) public {
        // TODO: 确保是owner在调用该接口
        _verifyBid(bid);
        bytes32 innerEid = keccak256(bytes(EVIDENCE_CATEGORY_RIGHT.concat(udri)));
        _removeDataRight(innerEid, bid, dataRight);
    }
    
    /* 5.2 查询确权存证信息 */
    /* 5.2.1 查询存证数据标识 */
    function getUdriByDatahash(string calldata dataHash) public view returns (string memory) {
        // TODO
    }

    /* 5.2.2 查询确权存证信息 */
    function getRegisteredData(string memory udri) public view returns (string memory,string memory,string[] memory,string[] memory,string[] memory,bool) {
        // TODO
    }

    /* 5.2.3 查询授权信息 */
    function getUserDataRight(string memory udri,string memory bid) public {
        // TODO
    }

}
