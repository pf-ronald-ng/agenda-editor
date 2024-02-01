import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { URLS } from '../utils/Urls';

@Injectable({providedIn: 'root',})
export class FindAgendasService{
	public url:string;

	constructor(private _http: HttpClient){
		this.url = URLS.FIND_AGENDAS;
	}

	findAgendas():Observable<any>{
        return this._http.get(this.url);
	}
}