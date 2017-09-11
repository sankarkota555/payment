import { SoldItem } from './SoldItem';
import { Customer } from './Customer';

export class Bill {
    billId: number;
    customer: Customer;
    soldItems: SoldItem[];
    netAmount: number;
    generatedDate: Date;

    constructor() {
        this.customer = new Customer();
        this.soldItems = [];
    }

}
