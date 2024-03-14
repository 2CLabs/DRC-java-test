// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;


import "./DAEvidenceConstants.sol";
import "./DAEvidenceBasicController.sol";
import "../DAAccessController.sol";
import "../DAEvidenceStorage.sol";
import "../utils/DAEvidenceString.sol";
import "@openzeppelin/contracts/utils/Strings.sol";

/* 审核存证 controller, 设置 查询*/
contract DAReviewEvidenceController is DAEvidenceBasicController {

    using DAEvidenceMap for DAEvidenceMap.DAMappingString;
    using DAEvidenceMap for DAEvidenceMap.DAMappingUint32;
    using DAEvidenceMap for DAEvidenceMap.DAMappingStringArray;
    using Strings for uint32;
    using DAEvidenceString for string;
    using DAEvidenceString for bytes32;
    using DAEvidenceString for uint256;


    /* 4.2 审查存证 */
    /* 4.2.1 新增审查存证 */
    function addReviewEvidence(string memory udri,string memory reviewerBid, string[] memory reviewDataHash, string[] memory metaData, string[] memory variableData) public {
        // TODO： 确保reviewDataHash没有被添加过
        _verifyBid(reviewerBid);

        // TODO: 确保udri对应的数据存证已经存在

        bytes32 rightInnerEid = keccak256(bytes(EVIDENCE_CATEGORY_RIGHT.concat(udri))); //确权存证 内部eid

        string memory str = EVIDENCE_CATEGORY_REVIEW.concat(udri);
        for (uint32 i = 0; i < reviewDataHash.length; i++) {
            str = str.concat(reviewDataHash[i]);
        }
        bytes32 innerEid = keccak256(bytes(str)); //审核存证 内部eid

        string[] memory indefinite;
        _storeCommEvidence(innerEid, EVIDENCE_CATEGORY_REVIEW, indefinite, metaData, variableData);
        _setCommEvidenceIndefiniteString(innerEid, "version", "EVIDENCE_CONTRACT_VERSION_V1");
        _setCommEvidenceIndefiniteString(innerEid, "udri", udri);
        _setCommEvidenceIndefiniteString(innerEid, "reviewerBid", reviewerBid);
        _setCommEvidenceIndefiniteStringArray(innerEid, "reviewDataHash", reviewDataHash);

        // 审核数目信息需要 放到 确权存证 相关的数据中
        string memory key = reviewerBid.concat(":review_count="); // 某个审查机构review数目
        uint32 current_number = _getCommEvidenceIndefiniteUint32(rightInnerEid, key);
        _setCommEvidenceIndefiniteUint32(rightInnerEid, key, current_number + 1);

        _setCommEvidenceIndefiniteBytes32(rightInnerEid, reviewerBid.concat(":review:index:").concat(current_number.toString()), innerEid);

        // 该 udri 数据，所有审查机构的review的数目
        current_number = _getCommEvidenceIndefiniteUint32(rightInnerEid, key);
        _setCommEvidenceIndefiniteUint32(rightInnerEid, "review_count=", current_number + 1);

        _setVariableData(innerEid, EVIDENCE_CATEGORY_REVIEW, variableData);

        // Generate outerEid
        string memory outerEid = EVIDENCE_ID_PREFIX_REVIEW.concat(innerEid.toHexStringWithoutPrefix());
        _bindOuterInnerEid(keccak256(bytes(outerEid)), innerEid);
        _emitNewEvidence(outerEid);
    }


    /* 4.2.2 撤回审查存证 */
    function withdrawReviewEvidence(string memory udri, string memory reviewerBid) public {
        // TODO
        bytes32 rightInnerEid = keccak256(bytes(EVIDENCE_CATEGORY_RIGHT.concat(udri))); //确权存证 内部eid

        string memory reviewCountKey = reviewerBid.concat(":review_count="); // 某个审查机构review数目
        uint32 current_number = _getCommEvidenceIndefiniteUint32(rightInnerEid, reviewCountKey);
        for (uint32 i = 0; i < current_number; i++){
            string memory key = reviewerBid.concat(":review:index:").concat(i.toString());
            bytes32 innerEid = _getCommEvidenceIndefiniteBytes32(rightInnerEid, key);
            _setCommEvidenceIndefiniteString(innerEid, "status", EVIDENCE_STATUS_DISABLED);
        }
    }

    /* 5.3  查询审查存证信息 */
    /* 5.3.1 查询审查存证数量 */
    function getReviewCount(string calldata udri) public view returns (uint256,uint256) {
        // TODO
    }

    /* 5.3.2 查询审查存证信息 */
    function getVerifyDAEvidence(string calldata udri) public view returns (string memory,string memory,string[] memory)  {
        // TODO
    }

}
