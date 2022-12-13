package main.java;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;

public class UserHome extends JFrame implements java.awt.event.ActionListener{
    private JPanel contentpane;
    private int user_id;
    private JButton but_enc=new JButton("ENCRYPT");
    private JButton but_dec=new JButton("DECRYPT");
    JTable table;
    JScrollPane scroll;
    DefaultTableModel model=new DefaultTableModel();

    // public static void main(String args[]){
    //     UserHome uh=new UserHome(1);
    //                 uh.setTitle("FILE ENCRYPTION SYSTEM");
    //                 uh.setExtendedState(JFrame.MAXIMIZED_BOTH);
    //                 uh.setVisible(true);
    // }
    

    UserHome(int user_id){
        this.user_id=user_id;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentpane=new JPanel();
        contentpane.setOpaque(true);
        contentpane.setBackground(Color.decode("#3b5998"));
        contentpane.setBorder(new EmptyBorder(15,15,15,15));
        setContentPane(contentpane);
        contentpane.setLayout(new GridLayout(4,0));

        JPanel bu=new JPanel();
        bu.setBackground(Color.WHITE);
        bu.setBorder(new EmptyBorder(15,15,15,15));

        JButton bg= new JButton("");
        bg.setEnabled(false);
        bg.setBackground(Color.decode("#1e7eae"));
        bg.setFont(new Font("Times New Roman",Font.PLAIN,50));
        bg.setBounds(620,160,700,500);
        //contentpane.add(bg,JLayeredPane.DEFAULT_LAYER);
        

        JLabel lbl_login= new JLabel("FILE ENCRYPTION SYSTEM");
        lbl_login.setForeground(Color.decode("#ffffff"));
        lbl_login.setFont(new Font("Times New Roman",Font.PLAIN,40));
        lbl_login.setBounds(250,100,550,95);
        lbl_login.setHorizontalAlignment(JLabel.CENTER);
        contentpane.add(lbl_login);

        but_enc.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        but_enc.setBounds(280,200,190,50);
        but_enc.setBackground(Color.decode("#3b5998"));
        but_enc.setForeground(new Color(255,255,255));
        but_enc.addActionListener(this);
        bu.add(but_enc);
        
        but_dec.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        but_dec.setBounds(550,200,190,50);
        but_dec.setBackground(Color.decode("#3b5998"));
        but_dec.setForeground(new Color(255,255,255));
        but_dec.addActionListener(this);     
        bu.add(but_dec);

        add(bu);

        //display all files of user
        String[] columnNames={"File ID","File Name","Remarks"};

        model.setColumnIdentifiers(columnNames);
        //DefaultTableModel model = new DefaultTableModel(tm.getData1(), tm.getColumnNames());
        //table = new JTable(model);
        table = new JTable();
        table.setModel(model);
        table.setPreferredSize(new Dimension(600, 600));
        //table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        table.setBackground(Color.WHITE);        
        table.setFont(new Font("Times New Roman", Font.PLAIN, 15));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/file_enc_sys","","");
            PreparedStatement st=conn.prepareStatement("select file_id,file_name,description from uploads where user_id=?");
            st.setInt(1,user_id);
            ResultSet rs=st.executeQuery();
            int fid;
            String fname,desc;
            while(rs.next()){
                fid=rs.getInt("file_id");
                fname=rs.getString("file_name");
                desc=rs.getString("description");
                System.out.println(fid);
                System.out.println(fname);
                System.out.println(desc);
                model.addRow(new Object[]{fid,fname,desc});
            } 


        }catch(Exception e){
            e.printStackTrace();
        }

        contentpane.add(scroll,BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==but_enc){
            
            Encrypt uh=new Encrypt(user_id);
            uh.setTitle("FILE ENCRYPTION SYSTEM");
            uh.setExtendedState(JFrame.MAXIMIZED_BOTH);
            uh.setVisible(true);
            dispose();

        }
        if(e.getSource()==but_dec){
            Decrypt uh=new Decrypt(user_id);
            uh.setTitle("File Decryption System");
            uh.setExtendedState(JFrame.MAXIMIZED_BOTH);
            uh.setVisible(true);
            dispose();
        }
        
    }
    
}
