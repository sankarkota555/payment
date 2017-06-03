
"use strict";
{

    function autoCompleteItem(itemsService, utilsService) {

        const directive = {};

        directive.restrict = 'E'; /* restrict this directive to Element */

        directive.scope = {
            item: "=",
        }

        directive.template = '<md-autocomplete md-no-cache="true" required md-search-text-change="searchItems(item.searchItemText)" md-selected-item="item.selectedItem"' +
            ' md-items="item in foundItems" md-search-text="item.searchItemText" md-item-text="item.itemName"' +
            ' md-dropdown-position="bottom" md-min-length="2" md-floating-label="Select Item" md-select-on-match="false" md-menu-class="autocomplete-custom-template">' +
            '<md-item-template>' +
            '  <span class="item-title capital-text" md-highlight-text="item.searchItemText">{{item.itemName}} </span>' +
            '</md-item-template>' +
            '</md-autocomplete>';

        directive.link = function (scope, element) {
            scope.searchItems = function (itemName) {
                itemsService.searchItems(itemName).then(function (response) {
                    console.log("successfully searched item " + itemName);
                    console.log(response.data);
                    scope.foundItems = response.data;
                    console.log("items st to variables");
                },
                    function (response) {
                        console.log("error");
                        //show error message opoup
                        utilsService.processError(response);
                    });;
            };

        };

        return directive;

    };

    angular.module('payment').directive('autoCompleteItem', autoCompleteItem);

};