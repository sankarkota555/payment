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
		 * Show edit form for system.
		 */
		me.showEditSystem = function (id) {
			me.currentlyEditingSystem = { id: id, loginTime: new Date(), hours: 1 };
			me.enableNewSystemForm = false;
		};

		/**
		 * Add system usage details in DB.
		 */
		me.addSystemUsageDetails = function () {
			const detailsObject = systemsService.createSysteUsageDetailsObj(me.currentlyEditingSystem);
			systemsService.addSystemUsageDetails(detailsObject).then(function (response) {
				me.cancelEdit();
				//me.getSystemsStatus();
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

		me.getSystemsStatus();

		function processError(response) {
			utilsService.processError(response);
		};

		$scope.$on('socketUpdate', function (event, data) {
			if (utilsService.validateSocketUpdate(systemClassName, data)) {
				let foundIndex = -1;
				for (let index = 0; index < me.systems.length; index++) {
					if (me.systems[index].id === data.value.id) {
						console.log('index:' + index);
						foundIndex = index;
					}
				}
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
