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
            const formDataString = $.param({ itemName: itemName }, true);
            return $http({
                headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8' },
                method: 'POST',
                url: 'findItems',
                data: formDataString
            });
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

        me.findCompaniesLike = function (companyName) {
            const formDataString = $.param({ companyName: companyName }, true);

            return $http({
                headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8' },
                method: 'POST',
                url: 'findCompaniesLike',
                data: formDataString
            });

        };
        /**
         * validate new item.
         */
        me.validateNewItem = function (newItem) {
            console.log("New item to validate");
            console.log(newItem);
            if (newItem.selectedItem && newItem.selectedCompany) {
                for (const itemDetail of newItem.selectedItem.itemDetails) {
                    console.log('item detail in validate new item: ');
                    console.log(itemDetail);
                    if (itemDetail.itemCompany.companyId == newItem.selectedCompany.companyId) {
                        for (const itemPriceDetail of itemDetail.itemPriceDetails) {
                            if ((itemPriceDetail.capacity && newItem.capacity && itemPriceDetail.capacity.toLowerCase() == newItem.capacity.toLowerCase())
                                || (!itemPriceDetail.capacity && !newItem.capacity)) {
                                return false;
                            }
                        }

                    }

                }
            }

            return true;
        };

        //findCompaniesLike

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
                processError(data);
            });
        };

        function processError(response) {
            utilsService.processError(response);
        }
        // load items from DB while initialising service.
        //loadAvailableItems();


    };

    angular.module('payment').service('itemsService', itemsService);

};
