package ly.domain;

import java.util.List;

public class Menu {
  String id;
  String state;
  String checked;
  List<Menu> children;
  String parentId;
  boolean hasParent;
  boolean hasChildren;

  public Menu(String id, String state, String checked, String parentId, boolean hasParent, boolean hasChildren) {
    this.id = id;
    this.state = state;
    this.checked = checked;
    this.parentId = parentId;
    this.hasParent = hasParent;
    this.hasChildren = hasChildren;
  }
}
