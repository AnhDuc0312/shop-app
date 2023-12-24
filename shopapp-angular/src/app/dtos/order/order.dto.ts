export class OrderDTO {
    user_id : number;

    fullname: string;

    email: string;

    phone_number: string;

    address: string;

    status : string;

    note: string;

    total_money?: number;

    shipping_method:string;

    order_date?: Date;

    payment_method: string;

    coupon_code: string;

    cart_items: { product_id : number, quantity :number  } [];


    constructor(data : any) {
        this.user_id = data.user_id;
        this.address = data.address;
        this.fullname = data.fullname;
        this.email = data.email;
        this.phone_number = data.phone_number;
        this.status = data.status;
        this.note = data.note;
        this.total_money = data.total_money;
        this.shipping_method = data.shipping_method;
        this.payment_method = data.payment_method;
        this.coupon_code = data.coupon_code;
        this.cart_items = data.cart_item;
    }
    
    


}