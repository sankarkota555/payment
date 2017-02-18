"use strict";
{

    function userActivityService($http) {

        const me = this;

        me.logout = function(){
             return $http({
                url: '/PaymentWeb/logout',
                method: "POST"
            });
        }

        me.logoutGET = function(){
             return $http({
                url: '/PaymentWeb/logout',
                method: "GET"
            });
        }

    };

    angular.module('payment').service('userActivityService', userActivityService);

};
