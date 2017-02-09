"use strict";
{

    function utilsService($mdDialog) {

        const me = this;

        // show error popup
        me.processError = function (errorMessage, errorMessageDetails) {
            $mdDialog.show(
                $mdDialog.alert()
                    .clickOutsideToClose(false)
                    .title(errorMessage)
                    .textContent(errorMessageDetails)
                    .ariaLabel('Alert error message')
                    .ok('Close')
                    .openFrom({
                        top: -50,
                        width: 30,
                        height: 80
                    }).closeTo({
                        left: 1500
                    })
            );

        }; // END - processError()

    };

    angular.module('payment').service('utilsService', utilsService);

};
