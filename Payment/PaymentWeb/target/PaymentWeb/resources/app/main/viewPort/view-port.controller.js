(function () {

    var viewPortController = function ($scope) {
        var me = this;

        me.appStart = "RapidNetSports";

       /* $scope.$watch(function(){ return menuService.menuBackground;},function(newValue,oldValue) {
            me.menuBacground = newValue;
        });*/
    


    };

    angular.module("payment").controller('viewPortController', viewPortController);

} ());