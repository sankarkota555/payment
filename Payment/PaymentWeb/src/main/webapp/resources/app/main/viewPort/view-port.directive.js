"use strict";
{

    function viewPort() {
        return {
            template: require('./viewPort.template.html'),
            controller: 'viewPortController',
            controllerAs: 'viewPortController'
        }
    };

    angular.module('payment').directive('viewPort', viewPort);

};
