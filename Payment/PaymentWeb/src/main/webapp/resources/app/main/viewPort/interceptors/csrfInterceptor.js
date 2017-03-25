"use strict";
{
    const module = angular.module('payment');

    function csrfInterceptor($cookies, $injector) {

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

                if (responseConfig.config.url.indexOf("/logout") == -1 && typeof responseConfig.data === 'string' && responseConfig.data.indexOf("<html>") != -1) {
                    let userActivityService = $injector.get("userActivityService");
                    let utilsService = $injector.get("utilsService");
                    utilsService.alertPopup("Session Expired!!", "Please Login...", userActivityService.navigateTologin);
                    return null;
                }
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
