"use strict";
{

	function systemsController(systemsService, utilsService) {


		const me = this;

		me.systems = [];
		me.enableNewSystemForm = false;
		me.currentlyEditingSystem = {};

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
					me.getSystemsStatus();
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
				me.getSystemsStatus();
			}, function (response) {
				// show error opoup
				processError(response);
			});
		};

		/**
		 * Cancel editing system usage details
		 */
		me.cancelEdit = function () {
			me.currentlyEditingSystem = {};
		};

		/**
		 * Shows form to add new system.
		 */
		me.showNewSystemForm = function () {
			me.enableNewSystemForm = true;
			me.currentlyEditingSystem = {};
		};

		me.cancelNewItem = function () {
			me.currentlyEditingSystem = {};
			me.enableNewSystemForm = false
		}

		me.getSystemsStatus();

		function processError(response) {
			utilsService.processError(response);
		}

	}

	angular.module('payment')
		.controller('systemsController', systemsController);
};
