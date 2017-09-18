import { ItemDetails } from './ItemDetails';
import { ItemPriceDetails } from './ItemPriceDetails';
import { Item } from './Item';

export class BillItem {

    quantity: number;
    soldPrice: number;
    selectedItem: Item;
    selectedCompany: ItemDetails;
    availableQuantity: number;
    actualPrice: number;
    itemPriceDetailsId: number;

    public constructor() {
        this.quantity = null;
        this.soldPrice = null;
        this.selectedItem = null;
        this.selectedCompany = new ItemDetails();
        this.availableQuantity = null;
        this.actualPrice = null;
        this.itemPriceDetailsId = null;
    }
}
