import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

class AddActivities implements ActionListener {
    private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";
    private String username;

    private JFrame frame = new JFrame("Programare activitati");
    private JPanel panel = new JPanel();
    private JLabel labelNumeMaterie = new JLabel("Materie");
    private JLabel labelTipMaterie = new JLabel("Tip(curs,laborator,seminar)");
    private JLabel labelNrParticipanti = new JLabel("Numarul maxim de participanti");
    private JLabel labelData = new JLabel("Data (YYYY-MM-DD)");//YYYY-MM-DD
    private JLabel labelOra = new JLabel("Ora");
    private JLabel labelDurata = new JLabel("Durata");
    private JButton btnProgrameaza = new JButton("Programeaza");
    private JTextField numeMaterieText = new JTextField();
    private JTextField tipText = new JTextField();
    private JTextField nrParticipantiText = new JTextField();
    private JTextField dataText = new JTextField();
    private JTextField oraText = new JTextField();
    private JTextField durataText = new JTextField();

    AddActivities(String username) {
        this.username = username;

        labelNumeMaterie.setBounds(20, 20, 200, 30);
        labelTipMaterie.setBounds(20, 50, 200, 30);
        labelNrParticipanti.setBounds(20, 80, 200, 30);
        labelData.setBounds(20, 110, 200, 30);
        labelOra.setBounds(20, 140, 200, 30);
        labelDurata.setBounds(20, 170, 200, 30);

        numeMaterieText.setBounds(200, 20, 200, 30);
        tipText.setBounds(200, 50, 200, 30);
        nrParticipantiText.setBounds(200, 80, 200, 30);
        dataText.setBounds(200, 110, 200, 30);
        oraText.setBounds(200, 140, 200, 30);
        durataText.setBounds(200, 170, 200, 30);
        btnProgrameaza.setBounds(100, 220, 200, 30);

        panel.add(labelNumeMaterie);
        panel.add(labelTipMaterie);
        panel.add(labelOra);
        panel.add(labelData);
        panel.add(labelDurata);
        panel.add(labelNrParticipanti);
        panel.add(numeMaterieText);
        panel.add(tipText);
        panel.add(nrParticipantiText);
        panel.add(dataText);
        panel.add(oraText);
        panel.add(durataText);
        panel.add(btnProgrameaza);

        btnProgrameaza.addActionListener(this);

        panel.setLayout(null);

        frame.setSize(600, 400);
        frame.setContentPane(panel);
        //frame.getContentPane().setBackground(Style.frameColor);
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnProgrameaza) {
            String idProffesor;
            String numeMaterie = numeMaterieText.getText();
            String tip = tipText.getText();
            String nrMaxParticipanti = nrParticipantiText.getText();
            int nrMaxParticipantiInteger = Integer.parseInt(nrMaxParticipanti);
            String data = dataText.getText();
            String ora = oraText.getText();
            int oraInteger = Integer.parseInt(ora);
            String durata = durataText.getText();
            int durataInteger = Integer.parseInt(durata);
            int numarParticipanti = 0;
            int ok = 1;

            if (data.contains("-") == false) {
                JOptionPane.showMessageDialog(btnProgrameaza, "Introdu data cu format corect");
                ok = 0;
            }
            if (ok == 1) {
                try {
                    Connection con = DriverManager.getConnection(url, uid, pw);
                    Statement stmt = con.createStatement();
                    stmt.execute("select id from profesor where email like \"" + username + "\";");
                    ResultSet rs = stmt.getResultSet();
                    if (rs.next() == true) {
                        idProffesor = rs.getString("id");
                        String sqlStmt = "insert into oraractivitati(idProfesor,numeMaterie, tip, nrParticipanti, nrMaxParticipanti, data, ora, durata) " +
                                "VALUES (\"" + idProffesor + "\", \"" + numeMaterie + "\", \"" + tip + "\", \"" + numarParticipanti + "\", \"" + nrMaxParticipantiInteger + "\", \"" + data +
                                "\", \"" + oraInteger + "\", \"" + durataInteger + "\");";
                        try {
                            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
                            stmt.execute(sqlStmt);
                            System.out.println("Operation succes!");
                            JOptionPane.showMessageDialog(btnProgrameaza, "Activitate programata cu succes !");
                        } catch (SQLException ex) {
                            System.out.println("Operation failed" + ex);
                        }
                    }
                    rs.close();
                    stmt.close();
                    con.close();
                } catch (Exception ex) {
                    System.err.println("Exception: " + ex);
                }

            }
        }
    }
}

