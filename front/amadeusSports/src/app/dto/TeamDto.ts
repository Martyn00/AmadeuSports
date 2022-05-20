export class TeamDto {
    id?: number;
    name: string = '';
    isFavorite: boolean = false
    constructor(id: number, name: string, isFavorite: boolean) {
        this.id = id;
        this.name = name;
        this.isFavorite = isFavorite;
    }
}