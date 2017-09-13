
import { Component } from '@angular/core';
import { ViewChild } from '@angular/core';
import { MdMenuTrigger } from '@angular/material';


@Component({
    // tslint:disable-next-line:component-selector
    selector: 'payment-menu',
    templateUrl: './paymentMenu.template.html'
})

export class PaymentMenuComponent {

    settingsOpened = false;

    @ViewChild(MdMenuTrigger) trigger: MdMenuTrigger;

    onlyColseSettings(): void {
        this.trigger.closeMenu();
        // this.settingsOpened = false;
    }

    constructor() {
        // this.settingsOpened = false;
    }

}
