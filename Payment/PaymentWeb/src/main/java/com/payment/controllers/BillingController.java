package com.payment.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payment.domain.Bill;
import com.payment.service.BillingService;

@RestController
public class BillingController {

	private static Logger log = Logger.getLogger(BillingController.class);

	@Autowired
	private BillingService billingService;

	@RequestMapping(name = "/saveBill", method = RequestMethod.POST, consumes = { "application/json" })
	public void saveBill(@RequestBody Bill bill) {
		billingService.saveBill(bill);

	}

	@RequestMapping(name = "/getCustomerBills", method = RequestMethod.GET)
	public List<Bill> getCunstomerBills(@RequestParam Long customerId) {
		return billingService.getCustomerBills(customerId);
	}

}