class ModificarePonderi implements ActionListener {

    private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";
    private String username;

    private JFrame frame = new JFrame("Modificare ponderi");
    private JPanel panel = new JPanel();
    private JLabel labelNumeMaterie = new JLabel("Materie");
    private JLabel labelRezultate = new JLabel("Rezultate:");
    private JLabel labelCurs = new JLabel("curs");
    private JLabel labelSeminar = new JLabel("seminar");
    private JLabel labelLaborator = new JLabel("laborator");
    private JLabel labelDateNoi = new JLabel();

    private JTextField pondereCursText = new JTextField();
    private JTextField pondereSeminarText = new JTextField();
    private JTextField pondereLaboratorText = new JTextField();
    private JTextField numeMaterieText = new JTextField();

    private JButton btnPonderiActuale = new JButton("Vezi ponderi actuale");
    private JButton btnSchimbaPonderi = new JButton("Schimba ponderile");

    ModificarePonderi(String username) {
        this.username = username;

        labelNumeMaterie.setBounds(20, 20, 200, 30);
        labelRezultate.setBounds(20, 100, 200, 30);
        labelCurs.setBounds(100, 100, 200, 30);
        labelSeminar.setBounds(100, 130, 200, 30);
        labelLaborator.setBounds(100, 160, 200, 30);
        labelDateNoi.setBounds(100, 200, 300, 30);

        numeMaterieText.setBounds(200, 20, 200, 30);
        pondereCursText.setBounds(200, 100, 200, 30);
        pondereSeminarText.setBounds(200, 130, 200, 30);
        pondereLaboratorText.setBounds(200, 160, 200, 30);
        btnPonderiActuale.setBounds(200, 60, 200, 30);
        btnSchimbaPonderi.setBounds(200, 230, 200, 30);

        panel.add(labelNumeMaterie);
        panel.add(labelRezultate);
        panel.add(labelCurs);
        panel.add(labelSeminar);
        panel.add(labelLaborator);
        panel.add(labelDateNoi);
        panel.add(numeMaterieText);
        panel.add(pondereCursText);
        panel.add(pondereSeminarText);
        panel.add(pondereLaboratorText);
        panel.add(btnPonderiActuale);
        panel.add(btnSchimbaPonderi);

        btnPonderiActuale.addActionListener(this);
        btnSchimbaPonderi.addActionListener(this);
        panel.setLayout(null);

        frame.setSize(600, 330);
        frame.setContentPane(panel);
        //frame.getContentPane().setBackground(Style.frameColor);
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        ModificarePonderi modificarePonderi = new ModificarePonderi("stan@gmail.com");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String numeMaterie = numeMaterieText.getText();
        ;
        if (e.getSource() == btnPonderiActuale) {
            String idProffesor;
            try {
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                stmt.execute("select id from profesor where email like \"" + username + "\";");
                ResultSet rs = stmt.getResultSet();
                if (rs.next()) {
                    idProffesor = rs.getString("id");
                    stmt.execute("select count(*) from oraractivitati where idProfesor = " +
                            "\"" + idProffesor + "\" and numeMaterie like " + "\"" + numeMaterie + "\";");
                    rs = stmt.getResultSet();
                    if (rs.next()) {
                        String predaMateria = rs.getString("Count(*)");
                        int predaMateriaInteger = Integer.parseInt(predaMateria);
                        if (predaMateriaInteger > 0) {
                            stmt.execute("select tip, pondere from activitate where numeMaterie like " + "\"" + numeMaterie + "\";");
                            rs = stmt.getResultSet();
                            while (rs.next()) {
                                String tip = rs.getString("tip");
                                String pondere = rs.getString("pondere");
                                if (tip.equals(labelCurs.getText())) {
                                    pondereCursText.setText(pondere);
                                }
                                if (tip.equals(labelSeminar.getText())) {
                                    pondereSeminarText.setText(pondere);
                                }
                                if (tip.equals(labelLaborator.getText())) {
                                    pondereLaboratorText.setText(pondere);
                                }

                            }
                            labelDateNoi.setText("Introduceti datele noi in casuțele de mai sus!");
                        } else {
                            JOptionPane.showMessageDialog(btnPonderiActuale, "Nu preda la materia respectiva");
                        }
                    }
                }
                rs.close();
                stmt.close();
                con.close();
            } catch (Exception ex) {
                System.err.println("Exception: " + ex.getMessage());
            }
        }

        if (e.getSource() == btnSchimbaPonderi) {
            String curs = labelCurs.getText();
            String seminar = labelSeminar.getText();
            String laborator = labelLaborator.getText();
            String pondereCurs = pondereCursText.getText();
            String pondereSeminar = pondereSeminarText.getText();
            String pondereLaborator = pondereLaboratorText.getText();
            try {
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                if (Integer.parseInt(pondereCurs) + Integer.parseInt(pondereSeminar) + Integer.parseInt(pondereLaborator) == 100) {
                    stmt.executeUpdate("update activitate set pondere = \"" + pondereCurs + "\" where numeMaterie = \"" + numeMaterie + "\" and tip = \"" + curs + "\" ;");
                    stmt.executeUpdate("update activitate set pondere = \"" + pondereSeminar + "\" where numeMaterie = \"" + numeMaterie + "\" and tip = \"" + seminar + "\" ;");
                    stmt.executeUpdate("update activitate set pondere = \"" + pondereLaborator + "\" where numeMaterie = \"" + numeMaterie + "\" and tip = \"" + laborator + "\" ;");
                    JOptionPane.showMessageDialog(btnSchimbaPonderi, "Ponderile actualizate cu succes!");
                } else {
                    JOptionPane.showMessageDialog(btnSchimbaPonderi, "Introduceti ponderi valide!");
                }
                stmt.close();
                con.close();
            } catch (Exception ex) {
                System.err.println("Exception: " + ex.getMessage());
            }
        }
    }
}

