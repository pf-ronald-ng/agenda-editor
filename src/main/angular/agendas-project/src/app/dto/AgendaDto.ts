import { AgendaItemDto } from './AgendaItemDto';

export class AgendaDto{
	public name: string | null;
	public agendaItems: AgendaItemDto[] | null

	constructor(){
		this.name = null;
		this.agendaItems = null;
	}
}
