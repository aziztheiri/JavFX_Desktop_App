package tn.arteco.services;

import tn.arteco.iservices.Iservice;
import tn.arteco.models.UserRequest;
import tn.arteco.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserRequestService implements Iservice<UserRequest> {
    private Connection conn;
    private PreparedStatement preparedStatement;
    private Statement statement;
    public UserRequestService(){
        conn= MyDataBase.getInstance().getConnection();
    }
    @Override
    public void add(UserRequest activationRequest) {
        try{
            preparedStatement=conn.prepareStatement("insert into userrequest(typeRequest,etat,dateRequest,idUser) values(?,?,current_date(),?)");
            preparedStatement.setString(1,activationRequest.getTypeRequest());
            preparedStatement.setString(2,activationRequest.getEtat());
            preparedStatement.setInt(3,activationRequest.getUser().getUserId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<UserRequest> getAll() {
        ArrayList<UserRequest> list=new ArrayList<>();
        UserService userService=new UserService();
        try{
            preparedStatement=conn.prepareStatement("select * from userrequest");
            ResultSet rs=preparedStatement.executeQuery();
            while(rs.next()){
                list.add(new UserRequest(rs.getInt("idRequest"),rs.getString("typeRequest"),rs.getString("etat"),rs.getDate("dateRequest"),userService.getById(rs.getInt("idUser"))));
            }
            rs.close();
            preparedStatement.close();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return list;
    }

    @Override
    public void update(UserRequest activationRequest) {
        try{
            preparedStatement=conn.prepareStatement("update userrequest set typerequest=?,etat=?,idUser=? where idRequest=?");
            preparedStatement.setString(1,activationRequest.getTypeRequest());
            preparedStatement.setString(2,activationRequest.getEtat());
            preparedStatement.setInt(3,activationRequest.getUser().getUserId());
            preparedStatement.setInt(4,activationRequest.getIdRequest() );
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void delete(int t) {
        try {
            preparedStatement= conn.prepareStatement("delete from userrequest where idRequest=?");
            preparedStatement.setInt(1,t);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public UserRequest getById(int id) {
        return getAll().stream().filter(e->e.getIdRequest()==id).findFirst().orElse(null);
    }
    public List<UserRequest> getPending(){
        return getAll().stream().filter(e->e.getEtat().equals("pending")).collect(Collectors.toList());
    }
    public List<UserRequest> getAccepeted(){
        return getAll().stream().filter(e->e.getEtat().equals("accepted")).collect(Collectors.toList());
    }
    public List<UserRequest> getRejected(){
        return getAll().stream().filter(e->e.getEtat().equals("rejected")).collect(Collectors.toList());
    }
    public List<UserRequest> getActivationRequests(){
        return getAll().stream().filter(e->e.getTypeRequest().equals("activation")).collect(Collectors.toList());
    }
    public boolean pendingActivationRequestExistPerUsername(String username){
        return getPending().stream().anyMatch(e->e.getUser().getUsername().equals(username));
    }
    public UserRequest getByUsername(String username){
        return getPending().stream().filter(e->e.getUser().getUsername().equals(username)).findFirst().orElse(null);
    }
    public List<UserRequest> sortBydate(List<UserRequest> list){
        return list.stream().sorted((a,b)->{
            return b.getDateRequest().compareTo(a.getDateRequest());
        }).collect(Collectors.toList());
    }
    public List<UserRequest> sortByUsername(List<UserRequest> list){
        return list.stream().sorted((a,b)->{
            return a.getUser().getUsername().compareTo(b.getUser().getUsername());
        }).collect(Collectors.toList());
    }
    public List<UserRequest> getUsernameReset(){
        return getAll().stream().filter(e->e.getTypeRequest().equals("usernameReset")).collect(Collectors.toList());
    }
    public List<UserRequest> getPasswordReset(){
        return getAll().stream().filter(e->e.getTypeRequest().equals("passwordReset")).collect(Collectors.toList());
    }
    public List<UserRequest> getArtistRequest(){
        return getAll().stream().filter(e->e.getTypeRequest().equals("artist")).collect(Collectors.toList());
    }
    public boolean pendingArtistRequestByUsername(String username){
        return getArtistRequest().stream().anyMatch(e->e.getUser().getUsername().equals(username) && e.getEtat().equals("pending") );
    }
    public List<UserRequest> getUsernameResetByusername(String username){
        return getUsernameReset().stream().filter(e->e.getUser().getUsername().equals(username)).sorted((a,b)->{
            return b.getDateRequest().compareTo(a.getDateRequest());
        }).collect(Collectors.toList());
    }
    public List<UserRequest> getPasswordResetByUsername(String username){
        return getPasswordReset().stream().filter(e->e.getUser().getUsername().equals(username)).collect(Collectors.toList());
    }
}
