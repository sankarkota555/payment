
"use strict";
{

    function systemStatusColor() {

        const directive = {};

        // time for yellow  is 10 minutes
        const tenMinutes = 10 * 60 * 1000;

        directive.restrict = 'A'; /* restrict this directive to Attribute */

        directive.scope = {
            systemStatusColor: '=',
        }

        directive.link = function (scope, element, attrs) {
            if (scope.systemStatusColor) {
                let currentTime = new Date().getTime();
                if (scope.systemStatusColor < currentTime) {
                    //Red flag
                    element.addClass('should-out');
                } else if ((scope.systemStatusColor - currentTime) < tenMinutes) {
                    //yellow flag
                    element.addClass('about-out');
                } else {
                    //Green flag
                    element.addClass('should-in');
                }
            }



        };

        return directive;


    };

    angular.module('payment').directive('systemStatusColor', systemStatusColor);

};