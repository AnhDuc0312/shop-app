import { OrderDetail } from "src/app/models/order.detail";

export interface OrderResponse {
    id: string;
    user_id: number;
    fullname: string;
    email: string;
    phong_number : string;
    address: string;
    note :string;
    order_date : Date;
    status : string;
    total_money : number;
    shipping_method :string;
    shipping_address : string;
    shipping_date: Date;
    payment_method: string;
    order_detail: OrderDetail;
}