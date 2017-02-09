"use strict";
{

	function itemsService($http) {

		const me = this;

		me.availableItems = null;

		// get available items from database.
		me.getAvailableCompanies = function () {
			return $http.get('getAllCompanies');
		}

		// get available items from database.
		me.getAvailableItems = function () {
			return $http.get('getAllItems');
		}

		/**
		 * 
		 */
		me.addItem = function (item) {
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
        function loadAvailableItems (){
            me.getAvailableItems().then(function (data) {
                me.availableItems = data.data;
            }, function (data) {
                // error loading items.
                //me.load = false;
                //utilSvc.processError(data.data, data.status, data.headers, data.config);
            });
        };

        // load items from DB while initialising service.
		loadAvailableItems();


	};

	angular.module('payment').service('itemsService', itemsService);

};
