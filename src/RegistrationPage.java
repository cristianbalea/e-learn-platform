import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;

public class RegistrationPage implements ActionListener {
    private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";

    private int TEXT_ALIGN = 140;
    private int LABEL_SIZE = 100;
    private JFrame frame = new JFrame("Register");
    private JPanel panel = new JPanel();
    private JLabel labelTitle = new JLabel("Inregistrare");
    private JLabel labelUser = new JLabel("Sunt:");
    private JRadioButton radioButtonStudent = new JRadioButton("student");
    private JRadioButton radioButtonProfesor = new JRadioButton("profesor");
    private JLabel labelCNP = new JLabel("CNP");
    private JLabel labelNume = new JLabel("Nume");
    private JLabel labelPrenume = new JLabel("Prenume");
    private JLabel labelAdresa = new JLabel("Adresa");
    private JLabel labelTelefon = new JLabel("Telefon");
    private JLabel labelEmail = new JLabel("E-Mail");
    private JLabel labelIBAN = new JLabel("IBAN");
    private JLabel labelParola = new JLabel("Parola");
    private JLabel labelDepartament = new JLabel("Departament*");
    private JLabel labelAnStudiu = new JLabel("An studiu*");
    private JTextField cnpText = new JTextField();
    private JTextField numeText = new JTextField();
    private JTextField prenumeText = new JTextField();
    private JTextField adresaText = new JTextField();
    private JTextField telefonText = new JTextField();
    private JTextField emailText = new JTextField();
    private JTextField ibanText = new JTextField();
    private JTextField departamentText = new JTextField();
    private JTextField anStudiuText = new JTextField();
    private JPasswordField parolaText = new JPasswordField();
    private JButton btnSignUp = new JButton("Inregistreaza-ma");
    private JButton btnBack = new JButton("Inapoi");

