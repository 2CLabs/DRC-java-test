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

contract DREvidenceRightController is Initializable, DRAccessController, DREvidenceStorage {

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

    /******************************************** 确权存证**************************************************/
    /*4. 存证 */
    /* 4.1.1 新增确权存证 */
    function addDataRightEvidence(
        string memory udri,
        string memory bid,
        string[] memory dataHash,
        string[] memory dataRight,
        string[] memory metaData,
        string[] memory variableData
    ) public {
        _checkUserRole(DREvidenceStorageConstant.USER_ROLE_DATA_HOLDER);
        dataStorage.addDataRightEvidence(udri, bid, dataHash, dataRight, metaData, variableData);
        // (, string memory outerEid) = DREvidenceStorageLib.genEidViaUrdi(udri, _chainName, DAEvideDREvidenceStorageConstantnceStorageLib.EVIDENCE_CATEGORY_RIGHT);
        // _emitNewEvidence(outerEid);
        _emitNewRightEvidence(udri);
    }

    /* 4.1.2  追加可变数据存证 */
    function appendVariableData(
        string memory udri,
        string[] memory variableData
    ) public {
        _checkUserRole(DREvidenceStorageConstant.USER_ROLE_DATA_HOLDER);
        _requireDataRightEvidenceOwnerByUrid(udri);
        dataStorage.appendVariableData(udri, variableData);
    }

    /* 4.1.3 撤回确权存证 */
    function withdrawDataRightRegister(
        string memory udri,
        /*string memory dataRightOwnerBid, */
        string[] memory dataRight
    ) public {
        // TODO: 接口和文档中不一致，需要确认是否修改成代码这边这种方式。
        _checkUserRole(DREvidenceStorageConstant.USER_ROLE_DATA_HOLDER);
        require(_isDataRightEvidenceOwner(udri)== true || _hasUserManageRole() == true, "Sender neither the owner nor the authority.");
        _requireDataRightEvidenceOwnerByUrid(udri);
        dataStorage.withdrawDataRightRegister(udri, dataRight);
    }

    /* 4.1.4 新增授权存证 */
    function grantUserDataRight(
        string memory udri,
        string memory bid,
        string[] memory dataRight
    ) public {
        _checkUserRole(DREvidenceStorageConstant.USER_ROLE_DATA_HOLDER);
        _requireDataRightEvidenceOwnerByUrid(udri);
        dataStorage.grantUserDataRight(udri, bid, dataRight);
    }

    /* 4.1.5 撤回授权存证 */
    function withdrawUserDataRight(
        string memory udri,
        string memory bid,
        string[] memory dataRight
    ) public {
        _checkUserRole(DREvidenceStorageConstant.USER_ROLE_DATA_HOLDER);
        _requireDataRightEvidenceOwnerByUrid(udri);
        dataStorage.withdrawUserDataRight(udri, bid, dataRight);
    }

    /* 5.2 查询确权存证信息 */
    /* 5.2.1 查询存证数据标识 */
    function getUdriByDatahash(string calldata dataHash)
        public
        view
        returns (string memory udri)
    {
        return dataStorage.getUdriByDatahash(dataHash);
    }

    /* 5.2.2 查询确权存证信息 */
    function getRegisteredData(string memory udri)
        public
        view
        returns (
            string memory dataHashSM,
            string memory dataHashSHA,
            string[] memory dataRight,
            string[] memory metaData,
            string[] memory variableData,
            bool isWithdraw
        )
    {
        return dataStorage.getRegisteredData(udri);
    }

    /* 5.2.3 查询授权信息 */
    function getUserDataRight(string memory udri, string memory bid)
        public
        view
        returns (string[] memory dataRight)
    {
        return dataStorage.getUserDataRight(udri, bid);
    }
}
