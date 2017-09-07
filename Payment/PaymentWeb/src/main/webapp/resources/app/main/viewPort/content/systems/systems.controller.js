"use strict";
{

	function systemsController($scope, systemsService, utilsService, $rootScope) {


		const me = this;

		me.systems = [];
		me.enableNewSystemForm = false;
		me.currentlyEditingSystem = {};

		const systemClassName = 'SystemDTO';

		/**
		 * Load all systems from DB.
		 */
		me.getSystemsStatus = function () {
			systemsService.getSystemsStatus().then(function (response) {
				me.systems = systemsService.convertLoginTimes(response.data);
			}, function (response) {
				// show error opoup
				processError(response);
			});
			;
		};


		/**
		 * Load all systems from DB.
		 */
		me.loadSystems = function () {
			systemsService.getAllSystems().then(function (response) {
				//me.systems = response.data;
			}, function (response) {
				// show error opoup
				processError(response);
			});
			;
		};

		/**
		 * Add new system and save it in DB.
		 */
		me.addNewSystem = function () {
			systemsService.addNewSystem(me.currentlyEditingSystem.systemName).then(function (response) {
				if (response.data == -1) {
					utilsService.alertPopup("Duplicate system name!", "A system with given name already exists.", null);
				} else {
					utilsService.success("Successfully added!");
					//me.getSystemsStatus();
					me.cancelNewItem();
				}

			}, function (response) {
				// show error popup
				processError(response);
			});
			;
		};

		/**
		 * Show form for new system usage.
		 */
		me.addSystemUsage = function (id) {
			me.currentlyEditingSystem = { id: id, loginTime: new Date(), hours: 1 };
			me.enableNewSystemForm = false;
		};

		/**
		 * Show edit form for edit system usage.
		 */
		me.editSystemUsage = function (index) {
			let usage = me.systems[index].usageDetails[0];
			me.currentlyEditingSystem = {
				id: me.systems[index].id, detailsId: usage.id,
				loginTime: systemsService.substractHours(usage.logoutTimeInMills, usage.hours),
				hours: usage.hours, customerName: usage.cutomerName
			};
			me.enableNewSystemForm = false;
		};


		/**
		 * Add system usage details in DB.
		 */
		function addSystemUsageDetails(detailsObject) {
			systemsService.addSystemUsageDetails(detailsObject).then(function (response) {
				me.cancelEdit();
			}, function (response) {
				// show error opoup
				processError(response);
			});
		};

		/**
		 * Cancel editing system usage details
		 */
		me.cancelEdit = function () {
			resetEditingItem();
		};

		/**
		 * Shows form to add new system.
		 */
		me.showNewSystemForm = function () {
			me.enableNewSystemForm = true;
			resetEditingItem();
		};

		me.cancelNewItem = function () {
			resetEditingItem();
			me.enableNewSystemForm = false
		};

		/**
		 * Based on gaven object saves new usage or updates usage.
		 */
		me.saveOrUpdateSystemUsage = function () {
			const detailsObject = systemsService.createSysteUsageDetailsObj(me.currentlyEditingSystem);
			if (detailsObject.id) {
				updateSystemUsageDetails(detailsObject);
			} else {
				addSystemUsageDetails(detailsObject);
			}
		};

		me.getSystemsStatus();

		/**
		 * updates system usage details in DD.
		 */
		function updateSystemUsageDetails(detailsObject) {
			systemsService.updateSystemUsageDetails(detailsObject).then(function (response) {
				me.cancelEdit();
			}, function (response) {
				// show error opoup
				processError(response);
			});
		};

		function processError(response) {
			utilsService.processError(response);
		};

		$scope.$on('socketUpdate', function (event, data) {
			if (utilsService.validateSocketUpdate(systemClassName, data)) {
				let foundIndex = utilsService.findIndex(me.systems, data.value, 'id');
				let object = systemsService.convertLoginTimes(data.value);
				if (foundIndex != -1) {
					me.systems.splice(foundIndex, 1, object);
				} else {
					me.systems.push(object);
				}
			} else {
				console.log("socket update is not belogs here: " + data.class);
			}

		});

		function resetEditingItem() {
			me.currentlyEditingSystem = {};
		}

	}

	angular.module('payment')
		.controller('systemsController', systemsController);
};
