import { BillingComponent } from './../content/billing/Billing.component';
import { HomeComponent } from './../content/home/Home.component';

const HomeState = {
    name: 'home',
    url: '/home',
    component: HomeComponent

};

const BillingState = {
    name: 'billing',
    url: '/billing',
    component: BillingComponent

};

/**
 * Export all states as an array.
 */
export const PaymentStates = [
    HomeState, BillingState

];


