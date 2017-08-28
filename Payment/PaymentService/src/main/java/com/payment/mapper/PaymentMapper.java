package com.payment.mapper;

import java.util.List;

import com.payment.domain.Bill;
import com.payment.domain.Customer;
import com.payment.domain.ItemDetails;
import com.payment.domain.ItemPriceDetails;
import com.payment.domain.PaymentSystem;
import com.payment.domain.PaymentSystemUsageDetails;
import com.payment.dto.BillDTO;
import com.payment.dto.CustomerDTO;
import com.payment.dto.ItemDTO;
import com.payment.dto.SystemDTO;
import com.payment.dto.SystemUsageDTO;

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
   * 
   * @param customer
   *          {@link Customer}
   * @param includeBills
   *          true, bills will be mapped.
   * @return {@link CustomerDTO}
   */
  CustomerDTO mapCustomer(Customer customer, boolean includeBills);

  /**
   * Converts {@link PaymentSystemUsageDetails} to {@link SystemUsageDTO}
   * 
   * @param systemUsageDetails
   *          {@link PaymentSystemUsageDetails}
   * @param includeSystem
   *          true, if you want to include {@link System} details.
   * @return {@link SystemUsageDTO}
   */
  SystemUsageDTO mapPaymentSystemUsageDetails(PaymentSystemUsageDetails systemUsageDetails,
      boolean includeSystem);

  /**
   * Converts {@link List} of {@link PaymentSystemUsageDetails} to {@link SystemUsageDTO}
   * 
   * @param systemUsageDetails
   *          {@link List} of {@link PaymentSystemUsageDetails}
   * @param includeSystem
   *          true, if you want to include {@link System} details.
   * @return {@link List} of {@link SystemUsageDTO}
   */
  List<SystemUsageDTO> mapPaymentSystemUsageDetails(
      List<PaymentSystemUsageDetails> systemUsageDetails, boolean includeSystem);

  /**
   * Converts {@link PaymentSystem} to {@link SystemDTO}
   * 
   * @param system
   *          {@link System}
   * @param includeUsageDetails
   *          true, if you want to include usage details.
   * @return {@link SystemDTO}
   */
  SystemDTO mapPaymentSystem(PaymentSystem system, boolean includeUsageDetails);

  /**
   * Converts {@link PaymentSystemUsageDetails} to {@link SystemDTO} for UI purpose.
   * 
   * @param details
   *          {@link PaymentSystemUsageDetails}
   * @return {@link SystemDTO}
   */
  SystemDTO convertSystemUsageToSystem(PaymentSystemUsageDetails details);

  /**
   * converts {@link ItemPriceDetails} to {@link ItemDTO} for UI purpose.
   * @param itemPriceDetails {@link ItemPriceDetails}
   * @return {@link ItemDTO}
   */
  ItemDTO convertItemPriceDetailsToItem(ItemPriceDetails itemPriceDetails);
}
