// export interface Item {
//     id: number;
//     description: string;
//     price: number;
//     name: string;
//     manufacturer: string;
//     image_src?: string;
//     averageReviewRate?:number;
// }

export class Item {
    id: number;
    descricao: string;
    valor: number;
    nome: string;
    image_src: string;
    averageReviewRate:number;
    constructor(
        id?: number,
        descricao?: string,
        valor?: number,
        nome?: string,
        image_src?: string,
        averageReviewRate?:number
    ) {}
}
