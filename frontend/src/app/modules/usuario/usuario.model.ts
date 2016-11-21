export interface IUsuario {
  id: number;
  nome: string;
  perfil: string;
  email: string;
  cpf: string;
  fone: string;
  senha: string;
}

export class Usuario implements IUsuario {
  constructor(
    public id?: number,
    public nome?: string,
    public perfil?: string,
    public email?: string,
    public cpf?: string,
    public fone?: string,
    public senha?: string

  ) {  }

}