import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.LinkedList;

public class Schedule implements ActionListener {
    String username;
    JFrame frame = new JFrame("Schedule");
    JTable table;
    JScrollPane scrollPane;
    JPanel panel = new JPanel();
    JPanel panel1 = new JPanel();
    String[][] data = new String[100][100];
    String[] columnNames = {"ID Activitate", "Materie", "Tip activitate", "Data", "Ora", "Durata"};

    JLabel labelInscrieActivitate = new JLabel("Inscrie-te la o activitate!");
    JLabel labelInscrieActivitateProfesor = new JLabel("Inscrie-te la o activitate de grup!");
    JLabel labelIdActivitate = new JLabel("Introdu ID-ul activitatii:");
    JTextField idActivitateText = new JTextField();
    JButton btnInscriere = new JButton("Inscrie-ma");
    JButton btnInscriereAll = new JButton("Inscrie-ma la toate activitatile");
    int student = 0;
    int nrOfActivities;
    private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";

    public Schedule(String username) {
        this.username = username;
        student = 0;
        try {
            Connection con = DriverManager.getConnection(url, uid, pw);
            Statement stmt = con.createStatement();
            stmt.execute("select count(*) from student where email like \"" + username + "\";");
            ResultSet rs = stmt.getResultSet();
            if (rs.next())
                student = rs.getInt("count(*)");
            rs.close();
            con.close();
        } catch (Exception ex) {
            System.err.println("Exception! " + ex.getMessage());
        }

        labelInscrieActivitate.setBounds(5, 5, 300, 30);
        labelInscrieActivitateProfesor.setBounds(5, 5, 300, 30);
        labelIdActivitate.setBounds(5, 60, 200, 30);
        idActivitateText.setBounds(205, 60, 200, 30);
        btnInscriere.setBounds(205, 90, 200, 30);
        btnInscriereAll.setBounds(5, 150, 200, 30);
        labelInscrieActivitate.setFont(Style.font1);
        labelInscrieActivitateProfesor.setFont(Style.font1);
        labelIdActivitate.setFont(Style.font1);

        btnInscriere.addActionListener(this);
        btnInscriereAll.addActionListener(this);
        if (student == 0) {
            panel1.add(labelInscrieActivitateProfesor);
        }
        if (student != 0) {
            panel1.add(labelInscrieActivitate);
            panel1.add(btnInscriereAll);
        }
        panel1.add(labelIdActivitate);
        panel1.add(idActivitateText);
        panel1.add(btnInscriere);

        table = new JTable(data, columnNames);
        scrollPane = new JScrollPane(table);
        scrollPane.setMinimumSize(new Dimension(1000, 500));
        scrollPane.setMaximumSize(new Dimension(1000, 500));
        if (student == 1) {
            try {
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                stmt.execute("select orar.id, orar.numematerie, orar.tip, orar.data, orar.ora, orar.durata " +
                        "from orar, inscrierematerie, student " +
                        "where data >= curdate() and inscrierematerie.idStudent = student.id and student.email like \"" + username + "\" and orar.numematerie = inscrierematerie.numematerie" +
                        " order by orar.data, orar.ora;");
                ResultSet rs = stmt.getResultSet();
                int i = 0;
                while (rs.next()) {
                    data[i] = new String[]{rs.getString("id"), rs.getString("numeMaterie"), rs.getString("tip"), rs.getString("data"),
                            rs.getString("ora"), rs.getString("durata")};
                    i++;
                }
                nrOfActivities = i;
                rs.close();
                con.close();
            } catch (Exception e) {
                System.err.println("Exception! " + e.getMessage());
            }
        } else {
            try {
                Connection con = DriverManager.getConnection(url, uid, pw);
                Statement stmt = con.createStatement();
                stmt.execute("select * from orar where data >= curdate() order by data;");
                ResultSet rs = stmt.getResultSet();
                int i = 0;
                while (rs.next()) {
                    data[i] = new String[]{rs.getString("id"), rs.getString("numeMaterie"), rs.getString("tip"), rs.getString("data"),
                            rs.getString("ora"), rs.getString("durata")};
                    i++;
                }
                rs.close();
                con.close();
            } catch (Exception e) {
                System.err.println("Exception! " + e.getMessage());
            }
        }
        System.out.println(table.getRowCount());
        panel1.setLayout(null);
        panel.add(scrollPane);
        panel.add(panel1);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        frame.setContentPane(panel);
        frame.setSize(1000, 800);
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Schedule schedule = new Schedule("cristianbalea@gmail.com");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (student != 0) {
            if (e.getSource() == btnInscriere) {
                try {
                    Connection con = DriverManager.getConnection(url, uid, pw);
                    Statement stmt = con.createStatement();
                    stmt.execute("select tip from orar where id = " + idActivitateText.getText() + ";");
                    ResultSet rs = stmt.getResultSet();
                    if (rs.next()) {
                        if (rs.getString("tip").equals("grupStudiu")) {
                            stmt.execute("select id from student where email like \"" + username + "\";");
                            rs = stmt.getResultSet();
                            if (rs.next()) {
                                int idStudent = rs.getInt("id");
                                stmt.execute("select activitategrup.idActivitate from activitategrup, orar " +
                                        "where orar.id = " + idActivitateText.getText() + " and activitategrup.dataActivitatii = orar.data and activitategrup.oraActivitatii = orar.ora;");
                                rs = stmt.getResultSet();
                                if (rs.next()) {
                                    int idActivitate = rs.getInt("idActivitate");
                                    stmt.executeUpdate("insert into participareactivitatigrup values (" + idStudent + ", " + idActivitate + ");");
                                    stmt.executeUpdate("update activitategrup set nrParticipantiInscrisi = nrParticipantiInscrisi + 1 where idActivitate = " + idActivitate + ";");
                                    JOptionPane.showMessageDialog(btnInscriere, "Inscriere cu succes!");
                                }
                            }
                        } else {
                            stmt.execute("select id from student where email like \"" + username + "\";");
                            rs = stmt.getResultSet();
                            if (rs.next()) {
                                int idStudent = rs.getInt("id");
                                stmt.execute("select orarActivitati.idActivitate from oraractivitati, orar " +
                                        "where orar.id = " + idActivitateText.getText() + " and oraractivitati.data = orar.data and oraractivitati.ora = orar.ora and orar.numematerie like oraractivitati.numeMaterie;");
                                rs = stmt.getResultSet();
                                if (rs.next()) {
                                    int idActivitate = rs.getInt("idActivitate");
                                    stmt.executeUpdate("insert into participareactivitati values (" + idStudent + ", " + idActivitate + ");");
                                    stmt.executeUpdate("update oraractivitati set nrParticipanti = nrParticipanti + 1 where idActivitate = " + idActivitate + ";");
                                    JOptionPane.showMessageDialog(btnInscriere, "Inscriere cu succes!");
                                }
                            }
                        }
                    }
                    rs.close();
                    con.close();
                } catch (Exception ex) {
                    System.err.println("Exception!" + ex.getMessage());
                    JOptionPane.showMessageDialog(btnInscriere, "Deja te-ai inscris la aceasta activitate!");
                }
            }
            if (e.getSource() == btnInscriereAll) {
                LinkedList<String> whiteList = new LinkedList();
                for(int i = 0; i < nrOfActivities; i++) {
                    whiteList.add(String.valueOf(table.getValueAt(i, 0)));
                }
                int i = 0;
                while (i < nrOfActivities - 1) {
                    String data1 = (String) table.getValueAt(i, 3);
                    String ora1 = (String) table.getValueAt(i, 4);
                    String data2 = (String) table.getValueAt(i + 1, 3);
                    String ora2 = (String) table.getValueAt(i + 1, 4);

                    LinkedList<String> Activities = new LinkedList();
                    if (data1.equals(data2) && ora1.equals(ora2) && i < nrOfActivities - 1)
                        Activities.add(String.valueOf(table.getValueAt(i, 0)));
                    int ok = 0;
                    while (data1.equals(data2) && ora1.equals(ora2) && i < nrOfActivities - 1) {
                        Activities.add(String.valueOf(table.getValueAt(i + 1, 0)));
                        System.out.println(data1 + " " + ora1 + " " + data2 + " " + ora2);
                        i++;
                        data1 = data2;
                        ora1 = ora2;
                        data2 = (String) table.getValueAt(i + 1, 3);
                        ora2 = (String) table.getValueAt(i + 1, 4);
                        ok = 1;
                    }

                    if (Activities.isEmpty() == false) {
                        String message = "Activitatile cu ID-urile: ";
                        for (String a : Activities) {
                            whiteList.remove(a);
                            message += a;
                            message += " ";
                        }
                        message = message + "se suprapun. Va rugam inscrieti-va manual la activitatea dorita.";
                        JOptionPane.showMessageDialog(btnInscriereAll, message);
                    }
                    if (ok == 0) i++;
                }
                for(int j = 0; j < nrOfActivities; j++) {
                    if(whiteList.contains(table.getValueAt(j, 0))) {
                        if(table.getValueAt(j,2).equals("grupStudiu")) {
                            try {
                                Connection con = DriverManager.getConnection(url, uid, pw);
                                Statement stmt = con.createStatement();
                                stmt.execute("select idGrupStudiu from grupstudiu where numematerie like \"" + table.getValueAt(j, 1) + "\";");
                                ResultSet rs = stmt.getResultSet();
                                int idGrupStudiu = -1;
                                if(rs.next()) {
                                    idGrupStudiu = rs.getInt("idGrupStudiu");
                                    stmt.execute("select idActivitate from activitategrup where idgrupstudiu = " + idGrupStudiu + " and dataActivitatii = \"" +
                                            table.getValueAt(j, 3) + "\" and oraActivitatii = " + table.getValueAt(j, 4) + ";");
                                    rs = stmt.getResultSet();
                                    if(rs.next()) {
                                        int idActivitate = -1;
                                        idActivitate = rs.getInt("idActivitate");
                                        stmt.execute("select id from student where email like \"" + username + "\";");
                                        rs = stmt.getResultSet();
                                        if(rs.next()) {
                                            int idStudent = -1;
                                            idStudent = rs.getInt("id");
                                            stmt.executeUpdate("insert into participareactivitatigrup values(" + idStudent + ", " + idActivitate + ");");

                                        }
                                    }
                                }
                                rs.close();
                                con.close();
                            } catch(Exception ex) {
                                System.err.println("Exception! " + ex.getMessage());
                            }
                        }
                        else {
                            try {
                                Connection con = DriverManager.getConnection(url, uid, pw);
                                Statement stmt = con.createStatement();
                                int idStudent = -1;
                                stmt.execute("select id from student where email like \"" + username + "\";");
                                ResultSet rs = stmt.getResultSet();
                                if(rs.next()) {
                                    System.out.println(" ! ");
                                    idStudent = rs.getInt("id");
                                    stmt.execute("select idActivitate from oraractivitati where numematerie like \"" + table.getValueAt(j, 1) + "\" and" +
                                            " tip like \"" + table.getValueAt(j, 2) + "\" and data like \"" + table.getValueAt(j, 3) +"\" and " +
                                            "ora = " + table.getValueAt(j, 4) + ";");
                                    rs = stmt.getResultSet();
                                    int idActivitate = -1;
                                    if(rs.next()) {
                                        idActivitate = rs.getInt("idActivitate");
                                        stmt.executeUpdate("insert into participareactivitati values(" + idStudent + ", " + idActivitate + ");");
                                    }
                                }
                                rs.close();
                                con.close();
                            } catch (Exception ex) {
                                System.err.println("");
                            }
                        }

                    }
                }
            }
        } else {
            if (e.getSource() == btnInscriere) {
                try {
                    Connection con = DriverManager.getConnection(url, uid, pw);
                    Statement stmt = con.createStatement();
                    stmt.execute("select tip from orar where id = " + idActivitateText.getText() + ";");
                    ResultSet rs = stmt.getResultSet();
                    if (rs.next()) {
                        if (rs.getString("tip").equals("grupStudiu")) {
                            stmt.execute("select id from profesor  where email like \"" + username + "\";");
                            System.out.println("of");
                            rs = stmt.getResultSet();
                            if (rs.next()) {
                                System.out.println("maa");
                                int idProfesor = rs.getInt("id");
                                stmt.execute("select activitategrup.idActivitate, activitategrup.dataActivitatii from activitategrup, orar " +
                                        "where orar.id = " + idActivitateText.getText() + " and activitategrup.dataActivitatii = orar.data and activitategrup.oraActivitatii = orar.ora;");
                                rs = stmt.getResultSet();
                                if (rs.next()) {
                                    System.out.println("--");
                                    int idActivitate = rs.getInt("idActivitate");
                                    String dataA = rs.getString("dataActivitatii");
                                    System.out.println("!!!!");
                                    stmt.executeUpdate("insert into participareactivitatigrupprofesor values (" + idProfesor + ", " + idActivitate + ");");

                                    stmt.executeUpdate("update activitategrup set nrParticipantiInscrisi = nrParticipantiInscrisi + 1 where idActivitate = " + idActivitate + ";");
                                    stmt.execute("select nume, prenume from profesor where email like \"" + username + "\";");
                                    rs = stmt.getResultSet();
                                    if (rs.next()) {
                                        String nume = rs.getString("nume") + " " + rs.getString("prenume");
                                        stmt.execute("select activitategrup.idGrupStudiu from activitategrup where idActivitate = " + idActivitate + ";");
                                        rs = stmt.getResultSet();
                                        if (rs.next()) {
                                            int idGrupStudiu = rs.getInt("idGrupStudiu");
                                            stmt.executeUpdate("insert into asignaregrupprofesor values(" + idGrupStudiu + ", " + idProfesor + ");");

                                            stmt.executeUpdate("insert into mesajeGrup values(" + idGrupStudiu + ", 1000, sysdate(), \"Profesorul " +
                                                    nume + " va participa la activitatea din data de " + dataA + ".\");");
                                        }
                                    }
                                    JOptionPane.showMessageDialog(btnInscriere, "Inscriere cu succes!");
                                }
                            }
                        }
                    }
                    rs.close();
                    con.close();
                } catch (Exception ex) {
                    System.err.println("Exception!" + ex.getMessage());
                    JOptionPane.showMessageDialog(btnInscriere, "Deja te-ai inscris la aceasta activitate!");
                }
            }
        }
    }
}
