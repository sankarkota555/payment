"use strict";
{

    function systemsService($http, $filter) {

        const me = this;
        const millisPerHour = 60 * 60 * 1000;
        /**
         * Loads All systems from DB.
         */
        me.getAllSystems = function () {
            return $http({
                method: 'POST',
                url: 'getAllSystems',

            });

        };

        /**
         * Loads systems status for current instance from DB.
         */
        me.getSystemsStatus = function () {
            return $http({
                method: 'POST',
                url: 'getSystemsStatus',
            });
        };

        /**
         * Add new system in DB.
         */
        me.addNewSystem = function (systemName) {
            const formDataString = $.param({ systemName: systemName }, true);
            return $http({
                headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8' },
                method: 'POST',
                url: 'addNewSystem',
                data: formDataString
            });
        };

        /**
         * Add system usage details in DB.
         */
        me.addSystemUsageDetails = function (systemUsageDetails) {
            return $http({
                method: 'POST',
                url: 'addSystemUsageDetails',
                data: systemUsageDetails
            });
        };

        /**
         * Creates system usage details object from given object.
         */
        me.createSysteUsageDetailsObj = function (systemDetails) {
            const usageObj = { paymentSystem: {} };
            usageObj.paymentSystem.id = systemDetails.id;
            usageObj.cutomerName = systemDetails.customerName;
            usageObj.hours = systemDetails.hours;
            usageObj.loginTime = systemDetails.loginTime;
            usageObj.id = systemDetails.detailsId;
            return usageObj;
        };

        /**
         * Converts login dates in millis to user readable format.
         * And adds logoutTime property to object.
         */
        me.convertLoginTimes = function (systems) {
            if (Array.isArray(systems)) {
                for (const system of systems) {
                    convertTimes(system);
                }

                return systems;
            } else {
                return convertTimes(systems);
            }

        };

        /**
         * updates system usage details in DB.
         */
        me.updateSystemUsageDetails = function (systemUsageDetails) {
            return $http({
                method: 'POST',
                url: 'updateSystemUsageDetails',
                data: systemUsageDetails
            });
        };

        me.substractHours = function (millis, hours) {
            return millis - (hours * millisPerHour);
        }

        /**
         * Applies Time filter to given date.
         */
        function filterTime(date) {
            return $filter('date')(date, "h:mm a");
        }

        function convertTimes(system) {
            if (system.usageDetails && system.usageDetails.length) {
                system.usageDetails[0].logoutTimeInMills = system.usageDetails[0].loginTime + (system.usageDetails[0].hours * millisPerHour);
                system.usageDetails[0].logoutTime = filterTime(system.usageDetails[0].logoutTimeInMills);
                system.usageDetails[0].loginTime = filterTime(system.usageDetails[0].loginTime);
            }

            return system;
        }


    }

    angular.module('payment').service('systemsService', systemsService);

};
