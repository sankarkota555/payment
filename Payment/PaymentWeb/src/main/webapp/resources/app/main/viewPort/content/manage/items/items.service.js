"use strict";
{

    function itemsService($http, utilsService) {

        const me = this;

        me.availableItems = null;


        // get available items from database.
        me.getAvailableCompanies = function () {
            return $http.get('getAllCompanies');
        };

        // get available items from database.
        me.getAvailableItems = function () {
            return $http.get('getAllItems');
        };

        // find by item name.
        me.searchItems = function (itemName) {
            return $http.get('findItems?searchText=' + itemName);
        }

        /**
         *  Function to add new Item
         */
        me.addItemDetails = function (item) {
            return $http({
                url: 'addItemDetails',
                method: "POST",
                data: item
            });
        };

        me.updateItem = function (itemPriceDetails) {
            return $http({
                url: 'updateItemDetails',
                method: "POST",
                data: itemPriceDetails
            });
        };

        /**
        * Get all available items.
        */
        function loadAvailableItems() {
            me.getAvailableItems().then(function (data) {
                me.availableItems = data.data;
            }, function (data) {
                // error loading items.
                console.log('error message');
                console.log(data);
                // data.data, data.status, data.headers, data.config
                utilsService.processError(data.data, data.config);
            });
        };

        function processError(response) {
            utilsService.processError(response.config.url, "Internal Server Error", response.data.errorMessage);
        }
        // load items from DB while initialising service.
        //loadAvailableItems();


    };

    angular.module('payment').service('itemsService', itemsService);

};
