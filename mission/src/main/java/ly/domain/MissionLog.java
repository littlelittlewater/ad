package ly.domain;

import ly.message.ErrorMessage;
import ly.message.Message;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class MissionLog {
  @Id
  public int id;

  public Long messionID;
  public String messageType;
  public Long messageNumber;
  public Date createDate;
  public String uuid;

  public static MissionLog build(Message message) {
    MissionLog missionLog = new MissionLog();
    missionLog.messageType = message.getName();
    missionLog.messionID = message.missionID;
    missionLog.messageNumber = message.number;
    missionLog.createDate = message.createTime;
    missionLog.uuid = message.uuid;
    return missionLog;
  }
}
