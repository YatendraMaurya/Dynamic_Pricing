package com.nearbuy.dynamic.pricing.dynamicpricing.service.model;

import java.util.Arrays;
import java.util.List;

public class AccountServiceModel {
    Rs rs;

    public Rs getRs() {
        return rs;
    }

    public void setRs(Rs rs) {
        this.rs = rs;
    }

    @Override
    public String toString() {
        return "AccountServiceModel{" +
                "rs=" + rs +
                '}';
    }

    public class AccountUser {
        UserDevice[] userDevices;

        public UserDevice[] getUserDevice() {
            return userDevices;
        }

        public void setUserDevice(UserDevice[] userDevice) {
            this.userDevices = userDevice;
        }

        public Role getRole() {
            return role;
        }

        public void setRole(Role role) {
            this.role = role;
        }
        Long id;

        @Override
        public String toString() {
            return "AccountUser{" +
                    "userDevices=" + Arrays.toString(userDevices) +
                    ", id=" + id +
                    ", role=" + role +
                    '}';
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        Role role;
    }


    public class Role {
        @Override
        public String toString() {
            return "Role{" +
                    "roleName='" + roleName + '\'' +
                    '}';
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        String roleName;
    }

    public class Rs {
        List<AccountUser> accountUsers;

        public List<AccountUser> getAccountUsers() {
            return accountUsers;
        }

        public void setAccountUsers(List<AccountUser> accountUsers) {
            this.accountUsers = accountUsers;
        }

        @Override
        public String toString() {
            return "Rs{" +
                    "accountUsers=" + accountUsers +
                    '}';
        }
    }

    public class UserDevice {
        String os;
        String appVersion;

        @Override
        public String toString() {
            return "UserDevice{" +
                    "os='" + os + '\'' +
                    ", appVersion='" + appVersion + '\'' +
                    '}';
        }

        public String getOs() {
            return os;
        }

        public void setOs(String os) {
            this.os = os;
        }

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }
    }
}
