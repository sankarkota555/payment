"use strict";
{

    function menu() {
        return {
            restrict: 'E',
            template: require('./menu.template.html'),
            controller: 'menuController',
            controllerAs: 'menuController'
        }
    };

    angular.module("payment").directive('paymentMenu', menu);

};
