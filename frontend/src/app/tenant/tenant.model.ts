export class Tenant {

  id: number;
  name: string;
  constructor(
    id?: number,
    name?: string
  ) {  }

}

export class Cupom {

  id: number;
  name: string;
  script: string;
  startDate: string;
  stopDate: string;
  sistemaId: string;

  constructor(
    id?: number,
    name?: string,
    script?: string,
    startDate?: string,
    stopDate?: string,
    sistemaId?: string
  ) {  }
  
}