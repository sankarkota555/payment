"use strict";
{

    function viewItemsController($scope, itemsService, utilsService) {

        const me = this;

        me.availableItems;
        me.currentlyEditingItem = null;
        me.newItem = false;
        me.foundCompanies = [];
        const itemClassName = "ItemDTO";

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
            utilsService.warning("Functinality not available");
        };

        me.updateItem = function () {
            itemsService.updateItem(me.currentlyEditingItem).then(function (data) {
                me.cancelEditing();

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
                    utilsService.success("Item added successfully!");
                    me.cancelNewItem();

                }, function (response) {
                    processError(response);
                });
            } else {
                utilsService.alertPopup('Duplicate Item ' + me.currentlyEditingItem.selectedItem.itemName + '!', me.currentlyEditingItem.selectedItem.itemName +
                    ' with company ' + me.currentlyEditingItem.selectedCompany.companyName + ' and capacity ' + me.currentlyEditingItem.capacity + ' already exists.', null);
            }

        };

        me.loadAllCompaniesLike = function (companyName) {
            itemsService.findCompaniesLike(companyName).then(function (data) {
                me.foundCompanies = data.data;

            }, function (response) {
                processError(response);
            });
        };

        me.cancelNewItem = function () {
            me.newItem = false;
            me.currentlyEditingItem = {};
            $scope.addNewItemForm.$setPristine();
            $scope.addNewItemForm.$setUntouched();
        };

        $scope.$on('socketUpdate', function (event, data) {
            if (utilsService.validateSocketUpdate(itemClassName, data)) {
                let object = data.value;
                let itemIndex = utilsService.findIndex(me.availableItems, object, 'itemId');
                if (itemIndex != -1) {
                    let detailsIndex = utilsService.findIndex(me.availableItems[itemIndex].itemDetails, object.itemDetails[0], 'id');
                    if (detailsIndex != -1) {
                        let itemPriceDetailsIndex = utilsService.findIndex(me.availableItems[itemIndex].itemDetails[detailsIndex].itemPriceDetails, object.itemDetails[0].itemPriceDetails[0], 'id');
                        if (itemPriceDetailsIndex != -1) {
                            me.availableItems[itemIndex].itemDetails[detailsIndex].itemPriceDetails.splice(itemPriceDetailsIndex, 1, object.itemDetails[0].itemPriceDetails[0]);
                        } else {
                            me.availableItems[itemIndex].itemDetails[detailsIndex].itemPriceDetails.push(object.itemDetails[0].itemPriceDetails[0]);
                        }
                    } else {
                        me.availableItems[itemIndex].itemDetails.push(object.itemDetails[0]);
                    }
                } else {
                    me.availableItems.push(object);
                }
                me.availableItems;
            } else {
                console.log("socket update is not belogs here: " + data.class);
            }

        });



        me.getAllItems();

        function processError(response) {
            utilsService.processError(response);
        }

    };

    angular.module('payment').controller('viewItemsController', viewItemsController);

};