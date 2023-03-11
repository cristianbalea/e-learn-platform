import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WelcomePage implements ActionListener {

    private JFrame frame = new JFrame("E-LEARN");
    final ImageIcon icon = new ImageIcon("C:\\Users\\balea\\Documents\\Facultate\\Baze de date\\Proiect\\Interfata\\src\\elearn.jpg");
    private JPanel panel = new JPanel() {
        Image img = icon.getImage();

        // instance initializer
        {
            setOpaque(false);
        }

        public void paintComponent(Graphics graphics) {
            graphics.drawImage(img, 0, 0, this);
            super.paintComponent(graphics);
        }
    };
    ;
    private JLabel labelWelcome = new JLabel("Bine ați venit !");
    private JButton btnConectare = new JButton("Conectare");
    private JButton btnInregistrare = new JButton("Înregistrare");
    Container con = frame.getContentPane();

    WelcomePage() {

        labelWelcome.setBounds(160, 110, 200, 30);
        btnConectare.setBounds(140, 160, 150, 30);
        btnInregistrare.setBounds(140, 200, 150, 30);

        labelWelcome.setForeground(new Color(146, 70, 176));
        frame.setBackground(Color.pink);
        //Functii
        btnConectare.addActionListener(this);
        btnInregistrare.addActionListener(this);
        btnConectare.setFocusable(false);
        btnInregistrare.setFocusable(false);
        labelWelcome.setFont(Style.font1);

        panel.add(labelWelcome);
        panel.add(btnConectare);
        panel.add(btnInregistrare);
        panel.setOpaque(false);


        panel.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container content = frame.getContentPane();
        content.add(panel, BorderLayout.CENTER);
        frame.setSize(410, 320);
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnConectare) {
            frame.dispose();
            LoginPage loginPage = new LoginPage();
        }
        if (e.getSource() == btnInregistrare) {
            frame.dispose();
            RegistrationPage registrationPage = new RegistrationPage();
        }
    }
}
