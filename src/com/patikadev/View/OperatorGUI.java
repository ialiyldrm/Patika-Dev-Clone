package com.patikadev.View;

import com.patikadev.Helper.*;
import com.patikadev.Model.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class OperatorGUI extends JFrame {
    private final DefaultTableModel mdl_education_list;
    private final Object[] row_education_list;
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_userList;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_user_name;
    private JTextField fld_user_uname;
    private JTextField fld_user_pass;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JTextField fld_user_id;
    private JButton btn_user_delete;
    private JTextField fld_sh_user_name;
    private JTextField fld_sh_user_uname;
    private JComboBox cmb_sh_user_type;
    private JButton btn_user_sh;
    private JPanel pnl_patikaList;
    private JTable tbl_patikaList;
    private JScrollPane scrl_patikaList;
    private JPanel pnl_patika_add;
    private JTextField fld_patika_name;
    private JButton btn_add;
    private JPanel pnl_courseList;
    private JPanel pnl_user_tab;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_list;
    private JPanel pnl_course_add;
    private JTextField fld_course_name;
    private JTextField fld_course_lang;
    private JComboBox cmb_course_patika;
    private JComboBox cmb_course_user;
    private JButton btn_course_add;
    private JTable tbl_educationList;
    private JScrollPane scrl_educationList;
    private JPanel pnl_educationList;
    private JTextField fld_education_name;
    private JButton btn_ekle;
    private JTextField fld_education_id;
    private JButton btn_sil;
    private JTextField fld_education_comment;
    private JTextField fld_education_quiz;
    private JTextField fld_education_url;
    private JTextField fld_educator_id;
    private JTextField fld_courseName;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;
    private  DefaultTableModel mdl_patika_list;
    private  Object[] row_patika_list;
    private  JPopupMenu patikaMenu;
    private  DefaultTableModel mdl_course_list;
    private  Object[] row_course_list;


    private final Operator operator;

    public OperatorGUI(Operator operator) {
        this.operator=operator;
        add(wrapper);
        setSize(1000,600);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Hoşgeldin " + operator.getName());

        //ModelUSerList
        mdl_user_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_user_list = {"ID","Ad Soyad","Kullanıcı Adı","Şifre","Üyelik Tipi" };
        mdl_user_list.setColumnIdentifiers(col_user_list);
        row_user_list = new Object[col_user_list.length];
        loadUserModel();

        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);
        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try{
                String select_user_id = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString();
                fld_user_id.setText(select_user_id);
            }catch (Exception exception) {
                //Index -1 out of bounds for length hatasını engellemek için oluşturuldu.
            }
        });

        tbl_user_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE){
                int user_id = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString());
                String user_name = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),1).toString();
                String user_uname = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),2).toString();
                String user_pass = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),3).toString();
                String user_type = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),4).toString();

                if(User.update(user_id,user_name,user_uname,user_pass,user_type)){
                    Helper.showMsg("done");

                }

                loadUserModel();
                loadEducatorCombo();
                loadCourseModel();
            }
        });
        //##ModelUserList

        //ModelPatikaList

        patikaMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Güncelle");
        JMenuItem deleteMenu = new JMenuItem("Sil");
        patikaMenu.add(updateMenu);
        patikaMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int selected_id = Integer.parseInt(tbl_patikaList.getValueAt(tbl_patikaList.getSelectedRow(),0).toString());
            UpdatePatikaGUI updateGUI =new UpdatePatikaGUI(Patika.getFetch(selected_id));
            updateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPatikaModel();
                    loadPatikaCombo();
                    loadCourseModel();
                }
            });
        });

        deleteMenu.addActionListener(e -> {
            if(Helper.confirm("sure")){
                int selected_id = Integer.parseInt(tbl_patikaList.getValueAt(tbl_patikaList.getSelectedRow(),0).toString());
                if(Patika.delete(selected_id)){
                    Helper.showMsg("done");
                    loadPatikaModel();
                    loadPatikaCombo();
                    loadCourseModel();
                } else {
                    Helper.showMsg("error");
                }
            }
        });


        mdl_patika_list = new DefaultTableModel();
        Object[] col_patika_list = {"ID","Patika Adı"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new Object[col_patika_list.length];
        loadPatikaModel();

        tbl_patikaList.setModel(mdl_patika_list);
        tbl_patikaList.setComponentPopupMenu(patikaMenu);
        tbl_patikaList.getTableHeader().setReorderingAllowed(false);
        tbl_patikaList.getColumnModel().getColumn(0).setMaxWidth(75);

        tbl_patikaList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_patikaList.rowAtPoint(point);
                tbl_patikaList.setRowSelectionInterval(selected_row,selected_row);

            }
        });
        //Right click select
        tbl_patikaList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON3){
                    patikaMenu.show(e.getComponent(),e.getX(),e.getY());
                }
            }
        });

        //##ModelPatikaList

        //ModelCourseList
        mdl_course_list = new DefaultTableModel();
        Object[] col_course_list = {"ID","Ders Adı","Programlama Dili","Patika","Eğitmen"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        row_course_list = new Object[col_course_list.length];
        loadCourseModel();

        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);

        loadPatikaCombo();
        loadEducatorCombo();

        //##ModelCourseList

        //ModelEducationList
        mdl_education_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_education_list={"ID","Eğitim Adı","Ders Adı","Eğitim Açıklaması","Youtube Linki","Quiz","Eğitmen Adı","Eğitmen ID"};
        mdl_education_list.setColumnIdentifiers(col_education_list);
        row_education_list = new Object[col_education_list.length];
        loadEducationModel();

        tbl_educationList.setModel(mdl_education_list);
        tbl_educationList.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_educationList.getTableHeader().setReorderingAllowed(false);
        //Listeden seçilen eğitimin id'sini almınan metod.
        tbl_educationList.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_education_id = tbl_educationList.getValueAt(tbl_educationList.getSelectedRow(), 0).toString();
                fld_education_id.setText(selected_education_id);
            }catch (Exception exception){
                System.out.println(exception.getMessage());
            }
        });

        btn_ekle.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_education_name)
                    || Helper.isFieldEmpty(fld_education_url)
                    || Helper.isFieldEmpty(fld_education_comment)
                    || Helper.isFieldEmpty(fld_education_quiz)
                    || Helper.isFieldEmpty(fld_courseName)
                    || Helper.isFieldEmpty(fld_educator_id)
            ){
                Helper.showMsg("fill");
            }else{
                int course_id = Course.getFetch(fld_courseName.getText()).getId();
                String name = fld_education_name.getText();
                String url = fld_education_url.getText();
                int user_id =Integer.parseInt(fld_educator_id.getText());
                String comment = fld_education_comment.getText();
                String quiz = fld_education_quiz.getText();
                if(Education.add(course_id,name,url,user_id,comment,quiz)){
                    Helper.showMsg("done");
                    loadEducationModel();

                    fld_education_name.setText(null);
                    fld_courseName.setText(null);
                    fld_education_comment.setText(null);
                    fld_education_quiz.setText(null);
                    fld_education_url.setText(null);
                }else{
                    Helper.showMsg("Bir hata oluştu tekrar deneyin!");
                }
            }

        });
        btn_sil.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_education_id)){
                Helper.showMsg("fill");
            }else{
                if(Helper.confirm("sure")){
                    int education_id = Integer.parseInt(fld_education_id.getText());
                    if(Education.delete(education_id)){
                        Helper.showMsg("done");
                        loadEducationModel();
                        fld_education_id.setText(null);
                    }else{
                        Helper.showMsg("error");
                    }
                }
            }
        });

        //##ModelEDucationList


        btn_user_add.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_user_name) || Helper.isFieldEmpty(fld_user_uname) || Helper.isFieldEmpty(fld_user_pass)){
                Helper.showMsg("fill");
            }else{
                //int ID = Integer.parseInt(fld_user_ID.getText());
                String name = fld_user_name.getText();
                String uname = fld_user_uname.getText();
                String pass = fld_user_pass.getText();
                String type = cmb_user_type.getSelectedItem().toString();
                if(User.add(name,uname,pass,type)){
                    Helper.showMsg("done");
                    loadUserModel();
                    loadEducatorCombo();
                    //fldClean() şeklinde metod geliştirip hatalardan sonra da kutulardaki yazıları silebilirim.
                    //fld_user_ID.setText(null);
                    fld_user_name.setText(null);
                    fld_user_uname.setText(null);
                    fld_user_pass.setText(null);

                }
            }
        });
        btn_user_delete.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_user_id)){
                Helper.showMsg("fill");
            } else {
                if(Helper.confirm("sure")){
                    int user_id = Integer.parseInt(fld_user_id.getText());
                    if(User.delete(user_id)){
                        Helper.showMsg("done");
                        loadUserModel();
                        loadEducatorCombo();
                        loadCourseModel();
                        fld_user_id.setText(null);
                    } else {
                        Helper.showMsg("error");
                    }
                }
            }
        });
        btn_user_sh.addActionListener(e -> {
            String name = fld_sh_user_name.getText();
            String uname = fld_sh_user_uname.getText();
            String type = cmb_sh_user_type.getSelectedItem().toString();
            String query = User.searchQuery(name,uname,type);
            ArrayList<User> searchingUser = User.searchUserList(query);
            loadUserModel(searchingUser);
        });

        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI login = new LoginGUI();
        });
        btn_add.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_patika_name)){
                Helper.showMsg("fill");
            } else {
                if(Patika.add(fld_patika_name.getText())) {
                    Helper.showMsg("done");
                    loadPatikaModel();
                    loadPatikaCombo();
                    fld_patika_name.setText(null);
                } else {
                    Helper.showMsg("error");
                }
            }
        });
        btn_course_add.addActionListener(e -> {
            Item patikaItem = (Item) cmb_course_patika.getSelectedItem();
            Item userItem = (Item) cmb_course_user.getSelectedItem();
            if(Helper.isFieldEmpty(fld_course_name) || Helper.isFieldEmpty(fld_course_lang )){
                Helper.showMsg("fill");
            } else {
                if(Course.add(userItem.getKey(),patikaItem.getKey(),fld_course_name.getText(),fld_course_lang.getText())){
                    Helper.showMsg("done");
                    loadCourseModel();
                    fld_course_name.setText(null);
                    fld_course_lang.setText(null);
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }

    private void loadCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for(Course obj: Course.getList()){
            i = 0;
            row_course_list[i++] = obj.getId();
            row_course_list[i++] = obj.getName();
            row_course_list[i++] = obj.getLanguage();
            row_course_list[i++] = obj.getPatika().getName();
            row_course_list[i++] = obj.getEducator().getName();
            mdl_course_list.addRow(row_course_list);
        }
    }

    private void loadPatikaModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_patikaList.getModel();
        clearModel.setRowCount(0);
        int i;
        for(Patika obj : Patika.getList()){
            i = 0;
            row_patika_list[i++] = obj.getId();
            row_patika_list[i++] = obj.getName();
            mdl_patika_list.addRow(row_patika_list);
        }
    }

    public void  loadUserModel(){

        DefaultTableModel clearModel= (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for(User obj : User.getList()){
            i = 0;
            row_user_list[i++] = obj.getID();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getUname();
            row_user_list[i++] = obj.getPass();
            row_user_list[i++] = obj.getType();
            mdl_user_list.addRow(row_user_list);
        }

    }

    public void  loadUserModel(ArrayList<User> list){

        DefaultTableModel clearModel= (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for(User obj : list){
            int i=0;
            row_user_list[i++] = obj.getID();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getUname();
            row_user_list[i++] = obj.getPass();
            row_user_list[i++] = obj.getType();
            mdl_user_list.addRow(row_user_list);
        }

    }
    public void loadPatikaCombo(){
        cmb_course_patika.removeAllItems();
        for(Patika obj:Patika.getList()){
            cmb_course_patika.addItem(new Item(obj.getId(),obj.getName()));
        }
    }

    public  void loadEducatorCombo(){
        cmb_course_user.removeAllItems();
        for(User obj: User.getList()){
            if(obj.getType().equals("educator")){
                cmb_course_user.addItem(new Item(obj.getID(),obj.getName()));
            }
        }
    }

    private  void loadEducationModel(){
        DefaultTableModel clearModel=(DefaultTableModel) tbl_educationList.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for(Education obj: Education.getList()){
            i=0;
            row_education_list[i++] = obj.getId();
            row_education_list[i++] = obj.getName();
            row_education_list[i++] = obj.getCourse().getName();
            row_education_list[i++] = obj.getComment();
            row_education_list[i++] = obj.getUrl();
            row_education_list[i++] = obj.getQuiz();
            row_education_list[i++] = obj.getUser().getName();
            row_education_list[i++] = obj.getUser().getID();
            mdl_education_list.addRow(row_education_list);
        }
    }
}
