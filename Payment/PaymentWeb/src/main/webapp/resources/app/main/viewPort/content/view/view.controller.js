"use strict";
{

    function viewController($scope, $location, $rootScope) {

        const me = this;

        me.activeName = null;

        $rootScope.$on('$stateChangeStart',
            function (event, toState, toParams, fromState, fromParams) {
                // do something
            })

        /**
         * Get current active tab by URL
         */
        function getCurrentAcrtiveItem() {
            const pathArr = $location.path().split('/');
            if (pathArr) {
                me.activeName = pathArr[pathArr.length - 1];
            }
        };

        $scope.$watch('$location.path()', function (value) {
            console.log(value);
        });

        getCurrentAcrtiveItem();



    };

    angular.module('payment').controller('viewController', viewController);

};
