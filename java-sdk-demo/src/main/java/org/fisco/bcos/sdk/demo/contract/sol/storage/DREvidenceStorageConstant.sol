// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

library DREvidenceStorageConstant {
    // Admin 参数
    string public constant ADMIN_PARAM_CONTRACT_ACCESS = "control_enabled=";

    // 用户状态
    uint32 public constant USER_STATUS_ACTIVE = 100;
    uint32 public constant USER_STATUS_DISABLED = 500;

    string public constant USER_ROLE_PREFIX = "user_role:";

    // 用户角色
    string public constant USER_ROLE_DATA_HOLDER = "data_holder";
    string public constant USER_ROLE_REVIEWER = "reviewer";
    string public constant USER_ROLE_REGISTRY = "registry";
    string public constant USER_ROLE_ELSE = "platform";

    // 用户数据
    string public constant USER_REVIEW_COUNT = "user:review:eid:count"; // 该KEY用于查询用户审核存证数目

    // 存证类型名, 确权存证、审核存证、登记存证 等
    string public constant EVIDENCE_CATEGORY_RIGHT = "right";
    string public constant EVIDENCE_CATEGORY_REVIEW = "review";

    // 存证状态
    string public constant EVIDENCE_STATUS_ACTIVE = "active";
    string public constant EVIDENCE_STATUS_DISABLED = "disabled";

    string public constant EVIDENCE_CONTRACT_VERSION_V1 = "V1";

    string public constant EVIDENCE_UDRI = "udri";
    string public constant EVIDENCE_BID = "bid";

    // 不同类型存证eid的前缀
    string public constant EVIDENCE_ID_PREFIX_NEW = "eid:new:";// 确权存证 eid 前缀
    string public constant EVIDENCE_ID_PREFIX_REVIEW = "eid:review:";// 审核存证 eid 前缀

    string public constant EVIDENCE_DATA_REVIEW_COUNT = "review_count="; // 某个确权存证数据，所有审核数据总数
    string public constant EVIDENCE_DATA_USER_REVIEW_COUNT = ":review_count="; // 某个机构对某个确权存证数据，上传的审核次数 `${reviewer_bid}:review_count=`
    string public constant EVIDENCE_DATA_USER_REVIEW_INDEX = ":review:index:"; // 该KEY用于查询某个机构对某个确权存证数据第N个审核存证， `${reviewer_bid}:review:index:${index}`
    string public constant EVIDENCE_DATA_REVIEW_INDEX = "review:index:"; // 该KEY用于查询某个确权存证数据第N个审核存证， `review:index:${index}`

    // 数据权限前缀，用于构建一个key 映射 某个用户对数据某个操作权限
    string public constant EVIDENCE_DATA_RIGHT_PREFIX = "data_right:";// 数据权限前缀，`data_right:${bid}${right_name}`

    // 用于用户数据
    string public constant EVIDENCE_USER_ = "e:index:"; // 该KEY用于查询用户第N个存证 `e:index:${index}`

    // 用于公共数据
    string public constant EVIDENCE_RIGHT_EID_WITH_INDEX = "e_right:index:"; // 该KEY用于查询用户第N个确权存证 `e_right:index:${index}`
    string public constant EVIDENCE_REVIEW_EID_WITH_INDEX = "e_review:index:"; // 该KEY用于查询用户第N个审核存证 `e_review:index:${index}`
    string public constant EVIDENCE_RIGHT_VARIABLE_DATA_KEY = "e_right:variable_data";// 该KEY勇于查询 确权存证中 可变数据 允许的字段
    string public constant EVIDENCE_REVIEW_VARIABLE_DATA_KEY = "e_review:variable_data";// 该KEY勇于查询 确权存证中 可变数据 允许的字段

    string public constant EVIDENCE_RIGHT_VARIABLE_DATA_MIDDLE = "variable_data:key=";// `${CATEGORY}:variable_data:key=${key}`

}