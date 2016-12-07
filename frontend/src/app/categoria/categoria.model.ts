export interface ICategoria {
  id: number;
  description: string;
}

export class Categoria implements ICategoria {
  constructor(
    public id?: number,
    public description?: string
  ) {  }

}