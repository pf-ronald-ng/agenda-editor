import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { URLS } from '../utils/Urls';
import { AgendaDto } from '../dto/AgendaDto';

@Injectable()
export class SaveAgendasService{
	//public url:string;

	constructor(private _http: HttpClient){}

    saveAgenda(agendaDto: AgendaDto):Observable<any>{
		let json = JSON.stringify(agendaDto);
        let params = "json="+json;
        let headers = new HttpHeaders().set('Content-Type','application/json');
         
        return this._http.post(URLS.HOST+URLS.API_URL, json, {headers: headers});
	}
}