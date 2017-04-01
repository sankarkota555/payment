package com.payment.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.payment.domain.Bill;
import com.payment.domain.Customer;
import com.payment.domain.Item;
import com.payment.domain.ItemCompany;
import com.payment.domain.ItemDetails;
import com.payment.domain.SoldItem;
import com.payment.repositories.BillRepository;
import com.payment.repositories.CustomerRepository;
import com.payment.repositories.ItemCompanyRepository;
import com.payment.repositories.ItemDetailsRepository;
import com.payment.repositories.ItemRepository;
import com.payment.service.BillingService;
import com.payment.utils.DateUtils;

@Service
public class BillingServiceImpl implements BillingService {

	private static final Logger log = LoggerFactory.getLogger(BillingServiceImpl.class);

	@Autowired
	private BillRepository billrepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ItemDetailsRepository itemDetailsRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ItemCompanyRepository itemCompanyRepository;

	/**
	 * saves given bill into database.
	 * 
	 * @param {@link
	 * 			Bill} object to save
	 * @return saved bill id.
	 */
	@Override
	@Transactional
	public Long saveBill(Bill bill) {
		log.info("bill details");
		Customer customer = bill.getCustomer();
		log.info("cutomer details: ");
		log.info("name: " + customer.getName() + "  email:" + customer.getEmail() + " Address: " + customer.getAddress()
				+ " phone:" + customer.getPhone());
		log.info("bill generated date: " + bill.getGeneratedDate());
		log.info("bill getNetAmount: " + bill.getNetAmount());

		if (bill.getGeneratedDate() == null) {
			bill.setGeneratedDate(Calendar.getInstance().getTime());
		}
		customer = customerRepository.findByPhone(bill.getCustomer().getPhone());
		List<SoldItem> soldItems = bill.getSoldItems();
		ItemDetails itemDetails;
		SoldItem soldItem;
		ItemCompany company;
		Item item;
		log.info("sold items details: ");
		for (int index = 0; index < soldItems.size(); index++) {
			soldItem = soldItems.get(index);
			if (soldItem.getItemDetails().getId() != null) {
				itemDetails = itemDetailsRepository.findOne(soldItems.get(index).getItemDetails().getId());
				if (itemDetails.getQuantity() != null) {
					itemDetails.setQuantity(itemDetails.getQuantity() - soldItem.getQuantity());
				}
				log.info("Item details exists in DB with id: " + itemDetails.getId());
				soldItem.setItemDetails(itemDetails);
			} else {
				log.info("Item details not found in DB - creating new item details");
				itemDetails = new ItemDetails();
				item = itemRepository.findByItemName(soldItems.get(index).getItemDetails().getItem().getItemName());
				if (item == null) {
					log.info("Item not found in DB with name: {}",
							soldItems.get(index).getItemDetails().getItem().getItemName());
					item = new Item();
					item.setItemName(soldItems.get(index).getItemDetails().getItem().getItemName());
				}
				itemDetails.setItem(item);
				company = itemCompanyRepository
						.findByCompanyName(soldItems.get(index).getItemDetails().getItemCompany().getCompanyName());
				if (company == null) {
					log.info("Company not found in DB with name:{}",
							soldItems.get(index).getItemDetails().getItemCompany().getCompanyName());
					company = new ItemCompany();
					company.setCompanyName(soldItems.get(index).getItemDetails().getItemCompany().getCompanyName());
				}

				itemDetails.setItemCompany(company);

				itemDetails.setPrice(soldItem.getSoldPrice());
				itemDetails.setCapacity(soldItems.get(index).getItemDetails().getCapacity());
				soldItem.setItemDetails(itemDetails);
			}

			log.info("item name: " + soldItem.getItemDetails().getItem().getItemName());
			log.info("sold quantity: " + soldItem.getQuantity() + "  sold price: " + soldItem.getSoldPrice());

			log.info("  getCapacity: " + soldItem.getItemDetails().getCapacity() + "  company name: "
					+ soldItem.getItemDetails().getItemCompany().getCompanyName());

		}
		if (customer != null) {
			bill.setCustomer(customer);
		}
		bill = billrepository.save(bill);
		log.info("bill saved success fully with id:{} ", bill.getBillId());
		return bill.getBillId();

	}

	@Override
	@Transactional
	public List<Bill> getCustomerBills(Long customerId) {
		Customer customer = customerRepository.findOne(customerId);
		return customer.getBills();
	}

	@Override
	@Transactional
	public Bill getBillById(long billId) {
		return billrepository.findOne(billId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Bill> getBillsBetweebDates(Date frmDate, Date toDate) {
		if (frmDate == null) {
			frmDate = DateUtils.addDaysToDate(frmDate, -1);
		}
		if (toDate == null) {
			toDate = DateUtils.getCurrentdate();
		}
		log.info("get bills between dates, from date:{}, to date: {}", frmDate, toDate);
		List<Bill> bills = billrepository.getBillsBetweenDates(frmDate, toDate);
		log.info("Number if bills: " + bills.size());
		return bills;
	}

}
