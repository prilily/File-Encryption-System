package main.java;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Decrypt extends JFrame implements ActionListener{

    private JLayeredPane contentpane;
    private int user_id;
    private JTextField file_id;
    private JTextField file_name;
    private JTextField file_key;
    private JButton but_dec,but_home;

    // public static void main(String args[]){
    //     Decrypt uh=new Decrypt(1);
    //                 uh.setTitle("FILE DECRYPTION SYSTEM");
    //    uh.setExtendedState(JFrame.MAXIMIZED_BOTH);
    //                 uh.setVisible(true);
    // }

    Decrypt(int user_id){
        this.user_id=user_id;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(200,300,700,500); //x y width height
        setResizable(false);
        contentpane=new JLayeredPane();
        contentpane.setOpaque(true);
        contentpane.setBackground(Color.decode("#3b5998"));
        contentpane.setBorder(new EmptyBorder(15,15,15,15));
        setContentPane(contentpane);
        contentpane.setLayout(null);

        JButton bg= new JButton("");
        bg.setEnabled(false);
        bg.setBackground(Color.decode("#ffffff"));
        bg.setFont(new Font("Times New Roman",Font.PLAIN,50));
        bg.setBounds(620,160,700,500);
        contentpane.add(bg,JLayeredPane.DEFAULT_LAYER);

        JLabel lbl_login= new JLabel("FILE DECRYPTION SYSTEM");
        lbl_login.setForeground(Color.decode("#3b5998"));
        lbl_login.setFont(new Font("Times New Roman",Font.PLAIN,40));
        lbl_login.setBounds(710,190,550,95);
        contentpane.add(lbl_login,JLayeredPane.PALETTE_LAYER);

        file_id=new JTextField();
        file_id.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        file_id.setBounds(920, 310, 300, 50);
        contentpane.add(file_id,JLayeredPane.PALETTE_LAYER);
        file_id.setForeground(Color.decode("#3b5998"));

        file_name=new JTextField();
        file_key=new JTextField();
        file_name.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        file_key.setForeground(Color.decode("#3b5998"));
        file_name.setForeground(Color.decode("#3b5998"));

        file_name.setBounds(920,370, 300, 50);
        contentpane.add(file_name,JLayeredPane.PALETTE_LAYER);

        file_key.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        file_key.setBounds(920, 430, 300, 50);
        contentpane.add(file_key,JLayeredPane.PALETTE_LAYER);

        file_id.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.decode("#3b5998")));
        file_key.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.decode("#3b5998")));
        file_name.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.decode("#3b5998")));

        JLabel lbl_id=new JLabel("FILE ID");
        lbl_id.setBackground(Color.BLACK);
        lbl_id.setForeground(Color.decode("#3b5998"));
        lbl_id.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lbl_id.setBounds(750, 320, 190, 40);
        contentpane.add(lbl_id,JLayeredPane.PALETTE_LAYER);

        JLabel lbl_name=new JLabel("FILE NAME");
        lbl_name.setBackground(Color.BLACK);
        lbl_name.setForeground(Color.decode("#3b5998"));
        lbl_name.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lbl_name.setBounds(750, 380, 190, 40);
        contentpane.add(lbl_name,JLayeredPane.PALETTE_LAYER);

        JLabel lbl_key=new JLabel("FILE KEY"); //must be 16 bytes
        lbl_key.setBackground(Color.BLACK);
        lbl_key.setForeground(Color.decode("#3b5998"));
        lbl_key.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lbl_key.setBounds(750, 440, 190, 40);
        contentpane.add(lbl_key,JLayeredPane.PALETTE_LAYER);

        but_dec=new JButton("DECRYPT");
        but_dec.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        but_dec.setBounds(750,550,200,50);
        but_dec.setBackground(Color.decode("#3b5998"));
        but_dec.setForeground(new Color(255,255,255));
        but_dec.addActionListener(this);     
        contentpane.add(but_dec,JLayeredPane.PALETTE_LAYER);

        but_home=new JButton("HOME");
        but_home.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        but_home.setBounds(1020,550,200,50);
        but_home.setBackground(Color.decode("#3b5998"));
        but_home.setForeground(Color.decode("#ffffff"));
        but_home.addActionListener(this);     
        contentpane.add(but_home,JLayeredPane.PALETTE_LAYER);
}

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if(arg0.getSource()==but_dec){
            int ip_file_id=-1;
            String ip_file_key,ip_file_name;
            ip_file_id=Integer.parseInt(file_id.getText());
            ip_file_key=file_key.getText();
            ip_file_name=file_name.getText();
            if(ip_file_id==-1 || ip_file_key.isEmpty() || ip_file_name.isEmpty()){
                JOptionPane.showMessageDialog(but_dec,"Empty fields not allowed.!");
            }else{
                //get file from database by checking id and filenamethen decrypt
                /*
                  implement mysql check here
                  // or given filename find absolute path and send that file. t 
                
                 */
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/file_enc_sys","root","12345678");
                    PreparedStatement check=conn.prepareStatement("select user_id from uploads where file_id=?");
                    check.setInt(1, ip_file_id);
                    ResultSet r=check.executeQuery();
                    while(r.next()){
                        int uid=r.getInt("user_id");

                        if(uid==user_id){
                            System.out.println("USER ID MATCHES");
                            PreparedStatement sql=conn.prepareStatement("select * from uploads where file_id=?");
                            sql.setInt(1, ip_file_id);
                            ResultSet rs=sql.executeQuery();

                            while(rs.next()){       
                                String file_key=rs.getString("file_key");
                                System.out.println(file_key);
                                if(file_key.equals(ip_file_key)){
                                    System.out.println("FILE KEY MATCHES");
                                    Blob data=rs.getBlob("data");
                                    //new file object with given string
                                    File enc_file=new File("temp.encrypted");
                                    InputStream inputStream=data.getBinaryStream();
                                    OutputStream outputStream=new FileOutputStream(enc_file.getAbsolutePath());

                                    int bytesread=-1;
                                    byte[] buffer=new byte[(int) data.length()];
                                    while((bytesread=inputStream.read(buffer))!=-1){
                                        outputStream.write(buffer,0,bytesread);
                                    }

                                    inputStream.close();
                                    outputStream.close();

                                    File dec_file=new File("/home/milano/Downloads/"+ip_file_id+".decrypted");
                                    try{
                                        Algo.decrypt(file_key, enc_file, dec_file);
                                        System.out.println("SUCCESS DECRYPTING");
                                        JOptionPane.showMessageDialog(null,"Decryption successful, file saved in downloads");
                                        dispose();
                                        UserHome uh=new UserHome(user_id);
                                        uh.setExtendedState(JFrame.MAXIMIZED_BOTH);
                                        uh.setTitle("File Encrytion System");
                                        uh.setVisible(true);
                                    }catch(Exception ex){
                                        ex.printStackTrace();
                                    }

                                }
                                else{
                                    JOptionPane.showMessageDialog(null,"The file keys don't match");
                                }
                            }
                        }
                        else{
                            JOptionPane.showMessageDialog(null,"invalid user id for given file id");
                        }
                    }
                    conn.close();

                }catch(Exception e){
                    e.printStackTrace();
                }
                
                
                
            }
        }
        else if(arg0.getSource()==but_home){
            UserHome uh=new UserHome(user_id);
            uh.setTitle("File Encrytion System");
            uh.setExtendedState(JFrame.MAXIMIZED_BOTH);
            uh.setVisible(true);
            dispose();
        }
        
    }
}

/*
 File input_file=new File("/home/milano/Desktop/JAVA/EncryptionFileSystem/File Encrytption System/document.encrypted"); //change it from database file
File dec_file=new File("document.decrypted");
System.out.println(dec_file.getAbsolutePath());
try{
    Algo.decrypt(ip_file_key, input_file, dec_file);
    System.out.println("SUCCESS DECRYPTING");
}catch(Exception ex){
    ex.printStackTrace();
}

 */