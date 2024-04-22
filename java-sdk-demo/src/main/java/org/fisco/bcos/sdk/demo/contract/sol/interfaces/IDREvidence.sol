// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

interface IDREvidence {
    /******************************************** Admin **************************************************/
    /*
     * 描述：设置链上数据存证的参数 - 名字
     * 参数：
     *     - name：链上数据存证的名称
     */
    function setChainName(string memory name) external;

    /*
     * 描述：设置参数 - 文本最大长度
     * 参数：
     *     - len：文本最大长度
     */
    function setTextMaxLen(uint32 len) external;

    /*
     * 描述：设置参数 - 字符串数组最大长度
     * 参数：
     *     - len：字符串数组最大长度
     */
    function setstrArrayMaxLen(uint32 len) external;

    /*
     * 描述：设置合约可访问
     */
    function enableAccessControl() external;

    /*
    描述：设置合约不可访问
    */
    function disableAccessControl() external;

    /*
     * 描述：获取参数 - 名字
     * 返回值：
     *     - name：链上数据存证的名称
     */
    function getChainName() external view returns (string memory name);

    /*
     * 描述：获取参数 - 文本最大长度
     * 返回值：
     *     - len: 文本最大长度
     */
    function getTextMaxLen() external view returns (uint32 len);

    /*
     * 描述：获取参数 - 字符串数组最大长度
     * 返回值：
     *     - len: 字符串数组最大长度
     */
    function getstrArrayMaxLen() external view returns (uint32 len);

    /*
     * 描述：获取参数 - 是否可访问
     * 返回值：
     *     - status: 1: 可访问; 0: 不可访问
     */
    function getAccessControl() external view returns (uint32 status);

    /*
     * 描述：获取当前某种存证支持的 variableData 字段
     * 参数：
     *     - category：存证类型
     * 返回值：
     *     - fileds：当前某种存证支持的 variableData 字段
     */
    function getSupportVariableDataFields(
        string memory category
    ) external view returns (string[] memory fileds);

    /*
     * 描述：设置某种存证 variableData 支持的字段，实现逻辑是：先将之前的所有fields都清除，再重新设置，
     *      原则上是只能在原有基础上新加字段，减少字段会导致之前保存的字段不能获取到。
     * 参数：
     *     - category：存证类型
     *     - fields：当前某种存证支持的 variableData 字段
     */
    function setDataRightSupportVariableDataFields(
        string memory category,
        string[] memory fields
    ) external;

    /*
     * 描述：设置数据权限分类 （目前有：1.数据资源持有权; 2.数据加工使用权; 3.数据产品经营权。）
     * 参数：
     *     - fields：数据权限类型 （比如["hold", "process", "operate"]）
     */
    function setDataRightCategory(string[] memory fields) external;

    /*
     * 描述：获取数据权限分类
     * 返回值：
     *     - fields：数据权限分类
     */
    function getDataRightCategory()
        external
        view
        returns (string[] memory fields);

    /******************************************** 用户 **************************************************/
    function queryUserRole() external view returns (string[] memory);

    /* 3.1 配置管理权限 */
    /* 3.1.1 配置管理权限 */
    /*
     * 描述：
     *     - 功能定位
     *         由运营方(合约所有者)在链上给某个机构帐号地址赋予用户管理权限。
     *     - 调用限制
     *         该接口只有运营方(合约所有者)能调用。
     * 参数：
     *     - bid：链上身份ID
     *     - account：机构帐号地址
     */
    function grantUserManagePermission(
        string memory bid,
        address account
    ) external;

    /* 3.1.2 回收管理权限 */
    /*
     * 描述：
     *     - 功能定位
     *         由运营方(合约所有者)在链上收回某个机构帐号地址的用户管理权限。
     *     - 调用限制
     *         该接口只有运营方(合约所有者)能调用。
     * 参数：
     *     - bid：链上身份ID
     */
    function revokeUserManagePermission(string memory bid) external;

    /*
     * 描述：检查用户是否有管理权限
     * 参数：
     *     - bid：链上身份ID
     * 返回值：
     *     - status：true or false
     */
    function hasUserManageRole(string memory bid) external view returns (bool);

    /* 3.2 用户角色管理 */
    /* 3.2.1 添加用户 */
    /*
     * 描述：
     *     - 功能定位 由用户管理员在链上添加用户信息，同时可以绑定用户角色。
     * 参数：
     *     - bid：链上身份ID
     *     - usci：统一社会信用编号
     *     - name：用户名称
     *     - account：用户帐号地址
     *     - roles：用户角色
     * 返回值：
     *     无
     */
    function addUser(
        string memory bid,
        string memory usci,
        string memory name,
        address account,
        string[] memory roles
    ) external;

    /* 3.2.2  查询用户 */
    /*
     * 描述：
     *     - 功能定位 根据用户bid查询当前用户信息及其绑定的角色。
     * 参数：
     *     - bid：链上身份ID
     *     - usci：统一社会信用编号
     * 返回值：
     *     - usci：统一社会信用编号
     *     - name：用户名称
     *     - roles：用户角色
     */
    function getUserRoles(
        string memory bid
    )
        external
        view
        returns (string memory usci, string memory name, string[] memory roles);

