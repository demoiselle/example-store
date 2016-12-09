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
        category?:any,
        image_src?: string,
        averageReviewRate?:number
    ) {}
}
