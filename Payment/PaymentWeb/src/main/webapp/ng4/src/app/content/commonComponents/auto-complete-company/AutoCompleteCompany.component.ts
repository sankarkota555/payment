import { BillItem } from './../../../domains/BillItem';
import { ItemDetails } from './../../../domains/ItemDetails';
import { Component, Input } from '@angular/core';
import { MdAutocompleteSelectedEvent } from '@angular/material'

@Component({
    selector: 'auto-complete-company',
    templateUrl: './auto-complete-company.template.html'
})

export class AutoCompleteCompanyComponent {

    @Input('item') item: BillItem;
    @Input('index') index: number;

    searchCompanyName: string;
    foundCompanies: ItemDetails[];

    constructor() { }

    searchCompany(companyName: string): void {
        if (typeof companyName === 'string') {
            this.foundCompanies = this.item.selectedItem.itemDetails.filter(itemDetail => itemDetail.itemCompany.companyName.indexOf(companyName) == 0);
        } else {
            this.foundCompanies = [];
        }
        console.log('this.foundCompanies');
        console.log(this.foundCompanies);
    }

    companySelected(event: MdAutocompleteSelectedEvent): void {
        this.item.selectedCompany = event.option.value;
        let details = event.option.value.itemPriceDetails;
        if (details.length === 1) {
            this.item.availableQuantity = details[0].quantity;
            this.item.actualPrice = details[0].price;
            this.item.itemPriceDetailsId = details[0].id;
        }

    }


    showCompany(itemDetail: ItemDetails): string {
        if (itemDetail) {
            return itemDetail.itemCompany.companyName;
        }
        return null;
    }

}
