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

  @Override
  public String getName() {
    return "finish";
  }

  public FinishMessage(Message message) {
    super(message);
  }

  public FinishMessage() {
  }
}