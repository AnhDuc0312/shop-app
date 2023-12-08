package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "orders")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname" ,length = 100)
    private String fullName;

    @Column(name = "email" ,length = 100)
    private String email;

    @Column(name = "phone_number" , length = 100 ,nullable = false)
    private String phoneNumber;

    @Column(name = "address" , length = 200)
    private String address;

    @Column(name = "note" , length = 100)
    private String note;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "status")
    private String status;

    @Column(name = "total_money ")
    private Integer totalMoney;

    @Column(name = "shipping_method")
    private String shippingMethod;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "shipping_date")
    private LocalDate shippingDate;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "payment_method")
    private String paymentMethod;

//    @Column(name = "payment_status")
//    private String paymentStatus;

//    @Column(name = "payment_date")
//    private Date paymentDate; // Người dùng thanh toán mới cập nhập

    @Column(name = "active")
    private Boolean active;//Thuộc về admin

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
