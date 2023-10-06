package com.binarfud.challenge4.controller;

import com.binarfud.challenge4.model.Users;
import com.binarfud.challenge4.service.UserService;
import com.binarfud.challenge4.view.MenuView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

import static com.binarfud.challenge4.view.MenuView.SEPARATOR;

@Component
public class UserController {

    Scanner scanner = new Scanner(System.in);
    private int currentPage = 0;
    UserInputController userInputController = new UserInputController();

    @Autowired
    UserService userService;

    public void serveInput() {
        Page<Users> userPage = userService.getUserPaged(currentPage);
        this.getUserAvailable(currentPage);
        System.out.print("=> ");
        try {
            int pilih = scanner.nextInt();
            scanner.nextLine();

            switch (pilih) {
                case 1:
                    this.addNewUsers();
                    break;
                case 2:
                    this.updateUserbyId();
                    break;
                case 3:
                    this.deleteProductbyId();
                    break;
                case 4:
                    if (userPage.hasNext()) {
                        currentPage++;
                        getUserAvailable(currentPage);
                        this.serveInput();
                    } else {
                        currentPage = 0;
                        getUserAvailable(currentPage);
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

    public void getUserAvailable(int page) {
        System.out.println(SEPARATOR);
        System.out.println(MenuView.centerText("USER", SEPARATOR.length()));
        System.out.println(SEPARATOR);

        Page<Users> userPage = userService.getUserPaged(page);

        if (!userPage.isEmpty()) {
            System.out.println(" -------------------------------------------------------");
            System.out.printf("|%-2s | %-20s | %-25s |%n", " No", " Username ", " Email ");
            System.out.println(" -------------------------------------------------------");

            for (Users users : userPage) {
                System.out.printf("| %-2s | %-20s | %-25s |%n",
                        users.getUserId(), users.getUsername(), users.getEmailAddress());
            }
            System.out.println(" -------------------------------------------------------");
            System.out.println("Page : " + userPage.getNumber() + " of " + userPage.getTotalPages());

        } else {
            System.out.println("User tidak tersedia di database atau halaman tidak tersedia !!!\n");
        }

        System.out.println("1. Tambah Users");
        System.out.println("2. Update Users");
        System.out.println("3. Delete Users");
        if (userPage.hasPrevious()) {
            System.out.println("4. Previous Page");
        }
        if (userPage.hasNext()) {
            System.out.println("4. Next Page");
        }
        System.out.println("0. Keluar");
    }


    public void addNewUsers() {
        try {
            System.out.print("Input Username : ");
            String username = scanner.nextLine();

            System.out.print("Input Email Address : ");
            String email = scanner.nextLine();

            System.out.print("Input Password : ");
            String password = scanner.nextLine();

            Users users = Users.builder().username(username).emailAddress(email).password(password).build();
            userService.addNewUser(users);

            System.out.println("User Baru Berhasil ditambahkan");
        } catch (Exception e) {
            System.out.println("Gagal menambahkan user: " + e.getMessage());
        }

        this.serveInput();
    }

    public void updateUserbyId() {
        try {
            System.out.print("Pilih id user yang ingin di ubah : ");
            long user_id = scanner.nextLong();
            scanner.nextLine();
            if (user_id > 0) {
                System.out.print("Update username : ");
                String username = scanner.nextLine();

                System.out.print("Update Email Address : ");
                String email = scanner.nextLine();

                System.out.print("Update Password : ");
                String password = scanner.nextLine();

                Users users = Users.builder().username(username).emailAddress(email).password(password).build();
                userService.updateUserbyId(user_id, username, email, password);
                System.out.println("User berhasil di update");
            } else {
                System.out.println("Id tidak valid");
            }
        } catch (Exception e) {
            System.out.println("Gagal mengupdate user: " + e.getMessage());
        }

        this.serveInput();
    }

    public void deleteProductbyId() {
        try {
            System.out.print("Pilih id user yang ingin di hapus : ");
            Long user_id = scanner.nextLong();
            userService.deleteUser(user_id);
            System.out.println("User Berhasil di hapus!");
        } catch (Exception e) {
            System.out.println("Gagal menghapus user: " + e.getMessage());
        }
        this.serveInput();
    }
}
