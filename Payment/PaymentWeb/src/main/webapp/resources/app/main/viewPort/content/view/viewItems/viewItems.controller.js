"use strict";
{

    function viewItemsController($scope, itemsService) {

        const me = this;

        me.availableItems;

        me.getAllItems = function () {
            console.log("getAllItems  from http");
            itemsService.getAvailableItems().then(function (data) {
                me.availableItems = data.data;
            }, function (data) {
                // error loading items.
                console.log('error message');
                console.log(data);
                // data.data, data.status, data.headers, data.config
                utilsService.processError(data.data, data.config);
            });
        }

        me.getAllItems();



    };

    angular.module('payment').controller('viewItemsController', viewItemsController);

};