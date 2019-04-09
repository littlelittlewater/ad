package ly.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Mission implements Serializable {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;

  private String name;

  private String remark;

  private String platInfo;

  private String type;

  private Long finfishNumber;

  private Long publishNumber;

  private Long exceptNumber;

  private Integer cron;

  private Long publishPerTrigger;

  private Date lastTriggerTime;

  private boolean cancel;


  public Mission() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getPlatInfo() {
    return platInfo;
  }

  public void setPlatInfo(String platInfo) {
    this.platInfo = platInfo;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Long getFinfishNumber() {
    return finfishNumber;
  }

  public void setFinfishNumber(Long finfishNumber) {
    this.finfishNumber = finfishNumber;
  }

  public Long getPublishNumber() {
    return publishNumber;
  }

  public void setPublishNumber(Long publishNumber) {
    this.publishNumber = publishNumber;
  }

  public Long getExceptNumber() {
    return exceptNumber;
  }

  public void setExceptNumber(Long exceptNumber) {
    this.exceptNumber = exceptNumber;
  }

  public Integer getCron() {
    return cron;
  }

  public void setCron(Integer cron) {
    this.cron = cron;
  }

  public boolean isCancel() {
    return cancel;
  }

  public void setCancel(boolean cancel) {
    this.cancel = cancel;
  }

  public Long getPublishPerTrigger() {
    return publishPerTrigger;
  }

  public void setPublishPerTrigger(Long publishPerTrigger) {
    this.publishPerTrigger = publishPerTrigger;
  }

  public Date getLastTriggerTime() {
    return lastTriggerTime;
  }

  public void setLastTriggerTime(Date lastTriggerTime) {
    this.lastTriggerTime = lastTriggerTime;
  }

  public void changeFinfishNumber(Long number) {
    this.finfishNumber += number;
  }

  public void changePublishNumber(Long number) {
    this.publishNumber += number;
  }

  
  public boolean canPublish() {
    return (!isCancel()) && restCheck() && timeCheck();
  }

  private boolean restCheck() {
    return exceptNumber - finfishNumber - publishNumber > 0;
  }

  private boolean timeCheck() {
    return new Date().getTime() - lastTriggerTime.getTime() > cron * 60 *1000;
}
  
  public Long getRealPublishNumber(){
    return Math.min(exceptNumber - finfishNumber - publishNumber ,publishPerTrigger);
  }
}
