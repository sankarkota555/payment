"use strict";
{

    function content() {
        return {
            restrict: 'E', // Resttrict to Element
            template: require('./content.template.html'),
            controller: 'contentController',
            controllerAs: 'contentController'
        };
    };

    angular.module('payment').directive('paymentContent', content);
};
