// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

contract DAEvidenceConstants {
    // 存证类型名, 确权存证、审核存证、登记存证 等
    string public EVIDENCE_CATEGORY_RIGHT = "right";
    string public EVIDENCE_CATEGORY_REVIEW = "review";

    // 存证状态
    string public EVIDENCE_STATUS_ACTIVE = "active";
    string public EVIDENCE_STATUS_DISABLED = "disabled";

    string public EVIDENCE_CONTRACT_VERSION_V1 = "V1";

    // 不同类型存证eid的前缀
    string public EVIDENCE_ID_PREFIX_NEW = "eid:new";// 确权存证 eid 前缀
    string public EVIDENCE_ID_PREFIX_REVIEW = "eid:review";// 审核存证 eid 前缀

    // 数据权限前缀，用于构建一个key 映射 某个用户对数据某个操作权限
    string public EVIDENCE_DATA_RIGHT_PREFIX = "data_right:";// 数据权限前缀，`data_right:${bid}${right_name}`

    // 用于公共数据
    string public EVIDENCE_RIGHT_VARIABLE_DATA_KEY = "e_right:variable_data";// 该KEY勇于查询 确权存证中 可变数据 允许的字段
    string public EVIDENCE_REVIEW_VARIABLE_DATA_KEY = "e_review:variable_data";// 该KEY勇于查询 确权存证中 可变数据 允许的字段


    string public EVIDENCE_RIGHT_VARIABLE_DATA_MIDDLE = "variable_data:key=";// `${CATEGORY}:variable_data:key=${key}`
}