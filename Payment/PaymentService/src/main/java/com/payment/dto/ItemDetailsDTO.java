package com.payment.dto;

import java.io.Serializable;
import java.util.List;

import com.payment.domain.ItemCompany;

/**
 * Using for socket updates.
 *
 */
public class ItemDetailsDTO implements Serializable {

  private static final long serialVersionUID = 6724922862882471855L;

  private Long id;

  private ItemCompany itemCompany;

  private List<ItemPriceDetailsDTO> itemPriceDetails;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ItemCompany getItemCompany() {
    return itemCompany;
  }

  public void setItemCompany(ItemCompany itemCompany) {
    this.itemCompany = itemCompany;
  }

  public List<ItemPriceDetailsDTO> getItemPriceDetails() {
    return itemPriceDetails;
  }

  public void setItemPriceDetails(List<ItemPriceDetailsDTO> itemPriceDetails) {
    this.itemPriceDetails = itemPriceDetails;
  }

}
