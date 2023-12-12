import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;


public class Main extends JFrame implements ActionListener
{
    Object[][] data = {{"Diaz","088888888888","Jalan Kp.Sawah","1","Express",8000}};
    String[] namaKolom = {"Nama", "Nomor WA", "Alamat", "Berat", "Tipe", "Harga"};
    DefaultTableModel model = new DefaultTableModel(data, namaKolom);
    private JPanel panelInput = new JPanel();
    private JPanel panelData = new JPanel();
    private JPanel panelHasil = new JPanel();
    private JFrame frame = new JFrame();

    public double hitungHarga(double berat, String tipe){
        double total = berat*3000;
        if (tipe.equals("Reguler")){
            total += 2000;
        } else{ total += 4000;}
        return total;
    }

    public void panelInput(){
        GridLayout layout = new GridLayout(6,2);
        layout.setVgap(10);
        panelInput.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panelInput.setBorder(BorderFactory.createTitledBorder("Input"));
        panelInput.setLayout(layout); panelInput.setPreferredSize(new Dimension(270,100));
        JLabel labelNama = new JLabel("Nama :");
        JTextField tfNama = new JTextField();

        JLabel labelNoHP = new JLabel("Nomor WA :");
        JTextField tfNoHP = new JTextField();

        JCheckBox cbAlamat = new JCheckBox("Alamat :");
        JTextArea taAlamat = new JTextArea();taAlamat.setLineWrap(true);taAlamat.setEnabled(false);

        cbAlamat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cbAlamat.isSelected()){
                    taAlamat.setEnabled(true);
                } else { taAlamat.setEnabled(false); }
            }
        });

        JLabel labelBerat = new JLabel("Berat (KG):");
        JTextField tfBerat = new JTextField();

        JButton btnReguler = new JButton("Reguler (Dipilih)");btnReguler.setEnabled(false);
        JButton btnExpress = new JButton("Express");

        btnReguler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnReguler.setEnabled(false);btnExpress.setEnabled(true);
                btnReguler.setText("Reguler (Dipilih)");btnExpress.setText("Express");
            }
        });

        btnExpress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnExpress.setEnabled(false);btnReguler.setEnabled(true);
                btnExpress.setText("Express (Dipilih)");btnReguler.setText("Reguler");
            }
        });

        JButton btnSubmit = new JButton("Submit");

        panelInput.add(labelNama);panelInput.add(tfNama);
        panelInput.add(labelNoHP);panelInput.add(tfNoHP);
        panelInput.add(cbAlamat);panelInput.add(taAlamat);
        panelInput.add(labelBerat);panelInput.add(tfBerat);
        panelInput.add(btnReguler);panelInput.add(btnExpress);
        panelInput.add(btnSubmit);

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String alamat, tipe;
                if (cbAlamat.isSelected()){
                    alamat = taAlamat.getText();
                } else { alamat = "Ambil Sendiri";}

                if (btnReguler.isEnabled() == false) {
                    tipe = "Reguler";
                } else { tipe = "Express"; }

                Object[] tambahTabel = {
                        tfNama.getText(), tfNoHP.getText(), alamat, tfBerat.getText(),
                        tipe, hitungHarga(Double.parseDouble(tfBerat.getText()), tipe)
                };
                model.addRow(tambahTabel);

                tfNama.setText(null);tfNoHP.setText(null);taAlamat.setText(null);
                cbAlamat.setSelected(false);tfBerat.setText(null);
            }
        });
    }

    public void panelData(){
        panelData.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panelData.setBorder(BorderFactory.createTitledBorder("Data")); panelData.setLayout(new BorderLayout());
        JTable table = new JTable(model);
        JPanel panelTable = new JPanel();
        panelTable.add(new JScrollPane(table));
        panelTable.setPreferredSize(new Dimension(400,330));
        panelData.add(panelTable, BorderLayout.NORTH);

        JPanel panelPilih = new JPanel();
        panelPilih.setLayout(new BorderLayout());
        JButton btnHapus = new JButton("Hapus");

        //PANEL HASIL

        panelHasil.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelHasil.setBorder(BorderFactory.createTitledBorder("Hasil"));
        panelHasil.setLayout(new BorderLayout());

        JPanel total = new JPanel(); total.setLayout(new GridLayout(2,1));
        total.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        total.setPreferredSize(new Dimension(120,50));
        total.add(new JLabel("Total :"));
        JLabel labelTotal = new JLabel(); labelTotal.setText("0"); total.add(labelTotal);
        labelTotal.setFont(new Font("Arial", Font.PLAIN, 20));

        JPanel uang = new JPanel(); uang.setLayout(new GridLayout(1,3));
        uang.setPreferredSize(new Dimension(400,50));
        JLabel uangPembeli = new JLabel("Uang Pembeli:");
        JTextField tfUangPembeli = new JTextField();
        JButton btnHitung = new JButton("Hitung");
        uang.add(uangPembeli);uang.add(tfUangPembeli);uang.add(btnHitung);

        JPanel akhir = new JPanel(); akhir.setLayout(new GridLayout(2,1));
        akhir.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        akhir.setPreferredSize(new Dimension(120,50));
        akhir.add(new JLabel("Kembalian :"));
        JLabel labelAkhir = new JLabel(); labelAkhir.setText("0"); akhir.add(labelAkhir);
        labelAkhir.setFont(new Font("Arial", Font.PLAIN, 20));

        panelHasil.add(total, BorderLayout.WEST);
        panelHasil.add(uang, BorderLayout.CENTER);
        panelHasil.add(akhir, BorderLayout.EAST);

        // AKHIR DARI PANEL HASIL

        panelPilih.add(btnHapus, BorderLayout.NORTH);panelPilih.add(panelHasil, BorderLayout.SOUTH);
        panelData.add(panelPilih, BorderLayout.SOUTH);
        validate();

        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rowIndex = table.getSelectedRow();
                btnHapus.setText("Hapus ( "+model.getValueAt(rowIndex,0)+" )");
                labelTotal.setText(""+model.getValueAt(rowIndex,5));
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        btnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.removeRow(table.getSelectedRow());
                labelTotal.setText("0");tfUangPembeli.setText(null);labelAkhir.setText("0");
            }
        });

        btnHitung.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowIndex = table.getSelectedRow();
                String objTostr = "" + model.getValueAt(rowIndex,5);
                double hargaAkhir = Double.parseDouble(tfUangPembeli.getText()) - Double.parseDouble(objTostr);
                labelAkhir.setText(Double.toString(hargaAkhir));
            }
        });
    }

    public Main()
    {
        panelInput();
        panelData();
        frame.add(panelInput, BorderLayout.WEST);
        frame.add(panelData, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Sistem Laundry");
        frame.pack();
        frame.setSize(800,500);
        frame.setVisible(true);

    }

    //method untuk menampilkan status check box

    public void actionPerformed(ActionEvent e) {
    //set text

    }
    public static void main(String[] args) {
        new Main();
    }
}
