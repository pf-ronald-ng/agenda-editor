export class AgendaItemDto{

    public id: number | null;
    public itemOrder: number | null;
    public phase: string | null;
    public content: string | null;
    public objectives: string | null;
    public duration: number | null;
    public creditable: boolean;

	constructor(){
		this.id = null;
        this.itemOrder = null;
		this.phase = null;
		this.content = null;
        this.objectives = null;
        this.duration = null;
        this.creditable = false;
	}
}
