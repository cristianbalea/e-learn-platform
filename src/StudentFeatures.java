import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class SearchCourse implements ActionListener {
    private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";
    private JFrame frame = new JFrame("Search Course");
    private JLabel labelCautare = new JLabel("Introdu numele cursului:");
    private JPanel panel = new JPanel();
    private JTextField searchText = new JTextField();
    private JButton btnSearch = new JButton("Cautare");
    private JLabel labelResults = new JLabel("0 rezultate");

    SearchCourse() {
        labelCautare.setBounds(20, 20, 200, 30);
        searchText.setBounds(20, 55, 400, 30);
        btnSearch.setBounds(20, 85, 150, 30);
        labelResults.setBounds(20, 105, 400, 700);

        btnSearch.addActionListener(this);

        panel.add(labelCautare);
        panel.add(searchText);
        panel.add(btnSearch);
        panel.add(labelResults);

        panel.setLayout(null);

        frame.setSize(600, 850);
        frame.setContentPane(panel);
        //frame.getContentPane().setBackground(Style.frameColor);
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SearchCourse searchCourse = new SearchCourse();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch) {
            String numeCurs = searchText.getText();
            try {
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                stmt.execute("select count(nume) as nrRezultate from materie where nume like \"%" + numeCurs + "%\";");
                ResultSet rs = stmt.getResultSet();
                if (rs.next() != false) {
                    labelResults.setText(rs.getString("nrRezultate") + " rezultate");
                }

                stmt.execute("select nume, descriere from materie where nume like \"%" + numeCurs + "%\";");
                rs = stmt.getResultSet();
                labelResults.setText(labelResults.getText() + "<ul>");
                while (rs.next()) {
                    labelResults.setText(labelResults.getText() + "<li>" + " <b> " + rs.getString("nume") + "</b>" + ": " +
                            "<i>" + rs.getString("descriere") + "</i>" + "</li>" + "<br>");
                }
                labelResults.setText("<html>" + "</ul>" + labelResults.getText() + "</html>");
                rs.close();
                con.close();
            } catch (Exception ex) {

            }

        }
    }
}

class JoinCourse implements ActionListener {
    String username;
    private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";
    private JFrame frame = new JFrame("Join Course");
    private JPanel panel = new JPanel();
    private JLabel labelInscriere = new JLabel("Introduceti numele cursului:");
    private JTextField cursText = new JTextField();
    private JButton btnInscriere = new JButton("Inscrie-ma");
    private JLabel labelResults = new JLabel(" ");

    JoinCourse(String username) {
        this.username = username;

        labelInscriere.setBounds(20, 20, 200, 30);
        cursText.setBounds(20, 55, 400, 30);
        btnInscriere.setBounds(20, 85, 150, 30);
        labelResults.setBounds(20, 110, 500, 30);

        panel.add(labelInscriere);
        panel.add(cursText);
        panel.add(btnInscriere);
        panel.add(labelResults);

        btnInscriere.addActionListener(this);

        panel.setLayout(null);

        frame.setSize(500, 300);
        frame.setContentPane(panel);
        //frame.getContentPane().setBackground(Style.frameColor);
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        JoinCourse joinCourse = new JoinCourse("blcristian2002@gmail.com");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnInscriere) {
            String numeCurs = cursText.getText();
            String idStudent = new String();
            try {
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                stmt.execute("select count(nume) as Materie from materie where nume like \"" + numeCurs + "\";");
                ResultSet rs = stmt.getResultSet();
                if (rs.next() == true) {
                    if (rs.getString("Materie").equals("1")) {

                        stmt.execute("select id from student where email like \"" + username + "\";");
                        rs = stmt.getResultSet();
                        if (rs.next() == true) {
                            idStudent = rs.getString("id");
                            stmt.execute("select count(*) from inscrierematerie where idStudent = " + idStudent +
                                    " and numematerie like \"" + numeCurs + "\";");
                            int alreadyJoined = -1;
                            rs = stmt.getResultSet();
                            if (rs.next()) {
                                alreadyJoined = Integer.parseInt(rs.getString("count(*)"));
                                if (alreadyJoined == 0) {
                                    stmt.executeUpdate("insert into inscrierematerie values (\"" + idStudent + "\", \"" + numeCurs + "\");");
                                    labelResults.setText("Inscriere cu succes la cursul: " + numeCurs + "!");
                                } else {
                                    JOptionPane.showMessageDialog(btnInscriere, "Esti deja inscris la curs!");
                                }
                            }
                        }
                    } else {
                        labelResults.setText("Inscriere esuata! Cursul \"" + numeCurs + "\" nu exista!");
                    }
                }

                rs.close();
                con.close();
            } catch (Exception ex) {
                System.err.println("Exception" + ex);
            }

        }
    }
}

