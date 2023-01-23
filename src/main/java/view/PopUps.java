package view;

import javax.swing.JOptionPane;

public class PopUps {
    public void deleteFromCartPopUp() {
        Object[] options = {"Tak",
                "Nie, wróć do koszyka"};
        int n = JOptionPane.showOptionDialog(null,
                "Czy na pewno chcesz usunąć produkt z koszyka?",
                "",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if (n == 0) {
            deleteFromCartMessage();
//            zrób coś
        }
        if (n == 1) {
//            zrób coś
        }
    }

//    public void logOutPopUp() {
//        Object[] options = {"Tak",
//                "Nie, wróć do sklepu"};
//        int n = JOptionPane.showOptionDialog(null,
//                "Czy na pewno chcesz się wylogować?",
//                "",
//                JOptionPane.YES_NO_CANCEL_OPTION,
//                JOptionPane.QUESTION_MESSAGE,
//                null,
//                options,
//                options[1]);
//        if (n == 0) {
//            logOutMessagePopUp();
////            zrób coś
//        }
//        if (n == 1) {
////            zrób coś
//        }
//    }

    public void addProductToShop() {
        Object[] options = {"Tak",
                "Anuluj"};
        int n = JOptionPane.showOptionDialog(null,
                "Czy na pewno chcesz dodać produkt do sklepu?",
                "",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if (n == 0) {
            addProductToShopPopUp();
//            zrób coś
        }
        if (n == 1) {
//            zrób coś
        }
    }

    public void deleteProductFromShop() {
        Object[] options = {"Tak",
                "Anuluj"};
        int n = JOptionPane.showOptionDialog(null,
                "Czy na pewno chcesz usunąć produkt ze sklepu?",
                "",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);
        if (n == 0) {
            deleteProductFromShopPopUp();
//            zrób coś
        }
        if (n == 1) {
//            zrób coś
        }
    }

//    public void addToCartPopUp() {
//        JOptionPane.showMessageDialog(null, "Dodano produkt do koszyka", "", JOptionPane.INFORMATION_MESSAGE);
//    }

    public void addProductToShopPopUp() {
        JOptionPane.showMessageDialog(null, "Dodano produkt do sklepu", "", JOptionPane.INFORMATION_MESSAGE);
    }

    public void deleteFromCartMessage() {
        JOptionPane.showMessageDialog(null, "Usunięto produkt z koszyka", "", JOptionPane.INFORMATION_MESSAGE);
    }

    public void deleteProductFromShopPopUp() {
        JOptionPane.showMessageDialog(null, "Usunięto produkt ze sklepu", "", JOptionPane.INFORMATION_MESSAGE);
    }

    public void passwordToShortPopUp() {
        JOptionPane.showMessageDialog(null, "Podane hasło jest za krótkie", "", JOptionPane.WARNING_MESSAGE);
    }

    public void wrongPasswordOrLoginPopUp() {
        JOptionPane.showMessageDialog(null, "Nieprawidłowe hasło lub login", "", JOptionPane.ERROR_MESSAGE);
    }

//    public void logOutMessagePopUp() {
//        JOptionPane.showMessageDialog(null, "Nastąpiło wylogowanie", "", JOptionPane.INFORMATION_MESSAGE);
//    }

    public void createAccountPopUp() {
        JOptionPane.showMessageDialog(null, "Utworzono konto", "", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void notEnoughOrderInfo() {
        JOptionPane.showMessageDialog(null, "Nie podano wszystkich informacji potrzebnych do złożenia zamówienia", "", JOptionPane.INFORMATION_MESSAGE);
    }
}
