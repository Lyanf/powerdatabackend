package dclab.powerdatabackend.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import dclab.powerdatabackend.model.Datas;
import dclab.powerdatabackend.model.Rtdata;
import dclab.powerdatabackend.service.DataService;
import dclab.powerdatabackend.util.DbOperation;
import dclab.powerdatabackend.util.ExcelOp;
import dclab.powerdatabackend.util.TreeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Array;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class getData {

    @Autowired
    private DataService datasService;


    @Autowired
    private DataSource dataSource;


    @RequestMapping(value = "/getSpecificData", method = RequestMethod.POST)
    @ResponseBody
    public String getSpecificData(@RequestBody Map<String, Object> dt) throws ParseException, SQLException {

        Map<String, Object> para = new HashMap<>();
        para.put("factory", dt.get("factory"));
        para.put("line", dt.get("line"));
        para.put("device", dt.get("device"));
        String device = (String)dt.get("device");
        ArrayList<String> timestamp = (ArrayList<String>) dt.get("timestamp");

        String timestart = timestamp.get(0).substring(0,10);
        String timeend = timestamp.get(1).substring(0,10);
        List<Map<String, Object>> res = null;
        if(device.equals("-1")) {

            res = new DbOperation().getAllByFactory("rtdata",timestart,timeend,(int)dt.get("factory") ,dataSource.getConnection());
        }else{
            res = new DbOperation().getAllBydevice("rtdata",timestart,timeend,device ,dataSource.getConnection());
        }



        String tempJS = JSON.toJSONString(res);
        return tempJS;
    }

    @RequestMapping(value = "/getSpecificData2", method = RequestMethod.POST)
    @ResponseBody
    public String getSpecificData2(@RequestBody Map<String, Object> dt) throws ParseException, SQLException {

        String measurePoint =(String)dt.get("measurePoint");
        ArrayList<ArrayList<String>> selectedMetaData = (ArrayList<ArrayList<String>>)dt.get("selectedMetaData");

        ArrayList<String> timestamp = (ArrayList<String>) dt.get("date");

        //String timestart = timestamp.get(0).substring(0,10);
        String timestart = timestamp.get(0);
        String timeend = timestamp.get(1);

        ArrayList<String> device = new ArrayList<>();
        ArrayList<Integer> factory = new ArrayList<>();
        for(int i = 0; i < selectedMetaData.size();i++){
            if(!factory.contains(selectedMetaData.get(i).get(0))){
                factory.add(Integer.parseInt(selectedMetaData.get(i).get(0)));
            }
            if(selectedMetaData.get(i).size() == 3){
                device.add(selectedMetaData.get(i).get(2));
            }
        }
        Map<String, Object> res = null;
        if(device.isEmpty()) {
            res = new DbOperation().getvalueByfactory("rtdata",timestart,timeend,measurePoint, factory ,dataSource.getConnection());
        }else{
            res = new DbOperation().getvalueBydevice("rtdata",timestart,timeend,device ,measurePoint, factory.get(0),dataSource.getConnection());
        }

        ArrayList<String> x = new ArrayList<>();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date s = sd.parse(timestart);
        Date e = sd.parse(timeend);
        x.add(sdf.format(s));
        while (s.before (e)){
            s = new Date(s.getTime() + 900000);
            x.add(sdf.format(s));
        }
        res.put("x", x);


        String tempJS = JSON.toJSONString(res);
        return tempJS;
    }

    //    获取factory、line、device信息
    @RequestMapping(value = "/getMetaDataTree", method = RequestMethod.POST)
    @ResponseBody
    public String getMetaDataTree() throws SQLException {

        List<Map<String, String>> list = ExcelOp.selectMetadata("rtdata", dataSource.getConnection());

        List<Map<String, Object>> rootList = new ArrayList<>();

        List<Map<String, Object>> bodyList = new ArrayList<>();
        List<String> tmp = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (!tmp.contains(list.get(i).get("factory"))) {
                Map<String, Object> dt = new HashMap<>();
                dt.put("label", list.get(i).get("factory"));
                dt.put("plabel", "root");
                dt.put("value", list.get(i).get("factory"));
                dt.put("flag", "1");
                tmp.add(list.get(i).get("factory"));
                rootList.add(dt);
            }
            if (!tmp.contains(list.get(i).get("factory") + list.get(i).get("line"))) {
                Map<String, Object> dt2 = new HashMap<>();
                dt2.put("label", list.get(i).get("line"));
                dt2.put("plabel", list.get(i).get("factory"));
                dt2.put("value", list.get(i).get("line"));
                dt2.put("flag", "2");
                tmp.add(list.get(i).get("factory") + list.get(i).get("line"));
                bodyList.add(dt2);
            }

            if (!tmp.contains(list.get(i).get("factory") + list.get(i).get("line") + list.get(i).get("device"))) {
                Map<String, Object> dt3 = new HashMap<>();
                dt3.put("label", list.get(i).get("device"));
                dt3.put("plabel", list.get(i).get("line"));
                dt3.put("value", list.get(i).get("device"));
                dt3.put("flag", "3");
                tmp.add(list.get(i).get("factory") + list.get(i).get("line") + list.get(i).get("device"));
                bodyList.add(dt3);
            }
        }


        TreeTool tt = new TreeTool(rootList, bodyList);
        List<Map<String, Object>> result = tt.getTree();
        return JSONObject.toJSONString(result);
    }
    @RequestMapping(value = "/getAllMeasurePoint", method = RequestMethod.POST)
    @ResponseBody
    public String getAllMeasurePoint() throws SQLException {
//        System.out.println(ExcelOp.valueToKey().values());
        List<String> l = new ArrayList<>();
        l = new DbOperation().getMeasurePoint("rtdata",dataSource.getConnection());
//        return JSONObject.toJSONString(ExcelOp.valueToKey().values());
        return JSONObject.toJSONString(l);
    }
    @RequestMapping(value = "/getMeasurePointData", method = RequestMethod.POST)
    @ResponseBody
    public String getMeasurePointData(@RequestBody Map<String, Object> dt) {
        QueryWrapper<Datas> queryWrapper = new QueryWrapper<>();
        Map<String, Object> para = new HashMap<>();
        para.put("factory", dt.get("factory"));
        para.put("line", dt.get("line"));
        para.put("device", dt.get("device"));
        String measurePoint = (String) dt.get("measurePoint");
        ArrayList<String> timestamp = (ArrayList<String>) dt.get("timestamp");
        String timestart = timestamp.get(0);
        String timeend = timestamp.get(1);

        queryWrapper.allEq(para)
                .between("timestamps", timestart, timeend);
        List<Map<String, Object>> res = datasService.listMaps(queryWrapper);
        List<Map<String,String>> cutDataList = new ArrayList<>();
        for (Map<String,Object> i : res) {
            HashMap<String,String>temp = new HashMap<String, String>();
            temp.put("timestamp",i.get("timestamps").toString());
            temp.put("value", i.get(measurePoint).toString());
            cutDataList.add(temp);
        }
        return JSON.toJSONString(cutDataList);
    }
}
