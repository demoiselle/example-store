// export interface IUsuario {
//   id: number;
//   name: string;
//   role: string;
//   email: string;
//   cpf: string;
//   fone: string;
//   password: string;
// }

export class Usuario{
  id: number;
  name: string;
  role: string;
  email: string;
  cpf: string;
  fone: string;
  password: string;
  constructor(
    id?: number,
    name?: string,
    role?: string,
    email?: string,
    cpf?: string,
    fone?: string,
    password?: string

  ) {  }

}