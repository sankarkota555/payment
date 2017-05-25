package com.payment.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.payment.domain.Bill;
import com.payment.domain.SoldItem;
import com.payment.dto.BillDTO;
import com.payment.dto.BillItemDTO;

@Component
public class PaymentMapperImpl implements PaymentMapper {

  @Override
  public BillDTO mapBillToDto(Bill bill) {
    return mapBillToBillDto(bill);
  }

  @Override
  public List<BillDTO> mapBillToDto(List<Bill> bills) {
    List<BillDTO> billsDtoList = new ArrayList<>();
    for (Bill bill : bills) {
      billsDtoList.add(mapBillToBillDto(bill));
    }
    return billsDtoList;
  }

  private BillDTO mapBillToBillDto(Bill bill) {
    BillDTO billDto;
    billDto = new BillDTO();
    billDto.setBillId(bill.getBillId());
    billDto.setGeneratedDate(bill.getGeneratedDate());
    billDto.setCustomer(bill.getCustomer());
    billDto.setTotalAmount(bill.getNetAmount());
    BillItemDTO billItemDto;
    List<BillItemDTO> billItemsList = new ArrayList<>();
    String capacity = null;
    for (SoldItem soldItem : bill.getSoldItems()) {
      billItemDto = new BillItemDTO();

      billItemDto.setCapacity(soldItem.getItemPriceDeatils().getCapacity());
      billItemDto.setCompanyId(soldItem.getItemPriceDeatils().getItemDetails().getItemCompany().getCompanyId());
      billItemDto.setCompanyName(soldItem.getItemPriceDeatils().getItemDetails().getItemCompany().getCompanyName());
      billItemDto.setItemId(soldItem.getItemPriceDeatils().getItemDetails().getItem().getItemId());
      billItemDto.setItemName(soldItem.getItemPriceDeatils().getItemDetails().getItem().getItemName());
      billItemDto.setPrice(soldItem.getSoldPrice());
      billItemDto.setQuantity(soldItem.getQuantity());
      capacity = (soldItem.getItemPriceDeatils().getCapacity()!= null)? soldItem.getItemPriceDeatils().getCapacity(): "-";
      billItemDto.setCapacity(capacity);
      
      billItemsList.add(billItemDto);

    }
    billDto.setBillItems(billItemsList);
    return billDto;
  }

}