    /* 3.2.3 添加用户角色 */
    /*
     * 描述：
     *     - 功能定位 为用户添加新的角色。
     * 参数：
     *     - bid：链上身份ID
     *     - usci：统一社会信用编号
     *     - roles：用户角色
     * 返回值：
     *     无
     */
    function grantUserRoles(string memory bid, string[] memory roles) external;

    /* 3.2.4 回收用户角色 */
    /*
     * 描述：
     *     - 功能定位 由用户管理员回收用户的一或多个角色。
     * 参数：
     *     - bid：链上身份ID
     *     - roles：用户角色
     * 返回值：
     *     无
     */
    function revokeUserRoles(string memory bid, string[] memory roles) external;

    /* 4.3 登记存证（二阶段）*/

    /* 5. 查询相关接口 */
    /* 5.1  查询用户数据信息 */
    /* 5.1.1 查询用户数据数量 */
    /*
     * 描述：
     *     - 功能定位 查询某个数据主体名下确权存证数量。
     * 参数：
     *     - bid：链上身份ID
     * 返回值：
     *     - dataCount：用户确权存证数量。
     */
    function getDataCount(
        string calldata bid
    ) external view returns (uint256 dataCount);

    /* 5.1.2  查询用户数据列表 */
    /*
     * 描述：
     *     - 功能定位 查询某个数据主体名下存证数据的列表，返回存证数据相应的udri。
     * 参数:
     *     - bid：链上身份ID
     *     - start：起始索引
     *     - count：查询数量
     * 返回值：
     *     - udriArray: 统一数据资源标识数组
     */
    function getDataList(
        string memory bid,
        uint256 start,
        uint256 count
    ) external view returns (string[] memory udriArray);

    /*
     * 描述：
     *     - 功能定位 查询某个数据主体名下审核存证数量。
     * 参数:
     *     - bid：链上身份ID
     * 返回值：
     *     - dataCount：用户审核存证数量
     */
    // function getUserReviewCount(string calldata bid) external view returns (uint256 dataCount);

    /* 查询用户审核存证列表 */
    /*
     * 描述：
     *     - 功能定位 查询某个数据主体名下审核存证数据的列表，返回审核存证数据相应的udri。
     * 参数:
     *     - bid：链上身份ID
     *     - start：起始索引
     *     - count：查询数量
     * 返回值：
     *     - udriArray: 统一数据资源标识数组
     */
    // function getUserReviewList(string memory bid, uint256 start, uint256 count) external view returns (string[] memory udriArray);

    /******************************************** 审查存证 **************************************************/
    /* 4.2 审查存证 */
    /* 4.2.1 新增审查存证 */
    /*
     * 描述：
     *     - 功能定位 第三方法律服务机构对数据资源或数据产品进行合规性审查，出具审查报告，并将审查结果及审查文件调用该合约接口上链存证。
     *               可以根据需要新增多次审查存证，如面向不同的数据交易机构要求进行多次审查的场景。
     * 参数：
     *     - udri：统一数据资源标识
     *     - reviewerBid：审查机构链上身份ID
     *     - reviewDataHash：审查数据哈希
     *     - metaData：元数据
     *     - variableData：可变数据
     */
    function addReviewEvidence(
        string memory udri,
        string memory reviewerBid,
        string[] memory reviewDataHash,
        string[] memory metaData,
        string[] memory variableData
    ) external;

    /* 4.2.2 撤回审查存证 */
    /*
     * 描述：
     *     - 功能定位 第三方法律服务机构撤回自己的审查存证，如果有多次审查存证记录，一并撤回，不支持对单一审查存证记录撤回。
     * 参数：
     *     - udri：统一数据资源标识
     *     - reviewerBid：审查机构链上身份ID
     */
    function withdrawReviewEvidence(
        string memory udri,
        string memory reviewerBid
    ) external;

    /* 5.3  查询审查存证信息 */
    /* 5.3.1 查询审查存证数量 */
    /*
     * 描述：
     *     - 功能定位 查询审查存证数量。
     * 参数：
     *     - udri：统一数据资源标识
     * 返回值：
     *     - reviewCount：审查存证数量
     */
    function getReviewCount(
        string calldata udri
    ) external view returns (uint256 reviewCount);

    /* 5.3.2 查询审查存证信息 */
    /*
     * 描述：
     *     - 功能定位 根据udri查询该数据资源的审查存证信息。
     * 参数：
     *     - udri：统一数据资源标识
     *     - index：审查存证索引
     * 返回值：
     *     - isWithdraw: 是否已经撤销
     *     - reviewerBid：审查机构链上身份ID
     *     - metaData：元数据
     *     - variableData：可变数据
     */
    function getVerifyEvidence(
        string calldata udri,
        uint32 index
    )
        external
        view
        returns (
            bool isWithdraw,
            string memory reviewerBid,
            string[] memory metaData,
            string[] memory variableData
        );

