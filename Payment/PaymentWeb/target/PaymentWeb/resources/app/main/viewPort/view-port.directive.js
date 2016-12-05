(function(){

    var viewPort = function() {
        return {
            template: require('./view-port.template.html'),
            controller: 'viewPortController',
            controllerAs: 'viewPortController'
        }
    };

    angular.module('payment').directive('viewPort',viewPort);

}());