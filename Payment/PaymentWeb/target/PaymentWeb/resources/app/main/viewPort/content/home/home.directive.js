(function(){

    var home = function() {
        return {
            template: require('./home.template.html'),
            controller: 'menuController',
            controllerAs: 'menuController'
        }
    };

    angular.module('payment').directive('home',home);

}());