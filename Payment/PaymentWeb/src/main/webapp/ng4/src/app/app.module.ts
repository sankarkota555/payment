
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

// Import for angular material browser animations
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// Import for state based routing
import { UIRouterModule } from '@uirouter/angular';

// Import our states
import { PaymentStates } from './router-states/PaymentStates';

// Import our state components
import { HomeComponent } from './content/home/Home.component';
import { BillingComponent } from './content/billing/Billing.component';

// Import for Angular material modules
import { MdMenuModule } from '@angular/material';
import { MdButtonModule } from '@angular/material';
import { MdInputModule } from '@angular/material';
import { MdCardModule } from '@angular/material';
import { MdIconModule } from '@angular/material';
import { MdAutocompleteModule } from '@angular/material';

// Import for Flex modules
import { FlexLayoutModule } from '@angular/flex-layout';

// Our components
import { PaymentRoot } from './paymentRoot.component';
import { PaymentContent } from './content/PaymentContent.component';
import { PaymentMenuComponent } from './menu/PaymentMenu.component';
import { AutoCompleteItem } from './content/commonComponents/auto-complete-item/AutoCompleteItem.component';



// Our services
import { ItemsService } from './content/services/Items.service';




@NgModule({
  declarations: [
    PaymentRoot, PaymentMenuComponent, PaymentContent, AutoCompleteItem,
    HomeComponent, BillingComponent,
  ],
  imports: [
    BrowserModule, BrowserAnimationsModule, HttpModule, FormsModule,
    MdMenuModule, MdButtonModule, MdInputModule, MdCardModule, MdIconModule, MdAutocompleteModule,
    FlexLayoutModule,
    UIRouterModule.forRoot({ states: PaymentStates, useHash: true, otherwise: { state: 'home' }, })
  ],
  providers: [
    ItemsService
  ],
  bootstrap: [PaymentRoot]
})

export class AppModule {

}
