import { AgendaItemDto } from './AgendaItemDto';

export class AgendaDto{
	public id: number | null;
	public name: string | null;
	public agendaItems: AgendaItemDto[] | null

	constructor(){
		this.id = null;
		this.name = null;
		this.agendaItems = null;
	}
}
