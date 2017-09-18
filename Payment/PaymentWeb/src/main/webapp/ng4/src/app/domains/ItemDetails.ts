import { ItemPriceDetails } from './ItemPriceDetails';
import { ItemCompany } from './ItemCompany';
import { Item } from './Item';

export class ItemDetails {
    id: number;
    itemCompany: ItemCompany;
    item: Item;
    itemPriceDetails: ItemPriceDetails[];

    constructor() {
        this.id = null;
        this.itemCompany = null;
        this.item = null;
        this.itemPriceDetails = [];
    }
}
