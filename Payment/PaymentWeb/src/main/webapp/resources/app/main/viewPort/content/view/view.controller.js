"use strict";
{

    function viewController($location, $transitions) {

        const me = this;

        me.activeName = null;

        $transitions.onSuccess({}, function (transitions) {
            me.activeName = transitions.$to().url.pattern.replace("/", "");
        });

        /**
         * Get current active tab by URL
         */
        function getCurrentAcrtiveItem() {
            const pathArr = $location.path().split('/');
            if (pathArr) {
                me.activeName = pathArr[pathArr.length - 1];
            }
        };

        getCurrentAcrtiveItem();

    };

    angular.module('payment').controller('viewController', viewController);

};
