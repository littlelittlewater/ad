package ly.message;

import ly.domain.Mission;

/*完成的message*/
public class FinishMessage extends Message {

  @Override
  public void cacl(Mission selected) {
    logger.info("finisiMessage is accpte" + selected);

    selected.changeFinfishNumber(number);
    selected.changePublishNumber(-number);
  }

  public FinishMessage(Long missionID, Long number) {
    super(missionID,number);
  }

  public FinishMessage(Message message) {
    super(message.missionID,message.number);
  }

  public FinishMessage() {
  }
}