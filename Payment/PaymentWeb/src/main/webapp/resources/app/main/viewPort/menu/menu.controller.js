"use strict";
{

    function menuController($scope, $location, $mdMenu) {
        const me = this;
        $scope.settingsOpened = false;
        $scope.isActive = function (viewLocation) {
            const path = $location.path();
            if (viewLocation === path) {
                return true;
            } else {
                viewLocation = viewLocation + "/";
                return (path.indexOf(viewLocation) === 0);
            }

        };

        $scope.toggleSettings = function () {
            if ($scope.settingsOpened) {
                $scope.onlyColseSettings();
            }
            else {
                $scope.settingsOpened = true;
            }

        };

        $scope.onlyColseSettings = function () {
            $mdMenu.cancel();
            $scope.settingsOpened = false;
        }

        /*
          $scope.$on('$mdMenuOpen', function (event, menu) {
              console.log('opening menu...', event, menu);
              // settingsOpened = true;
  
          });
          
          */
        $scope.$on('$mdMenuClose', function (event, menu) {
            //console.log('closing menu...', event, menu);
            $scope.settingsOpened = false;
        });

    };

    angular.module('payment').controller('menuController', menuController);
};
