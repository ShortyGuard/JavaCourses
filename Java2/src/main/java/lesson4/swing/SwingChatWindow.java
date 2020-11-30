package lesson4.swing;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Создать окно для клиентской части чата: большое текстовое поле для отображения переписки в центре окна.
 * Однострочное текстовое поле для ввода сообщений и кнопка для отсылки сообщений на нижней панели.
 * Сообщение должно отсылаться либо по нажатию кнопки на форме, либо по нажатию кнопки Enter.
 * При «отсылке» сообщение перекидывается из нижнего поля в центральное.
 */
public class SwingChatWindow extends JFrame {


    private final int DEFAULT_WIDTH = 400;
    private final int DEFAULT_HEIGHT = 400;

    public SwingChatWindow() {
        super("Окно чата");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        this.getRootPane().setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int h = screenSize.height;
        int w = screenSize.width;
        this.setLocation(screenSize.width / 2 - DEFAULT_WIDTH / 2, screenSize.height / 2 - DEFAULT_HEIGHT / 2);

        // Text Area at the Center
        JTextArea historyTextArea = new JTextArea();
        historyTextArea.setEditable(false);
        historyTextArea.append("Это начало");
        historyTextArea.setMargin(new Insets(4,4,4,4));
        JScrollPane jScrollPane = new JScrollPane(historyTextArea);

        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        JTextField messageTextField = new JTextField(); // accepts upto 10 characters
        messageTextField.setPreferredSize(new Dimension(250, 24));
        messageTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                historyTextArea.append("\n> "+messageTextField.getText());
                messageTextField.setText("");
            }
        });


        JButton send = new JButton("Послать");
        send.setPreferredSize(new Dimension(100, 24));
        send.addActionListener(event -> {
            historyTextArea.append("\n> "+messageTextField.getText());
            messageTextField.setText("");
        });

        send.setFocusable(false);
        send.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));

        panel.setLayout(new BorderLayout(2,2));
        panel.add(messageTextField, BorderLayout.CENTER);
        panel.add(send, BorderLayout.EAST);


        //Adding Components to the frame.
        this.getContentPane().add(BorderLayout.SOUTH, panel);
        this.getContentPane().add(BorderLayout.CENTER, jScrollPane);

        this.setVisible(true);
        messageTextField.setFocusable(true);
        messageTextField.setText("");
    }

    public static void main(String[] args) {
        SwingChatWindow swingChatWindow = new SwingChatWindow();
    }
}
