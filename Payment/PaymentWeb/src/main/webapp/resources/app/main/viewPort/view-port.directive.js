"use strict";
{

    function viewPort() {
        return {
            template: require('./view-port.template.html'),
            controller: 'viewPortController',
            controllerAs: 'viewPortController'
        }
    };

    angular.module('payment').directive('viewPort', viewPort);

};
