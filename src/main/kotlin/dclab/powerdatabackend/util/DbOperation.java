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
