import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class ProfessorPage implements ActionListener {

    JFrame frame = new JFrame("Profesor Page");
    JPanel panel = new JPanel();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();

    JLabel labelMeniu = new JLabel("Meniu:");
    JLabel labelDate = new JLabel("Date personale:");
    JLabel labelCNP = new JLabel("CNP:");
    JLabel labelNume = new JLabel("Nume:");
    JLabel labelPrenume = new JLabel("Prenume:");
    JLabel labelAdresa = new JLabel("Adresa:");
    JLabel labelNrTelefon = new JLabel("Nr. telefon:");
    JLabel labelEmail = new JLabel("E-mail:");
    JLabel labelIBAN = new JLabel("IBAN:");
    JLabel labelNrContract = new JLabel("Nr. contract:");
    JLabel labelDepartament = new JLabel("Departament:");

    JButton btnCursuri = new JButton("Cursurile mele");
    JButton btnGrupuriStudiu = new JButton("Grupurile mele");
    JButton btnAdaugaActivitati = new JButton("Adaugare si programare activitati");
    JButton btnModificarePonderi = new JButton("Modificare ponderi note");
    JButton btnNotareStudenti = new JButton("Notare studenti");
    JButton btnListaStudenti = new JButton("Lista studenti si descarcare catalog");
    JButton btnOrar = new JButton("Orar");
    JButton btnLogOut = new JButton("LogOut");


    private String url = "jdbc:mysql://localhost/elearn";
    private String uid = "root";
    private String pw = "parola";
    private String username;
    public ProfessorPage(String username) {
        this.username = username;

        labelMeniu.setBounds(40, 20, 200, 30);
        btnAdaugaActivitati.setBounds(40, 100, 300, 30);
        btnModificarePonderi.setBounds(40, 130, 300, 30);
        btnNotareStudenti.setBounds(40, 160, 300, 30);
        btnListaStudenti.setBounds(40, 190, 300, 30);
        btnCursuri.setBounds(40,220,300,30);
        btnGrupuriStudiu.setBounds(40,250,300,30);
        btnOrar.setBounds(40, 280, 300, 30);
        btnLogOut.setBounds(40,310,300,30);

        labelDate.setBounds(10, 30, 300, 30);
        labelCNP.setBounds(10, 100, 500, 30);
        labelNume.setBounds(10, 140, 500, 30);
        labelPrenume.setBounds(10, 180, 500, 30);
        labelAdresa.setBounds(10, 220, 500, 30);
        labelNrTelefon.setBounds(10, 260, 500, 30);
        labelEmail.setBounds(10, 300, 500, 30);
        labelIBAN.setBounds(10, 340, 500, 30);
        labelNrContract.setBounds(10, 380, 500, 30);
        labelDepartament.setBounds(10, 420, 500, 30);

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
        labelDepartament.setFont(Style.font1);

        btnLogOut.addActionListener(this::actionPerformed);
        btnAdaugaActivitati.addActionListener(this::actionPerformed);
        btnModificarePonderi.addActionListener(this);
        btnNotareStudenti.addActionListener(this);
        btnCursuri.addActionListener(this);
        btnListaStudenti.addActionListener(this);
        btnGrupuriStudiu.addActionListener(this);
        btnOrar.addActionListener(this);
        try{
            Connection con = DriverManager.getConnection(url,uid,pw);
            Statement stmt = con.createStatement();
            stmt.execute("select * from profesor where profesor.email = \""+ username + "\";");
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
                labelDepartament.setText(labelDepartament.getText() + rs.getString("Departament"));
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
        panel2.add(labelDepartament);
        panel1.add(labelMeniu);
        panel1.add(btnAdaugaActivitati);
        panel1.add(btnModificarePonderi);
        panel1.add(btnNotareStudenti);
        panel1.add(btnListaStudenti);
        panel1.add(btnOrar);
        panel1.add(btnLogOut);
        panel1.add(btnCursuri);
        panel1.add(btnGrupuriStudiu);
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel2,BorderLayout.PAGE_START);

        panel1.setBackground(new Color(159, 182, 209));
        panel2.setBackground(new Color(159, 182, 209));

        panel1.setLayout(null);
        panel2.setLayout(null);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setContentPane(panel);
        //frame.getContentPane().setBackground(Style.frameColor);
        frame.setIconImage(Style.img.getImage());
        frame.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnLogOut){
            frame.dispose();
            WelcomePage welcomePage = new WelcomePage();
        }
        if( e.getSource() == btnAdaugaActivitati){
            AddActivities addActivities = new AddActivities(username);
        }
        if ( e.getSource() == btnModificarePonderi){
            ModificarePonderi modificarePonderi = new ModificarePonderi(username);
        }
        if( e.getSource() == btnNotareStudenti){
            NotareStudenti notareStudenti = new NotareStudenti(username);
        }
        if(e.getSource() == btnCursuri){
            CursurileMele cursurileMele = new CursurileMele(username);
        }
        if(e.getSource() == btnListaStudenti){
            ListaStudenti listaStudenti = new ListaStudenti();
        }
        if(e.getSource() == btnGrupuriStudiu) {
            GroupsP groupsP = new GroupsP(username);
        }
        if(e.getSource() == btnOrar) {
            Schedule schedule = new Schedule(username);
        }
    }
}