package com.payment.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Using for socket updates.
 *
 */
public class ItemDTO implements Serializable {

  private static final long serialVersionUID = -6007593456939701503L;

  private Long itemId;

  private String itemName;

  private List<ItemDetailsDTO> itemDetails;

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public List<ItemDetailsDTO> getItemDetails() {
    return itemDetails;
  }

  public void setItemDetails(List<ItemDetailsDTO> itemDetails) {
    this.itemDetails = itemDetails;
  }

}
