/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smkyaop;
import database.Database;
import helper.Helper;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
/**
 *
 * @author Bunga Permatasari
 */
public class MengajarPegawai extends javax.swing.JFrame {

    /**
     * Creates new form JabatanForm
     */
    public MengajarPegawai() {
        initComponents();
        loadData("");
        active(true);
        tfIdPiket.setText(generateId());

        
    }
    
    public void setFromPegawai(String id){
        tfIdPegawai.setText(id);
    }
    
    
    private String generateId(){
        String str = "";
        String query = "SELECT idmengajar FROM mengajar ORDER BY idmengajar DESC";
        try{
            PreparedStatement ps = Database.getConn().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int nomer = Integer.parseInt(rs.getString("idmengajar").substring(2))+1;
                String nol = String.format("%04d", nomer);
                str = "MJ"+nol;
            }else{
                str = "MJ0001";
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            str = "";
        }
        return str;
    }
    
    private void loadData(String keys){
      String[] header= {"ID Mengajar","Tanggal","ID PEgawai","Nama Pegawai","Jenis SKS","Jam","SKS","Total Harian","Keterangan"};
      DefaultTableModel tabmode = new DefaultTableModel (null, header);
      table.setModel(tabmode);
      table.setRowHeight(40);
      String cari = "";
      String sql = "";
      if(keys != ""){
          cari = " WHERE pegawai.nip LIKE '%"+keys+"%' OR pegawai.namapegawai LIKE '%"+keys+"%' ";
      }
        sql = "SELECT mengajar.*,pegawai.namapegawai as np FROM pegawai INNER JOIN mengajar ON pegawai.idpegawai = mengajar.idpegawai ";
          DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
          renderer.setHorizontalAlignment(0);          
                  table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);      
      try {
          java.sql.Connection conn = database.Database.getConn();
          java.sql.Statement stat = conn.createStatement();
          ResultSet hasil = stat.executeQuery(sql+cari);
          int index = 0;
          while(hasil.next()){
//{"ID Mengajar","Tanggal","ID PEgawai","Nama Pegawai","Jenis SKS","Jam","SKS","Total Harian","Keterangan"};           
              SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", new Locale("id","ID"));
                java.util.Date date = new java.util.Date(hasil.getDate(2).getTime());
              SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm", new Locale("id","ID"));
                java.util.Date dateMasuk = new java.util.Date(hasil.getTime(5).getTime());
                java.util.Date dateSelesai = new java.util.Date(hasil.getTime(6).getTime());
                String tgl = sdf.format(date);
                String masuk = sdf2.format(dateMasuk);
                String selesai = sdf2.format(dateSelesai);

              Object[] data={hasil.getString(1)
                      ,tgl
                      ,hasil.getString("idpegawai")
                      ,hasil.getString(10)
                      ,hasil.getString(4)
                      ,masuk+" - "+selesai,hasil.getString(7),hasil.getString(8),hasil.getString(9)};
              tabmode.addRow(data);
          }
      }catch(SQLException e){   
          e.printStackTrace();
      }
    }
    
    private void clear(){
        tfTotal.setText("0");
        tfIdPegawai.setText("");
        tfIdPiket.setText("");
        tfTgl.setDate(null);
        tfMasuk.setText("");
        taKet.setText("");
        tfSelesai.setText("");
        comboJenis.setSelectedIndex(0);
        tfSks.setText("0");
    }
    
    private void firetable(){
        DefaultTableModel dtm = (DefaultTableModel)table.getModel();
        dtm.fireTableDataChanged();
    }
    
    private void active(boolean b){
        btnSave.setEnabled(b);
        btnDelete.setEnabled(!b);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        tfIdPegawai = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        tfCari = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnDelete = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tfTgl = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tfIdPiket = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tfMasuk = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        taKet = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        comboJenis = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        tfSelesai = new javax.swing.JTextField();
        tfSks = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        tfTotal = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Form Mengajar Pegawai");

        jPanel1.setBackground(new java.awt.Color(25, 59, 104));

        jPanel2.setBackground(new java.awt.Color(20, 165, 255));

        jLabel1.setFont(new java.awt.Font("Agency FB", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(25, 59, 104));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TENDIK MENGAJAR");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(20, 165, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(25, 59, 104));
        jLabel2.setText("ID Pegawai");

        tfIdPegawai.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        tfIdPegawai.setEnabled(false);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        table.getTableHeader().setReorderingAllowed(false);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        btnSave.setBackground(new java.awt.Color(255, 255, 255));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
        btnSave.setText("SIMPAN");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(255, 255, 255));
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancel.png"))); // NOI18N
        btnCancel.setText("BATAL");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnBack.setBackground(new java.awt.Color(255, 255, 255));
        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/kembali.png"))); // NOI18N
        btnBack.setText("KEMBALI");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        tfCari.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tfCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfCariActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(25, 59, 104));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/caricari.png"))); // NOI18N

        btnDelete.setBackground(new java.awt.Color(255, 255, 255));
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnDelete.setText("HAPUS");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        jLabel3.setText("Masukkan Nama / NIP");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(25, 59, 104));
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/caricari.png"))); // NOI18N
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(25, 59, 104));
        jLabel4.setText("Tanggal");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(25, 59, 104));
        jLabel5.setText("ID Mengajar");

        tfIdPiket.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        tfIdPiket.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(25, 59, 104));
        jLabel8.setText("Jam Masuk");

        tfMasuk.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N

        taKet.setColumns(20);
        taKet.setRows(5);
        jScrollPane2.setViewportView(taKet);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(25, 59, 104));
        jLabel9.setText("Keterangan");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(25, 59, 104));
        jLabel10.setText("Jenis");

        comboJenis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--- Pilih ----", "Reguler", "Industri" }));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(25, 59, 104));
        jLabel11.setText("SKS");

        tfSelesai.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        tfSelesai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfSelesaiActionPerformed(evt);
            }
        });

        tfSks.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        tfSks.setEnabled(false);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(25, 59, 104));
        jLabel12.setText("Jam Selesai");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(25, 59, 104));
        jLabel13.setText("Total Harian");

        tfTotal.setFont(new java.awt.Font("Tahoma", 0, 19)); // NOI18N
        tfTotal.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(tfIdPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7))
                            .addComponent(jLabel5)
                            .addComponent(tfIdPiket, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(53, 53, 53))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(198, 198, 198)
                                .addComponent(btnSave))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel10))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(comboJenis, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(58, 58, 58)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(400, 400, 400))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(btnCancel)
                        .addGap(54, 54, 54)
                        .addComponent(btnBack)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(tfMasuk, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(tfTgl, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel11)
                                .addComponent(tfSelesai, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(tfSks, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel13)
                                .addComponent(tfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(75, 75, 75)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfCari, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 908, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(118, 118, 118))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tfCari, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3))
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfIdPiket, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfMasuk, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(tfSelesai, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfTgl, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfSks, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(53, 53, 53)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfIdPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboJenis, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 953, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        String idpegawai = tfIdPegawai.getText();
        String idpiket = tfIdPiket.getText();
        java.util.Date tgl = tfTgl.getDate();
        String masuk = tfMasuk.getText();
        String selesai = tfSelesai.getText();
        String keterangan = taKet.getText();
        String jenis = comboJenis.getSelectedItem().toString();
        String totalHarian = tfTotal.getText();
        String sks = tfSks.getText();
        if(idpegawai.isEmpty() || idpiket.isEmpty() || tgl == null || masuk.isEmpty()
                || selesai.isEmpty() || keterangan.isEmpty() || jenis.contains("---") 
                || totalHarian.isEmpty() || sks.isEmpty()){
            Helper.alert("Isi Form Dahulu!");
            return;
        }
        
        String query = "INSERT INTO mengajar VALUES(?,?,?,?,?,?,?,?,?)";
        try{
            java.util.Date masukS = null,keluarS = null;
            try {
                 masukS = new SimpleDateFormat("HH:mm",new java.util.Locale("id","ID")).parse(masuk);                
                 keluarS = new SimpleDateFormat("HH:mm",new java.util.Locale("id","ID")).parse(selesai);
            } catch (ParseException ex) {
                Helper.alert("salah input jam!");
                return;
            }
            
            PreparedStatement ps = Database.getConn().prepareStatement(query);
            ps.setString(1, idpiket);
            ps.setDate(2, new java.sql.Date(tgl.getTime()));
            ps.setInt(3, Integer.parseInt(idpegawai));
            ps.setString(4, jenis);
            ps.setTime(5, new java.sql.Time(masukS.getTime()));
            ps.setTime(6, new java.sql.Time(keluarS.getTime()));
            ps.setInt(7, Integer.parseInt(sks));
            ps.setInt(8, Integer.parseInt(totalHarian));
            ps.setString(9,keterangan);
            int rs = ps.executeUpdate();
            if(rs > 0 ){
                Helper.alert("Berhasil dimasukkan!");
            }
        }catch(SQLException ex ){
            ex.printStackTrace();
        }
        
        loadData("");
        clear();
        firetable();
        tfIdPiket.setText(generateId());
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tfCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfCariActionPerformed
        // TODO add your handling code here:
        String keys = tfCari.getText();
        if(keys.isEmpty()){
            loadData("");
            return;
        }
        loadData(keys);
    }//GEN-LAST:event_tfCariActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        // TODO add your handling code here:
        int row = table.getSelectedRow();
        if(row < 0) return;
        active(false);
