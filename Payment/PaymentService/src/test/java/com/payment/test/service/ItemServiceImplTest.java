package com.payment.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.payment.domain.ItemPriceDetails;
import com.payment.mapper.PaymentMapper;
import com.payment.repositories.ItemCompanyRepository;
import com.payment.repositories.ItemPriceDetailsRepositoty;
import com.payment.repositories.ItemRepository;
import com.payment.service.impl.BillingServiceImpl;
import com.payment.service.impl.ItemServiceImpl;

public class ItemServiceImplTest {

  private ItemServiceImpl itemServiceImpl;

  @Mock
  private ItemCompanyRepository itemCompanyRepository;

  @Mock
  private ItemPriceDetailsRepositoty itemPriceDetailsRepositoty;

  @Mock
  private ItemRepository itemRepository;

  @Mock
  private PaymentMapper paymentMapper;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    itemServiceImpl = new ItemServiceImpl();
    itemServiceImpl.setItemCompanyRepository(itemCompanyRepository);
    itemServiceImpl.setItemPriceDetailsRepositoty(itemPriceDetailsRepositoty);
    itemServiceImpl.setItemRepository(itemRepository);
    itemServiceImpl.setPaymentMapper(paymentMapper);

  }

  /**
   * test updating item price details which are in DB.
   */
  @Test
  public void testUpdateItemDetails() {
    Long itemPriceDetailsId = 927321L;

    ItemPriceDetails itemPriceDetails = new ItemPriceDetails();
    itemPriceDetails.setCapacity("Cap");
    itemPriceDetails.setId(itemPriceDetailsId);
    itemPriceDetails.setPrice(700);
    itemPriceDetails.setQuantity(20);

    ItemPriceDetails dummyItemPriceDetails = getDummyItemPriceDetails(itemPriceDetailsId);

    when(itemPriceDetailsRepositoty.findOne(itemPriceDetailsId)).thenReturn(dummyItemPriceDetails);

    boolean result = itemServiceImpl.updateItemDetails(itemPriceDetails);

    assertEquals("Id should not change", dummyItemPriceDetails.getId(), itemPriceDetails.getId());
    assertEquals("Capacity should update", dummyItemPriceDetails.getCapacity(),
        itemPriceDetails.getCapacity());
    assertEquals("Price should update", dummyItemPriceDetails.getPrice(),
        itemPriceDetails.getPrice());
    assertEquals("Quantity should update", dummyItemPriceDetails.getQuantity(),
        itemPriceDetails.getQuantity());
    assertEquals("Should return true", true, result);

    verify(itemPriceDetailsRepositoty).findOne(itemPriceDetailsId);
  }

  /**
   * test updating item price details which are not in DB.
   */
  @Test
  public void testUpdateItemDetailsNegative() {
    Long itemPriceDetailsId = 927321L;

    ItemPriceDetails itemPriceDetails = new ItemPriceDetails();
    itemPriceDetails.setCapacity("Cap");
    itemPriceDetails.setId(itemPriceDetailsId);
    itemPriceDetails.setPrice(700);
    itemPriceDetails.setQuantity(20);

    when(itemPriceDetailsRepositoty.findOne(itemPriceDetailsId)).thenReturn(null);

    boolean result = itemServiceImpl.updateItemDetails(itemPriceDetails);

    assertEquals("Should return false", false, result);

    verify(itemPriceDetailsRepositoty).findOne(itemPriceDetailsId);
  }

  private ItemPriceDetails getDummyItemPriceDetails(Long id) {
    ItemPriceDetails itemPriceDetails = new ItemPriceDetails();
    itemPriceDetails.setCapacity("Cap-1");
    itemPriceDetails.setId(id);
    itemPriceDetails.setPrice(100);
    itemPriceDetails.setQuantity(40);

    return itemPriceDetails;
  }

}
