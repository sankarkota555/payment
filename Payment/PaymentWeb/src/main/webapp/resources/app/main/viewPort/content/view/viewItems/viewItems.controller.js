"use strict";
{

    function viewItemsController($scope, itemsService, utilsService) {

        const me = this;

        me.availableItems;
        me.currentlyEditingItem = null;
        me.newItem = false;

        me.getAllItems = function () {
            console.log("getAllItems  from http");
            itemsService.getAvailableItems().then(function (data) {
                me.availableItems = data.data;
                /*for (let count = 0; count < 100; count++) {
                    me.availableItems.push({ "itemId": count + 100, "itemName": "item " + count + 100 });
                } */
            }, function (data) {
                // error loading items.
                console.log('error message');
                console.log(data);
                // data.data, data.status, data.headers, data.config
                utilsService.processError(data.data, data.config);
            });
        }

        me.editItem = function (itemPriceDetail) {
            angular.copy(itemPriceDetail, me.currentlyEditingItem);
        };

        me.deleteItem = function (itemPriceDetail) {
            //utilsService.
        };

        me.updateItem = function (parentParentIndex, parentIndex) {
            itemsService.updateItem(me.currentlyEditingItem).then(function (data) {
                for (const detail of me.availableItems[parentParentIndex].itemDetails[parentIndex].itemPriceDetails) {
                    if (detail.id == me.currentlyEditingItem.id) {
                        angular.copy(me.currentlyEditingItem, detail);
                    }
                }
                me.currentlyEditingItem = {};

            }, function (data) {
                // error loading items.
                console.log('error message');
                console.log(data);
                // data.data, data.status, data.headers, data.config
                utilsService.processError(data.data, data.config);
            });

        };

        me.cancelEditing = function () {
            me.currentlyEditingItem = {};
        };

        me.addNewItem = function () {
            me.newItem = true;
        }

        me.saveNewItem = function () {
            const newItem = utilsService.mapItemPriceDetails(me.currentlyEditingItem);
            itemsService.addItemDetails(newItem).then(function (data) {


            }, function (data) {

            });
        };

        me.getAllItems();



    };

    angular.module('payment').controller('viewItemsController', viewItemsController);

};