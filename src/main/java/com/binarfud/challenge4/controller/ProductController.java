package com.binarfud.challenge4.controller;

import com.binarfud.challenge4.model.Product;
import com.binarfud.challenge4.service.ProductService;
import com.binarfud.challenge4.view.MenuView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

import static com.binarfud.challenge4.view.MenuView.*;

@Component
public class ProductController {
    Scanner scanner = new Scanner(System.in);

    @Autowired
    ProductService productService;
    private int currentPage = 0;
    UserInputController userInputController = new UserInputController();

    public void serveInput(){
        Page<Product> productPage = productService.getProductPaged(currentPage);
        this.getProductAvailable(currentPage);
        System.out.print("=> ");
        try{
            int pilih = scanner.nextInt();
            scanner.nextLine();

            switch (pilih){
                case 1:
                    this.addNewProduct();
                    break;
                case 2:
                    this.updateProductbyId();
                    break;
                case 3:
                    this.deleteProductbyId();
                    break;
                case 4:
                    if (productPage.hasNext()) {
                        currentPage++;
                        getProductAvailable(currentPage);
                        this.serveInput();
                    } else {
                        currentPage = 0;
                        getProductAvailable(currentPage);
                        this.serveInput();
                    }
                    break;
                default:
                    userInputController.mainMenu();
                    break;
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

    public void getProductAvailable(int page) {
        try {
            System.out.println(SEPARATOR);
            System.out.println(MenuView.centerText("PRODUCT AVAILABLE", SEPARATOR.length()));
            System.out.println(SEPARATOR);

            Page<Product> productPage = productService.getProductPaged(page);

            if (!productPage.isEmpty()) {
                System.out.println(" ---------------------------------------------");
                System.out.printf("|%-2s | %-20s | %s |%n", " No", " Nama Product ", " Harga Product ");
                System.out.println(" ---------------------------------------------");

                for (Product product : productPage) {
                    System.out.printf("| %-2s | %-20s | %-15s |%n",
                            product.getProductCode(), product.getProductName(), "Rp. " + product.getPrice());
                }
                System.out.println(" ---------------------------------------------");
                System.out.println("Page : " + (productPage.getNumber() + 1) + " of " + (productPage.getTotalPages() ));
            } else {
                System.out.println("\nMaaf, Produk sedang kosong !!!\n");
            }

            System.out.println("\n1. Tambah Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            if (productPage.hasNext()) {
                System.out.println("4. Next Page");
            } else if (!productPage.hasNext()) {
                System.out.println("4. Previous Page");
            }
            System.out.println("0. Keluar");
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }

    public void addNewProduct(){
        try {
            System.out.print("Input nama product : ");
            String product_name = scanner.nextLine();

            System.out.print("Input harga product : ");
            long price = scanner.nextLong();
            scanner.nextLine();

            Product product = Product.builder().productName(product_name).price(price).build();
            productService.addNewProduct(product);

            System.out.println("Produk Baru Berhasil ditambahkan");
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        } finally {
            this.serveInput();
        }
    }

    public void updateProductbyId(){
        try {
            System.out.print("Pilih id product yang ingin di ubah : ");
            Long product_code = scanner.nextLong();
            scanner.nextLine();
            if (product_code > 0) {
                System.out.print("Update nama product : ");
                String product_name = scanner.nextLine();

                System.out.print("Update harga product : ");
                long price = scanner.nextLong();
                scanner.nextLine();

                Product product = Product.builder().productCode(product_code).productName(product_name).price(price).build();
                productService.updateById(product_code, product_name, price);
                System.out.println("Product berhasil di update");
            } else {
                System.out.println("Id tidak valid");
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        } finally {
            this.serveInput();
        }
    }

    public void deleteProductbyId(){
        try {
            System.out.print("Pilih id product yang ingin di hapus : ");
            Long id_product = scanner.nextLong();
            productService.deleteProductbyId(id_product);
            System.out.println("Product Berhasil di hapus!");
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        } finally {
            this.serveInput();
        }
    }
}
