package dclab.powerdatabackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONType;
import com.opencsv.CSVParser;
import com.opencsv.CSVReader;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.MediaType;
import org.springframework.web.bind.annotation.*;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class OlapController {
    public MediaType jsonType = MediaType.parse("application/json; charset=utf-8");
    public String slicecsv = "D:\\Project\\dclab\\电力项目\\Powerdata\\poweralgorithm\\slice.csv";
    public String drillcsv = "D:\\Project\\dclab\\电力项目\\Powerdata\\poweralgorithm\\drill.csv";
    public String rotatecsv = "D:\\Project\\dclab\\电力项目\\Powerdata\\poweralgorithm\\rotate.csv";
//    public String slicecsv = "/home/pcsjtu/power/poweralgorithm/slice.csv";
//    public String drillcsv = "/home/pcsjtu/power/poweralgorithm/drill.csv";
//    public String rotatecsv = "/home/pcsjtu/power/poweralgorithm/rotate.csv";
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
    public String olapSlice(@RequestBody Map<String, Object> dt) throws IOException {
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
        if(p4 == "用户" || p4 == "天"){
            group.add("date");
        }else if(p4 == "设备"){
            group.add("device");
        }else if(p4 == "设备+天"){
            group.add("device");
            group.add("date");
        }
        if(p5 == "求和"){
            agg = "sum";
        }else if(p5 == "求平均"){
            agg = "mean";
        }
        File file = new File(slicecsv);
        List<Object> result = new ArrayList<>();
        List<Map<String, Object>> content = new ArrayList<>();
        List<Map<String,Object>> header = new ArrayList<>();
        if(file.exists()){
            ArrayList<String[]> re = readCsv(slicecsv);
            boolean flag = false;

            for (String[] s : re){
                Map<String,Object> m = new HashMap<>();

                for (int i = 0; i < s.length; i++){
                    if(!flag){
                        Map<String,Object> h = new HashMap<>();
                        h.put("prop",s[i]);
                        h.put("label",s[i]);
                        header.add(h);
                    }else{
                        m.put((String)header.get(i).get("prop"),s[i]);
                    }

                }
                if(flag)content.add(m);

                flag = true;
            }
            if(file.delete()){
                System.out.println(file.getName() + " 文件已被删除！");
            }else{
                System.out.println("文件删除失败！");
            }
        }else{
            OkHttpClient client =new OkHttpClient();
            JSONObject jsonObject =new JSONObject();
            jsonObject.put("factory", factory);
            jsonObject.put("timeRange", timeRange);
            jsonObject.put("device", device);
            jsonObject.put("measurePoint", measurePoint);
            jsonObject.put("group", group);
            jsonObject.put("agg", agg);
            com.squareup.okhttp.RequestBody requestBody = com.squareup.okhttp.RequestBody.create(jsonType, jsonObject.toJSONString());
            String apiURL = "http://localhost:5000/algorithm/olapslice";
            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder().url(apiURL).addHeader("Content-Type", "application/json;charset=utf-8").post(requestBody).build();
            client.newCall(request).execute();
            return "";
        }

        Map<String, Object> tmp = new HashMap<>();
        tmp.put("header",header);
        tmp.put("content",content);
        result.add(tmp);
        return JSONObject.toJSONString(result);
    }
    @RequestMapping(value = "/zuanqu", method = RequestMethod.POST)
    @ResponseBody
    public String olapDrill(@RequestBody Map<String, Object> dt) throws IOException {
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
        if(p4 == "分钟"){
            timeMode = 0;
        }else if(p4 == "天"){
            timeMode = 1;
        }else if(p4 == "月"){
            timeMode = 2;
        }
        if(p5 == "设备"){
            zoneMode = 0;
        }else if(p5 == "用户"){
            zoneMode = 1;
        }
        List<Object> result = new ArrayList<>();
        List<Map<String, Object>> content = new ArrayList<>();
        List<Map<String,Object>> header = new ArrayList<>();
        File file = new File(drillcsv);
        if(file.exists()){
            ArrayList<String[]> re = readCsv(drillcsv);
            boolean flag = false;

            for (String[] s : re){
                Map<String,Object> m = new HashMap<>();
                for (int i = 0; i < s.length; i++){
                    if(!flag){
                        Map<String,Object> h = new HashMap<>();
                        h.put("prop",s[i]);
                        h.put("label",s[i]);
                        header.add(h);
                    }else{
                        m.put((String)header.get(i).get("prop"),s[i]);
                    }

                }
                if(flag)content.add(m);
                flag = true;
            }
            if(file.delete()){
                System.out.println(file.getName() + " 文件已被删除！");
            }else{
                System.out.println("文件删除失败！");
            }
        }else{
            OkHttpClient client =new OkHttpClient();
            JSONObject jsonObject =new JSONObject();
            jsonObject.put("factory", factory);
            jsonObject.put("timeRange", timeRange);
            jsonObject.put("device", device);
            jsonObject.put("measurePoint", measurePoint);
            jsonObject.put("timeMode", timeMode);
            jsonObject.put("zoneMode", zoneMode);
            com.squareup.okhttp.RequestBody requestBody = com.squareup.okhttp.RequestBody.create(jsonType, jsonObject.toJSONString());
            String apiURL = "http://localhost:5000/algorithm/olapdrill";
//        System.out.println(apiURL);
            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder().url(apiURL).addHeader("Content-Type", "application/json;charset=utf-8").post(requestBody).build();
            client.newCall(request).execute();
            return "";
        }
        Map<String, Object> tmp = new HashMap<>();
        tmp.put("header",header);
        tmp.put("content",content);
        result.add(tmp);

        return JSONObject.toJSONString(result);
    }

    @RequestMapping(value = "/xuanzhuan", method = RequestMethod.POST)
    @ResponseBody
    public String olapRotate(@RequestBody Map<String, Object> dt) throws IOException {
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
        if(p4 == "用户" || p4 == "天"){
            group.add("date");
        }else if(p4 == "设备"){
            group.add("device");
        }else if(p4 == "设备+天"){
            group.add("device");
            group.add("date");
        }
        if(p5 == "求和"){
            agg = "sum";
        }else if(p5 == "求平均"){
            agg = "mean";
        }
        File file = new File(rotatecsv);
        List<Object> result = new ArrayList<>();
        List<Map<String, Object>> content = new ArrayList<>();
        List<Map<String,Object>> header = new ArrayList<>();
        if(file.exists()){
            ArrayList<String[]> re = readCsv(rotatecsv);
            boolean flag = false;

            for (String[] s : re){
                Map<String,Object> m = new HashMap<>();

                for (int i = 0; i < s.length; i++){
                    if(!flag){
                        Map<String,Object> h = new HashMap<>();
                        h.put("prop",s[i]);
                        h.put("label",s[i]);
                        header.add(h);
                    }else{
                        m.put((String)header.get(i).get("prop"),s[i]);
                    }

                }
                if(flag)content.add(m);

                flag = true;
            }
            if(file.delete()){
                System.out.println(file.getName() + " 文件已被删除！");
            }else{
                System.out.println("文件删除失败！");
            }
        }else{
            OkHttpClient client =new OkHttpClient();
            JSONObject jsonObject =new JSONObject();
            jsonObject.put("factory", factory);
            jsonObject.put("timeRange", timeRange);
            jsonObject.put("device", device);
            jsonObject.put("measurePoint", measurePoint);
            jsonObject.put("group", group);
            jsonObject.put("agg", agg);
            com.squareup.okhttp.RequestBody requestBody = com.squareup.okhttp.RequestBody.create(jsonType, jsonObject.toJSONString());
            String apiURL = "http://localhost:5000/algorithm/olaprotate";
            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder().url(apiURL).addHeader("Content-Type", "application/json;charset=utf-8").post(requestBody).build();
            client.newCall(request).execute();
            return "";
        }

        Map<String, Object> tmp = new HashMap<>();
        tmp.put("header",header);
        tmp.put("content",content);
        result.add(tmp);
        return JSONObject.toJSONString(result);
    }

}
