import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


class Modify implements ActionListener {
    String username;
    String id;
    JPanel panel = new JPanel();
    JLabel labelAfisareDate = new JLabel("Afisare date curente:");
    JLabel labelCNP = new JLabel("CNP:");
    JLabel labelNume = new JLabel("Nume:   ");
    JLabel labelPrenume = new JLabel("Prenume:   ");
    JLabel labelAdresa = new JLabel("Adresa:   ");
    JLabel labelNrTelefon = new JLabel("Nr. telefon:   ");
    JLabel labelEmail = new JLabel("E-mail:   ");
    JLabel labelIBAN = new JLabel("IBAN:   ");
    JLabel labelNrContract = new JLabel("Nr. contract:   ");
    JLabel labelCautaDupaNume = new JLabel("Cauta dupa nume:");
    String tabel = "";
    String cnpRef = "";
    int superAdmin = 0;
    private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";
    private JFrame frame = new JFrame("Modificare informatii");
    private JTextField textFieldCNP = new JTextField();
    private JTextField textFieldNume = new JTextField();
    private JTextField textFieldPrenume = new JTextField();
    private JTextField textFieldAdresa = new JTextField();
    private JTextField textFieldNrTelefon = new JTextField();
    private JTextField textFieldEmail = new JTextField();
    private JTextField textFieldIBAN = new JTextField();
    private JTextField textFieldNrContract = new JTextField();
    private JTextField textCautaDupaNume = new JTextField("Nume Prenume");
    private JRadioButton btnProfesor = new JRadioButton("Profesor");
    private JRadioButton btnStudent = new JRadioButton("Student");
    private JRadioButton btnAdmin = new JRadioButton("Administrator");
    private ButtonGroup buttonGroup = new ButtonGroup();
    private JButton btnCautaDupaNume = new JButton("Search");
    private JButton btnModifica = new JButton("Modifica date");


    public Modify(String username) {
        this.username = username;

        btnStudent.addActionListener(this);
        btnProfesor.addActionListener(this);
        btnCautaDupaNume.addActionListener(this);
        btnModifica.addActionListener(this);
        labelCautaDupaNume.setBounds(20, 70, 150, 20);
        textCautaDupaNume.setBounds(150, 70, 150, 20);
        btnCautaDupaNume.setBounds(320, 70, 150, 20);
        labelAfisareDate.setBounds(20, 100, 150, 20);
        labelCNP.setBounds(20, 130, 150, 20);
        textFieldCNP.setBounds(170, 130, 200, 20);
        labelNume.setBounds(20, 160, 150, 20);
        textFieldNume.setBounds(170, 160, 200, 20);
        labelPrenume.setBounds(20, 190, 150, 20);
        textFieldPrenume.setBounds(170, 190, 200, 20);
        labelAdresa.setBounds(20, 220, 150, 20);
        textFieldAdresa.setBounds(170, 220, 200, 20);
        labelNrTelefon.setBounds(20, 250, 150, 20);
        textFieldNrTelefon.setBounds(170, 250, 200, 20);
        labelEmail.setBounds(20, 280, 150, 20);
        textFieldEmail.setBounds(170, 280, 200, 20);
        labelIBAN.setBounds(20, 310, 150, 20);
        textFieldIBAN.setBounds(170, 310, 200, 20);
        labelNrContract.setBounds(20, 340, 150, 20);
        textFieldNrContract.setBounds(170, 340, 200, 20);
        btnModifica.setBounds(170, 400, 150, 20);


        btnCautaDupaNume.setForeground(Style.btnForeground);
        btnCautaDupaNume.setBackground(Style.btnBackground);

        btnStudent.setBounds(10, 30, 150, 30);
        btnProfesor.setBounds(160, 30, 150, 30);
        btnAdmin.setBounds(310, 30, 150, 30);

        try {
            Connection con = DriverManager.getConnection(url, uid, pw);
            Statement stmt = con.createStatement();
            stmt.execute("select super from administrator where email like \"" + username + "\";");
            ResultSet rs = stmt.getResultSet();
            if (rs.next() == true) {
                superAdmin = rs.getInt("super");
            }
            if (superAdmin == 1) {
                panel.add(btnAdmin);
                btnAdmin.addActionListener(this);
                buttonGroup.add(btnAdmin);
            }

        } catch (Exception exception) {
            System.err.println("Exception" + exception.getMessage());
        }

        panel.add(btnCautaDupaNume);

        panel.add(btnStudent);
        panel.add(btnProfesor);
        buttonGroup.add(btnStudent);
        buttonGroup.add(btnProfesor);

        panel.add(btnModifica);

        panel.add(textCautaDupaNume);
        panel.add(textFieldCNP);
        panel.add(textFieldNume);
        panel.add(textFieldPrenume);
        panel.add(textFieldNrContract);
        panel.add(textFieldEmail);
        panel.add(textFieldNrTelefon);
        panel.add(textFieldIBAN);
        panel.add(textFieldAdresa);

        panel.add(labelCautaDupaNume);
        panel.add(labelAfisareDate);
        panel.add(labelCNP);
        panel.add(labelNume);
        panel.add(labelPrenume);
        panel.add(labelAdresa);
        panel.add(labelEmail);
        panel.add(labelIBAN);
        panel.add(labelNrTelefon);
        panel.add(labelNrContract);
        panel.setLayout(null);

        frame.setSize(550, 500);
        frame.setContentPane(panel);
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Modify m = new Modify("anca@yahoo.com");
    }