class StudentGrades implements ActionListener {
    String username;
    String idStudent;
    JPanel panel = new JPanel();
    private String url = "jdbc:mysql://localhost:/elearn";
    private String uid = "root";
    private String pw = "parola";
    private JFrame frame = new JFrame("Grades");
    private JLabel labelMaterie = new JLabel("Introdu numele materiei:");
    private JLabel labelResults = new JLabel("");
    private JTextField searchText = new JTextField();
    private JButton btnCauta = new JButton("Vizualizare note");

    public StudentGrades(String username) {
        this.username = username;
        labelMaterie.setBounds(10, 15, 200, 30);
        labelResults.setBounds(5, 50, 500, 400);
        searchText.setBounds(20, 55, 400, 30);
        btnCauta.setBounds(20, 85, 150, 30);

        btnCauta.addActionListener(this);

        panel.add(searchText);
        panel.add(btnCauta);
        panel.add(labelMaterie);
        panel.add(labelResults);

        panel.setLayout(null);

        frame.setSize(500, 500);
        frame.setContentPane(panel);
        //frame.getContentPane().setBackground(Style.frameColor);
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        StudentGrades studentGrades = new StudentGrades("blcristian2002@gmail.com");
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCauta) {
            String numeMaterie = searchText.getText();

            try {
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                stmt.execute("select id from student where email like \"" + username + "\";");
                ResultSet rs = stmt.getResultSet();
                if (rs.next() == true) {
                    idStudent = rs.getString("id");
                    stmt.execute("select notaseminar, notalaborator, notacurs, notafinala from catalog where " +
                            "idStudent = \"" + idStudent + "\" " + "and numeMaterie = \"" + numeMaterie + "\";");
                    rs = stmt.getResultSet();

                    labelResults.setText("<ul>");
                    int i = 0;
                    if (rs.next() == true) {
                        labelResults.setText(labelResults.getText() + "<li>Nota seminar: " + rs.getString("notaseminar") +
                                "</li><br> <li> Nota laborator: " + rs.getString("notaLaborator") + "</li><br>" +
                                "<li> Nota curs: " + rs.getString("notaCurs") + "</li><br>" +
                                "<li> Nota finala: " + rs.getString("notaFinala") + "</li>");
                    }
                    labelResults.setText("<html><b>" + labelResults.getText() + "</ul></b></html>");
                    rs.close();
                    con.close();
                }

            } catch (Exception exception) {
                System.err.println("Exception" + exception);
            }

        }
    }
}

class Courses {
    String username;
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JLabel labelMaterii1 = new JLabel("Cursurile pe care le urmezi:");
    JLabel labelMaterii2 = new JLabel("");
    JScrollPane scroll = new JScrollPane();
    private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";

    Courses(String username) {
        this.username = username;
        labelMaterii1.setBounds(10, 15, 200, 30);
        labelMaterii2.setBounds(5, 50, 500, 400);


        String idStudent;
        try {
            Connection con = DriverManager.getConnection(url, uid, pw);
            Statement stmt = con.createStatement();
            stmt.execute("select id from student where email like \"" + username + "\";");
            ResultSet rs = stmt.getResultSet();
            if (rs.next() == true) {
                idStudent = rs.getString("id");
                stmt.execute("select inscrierematerie.numeMaterie from student, inscrierematerie where " +
                        "student.id = inscrierematerie.idStudent and idStudent = \"" + idStudent + "\";");
                rs = stmt.getResultSet();
                labelMaterii2.setText("<ul>");
                while (rs.next() == true) {
                    labelMaterii2.setText(labelMaterii2.getText() + "<li>" + rs.getString("numeMaterie") + "</li>" + "<br>");
                }
                labelMaterii2.setText("<html><b>" + labelMaterii2.getText() + "</ul></b></html>");
                rs.close();
                con.close();
            }

        } catch (Exception e) {
            System.err.println("Exception" + e);
        }

        panel.add(labelMaterii2);
        panel.add(labelMaterii1);
        panel.add(scroll);

        panel.setLayout(null);

        frame.setSize(500, 500);
        frame.setContentPane(panel);
        //frame.getContentPane().setBackground(Style.frameColor);
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Courses courses = new Courses("blcristian2002@gmail.com");
    }
}

