"use strict";
{

    function menuController($scope, $location) {
        const me = this;
        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };

    };

    angular.module('payment').controller('menuController', menuController);
};
