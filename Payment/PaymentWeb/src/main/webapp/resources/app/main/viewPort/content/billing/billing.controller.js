(function () {

    var billingController = function ($scope, $mdDialog, billingService, itemsService) {

        const me = this;

        me.availableItems = [];

        const billPrinfConfirmationDialog = $mdDialog.confirm()
            .title('Would you like to print this bill?')
            .textContent('All of the banks have agreed to forgive you your debts.')
            .ariaLabel('bill print confirmation')
            .targetEvent(ev)
            .ok('Print')
            .cancel('Close');

        for (let i = 0; i < 10; i++) {
            var obj = { type: 'type ' + i, name: 'item name' + i };
            me.availableItems.push(obj);
        }

        console.log("available items:" + me.availableItems.length);
        me.bill = { items: [{}] };

        // increments for time picker
        me.hrstep = 1;
        me.minstep = 30;

        //function to add items to user
        me.addItem = function () {
            me.bill.items.push({});
        };

        //function for generating bill
        me.generateBill = function () {
            console.log("bill objet");
            console.log(me.bill);
            billingService.generateBill(me.bill).then(function (response) {
                console.log("successfully saved");
                console.log(response);
                printBillConfirm(response.data);
            },
                function (response) {
                    console.log("failed to saved");
                });;
        };

        //function to reset form details
        me.resetForm = function () {
            // reset user object
            //me.user = {name:"", phone:"", email:"", address:"", items:[{}],soldDate:null};
        };

        //function to delete item
        me.deleteItem = function (index) {
            // delete selected item
            me.bill.items.splice(index, 1);
        };


        /**
         * Get all available items.
         */
        me.getAvalableItems = function () {
            itemsService.getAvailableItems().then(function (data) {
                me.availableItems = data.data;
            }, function (data) {
                // error loading items.
                //me.load = false;
                //utilSvc.processError(data.data, data.status, data.headers, data.config);
            });
        };

        //get all available items on controller load.
        me.getAvalableItems();

        /**
         * Prints the given bill based in user confirmation.
         */
        function printBillConfirm(billId) {
            $mdDialog.show(billPrinfConfirmationDialog).then(function () {
                billingService.printBill(billId);
            }, null);
        }

    };

    angular.module('payment').controller('billingController', billingController);

} ());