import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;

public class StudentPage implements ActionListener {
    JFrame frame = new JFrame("Student Page");
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel = new JPanel();
    JButton btnCursuri = new JButton("Cursurile mele");
    JButton btnGrupuri = new JButton("Grupurile mele");
    JLabel labelMeniu = new JLabel("Meniu:");
    JLabel labelDate = new JLabel("Date personale:");
    JLabel labelCNP = new JLabel("CNP:   ");
    JLabel labelNume = new JLabel("Nume:   ");
    JLabel labelPrenume = new JLabel("Prenume:   ");
    JLabel labelAdresa = new JLabel("Adresa:   ");
    JLabel labelNrTelefon = new JLabel("Nr. telefon:   ");
    JLabel labelEmail = new JLabel("E-mail:   ");
    JLabel labelIBAN = new JLabel("IBAN:   ");
    JLabel labelNrContract = new JLabel("Nr. contract:   ");
    JLabel labelAnStudiu = new JLabel("An de studiu:   ");
    JButton btnCautaCurs = new JButton("Cautare curs");
    JButton btnInscriere = new JButton("Inscriere curs");
    JButton btnVizualizareNote = new JButton("Vizualizare note");
    JButton btnVizualizareGrupuri = new JButton("Vizualizare grupuri");
    JButton btnOrar = new JButton("Orar");
    JButton btnLogOut = new JButton("Logout");
    private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";
    String username;

    public StudentPage(String username) {
        this.username = username;
        labelMeniu.setBounds(40, 20, 200, 30);
        btnCautaCurs.setBounds(40, 100, 200, 30);
        btnInscriere.setBounds(40, 130, 200, 30);
        btnVizualizareNote.setBounds(40, 160, 200, 30);
        btnVizualizareGrupuri.setBounds(40, 190, 200, 30);
        btnOrar.setBounds(40, 220, 200, 30);
        btnCursuri.setBounds(40, 250, 200, 30);
        btnGrupuri.setBounds(40, 280, 200, 30);
        btnLogOut.setBounds(40, 310, 200, 30);
        labelDate.setBounds(0, 30, 300, 30);
        labelCNP.setBounds(0, 100, 500, 30);
        labelNume.setBounds(0, 140, 500, 30);
        labelPrenume.setBounds(0, 180, 500, 30);
        labelAdresa.setBounds(0, 220, 600, 30);
        labelNrTelefon.setBounds(0, 260, 500, 30);
        labelEmail.setBounds(0, 300, 500, 30);
        labelIBAN.setBounds(0, 340, 500, 30);
        labelNrContract.setBounds(0, 380, 500, 30);
        labelAnStudiu.setBounds(0, 420, 500, 30);


        btnLogOut.addActionListener(this);
        btnCautaCurs.addActionListener(this);
        btnInscriere.addActionListener(this);
        btnCursuri.addActionListener(this);
        btnVizualizareGrupuri.addActionListener(this);
        btnGrupuri.addActionListener(this);
        btnVizualizareNote.addActionListener(this);
        btnOrar.addActionListener(this);

        labelMeniu.setFont(Style.font1);
        labelDate.setFont(Style.font1);
        labelCNP.setFont(Style.font1);
        labelNume.setFont(Style.font1);
        labelPrenume.setFont(Style.font1);
        labelAdresa.setFont(Style.font1);
        labelNrTelefon.setFont(Style.font1);
        labelEmail.setFont(Style.font1);
        labelIBAN.setFont(Style.font1);
        labelNrContract.setFont(Style.font1);
        labelAnStudiu.setFont(Style.font1);

        try {
            Connection con = DriverManager.getConnection(url, uid, pw);
            Statement stmt = con.createStatement();
            stmt.execute("select * from student where student.email = \"" + username + "\";");
            ResultSet rs = stmt.getResultSet();
            while (rs.next()) {
                labelCNP.setText(labelCNP.getText() + rs.getString("CNP"));
                labelNume.setText(labelNume.getText() + rs.getString("nume"));
                labelPrenume.setText(labelPrenume.getText() + rs.getString("prenume"));
                labelAdresa.setText(labelAdresa.getText() + rs.getString("adresa"));
                labelNrTelefon.setText(labelNrTelefon.getText() + rs.getString("nrTelefon"));
                labelEmail.setText(labelEmail.getText() + rs.getString("email"));
                labelIBAN.setText(labelIBAN.getText() + rs.getString("IBAN"));
                labelNrContract.setText(labelNrContract.getText() + rs.getString("nrContract"));
                labelAnStudiu.setText(labelAnStudiu.getText() + rs.getString("anStudiu"));

            }
            rs.close();
            con.close();
        } catch (Exception ex) {
            System.err.println("Exception: " + ex);
        }

        panel2.add(labelDate);
        panel2.add(labelCNP);
        panel2.add(labelNume);
        panel2.add(labelPrenume);
        panel2.add(labelAdresa);
        panel2.add(labelNrTelefon);
        panel2.add(labelEmail);
        panel2.add(labelIBAN);
        panel2.add(labelNrContract);
        panel2.add(labelAnStudiu);
        panel1.add(labelMeniu);
        panel1.add(btnCautaCurs);
        panel1.add(btnInscriere);
        panel1.add(btnVizualizareNote);
        panel1.add(btnVizualizareGrupuri);
        panel1.add(btnOrar);
        panel1.add(btnCursuri);
        panel1.add(btnGrupuri);
        panel1.add(btnLogOut);
        panel.add(panel1);
        panel.add(panel2);

        panel1.setBackground(new Color(159, 182, 209));
        panel2.setBackground(new Color(159, 182, 209));

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel1.setLayout(null);
        panel2.setLayout(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setContentPane(panel);
        //frame.getContentPane().setBackground(Style.frameColor);
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        StudentPage studentPage = new StudentPage("trifdiana2001@gmail.com");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogOut) {
            frame.dispose();
            WelcomePage welcomePage = new WelcomePage();
        }
        if(e.getSource() == btnCautaCurs) {
            SearchCourse searchCourse = new SearchCourse();
        }
        if(e.getSource() == btnInscriere) {
            JoinCourse joinCourse = new JoinCourse(username);
        }
        if(e.getSource() == btnCursuri) {
            Courses courses = new Courses(username);
        }
        if(e.getSource() == btnVizualizareGrupuri) {
            SeeStudyGroups seeStudyGroups = new SeeStudyGroups(username);
        }
        if(e.getSource() == btnGrupuri) {
            Groups groups = new Groups(username);
        }
        if(e.getSource() == btnVizualizareNote) {
            StudentGrades studentGrades = new StudentGrades(username);
        }
        if(e.getSource() == btnOrar) {
            Schedule schedule = new Schedule(username);
        }

    }
}