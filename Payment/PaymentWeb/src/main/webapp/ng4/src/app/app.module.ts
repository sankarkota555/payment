
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
// HttpModule is for Angular material icons http requests
import { HttpModule } from '@angular/http';
// FormsModule for froms
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
// HttpClientModule for Http client requests
import { HttpClientModule } from '@angular/common/http';

// BrowserAnimationsModule for angular material browser animations
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// UIRouterModule is  for state based routing
import { UIRouterModule } from '@uirouter/angular';

// Import for Angular material modules
import { MdMenuModule } from '@angular/material';
import { MdButtonModule } from '@angular/material';
import { MdInputModule } from '@angular/material';
import { MdCardModule } from '@angular/material';
import { MdIconModule } from '@angular/material';
import { MdAutocompleteModule } from '@angular/material';

// FlexLayoutModule is for Flex alignment
import { FlexLayoutModule } from '@angular/flex-layout';


// For interceptot supprot
import { HTTP_INTERCEPTORS } from '@angular/common/http';

// HTTP intcerceptors
import { PaymentHttpInterceptor } from './interceptor/PaymentHttpInterceptor';

// Our components
import { PaymentRoot } from './paymentRoot.component';
import { PaymentContent } from './content/PaymentContent.component';
import { PaymentMenuComponent } from './menu/PaymentMenu.component';
import { AutoCompleteItemComponent } from './content/commonComponents/auto-complete-item/AutoCompleteItem.component';
import { AutoCompleteCompanyComponent } from './content/commonComponents/auto-complete-company/AutoCompleteCompany.component';

// Import our states
import { PaymentStates } from './router-states/PaymentStates';

// Import our state components
import { HomeComponent } from './content/home/Home.component';
import { BillingComponent } from './content/billing/Billing.component';

// Our services
import { ItemsService } from './content/services/Items.service';
import { CookiesService } from './cookies/Cookies.service';


@NgModule({
  declarations: [
    PaymentRoot, PaymentMenuComponent, PaymentContent, AutoCompleteItemComponent, AutoCompleteCompanyComponent,
    HomeComponent, BillingComponent,
  ],
  imports: [
    BrowserModule, BrowserAnimationsModule, FormsModule, HttpModule, HttpClientModule,
    MdMenuModule, MdButtonModule, MdInputModule, MdCardModule, MdIconModule, MdAutocompleteModule,
    FlexLayoutModule,
    UIRouterModule.forRoot({ states: PaymentStates, useHash: true, otherwise: { state: 'billing' }, })
  ],
  providers: [
    ItemsService, CookiesService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: PaymentHttpInterceptor,
      multi: true,
    }
  ],
  bootstrap: [PaymentRoot]
})

export class AppModule {

}
