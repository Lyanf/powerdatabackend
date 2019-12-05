package dclab.powerdatabackend.util;

import dclab.powerdatabackend.model.Rtdata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbOperation {
    public List<Map<String,Object>> getAll(String tableName,String startTime, String endTime, Connection con) throws SQLException {
        PreparedStatement preparedStatement = null;
        StringBuilder sql = new StringBuilder();
        sql.append("Select * from ");
        sql.append(tableName);
        sql.append(" where regdate >= DATE(?) and regdate <= DATE(?)");
        List<Map<String,Object>> re = new ArrayList<>();

        try {
            preparedStatement = con.prepareStatement(sql.toString());
            preparedStatement.setString(1,startTime);
            preparedStatement.setString(2,endTime);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Map<String, Object> m = new HashMap<>();
                m.put("date",rs.getTimestamp("regdate"));
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
}
