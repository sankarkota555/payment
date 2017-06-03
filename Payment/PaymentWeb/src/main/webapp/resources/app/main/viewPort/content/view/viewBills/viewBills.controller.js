"use strict";
{

    function viewBillsController($scope, billingService, utilsService) {

        const me = this;

        me.fromDate = null;
        me.toDate = null;
        me.billsOnDate = [];
        me.description = "";
        me.query = {
            order: 'generatedDate',
            limit: 5,
            page: 1
        };

        me.getBillsBasedOnDates = function () {
            if (me.fromDate && me.toDate) {
                let fromStr = me.fromDate + "";
                let toStr = me.toDate + "";
                me.description = "Bill Details between " + fromStr.substr(3, 13) + " And " + toStr.substr(3, 13);
                const currentTime = new Date();
                me.toDate.setHours(currentTime.getHours());
                me.toDate.setMinutes(currentTime.getMinutes());
                getBills();
            }
        }

        me.getTodatBills = function () {
            me.fromDate = new Date();
            me.toDate = me.fromDate;
            me.description = "Today bill Details";
            getBills();
        }

        me.printBillConfirm = function (billId) {
            billingService.printBillConfirm(billId);
        }

        function getBills() {
            billingService.getBillsBasedOnDates(me.fromDate, me.toDate).then(
                function (response) {
                    console.log("returnung data: ", response.data);
                    me.billsOnDate = response.data;
                },
                function (response) {
                    console.log("error while searching user:", response);
                    processError(response);
                    me.billsOnDate = [];
                });
        }

        function processError(response) {
            utilsService.processError(response);
        }
    };


    angular.module('payment').controller('viewBillsController', viewBillsController);
};
