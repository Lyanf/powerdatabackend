package dclab.powerdatabackend.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import dclab.powerdatabackend.model.Datas;
import dclab.powerdatabackend.model.DeviceTree;
import dclab.powerdatabackend.service.DataService;
import dclab.powerdatabackend.util.ExcelOp;
import dclab.powerdatabackend.util.TreeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    public String getSpecificData(@ModelAttribute Datas dt) {
        QueryWrapper<Datas> qryWrapper = new QueryWrapper<>();
        Map<String, Object> para = new HashMap<>();
        para.put("factory", dt.getFactory());
        para.put("line", dt.getLine());
        para.put("device", dt.getDevice());
//        para.put("timestamp",dt.getTimestamp());
        String[] timestamp = dt.getTimestamp().replace('[', ',').replace(']', ',').split(",");

        String timestart = timestamp[1];
        String timeend = timestamp[2];

        qryWrapper.allEq(para).between("timestamp", timestart, timeend);
        List<Map<String, Object>> res = datasService.listMaps(qryWrapper);
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < res.size(); i++) {

            Object t = res.get(i).get("timestamp");
            for (String key : res.get(i).keySet()) {
                Map<String, Object> m = new HashMap<>();
                if (key.equals("timestamp") || key.equals("id")) continue;
                Object value = res.get(i).get(key);
                m.put("date", t);
                m.put("measurePoint", ExcelOp.valueToKey().get(key));
                m.put("value", value);
                result.add(m);
            }
        }
        System.out.print(JSON.toJSONString(result));
//        String str = "{status : 'Success',msg : 'h'}";
        //转换字符串为JSONObject
//        JSONObject result = JSONObject.parseObject(str);

        return JSON.toJSONString(result);
    }

    @RequestMapping(value = "/getMetaDataTree", method = RequestMethod.POST)
    @ResponseBody
    public String getMetaDataTree() throws SQLException {

        List<Map<String, String>> list = ExcelOp.selectMetadata("datas", dataSource.getConnection());

        List<DeviceTree> rootList = new ArrayList<>();

        List<DeviceTree> bodyList = new ArrayList<>();
        List<String> tmp = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (!tmp.contains(list.get(i).get("factory"))) {
                DeviceTree dt = new DeviceTree();
                dt.setLabel(list.get(i).get("factory"));
                dt.setPlabel("root");
                dt.setValue(list.get(i).get("factory"));
                tmp.add(list.get(i).get("factory"));
                rootList.add(dt);
            }
            if (!tmp.contains(list.get(i).get("factory") + list.get(i).get("line"))) {
                DeviceTree dt2 = new DeviceTree();
                dt2.setLabel(list.get(i).get("line"));
                dt2.setPlabel(list.get(i).get("factory"));
                dt2.setValue(list.get(i).get("line"));
                tmp.add(list.get(i).get("factory") + list.get(i).get("line"));
                bodyList.add(dt2);
            }

            if (!tmp.contains(list.get(i).get("factory") + list.get(i).get("line") + list.get(i).get("device"))) {
                DeviceTree dt3 = new DeviceTree();
                dt3.setLabel(list.get(i).get("device"));
                dt3.setPlabel(list.get(i).get("line"));
                dt3.setValue(list.get(i).get("device"));
                tmp.add(list.get(i).get("factory") + list.get(i).get("line") + list.get(i).get("device"));
                bodyList.add(dt3);
            }

        }
        TreeTool tt = new TreeTool(rootList, bodyList);
        List<DeviceTree> result = tt.getTree();
//        System.out.println(JSONObject.toJSON(list));
//
//        System.out.print(JSONObject.toJSON(result));
        return JSONObject.toJSONString(result);
    }
}
