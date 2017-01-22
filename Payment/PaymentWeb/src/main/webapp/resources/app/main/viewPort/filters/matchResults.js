(function() {

    var matchResults = function() {
		 return function(array, attribute, query) {
			
	        console.log('attribute');
	        console.log(attribute);
			 var matchItems = [];
			 if(attribute.indexOf(".") != -1){
		        	var attributes = attribute.split(".");
	                for(var index=0;index < array.length; index++ ){
	                	console.log("array[index].attribute: " +array[index].attribute);
		        		console.log("array[index][attributes[0]]: " +array[index][attributes[0]]);
		        		console.log("array[index][attributes[0]][attributes[1]]: " +array[index][attributes[0]][attributes[1]]);
		        		if(array[index][attributes[0]][attributes[1]].indexOf(query) === 0){
		        			matchItems.push(array[index]);
		        		}
	                }
			 }else{
				 for(var index=0;index < array.length; index++ ){
		        		if(array[index][attribute].indexOf(query) === 0){
		        			matchItems.push(array[index]);
		        		}
		        	}
			 }
			 
	        	
	        	console.log('matchItems');
	        	console.log(matchItems);
	            return matchItems;
		 };
    };

    angular.module('payment').filter('matchResults',matchResults);

}());