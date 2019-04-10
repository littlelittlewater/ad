package com.timeyang.user;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class User {
  private long id;
  private String username;
  private String password;
  private Long deptId;
  private String email;
  private String mobile;
  private String status;
  private Timestamp crateTime;
  private Timestamp modifyTime;
  private Timestamp lastLoginTime;
  private String ssex;
  private String theme;
  private String avatar;
  private String description;
  private String bilibiliType;
  private String acfunType;
  private String youkuType;
  private String aiqiyiType;
  private String souhuType;
  private String savePath;

  @Id
  @Column(name = "ID")
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Basic
  @Column(name = "USERNAME")
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Basic
  @Column(name = "PASSWORD")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Basic
  @Column(name = "DEPT_ID")
  public Long getDeptId() {
    return deptId;
  }

  public void setDeptId(Long deptId) {
    this.deptId = deptId;
  }

  @Basic
  @Column(name = "EMAIL")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Basic
  @Column(name = "MOBILE")
  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  @Basic
  @Column(name = "STATUS")
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Basic
  @Column(name = "CRATE_TIME")
  public Timestamp getCrateTime() {
    return crateTime;
  }

  public void setCrateTime(Timestamp crateTime) {
    this.crateTime = crateTime;
  }

  @Basic
  @Column(name = "MODIFY_TIME")
  public Timestamp getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Timestamp modifyTime) {
    this.modifyTime = modifyTime;
  }

  @Basic
  @Column(name = "LAST_LOGIN_TIME")
  public Timestamp getLastLoginTime() {
    return lastLoginTime;
  }

  public void setLastLoginTime(Timestamp lastLoginTime) {
    this.lastLoginTime = lastLoginTime;
  }

  @Basic
  @Column(name = "SSEX")
  public String getSsex() {
    return ssex;
  }

  public void setSsex(String ssex) {
    this.ssex = ssex;
  }

  @Basic
  @Column(name = "THEME")
  public String getTheme() {
    return theme;
  }

  public void setTheme(String theme) {
    this.theme = theme;
  }

  @Basic
  @Column(name = "AVATAR")
  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  @Basic
  @Column(name = "DESCRIPTION")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Basic
  @Column(name = "bilibiliType")
  public String getBilibiliType() {
    return bilibiliType;
  }

  public void setBilibiliType(String bilibiliType) {
    this.bilibiliType = bilibiliType;
  }

  @Basic
  @Column(name = "acfunType")
  public String getAcfunType() {
    return acfunType;
  }

  public void setAcfunType(String acfunType) {
    this.acfunType = acfunType;
  }

  @Basic
  @Column(name = "youkuType")
  public String getYoukuType() {
    return youkuType;
  }

  public void setYoukuType(String youkuType) {
    this.youkuType = youkuType;
  }

  @Basic
  @Column(name = "aiqiyiType")
  public String getAiqiyiType() {
    return aiqiyiType;
  }

  public void setAiqiyiType(String aiqiyiType) {
    this.aiqiyiType = aiqiyiType;
  }

  @Basic
  @Column(name = "souhuType")
  public String getSouhuType() {
    return souhuType;
  }

  public void setSouhuType(String souhuType) {
    this.souhuType = souhuType;
  }

  @Basic
  @Column(name = "savePath")
  public String getSavePath() {
    return savePath;
  }

  public void setSavePath(String savePath) {
    this.savePath = savePath;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return id == user.id &&
        Objects.equals(username, user.username) &&
        Objects.equals(password, user.password) &&
        Objects.equals(deptId, user.deptId) &&
        Objects.equals(email, user.email) &&
        Objects.equals(mobile, user.mobile) &&
        Objects.equals(status, user.status) &&
        Objects.equals(crateTime, user.crateTime) &&
        Objects.equals(modifyTime, user.modifyTime) &&
        Objects.equals(lastLoginTime, user.lastLoginTime) &&
        Objects.equals(ssex, user.ssex) &&
        Objects.equals(theme, user.theme) &&
        Objects.equals(avatar, user.avatar) &&
        Objects.equals(description, user.description) &&
        Objects.equals(bilibiliType, user.bilibiliType) &&
        Objects.equals(acfunType, user.acfunType) &&
        Objects.equals(youkuType, user.youkuType) &&
        Objects.equals(aiqiyiType, user.aiqiyiType) &&
        Objects.equals(souhuType, user.souhuType) &&
        Objects.equals(savePath, user.savePath);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, password, deptId, email, mobile, status, crateTime, modifyTime, lastLoginTime, ssex, theme, avatar, description, bilibiliType, acfunType, youkuType, aiqiyiType, souhuType, savePath);
  }
}
