"use strict";
{
    const module = angular.module('payment');

    function csrfInterceptor($cookies) {

        return {

            request: function (config) {

                var token = $cookies.get('XSRF-TOKEN');
                console.log("all cookies: ");
                console.log($cookies.getAll());
                if (token) {
                    config.headers['X-CSRF-TOKEN'] = token;
                }
                return config;
            },

            response: function (responseConfig) {

                return responseConfig;
            }
        }

    }


    module.factory('csrfInterceptor', csrfInterceptor);


    /**
     * COnfigure interceptor
     */
    module.config(['$httpProvider', function ($httpProvider) {

        $httpProvider.defaults.withCredentials = true;
        $httpProvider.interceptors.push('csrfInterceptor');

    }]);

}
