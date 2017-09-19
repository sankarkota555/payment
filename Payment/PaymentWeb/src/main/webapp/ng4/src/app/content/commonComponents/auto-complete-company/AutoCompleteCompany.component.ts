import { CustomEventManagerService } from './../../services/CustomEventManager.service';
import { BillItem } from './../../../domains/BillItem';
import { ItemDetails } from './../../../domains/ItemDetails';
import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { MdAutocompleteSelectedEvent } from '@angular/material'

@Component({
    selector: 'auto-complete-company',
    templateUrl: './auto-complete-company.template.html'
})

export class AutoCompleteCompanyComponent implements OnInit, OnDestroy {

    @Input('item') item: BillItem;
    @Input('index') index: number;

    searchCompanyName: string;
    foundCompanies: ItemDetails[];

    constructor(private customEventManagerService: CustomEventManagerService) {

    }

    searchCompany(companyName: string): void {
        if (typeof companyName === 'string') {
            this.foundCompanies = this.item.selectedItem.itemDetails.filter(itemDetail => itemDetail.itemCompany.companyName.indexOf(companyName) == 0);
            this.item.resetSelectedCompany();
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

    ngOnInit() {
        this.customEventManagerService.getItemChangeEvent().subscribe(event => {
            if (this.index === event) {
                this.searchCompanyName = null;
            }
        });
    }

    ngOnDestroy() {
        this.customEventManagerService.getItemChangeEvent().unsubscribe();
    }


}


