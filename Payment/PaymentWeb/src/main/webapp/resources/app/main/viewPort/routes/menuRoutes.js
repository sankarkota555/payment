"use strict";
{

    function menuRoutes($stateProvider, $urlRouterProvider, $locationProvider) {

        const prefixUrl = 'javax.faces.resource/app/main/viewPort/content/';

        $urlRouterProvider.otherwise("/billing");

        $urlRouterProvider.when("/view", "/view/viewBills");

        $stateProvider.state('home', {
            url: '/',
        }).state('billing', {
            url: '/billing',
            templateUrl: prefixUrl + 'billing/billing.template.html',
            controller: 'billingController',
            controllerAs: 'billingController'
        }).state('view', {
            url: '/view',
            templateUrl: prefixUrl + 'view/view.template.html',
            controller: 'viewController',
            controllerAs: 'viewController'
        }).state('view.bills', {
            url: '/viewBills',
            templateUrl: prefixUrl + 'view/viewBills/viewBills.template.html',
            controller: 'viewBillsController',
            controllerAs: 'viewBillsController'
        }).state('view.companies', {
            url: '/viewCompanies',
            templateUrl: prefixUrl + 'view/viewBills/viewBills.template.html',
            controller: 'viewBillsController',
            controllerAs: 'viewBillsController'
        }).state('view.items', {
            url: '/viewItems',
            templateUrl: prefixUrl + 'view/viewItems/viewItems.template.html',
            controller: 'viewItemsController',
            controllerAs: 'viewItemsController'
        }).state('changePassword', {
            url: '/changePassword',
            templateUrl: prefixUrl + 'settings/changePassword/changePassword.template.html',
            controller: 'changePasswordController',
            controllerAs: 'changePasswordController'
        }).state('systems', {
            url: '/systems',
            templateUrl: prefixUrl + 'systems/viewBills.template.html',
            controller: 'itemsController',
            controllerAs: 'itemsController'
        }).state('manage.companies', {
            url: '/companies',
            templateUrl: prefixUrl + 'viewBills/viewBills.template.html',
            controller: 'itemsController',
            controllerAs: 'itemsController'
        });

    };
    angular.module("payment").config(menuRoutes);

};
