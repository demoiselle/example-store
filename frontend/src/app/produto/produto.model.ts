// export interface IProduto {
//   id: number;
//   name: string;
//   description: string;
// }
import { Categoria } from '../categoria/categoria.model';

export class Produto {
    id: number;
    description: string;
    cost: number;
    name: string;
    category: any;
    image_src: string;
    averageReviewRate:number;
    constructor(
        id?: number,
        description?: string,
        cost?: number,
        name?: string,
        category?:Categoria,
        image_src?: string,
        averageReviewRate?:number
    ) {}
}