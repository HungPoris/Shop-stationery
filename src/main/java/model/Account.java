package model;

public class Account {

    private String accountId;
    private String username;
    private String email;
    private String password;
    private String roleId;
    private String registrationDate;
    private String lv2password;
    private int status;

    public Account() {
    }

    // Make sure the constructor is in this exact order
    public Account(String accountId, String username, String email, String password, String roleId, String registrationDate, int status) {
        this.accountId = accountId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
        this.registrationDate = registrationDate;
        this.status = status;
    }

    // Constructor không có roleId và status, registrationDate (update)
    public Account(String accountId, String username, String email, String password, String roleId) {
        this.accountId = accountId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }

    // Getter and Setter
    public String getLv2password() {
        return lv2password;
    }

    public void setLv2password(String lv2password) {
        this.lv2password = lv2password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

}
