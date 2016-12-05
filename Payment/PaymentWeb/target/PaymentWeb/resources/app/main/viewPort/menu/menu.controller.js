(function(){

    var menuController = function($scope, $location) {
        var me = this;
      $scope.isActive = function (viewLocation) {
    	  return viewLocation === $location.path();
      };
        
    };
    
    angular.module('payment').controller('menuController',menuController);
}());