package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Login extends JFrame {
    private JTextField usernameField;
    private String username;

    Action action = new AbstractAction(){
        public void actionPerformed(ActionEvent e){
            username = usernameField.getText();
            if (!username.isEmpty()) {
                dispose();
                new UI(username);
            } else {
                JOptionPane.showMessageDialog(Login.this, "Please enter your name !!!");
            }
        }
    };

    public Login() {
        super("Login Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screensize.width / 2, screensize.height / 2);

        initComponents();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Enter your name (hit ENTER to go): ");
        usernameField = new JTextField(20);
        usernameField.addActionListener(action);
//        JButton loginButton = new JButton("Login");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(usernameField, gbc);

//        gbc.gridx = 0;
//        gbc.gridy = 1;
//        gbc.gridwidth = 2;
//        panel.add(loginButton, gbc);

//        loginButton.addActionListener(e -> {
//            username = usernameField.getText();
//            if (!username.isEmpty()) {
//                dispose();
//                new UI(username);
//            } else {
//                JOptionPane.showMessageDialog(Login.this, "Please enter your name !!!");
//            }
//        });

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
    }

    public String getUsername() {
        return username;
    }
}
