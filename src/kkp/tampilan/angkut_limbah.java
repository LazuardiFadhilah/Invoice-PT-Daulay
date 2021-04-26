/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkp.tampilan;

import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import kkp.koneksi;
import kkp.session;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Lazuardi Fadhilah
 */
public class angkut_limbah extends javax.swing.JFrame {
    String nama_user = session.get_nama_user();
    String id_user = session.get_id_user();
    String id_mbl,id_customer;
    String tgl;
    /**
     * Creates new form angkut_limbah
     */
    public angkut_limbah() {
        initComponents();
        autoNumber();
        load_data_angkut();
        combo_no_polis();
        combo_id_inv();
        nama_customer();
        tanggal();
        kosong();
    }
    
    private void hapus(){
        try{
            String sql = "DELETE FROM angkut_limbah WHERE id_angkut='"+txt_id.getText()+"'";
            java.sql.Connection con = (Connection)koneksi.koneksidb();
            java.sql.PreparedStatement pst = con.prepareStatement(sql);
            int hapus = JOptionPane.showOptionDialog(this, "Apakah anda yakin ingin menghapus data ini ?","Hapus",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
            null, null);
            if(hapus == JOptionPane.YES_OPTION){
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Hapus");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error : "+e);
        }
    }
    
    private void ubah(){
        id_mbl();
        id_customer();
        String id = txt_id.getText();
        Object id_inv = combo_id_inv.getSelectedItem();
        try{
            String sql = "UPDATE angkut_limbah SET id_mobil = '"+id_mbl+"', id_user ='"+id_user+"', id_inv='"+id_inv+"', id_customer='"+id_customer+"', tanggal_angkut='"+tgl+"' WHERE id_angkut='"+id+"'";
            java.sql.Connection con = (Connection)koneksi.koneksidb();
            java.sql.PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Ubah");
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error : "+e);
        }
    
    }
    
    private void tanggal(){
        if(tgl_angkut.getDate() != null){
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            tgl = String.valueOf(format.format(tgl_angkut.getDate()));
        }
    }
    
    private void id_customer(){
        try{
        String sql = "SELECT * FROM customer WHERE nama_customer like '%"+txt_nama_customer.getText()+"%'";
        java.sql.Connection con = (Connection)koneksi.koneksidb();
        java.sql.Statement stm = con.createStatement();
        java.sql.ResultSet rs = stm.executeQuery(sql);
        while(rs.next()){
        id_customer = rs.getString("id_customer");}
    } catch(Exception e){
        JOptionPane.showMessageDialog(this, "Error " +e);
    }
    }
    
    private void id_mbl(){
        try{
        String sql = "SELECT * FROM mobil WHERE no_polis like '%"+combo_polis.getSelectedItem()+"%'";
        java.sql.Connection con = (Connection)koneksi.koneksidb();
        java.sql.Statement stm = con.createStatement();
        java.sql.ResultSet rs = stm.executeQuery(sql);
        while(rs.next()){
        id_mbl = rs.getString("id_mobil");}
    } catch(Exception e){
        JOptionPane.showMessageDialog(this, "Error " +e);
    }
    }
    
    private void tambah(){
        id_mbl();
        id_customer();
        String id = txt_id.getText();
        Object id_inv = combo_id_inv.getSelectedItem();
        try{
            String sql = "INSERT INTO angkut_limbah VALUES ('"+id+"','"+id_mbl+"','"+id_user+"','"+id_inv+"','"+id_customer+"','"+tgl+"')";
            java.sql.Connection con = (Connection)koneksi.koneksidb();
            java.sql.PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Berhasil di Tambah");
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error : "+e);
        }
    
    }
    
    public void nama_customer(){
        try{
            String sql ="select customer.nama_customer from invoice INNER JOIN customer ON invoice.id_customer=customer.id_customer WHERE invoice.id_inv = '"+combo_id_inv.getSelectedItem()+"'";
            java.sql.Connection con = (Connection)koneksi.koneksidb();
            java.sql.Statement stm = con.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            txt_nama_customer.setText("");
            while(res.next()){
            

            
            txt_nama_customer.setText(res.getString("nama_customer"));
            }
        } catch (SQLException e){
            System.out.println("Error :" +e);
        }
    }
    
