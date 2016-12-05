(function(){

    var content = function() {
        return {
            template : require('./content.template.html'),
            controller : 'contentController',
            controllerAs : 'contentController'
        };
    };

    angular.module('payment').directive('content', content);
}());