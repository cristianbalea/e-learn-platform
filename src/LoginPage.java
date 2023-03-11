import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.sql.*;

public class LoginPage implements ActionListener {
    private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";
    private BufferedReader reader;
    private Connection con;

    private JFrame frame = new JFrame("Login");
    private JPanel panel = new JPanel();
    private JLabel labelTitle = new JLabel("Autentificare");
    private JLabel labelUser = new JLabel("User");
    private JLabel labelPassword = new JLabel("Password");
    private JTextField userText = new JTextField();
    private JPasswordField passwordText = new JPasswordField();
    private JButton btnConectare = new JButton("Conectare");
    private JButton btnBack = new JButton("Inapoi");

    private JRadioButton btnStudent = new JRadioButton("student");
    private JRadioButton btnProfesor = new JRadioButton("profesor");
    private JRadioButton btnAdmin = new JRadioButton("admin");
    private JLabel labelLogin = new JLabel("logare ca");

    LoginPage() {
        //Asezare
        labelTitle.setBounds(200, 15, 120, 25);
        labelUser.setBounds(20, 70, 80, 25);
        userText.setBounds(110, 70, 200, 25);
        labelPassword.setBounds(20, 100, 80, 25);
        passwordText.setBounds(110, 100, 200, 25);
        labelLogin.setBounds(30, 140, 150, 25);
        btnStudent.setBounds(40, 170, 150, 25);
        btnProfesor.setBounds(40, 190, 150, 25);
        btnAdmin.setBounds(40, 210, 150, 25);
        btnConectare.setBounds(90, 250, 150, 30);
        btnBack.setBounds(240, 250, 170, 30);

        //Functii
        btnBack.addActionListener(this);
        labelTitle.setFont(Style.font1);
        labelUser.setFont(Style.font1);
        labelPassword.setFont(Style.font1);
        labelLogin.setFont(Style.font1);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(btnAdmin);
        buttonGroup.add(btnStudent);
        buttonGroup.add(btnProfesor);
        btnConectare.addActionListener(this);

        btnAdmin.setBackground(new Color(159, 182, 209));
        btnStudent.setBackground(new Color(159, 182, 209));
        btnProfesor.setBackground(new Color(159, 182, 209));
        panel.setBackground(new Color(159, 182, 209));

        //Adaugare pe panel
        panel.add(labelTitle);
        panel.add(labelUser);
        panel.add(userText);
        panel.add(labelPassword);
        panel.add(passwordText);
        panel.add(btnConectare);
        panel.add(btnBack);
        panel.add(labelLogin);
        panel.add(btnAdmin);
        panel.add(btnProfesor);
        panel.add(btnStudent);

        //Altele
        panel.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setContentPane(panel);
        //frame.getContentPane().setBackground(Style.frameColor);
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            frame.dispose();
            WelcomePage welcomePage = new WelcomePage();
        }
        if (e.getSource() == btnConectare) {
            String user = userText.getText();
            String parola = passwordText.getText();
            if (btnProfesor.isSelected()) {
                try {
                    Connection con = DriverManager.getConnection(url, uid, pw);
                    String sqlQuery = "select * from profesor where email = \""
                            + user + "\" and parola = \"" + Encryption.encrypt(parola,Encryption.secretKey) + "\";";
                    try {
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(sqlQuery);
                        if (rs.next()) {
                            System.out.println("Conectare cu succes.");
                            frame.dispose();
                            ProfessorPage professorPage = new ProfessorPage(user);
                        } else {
                            JOptionPane.showMessageDialog(btnConectare, "User sau parola incorecta");
                        }
                    } catch (SQLException ex) {
                        System.out.println("Exception " + ex);
                    }
                    con.close();
                } catch (Exception ex) {
                    System.err.println("Exception: " + ex);
                }
            } else {
                if (btnStudent.isSelected()) {
                    try {
                        Connection con = DriverManager.getConnection(url, uid, pw);
                        String sqlQuery = "select * from student where email = \""
                                + user + "\" and parola = \"" + Encryption.encrypt(parola,Encryption.secretKey) + "\";";
                        try {
                            Statement stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery(sqlQuery);
                            if (rs.next()) {
                                System.out.println("Conectare cu succes.");
                                frame.dispose();
                                StudentPage studentPage = new StudentPage(user);
                            } else {
                                JOptionPane.showMessageDialog(btnConectare, "User sau parola incorecta");
                            }
                        } catch (SQLException ex) {
                            System.out.println("Exception " + ex);
                        }
                        con.close();
                    } catch (Exception ex) {
                        System.err.println("Exception: " + ex);
                    }

                } else {
                    if (btnAdmin.isSelected()) {
                        try {
                            Connection con = DriverManager.getConnection(url, uid, pw);
                            String sqlQuery = "select * from administrator where email = \""
                                    + user + "\" and parola = \"" + Encryption.encrypt(parola,Encryption.secretKey) + "\";";
                            try {
                                Statement stmt = con.createStatement();
                                ResultSet rs = stmt.executeQuery(sqlQuery);
                                if (rs.next()) {
                                    System.out.println("Conectare cu succes.");
                                    System.out.println("Conectare cu succes.");
                                    frame.dispose();
                                    AdminPage adminPage = new AdminPage(user);
                                } else {
                                    JOptionPane.showMessageDialog(btnConectare, "User sau parola incorecta");
                                }
                            } catch (SQLException ex) {
                                System.out.println("Exception " + ex);
                            }
                            con.close();
                        } catch (Exception ex) {
                            System.err.println("Exception: " + ex);
                        }
                    } else JOptionPane.showMessageDialog(btnProfesor, "Selecteaza student/profesor/admin!");
                }
            }
        }
    }
}