class NotareStudenti implements ActionListener {
    private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";
    private String username;

    private JFrame frame = new JFrame("Notare student");
    private JPanel panel = new JPanel();
    private JLabel labelNume = new JLabel("Nume");
    private JLabel labelPrenume = new JLabel("Prenume");
    private JLabel labelNotaCurs = new JLabel("Nota curs");
    private JLabel labelNotaSeminar = new JLabel("Nota seminar");
    private JLabel labelNotaLaborator = new JLabel("Nota laborator");
    private JLabel labelMaterie = new JLabel("Materia:");
    private JLabel labelDateNoi = new JLabel();

    private JTextField numeText = new JTextField();
    private JTextField prenumeText = new JTextField();
    private JTextField NotaCursText = new JTextField();
    private JTextField NotaSeminarText = new JTextField();
    private JTextField NotaLaboratorText = new JTextField();
    private JTextField materieText = new JTextField();
    private JButton btnActualizareNote = new JButton("Actualizare note");
    private JButton btnVeziNoteActuale = new JButton("Vezi note actuale");

    NotareStudenti(String username) {
        this.username = username;

        labelNume.setBounds(20, 20, 200, 30);
        labelPrenume.setBounds(20, 50, 200, 30);
        labelNotaCurs.setBounds(20, 150, 200, 30);
        labelNotaSeminar.setBounds(20, 180, 200, 30);
        labelNotaLaborator.setBounds(20, 210, 200, 30);
        labelMaterie.setBounds(20, 80, 200, 30);
        //labelDateNoi.setBounds(100, 200, 300, 30);

        numeText.setBounds(200, 20, 200, 30);
        prenumeText.setBounds(200, 50, 200, 30);
        materieText.setBounds(200, 80, 200, 30);
        btnVeziNoteActuale.setBounds(200, 110, 200, 30);
        NotaCursText.setBounds(200, 150, 200, 30);
        NotaSeminarText.setBounds(200, 180, 200, 30);
        NotaLaboratorText.setBounds(200, 210, 200, 30);
        btnActualizareNote.setBounds(200, 250, 200, 30);

        panel.add(labelNume);
        panel.add(labelPrenume);
        panel.add(labelNotaCurs);
        panel.add(labelNotaSeminar);
        panel.add(labelNotaLaborator);
        panel.add(labelDateNoi);
        panel.add(numeText);
        panel.add(prenumeText);
        panel.add(NotaCursText);
        panel.add(NotaSeminarText);
        panel.add(NotaLaboratorText);
        panel.add(btnActualizareNote);
        panel.add(btnVeziNoteActuale);
        panel.add(labelMaterie);
        panel.add(materieText);
        btnActualizareNote.addActionListener(this);
        btnVeziNoteActuale.addActionListener(this);
        panel.setLayout(null);

        frame.setSize(600, 350);
        frame.setContentPane(panel);
        //frame.getContentPane().setBackground(Style.frameColor);
        frame.setIconImage(Style.img.getImage());
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        NotareStudenti notareStudenti = new NotareStudenti("stan@gmail.com");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nume = numeText.getText();
        String prenume = prenumeText.getText();
        String materie = materieText.getText();
        String idStudent;
        if (e.getSource() == btnVeziNoteActuale) {
            try {
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                stmt.execute("SELECT id from student where nume = \"" + nume + "\" and prenume = \"" + prenume + "\";");
                ResultSet rs = stmt.getResultSet();
                if (rs.next()) {
                    idStudent = rs.getString("id");
                    stmt.execute("select notaCurs, notaSeminar, notaLaborator from catalog where idStudent = \"" + idStudent + "\" and " +
                            "numematerie like \"" + materie + "\";");
                    rs = stmt.getResultSet();
                    if (rs.next()) {
                        String notaCurs = rs.getString("notaCurs");
                        String notaSeminar = rs.getString("notaSeminar");
                        String notaLaborator = rs.getString("notaLaborator");
                        NotaCursText.setText(notaCurs);
                        NotaSeminarText.setText(notaSeminar);
                        NotaLaboratorText.setText(notaLaborator);
                    }
                }

                rs.close();
                con.close();
            } catch (Exception ex) {
                System.err.println("Exception: " + ex.getMessage());
            }
        }
        //update catalog set notaCurs = 8.2, notaSeminar = 10, notaLaborator = '0' where idStudent = '10';
        if (e.getSource() == btnActualizareNote) {
            try {
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                stmt.execute("SELECT id from student where nume = \"" + nume + "\" and prenume = \"" + prenume + "\";");
                ResultSet rs = stmt.getResultSet();
                if (rs.next()) {
                    idStudent = rs.getString("id");
                    String notaCurs = NotaCursText.getText();
                    String notaSeminar = NotaSeminarText.getText();
                    String notaLaborator = NotaLaboratorText.getText();
                    stmt.execute("update catalog set notaCurs = \"" + notaCurs + "\"," + " notaSeminar = \"" + notaSeminar + "\", notaLaborator = \""
                            + notaLaborator + "\" where idStudent = \"" + idStudent + "\" and numeMaterie like \"" + materie + "\";");
                    stmt.execute("call nota_finala(" + idStudent + ", \"" + materie + "\");");
                    JOptionPane.showMessageDialog(btnActualizareNote, "Note adaugate cu succes !");
                }
            } catch (Exception ex) {
                System.err.println("Exception: " + ex.getMessage());
            }
        }
    }
}

