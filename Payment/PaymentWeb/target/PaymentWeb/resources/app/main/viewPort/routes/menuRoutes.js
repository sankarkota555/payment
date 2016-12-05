"use strict";
(function(){

    var menuRoutes = function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise("/billing");

        $stateProvider.state('home', {
            url: '/',
        }).state('billing', {
            url: '/billing',
            templateUrl: 'res/app/main/viewPort/content/billing/billing.template.html',
            controller: 'billingController',
            controllerAs: 'billingController'
        }).state('viewBills', {
            url: '/viewBills',
            templateUrl: 'res/app/main/viewPort/content/viewBills/viewBills.template.html',
            controller: 'viewBillsController',
            controllerAs: 'viewBillsController'
        }).state('items', {
            url: '/items',
            templateUrl: 'res/app/main/viewPort/content/items/items.template.html',
            controller: 'itemsController',
            controllerAs: 'itemsController'
        }).state('gallery', {
            url: '/gallery',
            templateUrl: 'javax.faces.resource/app/main/viewPort/content/gallery/gallery-directive.template.html',
            controller: 'galleryController',
            controllerAs: 'galleryController'
        }).state('contact', {
            url: '/contact',
            templateUrl: 'javax.faces.resource/app/main/viewPort/content/contact/contact-directive.template.html',
            controller: 'contactController',
            controllerAs: 'contactController'
        });

    };
    angular.module("payment").config(menuRoutes);
   
}());