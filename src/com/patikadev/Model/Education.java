package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;
import com.patikadev.Helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Education {
    private String name;
    private int id;
    private int user_id;
    private int course_id;
    private String url;
    private String comment;
    private String quiz;
    private Course course;
    private User user;

    public Education(String name, int id, int user_id, int course_id, String url, String comment,String quiz) {
        this.name = name;
        this.id = id;
        this.user_id = user_id;
        this.course_id = course_id;
        this.url = url;
        this.comment = comment;
        this.quiz=quiz;
        this.user= User.getFetch(user_id);
        this.course=Course.getFetch(course_id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getQuiz() {
        return quiz;
    }

    public void setQuiz(String quiz) {
        this.quiz = quiz;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public  static ArrayList<Education> getList(){
        ArrayList<Education> educationList= new ArrayList<>();

        Education obj;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM public.education");
            while (rs.next()){
                String name = rs.getString("name");
                int id = rs.getInt("id");
                int course_id = rs.getInt("course_id");
                int user_id = rs.getInt("user_id");
                String url = rs.getString("url");
                String comment=rs.getString("comment");
                String quiz=rs.getString("quiz");
                obj = new Education(name,id,user_id,course_id,url,comment,quiz);
                educationList.add(obj);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  educationList;
    }

    public  static ArrayList<Education> getListByUserId(int user_id){
        ArrayList<Education> educationList= new ArrayList<>();

        Education obj;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM public.education WHERE user_id = " + user_id);
            while (rs.next()){
                String name = rs.getString("name");
                int id = rs.getInt("id");
                int course_id = rs.getInt("course_id");
                int userID = rs.getInt("user_id");
                String url = rs.getString("url");
                String comment=rs.getString("comment");
                String quiz=rs.getString("quiz");
                obj = new Education(name,id,userID,course_id,url,comment,quiz);
                educationList.add(obj);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  educationList;
    }

    public static  boolean add(int course_id,String name,String url,int user_id,String comment,String quiz){
        String query = "INSERT INTO public.education (course_id,name,url,user_id,comment,quiz) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,course_id);
            pr.setString(2,name);
            pr.setString(3,url);
            pr.setInt(4,user_id);
            pr.setString(5,comment);
            pr.setString(6,quiz);
            int response = pr.executeUpdate();

            if(response == -1){
                Helper.showMsg("error");
            }

            return response != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean delete(int id){
        String query = "DELETE FROM public.education WHERE id = ?";

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        return  true;
    }
}