class SeeStudyGroups implements ActionListener {
    JFrame frame = new JFrame("Study Groups");
    JPanel panel = new JPanel();
    JButton[] btnGroup = new JButton[14];
    JButton[] btnJoinGroup = new JButton[14];
    JLabel labelStudyGroups = new JLabel("Grupuri de studiu:");
    JLabel labelMembers = new JLabel("Membrii din grup:");
    String username;
    private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";

    public SeeStudyGroups(String username) {
        this.username = username;
        labelStudyGroups.setBounds(20, 5, 400, 30);
        labelMembers.setBounds(500, 60, 280, 700);

        labelStudyGroups.setFont(Style.font1);
        labelMembers.setFont(Style.font1);

        int y = 60;
        for (int i = 0; i < 14; i++) {
            btnGroup[i] = new JButton();
            btnJoinGroup[i] = new JButton("Inscrie-ma");
            btnGroup[i].setBounds(20, y, 300, 40);
            btnJoinGroup[i].setBounds(330, y, 150, 40);
            panel.add(btnGroup[i]);
            panel.add(btnJoinGroup[i]);
            btnGroup[i].addActionListener(this);
            btnJoinGroup[i].addActionListener(this);
            y += 40;
        }

        try {
            Connection con = DriverManager.getConnection(url, uid, pw);
            Statement stmt = con.createStatement();
            stmt.execute("select nume from materie");
            ResultSet rs = stmt.getResultSet();
            int i = 0;
            while (rs.next()) {
                btnGroup[i].setText(rs.getString("nume"));
                i++;
            }
            rs.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Exception" + e);
        }

        panel.add(labelStudyGroups);
        panel.add(labelMembers);
        panel.setLayout(null);

        frame.setSize(800, 750);
        frame.setContentPane(panel);
        //frame.getContentPane().setBackground(Style.frameColor);
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SeeStudyGroups seeStudyGroups = new SeeStudyGroups("blcristian2002@gmail.com");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 14; i++) {
            if (e.getSource() == btnGroup[i]) {
                String numeMaterie = btnGroup[i].getText();
                int id;
                try {
                    Connection con = DriverManager.getConnection(url, uid, pw);
                    Statement stmt = con.createStatement();
                    stmt.execute("select idgrupstudiu from grupstudiu where numeMaterie like \"" + numeMaterie + "\";");
                    ResultSet rs = stmt.getResultSet();
                    if (rs.next()) {
                        id = Integer.parseInt(rs.getString("idgrupstudiu"));
                        stmt.execute("select idStudent from asignaregrup where idGrupStudiu = " + id + ";");
                        rs = stmt.getResultSet();
                        labelMembers.setText("Membrii din grup: <br> <ul>");
                        while (rs.next()) {
                            Statement stmt2 = con.createStatement();
                            stmt2.execute("select nume, prenume from student where id = " + Integer.parseInt(rs.getString("idStudent")) + ";");
                            ResultSet rs2 = stmt2.getResultSet();
                            if (rs2.next()) {
                                labelMembers.setText(labelMembers.getText() + "<li>" + rs2.getString("nume") + " " +
                                        rs2.getString("prenume") + "</li> <br>");
                            }
                            rs2.close();
                        }
                        labelMembers.setText("<html>" + labelMembers.getText() + "</ul></html>");
                        rs.close();
                        con.close();
                    }

                } catch (Exception ex) {
                    System.err.println("Exception!!!" + ex.getMessage());
                }
            }
        }
        for (int i = 0; i < 14; i++) {
            if (e.getSource() == btnJoinGroup[i]) {
                String numeMaterie = btnGroup[i].getText();
                try {
                    Connection con = DriverManager.getConnection(url, uid, pw);
                    Statement stmt = con.createStatement();
                    stmt.execute("select id from student where email like \"" + username + "\";");
                    ResultSet rs = stmt.getResultSet();
                    if (rs.next()) {
                        String idStudent = rs.getString("id");
                        stmt.execute("select count(*) from inscrierematerie where numeMaterie like \"" + numeMaterie +
                                "\" and idStudent = " + idStudent + ";");
                        rs = stmt.getResultSet();
                        int ok = 0;
                        if (rs.next()) ok = Integer.parseInt(rs.getString("count(*)"));
                        if (ok == 1) {

                            stmt.execute("select idGrupStudiu from grupstudiu where numeMaterie like \"" + numeMaterie + "\";");
                            rs = stmt.getResultSet();
                            if (rs.next()) {
                                String idGrupStudiu = rs.getString("idGrupStudiu");
                                stmt.execute("select count(*) from asignaregrup where idGrupStudiu=" + idGrupStudiu + " and idStudent = " +
                                        idStudent + ";");
                                rs = stmt.getResultSet();
                                int alreadyJoined = 0;
                                if (rs.next()) {
                                    alreadyJoined = Integer.parseInt(rs.getString("count(*)"));
                                }
                                if (alreadyJoined == 0) {
                                    stmt.executeUpdate("insert into asignaregrup values(\"" + idGrupStudiu + "\", \"" + idStudent + "\");");
                                    System.out.println("Operation succes!");
                                } else {
                                    JOptionPane.showMessageDialog(btnJoinGroup[i], "Esti inscris deja in acest grup!");
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(btnJoinGroup[i], "Nu esti inscris la aceasta materie!");
                        }

                    }
                    rs.close();
                    con.close();
                } catch (Exception ex) {
                    System.err.println("Exception" + ex.getMessage());
                }
            }
        }
    }
}

class Groups implements ActionListener {
    String username;
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    JButton[] btnGroup;
    JLabel labelGroups = new JLabel("Grupurile mele");
    int nrGroups = 0;
    int idStudent = 0;
    private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";