//{"ID Mengajar","Tanggal","ID PEgawai","Nama Pegawai","Jenis SKS","Jam","SKS","Total Harian","Keterangan"};         
        String idpiket = table.getValueAt(row, 0).toString();
        String tanggal = table.getValueAt(row, 1).toString();
        String idpegawai = table.getValueAt(row, 2).toString();
        String jenis = table.getValueAt(row, 4).toString();
        String jam = table.getValueAt(row, 5).toString();
        String masuk = jam.split("-")[0].trim();
        String keluar = jam.split("-")[1].trim();
        String sks = table.getValueAt(row,6).toString();
        String totalharian = table.getValueAt(row,7).toString();
        String ketr = table.getValueAt(row,8).toString();
        try {
            java.util.Date tgl = new SimpleDateFormat("dd MMMM yyyy",new java.util.Locale("id","ID")).parse(tanggal);
            tfIdPiket.setText(idpiket);
            tfTgl.setDate(tgl);
            tfIdPegawai.setText(idpegawai);
            comboJenis.setSelectedItem(jenis);
            tfMasuk.setText(masuk);
            tfSelesai.setText(keluar);
            tfSks.setText(sks);
            tfTotal.setText(totalharian);
            taKet.setText(ketr);

        } catch (ParseException ex) {
            Logger.getLogger(MengajarPegawai.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tableMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int row = table.getSelectedRow();
        if(row < 0) {
            Helper.alert("Pilih Data Dahulu!");
            return;
        }
        
        int tanya = Helper.tanya("Apakah anda ingin menghapus data inich ?");
        if(tanya == JOptionPane.YES_OPTION){        
            String id = tfIdPiket.getText();
            String query = "DELETE FROM mengajar WHERE idmengajar = ?";
            try{
                Connection conn = Database.getConn();
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1,id);

                int rs = ps.executeUpdate();
                if(rs > 0 ){
                    Helper.alert("Berhasil dihapus!");
                }

            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
        loadData("");
        clear();
        firetable();       
        active(true);
        tfIdPiket.setText(generateId());        
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        clear();
        loadData("");
        firetable();
        active(true);
        tfIdPiket.setText(generateId());
    }//GEN-LAST:event_btnCancelActionPerformed

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        HelperTable1.mengajar = this;
        HelperTable1 form = new HelperTable1();
        form.setVisible(true);
    }//GEN-LAST:event_jLabel7MouseClicked

    private void tfSelesaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfSelesaiActionPerformed
        try {
            // TODO add your handling code here:
            String masuk = tfMasuk.getText();
            String selesai = tfSelesai.getText();
            
            if(masuk.isEmpty() || selesai.isEmpty() || !masuk.contains(":") || !selesai.contains(":") ){
                Helper.alert("Isi jam masuk dan selesai!");
                return;
            }
            
            java.util.Date masukS = new SimpleDateFormat("HH:mm",new java.util.Locale("id","ID")).parse(masuk);
            java.util.Date keluarS = new SimpleDateFormat("HH:mm",new java.util.Locale("id","ID")).parse(selesai);
            long toDetik = Math.abs(masukS.getTime() - keluarS.getTime())/1000;
            long satuJam = 3600;
            int index = comboJenis.getSelectedIndex();
            if(index == 0){
                Helper.alert("Pilih Jenis Sks dulu!");
                return;
            }
            int harian = 0;
            if(index == 1) harian = 25000; 
            else harian = 30000;
            
            int sks = (int)toDetik/ (int)satuJam;
            tfSks.setText(sks+"");
            tfTotal.setText((harian*sks)+"");
        } catch (ParseException ex) {
            Helper.alert("Masukkan jam seperti , ini 11:10");
            tfMasuk.setText("");
            tfSelesai.setText("");
            tfSelesai.requestFocus();
            Logger.getLogger(MengajarPegawai.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tfSelesaiActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MengajarPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MengajarPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MengajarPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MengajarPegawai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MengajarPegawai().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> comboJenis;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea taKet;
    private javax.swing.JTable table;
    private javax.swing.JTextField tfCari;
    private javax.swing.JTextField tfIdPegawai;
    private javax.swing.JTextField tfIdPiket;
    private javax.swing.JTextField tfMasuk;
    private javax.swing.JTextField tfSelesai;
    private javax.swing.JTextField tfSks;
    private com.toedter.calendar.JDateChooser tfTgl;
    private javax.swing.JTextField tfTotal;
    // End of variables declaration//GEN-END:variables
}
