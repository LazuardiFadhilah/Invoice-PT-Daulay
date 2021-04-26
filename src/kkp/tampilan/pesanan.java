/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkp.tampilan;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import kkp.koneksi;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Lazuardi Fadhilah
 */
public class pesanan extends javax.swing.JFrame {
    String ID;
    String idinv;
    /**
     * Creates new form pesanan
     */
    public pesanan() {
        initComponents();
        autoNumber();
        combo_nama_customer();
        kosong();
        load_table_pesanan();
    }
    
    private void id_inv(){
        try{
        String sql = "SELECT * FROM invoice WHERE id_inv = '"+txt_id.getText()+"'";
        java.sql.Connection con = (Connection)koneksi.koneksidb();
        java.sql.Statement stm = con.createStatement();
        java.sql.ResultSet rs = stm.executeQuery(sql);
        while(rs.next()){
        idinv = rs.getString(1);
        }
    } catch(Exception e){
        JOptionPane.showMessageDialog(this, "Error " +e);
    }
    }
    
    private void hapus(){
        id_inv();
        String id = txt_id.getText();
        try{
            String sql = "DELETE FROM invoice WHERE id_inv='"+id+"'";
            java.sql.Connection con = (Connection)koneksi.koneksidb();
            java.sql.PreparedStatement pst = con.prepareStatement(sql);

            if(id.equals(idinv)){
             
            int hapus = JOptionPane.showOptionDialog(this, "Apakah anda yakin ingin menghapus data ini ?","Hapus",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
            null, null);
            if (hapus == JOptionPane.YES_OPTION) {
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Hapus");
         }    
         } else{
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error : "+e);
        }
    }
    
    private void ubah(){
        getID();
        hitung();
        String id = txt_id.getText();
        Object jenis = combo_limbah.getSelectedItem();
        String berat = txt_berat.getText();
        String harga = txt_harga.getText();            
        try{
            String sql = "UPDATE invoice SET jenis_limbah ='"+jenis+"', berat_total ='"+berat+"', id_customer='"+ID+"', total_harga='"+harga+"' WHERE id_inv='"+id+"'";
            java.sql.Connection con = (Connection)koneksi.koneksidb();
            java.sql.PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Ubah");
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error : "+e);
        }
    }
    
    private void tambah(){
        getID();
        hitung();
        String id = txt_id.getText();
        Object jenis = combo_limbah.getSelectedItem();
        String berat = txt_berat.getText();
        String harga = txt_harga.getText();
        
        try{
            String sql = "INSERT INTO invoice VALUES ('"+id+"','"+jenis+"','"+berat+"','"+ID+"','"+harga+"')";
            java.sql.Connection con = (Connection)koneksi.koneksidb();
            java.sql.PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Tambah");
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error : "+e);
        }
        
        
    }
    
    private void hitung(){
    int berat = Integer.parseInt(txt_berat.getText());
    Object jenis = combo_limbah.getSelectedItem();
    int hitung;
    
    if(jenis.equals("Limbah Padat")){
        hitung = 10000 * berat ;
        String htg = Integer.toString(hitung);
        txt_harga.setText(htg);
    } else if (jenis.equals("Limbah Cair")){
        hitung = 15000 * berat ;
        String htg = Integer.toString(hitung);
        txt_harga.setText(htg);
    }
    }
    
    private void getID(){
    try{
        String sql = "SELECT * FROM customer WHERE nama_customer like '%"+combo_nama.getSelectedItem()+"%'";
        java.sql.Connection con = (Connection)koneksi.koneksidb();
        java.sql.Statement stm = con.createStatement();
        java.sql.ResultSet rs = stm.executeQuery(sql);
        while(rs.next()){
        ID = rs.getString(1);
        }
    } catch(Exception e){
        JOptionPane.showMessageDialog(this, "Error " +e);
    }
    }
    
    private void load_table_pesanan(){
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID");
    model.addColumn("Nama Customer");
    model.addColumn("Jenis Limbah");
    model.addColumn("Berat Total");
    model.addColumn("Total Harga");
    try{
        String sql = "SELECT invoice.id_inv, customer.nama_customer, invoice.jenis_limbah, invoice.berat_total, invoice.total_harga FROM invoice INNER JOIN customer ON invoice.id_customer = customer.id_customer WHERE invoice.id_inv like '%"+txt_cari.getText()+"%' or customer.nama_customer like '%"+txt_cari.getText()+"%' ORDER by invoice.id_inv ASC";
        java.sql.Connection con = (Connection)koneksi.koneksidb();
        java.sql.Statement stm = con.createStatement();
        java.sql.ResultSet rs = stm.executeQuery(sql);
        while(rs.next()){
            model.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)});
        }
        table_pesanan.setModel(model);
    } catch(Exception e){
        JOptionPane.showMessageDialog(this, "Error " +e);
    }
    }
    
    public void combo_nama_customer(){
        try{
            String sql ="select * from customer";
            java.sql.Connection con = (Connection)koneksi.koneksidb();
            java.sql.Statement stm = con.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            combo_nama.removeAllItems();
            while(res.next()){
            

            
            combo_nama.addItem(res.getString("nama_customer"));
            }
        } catch (SQLException e){
            System.out.println("Error :" +e);
        }
    }
    
    public void kosong(){
        txt_id.setEnabled(false);
        combo_nama.setSelectedItem(null);
        combo_limbah.setSelectedItem(null);
        txt_berat.setText("");
        txt_harga.setText("");
        txt_cari.setText("");
        
    }
    
    public void autoNumber(){
        try{
            java.sql.Connection Con = (Connection)koneksi.koneksidb();
            java.sql.Statement stm = Con.createStatement();
            String sql = "SELECT MAX(RIGHT(id_inv,4)) AS nomor FROM invoice";
            java.sql.ResultSet res = stm.executeQuery(sql);
            
            if(res.first() ==  false){
                txt_id.setText("INV0001");
            } else {
                res.last();
                int no = res.getInt(1) + 1;
                String nomor = String.valueOf(no);
                int oto = nomor.length();
                switch(oto){
                    case 1: nomor = "000" +nomor; break;
                    case 2: nomor = "00" +nomor; break;
                    case 3: nomor = "0" +nomor; break;
                    case 4: nomor = "" +nomor; break;
                }
                txt_id.setText("INV" +nomor);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
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
        jLabel1 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        combo_nama = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        combo_limbah = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txt_berat = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btn_tambah = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_kosong = new javax.swing.JButton();
        txt_harga = new javax.swing.JLabel();
        txt_harga1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txt_cari = new javax.swing.JTextField();
        btn_cari = new javax.swing.JButton();
        btn_cetak = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_pesanan = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new java.awt.Dimension(0, 0));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pesanan", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Montserrat SemiBold", 0, 14))); // NOI18N
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel1.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel1.setText("ID Pesanan :");

        jLabel2.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel2.setText("Nama Customer :");

        combo_nama.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        combo_nama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel3.setText("Jenis Limbah :");

        combo_limbah.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        combo_limbah.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Limbah Padat", "Limbah Cair" }));

        jLabel4.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel4.setText("Berat Total :");

        txt_berat.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        txt_berat.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                txt_beratInputMethodTextChanged(evt);
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        txt_berat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_beratActionPerformed(evt);
            }
        });
        txt_berat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_beratKeyPressed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel5.setText("Kg");

        jLabel6.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel6.setText("Total Harga :");

        btn_tambah.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        btn_tambah.setText("Tambah");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });

        btn_ubah.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        btn_ubah.setText("Ubah");
        btn_ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubahActionPerformed(evt);
            }
        });

        btn_hapus.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        btn_hapus.setText("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        btn_kosong.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        btn_kosong.setText("Kosong");
        btn_kosong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kosongActionPerformed(evt);
            }
        });

        txt_harga.setFont(new java.awt.Font("Montserrat SemiBold", 2, 14)); // NOI18N
        txt_harga.setText("20000");

        txt_harga1.setFont(new java.awt.Font("Montserrat SemiBold", 2, 14)); // NOI18N
        txt_harga1.setText("Rp.  ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_id)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(combo_nama, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(combo_limbah, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txt_berat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5))
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_tambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_ubah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_hapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_kosong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txt_harga1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(combo_nama, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(combo_limbah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_berat, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_ubah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_kosong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_harga1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_harga, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Pesanan", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Montserrat SemiBold", 0, 14))); // NOI18N

        txt_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cariActionPerformed(evt);
            }
        });

        btn_cari.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        btn_cari.setText("Cari");
        btn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cariActionPerformed(evt);
            }
        });

        btn_cetak.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        btn_cetak.setText("Cetak");
        btn_cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cetakActionPerformed(evt);
            }
        });

        table_pesanan.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        table_pesanan.setModel(new javax.swing.table.DefaultTableModel(
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
        table_pesanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_pesananMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_pesanan);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_kosongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kosongActionPerformed
        load_table_pesanan();
        autoNumber();
        kosong();
    }//GEN-LAST:event_btn_kosongActionPerformed

    private void btn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cariActionPerformed
        // TODO add your handling code here:
        load_table_pesanan();
        kosong();
    }//GEN-LAST:event_btn_cariActionPerformed

    private void table_pesananMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_pesananMouseClicked
        // TODO add your handling code here:
        int baris = table_pesanan.rowAtPoint(evt.getPoint());
        String id = table_pesanan.getValueAt(baris, 0).toString();
        txt_id.setText(id);
        String nama = table_pesanan.getValueAt(baris, 1).toString();
        combo_nama.setSelectedItem(nama);
        String limbah = table_pesanan.getValueAt(baris, 2).toString();
        combo_limbah.setSelectedItem(limbah);
        String berat = table_pesanan.getValueAt(baris, 3).toString();
        txt_berat.setText(berat);
        String harga = table_pesanan.getValueAt(baris, 4).toString();
        txt_harga.setText(harga);
    }//GEN-LAST:event_table_pesananMouseClicked

    private void txt_beratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_beratKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_beratKeyPressed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        if(txt_id.getText().equals("") || txt_berat.getText().equals("") || txt_harga.getText().equals("")){
                JOptionPane.showMessageDialog(rootPane, "Data tidak boleh ada yang kosong","Pemberitahuan",JOptionPane.WARNING_MESSAGE);
        } else{
        hitung();
        tambah();
        load_table_pesanan();
        autoNumber();
        kosong();
        }
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void txt_beratInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_txt_beratInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_beratInputMethodTextChanged

    private void txt_beratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_beratActionPerformed
        // TODO add your handling code here:
        hitung();
    }//GEN-LAST:event_txt_beratActionPerformed

    private void txt_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cariActionPerformed
        // TODO add your handling code here:
        load_table_pesanan();
    }//GEN-LAST:event_txt_cariActionPerformed

    private void btn_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahActionPerformed
        // TODO add your handling code here:
        if(txt_id.getText().equals("") || txt_berat.getText().equals("") || txt_harga.getText().equals("")){
        JOptionPane.showMessageDialog(rootPane, "Data tidak boleh ada yang kosong","Pemberitahuan",JOptionPane.WARNING_MESSAGE);
        } else{
            hitung();
            ubah();
            load_table_pesanan();
            autoNumber();
            kosong();
        }
    }//GEN-LAST:event_btn_ubahActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        hapus();
        load_table_pesanan();
        autoNumber();
        kosong();     
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cetakActionPerformed
        // TODO add your handling code here:
        try {

            String path="src/kkp.report/pesanan.jasper";      // letak penyimpanan report

            HashMap parameter = new HashMap();

            parameter.put("ID_INV",txt_id.getText());
            // "no_faktur" => nama parameter yang dibuat
            //jTextFieldnofaktur.getText() ==> disesuaikan dengan jTextField yang digunakan

            JasperPrint print = JasperFillManager.fillReport(path,parameter,koneksi.koneksidb());

            JasperViewer.viewReport(print, false);

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(rootPane,"Error: "+ex);

        }
    }//GEN-LAST:event_btn_cetakActionPerformed

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
            java.util.logging.Logger.getLogger(pesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pesanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pesanan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_cetak;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_kosong;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_ubah;
    private javax.swing.JComboBox<String> combo_limbah;
    private javax.swing.JComboBox<String> combo_nama;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_pesanan;
    private javax.swing.JTextField txt_berat;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JLabel txt_harga;
    private javax.swing.JLabel txt_harga1;
    private javax.swing.JTextField txt_id;
    // End of variables declaration//GEN-END:variables
}
