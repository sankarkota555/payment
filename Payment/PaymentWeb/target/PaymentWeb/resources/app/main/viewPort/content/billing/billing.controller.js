(function(){

	var billingController = function($scope,billingService,itemsService) {
		
        var me = this;
        
        me.availableItems = [];
        
        for(var i=0; i< 10; i++){
        	var obj = {type:'type '+i, name: 'item name'+i};
        	me.availableItems.push(obj);
        }
               
        me.user = {items:[{}]};
        
        // increments for time picker
        me.hrstep = 1;
        me.minstep = 30;
        
        //function to add items to user
        me.addItem = function(){
        	me.user.items.push({});
        };
        
        //function for generating bill
        me.generateBill = function(){
        	console.log("user objet");
        	console.log(me.user);
        };
        
        //function to reset form details
        me.resetForm = function(){
            // reset user object
        	me.user = {name:"", phone:"", email:"", address:"", items:[{}],soldDate:null};
        };
        
        //function to delete item
        me.deleteItem = function(index){
        	// delete selected item
        	me.user.items.splice(index,1);
        };
        
  
        /**
         * Get all available items.
         */
        me.getAvalableItems = function(){
        	itemsService.getAvailableItems().then(function (data) {
                me.availableItems = data.data;
            }, function (data) {
                // error loading items.
               //me.load = false;
               //utilSvc.processError(data.data, data.status, data.headers, data.config);
            });
        };
       
        //get all available items on controller load.
        me.getAvalableItems();
        
  
    };
    

    angular.module('payment').controller('billingController', billingController);
    
}());