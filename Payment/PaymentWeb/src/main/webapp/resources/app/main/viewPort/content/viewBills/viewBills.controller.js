"use strict";
{

    function viewBillsController($scope, billingService) {
        const me = this;
        // Could not pass controller as object to directive, so using scope
        $scope.showNews = false;


        me.getBillsBasedOnDate = function () {
            getBillsBasedOnDate(new Date());
        }

        function getBillsBasedOnDate(billDate) {
            billingService.getBillsBasedOnDate(billDate).then(
                function (response) {
                    console.log("returnung data: ", response.data);
                    me.foundCustomers = response.data;
                },
                function (response) {
                    console.log("error while searching user:", response);
                    me.foundCustomers = [];
                });
        }





    };


    angular.module('payment').controller('viewBillsController', viewBillsController);
};
