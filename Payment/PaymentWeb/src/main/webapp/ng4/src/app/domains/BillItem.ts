import { ItemPriceDetails } from './ItemPriceDetails';
import { Item } from './Item';

export class BillItem {

    quantity: number;
    soldPrice: number;
    selectedItem: Item;
    availableQuantity: number;
    actualPrice: number;
    detailsId: number;

    public constructor() {
        this.quantity = null;
        this.soldPrice = null;
        this.selectedItem = null;
        this.availableQuantity = null;
        this.actualPrice = null;
        this.detailsId = null;
    }
}
