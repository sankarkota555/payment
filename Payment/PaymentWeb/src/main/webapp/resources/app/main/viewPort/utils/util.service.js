"use strict";
{

    function utilsService($mdDialog) {

        const me = this;

        // show error popup
        me.processError = function (errorRequest, errorMessage, errorMessageDetails) {
            $mdDialog.show(
                $mdDialog.alert()
                    .clickOutsideToClose(false)
                    .title(errorMessage+" for "+errorRequest)
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

    };

    angular.module('payment').service('utilsService', utilsService);

};
