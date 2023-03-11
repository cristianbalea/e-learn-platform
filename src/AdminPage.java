import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;

public class AdminPage implements ActionListener {
    JFrame frame = new JFrame("Admin Page");
    JPanel panel = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();

    JLabel labelMeniu = new JLabel("Meniu:  ");
    JLabel labelDate = new JLabel("Date personale:  ");
    JLabel labelCNP = new JLabel("CNP:  ");
    JLabel labelNume = new JLabel("Nume:  ");
    JLabel labelPrenume = new JLabel("Prenume:  ");
    JLabel labelAdresa = new JLabel("Adresa:  ");
    JLabel labelNrTelefon = new JLabel("Nr. telefon:  ");
    JLabel labelEmail = new JLabel("E-mail:  ");
    JLabel labelIBAN = new JLabel("IBAN:  ");
    JLabel labelNrContract = new JLabel("Nr. contract:  ");

    JButton btnModifica = new JButton("Modifica informatiile utilizatorilor");
    JButton btnCautaUtilizatori = new JButton("Cauta utilizatori");
    JButton btnAsignare= new JButton("Asignare profesor la curs");
    JButton btnCautaCurs= new JButton("Cauta curs");
    JButton btnDelogare= new JButton("LogOut");

    private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";
    private String username;
    private JButton btnModificaCursuri = new JButton("Modifica cursul");

    public AdminPage(String username)
    {
        this.username = username;
        labelMeniu.setBounds(40, 20, 200, 30);
        btnModifica.setBounds(40, 100, 300, 30);
        btnCautaUtilizatori.setBounds(40, 130, 300, 30);
        btnAsignare.setBounds(40, 160, 300, 30);
        btnCautaCurs.setBounds(40, 190, 300, 30);
        btnDelogare.setBounds(40,250,300,30);
        btnModificaCursuri.setBounds(40, 220, 300, 30);
        labelDate.setBounds(10, 30, 300, 30);
        labelCNP.setBounds(10, 100, 500, 30);
        labelNume.setBounds(10, 140, 500, 30);
        labelPrenume.setBounds(10, 180, 500, 30);
        labelAdresa.setBounds(10, 220, 500, 30);
        labelNrTelefon.setBounds(10, 260, 500, 30);
        labelEmail.setBounds(10, 300, 500, 30);
        labelIBAN.setBounds(10, 340, 500, 30);
        labelNrContract.setBounds(10, 380, 500, 30);

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
        btnDelogare.addActionListener(this);
        btnCautaUtilizatori.addActionListener(this);
        btnAsignare.addActionListener(this);
        btnModifica.addActionListener(this);
        btnCautaCurs.addActionListener(this);
        btnModificaCursuri.addActionListener(this);
        try{
            Connection con = DriverManager.getConnection(url,uid,pw);
            Statement stmt = con.createStatement();
            stmt.execute("select * from administrator where administrator.email = \""+ username + "\";");
            ResultSet rs = stmt.getResultSet();
            while (rs.next()){
                labelCNP.setText(labelCNP.getText() + rs.getString("CNP"));
                labelNume.setText(labelNume.getText() + rs.getString("nume"));
                labelPrenume.setText(labelPrenume.getText() + rs.getString("prenume"));
                labelAdresa.setText(labelAdresa.getText() + rs.getString("adresa"));
                labelNrTelefon.setText(labelNrTelefon.getText() + rs.getString("nrTelefon"));
                labelEmail.setText(labelEmail.getText() + rs.getString("email"));
                labelIBAN.setText(labelIBAN.getText() + rs.getString("IBAN"));
                labelNrContract.setText(labelNrContract.getText() + rs.getString("nrContract"));
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
        panel1.add(btnModificaCursuri);
        panel1.add(labelMeniu);
        panel1.add(btnCautaUtilizatori);
        panel1.add(btnModifica);
        panel1.add(btnAsignare);
        panel1.add(btnCautaCurs);
        panel1.add(btnDelogare);

        panel1.setBackground(new Color(159, 182, 209));
        panel2.setBackground(new Color(159, 182, 209));

        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel2,BorderLayout.PAGE_START);

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel1.setLayout(null);
        panel2.setLayout(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setContentPane(panel);
        frame.getContentPane().setBackground(Style.frameColor);
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDelogare) {
            frame.dispose();
            WelcomePage welcomePage = new WelcomePage();
        }
        if(e.getSource() == btnModifica){
            Modify modify = new Modify(username);
        }
        if( e.getSource() == btnCautaUtilizatori){
            SearchUsers  searchUsers = new SearchUsers();
        }
        if (e.getSource() == btnAsignare){
            AsignareProfesor asignareProfesor = new AsignareProfesor();
        }
        if(e.getSource() == btnCautaCurs) {
            AdminSearchCourse adminSearchCourse = new AdminSearchCourse(username);
        }
        if(e.getSource() == btnModificaCursuri){
            ModificareCursuri modificareCursuri = new ModificareCursuri();
        }
    }
}
