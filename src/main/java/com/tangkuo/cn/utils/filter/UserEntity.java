package com.tangkuo.cn.utils.filter;

import javax.persistence.*;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.sql.Timestamp;

/**
 * 
* @ClassName: UserEntity
* @Description: (数据库对应t_user表关系映射)
* @author tangkuo
* @date 2017年5月2日 下午2:31:26
*
 */
@Entity
@Table(name = "t_user", schema = "", catalog = "dbNameManager")
public class UserEntity implements java.io.Serializable{

    private long id;
    private String loginName;
    private String password;
    private String userName;
    private String mobile;
    private String email;
    private Timestamp createTime;
    private Timestamp loginTime;
    private Timestamp lastLoginTime;
    private int count;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TU_ID", nullable = false, insertable = true, updatable = true)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "LOGIN_NAME", nullable = true, insertable = true, updatable = true, length = 64)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Basic
    @Column(name = "PASSWORD", nullable = true, insertable = true, updatable = true, length = 64)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "USER_NAME", nullable = true, insertable = true, updatable = true, length = 64)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "MOBILE", nullable = true, insertable = true, updatable = true, length = 64)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "EMAIL", nullable = true, insertable = true, updatable = true, length = 64)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "CREATE_TIME", nullable = true, insertable = true, updatable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "LOGIN_TIME", nullable = true, insertable = true, updatable = true)
    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    @Basic
    @Column(name = "LAST_LOGIN_TIME", nullable = true, insertable = true, updatable = true)
    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Basic
    @Column(name = "COUNT", nullable = true, insertable = true, updatable = true)
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}