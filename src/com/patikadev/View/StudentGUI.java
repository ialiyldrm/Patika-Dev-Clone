package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.Education;
import com.patikadev.Model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentGUI extends JFrame{
    private final DefaultTableModel mdl_education_list;
    private final Object[] row_education_list;
    private JPanel wrapper;
    private JPanel pnl_top;
    private JLabel lbl_welcome;
    private JButton btn_logout;
    private JScrollPane scrl_educationList;
    private JTable tbl_education_list;


    private final Student student;
    public StudentGUI(Student student){
        this.student=student;
        add(wrapper);
        setSize(1000,500);
        setLocation(Helper.screenCenterPoint("x",getSize()),Helper.screenCenterPoint("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Hoşgeldin " + student.getName());
        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI login = new LoginGUI();
        });

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
    }

    private  void loadEducationModel(){
        DefaultTableModel clearModel=(DefaultTableModel) tbl_education_list.getModel();
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
            mdl_education_list.addRow(row_education_list);
        }
    }
}
