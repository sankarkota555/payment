import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import $ from 'jquery';

@Injectable()
export class ItemsService {

    constructor(private http: HttpClient) { }

    searchItems(searchText: string): Observable<object> {
        const paramString = $.param({ itemName: searchText }, true);
        const formHeader = new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8' });
        return this.http.post('findItems', paramString, { headers: formHeader })
            .catch(error => this.processError(error));
    }

    processError(error: Response) {
        console.log('Error during http: ');
        console.log(error);
        return Observable.throw(error || error);

    }


}
