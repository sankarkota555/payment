package com.payment.mapper;

import java.util.List;

import com.payment.domain.Bill;
import com.payment.dto.BillDTO;

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

}
