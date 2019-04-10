package ly.message;

import ly.domain.Mission;

/*完成的message*/
public class ErrorMessage extends Message {

  @Override
  public void cacl(Mission selected) {
    logger.info("errorMessage is accepted" + selected);
    selected.changeErrorNumber(number);
    selected.changePublishNumber(-number);
  }


  @Override
  public String getName() {
    return "error";
  }

  public ErrorMessage(Message message) {
    super(message);
  }

  public ErrorMessage() {
  }
}