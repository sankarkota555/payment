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
      sanitizer.bypassSecurityTrustResourceUrl('javax.faces.resource/images/thumbs/person.svg'))
      .addSvgIcon('phone',
      sanitizer.bypassSecurityTrustResourceUrl('javax.faces.resource/images/thumbs/phone.svg'))
      .addSvgIcon('cancel',
      sanitizer.bypassSecurityTrustResourceUrl('javax.faces.resource/images/thumbs/cancel.svg'))
      .addSvgIcon('email',
      sanitizer.bypassSecurityTrustResourceUrl('javax.faces.resource/images/thumbs/email.svg'))
      .addSvgIcon('menu',
      sanitizer.bypassSecurityTrustResourceUrl('javax.faces.resource/images/thumbs/menu.svg'))
      .addSvgIcon('multiply',
      sanitizer.bypassSecurityTrustResourceUrl('javax.faces.resource/images/thumbs/multiply.svg'))
      .addSvgIcon('verification-mark',
      sanitizer.bypassSecurityTrustResourceUrl('javax.faces.resource/images/thumbs/verification-mark.svg'))
      .addSvgIcon('writer',
      sanitizer.bypassSecurityTrustResourceUrl('javax.faces.resource/images/thumbs/writer.svg'))
      .addSvgIcon('home',
      sanitizer.bypassSecurityTrustResourceUrl('javax.faces.resource/images/thumbs/home.svg'))
      .addSvgIcon('phone',
      sanitizer.bypassSecurityTrustResourceUrl('javax.faces.resource/images/thumbs/phone.svg'))

      .registerFontClassAlias('fontawesome', 'fa');

  }
}
