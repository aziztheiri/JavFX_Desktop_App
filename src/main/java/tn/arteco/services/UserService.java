package tn.arteco.services;

import tn.arteco.iservices.Iservice;
import tn.arteco.models.Artiste;
import tn.arteco.models.NonArtiste;
import tn.arteco.models.Roles;
import tn.arteco.models.User;
import tn.arteco.utils.HashPassword;
import tn.arteco.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserService implements Iservice<User> {
    private Connection conn;
    private PreparedStatement preparedStatement;
    private Statement statement;
    public UserService(){
        conn= MyDataBase.getInstance().getConnection();
    }
    @Override
    public void add(User user) {
        if(user instanceof Artiste)
            user.setRole(Roles.ARTISTE);
        else if(user instanceof NonArtiste)
            user.setRole(Roles.NONARTISTE);
        try{
            preparedStatement= conn.prepareStatement("insert into user (username,nom,prenom,password,email,imageUrl,role,etat) values(?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getNom());
            preparedStatement.setString(3, user.getPrenom());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, user.getImageUrl());
            preparedStatement.setString(7, user.getRole().toString());
            preparedStatement.setBoolean(8,true);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> ret=new ArrayList<>();
        try{
            statement= conn.createStatement();
            ResultSet rs= statement.executeQuery("select * from user");
            while(rs.next()){
                int id=rs.getInt("userId");
                String username = rs.getString("username");
                String name=rs.getString("nom");
                String prenom=rs.getString("prenom");
                String password=rs.getString("password");
                String email=rs.getString("email");
                String imageUrl=rs.getString("imageUrl");
                String role=rs.getString("role");
                boolean etat =rs.getBoolean("etat");
                int points=rs.getInt("points");
                User user=null;
                if(role.equals("NONARTISTE")){
                    user=new NonArtiste(id,username,name,prenom,password,email,Roles.NONARTISTE,imageUrl,etat,points);
                } else if (role.equals("ARTISTE")) {
                    user=new Artiste(id,username,name,prenom,password,email,Roles.NONARTISTE,imageUrl,etat,points);
                }
                else {
                    user=new User(id,username,name,prenom,password,email,Roles.ADMIN,imageUrl,etat);
                }
                ret.add(user);

            }
            statement.close();
            rs.close();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }

        return ret;
    }
    public List<User> getActivate(){
        return getAll().stream().filter(User::isEtat).collect(Collectors.toList());
    }
    public List<User> getInactivate(){
        return getAll().stream().filter(e-> !e.isEtat()).collect(Collectors.toList());
    }
    public List<User> getArtists(){
        return getAll().stream().filter(e-> e.getRole().toString().equals("ARTISTE")).collect(Collectors.toList());
    }
    public List<User> getNonArtists(){
        return getAll().stream().filter(e->e.getRole().toString().equals("NONARTISTE")).collect(Collectors.toList());
    }

    @Override
    public void update(User user) {
        try{
            preparedStatement=conn.prepareStatement("update user set nom=?,prenom=?,password=?,email=?,imageURL=?,etat=?,role=?,username=? where userId=?");
            preparedStatement.setString(1, user.getNom());
            preparedStatement.setString(2, user.getPrenom());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getImageUrl());
            preparedStatement.setBoolean(6, user.isEtat());
            preparedStatement.setString(7,user.getRole().toString());
            preparedStatement.setString(8,user.getUsername());
            preparedStatement.setInt(9,user.getUserId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(int userId) {
        try{
            preparedStatement= conn.prepareStatement("delete from user where userId=? ");
            preparedStatement.setInt(1,userId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    public User getUserByUsername(String username){
        return getAll().stream().filter(e->e.getUsername().equals(username)).findFirst().orElse(null);
    }
    public User getById(int userId){
        return getAll().stream().filter(e->e.getUserId()==userId).findFirst().orElse(null);
    }
    public boolean usernameExists(String username){
        return getAll().stream().anyMatch(e->e.getUsername().equals(username));
    }
    public List<User> sortByUsername(List<User> list){
        list.sort((a,b)->
                a.getUsername().compareTo(b.getUsername())
        );
        return list;
    }
    public List<User> sortBymats(List<User> list){
        return list.stream().filter(e-> e instanceof NonArtiste na).sorted((a,b)->{
            return ((NonArtiste) b).getListeMateriel().size()- ((NonArtiste) a).getListeMateriel().size();
        }).collect(Collectors.toList());
    }
    public List<User> sortByProds(List<User> list){
        return list.stream().filter(e-> e instanceof Artiste).sorted((a,b)->{
            return ((Artiste) b).getListeProduitFini().size()- ((Artiste) a).getListeProduitFini().size();
        }).collect(Collectors.toList());
    }
    public List<User> sortByFact(List<User> list){
        return list.stream().filter(e-> e instanceof NonArtiste na).sorted((a,b)->{
            return ((NonArtiste) b).getListeFacture().size()- ((NonArtiste) a).getListeFacture().size();
        }).collect(Collectors.toList());
    }
    public List<User> sortByQuiz(List<User> list){
        return list.stream().filter(e-> e instanceof NonArtiste na).sorted((a,b)->{
            return ((NonArtiste) b).getListeResultat().size()- ((NonArtiste) a).getListeResultat().size();
        }).collect(Collectors.toList());
    }
    public List<User> sortByParticipation(List<User> list){
        return list.stream().filter(e-> e instanceof NonArtiste na).sorted((a,b)->{
            return ((NonArtiste) b).getListeParticipation().size()- ((NonArtiste) a).getListeParticipation().size();
        }).collect(Collectors.toList());
    }
    public void updateArtiste(NonArtiste artiste){
        try{
            preparedStatement=conn.prepareStatement("update user set nom=?,prenom=?,password=?,email=?,imageURL=?,etat=?,role=?,username=?,points=? where userId=?");
            preparedStatement.setString(1, artiste.getNom());
            preparedStatement.setString(2, artiste.getPrenom());
            preparedStatement.setString(3, artiste.getPassword());
            preparedStatement.setString(4, artiste.getEmail());
            preparedStatement.setString(5, artiste.getImageUrl());
            preparedStatement.setBoolean(6, artiste.isEtat());
            preparedStatement.setString(7,artiste.getRole().toString());
            preparedStatement.setString(8,artiste.getUsername());
            preparedStatement.setInt(9,artiste.getPoints());
            preparedStatement.setInt(10,artiste.getUserId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }
    public boolean emailExists(String email){
        return getAll().stream().anyMatch(e->e.getEmail().equals(email));
    }
    public User getByEmail(String email){
        return getAll().stream().filter(e->e.getEmail().equals(email)).findFirst().orElse(null);
    }
    public void updateUserPoints(int id ,int points){
        String req="UPDATE user SET points = ? WHERE userId = ?";
        try{
            preparedStatement=conn.prepareStatement(req);
            preparedStatement.setInt(1,points);
            preparedStatement.setInt(2,id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
