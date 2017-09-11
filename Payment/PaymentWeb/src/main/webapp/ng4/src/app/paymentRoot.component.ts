import { Component } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { MdIconRegistry } from '@angular/material';


@Component({
  // tslint:disable-next-line:component-selector
  selector: 'payment-root',
  templateUrl: './paymentRoot.template.html',
  styleUrls: ['./app.component.css'],

})
// tslint:disable-next-line:component-class-suffix
export class PaymentRoot {
  title = 'app';

  /**
   * Load and set names for Icons
   * @param mdIconRegistry icon import registry
   * @param sanitizer Dom snaitizer
   */
  constructor(mdIconRegistry: MdIconRegistry, sanitizer: DomSanitizer) {
    mdIconRegistry
      .addSvgIcon('person',
      sanitizer.bypassSecurityTrustResourceUrl('http://127.0.0.1:8086/PaymentWeb/pymt/javax.faces.resource/images/thumbs/person.svg'))
      .addSvgIconSetInNamespace('phone',
      sanitizer.bypassSecurityTrustResourceUrl('/javax.faces.resource/images/thumbs/phone.svg'))
      .addSvgIconSetInNamespace('phone',
      sanitizer.bypassSecurityTrustResourceUrl('/javax.faces.resource/images/thumbs/phone.svg'))
      .registerFontClassAlias('fontawesome', 'fa');

  }
}
