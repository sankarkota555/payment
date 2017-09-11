import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class ItemsService {
    constructor(private http: Http) { }

    // find by item name.
    test() {

    }
    searchItems(itemName) {

        return [{ name: 'one', price: 1 }, { name: 'two', price: 2 }, { name: 'three', price: 3 }, { name: 'four', price: 4 }];
        //const formDataString = $.param({ itemName: itemName }, true);
        // return $http({
        //     headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8' },
        //     method: 'POST',
        //     url: 'findItems',
        //     data: formDataString
        // });
    }


}
