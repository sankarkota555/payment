"use strict";
{

    function content() {
        return {
            template: require('./content.template.html'),
            controller: 'contentController',
            controllerAs: 'contentController'
        };
    };

    angular.module('payment').directive('content', content);
};
