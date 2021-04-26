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
import kkp.session;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Lazuardi Fadhilah
 */
public class reimbursement extends javax.swing.JFrame {
    String id_user = session.get_id_user();
    String nama_user = session.get_nama_user();
    String idrmb;
    /**
     * Creates new form reimbursement
     */
    public reimbursement() {
        initComponents();
        autoNumber();
        id_rmb();
        kosong();
        combo_id_invoice();
        load_table_reimburse();
        id_rmb();
    }
    
    private void hapus(){
        id_rmb();
        String id = txt_id.getText();
        try{
            String sql = "DELETE FROM reimburse WHERE id_reimburse='"+id+"'";
            java.sql.Connection con = (Connection)koneksi.koneksidb();
            java.sql.PreparedStatement pst = con.prepareStatement(sql);

            if(id.equals(idrmb)){
             
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
    
    private void id_rmb(){
        try{
        String sql = "SELECT * FROM reimburse WHERE id_reimburse = '"+txt_id.getText()+"'";
        java.sql.Connection con = (Connection)koneksi.koneksidb();
        java.sql.Statement stm = con.createStatement();
        java.sql.ResultSet rs = stm.executeQuery(sql);
        while(rs.next()){
        idrmb = rs.getString(1);
        }
    } catch(Exception e){
        JOptionPane.showMessageDialog(this, "Error " +e);
    }
    }
    
    private void ubah(){
        String id = txt_id.getText();
        Object inv = combo_id_inv.getSelectedItem();
        String kegiatan = txt_kegiatan.getText();
        String biaya = txt_biaya.getText();
        
        try{
            String sql = "UPDATE reimburse SET id_inv ='"+inv+"', kegiatan ='"+kegiatan+"', biaya ='"+biaya+"', id_user='"+id_user+"' WHERE id_reimburse='"+id+"' ";
            java.sql.Connection con = (Connection)koneksi.koneksidb();
            java.sql.PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Ubah");
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error : "+e);
        }
    }
    
    private void simpan(){
        String id = txt_id.getText();
        Object inv = combo_id_inv.getSelectedItem();
        String kegiatan = txt_kegiatan.getText();
        String biaya = txt_biaya.getText();
        
        try{
            String sql = "INSERT INTO reimburse VALUES ('"+id+"','"+inv+"','"+kegiatan+"','"+biaya+"','"+id_user+"')";
            java.sql.Connection con = (Connection)koneksi.koneksidb();
            java.sql.PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Tambah");
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error : "+e);
        }
    }
    
    private void load_table_reimburse(){
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ID Reimburse");
    model.addColumn("ID Invoice");
    model.addColumn("Nama Karyawan");
    model.addColumn("Kegiatan");
    model.addColumn("Biaya");
    try{
        String sql = "SELECT reimburse.id_reimburse, invoice.id_inv, user.nama_user, reimburse.kegiatan, reimburse.biaya FROM reimburse INNER JOIN invoice ON reimburse.id_inv = invoice.id_inv INNER JOIN user ON reimburse.id_user = user.id_user WHERE user.nama_user = '"+nama_user+"'"
                + "AND reimburse.id_reimburse like '%"+txt_cari.getText()+"%' or invoice.id_inv like '%"+txt_cari.getText()+"%' or kegiatan like '%"+txt_cari.getText()+"%' ";
        java.sql.Connection con = (Connection)koneksi.koneksidb();
        java.sql.Statement stm = con.createStatement();
        java.sql.ResultSet rs = stm.executeQuery(sql);
        while(rs.next()){
            model.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)});
        }
        table_reimburse.setModel(model);
    } catch(Exception e){
        JOptionPane.showMessageDialog(this, "Error " +e);
    }
    }
    
    public void combo_id_invoice(){
        try{
            String sql ="select * from invoice";
            java.sql.Connection con = (Connection)koneksi.koneksidb();
            java.sql.Statement stm = con.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            combo_id_inv.removeAllItems();
            while(res.next()){
            

            
            combo_id_inv.addItem(res.getString("id_inv"));
            }
        } catch (SQLException e){
            System.out.println("Error :" +e);
        }
    }
    
    public void kosong(){
        txt_id.setEnabled(false);
        combo_id_inv.setSelectedItem(null);
        txt_nama_user.setEnabled(false);
        txt_nama_user.setText(nama_user);
        txt_kegiatan.setText("");
        txt_biaya.setText("");
        txt_cari.setText("");
    }  
    
