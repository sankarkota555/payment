import { PaymentConstants } from './../constants/PaymentConstants';
import { CookiesService } from './../cookies/Cookies.service';
import { Injectable } from '@angular/core';
import { HttpInterceptor, HttpEvent, HttpHandler, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/do';


@Injectable()
export class PaymentHttpInterceptor implements HttpInterceptor {

    constructor(private cookiesService: CookiesService) {

    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // console.log('request in inteceptor');
        // console.log(req);
        // console.log('cookie in intercetor: ' + this.cookiesService.getCookie(PaymentConstants.XSRF_COOKIE));
        const request = req.clone({
            setHeaders: {
                'X-CSRF-TOKEN': this.cookiesService.getCookie(PaymentConstants.XSRF_COOKIE)
            }
        });
        return next.handle(request).do(event => { console.log('event'), console.log(event) }, error => { console.log(error) });
    }

}