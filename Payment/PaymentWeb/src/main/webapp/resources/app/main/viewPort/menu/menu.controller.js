"use strict";
{

    function menuController($scope, $location, $mdMenu) {
        const me = this;
        let settingsOpened = false;
        $scope.isActive = function (viewLocation) {
            const path = $location.path();
            if (viewLocation === path) {
                return true;
            } else {
                viewLocation = viewLocation + "/";
                return (path.indexOf(viewLocation) === 0);
            }

        };

        $scope.closeSettings = function () {
            if (settingsOpened) {
                $mdMenu.hide();
                settingsOpened = false;
            } else {
                settingsOpened = true;
            }

        };

        $scope.$on('$mdMenuOpen', function (event, menu) {
            console.log('opening menu...', event, menu);
            // settingsOpened = true;

        });
        $scope.$on('$mdMenuClose', function (event, menu) {
            console.log('closing menu...', event, menu);
            settingsOpened = false;
        });

    };

    angular.module('payment').controller('menuController', menuController);
};
