package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.User;

import javax.swing.*;

public class RegisterGUI extends JFrame {
    private JPanel pnl_top;
    private JPanel wrapper;
    private JTextField fld_user_name;
    private JTextField fld_user_uname;
    private JTextField fld_user_pass;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JPanel pnl_bottom;

    public RegisterGUI(){
        add(wrapper);
        setSize(450,350);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
        btn_user_add.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_user_name)
                    || Helper.isFieldEmpty(fld_user_uname)
                    || Helper.isFieldEmpty(fld_user_pass)){
                Helper.showMsg("fill");
            }else {
                String name = fld_user_name.getText();
                String uname = fld_user_uname.getText();
                String pass = fld_user_pass.getText();
                String type = cmb_user_type.getSelectedItem().toString();
                if(User.add(name,uname,pass,type)) {
                    Helper.showMsg("done");
                    fld_user_name.setText(null);
                    fld_user_uname.setText(null);
                    fld_user_pass.setText(null);
                }
            }

            dispose();
            LoginGUI login = new LoginGUI();
        });
    }
}