    public void actionPerformed(ActionEvent e) {
        int ok = 0;
        if (btnStudent.isSelected() || btnAdmin.isSelected() || btnProfesor.isSelected())
            ok = 1;
        if (ok != 0) {
            if (e.getSource() == btnCautaDupaNume) {
                try {

                    String fullName = textCautaDupaNume.getText();
                    String[] splittedName = fullName.split(" ");
                    if (btnStudent.isSelected()) {
                        tabel = "student";
                    }
                    if (btnProfesor.isSelected()) {
                        tabel = "profesor";
                    }
                    if (btnAdmin.isSelected()) {
                        tabel = "administrator";
                    }
                    Connection con = DriverManager.getConnection(url, uid, pw);
                    Statement stmt = con.createStatement();
                    stmt.execute("select * from " + tabel + " where nume= \"" + splittedName[0] + "\" and prenume= \"" + splittedName[1] + "\";");
                    ResultSet rs = stmt.getResultSet();
                    if (rs.next()) {
                        cnpRef = rs.getString("cnp");
                        textFieldCNP.setText(rs.getString("cnp"));
                        textFieldAdresa.setText(rs.getString("adresa"));
                        textFieldEmail.setText(rs.getString("email"));
                        textFieldIBAN.setText(rs.getString("iban"));
                        textFieldNrContract.setText(rs.getString("nrContract"));
                        textFieldNrTelefon.setText(rs.getString("nrTelefon"));
                        textFieldNume.setText(rs.getString("nume"));
                        textFieldPrenume.setText(rs.getString("prenume"));
                    }
                    rs.close();
                    con.close();

                } catch (Exception exception) {
                    System.err.println("Exception " + exception.getMessage());
                }

            }
            if (e.getSource() == btnModifica) {
                try {
                    Connection con = DriverManager.getConnection(url, uid, pw);
                    Statement stmt = con.createStatement();

                    stmt.execute("SET SQL_SAFE_UPDATES = 0;");
                    String query = "update " + tabel + " set nume =\"" + textFieldNume.getText() + "\",cnp =\"" + textFieldCNP.getText() +
                            "\",prenume =\"" + textFieldPrenume.getText() + "\",adresa =\"" +
                            textFieldAdresa.getText() + "\",nrTelefon =\"" + textFieldNrTelefon.getText() +
                            "\",email =\"" + textFieldEmail.getText() + "\",IBAN =\"" + textFieldIBAN.getText() + "\" where cnp =\"" + cnpRef + "\";";
                    stmt.execute(query);

                    con.close();
                } catch (Exception exception) {
                    System.err.println("Exception" + exception.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(btnCautaDupaNume, "Selecteaza tipul de utilizator!");
        }

    }
}

class SearchUsers implements ActionListener {
    private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";

    private JFrame frame = new JFrame("Cautare utilizatori");
    private JPanel panel = new JPanel();
    private JLabel labelNume = new JLabel("Introduceti nume");
    private JLabel labelTip = new JLabel("Introduceti tip valid");
    private JLabel labelUsers = new JLabel();
    private JButton btnSearch = new JButton("Cauta dupa nume");
    private JButton btnSearchTip = new JButton("Cauta dupa tip si nume");
    private JTextField numeText = new JTextField();
    private JTextField tipText = new JTextField("student/profesor/administrator");

    SearchUsers() {
        tipText.setFont(Style.font2);
        labelNume.setBounds(20, 20, 200, 30);
        labelTip.setBounds(20, 100, 200, 30);
        labelUsers.setBounds(0, 100, 1200, 400);

        numeText.setBounds(200, 20, 200, 30);
        tipText.setBounds(200, 100, 200, 30);

        btnSearch.setBounds(200, 60, 200, 30);
        btnSearchTip.setBounds(200, 140, 200, 30);


        btnSearch.addActionListener(this);
        btnSearchTip.addActionListener(this);

        panel.add(labelNume);
        panel.add(labelTip);
        panel.add(labelUsers);

        panel.add(numeText);
        panel.add(tipText);

        panel.add(btnSearch);
        panel.add(btnSearchTip);
        panel.setLayout(null);

        frame.setSize(1200, 800);
        frame.setContentPane(panel);
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SearchUsers searchUsers = new SearchUsers();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nume = numeText.getText();
        String tip = tipText.getText();

        if (e.getSource() == btnSearch) {
            try {
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                stmt.execute("select cnp,nume,prenume,adresa,nrTelefon,email,IBAN,nrContract from student where nume like \"%" +
                        nume + "%\" union select cnp,nume,prenume,adresa,nrTelefon,email,IBAN,nrContract from profesor where nume like \"%" +
                        nume + "%\" union select cnp,nume,prenume,adresa,nrTelefon,email,IBAN,nrContract from administrator where nume like\"%" +
                        nume + "%\" order by nume;");
                printSearch(con, stmt);
            } catch (SQLException ex) {
                System.err.println("Exception " + ex);
            }
        }
        if (e.getSource() == btnSearchTip) {
            try {
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                stmt.execute("select cnp,nume,prenume,adresa,nrTelefon,email,IBAN,nrContract from " + tip + " where nume like \"%" +
                        nume + "%\" order by nume;");
                printSearch(con, stmt);
            } catch (SQLException ex) {
                System.err.println("Exception " + ex);
            }
        }
    }

    private void printSearch(Connection con, Statement stmt) throws SQLException {
        ResultSet rs = stmt.getResultSet();
        int ok = 0;
        labelUsers.setText("<ul>");
        while (rs.next()) {
            labelUsers.setText(labelUsers.getText() + "<li>CNP : " + rs.getString("cnp") +
                    " | Nume : " + rs.getString("nume") + " " + rs.getString("prenume") +
                    " | Adresa : " + rs.getString("adresa") + " | Nr.telefon: " + rs.getString("nrTelefon") +
                    " | Email : " + rs.getString("email") + " | IBAN : " + rs.getString("IBAN") +
                    " | Numar contract : " + rs.getString("nrContract") + "</li><br>");
            ok++;
        }
        if (ok != 0) {
            labelUsers.setText("<html><b>" + labelUsers.getText() + "</ul></b></html>");
        } else {
            labelUsers.setText("<html><b> Nu exista utilizatori cu numele dat !</ul></b></html>");
        }
        rs.close();
        con.close();
    }
}

class AsignareProfesor implements ActionListener {
    private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";

    private JFrame frame = new JFrame("Asignare profesori");
    private JPanel panel = new JPanel();
    private JLabel labelNume = new JLabel("Introduceti nume");
    private JLabel labelPrenume = new JLabel("Introduceti prenume");
    private JLabel labelCurs = new JLabel("Introduceti nume materie");

    private JButton btnAsignare = new JButton("Asignare");
    private JTextField numeText = new JTextField();
    private JTextField prenumeText = new JTextField();
    private JTextField cursText = new JTextField();

    AsignareProfesor() {

        labelNume.setBounds(20, 20, 200, 30);
        labelPrenume.setBounds(20, 50, 200, 30);
        labelCurs.setBounds(20, 80, 200, 30);

        numeText.setBounds(200, 20, 200, 30);
        prenumeText.setBounds(200, 50, 200, 30);
        cursText.setBounds(200, 80, 200, 30);

        btnAsignare.setBounds(200, 120, 200, 30);


        btnAsignare.addActionListener(this);
        panel.add(labelNume);
        panel.add(labelPrenume);
        panel.add(labelCurs);

        panel.add(numeText);
        panel.add(prenumeText);
        panel.add(cursText);

        panel.add(btnAsignare);
        panel.setLayout(null);

        frame.setSize(600, 250);
        frame.setContentPane(panel);
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        AsignareProfesor asignareProfesor = new AsignareProfesor();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAsignare) {
            String idProfesor;
            String nume = numeText.getText();
            String prenume = prenumeText.getText();
            String materie = cursText.getText();
            try {
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                stmt.execute("SELECT id from profesor where nume = \"" + nume + "\" and prenume = \"" + prenume + "\";");
                ResultSet rs = stmt.getResultSet();
                if (rs.next()) {
                    idProfesor = rs.getString("id");
                    int integerIdProfesor = Integer.parseInt(idProfesor);
                    stmt.execute("insert into oraractivitati (idProfesor, numeMaterie, nrParticipanti, nrMaxParticipanti, data, ora, durata) " +
                            "values (" + integerIdProfesor + ", \"" + materie + "\" , 0, 0, '2000-01-01',0,0 );");
                    JOptionPane.showMessageDialog(btnAsignare, "Profesor asignat cu succes!");
                } else {
                    JOptionPane.showMessageDialog(btnAsignare, "Profesor inexistent!");
                }

                stmt.close();
                rs.close();
                con.close();
            } catch (SQLException ex) {
                System.err.println("Exception " + ex);
            }
        }
    }
}

class AdminSearchCourse implements ActionListener {
    String username;
    JPanel panel = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JLabel labelIntroducere = new JLabel("Introduceti numele cursului:");
    JLabel labelAfisareProfesori = new JLabel("");
    JLabel labelAfisareStudenti = new JLabel("");
    JTextField cautaText = new JTextField();
    JButton btnCautare = new JButton("Cautare");
    JButton btnVeziStudenti = new JButton("Vezi studentii inscrisi");
    private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";
    private JFrame frame = new JFrame("Cauta curs");

    public AdminSearchCourse(String username) {
        this.username = username;
        labelIntroducere.setBounds(10, 20, 250, 20);
        cautaText.setBounds(200, 20, 250, 20);
        btnCautare.setBounds(10, 60, 100, 20);
        btnVeziStudenti.setBounds(10, 20, 250, 20);
        labelAfisareProfesori.setBounds(10, 100, 200, 200);
        labelAfisareStudenti.setBounds(10, 60, 300, 200);

        btnCautare.addActionListener(this);
        btnVeziStudenti.addActionListener(this);

        panel1.add(labelIntroducere);
        panel1.add(cautaText);
        panel1.add(btnCautare);
        panel1.add(labelAfisareProfesori);

        panel2.add(btnVeziStudenti);
        panel2.add(labelAfisareStudenti);

        panel.add(panel1);
        panel.add(panel2, BorderLayout.PAGE_START);

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel1.setLayout(null);
        panel2.setLayout(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 500);
        frame.setContentPane(panel);
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCautare) {
            try {
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                stmt.execute("select profesor.nume, profesor.prenume from profesor, oraractivitati where " +
                        "numematerie like \"" + cautaText.getText() + "\" and oraractivitati.idprofesor = profesor.id " +
                        " group by nume;");
                ResultSet rs = stmt.getResultSet();
                labelAfisareProfesori.setText("<ul>");
                while (rs.next()) {
                    labelAfisareProfesori.setText(labelAfisareProfesori.getText() + "<li>" + rs.getString("nume") + "  " + rs.getString("prenume") + "</li>" + "<br>");
                }
                labelAfisareProfesori.setText("<html><b>" + labelAfisareProfesori.getText() + "</ul></b></html>");
                rs.close();
                con.close();
            } catch (Exception exception) {
                System.err.println("Exception" + exception);
            }
        }
        if (e.getSource() == btnVeziStudenti) {
            try {
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                stmt.execute("select student.nume, student.prenume from student, inscrierematerie where numematerie like \"" + cautaText.getText() +
                        "\" and student.id = inscrierematerie.idstudent order by nume, prenume;");
                ResultSet rs = stmt.getResultSet();
                labelAfisareStudenti.setText("<ul>");
                while (rs.next()) {
                    System.out.println("aa");
                    labelAfisareStudenti.setText(labelAfisareStudenti.getText() + "<li>" + rs.getString("nume") +
                            "  " + rs.getString("prenume") + "</li>" + "<br>");
                }
                labelAfisareStudenti.setText("<html><b>" + labelAfisareStudenti.getText() + "</ul></b></html>");
                rs.close();
                con.close();
            } catch (Exception exception) {
                System.err.println("Exception" + exception);
            }
        }
    }
}

class ModificareCursuri implements ActionListener {
    private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";

