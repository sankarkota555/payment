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
        me.confirmationPopup = function (confirmationMessage, yesButtonText, successCallbackFunction, erroCallbackFunction, successParam, errorParam) {
            if (!yesButtonText) {
                yesButtonText = 'YES';
            }
            const confirmationDialog = $mdDialog.confirm()
                .title(confirmationMessage)
                .ariaLabel('confirmation prompt')
                .ok(yesButtonText)
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
        me.alertPopup = function (alertTitle, alertDetails, sucessCallbackFunction) {
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
            ).then(function () {
                sucessCallbackFunction();
            }, null);

        }; // END - processError()

        me.mapItemPriceDetails = function (item) {
            itemPriceDetails = { id: null, capacity: item.capacity, price: item.price, itemDetails: {} };
            itemPriceDetails.itemDetails = { id: null, itemCompany: null, item: null };
            itemPriceDetails.itemDetails.itemCompany = { companyName: item.searchCompanyText.toLowerCase() };
            itemPriceDetails.itemDetails.item = { itemName: item.searchItemText.toLowerCase() };
            return itemPriceDetails;
        };
    };

    angular.module('payment').service('utilsService', utilsService);

};
