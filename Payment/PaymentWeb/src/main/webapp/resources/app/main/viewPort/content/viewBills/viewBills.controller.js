(function(){

	var viewBillsController = function($scope) {
        var me = this;
        // Could not pass controller as object to directive, so using scope
        $scope.showNews = false;
        
    };
    

    angular.module('payment').controller('viewBillsController', viewBillsController);
}());