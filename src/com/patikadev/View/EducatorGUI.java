package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Course;
import com.patikadev.Model.Education;
import com.patikadev.Model.Educator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EducatorGUI extends JFrame {
    private JPanel wrapper;
    private JPanel pnl_top;
    private JLabel lbl_welcome;
    private JButton btn_logout;
    private JPanel pnl_educationList;
    private JTextField fld_education_name;
    private JTextField fld_education_url;
    private JButton btn_ekle;
    private JTextField fld_education_id;
    private JButton btn_sil;
    private JPanel pnl_courseList;
    private JScrollPane scrl_courseList;
    private JTable tbl_course_list;
    private JTabbedPane tab_educator;
    private JScrollPane scrl_educationList;
    private JTable tbl_education_list;
    private JTextField fld_education_comment;
    private JTextField fld_education_quiz;
    private JTextField fld_course_name;
    private DefaultTableModel mdl_course_list;
    private final Object[] row_course_list;
    private  DefaultTableModel mdl_education_list;
    private  final  Object[] row_education_list;
    private Educator educator;
    public EducatorGUI(Educator educator){
        this.educator=educator;
        add(wrapper);
        setSize(1000,600);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);
        setResizable(false);

        lbl_welcome.setText("Hoşgeldin " + educator.getName());
        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI login = new LoginGUI();
        });
        
        //ModelCourseList

        mdl_course_list = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column == 0)
                    return false;
                return super.isCellEditable(row, column);
            }
        };

        Object[] col_course_list = {"ID","Ders Adı","Programlama Dili","Patika","Eğitmen"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        row_course_list = new Object[col_course_list.length];
        loadCourseModel();

        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);

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
        Object[] col_education_list={"ID","Eğitim Adı","Ders Adı","Eğitim Açıklaması","Youtube Linki","Quiz","Eğitmen Adı"};
        mdl_education_list.setColumnIdentifiers(col_education_list);
        row_education_list = new Object[col_education_list.length];
        loadEducationModel();

        tbl_education_list.setModel(mdl_education_list);
        tbl_education_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_education_list.getTableHeader().setReorderingAllowed(false);
        //Listeden seçilen eğitimin id'sini alan metod.
        tbl_education_list.getSelectionModel().addListSelectionListener(e -> {
        try {
            String selected_education_id = tbl_education_list.getValueAt(tbl_education_list.getSelectedRow(), 0).toString();
            fld_education_id.setText(selected_education_id);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        });

        //##ModelEDucationLİst

        btn_ekle.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_education_name)
                    || Helper.isFieldEmpty(fld_education_url)
                    || Helper.isFieldEmpty(fld_education_comment)
                    || Helper.isFieldEmpty(fld_education_quiz)
                    || Helper.isFieldEmpty(fld_course_name)
            ){
                Helper.showMsg("fill");
            }else{
                int course_id = Course.getFetch(fld_course_name.getText()).getId();
                String name = fld_education_name.getText();
                String url = fld_education_url.getText();
                int user_id = this.educator.getID();
                String comment = fld_education_comment.getText();
                String quiz = fld_education_quiz.getText();
                if(Education.add(course_id,name,url,user_id,comment,quiz)){
                    Helper.showMsg("done");
                    loadEducationModel();

                    fld_education_name.setText(null);
                    fld_course_name.setText(null);
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
    }
    private void loadCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);
        int i = 0;
        for(Course obj: Course.getListByUSer(educator.getID())){
            i = 0;
            row_course_list[i++] = obj.getId();
            row_course_list[i++] = obj.getName();
            row_course_list[i++] = obj.getLanguage();
            row_course_list[i++] = obj.getPatika().getName();
            row_course_list[i++] = obj.getEducator().getName();
            mdl_course_list.addRow(row_course_list);
        }
    }

    private  void loadEducationModel(){
        DefaultTableModel clearModel=(DefaultTableModel) tbl_education_list.getModel();
        clearModel.setRowCount(0);
        int i=0;
        for(Education obj: Education.getListByUserId(educator.getID())){
            i=0;
            row_education_list[i++] = obj.getId();
            row_education_list[i++] = obj.getName();
            row_education_list[i++] = obj.getCourse().getName();
            row_education_list[i++] = obj.getComment();
            row_education_list[i++] = obj.getUrl();
            row_education_list[i++] = obj.getQuiz();
            row_education_list[i++] = obj.getUser().getName();
            mdl_education_list.addRow(row_education_list);
        }
    }
}
