import { ItemDetails } from './../../domains/ItemDetails';
import { Item } from './../../domains/Item';
import { BillItem } from './../../domains/BillItem';
import { Component } from '@angular/core';
import { Customer } from './../../domains/Customer';
import { Bill } from './../../domains/Bill';


@Component({
    selector: 'billing-component',
    templateUrl: './billing.template.html'
})

export class BillingComponent {

    bill: Bill;
    filteredStates = ['one', 'two', 'three'];


    constructor() {
        this.bill = new Bill();
        this.bill.soldItems.push(new BillItem());

    }

    billObject() {
        console.log('bill object ');
        console.log(this.bill);
    }

    checkCapacityEnableStatus(itemDetails: ItemDetails): boolean {
        if (itemDetails) {
            for (const object of itemDetails.itemPriceDetails) {
                if (object.capacity) {
                    return false;
                }
            }
        }
        return true;
    }
    saveBill() {

    }





}
