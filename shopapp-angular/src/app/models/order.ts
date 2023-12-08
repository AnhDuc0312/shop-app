import { OrderDetail } from "./order.detail";

export interface Order {
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
    tracking_number:string;
    payment_method: string;
    active: boolean;
    order_detail: OrderDetail;
}