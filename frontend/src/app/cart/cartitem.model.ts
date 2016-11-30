
import { Item } from '../shopping/item.model'

export class CartItem extends Item {
    amount: number;
    constructor(item?: Item) {
        super();
        this.id = item && item.id;
        this.description = item && item.description;
        this.price = item && item.price;
        this.name = item && item.name;
        this.image_src = item && item.image_src;
        this.averageReviewRate = item && item.averageReviewRate;
        
        this.amount = 1;
    }

}

