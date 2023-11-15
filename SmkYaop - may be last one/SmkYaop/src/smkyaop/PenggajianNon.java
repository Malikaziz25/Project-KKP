/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smkyaop;
import java.sql.*;
import database.Database;
import helper.Helper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Bunga Permatasari
 */
public class PenggajianNon extends javax.swing.JFrame {

    /**
     * Creates new form PenggajianNon
     */
    
    private int totalpendidikan = 0,gajipokok = 0,sksreg = 0,totalsksreg= 0 ,sksindustri = 0,totalsksindustri = 0,tunjabatan = 0,tunwalas = 0,jmlTran = 0,tottransport = 0,jmlPiket = 0,totpiket = 0,tunmasakerja = 0,tunbpjskesehatan = 0,totpotongan;
    private boolean isgty = false,iswalas = false;
    private long totgaji = 0;
    public PenggajianNon() {
        initComponents();
        generateId();
        loadData("");
    }
    
    private void generateId(){
        String str = "";
        String query = "SELECT idgajian FROM gajiannon ORDER BY idgajian DESC";
        try{
            PreparedStatement ps = Database.getConn().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int nomer = Integer.parseInt(rs.getString("idgajian").substring(3))+1;
                String nol = String.format("%04d", nomer);
                tfGaji.setText("PTN"+nol);
            }else{
                tfGaji.setText("PTN0001");
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            str = "";
        }
    }
    
