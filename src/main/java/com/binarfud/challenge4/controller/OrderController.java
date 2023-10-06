package com.binarfud.challenge4.controller;

import com.binarfud.challenge4.model.*;
import com.binarfud.challenge4.model.response.OrdersResponse;
import com.binarfud.challenge4.service.OrderService;
import com.binarfud.challenge4.service.ProductService;
import com.binarfud.challenge4.service.UserService;
import com.binarfud.challenge4.view.MenuView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

import static com.binarfud.challenge4.view.MenuView.SEPARATOR;

@Component
public class OrderController {
    Scanner scanner = new Scanner(System.in);

    @Autowired
    OrderService orderService;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;
    private int currentPage = 0;


    public void serveInput(){
        Page<OrdersResponse> ordersPage = orderService.getOrdersPaged(currentPage);
        this.getOrdersAvailable(currentPage);
        System.out.print("=> ");
        try{
            int pilih = scanner.nextInt();
            scanner.nextLine();

            switch (pilih){
                case 1:
                    this.addNewOrders();
                    break;
                case 2:
                    if (ordersPage.hasNext()) {
                        currentPage++;
                        getOrdersAvailable(currentPage);
                        this.serveInput();
                    } else {
                        currentPage = 0;
                        getOrdersAvailable(currentPage);
                        this.serveInput();
                    }
                    break;
                default:
                    return;
            }
        }catch (Exception e){
            MenuView.showError();
            scanner.nextLine();
            String inputError = Optional.of(scanner.nextLine()).orElse("N");
            Optional.of(inputError)
                    .map(String::toUpperCase)
                    .ifPresent(input ->{
                        if (input.equals("Y")){
                            this.serveInput();
                        }
                    });
        }
    }

    public void getOrdersAvailable(int page) {
        System.out.println(SEPARATOR);
        System.out.println(MenuView.centerText("ORDERS", SEPARATOR.length()));
        System.out.println(SEPARATOR);

        try {
            Page<OrdersResponse> ordersPage = orderService.getOrdersPaged(page);

            if (!ordersPage.isEmpty()) {
                System.out.println(" ------------------------------------------------------------------------------------------------");
                System.out.printf("| %-2s | %-20s | %-20s | %-15s | %-3s | %-20s |%n", " Id ", " Name Customer ", " Name Product ", " Location ", " Qty ", " Total Price ");
                System.out.println(" ------------------------------------------------------------------------------------------------");

                for (OrdersResponse ordersResponse : ordersPage) {
                    System.out.printf("| %-4s | %-20s | %-20s | %-15s | %-5s | %-20s |%n",
                            ordersResponse.getOrderResponse_id(), ordersResponse.getNameCustomer(), ordersResponse.getNameProduct(),ordersResponse.getLocation() ,ordersResponse.getQty(), ordersResponse.getTotal_price());
                }

                System.out.println(" ------------------------------------------------------------------------------------------------");
                System.out.println("Page : " + (ordersPage.getNumber() + 1) + " of " + ordersPage.getTotalPages());

            } else {
                System.out.println("Maaf, Orders Kosong !!!\n");
            }

            System.out.println("1. Tambah Orders");
            if (ordersPage.hasPrevious()) {
                System.out.println("2. Previous Page");
            }
            if (ordersPage.hasNext()) {
                System.out.println("2. Next Page");
            }
            System.out.println("0. Keluar");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addNewOrders() {
        try {
            Page<Product> productPage = productService.getProductPaged(currentPage);
            for (Product product : productPage) {
                System.out.printf(" %-2s | %-20s | %-15s |%n",
                        product.getProductCode(), product.getProductName(), "Rp. " + product.getPrice());
            }

            System.out.print("Pilih Product Id : ");
            Long product_code = scanner.nextLong();
            scanner.nextLine();

            System.out.print("Input  Qty : ");
            int product_qty = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Input Destination Address : ");
            String destinationAddress = scanner.nextLine();

            Page<Users>  userPage = userService.getUserPaged(currentPage);
            for (Users user : userPage ) {
                System.out.printf(" %-2s | %-20s | %-25s |%n",
                        user.getUserId(), user.getUsername(), user.getEmailAddress());
            }

            System.out.print("Pilih User Id : ");
            Long userId = scanner.nextLong();
            scanner.nextLine();

            System.out.print("Completed True/False: ");
            String open = scanner.nextLine();
            boolean isOpen = Boolean.parseBoolean(open);

            orderService.createNewOrders(userId, product_code,destinationAddress,product_qty,isOpen);

            System.out.println("Order Baru Berhasil ditambahkan");
        } catch (Exception e) {
            System.out.println("Gagal Membuat Orderan: " + e.getMessage());
        }
        this.serveInput();
    }
}
