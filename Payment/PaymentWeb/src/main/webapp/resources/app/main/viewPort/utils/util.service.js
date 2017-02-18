"use strict";
{

    function utilsService($mdDialog) {

        const me = this;

        // show error popup
        me.processError = function (errorRequest, errorMessage, errorMessageDetails) {
            $mdDialog.show(
                $mdDialog.alert()
                    .clickOutsideToClose(false)
                    .title(errorMessage + " for " + errorRequest)
                    .textContent(errorMessageDetails)
                    .ariaLabel('Alert error message')
                    .ok('Close')
                    .openFrom({
                        top: -50,
                        width: 30,
                        height: 80
                    }).closeTo({
                        right: 1500
                    })
            );

        }; // END - processError()

        // Confirmation popup
        me.confirmationPopup = function (confirmationMessage, successCallbackFunction, erroCallbackFunction, successParam, errorParam) {
            const confirmationDialog = $mdDialog.confirm()
                .title(confirmationMessage)
                .ariaLabel('confirmation prompt')
                .ok('YES')
                .cancel('NO')
                .openFrom({
                    top: -50,
                    width: 30,
                    height: 80
                })
                .closeTo({
                    right: 1500
                });

            $mdDialog.show(confirmationDialog).then(function () {
                successCallbackFunction(successParam);
            }, function () {
                erroCallbackFunction(errorParam);
            });

        }; // END - processError()

        // show alert popup
        me.alertPopup = function (alertTitle, alertDetails) {
            $mdDialog.show(
                $mdDialog.alert()
                    .clickOutsideToClose(false)
                    .title(alertTitle)
                    .textContent(alertDetails)
                    .ariaLabel('Alert infromation message')
                    .ok('Close')
                    .openFrom({
                        top: -50,
                        width: 30,
                        height: 80
                    }).closeTo({
                        right: 1500
                    })
            );

        }; // END - processError()

    };

    angular.module('payment').service('utilsService', utilsService);

};
