"use strict";
{

    function userActivityService($http, $cookies, $window) {

        const me = this;

        /**
         * Call logout.
         */
        me.logout = function () {
            return $http({
                url: '/PaymentWeb/logout',
                method: "POST"
            });
        }

        /**
         * Clear cookes on logout success
         */
        me.logoutSuccess = function () {
            let allCookes = $cookies.getAll();
            $cookies.remove("XSRF-TOKEN", { path: "/" });
            $cookies.remove("XSRF-TOKEN", { path: "/PaymentWeb" });

            console.log("all cookes");
            console.log(allCookes);

            allCookes = $cookies.getAll();

            console.log("all cookes after delete");
            console.log(allCookes);

        };

        /**
         *  Redirect to login page.
         */
        me.navigateTologin = function () {
            $window.location.href = "home";
        }


    };

    angular.module('payment').service('userActivityService', userActivityService);

};
