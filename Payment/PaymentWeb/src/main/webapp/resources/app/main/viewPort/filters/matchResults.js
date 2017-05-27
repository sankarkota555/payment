"use strict";
{
	function matchResults() {
		return function (array, attribute, query) {
			if (array && array.length > 0) {
				query = query.toLowerCase();
				//console.log('attribute');
				//console.log(attribute);
				let matchItems = [];
				if (attribute.indexOf(".") != -1) {
					const attributes = attribute.split(".");
					for (let index = 0; index < array.length; index++) {
						//console.log("array[index].attribute: " + array[index].attribute);
						//console.log("array[index][attributes[0]]: " + array[index][attributes[0]]);
						//console.log("array[index][attributes[0]][attributes[1]]: " + array[index][attributes[0]][attributes[1]]);
						if (array[index][attributes[0]][attributes[1]].toLowerCase().indexOf(query) === 0) {
							matchItems.push(array[index]);
						}
					}
				} else {
					for (let index = 0; index < array.length; index++) {
						if (array[index][attribute].toLowerCase().indexOf(query) === 0) {
							matchItems.push(array[index]);
						}
					}
				}
				//console.log('matchItems');
				//console.log(matchItems);
				return matchItems;
			};
		}
	};

	angular.module('payment').filter('matchResults', matchResults);

};
