"use strict";

{

    function billingService($http, $window, utilsService) {

        const me = this;

        me.generateBill = function (bill) {

            const billData = { soldItems: [] };
            let totalAmount = 0;
            billData.customer = bill.customer;
            billData.generatedDate = bill.generatedDate;
            // billData.netAmount = 
            for (const item of bill.items) {
                const itemObj = { itemPriceDeatils: { id: null }, quantity: item.quantity, soldPrice: item.price };
                // existing item price details selected
                if (item.detailsId) {
                    itemObj.itemPriceDeatils.id = item.detailsId;
                }
                // check item is new or not, if new create new item and item details
                //else if (item.selectedItem == null || item.selectedCompany == null) {
                else {
                    // itemObj.itemName = item.searchItemText;
                    itemObj.itemPriceDeatils = { id: null, capacity: item.capacity, price: item.price, itemDetails: {} };
                    itemObj.itemPriceDeatils.itemDetails = { id: null, itemCompany: null, item: null };
                    itemObj.itemPriceDeatils.itemDetails.itemCompany = { companyName: item.searchCompanyText.toLowerCase() };
                    itemObj.itemPriceDeatils.itemDetails.item = { itemName: item.searchItemText.toLowerCase() };

                }// check item company is new or not 
                /*else if (item.selectedCompany == null) {
                    itemObj.itemPriceDeatils = { id: null, capacity: item.capacity, price: item.price, itemDetails: {} };
                    itemObj.itemPriceDeatils.itemDetails = { id: null, itemCompany: null, item: null };
                    itemObj.itemDetails.itemCompany = { companyName: item.searchCompanyText.toLowerCase() };
                    itemObj.itemDetails.item = { itemName: item.selectedItem.itemName.toLowerCase() };
                } */

                billData.soldItems.push(itemObj);
                totalAmount += (item.price * item.quantity);
            }
            billData.netAmount = totalAmount;
            console.log("final bill object prepared: ");
            console.log(billData);

            return $http({
                url: 'saveBill',
                method: "POST",
                data: billData,
            });

        }; // END - generateBill()

        me.printBill = function (billId) {
            const height = screen.height;
            const width = screen.width;
            $window.open('generateBillPdf?billId=' + billId, '', "top=" + height * (1 / 2.5) + ",left=" + width * (3 / 4.1) + ",width=" + (width / 4) + ",height=" + (height / 2));
        }; // END - printBill()

        /**
         * Returns bills between given dates
         */
        me.getBillsBasedOnDates = function (fromDate, toDate) {
            const params = { fromDate: fromDate, toDate: toDate };
            return $http({
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                url: 'getBillsBasedOnDates',
                method: "POST",
                data: $.param(params)
            });

        };

        /**
         * Prints the given bill based in user confirmation.
         */
        me.printBillConfirm = function (billId) {
            utilsService.confirmationPopup('Would you like to print this bill?', 'print', me.printBill, null, billId);
        };

    }; // END - billingService()

    angular.module('payment').service('billingService', billingService);

};
