package com.project.shopapp.services;

import com.project.shopapp.dtos.CartItemDTO;
import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.dtos.OrderWithDetailsDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.*;
import com.project.shopapp.repositories.OrderDetailRepository;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.ProductRepository;
import com.project.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.modelmapper.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;


@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;
    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        //Tìm xem userId tồn tại không
        User user = userRepository
                .findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException("Cannot find user with id : "+ orderDTO.getUserId()));
        //dùng thư viện Model Mapper
        //Tạo một luồng bảng ánh xạ riêng để kiểm soát việc ánh xạ
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderDTO,order);
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setStatus(OrderStatus.PENDING);

        LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate();
        if ( shippingDate.isBefore(LocalDate.now())){
            throw new DataNotFoundException("Data must be at least today");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItemDTO cartItemDTO : orderDTO.getCartItems()){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            Long productId = cartItemDTO.getProductId();
            int quantity = cartItemDTO.getQuantity();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new DataNotFoundException("Product not found with id : " + productId));
            orderDetail.setProduct(product);
            orderDetail.setNumberOfProducts(quantity);
            orderDetail.setPrice(product.getPrice());
            orderDetails.add(orderDetail);
        }

        orderDetailRepository.saveAll(orderDetails);


        return order ;
    }

    @Transactional
    public Order updateOrderWithDetails(OrderWithDetailsDTO orderWithDetailsDTO) {
        modelMapper.typeMap(OrderWithDetailsDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderWithDetailsDTO, order);
        Order savedOrder = orderRepository.save(order);

        // Set the order for each order detail
        for (OrderDetailDTO orderDetailDTO : orderWithDetailsDTO.getOrderDetailDTOS()) {
            //orderDetail.setOrder(OrderDetail);
        }

        // Save or update the order details
        List<OrderDetail> savedOrderDetails = orderDetailRepository.saveAll(order.getOrderDetails());

        // Set the updated order details for the order
        savedOrder.setOrderDetails(savedOrderDetails);

        return savedOrder;
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, OrderDTO orderDTO) throws Exception {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Cannot find order with id" + id)
        );
        User existingUser = userRepository.findById(orderDTO.getUserId()).orElseThrow(
                () -> new DataNotFoundException("Cannot find user with id" + id)
        );
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));

        modelMapper.map(orderDTO,order);
        order.setUser(existingUser);

        return orderRepository.save(order);

    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            order.setActive(false);
            orderRepository.save(order);
        }

    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public Page<Order> getOrdersByKeyword(String keyword, Pageable pageable) {
        return orderRepository.findByKeyword(keyword,pageable);
    }
}
