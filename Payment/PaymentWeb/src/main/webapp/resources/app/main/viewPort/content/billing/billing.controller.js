"use strict";
{

    function billingController($scope, billingService, itemsService, utilsService) {

        const me = this;

        me.availableItems = [];
        me.searchedItems = [];

        let previousSearchText = "<<";

        console.log("available items:" + me.availableItems.length);
        me.bill = { items: [{ selectedCompany: null, availableQuantity: null, actualPrice: null }] };

        // increments for time picker
        me.hrstep = 1;
        me.minstep = 30;

        /**
         * function to add items to user
         */
        me.addItem = function () {
            me.bill.items.push({ selectedCompany: null, availableQuantity: null, actualPrice: null });
        };

        /**
         * function for generating bill
         */
        me.generateBill = function () {
            console.log("bill objet");
            console.log(me.bill);
            me.generateButtonText = "";
            me.inProgress = true;
            utilsService.confirmationPopup('Do you want to Generate Bill?', 'yes', saveBill, setButtonText, 'testparam');

        };

        function saveBill(test) {
            console.log("test param received: " + test);
            billingService.generateBill(me.bill).then(function (response) {
                console.log("successfully saved");
                console.log(response);
                me.resetForm();
                billingService.printBillConfirm(response.data);
            },
                function (response) {
                    console.log("failed to saved");
                    setButtonText();
                    // show error opoup
                    processError(response);
                });;
        }

        /**
         * function to reset form details
         */
        me.resetForm = function () {
            // reset form
            me.bill = { items: [{ selectedCompany: null }] };
            $scope.billingForm.$setPristine();
            $scope.billingForm.$setUntouched();
            setButtonText();
            console.log("form reset completed");
        };

        function setButtonText() {
            me.generateButtonText = "GENERATE";
            me.inProgress = false;
        }

        setButtonText();

        /**
         * function to delete item
         */
        me.deleteItem = function (index) {
            // delete selected item
            me.bill.items.splice(index, 1);
        };

        /**
         * function to search for an item by item name
         */
        me.searchItems = function (itemName) {
            if (itemName.length > 1 && itemName.indexOf(previousSearchText) != 0) {
                me.searchedItems = [];
                itemsService.searchItems(itemName).then(function (response) {
                    console.log("successfully searched item " + itemName);
                    console.log(response.data);
                    previousSearchText = itemName;
                    me.searchedItems = response.data;
                    console.log("items st to variables");
                },
                    function (response) {
                        console.log("error");
                        //show error message opoup
                        processError(response);
                    });;
            }

        };

        /**
         * Update item availableQuantity and actualPrice.
         */
        me.capacitySelected = function (index, detailsId) {
            for (const obj of me.bill.items[index].selectedCompany.itemPriceDeatils) {
                if (obj.id == detailsId) {
                    me.bill.items[index].availableQuantity = obj.quantity;
                    me.bill.items[index].actualPrice = obj.price;
                }
            }

        };

        /**
         * Update item availableQuantity and actualPrice.
         */
        me.companySelected = function (index, itemPriceDeatils) {
            if (itemPriceDeatils.length == 1) {
                me.bill.items[index].availableQuantity = itemPriceDeatils[0].quantity;
                me.bill.items[index].actualPrice = itemPriceDeatils[0].price;
                me.bill.items[index].detailsId = itemPriceDeatils[0].id;
            }
        };



        function processError(response) {
            utilsService.processError(response.config.url, "Internal Server Error", response.data.errorMessage);
        }

    };

    angular.module('payment').controller('billingController', billingController);

};