class CursurileMele {
    private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";
    private String username;

    private JFrame frame = new JFrame("Cursurile mele");
    private JPanel panel = new JPanel();
    private JLabel labelCursurileMele = new JLabel();

    CursurileMele(String username) {
        this.username = username;

        labelCursurileMele.setBounds(0, 0, 600, 300);
        String idProffesor;
        try {
            Connection con = DriverManager.getConnection(url, uid, pw);
            Statement stmt = con.createStatement();
            stmt.execute("select id from profesor where email like \"" + username + "\";");
            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                idProffesor = rs.getString("id");
                stmt.execute("select distinct numeMaterie, tip from oraractivitati where idProfesor = \"" +
                        idProffesor + "\";");
                rs = stmt.getResultSet();
                labelCursurileMele.setText("<ul>");
                int ok = 1;
                while (rs.next()) {
                    ok = 0;
                    labelCursurileMele.setText(labelCursurileMele.getText() + "<li>" + rs.getString("numeMaterie") +
                            " - " + rs.getString("tip") + "</li>" + "<br>");
                }
                if (ok == 1) {
                    labelCursurileMele.setBounds(10, 10, 300, 100);
                    labelCursurileMele.setText("Nu preda nici un curs");
                } else {
                    labelCursurileMele.setText("<html><b>" + labelCursurileMele.getText() + "</ul></b></html>");
                }
                rs.close();
                con.close();

            }

        } catch (Exception e) {
            System.err.println("Exception" + e);
        }
        panel.add(labelCursurileMele);
        panel.setLayout(null);

