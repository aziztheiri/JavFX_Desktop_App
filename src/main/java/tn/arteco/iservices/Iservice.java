package tn.arteco.iservices;

import java.util.ArrayList;
import java.util.Optional;

public interface Iservice<T>{
     void add(T t);
     ArrayList<T> getAll();
     void update(T t);
     void delete(int t);

     T getById(int id);
}
