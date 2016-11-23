export interface IProduto {
  id: number;
  name: string;
  description: string;
}

export class Produto implements IProduto {
  constructor(
    public id?,
    public name?,
    public description?
    
  ) {  }

}