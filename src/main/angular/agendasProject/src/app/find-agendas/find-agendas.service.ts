import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { URLS } from '../utils/Urls';
import { AgendaItemDto } from '../dto/AgendaItemDto';

@Injectable({providedIn: 'root',})
export class FindAgendasService{
	public url:string;

	constructor(private _http: HttpClient){
		this.url = URLS.HOST + URLS.API_URL;
	}

	findAgendas(page: number, size: number):Observable<any>{
		const params = { page: page.toString(), size: size.toString() };
        return this._http.get(this.url, { params });
	}

	deleteAgenda(agendaId: number):Observable<any>{
        let headers = new HttpHeaders().set('Content-Type','application/json');
         
        return this._http.delete(URLS.HOST+URLS.API_URL+"/"+agendaId, {headers: headers});
	}

	deleteAgendaItem(agendaItemId: number):Observable<any>{
        let headers = new HttpHeaders().set('Content-Type','application/json');
         
        return this._http.delete(URLS.HOST+URLS.API_URL+"/item/"+agendaItemId, {headers: headers});
	}
}