    private JFrame frame = new JFrame("Modificare cursuri");
    private JPanel panel = new JPanel();
    private JLabel labelMaterie = new JLabel("Introduceti nume curs");
    private JLabel labelInfo1 = new JLabel("Creeaza sau sterge un curs");
    private JLabel labelInfo2 = new JLabel("Modifica o materie");

    private JLabel labelMaterie1 = new JLabel("Introduceti numele cursului");


    private JButton btnCreate = new JButton("Creare");
    private JButton btnDelete = new JButton("Stergere");
    private JButton btnModifica = new JButton("Actualizare");
    private JButton btnCautaCurs = new JButton("Cauta curs");

    private JTextField numeMaterieText = new JTextField();
    private JTextField numeMaterie1Text = new JTextField();
    private JTextArea descriereText = new JTextArea();
    private JTextField numeMaterie2Text = new JTextField();

    ModificareCursuri() {
        labelInfo1.setBounds(20, 20, 400, 30);
        labelMaterie.setBounds(20, 60, 200, 30);
        numeMaterieText.setBounds(20, 100, 250, 30);
        btnCreate.setBounds(20, 140, 150, 30);
        btnDelete.setBounds(200, 140, 150, 30);

        labelInfo2.setBounds(20, 240, 300, 30);
        labelMaterie1.setBounds(20, 280, 200, 30);
        numeMaterie1Text.setBounds(20, 320, 250, 30);
        btnCautaCurs.setBounds(20, 360, 160, 30);
        numeMaterie2Text.setBounds(20, 460, 250, 30);
        descriereText.setBounds(20, 500, 500, 200);
        btnModifica.setBounds(20, 710, 160, 30);

        btnCreate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnModifica.addActionListener(this);
        btnCautaCurs.addActionListener(this);
        labelInfo1.setFont(Style.font1);
        labelInfo2.setFont(Style.font1);

        descriereText.setLineWrap(true);
        panel.add(labelInfo1);
        panel.add(labelInfo2);
        panel.add(labelMaterie);
        panel.add(numeMaterie2Text);
        panel.add(numeMaterieText);
        panel.add(labelMaterie1);
        panel.add(numeMaterie1Text);
        panel.add(descriereText);
        panel.add(btnCreate);
        panel.add(btnDelete);
        panel.add(btnModifica);
        panel.add(btnCautaCurs);

        panel.setLayout(null);

        frame.setSize(600, 800);
        frame.setContentPane(panel);
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        ModificareCursuri modificareCursuri = new ModificareCursuri();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String numeMaterie = numeMaterieText.getText();
        String numeMaterieModificare = numeMaterie1Text.getText();
        String numeMaterieModificare2 = numeMaterie2Text.getText();
        String descriere = descriereText.getText();

        if (e.getSource() == btnCreate) {
            try {
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                stmt.execute("Insert into materie(nume) values (\"" + numeMaterie + "\");");
                JOptionPane.showMessageDialog(btnCreate, "Creare curs cu succes!");
                stmt.close();
                con.close();
            } catch (SQLException ex) {
                System.err.println("Exception" + ex);

            }
        }
        if (e.getSource() == btnDelete) {
            try {
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                stmt.execute("delete from materie where nume like (\"" + numeMaterie + "\");");
                JOptionPane.showMessageDialog(btnDelete, "Stergere curs cu succes!");
                stmt.close();
                con.close();
            } catch (SQLException ex) {
                System.err.println("Exception" + ex);
            }
        }
        if (e.getSource() == btnCautaCurs) {
            try {
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                stmt.execute("select * from materie where nume like (\"" + numeMaterieModificare + "\");");
                ResultSet rs = stmt.getResultSet();
                if (rs.next()) {
                    numeMaterie2Text.setText(rs.getString("nume"));
                    descriereText.setText(rs.getString("descriere"));
                }
                rs.close();
                stmt.close();
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == btnModifica) {
            try {
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                stmt.execute("update materie set nume = \"" + numeMaterieModificare2 + "\", descriere = \"" + descriere
                        + "\" where nume like \"" + numeMaterieModificare + "\";");
                JOptionPane.showMessageDialog(btnModifica, "Modificare realizata cu succes!");
                stmt.close();
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}