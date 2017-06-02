
"use strict";
{

    function autoCompleteItemCompany(matchResultsFilter) {

        const directive = {};

        directive.restrict = 'E'; /* restrict this directive to Element */

        directive.scope = {
            item: '=',
            index: '=',
            onSelectCallbackFunction: '=', // Use '=' for method with arguments and for without args also. 
            //other wise you will face probelm that callback function not calling
        }

        directive.template = '<md-autocomplete md-no-cache="true" required md-selected-item="item.selectedCompany" ' +
            'md-items="company in item.selectedItem.itemDetails | matchResults: \'itemCompany.companyName\': item.searchCompanyText"' +
            'md-search-text="item.searchCompanyText" md-item-text="company.itemCompany.companyName" md-min-length="2" md-floating-label="Select Company"' +
            'md-select-on-match="false" md-menu-class="autocomplete-custom-template" md-selected-item-change="onSelectCallbackFunction(index,company.itemPriceDetails)" >' +
            '<md-item-template>' +
            ' <span class="auto-complete">{{company.itemCompany.companyName}}</span>' +
            '</md-item-template>' +
            '</md-autocomplete >';

        return directive;


    };

    angular.module('payment').directive('autoCompleteItemCompany', autoCompleteItemCompany);

};