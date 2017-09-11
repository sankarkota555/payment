import { SoldItem } from './../../domains/SoldItem';
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
        this.bill.customer.name = 'test';
        this.bill.soldItems.push(new SoldItem());
        this.bill.soldItems.push(new SoldItem());
        console.log('sold items ');
        console.log(this.bill.soldItems);

    }

    billObject() {
        console.log('bill object ');
        console.log(this.bill);
    }
    saveBill() {

    }





}
