import { ItemsService } from './../../services/Items.service';
import { Component, Input } from '@angular/core';

@Component({
    selector: 'auto-complete-item',
    templateUrl: './auto-complete-item.template.html'
})

export class AutoCompleteItem {

    @Input('item') item;
    
    @Input('index') index;

    constructor(private itemsService: ItemsService) {

    }

    itemSelected(event) {
        console.log('index: ' + this.index);
        console.log('item: ');
        console.log(this.item);

    }

    searchItem(itemName) {
        return this.itemsService.searchItems(itemName);
    }

    showItem(item): string {
        if (item) {
            return item.name;
        }
    }


}