    RegistrationPage() {
        //Asezare
        labelTitle.setBounds(200, 10, 250, 50);
        labelUser.setBounds(40, 50, LABEL_SIZE, 15);
        radioButtonStudent.setBounds(30, 70, 250, 20);
        radioButtonProfesor.setBounds(30, 90, 250, 20);
        labelCNP.setBounds(20, 130, LABEL_SIZE, 20);
        cnpText.setBounds(TEXT_ALIGN, 130, 150, 20);
        labelNume.setBounds(20, 160, LABEL_SIZE, 20);
        numeText.setBounds(TEXT_ALIGN, 160, 150, 20);
        labelPrenume.setBounds(20, 190, LABEL_SIZE, 20);
        prenumeText.setBounds(TEXT_ALIGN, 190, 150, 20);
        labelAdresa.setBounds(20, 220, LABEL_SIZE, 20);
        adresaText.setBounds(TEXT_ALIGN, 220, 300, 20);
        labelTelefon.setBounds(20, 250, LABEL_SIZE, 20);
        telefonText.setBounds(TEXT_ALIGN, 250, 150, 20);
        labelEmail.setBounds(20, 280, LABEL_SIZE, 20);
        emailText.setBounds(TEXT_ALIGN, 280, 200, 20);
        labelIBAN.setBounds(20, 310, LABEL_SIZE, 20);
        ibanText.setBounds(TEXT_ALIGN, 310, 200, 20);
        labelParola.setBounds(20, 340, LABEL_SIZE, 20);
        parolaText.setBounds(TEXT_ALIGN, 340, 200, 20);
        labelDepartament.setBounds(20, 370, 200, 20);
        departamentText.setBounds(TEXT_ALIGN, 370, 200, 20);
        labelAnStudiu.setBounds(20, 400, 200, 20);
        anStudiuText.setBounds(TEXT_ALIGN, 400, 200, 20);
        btnSignUp.setBounds(100, 450, 150, 30);
        btnBack.setBounds(250, 450, 150, 30);

        //Functii
        ButtonGroup users = new ButtonGroup();
        users.add(radioButtonProfesor);
        users.add(radioButtonStudent);

        btnBack.addActionListener(this);

        labelTitle.setFont(Style.font1);
        labelAdresa.setFont(Style.font1);
        labelCNP.setFont(Style.font1);
        labelEmail.setFont(Style.font1);
        labelIBAN.setFont(Style.font1);
        labelNume.setFont(Style.font1);
        labelPrenume.setFont(Style.font1);
        labelTelefon.setFont(Style.font1);
        labelUser.setFont(Style.font1);
        labelParola.setFont(Style.font1);
        labelDepartament.setFont(Style.font1);
        labelAnStudiu.setFont(Style.font1);
        btnSignUp.addActionListener(this);

        //Adaugare pe panel
        panel.add(labelTitle);
        panel.add(labelUser);
        panel.add(radioButtonStudent);
        panel.add(radioButtonProfesor);
        panel.add(labelCNP);
        panel.add(cnpText);
        panel.add(labelNume);
        panel.add(numeText);
        panel.add(labelPrenume);
        panel.add(prenumeText);
        panel.add(labelAdresa);
        panel.add(adresaText);
        panel.add(labelTelefon);
        panel.add(telefonText);
        panel.add(labelEmail);
        panel.add(emailText);
        panel.add(labelIBAN);
        panel.add(ibanText);
        panel.add(btnSignUp);
        panel.add(btnBack);
        panel.add(labelParola);
        panel.add(parolaText);
        panel.add(labelDepartament);
        panel.add(departamentText);
        panel.add(labelAnStudiu);
        panel.add(anStudiuText);

        panel.setLayout(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 550);
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
        if (e.getSource() == btnSignUp) {
            String cnp = cnpText.getText();
            String nume = numeText.getText();
            String prenume = prenumeText.getText();
            String adresa = adresaText.getText();
            String telefon = telefonText.getText();
            String email = emailText.getText();
            String iban = ibanText.getText();
            String parola = parolaText.getText();
            String departament = departamentText.getText();
            String anStudiu = anStudiuText.getText();
            int cnpLen = cnp.length();//13
            int telefonLen = telefon.length();//10
            int ibanLen = iban.length();//24
            int ok = 1;
            if (cnpLen != 13) {
                JOptionPane.showMessageDialog(btnSignUp, "Introdu un CNP valid!");
                ok = 0;
            }
            if (telefonLen != 10) {
                JOptionPane.showMessageDialog(btnSignUp, "Introdu un numar de telefon valid!");
                ok = 0;
            }
            if (ibanLen != 24) {
                JOptionPane.showMessageDialog(btnSignUp, "Introdu un IBAN valid!");
                ok = 0;
            }
            if(email.contains("@") == false) {
                JOptionPane.showMessageDialog(btnSignUp, "Introdu un email valid!");
                ok = 0;
            }
            if (ok == 1) {
                if (radioButtonStudent.isSelected()) {
                    try {
                        Connection con = DriverManager.getConnection(url, uid, pw);
                        String sqlStmt = "insert into student(cnp, nume, prenume, adresa, nrTelefon, email, IBAN, parola, anStudiu) " +
                                "VALUES (\"" + cnp + "\", \"" + nume + "\", \"" + prenume + "\", \"" + adresa +
                                "\", \"" + telefon + "\", \"" + email + "\", \"" + iban + "\", \"" + Encryption.encrypt(parola, Encryption.secretKey) + "\", \"" + anStudiu + "\");";

                        try {
                            Statement stmt = con.createStatement();
                            stmt.executeUpdate(sqlStmt);
                            System.out.println("Operation success!");
                        } catch (SQLException ex) {
                            System.out.println("Operation failed: " + ex);
                        }
                        con.close();
                    } catch (Exception ex) {
                        System.err.println("Exception: " + ex);
                    }
                }
                else if (radioButtonProfesor.isSelected()) {
                    try {
                        Connection con = DriverManager.getConnection(url, uid, pw);
                        String sqlStmt = "insert into profesor(cnp, nume, prenume, adresa, nrTelefon, email, IBAN, parola, departament) " +
                                "VALUES (\"" + cnp + "\", \"" + nume + "\", \"" + prenume + "\", \"" + adresa +
                                "\", \"" + telefon + "\", \"" + email + "\", \"" + iban + "\", \"" +
                                Encryption.encrypt(parola, Encryption.secretKey) + "\", \"" + departament + "\");";

                        try {
                            Statement stmt = con.createStatement();
                            stmt.executeUpdate(sqlStmt);
                            System.out.println("Operation success!");
                        } catch (SQLException ex) {
                            System.out.println("Operation failed: " + ex);
                        }
                        con.close();
                    } catch (Exception ex) {
                        System.err.println("Exception: " + ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(radioButtonProfesor, "Selecteaza student/profesor!");
                }
            }


        }
    }
}