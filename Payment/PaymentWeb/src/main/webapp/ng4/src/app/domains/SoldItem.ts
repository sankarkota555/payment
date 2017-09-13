import { ItemPriceDetails } from './ItemPriceDetails';
import { Item } from './Item';

export class SoldItem {
    soldItemId: number;
    quantity: number;
    soldPrice: number;
    itemPriceDetails: ItemPriceDetails;
    selectedItem: Item;

    public constructor() {
        this.soldItemId = null;
        this.quantity = null;
        this.soldPrice = null;
        this.itemPriceDetails = null;
    }
}
