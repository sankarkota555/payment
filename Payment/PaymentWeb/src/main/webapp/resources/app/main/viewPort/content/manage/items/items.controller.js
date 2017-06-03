"use strict";
{

    function itemsController($scope, itemsService, utilsService) {

        const me = this;

        me.items = [];
        me.availableCompanies = [];

        me.addItem = function (item) {
            itemsService.addItem(item).then(function (data) {
                console.log("added success fully");
            }, function (data) {
                // error loading items.
                utilsService.processError(response);
            });
        }

        /**
         * Get all available companies.
         */
        me.getAvalableCompanies = function () {
            itemsService.getAvailableCompanies().then(function (data) {
                me.availableCompanies = data.data;
            }, function (data) {
                // error loading items.
                //me.load = false;
                utilsService.processError(response);
            });
        };

        // get all companies
        //me.getAvalableCompanies();

    };


    angular.module('payment').controller('itemsController', itemsController);

};