    public Groups(String username) {
        this.username = username;
        try {
            Connection con = DriverManager.getConnection(url, uid, pw);
            Statement stmt = con.createStatement();
            stmt.execute("select id from student where email like \"" + username + "\";");
            ResultSet rs = stmt.getResultSet();
            if (rs.next()) {
                idStudent = Integer.parseInt(rs.getString("id"));
                stmt.execute("select count(*) from asignaregrup where idStudent = " + idStudent + ";");
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
            stmt.execute("select idGrupStudiu from asignaregrup where idStudent = " + idStudent + ";");
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
                frame.dispose();
                GroupPage groupPage = new GroupPage(btnGroup[i].getText(), username);
            }
        }
    }

}

class GroupPage implements ActionListener {
    String numeMaterie;
    String username;
    JFrame frame;
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel = new JPanel();
    JLabel labelGroupChat = new JLabel("Mesajele grupului");
    JLabel labelAddActivity = new JLabel("Adauga o activitate");

    JLabel labelNrParticipanti = new JLabel("Nr. minim participanti:");
    JLabel labelData = new JLabel("Data:");
    JLabel labelOra = new JLabel("Ora:");
    JLabel labelDurata = new JLabel("Durata:");
    JLabel labelDeadlineInscriere = new JLabel("Deadline inscriere:");
    JLabel labelMessage = new JLabel("Trimite un mesaj");
    JTextField nrParticipantiText = new JTextField();
    JTextField dataText = new JTextField("YYYY-MM-DD");
    JTextField oraText = new JTextField();
    JTextField durataText = new JTextField();
    JTextField deadlineInscriereText = new JTextField("YYYY-MM-DD");
    JTextField messageText = new JTextField();
    JButton btnSend = new JButton("Trimite");
    JScrollPane scrollPane;
    JButton btnAddActivity = new JButton("Adauga");
    JButton btnLeaveGroup = new JButton("Paraseste grupul de studiu");
    JTextArea messages = new JTextArea("");
    JLabel labelSuggestions = new JLabel("Sugestii participanti: ");
    private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";

