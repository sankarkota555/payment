(function(){

	var manageController = function($scope,itemsService) {
		
        var me = this;
        
        me.items =[];
        me.availableCompanies = [];
        
        me.addItem = function(item){
        	itemsService.addItem(item).then(function (data) {
                console.log("added success fully");
            }, function (data) {
                // error loading items.
           
            });
        }
        
        /**
         * Get all available companies.
         */
        me.getAvalableCompanies = function(){
        	itemsService.getAvailableCompanies().then(function (data) {
                me.availableCompanies = data.data;
            }, function (data) {
                // error loading items.
               //me.load = false;
               //utilSvc.processError(data.data, data.status, data.headers, data.config);
            });
        };	
        
        // get all companies
        me.getAvalableCompanies();
        
    };
    

    angular.module('payment').controller('manageController', manageController);
    
}());