        public void autoNumber(){
        try{
            java.sql.Connection Con = (Connection)koneksi.koneksidb();
            java.sql.Statement stm = Con.createStatement();
            String sql = "SELECT MAX(RIGHT(id_reimburse,4)) AS nomor FROM reimburse";
            java.sql.ResultSet res = stm.executeQuery(sql);
            
            if(res.first() ==  false){
                txt_id.setText("RMB0001");
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
                txt_id.setText("RMB" +nomor);
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
        combo_id_inv = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txt_nama_user = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_kegiatan = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_biaya = new javax.swing.JTextField();
        btn_tambah = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_kosong = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txt_cari = new javax.swing.JTextField();
        btn_cari = new javax.swing.JButton();
        btn_cetak = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_reimburse = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Reimbursement", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Montserrat SemiBold", 0, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel1.setText("ID Reimburse :");

        jLabel2.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel2.setText("ID Invoice :");

        combo_id_inv.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        combo_id_inv.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel3.setText("Nama Karyawan :");

        jLabel4.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel4.setText("Kegiatan :");

        jLabel5.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel5.setText("Biaya :");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_id)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(combo_id_inv, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(txt_nama_user)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(txt_kegiatan)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(txt_biaya)
                    .addComponent(btn_tambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_ubah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_hapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_kosong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(combo_id_inv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nama_user, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_kegiatan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_biaya, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_ubah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_kosong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Reimbursement", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Montserrat SemiBold", 0, 14))); // NOI18N

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

        table_reimburse.setModel(new javax.swing.table.DefaultTableModel(
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
        table_reimburse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_reimburseMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_reimburse);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cariActionPerformed
        // TODO add your handling code here:
        load_table_reimburse();
        autoNumber();
    }//GEN-LAST:event_btn_cariActionPerformed

    private void btn_cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cetakActionPerformed
        // TODO add your handling code here:
        try {

            String path="src/kkp.report/reimbursement.jasper";      // letak penyimpanan report

            HashMap parameter = new HashMap();

            parameter.put("RMB01",combo_id_inv.getSelectedItem());
            parameter.put("RMB02", id_user);
            // "no_faktur" => nama parameter yang dibuat
            //jTextFieldnofaktur.getText() ==> disesuaikan dengan jTextField yang digunakan

            JasperPrint print = JasperFillManager.fillReport(path,parameter,koneksi.koneksidb());

            JasperViewer.viewReport(print, false);

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(rootPane,"Error: "+ex);

        }
        
    }//GEN-LAST:event_btn_cetakActionPerformed

    private void btn_kosongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kosongActionPerformed
        // TODO add your handling code here:
        kosong();
        load_table_reimburse();
        autoNumber();
    }//GEN-LAST:event_btn_kosongActionPerformed

    private void table_reimburseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_reimburseMouseClicked
        // TODO add your handling code here:
        int baris = table_reimburse.rowAtPoint(evt.getPoint());
        String id = table_reimburse.getValueAt(baris, 0).toString();
        txt_id.setText(id);
        String idinv = table_reimburse.getValueAt(baris, 1).toString();
        combo_id_inv.setSelectedItem(idinv);
        String nama = table_reimburse.getValueAt(baris, 2).toString();
        txt_nama_user.setText(nama);
        String kegiatan = table_reimburse.getValueAt(baris, 3).toString();
        txt_kegiatan.setText(kegiatan);
        String biaya = table_reimburse.getValueAt(baris, 4).toString();
        txt_biaya.setText(biaya);
    }//GEN-LAST:event_table_reimburseMouseClicked

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        if(txt_id.getText().equals("") || txt_biaya.getText().equals("") || txt_kegiatan.getText().equals("")){
            JOptionPane.showMessageDialog(rootPane, "Data tidak boleh ada yang kosong","Pemberitahuan",JOptionPane.WARNING_MESSAGE);
        }else{
            simpan();
            load_table_reimburse();
            kosong();
            autoNumber();
        }
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahActionPerformed
        // TODO add your handling code here:
        if(txt_id.getText().equals("") || txt_biaya.getText().equals("") || txt_kegiatan.getText().equals("")){
            JOptionPane.showMessageDialog(rootPane, "Data tidak boleh ada yang kosong","Pemberitahuan",JOptionPane.WARNING_MESSAGE);
        }else{
            ubah();
            load_table_reimburse();
            kosong();
            autoNumber();
        }
    }//GEN-LAST:event_btn_ubahActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
            hapus();
            load_table_reimburse();
            kosong();
            autoNumber();
    }//GEN-LAST:event_btn_hapusActionPerformed

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
            java.util.logging.Logger.getLogger(reimbursement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(reimbursement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(reimbursement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(reimbursement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new reimbursement().setVisible(true);
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
    private javax.swing.JComboBox<String> combo_id_inv;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table_reimburse;
    private javax.swing.JTextField txt_biaya;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_kegiatan;
    private javax.swing.JTextField txt_nama_user;
    // End of variables declaration//GEN-END:variables
}