    public GroupPage(String numeMaterie, String username) {
        frame = new JFrame(numeMaterie);
        scrollPane = new JScrollPane(panel2, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.numeMaterie = numeMaterie;
        this.username = username;

        labelAddActivity.setBounds(10, 10, 200, 40);
        labelGroupChat.setBounds(10, 10, 200, 40);
        labelNrParticipanti.setBounds(10, 50, 200, 40);
        nrParticipantiText.setBounds(210, 50, 200, 40);
        labelData.setBounds(10, 90, 200, 40);
        dataText.setBounds(210, 90, 200, 40);
        labelOra.setBounds(10, 130, 200, 40);
        oraText.setBounds(210, 130, 200, 40);
        labelDurata.setBounds(10, 170, 200, 40);
        durataText.setBounds(210, 170, 200, 40);
        labelDeadlineInscriere.setBounds(10, 210, 200, 40);
        deadlineInscriereText.setBounds(210, 210, 200, 40);
        btnAddActivity.setBounds(210, 250, 200, 40);
        labelMessage.setBounds(10, 350, 200, 40);
        messageText.setBounds(10, 390, 200, 40);
        btnSend.setBounds(10, 430, 200, 40);
        btnLeaveGroup.setBounds(10, 650, 200, 40);
        messages.setBounds(10, 50, 990, 2000);
        labelSuggestions.setBounds(10, 500, 400, 100);

        messages.setEditable(false);
        messages.setLineWrap(true);
        btnSend.addActionListener(this);
        btnAddActivity.addActionListener(this);
        btnLeaveGroup.addActionListener(this);

        int idGrupStudiu = 0;
        try {
            Connection con = DriverManager.getConnection(url, uid, pw);
            Statement stmt = con.createStatement();
            stmt.execute("select student.nume as Nume, student.prenume as Prenume, mesajegrup.mesaj as Mesaj from student, " +
                    "mesajegrup, grupstudiu where mesajegrup.idstudent = student.id and mesajegrup.idGrupStudiu = grupstudiu.idgrupstudiu and " +
                    "grupstudiu.numematerie like \"" + numeMaterie + "\" order by mesajegrup.data;");
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                messages.setText(messages.getText() + rs.getString("Nume") + " " + rs.getString("Prenume") + ":   " +
                        rs.getString("Mesaj") + "\n");
            }

            stmt.execute("select idGrupStudiu from grupstudiu where numematerie like \"" + numeMaterie + "\";");
            rs = stmt.getResultSet();
            if (rs.next())
                idGrupStudiu = rs.getInt("idGrupStudiu");
            stmt.execute("select idStudent, nume, prenume from inscrierematerie, student where student.id = inscrierematerie.idstudent and " +
                    "numeMaterie like \"" + numeMaterie + "\";");
            rs = stmt.getResultSet();
            int nr = 0;
            while (rs.next() && nr < 4) {
                Statement stmt2 = con.createStatement();
                stmt2.execute("select count(*) as Numar from asignaregrup where idStudent = " + rs.getString("idStudent") +
                        " and idGrupStudiu = " + idGrupStudiu + ";");
                ResultSet rs2 = stmt2.getResultSet();
                if (rs2.next())
                    if (rs2.getInt("Numar") == 0) {
                        System.out.println(rs2.getString("Numar"));
                        labelSuggestions.setText(labelSuggestions.getText() + "<br>" + rs.getString("nume") + " " + rs.getString("prenume"));
                        nr++;
                    }
            }
            labelSuggestions.setText("<html>" + labelSuggestions.getText() + "</html>");
            rs.close();
            con.close();
        } catch (Exception e) {
            System.err.println("Exception" + e.getMessage());
        }

        labelGroupChat.setFont(Style.font1);
        labelAddActivity.setFont(Style.font1);
        labelNrParticipanti.setFont(Style.font1);
        labelData.setFont(Style.font1);
        labelOra.setFont(Style.font1);
        labelDurata.setFont(Style.font1);
        labelDeadlineInscriere.setFont(Style.font1);
        labelSuggestions.setFont(Style.font1);
        panel1.add(btnLeaveGroup);
        panel1.add(labelAddActivity);
        panel1.add(labelNrParticipanti);
        panel1.add(labelData);
        panel1.add(labelOra);
        panel1.add(labelDurata);
        panel1.add(labelDeadlineInscriere);
        panel1.add(btnAddActivity);
        panel1.add(nrParticipantiText);
        panel1.add(dataText);
        panel1.add(oraText);
        panel1.add(durataText);
        panel1.add(deadlineInscriereText);
        panel1.add(labelMessage);
        panel1.add(messageText);
        panel1.add(btnSend);
        panel2.add(labelGroupChat);
        panel2.add(messages);
        panel1.add(labelSuggestions);

        panel.add(panel1);
        panel.add(scrollPane);

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel1.setLayout(null);
        panel2.setLayout(null);
        panel2.setPreferredSize(new Dimension(100, 1000));

        frame.setSize(1000, 760);
        frame.setContentPane(panel);
        //frame.getContentPane().setBackground(Style.frameColor);
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        GroupPage groupPage = new GroupPage("Algoritmi fundamentali", "cristianbalea@gmail.com");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSend) {
            int idGrupStudiu = 0;
            int idStudent = 0;
            String mesaj = messageText.getText();
            try {
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
                stmt.execute("select id from student where email like \"" + username + "\";");
                ResultSet rs = stmt.getResultSet();
                if (rs.next()) {
                    idStudent = Integer.parseInt(rs.getString("id"));
                    stmt.execute("select idGrupStudiu from grupstudiu where numeMaterie like \"" + numeMaterie + "\"");
                    rs = stmt.getResultSet();
                    if (rs.next()) {
                        idGrupStudiu = Integer.parseInt(rs.getString("idGrupStudiu"));
                        stmt.executeUpdate("insert into mesajegrup(idGrupStudiu, idStudent, data, mesaj) values(\"" +
                                idGrupStudiu + "\", \"" + idStudent + "\", \"2000-01-01\", \"" + mesaj + "\");");
                        stmt.execute("select nume, prenume from student where email like \"" + username + "\";");
                        rs = stmt.getResultSet();
                        if (rs.next()) {
                            messages.append(rs.getString("nume") + " " + rs.getString("prenume") + ":  " + messageText.getText() + "\n");
                        }

                    }
                }
                rs.close();
                con.close();
            } catch (Exception ex) {
                System.err.println("Exception" + ex.getMessage());
            }
        }
        if (e.getSource() == btnAddActivity) {
            try {
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                stmt.execute("select idGrupStudiu from grupstudiu where numematerie like \"" + numeMaterie + "\";");
                ResultSet rs = stmt.getResultSet();
                if (rs.next()) {
                    int idGrupStudiu = rs.getInt("idGrupStudiu");
                    stmt.executeUpdate("insert into activitategrup(idgrupstudiu, nrminimparticipanti, deadlineinscriere, oraactivitatii, dataactivitatii, durataActivitatii)" +
                            " values(\"" + rs.getString("idgrupstudiu") + "\", \"" + nrParticipantiText.getText() + "\", \"" + deadlineInscriereText.getText() +
                            "\", \"" + oraText.getText() + "\", \"" + dataText.getText() + "\", \"" + durataText.getText() + "\");");
                    int idActivitate = -1, idStudent = -1;
                    stmt.execute("select idActivitate from activitategrup where idGrupStudiu = " + idGrupStudiu + " and oraActivitatii = " + oraText.getText() +
                            " and dataActivitatii like \"" + dataText.getText() + "\";");
                    rs = stmt.getResultSet();
                    if (rs.next()) {
                        System.out.println("aici");
                        idActivitate = rs.getInt("idActivitate");
                        stmt.execute("select id from student where email like \"" + username + "\";");
                        rs = stmt.getResultSet();
                        if (rs.next()) {
                            idStudent = rs.getInt("id");
                            stmt.executeUpdate("insert into participareactivitatigrup values(" + idStudent + ", " + idActivitate + ");");
                            JOptionPane.showMessageDialog(btnAddActivity, "Activitate adaugata cu succes!");
                        }
                    }
                }
                rs.close();
                con.close();
            } catch (Exception ex) {
                System.err.println("Exception" + ex.getMessage());
            }
        }
        if (e.getSource() == btnLeaveGroup) {
            try {
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                int idGrupStudiu = -1, idStudent = -1;
                stmt.execute("select idGrupStudiu from grupstudiu where numematerie like \"" + numeMaterie + "\";");
                ResultSet rs = stmt.getResultSet();
                if (rs.next()) {
                    idGrupStudiu = rs.getInt("idGrupStudiu");
                    stmt.execute("select id from student where email like \"" + username + "\";");
                    rs = stmt.getResultSet();
                    if (rs.next()) {
                        idStudent = rs.getInt("id");
                        stmt.executeUpdate("delete from asignaregrup where idStudent = " + idStudent + " and idGrupStudiu = " + idGrupStudiu + ";");
                        frame.dispose();
                    }
                }
            } catch (Exception ex) {
                System.err.println("Exception! " + ex.getMessage());
            }
        }
    }
}


