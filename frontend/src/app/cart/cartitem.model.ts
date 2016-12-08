
import { Item } from '../shopping/item.model'

export class CartItem extends Item {
    quantity: number;
    constructor(item?: Item) {
        super();
        this.id = item && item.id;
        this.description = item && item.description;
        this.cost = item && item.cost;
        this.name = item && item.name;
        this.image_src = item && item.image_src;
        this.averageReviewRate = item && item.averageReviewRate;
        
        this.quantity = 1;
    }

}

