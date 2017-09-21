"use strict";
{

    function contentController($scope) {
        // Could not pass controller as object to directive, so using scope
        $scope.showNews = false;

    };


    angular.module('payment').controller('contentController', contentController);
};
