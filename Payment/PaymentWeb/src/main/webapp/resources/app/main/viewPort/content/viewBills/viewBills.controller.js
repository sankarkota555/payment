"use strict";
{

    function viewBillsController($scope, billingService) {
        const me = this;

        me.fromDate = null;
        me.toDate = null;
        me.billsOnDate = [];

        me.getBillsBasedOnDates = function () {
            billingService.getBillsBasedOnDates(me.fromDate, me.toDate).then(
                function (response) {
                    console.log("returnung data: ", response.data);
                    me.billsOnDate = response.data;
                },
                function (response) {
                    console.log("error while searching user:", response);
                    me.billsOnDate = [];
                });
        }
    };


    angular.module('payment').controller('viewBillsController', viewBillsController);
};
