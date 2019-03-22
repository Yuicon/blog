package model;

import constant.UserStateConstant;

import java.time.LocalDateTime;

/**
 * @author Yuicon
 */
public class Account {

    private int id;

    private String username;

    private String phone;

    private int sex;

    private String password;

    private String email;

    private LocalDateTime createAt;

    private String createAtIP;

    private LocalDateTime lastLoginAt;

    private String lastLoginIP;

    private int loginTimes;

    private int state;

    public String getCreateAtIP() {
        return createAtIP;
    }

    public void setCreateAtIP(String createAtIP) {
        this.createAtIP = createAtIP;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public int getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(int loginTimes) {
        this.loginTimes = loginTimes;
    }

    /**
     * @return 用户状态是否正常
     */
    public boolean isAlright() {
        return state == UserStateConstant.ALRIGHT;
    }

    /**
     * @return 状态说明
     */
    public String getStateInstruction() {
        return UserStateConstant.instruction(state);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}