    private void loadData(String keys){
        String[] header = {"ID Penggajian","ID Pegawai","Nama Pegawai","Tanggal Periode","Total Gaji","Total Potongan","Dibayarkan"};
        DefaultTableModel dtm = new DefaultTableModel(null,header);
        table.setModel(dtm);
        String cari = "";
        try{
            String query = "SELECT gajiannon.*,pegawai.namapegawai FROM pegawai INNER JOIN gajiannon ON "
                    + "pegawai.idpegawai = gajiannon.idpegawai ";
            if(keys != ""){
                cari +=" WHERE pegawai.namapegawai LIKE '%"+keys+"%' OR gajiantendik.idgajian LIKE '%"+keys+"%' ";
            }
            
            PreparedStatement ps = Database.getConn().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String tgl1 = new SimpleDateFormat("dd MMMMM yyyy", new java.util.Locale("id","ID")).format(rs.getDate(2));
                String tgl2 = new SimpleDateFormat("dd MMMMM yyyy", new java.util.Locale("id","ID")).format(rs.getDate(3));
                Object[] obj = {rs.getString(1)
                        ,rs.getString(4)
                        ,rs.getString(8)
                        ,tgl1 + " - " + tgl2
                        ,rs.getString(5),rs.getString(6),rs.getString(7)
                };
                dtm.addRow(obj);
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    private void firetable(){
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        dtm.fireTableDataChanged();
    }
    

    private void clear(){
        tfTgl.setDate(null);
        tfTgl2.setDate(null);
        tfIdPeg.setText("");
        tfTerima.setText("0");
        tfTotalGaji.setText("0");
        tfTotalPotongan.setText("0");
        
        
    }
    
    private void nolin(){
        gajipokok = 0;
        totalpendidikan = 0;
    sksreg = 0;
    totalsksreg= 0;
    sksindustri = 0;
    totalsksindustri = 0;tunjabatan = 0;
    tunwalas = 0;
    jmlTran = 0;tottransport = 0;
    jmlPiket = 0;totpiket = 0;tunmasakerja = 0;tunbpjskesehatan = 0;totpotongan = 0;
    isgty = false;
    iswalas = false;
    
    }
    
    public void setFromPegawai(String id, boolean isgty, boolean iswalas,int tunjjabatan,int gapok){
        tfIdPeg.setText(id);
        tunjabatan = tunjjabatan;
        this.isgty = isgty;
        this.iswalas = iswalas;
        if(iswalas && isgty) tunwalas = 50000;
        this.tunjabatan = tunjjabatan;
        this.gajipokok = gapok;
        java.util.Date tgl1 = tfTgl.getDate();
        java.util.Date tgl2 = tfTgl2.getDate();
        
        if(tgl1 == null || tgl2 == null){
            Helper.alert("masukkan kedua tanggal tsb dahulu!");
            tfIdPeg.setText("");
            tfIdPeg.requestFocus();
            return;
        }
        
        String stgl1 = new SimpleDateFormat("yyyy-MM-dd").format(tgl1);
        String stgl2 = new SimpleDateFormat("yyyy-MM-dd").format(tgl2);      
        hitunggaji(stgl1,stgl2).execute();
    }
    
    private SwingWorker<Boolean,Void> hitunggaji(String tgl1,String tgl2){
        return new SwingWorker<Boolean,Void>(){
            private Loading loading = new Loading();
            private String idpegawai = tfIdPeg.getText();
            private PreparedStatement ps = null;
            private ResultSet rs = null;
            protected Boolean doInBackground() throws Exception {                
                loading.setVisible(true);
                int totpend = 0;
                try{

                    
                    String query = "SELECT * FROM absensi WHERE idpegawai = ? AND (absensi.tanggal >= '"+tgl1+"' AND absensi.tanggal <= DATE_ADD('"+tgl2+"',INTERVAL 1 DAY )) ";
                    ps = Database.getConn().prepareStatement(query);
                    ps.setInt(1, Integer.parseInt(idpegawai));
                    rs = ps.executeQuery();
                    while(rs.next()){
                        int statusmasuk = rs.getInt(6);
                        java.util.Date tgl =   new java.util.Date(rs.getDate(3).getTime());
                        long jammasuk = rs.getTime(4).getTime();
                        long jampulang = rs.getTime(5).getTime();
                        long perbedaan = (jampulang  - jammasuk) / 1000;
                        int satujam = 3600;
                        long jam = Math.round(perbedaan / satujam);
                        if(statusmasuk == 1 || statusmasuk ==3){                        
                            if(jam > 0 && jam < 5 ){
                                tottransport += 25000;
                                jmlTran += 1;
                            }else if(jam >= 5){
                                tottransport += 50000;
                                jmlTran+=2;
                            }
                        }else{
                            gajipokok -= 250000;
                        }
                    }
                    
                    String query2 = "SELECT pendidikan FROM pegawai WHERE idpegawai = ?";
                        PreparedStatement ps2 = Database.getConn().prepareStatement(query2);
                        ps2.setInt(1, Integer.parseInt(idpegawai));
                        ResultSet rs2 = ps2.executeQuery();
                        while(rs2.next()){
                             if(rs2.getString("pendidikan").equalsIgnoreCase("SMA/K")){
                                 totpend += 100000;
                             }else if(rs2.getString("pendidikan").equalsIgnoreCase("SMA/K")){
                                 totpend += 125000;
                             }
                             totalpendidikan = totpend;
                        }                                    
                         query2 = "SELECT * FROM pegawaijabat WHERE idpegawai = ?";
                         ps2 = Database.getConn().prepareStatement(query2);
                        ps2.setInt(1, Integer.parseInt(idpegawai));
                        rs2 = ps2.executeQuery();
                        while(rs2.next()){
                            int maskerja = rs2.getInt("masajabat");
                            if(maskerja % 5 == 0){
                                tunmasakerja += (maskerja * 50000);
                            }
                        }                    
                    

                        query2 = "SELECT * FROM bpjspegawai WHERE idpegawai = ?";
                        ps2 = Database.getConn().prepareStatement(query2);
                        ps2.setInt(1, Integer.parseInt(idpegawai));
                        rs2 = ps2.executeQuery();
                        while(rs2.next()){
                            int jumlah = rs2.getInt("jumlah");
                            tunbpjskesehatan += jumlah;
                        }
                                  
                        
                    
                    totgaji += gajipokok+tunjabatan+tottransport+tunmasakerja+tunbpjskesehatan+totpend;
                        query2 = "SELECT pegawai.namapegawai ,potongan.nominal FROM potongan,potonganpegawai,pegawai "
                                + "WHERE potongan.id = potonganpegawai.idpotongan "
                                + "AND potonganpegawai.idpegawai = pegawai.idpegawai AND pegawai.idpegawai = ? "
                                + "AND (potonganpegawai.tanggal >= '"+tgl1+"' AND potonganpegawai.tanggal <= DATE_ADD('"+tgl2+"',INTERVAL 1 DAY )) ";
                         ps2 = Database.getConn().prepareStatement(query2);
                        ps2.setInt(1, Integer.parseInt(idpegawai));
                        rs2 = ps2.executeQuery();
                        while(rs2.next()){
                            int jumlah = rs2.getInt("nominal");
                            totpotongan += jumlah;
                        }                                  
                    
                }catch(SQLException ex){
                    ex.printStackTrace();
                    return false;
                }
                return true;
            }
            

            @Override
            protected void done() {
                    loading.setVisible(false);
                    tfTotalPotongan.setText(totpotongan+"");
                    tfTotalGaji.setText(totgaji+"");
                    long dibayar = totgaji - totpotongan;
                    tfTerima.setText(dibayar+"");
            }
            
        };

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
        jLabel3 = new javax.swing.JLabel();
        tfIdPeg = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        tfTotalGaji = new javax.swing.JTextField();
        tfTotalPotongan = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        tfTerima = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        tfTgl = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        tfTgl2 = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        tfGaji = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnSave1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Penggajian Non Tenaga Pendidikan");

        jPanel1.setBackground(new java.awt.Color(25, 59, 104));

        jPanel2.setBackground(new java.awt.Color(20, 165, 255));

        jLabel1.setFont(new java.awt.Font("Agency FB", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(25, 59, 104));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Penggajian Non Tenaga Pendidikan");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 971, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 76, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel3.setBackground(new java.awt.Color(20, 165, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Tanggal Awal Periode");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel3.setText("ID Penggajian");

        tfIdPeg.setEnabled(false);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel12.setText("Total Gaji");

        tfTotalGaji.setText("0");
        tfTotalGaji.setEnabled(false);

        tfTotalPotongan.setText("0");
        tfTotalPotongan.setEnabled(false);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel13.setText("Total Potongan");

        tfTerima.setText("0");
        tfTerima.setEnabled(false);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel14.setText("Dibayarkan");

        btnSave.setBackground(new java.awt.Color(255, 255, 255));
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/delete.png"))); // NOI18N
        btnSave.setText("DELETE");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(255, 255, 255));
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancel.png"))); // NOI18N
        btnCancel.setText("CANCEL");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnBack.setBackground(new java.awt.Color(255, 255, 255));
        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/kembali.png"))); // NOI18N
        btnBack.setText("BACK");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        jLabel15.setBackground(new java.awt.Color(20, 165, 255));
        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(25, 59, 104));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/caricari.png"))); // NOI18N
        jLabel15.setOpaque(true);
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel4.setText("ID Pegawai ");

        tfGaji.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("-");

        btnSave1.setBackground(new java.awt.Color(255, 255, 255));
        btnSave1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save.png"))); // NOI18N
        btnSave1.setText("SAVE");
        btnSave1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSave1ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Tanggal Akhir Periode");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfTotalGaji, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnSave1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(225, 225, 225)
                                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(121, 121, 121)
                                        .addComponent(btnCancel))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tfTotalPotongan, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(tfTgl2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(73, 73, 73)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tfTerima, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel14)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addComponent(tfIdPeg, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(30, 30, 30)
                                                .addComponent(jLabel15))
                                            .addComponent(jLabel4))))))
                        .addGap(103, 103, 103))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfGaji, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(261, 261, 261)
                                .addComponent(jLabel5))
                            .addComponent(tfTgl, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(tfGaji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfTgl2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfTgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfIdPeg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(tfTotalGaji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(tfTotalPotongan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfTerima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSave1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBack, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        int row = table.getSelectedRow();
        if(row < 0){
            Helper.alert("PIlih data dahulu di tabel!");
            return;
        }
        String id = tfGaji.getText();
        
        int tanya = Helper.tanya("Apakah anda ingin menghapus data ini ?");
        if(tanya == JOptionPane.YES_OPTION && !id.isEmpty() ){
            try{
                PreparedStatement ps = Database.getConn().prepareStatement("DELETE fROM gajiannon WHERE idgajian = ?");
                ps.setString(1, id);
                int hasil = ps.executeUpdate();
                if(hasil > 0 ){
                    PreparedStatement ps2 = Database.getConn().prepareStatement("DELETE FROM detailnon WHERE idgajian = ?");
                    ps2.setString(1, id);
                    int rs = ps2.executeUpdate();
                    if(rs > 0){
                        Helper.alert("Data berhasil dihapus");
                    }
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        }
                nolin();
        loadData("");
        firetable();
        clear();
        generateId();
                jLabel15.setVisible(true);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
        nolin();
        loadData("");
        firetable();
        clear();
        generateId();
                jLabel15.setVisible(true);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
            if(HelperTable.isFromGajiTen){
                HelperTable.isFromGajiTen = false;
                HelperTable.gajiT = null;
                dispose();
            }
        dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        // TODO add your handling code here:
        HelperTable.isFromGajiNon = true;
        HelperTable.gajinon = this;
        HelperTable form = new HelperTable();
        form.setVisible(true);
        jLabel15.setVisible(false);
    }//GEN-LAST:event_jLabel15MouseClicked

    private void btnSave1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave1ActionPerformed
        // TODO add your handling code here:
        
        String id = tfGaji.getText();
        java.util.Date tgl1 = tfTgl.getDate();
        java.util.Date tgl2 = tfTgl2.getDate();
        String idpegawai = tfIdPeg.getText();
        String countgaji = tfTotalGaji.getText();
        String countpotongan = tfTotalPotongan.getText();
        String dibayar = tfTerima.getText();

        if(tgl1 == null || tgl2 == null || idpegawai.isEmpty() || countgaji.isEmpty() || countpotongan.isEmpty() || dibayar.isEmpty()){
            Helper.alert("Isi form dahulu!");
            return;
        }
        
        String query = "INSERT INTO gajiannon VALUES(?,?,?,?,?,?,?)";
        try{
            PreparedStatement ps = Database.getConn().prepareStatement(query);
            ps.setString(1, id);
            ps.setDate(2, new java.sql.Date(tgl1.getTime()));
            ps.setDate(3, new java.sql.Date(tgl2.getTime()));
            ps.setInt(4,Integer.parseInt(idpegawai));
            ps.setInt(5,Integer.parseInt(countgaji));
            ps.setInt(6,Integer.parseInt(countpotongan));
            ps.setInt(7,Integer.parseInt(dibayar));
            int rs = ps.executeUpdate();
            if(rs > 0){
                String query2 = "INSERT INTO detailnon VALUES(?,?,?,?,?,?,?,?)";
                PreparedStatement ps2 = Database.getConn().prepareStatement(query2);
                ps2.setString(1, id);
                ps2.setInt(2,gajipokok);
                ps2.setInt(3,tunjabatan);
                ps2.setInt(4,jmlTran);
                ps2.setInt(5,tottransport);
                ps2.setInt(6,totalpendidikan);
                ps2.setInt(7,tunbpjskesehatan);
                ps2.setTimestamp(8, new java.sql.Timestamp(new java.util.Date().getTime()));
                int rs2 = ps2.executeUpdate();
                if(rs2 > 0){
                    Helper.alert("data Berhasil ditambahkan!");
                    
                }
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        nolin();
        loadData("");
        firetable();
        clear();
        generateId();
        jLabel15.setVisible(true);        
    }//GEN-LAST:event_btnSave1ActionPerformed

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        // TODO add your handling code here:
 // {"ID Penggajian","ID Pegawai","Nama Pegawai","Tanggal Periode","Total Gaji","Total Potongan","Dibayarkan"};
        int row = table.getSelectedRow();
        if(row < 0) return;
        String idg = table.getValueAt(row,0).toString();
        String idp = table.getValueAt(row,1).toString();
        String tgl = table.getValueAt(row,3).toString();
        String total = table.getValueAt(row,4).toString();
        String tp = table.getValueAt(row,5).toString();
        String db = table.getValueAt(row,6).toString(); 
        
        try{
            String tgls = tgl.split("-")[0].trim();
            String tgls2 = tgl.split("-")[1].trim();            
            java.util.Date tgld = new SimpleDateFormat("dd MMMMM yyyy",new java.util.Locale("id","ID")).parse(tgls);
            java.util.Date tgld2 = new SimpleDateFormat("dd MMMMM yyyy",new java.util.Locale("id","ID")).parse(tgls2);
            tfGaji.setText(idg);
            tfIdPeg.setText(idp);
            tfTotalPotongan.setText(tp);
            tfTerima.setText(db);
            tfTotalGaji.setText(total);
            tfTgl.setDate(tgld);tfTgl2.setDate(tgld2);
        }catch(ParseException ex){
            ex.printStackTrace();
        }
        
    }//GEN-LAST:event_tableMouseClicked

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
            java.util.logging.Logger.getLogger(PenggajianNon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PenggajianNon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PenggajianNon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PenggajianNon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PenggajianNon().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSave1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    private javax.swing.JTextField tfGaji;
    private javax.swing.JTextField tfIdPeg;
    private javax.swing.JTextField tfTerima;
    private com.toedter.calendar.JDateChooser tfTgl;
    private com.toedter.calendar.JDateChooser tfTgl2;
    private javax.swing.JTextField tfTotalGaji;
    private javax.swing.JTextField tfTotalPotongan;
    // End of variables declaration//GEN-END:variables
}
