package dclab.powerdatabackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONType;
import com.google.common.base.Joiner;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.*;
import dclab.powerdatabackend.util.DbOperation;
@CrossOrigin
@RestController
@RequestMapping("/api")
public class OlapController {
    public MediaType jsonType = MediaType.parse("application/json; charset=utf-8");
    @Autowired
    private DataSource dataSource;
    public ArrayList<String[]> readCsv(String path) {
        ArrayList<String[]> csvFileList = new ArrayList<String[]>();
        String[] strs = null;
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(new File(path)));
// CSVReader csvReader = new CSVReader(new InputStreamReader(in,"GBK"));
            CSVReader csvReader = new CSVReader(new InputStreamReader(in, "GBK"), CSVParser.DEFAULT_SEPARATOR,
                    CSVParser.DEFAULT_QUOTE_CHARACTER, CSVParser.DEFAULT_ESCAPE_CHARACTER, 0);

            while ((strs = csvReader.readNext()) != null) {
//                System.out.println(Arrays.deepToString(strs));
                Arrays.deepToString(strs);
                csvFileList.add(strs);
            }
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return csvFileList;
    }


    @RequestMapping(value = "/qiepian", method = RequestMethod.POST)
    @ResponseBody
    public String olapSlice(@RequestBody Map<String, Object> dt) throws IOException, NoSuchAlgorithmException, SQLException {
        ArrayList<ArrayList<String>> p1 = (ArrayList<ArrayList<String>>)dt.get("p1");
        ArrayList<String> device =  new ArrayList<>();
        ArrayList<Integer> factory = new ArrayList<>();
        for(ArrayList<String> a : p1){
            device.add(a.get(2));
            if(!factory.contains(a.get(0))){
                factory.add(Integer.parseInt(a.get(0)));
            }
        }

        ArrayList<String> timeRange = (ArrayList<String>)dt.get("p3");
        ArrayList<String> measurePoint = (ArrayList<String>)dt.get("p2");
        for (int i = 0; i< timeRange.size();i++){
            timeRange.set(i,timeRange.get(i).substring(0,10));
        }
        String p4 = (String)dt.get("p4");
        String p5 = (String)dt.get("p5");

        ArrayList<String> group = new ArrayList<>();
        String agg = "";
        if(p4.equals("用户") || p4.equals("天")){
            group.add("date");
        }else if(p4.equals("设备")){

            group.add("device");
        }else if(p4.equals("设备+天")){

            group.add("device");
            group.add("date");
        }
        if(p5.equals("求和")){
            agg = "sum";
        }else if(p5.equals("求平均")){
            agg = "mean";
        }

        String allString =  Joiner.on("").join(factory) + String.join("", timeRange) + String.join("", device) + String.join("", measurePoint) + String.join("", group) +agg;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(allString.getBytes());
        String stringHash = (DatatypeConverter.printHexBinary(md.digest())).toLowerCase();
        List<String> res = new DbOperation().getOlapResult("olapresult", stringHash, dataSource.getConnection());

        if(res.size() != 0){
            return res.get(0);
        }else{

            if((int)dt.get("count") != 0)return "";
            OkHttpClient client =new OkHttpClient();
            JSONObject jsonObject =new JSONObject();
            jsonObject.put("factory", factory);
            jsonObject.put("timeRange", timeRange);
            jsonObject.put("device", device);
            jsonObject.put("measurePoint", measurePoint);
            jsonObject.put("group", group);
            jsonObject.put("agg", agg);
            jsonObject.put("hashname",stringHash);
            com.squareup.okhttp.RequestBody requestBody = com.squareup.okhttp.RequestBody.create(jsonType, jsonObject.toJSONString());
            String apiURL = "http://localhost:5000/algorithm/olapslice";
            Request request = new Request.Builder().url(apiURL).addHeader("Content-Type", "application/json;charset=utf-8").post(requestBody).build();
            client.newCall(request).execute();
            return "";
        }

    }



    @RequestMapping(value = "/zuanqu", method = RequestMethod.POST)
    @ResponseBody
    public String olapDrill(@RequestBody Map<String, Object> dt) throws IOException, NoSuchAlgorithmException, SQLException {
        ArrayList<ArrayList<String>> p1 = (ArrayList<ArrayList<String>>)dt.get("p1");
        ArrayList<String> device =  new ArrayList<>();
        ArrayList<Integer> factory = new ArrayList<>();
        for(ArrayList<String> a : p1){
            device.add(a.get(2));
            if(!factory.contains(a.get(0))){
                factory.add(Integer.parseInt(a.get(0)));
            }
        }

        ArrayList<String> timeRange = (ArrayList<String>)dt.get("p3");
        ArrayList<String> measurePoint = (ArrayList<String>)dt.get("p2");

        for (int i = 0; i< timeRange.size();i++){
            timeRange.set(i,timeRange.get(i).substring(0,10));
        }
        String p4 = (String)dt.get("p4");
        String p5 = (String)dt.get("p5");

        int timeMode = 0;
        int zoneMode = 0;
        if(p4.equals("分钟")){
            timeMode = 0;
        }else if(p4.equals( "天")){
            timeMode = 1;
        }else if(p4.equals("月")){
            timeMode = 2;
        }
        if(p5.equals("设备")){
            zoneMode = 0;
        }else if(p5.equals("用户")){
            zoneMode = 1;
        }
        List<Object> result = new ArrayList<>();
        List<Map<String, Object>> content = new ArrayList<>();
        List<Map<String,Object>> header = new ArrayList<>();

        String allString =  Joiner.on("").join(factory) + String.join("", timeRange) + String.join("", device) + String.join("", measurePoint) +  String.valueOf(timeMode) +String.valueOf(zoneMode);
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(allString.getBytes());
        String stringHash = (DatatypeConverter.printHexBinary(md.digest())).toLowerCase();

        List<String> res = new DbOperation().getOlapResult("olapresult", stringHash, dataSource.getConnection());

        if(res.size() != 0){
            return res.get(0);
        }else{
            if((int)dt.get("count") != 0)return "";
            OkHttpClient client =new OkHttpClient();
            JSONObject jsonObject =new JSONObject();
            jsonObject.put("factory", factory);
            jsonObject.put("timeRange", timeRange);
            jsonObject.put("device", device);
            jsonObject.put("measurePoint", measurePoint);
            jsonObject.put("timeMode", timeMode);
            jsonObject.put("zoneMode", zoneMode);
            jsonObject.put("hashname",stringHash);
            com.squareup.okhttp.RequestBody requestBody = com.squareup.okhttp.RequestBody.create(jsonType, jsonObject.toJSONString());
            String apiURL = "http://localhost:5000/algorithm/olapdrill";
//        System.out.println(apiURL);
            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder().url(apiURL).addHeader("Content-Type", "application/json;charset=utf-8").post(requestBody).build();
            client.newCall(request).execute();
            return "";
        }

    }

    @RequestMapping(value = "/xuanzhuan", method = RequestMethod.POST)
    @ResponseBody
    public String olapRotate(@RequestBody Map<String, Object> dt) throws IOException, NoSuchAlgorithmException, SQLException {
        ArrayList<ArrayList<String>> p1 = (ArrayList<ArrayList<String>>)dt.get("p1");
        ArrayList<String> device =  new ArrayList<>();
        ArrayList<Integer> factory = new ArrayList<>();
        for(ArrayList<String> a : p1){
            device.add(a.get(2));
            if(!factory.contains(a.get(0))){
                factory.add(Integer.parseInt(a.get(0)));
            }
        }

        ArrayList<String> timeRange = (ArrayList<String>)dt.get("p3");
        ArrayList<String> measurePoint = (ArrayList<String>)dt.get("p2");
        for (int i = 0; i< timeRange.size();i++){
            timeRange.set(i,timeRange.get(i).substring(0,10));
        }
        String p4 = (String)dt.get("p4");
        String p5 = (String)dt.get("p5");

        ArrayList<String> group = new ArrayList<>();
        
        String agg = "";
        if(p4.equals("用户") || p4.equals("天")){
            group.add("date");
        }else if(p4.equals("设备")){
            group.add("device");
        }else if(p4.equals("设备+天")){
            group.add("device");
            group.add("date");
        }
        if(p5.equals("求和")){
            agg = "sum";
        }else if(p5.equals("求平均")){
            agg = "mean";
        }
        String os = System.getProperty("os.name");

        String allString =  Joiner.on("").join(factory) + String.join("", timeRange) + String.join("", device) + String.join("", measurePoint) + String.join("", group) +agg + "rotate";
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(allString.getBytes());
        String stringHash = (DatatypeConverter.printHexBinary(md.digest())).toLowerCase();
        List<String> res = new DbOperation().getOlapResult("olapresult", stringHash, dataSource.getConnection());

        if(res.size() != 0){
            return res.get(0);
        }else{
            if((int)dt.get("count") != 0)return "";
            OkHttpClient client =new OkHttpClient();
            JSONObject jsonObject =new JSONObject();
            jsonObject.put("factory", factory);
            jsonObject.put("timeRange", timeRange);
            jsonObject.put("device", device);
            jsonObject.put("measurePoint", measurePoint);
            jsonObject.put("group", group);
            jsonObject.put("agg", agg);
            jsonObject.put("hashname",stringHash);
            com.squareup.okhttp.RequestBody requestBody = com.squareup.okhttp.RequestBody.create(jsonType, jsonObject.toJSONString());
            String apiURL = "http://localhost:5000/algorithm/olaprotate";
            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder().url(apiURL).addHeader("Content-Type", "application/json;charset=utf-8").post(requestBody).build();
            client.newCall(request).execute();
            return "";
        }

    }

}
