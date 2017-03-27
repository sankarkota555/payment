"use strict";
{

    function menuRoutes($stateProvider, $urlRouterProvider) {

        const prefixUrl = 'javax.faces.resource/';

        $urlRouterProvider.otherwise("/billing");

        $stateProvider.state('home', {
            url: '/',
        }).state('billing', {
            url: '/billing',
            templateUrl: prefixUrl + 'app/main/viewPort/content/billing/billing.template.html',
            controller: 'billingController',
            controllerAs: 'billingController'
        }).state('viewBills', {
            url: '/viewBills',
            templateUrl: prefixUrl + 'app/main/viewPort/content/viewBills/viewBills.template.html',
            controller: 'viewBillsController',
            controllerAs: 'viewBillsController'
        }).state('manage', {
            url: '/manage',
            templateUrl: prefixUrl + 'app/main/viewPort/content/manage/manage.template.html',
            controller: 'manageController',
            controllerAs: 'manageController'
        }).state('manage.items', {
            url: '/items',
            templateUrl: prefixUrl + 'app/main/viewPort/content/viewBills/viewBills.template.html',
            controller: 'itemsController',
            controllerAs: 'itemsController'
        }).state('manage.companies', {
            url: '/companies',
            templateUrl: prefixUrl + 'app/main/viewPort/content/viewBills/viewBills.template.html',
            controller: 'itemsController',
            controllerAs: 'itemsController'
        });

    };
    angular.module("payment").config(menuRoutes);

};
