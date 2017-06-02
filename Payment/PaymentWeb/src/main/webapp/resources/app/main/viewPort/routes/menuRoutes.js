"use strict";
{

    function menuRoutes($stateProvider, $urlRouterProvider, $locationProvider) {

        const prefixUrl = 'javax.faces.resource/';

        $urlRouterProvider.otherwise("/billing");

        $urlRouterProvider.when("/view", "/view/viewBills");

        $stateProvider.state('home', {
            url: '/',
        }).state('billing', {
            url: '/billing',
            templateUrl: prefixUrl + 'app/main/viewPort/content/billing/billing.template.html',
            controller: 'billingController',
            controllerAs: 'billingController'
        }).state('view', {
            url: '/view',
            templateUrl: prefixUrl + 'app/main/viewPort/content/view/view.template.html',
            controller: 'viewController',
            controllerAs: 'viewController'
        }).state('view.bills', {
            url: '/viewBills',
            templateUrl: prefixUrl + 'app/main/viewPort/content/view/viewBills/viewBills.template.html',
            controller: 'viewBillsController',
            controllerAs: 'viewBillsController'
        }).state('view.companies', {
            url: '/viewCompanies',
            templateUrl: prefixUrl + 'app/main/viewPort/content/view/viewBills/viewBills.template.html',
            controller: 'viewBillsController',
            controllerAs: 'viewBillsController'
        }).state('view.items', {
            url: '/viewItems',
            templateUrl: prefixUrl + 'app/main/viewPort/content/view/viewItems/viewItems.template.html',
            controller: 'viewItemsController',
            controllerAs: 'viewItemsController'
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
