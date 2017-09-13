import { Injectable } from '@angular/core';

@Injectable()
export class CookiesService {

    constructor() { }
    getCookie(cookieName: string): string {
        const subLength = (cookieName.length + 1);
        return document.cookie.split(';')
            .map(cookie => cookie.trim())
            .filter(cooke => cooke.substring(0, subLength) === `${cookieName}=`)
            .map(cookie => cookie.substring(subLength))[0] || null;
    }
}