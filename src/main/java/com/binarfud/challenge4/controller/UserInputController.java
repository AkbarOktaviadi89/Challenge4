package com.binarfud.challenge4.controller;

import com.binarfud.challenge4.view.MenuView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Scanner;

import static com.binarfud.challenge4.view.MenuView.*;

@Component
public class UserInputController {

    @PostConstruct
    public void init(){
        this.mainMenu();
    }

    static Scanner scanner = new Scanner(System.in);

    @Autowired
    ProductController productController;

    @Autowired
    MerchantController merchantController;

    @Autowired
    UserController userController;

    @Autowired
    OrderController orderController;

        public void mainMenu() {
            MenuView.showMenu();
            try {
                int input = scanner.nextInt();
                scanner.nextLine();

                switch (input) {
                    case 1:
                        productController.serveInput();
                        break;
                    case 2:
                        merchantController.serveInput();
                        break;
                    case 3:
                        userController.serveInput();
                        break;
                    case 4:
                        orderController.serveInput();
                        break;
                }
                this.mainMenu();
            }catch (Exception e){
                MenuView.showError();
            }
        }
    }