        frame.setSize(600, 600);
        frame.setContentPane(panel);
        //frame.getContentPane().setBackground(Style.frameColor);
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);
    }
}

class ListaStudenti implements ActionListener {
    private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";

    private JFrame frame = new JFrame("Lista studenti");
    private JPanel panel = new JPanel();
    private JLabel labelMaterie = new JLabel("Materie");
    private JLabel labelStudenti = new JLabel("Studenti:");

    private JTextField materieText = new JTextField();
    private JButton btnVeziStudenti = new JButton("Vizualizare lista");
    private JButton btnDescarcaCatalog = new JButton("Descarca catalog");

    ListaStudenti() {
        labelMaterie.setBounds(20, 20, 200, 30);
        labelStudenti.setBounds(20, 60, 200, 300);

        materieText.setBounds(100, 20, 200, 30);
        btnVeziStudenti.setBounds(100, 60, 200, 30);
        btnDescarcaCatalog.setBounds(100, 700, 200, 30);
        panel.add(labelMaterie);
        panel.add(labelStudenti);
        panel.add(materieText);
        panel.add(btnDescarcaCatalog);
        panel.add(btnVeziStudenti);

        btnDescarcaCatalog.addActionListener(this);
        btnVeziStudenti.addActionListener(this);
        panel.setLayout(null);

        frame.setSize(600, 800);
        frame.setContentPane(panel);
        //frame.getContentPane().setBackground(Style.frameColor);
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String numeMaterie = materieText.getText();

        if (e.getSource() == btnVeziStudenti) {
            String idStudent;
            try {
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                stmt.execute("SELECT idStudent from catalog where numeMaterie = \"" + numeMaterie + "\";");
                ResultSet rs = stmt.getResultSet();
                labelStudenti.setText(labelStudenti.getText() + "<br>" + "<ul>");
                while (rs.next()) {
                    idStudent = rs.getString("idStudent");
                    Statement stmt2 = con.createStatement();
                    stmt2.execute("SELECT nume,prenume from student where id = \"" + idStudent + "\";");
                    ResultSet rs2 = stmt2.getResultSet();
                    if (rs2.next()) {
                        labelStudenti.setText(labelStudenti.getText() + "<li>" + rs2.getString("nume") +
                                " " + rs2.getString("prenume") + "</li>" + "<br>");
                    }

                }
                labelStudenti.setText("<html><b>" + labelStudenti.getText() + "</ul></b></html>");
                rs.close();
                con.close();

            } catch (Exception ex) {
                System.err.println("Exception: " + ex.getMessage());
            }
        }
        if (e.getSource() == btnDescarcaCatalog) {
            String nume;
            String prenume;
            String notaCurs;
            String notaSeminar;
            String notaLaborator;
            String notaFinala;
            try {
                FileWriter fWrite = new FileWriter("C:\\users\\balea\\Desktop\\Catalog.txt");
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                stmt.execute("SELECT student.nume, student.prenume, catalog.notaCurs, catalog.notaSeminar,catalog.notaLaborator, catalog.notaFinala " +
                        "from student, catalog where student.id = catalog.idStudent and catalog.numeMaterie = \"" + numeMaterie + "\";");
                ResultSet rs = stmt.getResultSet();
                while (rs.next()) {
                    nume = rs.getString("nume");
                    prenume = rs.getString("prenume");
                    notaCurs = rs.getString("notaCurs");
                    notaSeminar = rs.getString("notaSeminar");
                    notaLaborator = rs.getString("notaLaborator");
                    notaFinala = rs.getString("notaFinala");
                    fWrite.write("# " + "Nume, prenume : " + nume + " " + prenume + "\n"
                            +"  Nota curs : " + notaCurs + "\n" + "  Nota seminar : " + notaSeminar + "\n" + "  Nota laborator : " + notaLaborator
                            + "\n" + "  Nota finala : " + notaFinala + "\n");
                }
                fWrite.close();
                JOptionPane.showMessageDialog(btnDescarcaCatalog, "Catalog descarcat cu succes !");
                rs.close();
                con.close();
            } catch (SQLException | IOException ex) {
                System.err.println("Exception: " + ex);
            }

        }
    }
}

