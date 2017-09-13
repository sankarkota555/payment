import { ItemPriceDetails } from './ItemPriceDetails';
import { ItemCompany } from './ItemCompany';
import { Item } from './Item';

export class ItemDetails {
    id: number;
    itemCompany: ItemCompany;
    item: Item;
    itemPriceDetails: ItemPriceDetails[];
}
