import { BillItem } from './BillItem';
import { Customer } from './Customer';

export class Bill {
    billId: number;
    customer: Customer;
    soldItems: BillItem[];
    netAmount: number;
    generatedDate: Date;

    constructor() {
        this.customer = new Customer();
        this.soldItems = [];
    }

}
