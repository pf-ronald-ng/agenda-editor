import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { AgendaItemDto } from '../dto/AgendaItemDto';
import { URLS } from '../utils/Urls';

@Injectable()
export class UpdateAgendaItemService{
	//public url:string;

	constructor(private _http: HttpClient){}

	updateAgendaItem(agendaId: number, agendaItemDto: AgendaItemDto):Observable<any>{
		let json = JSON.stringify(agendaItemDto);
        let params = "json="+json;
        let headers = new HttpHeaders().set('Content-Type','application/json');
         
        return this._http.put(URLS.HOST+URLS.API_URL+"/"+agendaId+"/item", json, {headers: headers});
	}
}