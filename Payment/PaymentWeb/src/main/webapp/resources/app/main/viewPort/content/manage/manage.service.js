"use strict";
{

	function itemsService($http) {
		const me = this;

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
			})
		}

	};

	angular.module('payment').service('itemsService', itemsService);

}

