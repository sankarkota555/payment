"use strict";
{

    function itemsService($http, utilsService) {

        const me = this;

        me.availableItems = null;

        // get available items from database.
        me.getAvailableCompanies = function() {
            return $http.get('getAllCompanies');
        }

        // get available items from database.
        me.getAvailableItems = function() {
            return $http.get('getAllItems');
        }

        // find by item name.
        me.searchItems = function(itemName) {
            return $http.get('findItems?searchText=' + itemName);
        }

		/**
		 * 
		 */
        me.addItem = function(item) {
            const object = { itemName: item.itemName, companyId: item.company.companyId };
            return $http({
                url: 'addItem',
                method: "POST",
                data: object
            });
        }

		/**
		* Get all available items.
		*/
        function loadAvailableItems() {
            me.getAvailableItems().then(function(data) {
                me.availableItems = data.data;
            }, function(data) {
                // error loading items.
                console.log('error message');
                console.log(data);
                // data.data, data.status, data.headers, data.config
                utilsService.processError(data.data, data.config);
            });
        };

        // load items from DB while initialising service.
        loadAvailableItems();


    };

    angular.module('payment').service('itemsService', itemsService);

};
