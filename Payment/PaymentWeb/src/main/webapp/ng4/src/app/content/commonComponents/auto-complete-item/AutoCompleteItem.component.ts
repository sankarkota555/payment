import { CustomEventManagerService } from './../../services/CustomEventManager.service';
import { BillItem } from './../../../domains/BillItem';
import { Item } from './../../../domains/Item';
import { ItemsService } from './../../services/Items.service';
import { Component, EventEmitter, Input } from '@angular/core';
import { MdAutocompleteSelectedEvent } from '@angular/material';

@Component({
    selector: 'auto-complete-item',
    templateUrl: './auto-complete-item.template.html'
})

export class AutoCompleteItemComponent {

    @Input('item') item: BillItem;

    @Input('index') index: number;

    searchItemName: string;
    foundItems: Item[];

    constructor(private itemsService: ItemsService, private customEventManagerService: CustomEventManagerService) {

    }

    itemSelected(event: MdAutocompleteSelectedEvent): void {
        console.log('index: ' + this.index);
        console.log('item: ');
        this.item.selectedItem = event.option.value;
        this.item.resetSelectedCompany();
        console.log(this.item.selectedItem);
    }

    searchItem(itemName: string): void {
        if (typeof itemName === 'string') {
            // Emit event to clear 'item company search text' from input field 
            this.customEventManagerService.emitItemChangeEvent(this.index);
            this.itemsService.searchItems(itemName)
                .subscribe(data => {
                    this.foundItems = data;
                    this.resetSelectedValues();
                });

        } else {
            this.foundItems = [];
        }
    }

    showItem(item: Item): string {
        if (item) {
            return item.itemName;
        }
    }

    private resetSelectedValues(): void {
        this.item.selectedItem = null;
        this.item.resetSelectedCompany();
    }



}
