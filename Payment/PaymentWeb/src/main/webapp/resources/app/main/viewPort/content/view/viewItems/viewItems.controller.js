"use strict";
{

    function viewItemsController($scope, itemsService, utilsService) {

        const me = this;

        me.availableItems;
        me.currentlyEditingItem = null;
        me.newItem = false;
        me.foundCompanies = [];

        me.getAllItems = function () {
            console.log("getAllItems  from http");
            itemsService.getAvailableItems().then(function (data) {
                me.availableItems = data.data;
                /*for (let count = 0; count < 100; count++) {
                    me.availableItems.push({ "itemId": count + 100, "itemName": "item " + count + 100 });
                } */
            }, function (response) {
                // error loading items.
                processError(response);
            });
        }

        me.editItem = function (itemPriceDetail) {
            angular.copy(itemPriceDetail, me.currentlyEditingItem);
            me.newItem = false;
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

            }, function (response) {
                // error updating item
                processError(response);
            });

        };

        me.cancelEditing = function () {
            me.currentlyEditingItem = {};
        };

        me.addNewItem = function () {
            me.newItem = true;
            me.cancelEditing();
        }

        me.saveNewItem = function () {
            if (itemsService.validateNewItem(me.currentlyEditingItem)) {
                const newItem = utilsService.mapItemPriceDetails(me.currentlyEditingItem);
                itemsService.addItemDetails(newItem).then(function (data) {
                    utilsService.alertPopup("Item added successfully!", "Your page will be reloaded.", reloadPage);

                }, function (data) {
                    processError(response);
                });
            } else {
                utilsService.alertPopup('Duplicate Item ' + me.currentlyEditingItem.selectedItem.itemName + '!', me.currentlyEditingItem.selectedItem.itemName +
                    ' with company ' + me.currentlyEditingItem.selectedCompany.companyName + ' and capacity ' + me.currentlyEditingItem.capacity + ' already exists.', null);
            }

        };


        function reloadPage() {
            window.location.reload();
        }

        me.loadAllCompaniesLike = function (companyName) {
            itemsService.findCompaniesLike(companyName).then(function (data) {
                me.foundCompanies = data.data;

            }, function (response) {
                processError(response);
            });
        }

        me.getAllItems();

        function processError() {
            utilsService.processError(response);
        }

    };

    angular.module('payment').controller('viewItemsController', viewItemsController);

};