class GroupsP implements ActionListener {
    String username;
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JButton[] btnGroup;
    JLabel labelGroups = new JLabel("Grupurile mele");
    int nrGroups = 0;
    int idProfesor = 0;private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";


    public GroupsP(String username) {
        this.username = username;
        try {
            Connection con = DriverManager.getConnection(url, uid, pw);
            Statement stmt = con.createStatement();
            stmt.execute("select id from profesor where email like \"" + username + "\";");
            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                idProfesor = Integer.parseInt(rs.getString("id"));
                stmt.execute("select count(*) from asignaregrupprofesor where idProfesor = " + idProfesor + ";");
                rs = stmt.getResultSet();
                if (rs.next())
                    nrGroups = Integer.parseInt(rs.getString("count(*)"));
            }
            rs.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Exceptie" + e);
        }
        labelGroups.setBounds(20, 20, 300, 40);
        labelGroups.setFont(Style.font1);

        btnGroup = new JButton[nrGroups];
        int y = 70;
        for (int i = 0; i < nrGroups; i++) {
            btnGroup[i] = new JButton();
            btnGroup[i].setBounds(20, y, 300, 40);
            btnGroup[i].addActionListener(this);
            panel.add(btnGroup[i]);
            y += 40;

        }
        try {
            Connection con = DriverManager.getConnection(url, uid, pw);
            Statement stmt = con.createStatement();
            stmt.execute("select idGrupStudiu from asignaregrupProfesor where idProfesor = " + idProfesor + ";");
            ResultSet rs = stmt.getResultSet();
            int i = 0;
            while (rs.next()) {
                Statement stmt2 = con.createStatement();
                stmt2.execute("select numematerie from grupstudiu where idGrupStudiu =" + rs.getString("idGrupStudiu") + ";");
                ResultSet rs2 = stmt2.getResultSet();
                if (rs2.next()) {
                    btnGroup[i].setText(rs2.getString("numematerie"));
                }
                i++;
            }
        } catch (Exception e) {
            System.err.println("Exception" + e.getMessage());
        }

        panel.add(labelGroups);
        panel.setLayout(null);

        frame.setSize(400, 700);
        frame.setContentPane(panel);
        //frame.getContentPane().setBackground(Style.frameColor);
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Groups groups = new Groups("blcristian2002@gmail.com");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < nrGroups; i++) {
            if (e.getSource() == btnGroup[i]) {
                //frame.dispose();
                //GroupPage groupPage = new GroupPage(btnGroup[i].getText(), username);
            }
        }
    }

}