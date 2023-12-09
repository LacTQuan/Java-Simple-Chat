package Ex02;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class UI extends JFrame {
    private JPanel msgBox;
    private JTextArea msgTextArea;
    private JPanel inputBox;
    private JTextField inputField;
    private JButton sendButton;
    private String username;

    private List<Message> messages = new ArrayList<>();
    private Client client;
    Action action = new AbstractAction(){
        public void actionPerformed(ActionEvent e){
            String msg = inputField.getText();
            inputField.setText("");
            Message message = new Message(username, msg);
            client.send(message);
        }
    };

    public UI(String username) {
        super("Chat");
        this.username = username;
        client = new Client(this);

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screensize.width / 2, screensize.height / 2);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                client.send(new Message(username, "has left the chat room."));
                System.out.println("Client disconnected: " + username);
                client.close();
                dispose();
            }
        });
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        initComponents();
        EventQueue.invokeLater(() -> inputField.requestFocusInWindow());

        setVisible(true);
    }


    public void initComponents() {
        msgBox = new JPanel();
        msgTextArea = new JTextArea();
        inputBox = new JPanel();
        inputField = new JTextField();
        sendButton = new JButton("Send");

        msgBox.setLayout(new BorderLayout());
        msgBox.add(new JScrollPane(msgTextArea), BorderLayout.CENTER);

        inputBox.setLayout(new BorderLayout());
        inputBox.add(inputField, BorderLayout.CENTER);
        inputField.addActionListener(action);
        inputField.requestFocus();

        inputBox.add(sendButton, BorderLayout.EAST);

        // Join the chat room message
        client.send(new Message(username, "has joined the chat room."));

        sendButton.addActionListener(e -> {
            String msg = inputField.getText();
            inputField.setText("");
            Message message = new Message(username, msg);
            client.send(message);
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
        msgTextArea.setText("");

        for (Message message : messages) {
            msgTextArea.append(message.getUsername() + ": " + message.getMsg() + "\n");
            msgTextArea.setForeground(Color.BLACK);
        }
    }

    public void addChatMsg(Message message) {
        messages.add(message);
    }

}
