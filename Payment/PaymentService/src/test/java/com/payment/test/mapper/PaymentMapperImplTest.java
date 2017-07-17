package com.payment.test.mapper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.payment.domain.Item;
import com.payment.domain.ItemCompany;
import com.payment.domain.ItemDetails;
import com.payment.domain.ItemPriceDetails;
import com.payment.mapper.PaymentMapperImpl;
import com.payment.repositories.ItemCompanyRepository;
import com.payment.repositories.ItemDetailsRepository;
import com.payment.repositories.ItemRepository;

public class PaymentMapperImplTest {

  private PaymentMapperImpl paymentMapperImpl;

  @Mock
  private ItemRepository itemRepository;

  @Mock
  private ItemDetailsRepository itemDetailsRepository;

  @Mock
  private ItemCompanyRepository itemCompanyRepository;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    paymentMapperImpl = new PaymentMapperImpl();
    paymentMapperImpl.setItemRepository(itemRepository);
    paymentMapperImpl.setItemDetailsRepository(itemDetailsRepository);
    paymentMapperImpl.setItemCompanyRepository(itemCompanyRepository);
  }

  /**
   * Verify mapping item price details with existing company and existing item for bill.
   */
  @Test
  public void testMappingItemPriceDetailsWithExistingDetailsForBill() {
    Integer soldPrice = 300;
    Item item = new Item();
    item.setItemName("Item - one");

    ItemCompany itemCompany = new ItemCompany();
    itemCompany.setCompanyName("Company - one");

    ItemDetails itemDetails = new ItemDetails();
    itemDetails.setItem(item);
    itemDetails.setItemCompany(itemCompany);
    ItemPriceDetails itemPriceDetails = new ItemPriceDetails();
    itemPriceDetails.setItemDetails(itemDetails);
    itemPriceDetails.setCapacity("Cap");
    itemPriceDetails.setPrice(200);
    itemPriceDetails.setQuantity(2);

    Item dummyItem = getDummyItem();
    ItemCompany dummyCompany = getDummyItemCompanty();

    when(itemRepository.findByItemNameIgnoreCase(item.getItemName())).thenReturn(dummyItem);
    when(itemCompanyRepository.findByCompanyNameIgnoreCase(itemCompany.getCompanyName()))
        .thenReturn(dummyCompany);

    paymentMapperImpl.findAndMapItemPricedetails(itemPriceDetails, soldPrice, true);

    verify(itemRepository).findByItemNameIgnoreCase(item.getItemName());
    verify(itemCompanyRepository).findByCompanyNameIgnoreCase(itemCompany.getCompanyName());
    verify(itemDetailsRepository,never()).findOne(Matchers.anyLong());

    assertEquals("Item is not mapped correctly", dummyItem,
        itemPriceDetails.getItemDetails().getItem());
    assertEquals("Item comapny is not mapped correctly", dummyCompany,
        itemPriceDetails.getItemDetails().getItemCompany());
    assertEquals("Price is mapped incorrect", soldPrice, itemPriceDetails.getPrice());
    assertEquals("Qunatity should not map for bill generation", null,
        itemPriceDetails.getQuantity());

  }

  /**
   * Verify mapping item price details with existing company and new item for bill.
   */
  @Test
  public void testMappingItemPriceDetailsWithExistingCompanyForBill() {
    Integer soldPrice = 300;
    Item item = new Item();
    item.setItemName("Item - one");

    ItemCompany itemCompany = new ItemCompany();
    itemCompany.setCompanyName("Company - one");

    ItemDetails itemDetails = new ItemDetails();
    itemDetails.setItem(item);
    itemDetails.setItemCompany(itemCompany);
    ItemPriceDetails itemPriceDetails = new ItemPriceDetails();
    itemPriceDetails.setItemDetails(itemDetails);
    itemPriceDetails.setCapacity("Cap");
    itemPriceDetails.setPrice(200);
    itemPriceDetails.setQuantity(2);

    ItemCompany dummyCompany = getDummyItemCompanty();

    when(itemRepository.findByItemNameIgnoreCase(item.getItemName())).thenReturn(null);
    when(itemCompanyRepository.findByCompanyNameIgnoreCase(itemCompany.getCompanyName()))
        .thenReturn(dummyCompany);

    paymentMapperImpl.findAndMapItemPricedetails(itemPriceDetails, soldPrice, true);

    verify(itemRepository).findByItemNameIgnoreCase(item.getItemName());
    verify(itemCompanyRepository).findByCompanyNameIgnoreCase(itemCompany.getCompanyName());
    verify(itemDetailsRepository,never()).findOne(Matchers.anyLong());

    assertEquals("New Item name should same", item.getItemName(),
        itemPriceDetails.getItemDetails().getItem().getItemName());
    assertEquals("New Item Id should be null", null,
        itemPriceDetails.getItemDetails().getItem().getItemId());
    assertEquals("Item comapny is not mapped correctly", dummyCompany,
        itemPriceDetails.getItemDetails().getItemCompany());
    assertEquals("Price is mapped incorrect", soldPrice, itemPriceDetails.getPrice());
    assertEquals("Qunatity should not map for bill generation", null,
        itemPriceDetails.getQuantity());

  }

  /**
   * Verify mapping item price details with new company and existing item for bill.
   */
  @Test
  public void testMappingItemPriceDetailsWithExistingItemForBill() {
    Integer soldPrice = 300;
    Item item = new Item();
    item.setItemName("Item - one");

    ItemCompany itemCompany = new ItemCompany();
    itemCompany.setCompanyName("Company - one");

    ItemDetails itemDetails = new ItemDetails();
    itemDetails.setItem(item);
    itemDetails.setItemCompany(itemCompany);
    ItemPriceDetails itemPriceDetails = new ItemPriceDetails();
    itemPriceDetails.setItemDetails(itemDetails);
    itemPriceDetails.setCapacity("Cap");
    itemPriceDetails.setPrice(200);
    itemPriceDetails.setQuantity(2);

    Item dummyItem = getDummyItem();

    when(itemRepository.findByItemNameIgnoreCase(item.getItemName())).thenReturn(dummyItem);
    when(itemCompanyRepository.findByCompanyNameIgnoreCase(itemCompany.getCompanyName()))
        .thenReturn(null);

    paymentMapperImpl.findAndMapItemPricedetails(itemPriceDetails, soldPrice, true);

    verify(itemRepository).findByItemNameIgnoreCase(item.getItemName());
    verify(itemCompanyRepository).findByCompanyNameIgnoreCase(itemCompany.getCompanyName());
    verify(itemDetailsRepository,never()).findOne(Matchers.anyLong());

    assertEquals("New Item company name should same", itemCompany.getCompanyName(),
        itemPriceDetails.getItemDetails().getItemCompany().getCompanyName());
    assertEquals("New Item company Id should be null", null,
        itemPriceDetails.getItemDetails().getItemCompany().getCompanyId());

    assertEquals("Item is not mapped correctly", dummyItem,
        itemPriceDetails.getItemDetails().getItem());
    assertEquals("Price is mapped incorrect", soldPrice, itemPriceDetails.getPrice());
    assertEquals("Qunatity should not map for bill generation", null,
        itemPriceDetails.getQuantity());

  }

  /**
   * Verify mapping with item price details and new company and existing item for bill.
   */
  @Test
  public void testMappingItemPriceDetailsWithNewDetailsForBill() {
    Integer soldPrice = 300;
    Item item = new Item();
    item.setItemName("Item - one");

    ItemCompany itemCompany = new ItemCompany();
    itemCompany.setCompanyName("Company - one");

    ItemDetails itemDetails = new ItemDetails();
    itemDetails.setItem(item);
    itemDetails.setItemCompany(itemCompany);
    ItemPriceDetails itemPriceDetails = new ItemPriceDetails();
    itemPriceDetails.setItemDetails(itemDetails);
    itemPriceDetails.setCapacity("Cap");
    itemPriceDetails.setPrice(200);
    itemPriceDetails.setQuantity(2);

    when(itemRepository.findByItemNameIgnoreCase(item.getItemName())).thenReturn(null);
    when(itemCompanyRepository.findByCompanyNameIgnoreCase(itemCompany.getCompanyName()))
        .thenReturn(null);

    paymentMapperImpl.findAndMapItemPricedetails(itemPriceDetails, soldPrice, true);

    verify(itemRepository).findByItemNameIgnoreCase(item.getItemName());
    verify(itemCompanyRepository).findByCompanyNameIgnoreCase(itemCompany.getCompanyName());
    verify(itemDetailsRepository,never()).findOne(Matchers.anyLong());

    assertEquals("New Item company name should same", itemCompany.getCompanyName(),
        itemPriceDetails.getItemDetails().getItemCompany().getCompanyName());
    assertEquals("New Item company Id should be null", null,
        itemPriceDetails.getItemDetails().getItemCompany().getCompanyId());
    assertEquals("New Item company name should same", itemCompany.getCompanyName(),
        itemPriceDetails.getItemDetails().getItemCompany().getCompanyName());
    assertEquals("New Item company Id should be null", null,
        itemPriceDetails.getItemDetails().getItemCompany().getCompanyId());
    assertEquals("Price is mapped incorrect", soldPrice, itemPriceDetails.getPrice());
    assertEquals("Qunatity should not map for bill generation", null,
        itemPriceDetails.getQuantity());

  }

  private Item getDummyItem() {
    Item item = new Item();
    item.setItemId(458995L);
    item.setItemName("Dummy Item - one");
    return item;
  }

  private ItemCompany getDummyItemCompanty() {
    ItemCompany itemCompany = new ItemCompany();
    itemCompany.setCompanyId(12345L);
    itemCompany.setCompanyName("Dummy Company - one");
    return itemCompany;
  }
}
