"use strict";
{

    function userActivityController($scope, userActivityService, utilsService) {

        const me = this;
        me.logout = function () {
            userActivityService.logout().then(function (response) {
                console.log("successfully logged out");
                console.log(response);
                userActivityService.logoutSuccess();
                utilsService.alertPopup('Logout Success!!', null, userActivityService.navigateTologin);
            },
                function (response) {
                    console.log("failed to logout");
                    // show error opoup
                    processError(response);
                });;
        }

        function processError(response) {
            utilsService.processError(response.config.url, "Internal Server Error", response.data.errorMessage);
        }


    };

    angular.module('payment').controller('userActivityController', userActivityController);

};