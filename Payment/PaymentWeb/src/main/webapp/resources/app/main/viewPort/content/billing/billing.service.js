"use strict";

{

    function billingService($http) {

        const me = this;

        me.generateBill = function (bill) {

            const billData = { soldItems: [] };
            let totalAmount = 0;
            billData.customer = bill.customer;
            billData.generatedDate = bill.generatedDate;
            // billData.netAmount = 
            for (let index = 0; index < bill.items.length; index++) {
                const itemObj = { itemDetails: { id: null }, quantity: bill.items[index].quantity, soldPrice: bill.items[index].price };
                if (bill.items[index].selectedItem == null) {
                    // itemObj.itemName = bill.items[index].searchItemText;
                    itemObj.itemDetails = { id: null, capacity: bill.items[index].capacity, price: bill.items[index].price, itemCompany: null, item: null };
                    itemObj.itemDetails.itemCompany = { companyName: bill.items[index].searchCompanyText };
                    itemObj.itemDetails.item = { itemName: bill.items[index].searchItemText };

                } else if (bill.items[index].selectedCompany == null) {
                    itemObj.itemDetails = { id: null, capacity: bill.items[index].capacity, price: bill.items[index].price, itemCompany: null, item: null };
                    itemObj.itemDetails.itemCompany = { companyName: bill.items[index].searchCompanyText };
                    itemObj.itemDetails.item = { itemName: bill.items[index].selectedItem.itemName };
                }
                else {
                    itemObj.itemDetails.id = bill.items[index].selectedCompany.id;
                }


                billData.soldItems.push(itemObj);
                totalAmount += (bill.items[index].price * bill.items[index].quantity);
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
            const reqData = { billId: billId }
            return $http({
                url: 'generateBillPdf',
                method: "GET",
                data: reqData,
            });
        }; // END - printBill()

    }; // END - billingService()

    angular.module('payment').service('billingService', billingService);

};
