"use strict";
/**
 *  Directive for displaying twitter user details in the middle panel.
 */
angular.module('payment')
    .directive('manageItems', function() {
    	return {
    		controller: 'itemsController'
    	};
    });