"use strict";
{

    function menuController($scope, $location) {
        const me = this;
        $scope.isActive = function (viewLocation) {
            const path = $location.path();
            if (viewLocation === path) {
                return true;
            } else {
                viewLocation = viewLocation + "/";
                return (path.indexOf(viewLocation) === 0);
            }

        };

    };

    angular.module('payment').controller('menuController', menuController);
};
