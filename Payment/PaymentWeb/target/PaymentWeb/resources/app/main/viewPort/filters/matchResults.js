(function() {

    var matchResults = function() {
		 return function(array, attribute, query) {
			 console.log('array');
	         console.log(array);
	         console.log('attribute');
	        console.log(attribute);
	        
			 var matchItems = [];
	        	for(var index=0;index < array.length; index++ ){
	        		if(array[index][attribute].indexOf(query) === 0){
	        			matchItems.push(array[index]);
	        		}
	        	}
	        	console.log('matchItems');
	        	console.log(matchItems);
	            return matchItems;
		 };
    };

    angular.module('payment').filter('matchResults',matchResults);

}());