    /*
     * 描述：
     *     - 功能定位 获取某个审核机构对某个数据存证的 审核次数。
     * 参数：
     *     - udri：统一数据资源标识
     *     - reviewerBid：审查机构链上身份ID
     * 返回值：
     *     - count：审核次数
     */
    function getReviewCountOfReviewer(
        string calldata udri,
        string calldata reviewerBid
    ) external view returns (uint256 count);

    /*
     * 描述：
     *     - 功能定位 获取某个审核机构对某个数据存证的 某次审核信息。
     * 参数：
     *     - udri：统一数据资源标识
     *     - reviewerBid：审查机构链上身份ID
     *     - index：审核存证索引
     * 返回值：
     *     - isWithdraw: 是否已经撤销
     *     - metaData：元数据
     *     - variableData：可变数据
     */
    function getVerifyEvidenceOfReviewer(
        string calldata udri,
        string calldata reviewerBid,
        uint32 index
    )
        external
        view
        returns (
            bool isWithdraw,
            string[] memory metaData,
            string[] memory variableData
        );

    /******************************************** 确权存证**************************************************/
    /*4. 存证 */
    /* 4.1.1 新增确权存证 */
    /*
     * 描述：
     *     - 功能定位 新增确权存证。
     * 参数：
     *     - udri：统一数据资源标识
     *     - bid: 链上身份ID
     *     - dataHash：数据哈希
     *     - dataRight：数据权限
     *     - metaData：元数据
     *     - variableData：可变数据
     */
    function addDataRightEvidence(
        string memory udri,
        string memory bid,
        string[] memory dataHash,
        string[] memory dataRight,
        string[] memory metaData,
        string[] memory variableData
    ) external;

    /* 4.1.2  追加可变数据存证 */
    /*
     * 描述：
     *     - 功能定位 追加存证数据的扩展信息， 追加的扩展信息作为新的记录存在，与原扩展信息同时作为数据的附属数据。
     * 参数：
     *     - udri：统一数据资源标识
     *     - variableData：可变数据
     */
    function appendVariableData(
        string memory udri,
        string[] memory variableData
    ) external;

    /* 4.1.3 撤回确权存证 */
    /*
     * 描述：
     *     - 功能定位 数据的权益主体撤回自己的权益存证。
     * 参数：
     *     - udri：统一数据资源标识
     *     - bid：链上身份ID
     */
    function withdrawDataRightRegister(
        string memory udri,
        /*string memory dataRightOwnerBid, */
        string[] memory dataRight
    ) external;

    /* 4.1.4 新增授权存证 */
    /*
     * 描述：
     *     - 功能定位 拥有数据的“数据资源持有权”用户向其他用户授予该数据资源权利。
     * 参数：
     *     - udri：统一数据资源标识
     *     - bid：链上身份ID
     *     - dataRight：数据权限
     */
    function grantUserDataRight(
        string memory udri,
        string memory bid,
        string[] memory dataRight
    ) external;

    /* 4.1.5 撤回授权存证 */
    /*
     * 描述：
     *     - 功能定位 拥有数据的“数据资源持有权”用户撤回向其他用户授予的该数据资源权利。
     * 参数：
     *     - udri：统一数据资源标识
     *     - bid：链上身份ID
     *     - dataRight：数据权限
     */
    function withdrawUserDataRight(
        string memory udri,
        string memory bid,
        string[] memory dataRight
    ) external;

    /* 5.2 查询确权存证信息 */
    /* 5.2.1 查询存证数据标识 */
    /*
     * 描述：
     *     - 功能定位 根据数据块hash,查询存证数据标识。
     * 参数：
     *     - dataHash：数据哈希
     * 返回值：
     *     - udri：统一数据资源标识
     */
    function getUdriByDatahash(
        string calldata dataHash
    ) external view returns (string memory udri);

    /* 5.2.2 查询确权存证信息 */
    /*
     * 描述：
     *     - 功能定位 根据udri查询该数据资源的确权存证信息。
     * 参数：
     *     - udri：统一数据资源标识
     * 返回值：
     *     - bid：链上身份ID
     *     - dataHashSM：数据Hash
     *     - dataHashSHA：数据Hash
     *     - dataRight：数据权限
     *     - metaData：元数据
     *     - variableData：可变数据
     *     - isWithdraw：是否已经撤销
     */
    function getRegisteredData(
        string memory udri
    )
        external
        view
        returns (
            string memory dataHashSM,
            string memory dataHashSHA,
            string[] memory dataRight,
            string[] memory metaData,
            string[] memory variableData,
            bool isWithdraw
        );

    /* 5.2.3 查询授权信息 */
    /*
     * 描述：
     *     - 功能定位 获取某个用户对某个“数据资源”的权利。
     * 参数：
     *     - udri：统一数据资源标识
     *     - bid：链上身份ID
     * 返回值：
     *     - dataRight：数据权限
     */
    function getUserDataRight(
        string memory udri,
        string memory bid
    ) external view returns (string[] memory dataRight);
}
