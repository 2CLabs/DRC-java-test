// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

import "@openzeppelin/contracts-upgradeable/access/AccessControlUpgradeable.sol";

contract DAAccessController is AccessControlUpgradeable {
    bytes32 public constant USERMANAGE_ROLE = keccak256("USERMANAGE_ROLE");

    mapping(string => address) private _usermanagers; // bid = address

    function grantUserManagePermission(string memory bid, address user) public virtual onlyRole(DEFAULT_ADMIN_ROLE) {
        if(_usermanagers[bid]==address(0)) {
          _grantRole(USERMANAGE_ROLE, user);
          _usermanagers[bid] = user;
        }
    }

    function revokeUserManagePermission(string memory bid) public virtual onlyRole(DEFAULT_ADMIN_ROLE) {
        if(_usermanagers[bid]!=address(0)) {
          _revokeRole(USERMANAGE_ROLE, _usermanagers[bid]);
          _usermanagers[bid] = address(0);
        }
    }

    function hasDAUserManageRole(string memory bid) public view virtual returns (bool) {
        if(_usermanagers[bid]!=address(0))
          return hasRole(USERMANAGE_ROLE, _usermanagers[bid]);
        else
          return false;
    }

    //empty reserved space for future to add new variables
    uint256[49] private __gap;
}