package com.payment.service.impl;

import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payment.domain.Bill;
import com.payment.domain.Customer;
import com.payment.domain.ItemDetails;
import com.payment.domain.SoldItem;
import com.payment.repositories.BillRepository;
import com.payment.repositories.CustomerRepository;
import com.payment.repositories.ItemDetailsRepository;
import com.payment.service.BillingService;

@Service
public class BillingServiceImpl implements BillingService {

	private static Logger log = Logger.getLogger(BillingServiceImpl.class);

	@Autowired
	private BillRepository billrepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private ItemDetailsRepository itemDetailsRepository;

	@Override
	@Transactional
	public void saveBill(Bill bill) {
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
		log.info("sold items details: ");
		for (int index = 0; index < soldItems.size(); index++) {
			soldItem = soldItems.get(index);
			if (soldItem.getItemDetails().getId() != null) {
				itemDetails = itemDetailsRepository.findOne(soldItems.get(index).getItemDetails().getId());
				log.info("Item details exists in DB: id: " + itemDetails.getId());
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
		log.info("bill saved success fully");

	}

	@Override
	@Transactional
	public List<Bill> getCustomerBills(Long customerId) {
		Customer customer = customerRepository.findOne(customerId);
		return customer.getBills();
	}

}
