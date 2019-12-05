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
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class getData {

    @Autowired
    private DataService datasService;


    @Autowired
    private DataSource dataSource;

    //    不指定测点，列出所有测点的数据，对应于查询功能，并构造出可以直接在前端放置的json数据结构
//    @RequestMapping(value = "/getSpecificData", method = RequestMethod.POST)
//    @ResponseBody
//    public String getSpecificData(@RequestBody Map<String, Object> dt) {
//        QueryWrapper<Datas> qryWrapper = new QueryWrapper<>();
//        Map<String, Object> para = new HashMap<>();
//        para.put("factory", dt.get("factory"));
//        para.put("line", dt.get("line"));
//        para.put("device", dt.get("device"));
//        ArrayList<String> timestamp = (ArrayList<String>) dt.get("timestamp");
//
//        String timestart = timestamp.get(0);
//        String timeend = timestamp.get(1);
//
//        qryWrapper.allEq(para).between("timestamps", timestart, timeend);
//        List<Map<String, Object>> res = datasService.listMaps(qryWrapper);
//        List<Map<String, Object>> result = new ArrayList<>();
//        for (int i = 0; i < res.size(); i++) {
//
//            Object t = res.get(i).get("timestamps");
//            for (String key : res.get(i).keySet()) {
//                Map<String, Object> m = new HashMap<>();
//                if (key.equals("timestamps")) continue;
//                Object value = res.get(i).get(key);
//                m.put("date", t);
//                m.put("measurePoint", ExcelOp.valueToKey().get(key));
//                m.put("value", value);
//                result.add(m);
//            }
//        }
//
//        String tempJS = JSON.toJSONString(result);
//        return tempJS;
//    }

    @RequestMapping(value = "/getSpecificData", method = RequestMethod.POST)
    @ResponseBody
    public String getSpecificData(@RequestBody Map<String, Object> dt) throws ParseException, SQLException {

        Map<String, Object> para = new HashMap<>();
        para.put("factory", dt.get("factory"));
        para.put("line", dt.get("line"));
        para.put("device", dt.get("device"));
        ArrayList<String> timestamp = (ArrayList<String>) dt.get("timestamp");

        String timestart = timestamp.get(0).substring(0,10);
        String timeend = timestamp.get(1).substring(0,10);


        List<Map<String, Object>> res = new DbOperation().getAll("rtdata",timestart,timeend,dataSource.getConnection());
        System.out.println(res.size());


        String tempJS = JSON.toJSONString(res);
        return tempJS;
    }

    //    获取factory、line、device信息
    @RequestMapping(value = "/getMetaDataTree", method = RequestMethod.POST)
    @ResponseBody
    public String getMetaDataTree() throws SQLException {

        List<Map<String, String>> list = ExcelOp.selectMetadata("datas", dataSource.getConnection());

        List<Map<String, Object>> rootList = new ArrayList<>();

        List<Map<String, Object>> bodyList = new ArrayList<>();
        List<String> tmp = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (!tmp.contains(list.get(i).get("factory"))) {
                Map<String, Object> dt = new HashMap<>();
                dt.put("label", list.get(i).get("factory"));
                dt.put("plabel", "root");
                dt.put("value", list.get(i).get("factory"));
                tmp.add(list.get(i).get("factory"));
                rootList.add(dt);
            }
            if (!tmp.contains(list.get(i).get("factory") + list.get(i).get("line"))) {
                Map<String, Object> dt2 = new HashMap<>();
                dt2.put("label", list.get(i).get("line"));
                dt2.put("plabel", list.get(i).get("factory"));
                dt2.put("value", list.get(i).get("line"));
                tmp.add(list.get(i).get("factory") + list.get(i).get("line"));
                bodyList.add(dt2);
            }

            if (!tmp.contains(list.get(i).get("factory") + list.get(i).get("line") + list.get(i).get("device"))) {
                Map<String, Object> dt3 = new HashMap<>();
                dt3.put("label", list.get(i).get("device"));
                dt3.put("plabel", list.get(i).get("line"));
                dt3.put("value", list.get(i).get("device"));
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
    public String getAllMeasurePoint() {
        return JSONObject.toJSONString(ExcelOp.valueToKey().values());
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
