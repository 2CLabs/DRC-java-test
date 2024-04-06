// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./utils/DREvidenceString.sol";
import "./utils/DREvidenceMap.sol";
import "./DRAccessController.sol";
import "./storage/DREvidenceStorage.sol";
import "./storage/DREvidenceStorageLib.sol";
import "./storage/DREvidenceStorageDefine.sol";
import "./storage/DREvidenceStorageConstant.sol";
import "@openzeppelin/contracts/utils/Strings.sol";
import "@openzeppelin/contracts-upgradeable/proxy/utils/Initializable.sol";
import "@openzeppelin/contracts/proxy/Proxy.sol";

contract DREvidenceReviewController is Initializable, DRAccessController, DREvidenceStorage {

    using DREvidenceMap for DREvidenceMap.DRMappingString;
    using DREvidenceMap for DREvidenceMap.DRMappingUint32;
    using DREvidenceMap for DREvidenceMap.DRMappingStringArray;
    using DREvidenceMap for DREvidenceMap.DRMappingBytes32;
    using Strings for uint32;
    using DREvidenceString for string;
    using DREvidenceString for bytes32;
    using DREvidenceString for uint256;
    // using DREvidenceString for uint32;

    using DREvidenceStorageLib for DREvidenceStorageDefine.DREStorage;



    /******************************************** 内部接口 **************************************************/
    function _hasUserManageRole() internal view returns(bool) {
        (bool isExist, string memory bid) = dataStorage._getUserBidByAccount(msg.sender);
        require(isExist == true, "Sender is not registered.");
        return hasUserManageRole(bid);
    }

    function _isDataRightEvidenceOwner(string memory udri) internal view returns(bool) {
        bytes32 innerEid = keccak256(
            bytes(DREvidenceStorageConstant.EVIDENCE_CATEGORY_RIGHT.concat(udri))
        );
        (bool isExist, string memory bid) = dataStorage._getUserBidByAccount(msg.sender);
        require(isExist == true, "Sender is not registered.");

        DREvidenceStorageDefine.CommEvidence storage evidence = dataStorage._getCommEvidence(innerEid);
        require(evidence.timestamp != 0, "evidence is not exist.");

        string memory bidInEvidence = dataStorage._getCommEvidenceIndefiniteString(innerEid, "bid");

        return bid.equal(bidInEvidence);
    }

    // 检查sender是 存证 owner
    function _requireDataRightEvidenceOwnerByUrid(string memory udri) internal view{
        require(_isDataRightEvidenceOwner(udri) == true, "Sender is not evidence owner.");
    }

    // 检查sender是否是某个角色
    function _checkUserRole(string memory role) internal view{
        (bool isExist, string memory bid) = dataStorage._getUserBidByAccount(msg.sender);
        require(isExist == true, "Sender is not registered.");
        DREvidenceStorageDefine.UserInfoV1 storage user = dataStorage._getUseStoragerByBid(bid);
        require((user.indefiniteString.get(DREvidenceStorageConstant.USER_ROLE_PREFIX.concat(role).hash()).equal("exist")) == true, "User without corresponding role permissions.");
    }

    /******************************************** 审查存证 **************************************************/
    /* 4.2 审查存证 */
    /* 4.2.1 新增审查存证 */
    function addReviewEvidence(string memory udri, string memory reviewerBid, string[] memory reviewDataHash, string[] memory metaData, string[] memory variableData) public {
        _checkUserRole(DREvidenceStorageConstant.USER_ROLE_REVIEWER);
        dataStorage.addReviewEvidence(udri, reviewerBid, reviewDataHash, metaData, variableData);
        // (, string memory outerEid) = DREvidenceStorageLib.genEidViaUrdi(udri, _chainName, DREvidenceStorageConstant.EVIDENCE_CATEGORY_REVIEW);
        uint32 count = dataStorage.getEvidenceReviewCount(udri);
        // _emitNewRightEvidence(outerEid);
        _emitNewReviewEvidence(udri, count - 1);
    }

    /* 4.2.2 撤回审查存证 */
    function withdrawReviewEvidence(string memory udri, string memory reviewerBid) public {
        _checkUserRole(DREvidenceStorageConstant.USER_ROLE_REVIEWER);
        dataStorage.withdrawReviewEvidence(udri, reviewerBid);
    }

    /* 5.3  查询审查存证信息 */
    /* 5.3.1 查询审查存证数量 */
    function getReviewCount(string calldata udri) public view returns (uint256 reviewCount) {
        // TODO: 修改文档那边，方法定义不一致
        return dataStorage.getReviewCount(udri);
    }

    /* 5.3.2 查询审查存证信息 */
    function getVerifyEvidence(string calldata udri, uint32 index) public view returns (bool isWithdraw, string memory reviewerBid, string[] memory metaData, string[] memory variableData)  {
        // TODO: 添加了isWithdraw 需要在文档中也修改
        return dataStorage.getVerifyEvidence(udri, index);
    }

    // 获取某个审核机构对某个数据存证的 审核次数
    function getReviewCountOfReviewer(string calldata udri, string calldata reviewerBid) public view returns (uint256 count) {
        // TODO: 修改文档那边，方法定义不一致
        return dataStorage.getReviewCountOfReviewer(udri, reviewerBid);
    }

    // 获取某个审核机构对某个数据存证的 某次审核信息
    function getVerifyEvidenceOfReviewer(string calldata udri, string calldata reviewerBid, uint32 index) public view returns (bool isWithdraw, string[] memory metaData, string[] memory variableData) {
        return dataStorage.getVerifyEvidenceOfReviewer(udri, reviewerBid, index);
    }
}