    public void combo_id_inv(){
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
    
    public void combo_no_polis(){
        try{
            String sql ="select * from mobil WHERE jenis_mobil = '"+combo_jenis.getSelectedItem()+"'";
            java.sql.Connection con = (Connection)koneksi.koneksidb();
            java.sql.Statement stm = con.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            combo_polis.removeAllItems();
            while(res.next()){
            

            
            combo_polis.addItem(res.getString("no_polis"));
            }
        } catch (SQLException e){
            System.out.println("Error :" +e);
        }
    }
    
    
    private void load_data_angkut(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Angkut");
        model.addColumn("Jenis Mobil");
        model.addColumn("No Polis");
        model.addColumn("Nama Petugas");
        model.addColumn("ID Invoice");
        model.addColumn("Nama Customer");
        model.addColumn("Tanggal Angkut");
        try{
            String sql = "SELECT angkut_limbah.id_angkut, mobil.jenis_mobil, mobil.no_polis, user.nama_user, invoice.id_inv, customer.nama_customer, angkut_limbah.tanggal_angkut "
                    + "FROM angkut_limbah JOIN mobil ON angkut_limbah.id_mobil = mobil.id_mobil "
                    + "JOIN user ON angkut_limbah.id_user = user.id_user "
                    + "JOIN invoice ON angkut_limbah.id_inv = invoice.id_inv "
                    + "JOIN customer ON angkut_limbah.id_customer = customer.id_customer WHERE angkut_limbah.id_angkut like '%"+txt_cari.getText()+"%' OR mobil.no_polis like '%"+txt_cari.getText()+"%' OR invoice.id_inv like '%"+txt_cari.getText()+"%' OR customer.nama_customer like '%"+txt_cari.getText()+"%' ORDER BY angkut_limbah.id_angkut ASC";
            java.sql.Connection con = (Connection)koneksi.koneksidb();
            java.sql.Statement stm = con.createStatement();
            java.sql.ResultSet rs = stm.executeQuery(sql);
            while(rs.next()){
                model.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getDate(7)});
            }
            tabel_angkut.setModel(model);
        } catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error " +e);
        }
    }
    
    public void autoNumber(){
        try{
            java.sql.Connection Con = (Connection)koneksi.koneksidb();
            java.sql.Statement stm = Con.createStatement();
            String sql = "SELECT MAX(RIGHT(id_angkut,4)) AS nomor FROM angkut_limbah";
            java.sql.ResultSet res = stm.executeQuery(sql);
            
            if(res.first() ==  false){
                txt_id.setText("AGT0001");
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
                txt_id.setText("AGT" +nomor);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void kosong(){
        txt_id.setEnabled(false);
        combo_jenis.setSelectedItem(null);
        combo_polis.setSelectedItem(null);
        txt_nama_user.setText(nama_user);
        txt_nama_user.setEnabled(false);
        combo_id_inv.setSelectedItem(null);
        txt_nama_customer.setEnabled(false);
        txt_nama_customer.setText("");
        tgl_angkut.setDate(null);
        txt_cari.setText("");
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
        combo_jenis = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txt_nama_user = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        combo_id_inv = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txt_nama_customer = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tgl_angkut = new com.toedter.calendar.JDateChooser();
        btn_tambah = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_kosong = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        combo_polis = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        txt_cari = new javax.swing.JTextField();
        btn_cari = new javax.swing.JButton();
        btn_cetak = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_angkut = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Angkut Limbah", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Montserrat SemiBold", 0, 14))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel1.setText("ID Angkut Limbah :");

        txt_id.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel2.setText("Jenis Mobil :");

        combo_jenis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dump Truck", "Armroll Truck", "Compactor Truck", "Trailler Truck" }));
        combo_jenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_jenisActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel3.setText("Nama Petugas  :");

        txt_nama_user.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel4.setText("ID Invoice :");

        combo_id_inv.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_id_inv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_id_invActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel5.setText("Nama Customer :");

        txt_nama_customer.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel6.setText("Tanggal angkut :");

        tgl_angkut.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tgl_angkutPropertyChange(evt);
            }
        });

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
        btn_kosong.setText("Kosongkan");
        btn_kosong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kosongActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        jLabel7.setText("No Polis :");

        combo_polis.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_id)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(combo_jenis, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(txt_nama_user, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(combo_id_inv, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(txt_nama_customer, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(tgl_angkut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_tambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_ubah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_hapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_kosong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                    .addComponent(combo_polis, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(combo_jenis, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(combo_polis, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nama_user, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(combo_id_inv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nama_customer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tgl_angkut, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Data Angkut Limbah", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Montserrat SemiBold", 0, 14))); // NOI18N

        btn_cari.setText("Cari");
        btn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cariActionPerformed(evt);
            }
        });

        btn_cetak.setText("Cetak");
        btn_cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cetakActionPerformed(evt);
            }
        });

        tabel_angkut.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel_angkut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_angkutMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_angkut);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 1, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txt_cari)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
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
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void combo_jenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_jenisActionPerformed
        // TODO add your handling code here:
        combo_no_polis();
    }//GEN-LAST:event_combo_jenisActionPerformed

    private void combo_id_invActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_id_invActionPerformed
        // TODO add your handling code here:
        nama_customer();
    }//GEN-LAST:event_combo_id_invActionPerformed

    private void btn_kosongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kosongActionPerformed
        // TODO add your handling code here:
        kosong();
        autoNumber();
        load_data_angkut();
    }//GEN-LAST:event_btn_kosongActionPerformed

    private void tabel_angkutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_angkutMouseClicked
        // TODO add your handling code here:
        int baris = tabel_angkut.rowAtPoint(evt.getPoint());
        String id = tabel_angkut.getValueAt(baris, 0).toString();
        txt_id.setText(id);
        String jenis = tabel_angkut.getValueAt(baris, 1).toString();
        combo_jenis.setSelectedItem(jenis);
        String polis = tabel_angkut.getValueAt(baris, 2).toString();
        combo_polis.setSelectedItem(polis);
        String nama_user = tabel_angkut.getValueAt(baris, 3).toString();
        txt_nama_user.setText(nama_user);
        String id_inv = tabel_angkut.getValueAt(baris, 4).toString();
        combo_id_inv.setSelectedItem(id_inv);
        String nama_customer = tabel_angkut.getValueAt(baris, 5).toString();
        txt_nama_customer.setText(nama_customer);
        try{
        String tgl = tabel_angkut.getValueAt(baris, 6).toString();
        java.util.Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(tgl);
        tgl_angkut.setDate(date2);
        }
        catch(Exception e){
        System.out.print("error "+e);
        }
        
    }//GEN-LAST:event_tabel_angkutMouseClicked

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        if(txt_id.getText().equals("") || txt_nama_user.getText().equals("") || txt_nama_customer.getText().equals("")){
           JOptionPane.showMessageDialog(rootPane, "Data tidak boleh ada yang kosong","Pemberitahuan",JOptionPane.WARNING_MESSAGE);
        } else{
        tambah();
        kosong();
        autoNumber();
        load_data_angkut();
        
        }
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void tgl_angkutPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tgl_angkutPropertyChange
        // TODO add your handling code here:
        tanggal();
    }//GEN-LAST:event_tgl_angkutPropertyChange

    private void btn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cariActionPerformed
        // TODO add your handling code here:
        load_data_angkut();
        kosong();
        autoNumber();
    }//GEN-LAST:event_btn_cariActionPerformed

    private void btn_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahActionPerformed
        // TODO add your handling code here:
       try{
        String sql = "Select * from angkut_limbah where id_angkut = '"+txt_id.getText()+"'";
        java.sql.Connection con = (Connection)koneksi.koneksidb();
        java.sql.Statement pst = con.createStatement();
        java.sql.ResultSet res = pst.executeQuery(sql);
        
        if(res.first() == false){
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        }
        
        else{
        if(txt_id.getText().equals("") || txt_nama_user.getText().equals("") || txt_nama_customer.getText().equals("")){
           JOptionPane.showMessageDialog(rootPane, "Data tidak boleh ada yang kosong","Pemberitahuan",JOptionPane.WARNING_MESSAGE);
        } else{
        ubah();
        kosong();
        autoNumber();
        load_data_angkut();
        }
        
        }
       }catch(Exception e){
           JOptionPane.showMessageDialog(null, "Error "+e);
       }
    }//GEN-LAST:event_btn_ubahActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        try{
        String sql = "Select * from angkut_limbah where id_angkut = '"+txt_id.getText()+"'";
        java.sql.Connection con = (Connection)koneksi.koneksidb();
        java.sql.Statement pst = con.createStatement();
        java.sql.ResultSet res = pst.executeQuery(sql);
        
        if(res.first() == false){
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        }
        
        else{
        hapus();
        kosong();
        autoNumber();
        load_data_angkut();
        }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cetakActionPerformed
        // TODO add your handling code here:
        try {

            String path="src/kkp.report/angkut.jasper";      // letak penyimpanan report

            HashMap parameter = new HashMap();

            parameter.put("AGT",txt_id.getText());
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
            java.util.logging.Logger.getLogger(angkut_limbah.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(angkut_limbah.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(angkut_limbah.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(angkut_limbah.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new angkut_limbah().setVisible(true);
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
    private javax.swing.JComboBox<String> combo_jenis;
    private javax.swing.JComboBox<String> combo_polis;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabel_angkut;
    private com.toedter.calendar.JDateChooser tgl_angkut;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_nama_customer;
    private javax.swing.JTextField txt_nama_user;
    // End of variables declaration//GEN-END:variables
}
