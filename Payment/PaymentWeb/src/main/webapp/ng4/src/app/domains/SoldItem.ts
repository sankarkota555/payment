import { ItemPriceDetails } from './ItemPriceDetails';

export class SoldItem {
    soldItemId: number;
    quantity: number;
    soldPrice: number;
    itemPriceDetails: ItemPriceDetails;

    public constructor() {
        this.soldItemId = null;
        this.quantity = null;
        this.soldPrice = null;
        this.itemPriceDetails = null;
    }
}
