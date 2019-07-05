package dclab.powerdatabackend.util;

import java.sql.*;
import java.util.*;

public class ExcelOp {
    public static int insertAll(String tableName, List<Map<String, Object>> datas, Connection con) throws SQLException {
        int affectRowCount = -1;

        PreparedStatement preparedStatement = null;
        try {

            Map<String, Object> valueMap = datas.get(0);
            /**获取数据库插入的Map的键值对的值**/
            Set<String> keySet = valueMap.keySet();
            Iterator<String> iterator = keySet.iterator();
            /**要插入的字段sql，其实就是用key拼起来的**/
            StringBuilder columnSql = new StringBuilder();
            /**要插入的字段值，其实就是？**/
            StringBuilder unknownMarkSql = new StringBuilder();
            Object[] keys = new Object[valueMap.size()];
            int i = 0;
            while (iterator.hasNext()) {
                String key = iterator.next();
                keys[i] = key;
                columnSql.append(i == 0 ? "" : ",");
                columnSql.append(key);

                unknownMarkSql.append(i == 0 ? "" : ",");
                unknownMarkSql.append("?");
                i++;
            }
            /**开始拼插入的sql语句**/
            StringBuilder sql = new StringBuilder();
            sql.append("INSERT INTO ");
            sql.append(tableName);
            sql.append(" (");
            sql.append(columnSql);
            sql.append(" )  VALUES (");
            sql.append(unknownMarkSql);
            sql.append(" )");

            /**执行SQL预编译**/
            preparedStatement = con.prepareStatement(sql.toString());
            /**设置不自动提交，以便于在出现异常的时候数据库回滚**/
            con.setAutoCommit(false);
            System.out.println(sql.toString());
            for (int j = 0; j < datas.size(); j++) {
                for (int k = 0; k < keys.length; k++) {
                    preparedStatement.setObject(k + 1, datas.get(j).get(keys[k]));
                }
                preparedStatement.addBatch();
            }
            int[] arr = preparedStatement.executeBatch();
            con.commit();
            affectRowCount = arr.length;
            System.out.println("成功了插入了" + affectRowCount + "行");
            System.out.println();
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return affectRowCount;

    }

    public static List<Map<String, String>> selectMetadata(String tableName, Connection con){
        List<Map<String,String>> res = new ArrayList<>();
        try
        {

            Connection conn = con;

            String query = "SELECT DISTINCT factory,device,line FROM datas";
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(query);
            while (rs.next())
            {
                Map<String, String> map = new HashMap<>();
                map.put("factory",rs.getString("factory"));
                map.put("line", rs.getString("line"));
                map.put("device", rs.getString("device"));
                res.add(map);


            }
            st.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return res;
    }

    public static Map<String, String> dataStructure(){
        Map<String, String> map = new HashMap<>();
        map.put("时间","timestamp");
        map.put("A相电压","APhaseElectricTension");
        map.put("B相电压","BPhaseElectricTension");
        map.put("C相电压","CPhaseElectricTension");
        map.put("AB线电压","ABLineElectricTension");
        map.put("BC线电压","BCLineElectricTension");
        map.put("CA线电压","CALineElectricTension");
        map.put("A相电流","APhaseElectricCurrent");
        map.put("B相电流","BPhaseElectricCurrent");
        map.put("C相电流","CPhaseElectricCurrent");
        map.put("零序电流,","ZeroSequenceCurrent");
        map.put("A相有功功率","APhaseActivePower");
        map.put("B相有功功率","BPhaseActivePower");
        map.put("C相有功功率","CPhaseActivePower");
        map.put("三相总有功功率","ThreePhaseTotalActivePower");
        map.put("A相无功功率","APhaseReactivePower");
        map.put("B相无功功率","BPhaseReactivePower");
        map.put("C相无功功率","CPhaseReactivePower");
        map.put("三相总无功功率","ThreePhaseTotalReactivePower");
        map.put("A相视在功率","APhaseAtPower");
        map.put("B相视在功率","BPhaseAtPower");
        map.put("C相视在功率","CPhaseAtPower");
        map.put("三相总视在功率","ThreePhaseTotalAtPower");
        map.put("A相功率因数","APhasePowerFactor");
        map.put("B相功率因数","BPhasePowerFactor");
        map.put("C相功率因数","CPhasePowerFactor");
        map.put("平均功率因数","AveragePowerFactor");
        map.put("频率","Frequency");
        map.put("正向有功电度","ForwardActive");
        map.put("反向有功电度","ReverseActive");
        map.put("正向无功电度","ForwardReactiveWattage");
        map.put("反向无功电度","ReverseReactiveWattage");
        map.put("电压不平衡度","VoltageUnbalance");
        map.put("电流不平衡度","ElectricCurrentUnbalance");
        map.put("A相电压谐波总失真","APhaseVoltageHarmonicTotalDistortion");
        map.put("B相电压谐波总失真","BPhaseVoltageHarmonicTotalDistortion");
        map.put("C相电压谐波总失真","CPhaseVoltageHarmonicTotalDistortion");
        map.put("A相电流谐波总失真","TotalHarmonicDistortionOfAPhaseCurrent");
        map.put("B相电流谐波总失真","TotalHarmonicDistortionOfBPhaseCurrent");
        map.put("C相电流谐波总失真","TotalHarmonicDistortionOfCPhaseCurrent");
        map.put("正向有功最大需量","MaximumPositiveActiveDemand");
        map.put("反向有功最大需量","MaximumReverseActiveDemand");
        map.put("正向无功最大需量","MaximumForwardReactivePowerDemand");
        map.put("反向无功最大需量","MaximumReverseReactivePowerDemand");
//        map.put("总有功功率实时需量","RealtimeDemandForTotalActivePower");
//        map.put("总无功功率实时需量","TotalReactivePowerRealtimeDemand");
        return map;
    }

    public static Map<String, String> valueToKey(){
        Map<String, String> map = new HashMap<>();
        map.put("timestamp","时间");
        map.put("APhaseElectricTension","A相电压");
        map.put("BPhaseElectricTension","B相电压");
        map.put("CPhaseElectricTension","C相电压");
        map.put("ABLineElectricTension","AB线电压");
        map.put("BCLineElectricTension","BC线电压");
        map.put("CALineElectricTension","CA线电压");
        map.put("APhaseElectricCurrent","A相电流");
        map.put("BPhaseElectricCurrent","B相电流");
        map.put("CPhaseElectricCurrent","C相电流");
        map.put("ZeroSequenceCurrent","零序电流,");
        map.put("APhaseActivePower","A相有功功率");
        map.put("BPhaseActivePower","B相有功功率");
        map.put("CPhaseActivePower","C相有功功率");
        map.put("ThreePhaseTotalActivePower","三相总有功功率");
        map.put("APhaseReactivePower","A相无功功率");
        map.put("BPhaseReactivePower","B相无功功率");
        map.put("CPhaseReactivePower","C相无功功率");
        map.put("ThreePhaseTotalReactivePower","三相总无功功率");
        map.put("APhaseAtPower","A相视在功率");
        map.put("BPhaseAtPower","B相视在功率");
        map.put("CPhaseAtPower","C相视在功率");
        map.put("ThreePhaseTotalAtPower","三相总视在功率");
        map.put("APhasePowerFactor","A相功率因数");
        map.put("BPhasePowerFactor","B相功率因数");
        map.put("CPhasePowerFactor","C相功率因数");
        map.put("AveragePowerFactor","平均功率因数");
        map.put("Frequency","频率");
        map.put("ForwardActive","正向有功电度");
        map.put("ReverseActive","反向有功电度");
        map.put("ForwardReactiveWattage","正向无功电度");
        map.put("ReverseReactiveWattage","反向无功电度");
        map.put("VoltageUnbalance","电压不平衡度");
        map.put("ElectricCurrentUnbalance","电流不平衡度");
        map.put("APhaseVoltageHarmonicTotalDistortion","A相电压谐波总失真");
        map.put("BPhaseVoltageHarmonicTotalDistortion","B相电压谐波总失真");
        map.put("CPhaseVoltageHarmonicTotalDistortion","C相电压谐波总失真");
        map.put("TotalHarmonicDistortionOfAPhaseCurrent","A相电流谐波总失真");
        map.put("TotalHarmonicDistortionOfBPhaseCurrent","B相电流谐波总失真");
        map.put("TotalHarmonicDistortionOfCPhaseCurrent","C相电流谐波总失真");
        map.put("MaximumPositiveActiveDemand","正向有功最大需量");
        map.put("MaximumReverseActiveDemand","反向有功最大需量");
        map.put("MaximumForwardReactivePowerDemand","正向无功最大需量");
        map.put("MaximumReverseReactivePowerDemand","反向无功最大需量");

        return map;
    }

}
