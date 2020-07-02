package dclab.powerdatabackend.util;

import dclab.powerdatabackend.model.Rtdata;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbOperation {

    public Map<String,Object> getvalueByfactory(String tableName,String startTime, String endTime,  String measurePoint, ArrayList<Integer> factory, Connection con) throws SQLException {
        PreparedStatement preparedStatement = null;
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT customerid, regdate, metercolumn, sum(culunmvalue) as allvalue FROM ");
        sql.append(tableName);
        sql.append(" where regdate >= DATE(?) AND regdate <= DATE(?) and metercolumn = ? and ( ");

        for(int i = 0; i< factory.size(); i++){
            sql.append(" customerid = ? ");
            if(i == factory.size() - 1)continue;
            sql.append("or");
        }
        sql.append(")");
        sql.append(" GROUP BY  customerid, regdate, metercolumn order by customerid, regdate");
        List<Map<String,Object>> re = new ArrayList<>();
        Map<String, ArrayList<ArrayList<Object>>> linedata = new HashMap<>();
        ArrayList<String> allPlace = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();
        try {
            System.out.print(sql.toString());
            preparedStatement = con.prepareStatement(sql.toString());
            preparedStatement.setString(1,startTime);
            preparedStatement.setString(2,endTime);
            preparedStatement.setString(3,measurePoint);

            for(int i = 0; i< factory.size(); i++){
                preparedStatement.setInt(4 + i,factory.get(i));
            }
            ResultSet rs = preparedStatement.executeQuery();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while(rs.next()){
                String timevalue = df.format(rs.getTimestamp("regdate"));
                String placevalue = rs.getString("customerid");
                Double value = rs.getDouble("allvalue");
                Map<String, Object> m = new HashMap<>();
                m.put("date",timevalue);
                m.put("place",placevalue);
                m.put("value",value);
                re.add(m);
                if(!allPlace.contains(placevalue)){
                    allPlace.add(placevalue);
                }
                if(linedata.containsKey(placevalue)){
                    ArrayList<Object> tmp = new ArrayList<>();
                    tmp.add(timevalue);
                    tmp.add(value);
                    linedata.get(placevalue).add(tmp);
                }else{
                    ArrayList<ArrayList<Object>>t = new ArrayList<>();
                    ArrayList<Object> tmp = new ArrayList<>();
                    tmp.add(timevalue);
                    tmp.add(value);
                    t.add(tmp);
                    linedata.put(placevalue, t);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
        result.put("tableData", re);
        result.put("lineData", linedata);
        result.put("allPlace", allPlace);
        return result;
    }

    public Map<String,Object> getvalueBydevice(String tableName,String startTime, String endTime, ArrayList<String> device, String measurePoint, int factory, Connection con) throws SQLException {
        PreparedStatement preparedStatement = null;
        StringBuilder sql = new StringBuilder();
        sql.append("Select distinct regdate, meterid, culunmvalue from ");
        sql.append(tableName);
        sql.append(" where regdate >= DATE(?) and regdate <= DATE(?) and metercolumn = ? and customerid = ? and (");
        for(int i = 0; i< device.size(); i++){
            sql.append(" meterid = ? ");
            if(i == device.size() - 1)continue;
            sql.append("or");
        }
        sql.append(") order by meterid,regdate ");
        List<Map<String,Object>> re = new ArrayList<>();
        Map<String, ArrayList<ArrayList<Object>>> linedata = new HashMap<>();
        ArrayList<String> allPlace = new ArrayList<>();
        Map<String, Object> result = new HashMap<>();
        try {

            preparedStatement = con.prepareStatement(sql.toString());
            preparedStatement.setString(1,startTime);
            preparedStatement.setString(2,endTime);
            preparedStatement.setString(3,measurePoint);
            preparedStatement.setInt(4,factory);
            for(int i = 0; i< device.size(); i++){
                preparedStatement.setString(5 + i,device.get(i));
            }
            ResultSet rs = preparedStatement.executeQuery();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while(rs.next()){
                String timevalue = df.format(rs.getTimestamp("regdate"));
                String placevalue = rs.getString("meterid");
                Double value = rs.getDouble("culunmvalue");

                Map<String, Object> m = new HashMap<>();
                m.put("date",timevalue);
                m.put("place",factory + "/" + placevalue);
                m.put("value",value);
                re.add(m);
                if(!allPlace.contains(placevalue)){
                    allPlace.add(placevalue);
                }
                if(linedata.containsKey(placevalue)){
                    ArrayList<Object> tmp1 = new ArrayList<>();
                    tmp1.add(timevalue);
                    tmp1.add(value);
                    linedata.get(placevalue).add(tmp1);

                }else{
                    ArrayList<ArrayList<Object>>t = new ArrayList<>();
                    ArrayList<Object> tmp = new ArrayList<>();
                    tmp.add(timevalue);
                    tmp.add(value);
                    t.add(tmp);
                    linedata.put(placevalue, t);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
        result.put("tableData", re);
        result.put("lineData", linedata);
        result.put("allPlace", allPlace);
        return result;
    }

    public List<Map<String,Object>> getAllBydevice(String tableName,String startTime, String endTime, String device, Connection con) throws SQLException {
        PreparedStatement preparedStatement = null;
        StringBuilder sql = new StringBuilder();
        sql.append("Select * from ");
        sql.append(tableName);
        sql.append(" where regdate >= DATE(?) and regdate <= DATE(?) and meterid = ?");
        List<Map<String,Object>> re = new ArrayList<>();

        try {
            preparedStatement = con.prepareStatement(sql.toString());
            preparedStatement.setString(1,startTime);
            preparedStatement.setString(2,endTime);
            preparedStatement.setString(3,device);
            ResultSet rs = preparedStatement.executeQuery();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while(rs.next()){
                Map<String, Object> m = new HashMap<>();
                m.put("date",df.format(rs.getTimestamp("regdate")));
                m.put("measurePoint",rs.getString("metercolumn"));
                m.put("value",rs.getDouble("culunmvalue"));
                re.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return re;
    }
    //用户层面获取测点对应的值
    public List<Map<String,Object>> getAllByFactory(String tableName,String startTime, String endTime, int customerid, Connection con) throws SQLException {
        PreparedStatement preparedStatement = null;

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT customerid, regdate, metercolumn, sum(culunmvalue) as allvalue FROM ");
        sql.append(tableName);
        sql.append(" where regdate >= DATE(?) AND regdate <= DATE(?) and customerid = ? GROUP BY  customerid, regdate, metercolumn");
        List<Map<String,Object>> re = new ArrayList<>();

        try {
            preparedStatement = con.prepareStatement(sql.toString());
            preparedStatement.setString(1,startTime);
            preparedStatement.setString(2,endTime);
            preparedStatement.setInt(3,customerid);
            ResultSet rs = preparedStatement.executeQuery();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            while(rs.next()){
                Map<String, Object> m = new HashMap<>();
                m.put("date",df.format(rs.getTimestamp("regdate")));
                m.put("measurePoint",rs.getString("metercolumn"));
                m.put("value",rs.getDouble("allvalue"));
                re.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return re;
    }

    public List<String> getDevice(String tableName, Connection con) throws SQLException {
        PreparedStatement preparedStatement = null;
        StringBuilder sql = new StringBuilder();
        sql.append("Select distinct meterid from ");
        sql.append(tableName);
        List<String> re = new ArrayList<>();

        try {
            preparedStatement = con.prepareStatement(sql.toString());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){

                re.add(rs.getString("meterid"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return re;
    }
    public List<Integer> getFactoryOrDevice(String tableName, Connection con) throws SQLException {
        PreparedStatement preparedStatement = null;
        StringBuilder sql = new StringBuilder();
        sql.append("Select distinct customerid from ");
        sql.append(tableName);
        List<Integer> re = new ArrayList<>();

        try {
            preparedStatement = con.prepareStatement(sql.toString());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){

                re.add(rs.getInt("meterid"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return re;
    }
    public List<String> getMeasurePoint(String tableName, Connection con) throws SQLException {
        PreparedStatement preparedStatement = null;
        StringBuilder sql = new StringBuilder();
        sql.append("Select distinct metercolumn from ");
        sql.append(tableName);
        List<String> re = new ArrayList<>();

        try {
            preparedStatement = con.prepareStatement(sql.toString());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){

                re.add(rs.getString("metercolumn"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return re;
    }

    public List<String> getOlapResult(String tableName,String hashname, Connection con) throws SQLException {
        PreparedStatement preparedStatement = null;
        StringBuilder sql = new StringBuilder();
        sql.append("Select json from ");
        sql.append(tableName);
        sql.append(" where hashname = '" + hashname + "'");
        List<String> re = new ArrayList<>();

        try {
            preparedStatement = con.prepareStatement(sql.toString());

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){

                re.add(rs.getString("json"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return re;
    }
}
