"use strict";
{

    function menuRoutes($stateProvider, $urlServiceProvider) {

        const prefixUrl = 'javax.faces.resource/app/main/viewPort/content/';

        $urlServiceProvider.rules.initial({ state: 'billing' });

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
            controllerAs: 'viewController',
            redirectTo: 'view.items'
        }).state('view.bills', {
            url: '/viewBills',
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
            templateUrl: prefixUrl + 'systems/systems.template.html',
            controller: 'systemsController',
            controllerAs: 'systemsController'
        });

    };
    angular.module("payment").config(menuRoutes);

};
