export interface ITenant {
  id: number;
  name: string;
  scriptCreateUser: string;
}

export class Tenant implements ITenant {
  constructor(
    public id?: number,
    public name?: string,
    public scriptCreateUser?: string
    
  ) {  }

}