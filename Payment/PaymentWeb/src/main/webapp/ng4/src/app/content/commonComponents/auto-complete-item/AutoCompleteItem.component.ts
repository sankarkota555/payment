import { BillItem } from './../../../domains/BillItem';
import { Item } from './../../../domains/Item';
import { ItemsService } from './../../services/Items.service';
import { Component, Input } from '@angular/core';
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

    constructor(private itemsService: ItemsService) {

    }

    itemSelected(event: MdAutocompleteSelectedEvent): void {
        console.log('index: ' + this.index);
        console.log('item: ');
        this.item.selectedItem = event.option.value;
        console.log(this.item.selectedItem);
    }

    searchItem(itemName: string): void {
        if (typeof itemName === 'string') {

            this.itemsService.searchItems(itemName)
                .subscribe(data => {
                    this.foundItems = data;
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


}
