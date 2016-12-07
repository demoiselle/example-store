
import { Item } from '../shopping/item.model'

export class CartItem extends Item {
    amount: number;
    constructor(item?: Item) {
        super();
        this.id = item && item.id;
        this.descricao = item && item.descricao;
        this.valor = item && item.valor;
        this.nome = item && item.nome;
        this.image_src = item && item.image_src;
        this.averageReviewRate = item && item.averageReviewRate;
        
        this.amount = 1;
    }

}

