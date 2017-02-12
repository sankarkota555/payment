"use strict";
{

    function billingController($scope, $mdDialog, $timeout, billingService, itemsService, utilsService) {

        const me = this;

        me.availableItems = [];
        me.searchedItems = [];

        for (let i = 0; i < 10; i++) {
            const obj = { type: 'type ' + i, name: 'item name' + i };
            me.availableItems.push(obj);
        }

        console.log("available items:" + me.availableItems.length);
        me.bill = { items: [{ selectedCompany: null }] };

        // increments for time picker
        me.hrstep = 1;
        me.minstep = 30;

        //function to add items to user
        me.addItem = function() {
            me.bill.items.push({ selectedCompany: null });
        };

        //function for generating bill
        me.generateBill = function() {
            console.log("bill objet");
            console.log(me.bill);
            billingService.generateBill(me.bill).then(function(response) {
                console.log("successfully saved");
                console.log(response);
                printBillConfirm(response.data);
            },
                function(response) {
                    console.log("failed to saved");
                    // show error opoup
                    processError(response); 
                });;
        };

        //function to reset form details
        me.resetForm = function() {
            // reset user object
            //me.user = {name:"", phone:"", email:"", address:"", items:[{}],soldDate:null};
        };

        //function to delete item
        me.deleteItem = function(index) {
            // delete selected item
            me.bill.items.splice(index, 1);
        };

        //function to search for item
        me.searchItems = function(itemName) {
            itemsService.searchItems(itemName).then(function(response) {
                console.log("successfully searched item " + itemName);
                console.log(response.data);
                me.searchedItems = response.data; 
                console.log("items st to variables");
            },
                function(response) {
                    console.log("error");
                    //show error message opoup
                    processError(response);                   
                });;
        };

        /**
         * Prints the given bill based in user confirmation.
         */
        function printBillConfirm(billId, $event) {
            const billPrinfConfirmationDialog = $mdDialog.confirm()
                .title('Would you like to print this bill?')
                .ariaLabel('bill print confirmation')
                .targetEvent($event)
                .ok('Print')
                .cancel('Close');
            $mdDialog.show(billPrinfConfirmationDialog).then(function() {
                billingService.printBill(billId);
            }, null);
        }

    };

    function processError(response){
         utilsService.processError(response.config.url,"Internal Server Error", response.data.errorMessage);
    }

    angular.module('payment').controller('billingController', billingController);

};
