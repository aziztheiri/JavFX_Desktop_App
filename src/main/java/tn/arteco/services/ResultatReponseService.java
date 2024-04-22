package tn.arteco.services;

import tn.arteco.iservices.Iservice;
import tn.arteco.models.Reponse;
import tn.arteco.models.ResultatReponse;
import tn.arteco.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.prefs.BackingStoreException;
import java.util.stream.Collectors;

public class ResultatReponseService implements Iservice<ResultatReponse>{

        private Connection con;

        public ResultatReponseService() {
                con = MyDataBase.getInstance().getConnection();
        }

        public void add(ResultatReponse R){
                R.prefs.putBoolean(R.getIdReponse(),R.isEtat());
        }
        public void update(ResultatReponse R){

        }
        public void delete(int id){
                ResultatReponse R = new ResultatReponse();
                R.prefs.remove(String.valueOf(id));
        }
        public ArrayList<ResultatReponse> getAll(){
                ResultatReponse R = new ResultatReponse();
                ArrayList<ResultatReponse> RR = new ArrayList<>();
                try {
                        String[] S = R.prefs.keys();
                        for (String S1: S){
                                RR.add(new ResultatReponse(R.prefs.getBoolean(S1,false),S1));
                }}catch (BackingStoreException e){
                        throw new RuntimeException(e);
                }
                return RR;
        }
        public ResultatReponse getById(int id){
                ResultatReponse R = new ResultatReponse();
                ResultatReponse R1 = new ResultatReponse(R.prefs.getBoolean(String.valueOf(id),false),String.valueOf(id));
                return R1;
        }


        public List<ResultatReponse> Sort(List<ResultatReponse> RR){
                return RR.stream().sorted((R1,R2)->
                        (R1.isEtat() && R2.isEtat()) ? 1 : 0
                ).collect(Collectors.toList());
        }

}
