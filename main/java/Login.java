package main.java;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener{
    private JTextField id;
    private JTextField name;
    private JPasswordField pwd;
    private JButton but_login;
    private JLayeredPane contentpane;

    // public static void main(String args[]){
    //     try{
    //         Login frame=new Login();
    //         frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    //         frame.setVisible(true);

    //     }
    //     catch(Exception e){
    //         e.printStackTrace();
    //     }
    // }

    public Login(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450,190,650,500); //x y width height
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

        JLabel lbl_login= new JLabel("USER LOGIN");
        lbl_login.setForeground(Color.decode("#3b5998"));
        lbl_login.setFont(new Font("Times New Roman",Font.PLAIN,50));
        lbl_login.setBounds(820,190,400,95);
        contentpane.add(lbl_login,JLayeredPane.PALETTE_LAYER);

        id=new JTextField();
        name=new JTextField();
        id.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        id.setBounds(920, 310, 300, 40);
        contentpane.add(id,JLayeredPane.PALETTE_LAYER);
        name.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        name.setBounds(920, 370, 300, 40);
        contentpane.add(name,JLayeredPane.PALETTE_LAYER);

        pwd=new JPasswordField();
        pwd.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        pwd.setBounds(920, 430, 300, 40);
        contentpane.add(pwd,JLayeredPane.PALETTE_LAYER);

        name.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.decode("#3b5998")));
        id.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.decode("#3b5998")));
        pwd.setBorder(BorderFactory.createMatteBorder(0,0,2,0,Color.decode("#3b5998")));


        JLabel lbl_id=new JLabel("USER ID");
        lbl_id.setBackground(Color.BLACK);
        lbl_id.setForeground(Color.decode("#3b5998"));
        lbl_id.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lbl_id.setBounds(750, 320, 190, 40);
        contentpane.add(lbl_id,JLayeredPane.PALETTE_LAYER);

        JLabel lbl_name=new JLabel("NAME");
        lbl_name.setBackground(Color.BLACK);
        lbl_name.setForeground(Color.decode("#3b5998"));
        lbl_name.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lbl_name.setBounds(750, 380, 190, 40);
        contentpane.add(lbl_name,JLayeredPane.PALETTE_LAYER);

        JLabel lbl_pwd=new JLabel("PASSWORD");
        lbl_pwd.setBackground(Color.WHITE);
        lbl_pwd.setForeground(Color.decode("#3b5998"));
        lbl_pwd.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lbl_pwd.setBounds(750, 440, 190, 40);
        contentpane.add(lbl_pwd,JLayeredPane.PALETTE_LAYER);

        
        but_login=new JButton("LOGIN");
        but_login.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        but_login.setBounds(880,520,200,50);
        but_login.setBackground(Color.decode("#3b5998"));
        but_login.setForeground(new Color(255,255,220));
        but_login.addActionListener(this);     
        contentpane.add(but_login,JLayeredPane.PALETTE_LAYER);     

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int input_id=Integer.parseInt(id.getText());
            String input_name=name.getText();
            String input_pwd=new String(pwd.getPassword()); //resolved with new
            
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/file_enc_sys","root","12345678");
                PreparedStatement st=conn.prepareStatement("select user_id,name,password from user_data where user_id=? and name=? and password=?;");
                st.setInt(1,input_id);
                st.setString(2,input_name);
                st.setString(3,input_pwd);

                ResultSet rs=st.executeQuery();
                if(rs.next()){
                    conn.close();

                    UserHome uh=new UserHome(input_id);
                    uh.setTitle("File Encrytion System");
                    uh.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    uh.setVisible(true);
                    dispose();

                    
                }else{
                    JOptionPane.showMessageDialog(but_login,"Wrong user id & Password!");
                }
            }
            catch (Exception sqlException){
                sqlException.printStackTrace();
            }        
    }

}
