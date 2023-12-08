package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JTextField usernameField;

    public Login() {
        super("Login Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();

        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Enter your name:");
        usernameField = new JTextField(20);
        JButton loginButton = new JButton("Login");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                if (!username.isEmpty()) {
                    dispose(); // Close the login screen
                    new UI();
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Please enter your name.");
                }
            }
        });

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
    }

//    private void openMainScreen(String username) {
//        SwingUtilities.invokeLater(() -> new UI());
//    }
}
