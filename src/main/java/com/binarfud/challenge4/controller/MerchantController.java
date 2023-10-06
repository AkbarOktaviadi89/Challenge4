package com.binarfud.challenge4.controller;

import com.binarfud.challenge4.model.Merchant;
import com.binarfud.challenge4.service.MerchantService;
import com.binarfud.challenge4.view.MenuView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

import static com.binarfud.challenge4.view.MenuView.SEPARATOR;

@Component
public class MerchantController {
    Scanner scanner = new Scanner(System.in);

    UserInputController userInputController;
    private int currentPage = 0;

    @Autowired
    MerchantService merchantService;

    public void serveInput() {
        Page<Merchant> merchantPage = merchantService.getMerchantPaged(currentPage);
        this.getMerchantAvailable(currentPage);
        System.out.print("=> ");
        try {
            int pilih = scanner.nextInt();
            scanner.nextLine();

            switch (pilih) {
                case 1:
                    this.addNewMerchant();
                    break;
                case 2:
                    this.updateMerchantbyId();
                    break;
                case 3:
                    if (merchantPage.hasNext()) {
                        currentPage++;
                        getMerchantAvailable(currentPage);
                        this.serveInput();
                    } else {
                        currentPage = 0;
                        getMerchantAvailable(currentPage);
                        this.serveInput();
                    }
                    break;
                default:
                    return;
            }
        } catch (Exception e) {
            MenuView.showError();
            scanner.nextLine();
            String inputError = Optional.of(scanner.nextLine()).orElse("N");
            Optional.of(inputError)
                    .map(String::toUpperCase)
                    .ifPresent(input -> {
                        if (input.equals("Y")) {
                            this.serveInput();
                        }
                    });
        }
    }

    public void getMerchantAvailable(int page) {
        System.out.println(SEPARATOR);
        System.out.println(MenuView.centerText("MERCHANT OPEN", SEPARATOR.length()));
        System.out.println(SEPARATOR);

        try {
            Page<Merchant> merchantPage = merchantService.getMerchantPaged(page);

            if (!merchantPage.isEmpty()) {
                System.out.println(" -------------------------------------------------------");
                System.out.printf("|%-2s | %-20s | %-15s | %-5s |%n", " No", " Nama Merchant ", " Location ", " Open ");
                System.out.println(" -------------------------------------------------------");

                for (Merchant merchant : merchantPage) {
                    System.out.printf("| %-2s | %-20s | %-15s | %-6s |%n",
                            merchant.getMerchantCode(), merchant.getMerchantName(), merchant.getMerchantLocation(), merchant.isOpen());
                }
                System.out.println(" -------------------------------------------------------");
                System.out.println("Page : " + (merchantPage.getNumber() + 1) + " of " + merchantPage.getTotalPages());

            } else {
                System.out.println("Maaf, Merchant Sedang Tutup / Kosong !!!\n");
            }

            System.out.println("1. Tambah Merchant");
            System.out.println("2. Update Merchant");
            if (merchantPage.hasPrevious()) {
                System.out.println("3. Previous Page");
            }
            if (merchantPage.hasNext()) {
                System.out.println("3. Next Page");
            }
            System.out.println("0. Keluar");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addNewMerchant() {
        try {
            System.out.print("Input nama merchant : ");
            String product_name = scanner.nextLine();

            System.out.print("Input lokasi merchant : ");
            String location = scanner.nextLine();

            System.out.print("Merchant is open? True/False: ");
            String open = scanner.nextLine();
            boolean isOpen = Boolean.parseBoolean(open);

            Merchant merchant = Merchant.builder().merchantName(product_name).merchantLocation(location).open(isOpen).build();
            merchantService.addNewMerchant(merchant);
            System.out.println("Merchant Baru Berhasil ditambahkan");
        } catch (Exception e) {
            System.out.println("Gagal menambahkan merchant: " + e.getMessage());
        }
        this.serveInput();
    }

    public void updateMerchantbyId() {
        try {
            System.out.print("Pilih id merchant yang ingin di ubah : ");
            long product_code = scanner.nextLong();
            scanner.nextLine();
            if (product_code > 0) {
                System.out.print("Update nama merchant : ");
                String merchant_name = scanner.nextLine();

                System.out.print("Update lokasi merchant : ");
                String location = scanner.nextLine();

                System.out.print("Update Merchant is open? True/False: ");
                String open = scanner.nextLine();
                boolean isOpen = Boolean.parseBoolean(open);

                Merchant merchant = Merchant.builder().merchantName(merchant_name).merchantLocation(location).open(isOpen).build();
                merchantService.updateMerchant(product_code, merchant_name, location, isOpen);
                System.out.println("Merchant berhasil di update");
            } else {
                System.out.println("Id tidak valid");
            }
        } catch (Exception e) {
            System.out.println("Gagal mengupdate merchant: " + e.getMessage());
        }
        this.serveInput();
    }
}

