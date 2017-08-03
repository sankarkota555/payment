"use strict";
{

    function utilsService($mdDialog, Notification) {

        const me = this;

        // show error popup
        me.processError = function (response, errorMessage) {
            let errorRequest = response.config.url;
            let errorMessageDetails = response.data.errorMessage;
            if (!errorMessage) {
                errorMessage = "Internal Server Error";
            }
            if (response.status == 500) {
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
            }

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
                if (erroCallbackFunction) {
                    erroCallbackFunction(errorParam);
                }
            });

        }; // END - processError()

        // show alert popup
        me.alertPopup = function (alertTitle, alertDetails, successCallbackFunction) {
            const alertPopup = $mdDialog.show(
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
            if (successCallbackFunction) {
                alertPopup.then(function () {
                    successCallbackFunction();
                }, null);
            } /*else {
                alertPopup.then(null, null);
            } */


        }; // END - processError()

        me.mapItemPriceDetails = function (item) {
            const itemPriceDetails = { id: null, capacity: item.capacity, price: item.price, itemDetails: {}, quantity: item.quantity };
            itemPriceDetails.itemDetails = { id: null, itemCompany: null, item: null };
            if (item.selectedItem && item.selectedItem.itemDetails && item.selectedCompany) {
                for (const itemdetail of item.selectedItem.itemDetails) {
                    if (itemdetail.itemCompany.companyId == item.selectedCompany.companyId) {
                        itemPriceDetails.itemDetails.id = itemdetail.id;
                    }
                }

            }
            itemPriceDetails.itemDetails.itemCompany = { companyName: item.searchCompanyText.toLowerCase() };
            itemPriceDetails.itemDetails.item = { itemName: item.searchItemText.toLowerCase() };

            return itemPriceDetails;
        };

        me.reloadPage = function () {
            window.location.reload();
        };

        me.success = function (message) {
            Notification.success(message);
        };

        me.warning = function (message) {
            Notification(message);
        };

        me.error = function (message) {
            Notification.error(message);
        };

        me.noDelayError = function (message) {
            Notification.error({ message: message, delay: null });
        };

    };

    angular.module('payment').service('utilsService', utilsService);

};
