package com.payment.mapper;

import java.util.List;

import com.payment.domain.Bill;
import com.payment.domain.Customer;
import com.payment.domain.ItemDetails;
import com.payment.domain.ItemPriceDetails;
import com.payment.dto.BillDTO;
import com.payment.dto.CustomerDTO;

public interface PaymentMapper {

  /**
   * Maps given {@link Bill} to {@link BillDTO}
   * 
   * @param bill
   *          {@link Bill} object
   * @return {@link BillDTO} object
   */
  BillDTO mapBillToDto(Bill bill);

  /**
   * Maps given list of {@link Bill} to {@link BillDTO}
   * 
   * @param bills
   *          list of {@link Bill} object
   * @return List of {@link BillDTO} object
   */
  List<BillDTO> mapBillToDto(List<Bill> bills);

  /**
   * Maps given itemPriceDeatils into DB format.<br>
   * Finds items and companies if present in DB or else creates new ones.
   * 
   * @param itemPriceDeatils
   *          {@link ItemDetails} to be mapped.
   * @param price
   *          price of item.
   * @param isForBill
   *          true, if you are mapping to save bill. <br>
   *          false, if you are mapping for update/adding new item.
   */
  void findAndMapItemPricedetails(ItemPriceDetails itemPriceDetails, Integer price,
      boolean isForBill);

  /**
   * Converts and prints given object as JSON string.
   * 
   * @param object
   *          object which is to be converted as JSON.
   */
  void writeObjectAsJson(Object object);
  
  /**
   * Converts {@link Customer} into {@link CustomerDTO}
   * @param customer {@link Customer}
   * @param includeBills true, bills will be mapped.
   * @return {@link CustomerDTO}
   */
  CustomerDTO mapCustomer(Customer customer, boolean includeBills);
}
