package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UI extends JFrame {
    private JPanel msgBox;
    private JTextArea msgTextArea;  // Changed from JTextField to JTextArea
    private JPanel inputBox;
    private JTextField inputField;
    private JButton sendButton;
    private JTextArea username;

    private List<ChatMsg> chatMsgs = new ArrayList<>();
    private Client client;

    public UI() {
        super("Chat");
        client = new Client();
        setPreferredSize(new Dimension(800, 600));
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        UsernameScreen();
        initComponents();

        // Make the frame visible after adding components
        setVisible(true);
    }

    public void UsernameScreen() {
        setPreferredSize(new Dimension(400, 200));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        username = new JTextArea();

        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    public void initComponents() {
        msgBox = new JPanel();
        msgTextArea = new JTextArea();  // Changed from JTextField to JTextArea
        inputBox = new JPanel();
        inputField = new JTextField();
        sendButton = new JButton("Send");  // Set the button text in the constructor

        msgBox.setLayout(new BorderLayout());
        msgBox.add(new JScrollPane(msgTextArea), BorderLayout.CENTER);  // Use JScrollPane for the text area

        inputBox.setLayout(new BorderLayout());
        inputBox.add(inputField, BorderLayout.CENTER);
        inputBox.add(sendButton, BorderLayout.EAST);

        sendButton.addActionListener(e -> {
            String msg = inputField.getText();
            inputField.setText("");
            ChatMsg chatMsg = new ChatMsg(UserProfile.getUserProfile(), msg);
            client.send(chatMsg);
            addChatMsg(chatMsg);
            displayChatMsgs();
        });

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;  // Allow the component to grow both horizontally and vertically
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;  // Make the message box expand horizontally
        gbc.weighty = 1.0;  // Make the message box expand vertically
        add(msgBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 0.0;  // Don't let the input box expand vertically
        add(inputBox, gbc);
    }

    public void displayChatMsgs() {
        // Clear the text area before adding messages
        msgTextArea.setText("");

        for (ChatMsg chatMsg : chatMsgs) {
            msgTextArea.append(chatMsg.getUsername() + ": " + chatMsg.getMsg() + "\n");
        }
    }

    public void addChatMsg(ChatMsg chatMsg) {
        chatMsgs.add(chatMsg);
    }

    public static void main(String[] arg){
        SwingUtilities.invokeLater(() -> new UI());
    }
}
