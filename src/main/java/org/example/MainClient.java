package org.example;

import javax.swing.*;

class MainClient {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}