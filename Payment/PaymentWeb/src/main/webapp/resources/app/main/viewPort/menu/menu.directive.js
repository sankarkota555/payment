"use strict";
{

    function menu() {
        return {
            template: require('./menu.template.html'),
            controller: 'menuController',
            controllerAs: 'menuController'
        }
    };

    angular.module("payment").directive('menu', menu);

};
