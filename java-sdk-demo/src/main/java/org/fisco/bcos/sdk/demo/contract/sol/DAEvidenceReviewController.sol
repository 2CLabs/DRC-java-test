// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "./utils/DAEvidenceString.sol";
import "./utils/DAEvidenceMap.sol";
import "./DAAccessController.sol";
import "./storage/DAEvidenceStorage.sol";
import "./storage/DAEvidenceStorageLib.sol";
import "./storage/DAEvidenceStorageDefine.sol";
import "./storage/DAEvidenceStorageConstant.sol";
import "@openzeppelin/contracts/utils/Strings.sol";
import "@openzeppelin/contracts-upgradeable/proxy/utils/Initializable.sol";
import "@openzeppelin/contracts/proxy/Proxy.sol";

contract DAEvidenceReviewController is Initializable, DAAccessController, DAEvidenceStorage {

    using DAEvidenceMap for DAEvidenceMap.DAMappingString;
    using DAEvidenceMap for DAEvidenceMap.DAMappingUint32;
    using DAEvidenceMap for DAEvidenceMap.DAMappingStringArray;
    using DAEvidenceMap for DAEvidenceMap.DAMappingBytes32;
    using Strings for uint32;
    using DAEvidenceString for string;
    using DAEvidenceString for bytes32;
    using DAEvidenceString for uint256;
    // using DAEvidenceString for uint32;

    using DAEvidenceStorageLib for DAEvidenceStorageDefine.DAEStorage;



    /******************************************** 内部接口 **************************************************/
    function _hasDAUserManageRole() internal view returns(bool) {
        (bool isExist, string memory bid) = dataStorage._getUserBidByAccount(msg.sender);
        require(isExist == true, "Sender is not registered.");
        return hasDAUserManageRole(bid);
    }

    function _isDataRightEvidenceOwner(string memory udri) internal view returns(bool) {
        bytes32 innerEid = keccak256(
            bytes(DAEvidenceStorageConstant.EVIDENCE_CATEGORY_RIGHT.concat(udri))
        );
        (bool isExist, string memory bid) = dataStorage._getUserBidByAccount(msg.sender);
        require(isExist == true, "Sender is not registered.");

        DAEvidenceStorageDefine.CommEvidence storage evidence = dataStorage._getCommEvidence(innerEid);
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
        DAEvidenceStorageDefine.UserInfoV1 storage user = dataStorage._getUseStoragerByBid(bid);
        require((user.indefiniteString.get(DAEvidenceStorageConstant.USER_ROLE_PREFIX.concat(role).hash()).equal("exist")) == true, "User without corresponding role permissions.");
    }

    /******************************************** 审查存证 **************************************************/
    /* 4.2 审查存证 */
    /* 4.2.1 新增审查存证 */
    function addReviewEvidence(string memory udri, string memory reviewerBid, string[] memory reviewDataHash, string[] memory metaData, string[] memory variableData) public {
        _checkUserRole(DAEvidenceStorageConstant.USER_ROLE_REVIEWER);
        dataStorage.addReviewEvidence(udri, reviewerBid, reviewDataHash, metaData, variableData);
        // (, string memory outerEid) = DAEvidenceStorageLib.genEidViaUrdi(udri, _chainName, DAEvidenceStorageConstant.EVIDENCE_CATEGORY_REVIEW);
        uint32 count = dataStorage.getEvidenceReviewCount(udri);
        // _emitNewRightEvidence(outerEid);
        _emitNewReviewEvidence(udri, count - 1);
    }

    /* 4.2.2 撤回审查存证 */
    function withdrawReviewEvidence(string memory udri, string memory reviewerBid) public {
        _checkUserRole(DAEvidenceStorageConstant.USER_ROLE_REVIEWER);
        dataStorage.withdrawReviewEvidence(udri, reviewerBid);
    }

    /* 5.3  查询审查存证信息 */
    /* 5.3.1 查询审查存证数量 */
    function getReviewCount(string calldata udri) public view returns (uint256 reviewCount) {
        // TODO: 修改文档那边，方法定义不一致
        return dataStorage.getReviewCount(udri);
    }

    /* 5.3.2 查询审查存证信息 */
    function getVerifyDAEvidence(string calldata udri, uint32 index) public view returns (bool isWithdraw, string memory reviewerBid, string[] memory metaData, string[] memory variableData)  {
        // TODO: 添加了isWithdraw 需要在文档中也修改
        return dataStorage.getVerifyDAEvidence(udri, index);
    }

    // 获取某个审核机构对某个数据存证的 审核次数
    function getReviewCountOfReviewer(string calldata udri, string calldata reviewerBid) public view returns (uint256 count) {
        // TODO: 修改文档那边，方法定义不一致
        return dataStorage.getReviewCountOfReviewer(udri, reviewerBid);
    }

    // 获取某个审核机构对某个数据存证的 某次审核信息
    function getVerifyDAEvidenceOfReviewer(string calldata udri, string calldata reviewerBid, uint32 index) public view returns (bool isWithdraw, string[] memory metaData, string[] memory variableData) {
        return dataStorage.getVerifyDAEvidenceOfReviewer(udri, reviewerBid, index);
    }

    // function _implementation() internal view virtual override returns (address) {
    //     address nextLogicContract = dataStorage.LogicAddress["next_logic_of_review_evidence"];
    //     require(nextLogicContract != address(0), "Unknown function.");
    //     return nextLogicContract;
    // }
}
