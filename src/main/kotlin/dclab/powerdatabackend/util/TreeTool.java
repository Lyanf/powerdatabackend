package dclab.powerdatabackend.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class TreeTool {


    private List<Map<String, Object>> rootList; //根节点对象存放到这里

    private List<Map<String, Object>> bodyList; //其他节点存放到这里，可以包含根节点

    public TreeTool(List<Map<String, Object>> rootList, List<Map<String, Object>> bodyList) {
        this.rootList = rootList;
        this.bodyList = bodyList;
    }

    public List<Map<String, Object>> getTree(){   //调用的方法入口
        if(bodyList != null && !bodyList.isEmpty()){
            //声明一个map，用来过滤已操作过的数据
            Map<Object,Object> map = Maps.newHashMapWithExpectedSize(bodyList.size());
            rootList.forEach(deviceTree -> getChild(deviceTree,map));
            return rootList;
        }
        return null;
    }

    public void getChild(Map<String, Object> deviceTree,Map<Object,Object> map){
        List<Map<String, Object>> childList = Lists.newArrayList();
        bodyList.stream()
                .filter(c -> !map.containsKey(c.get("label")))
                .filter(c ->c.get("plabel").equals(deviceTree.get("label")))
                .forEach(c ->{
                    map.put(c.get("label"),c.get("plabel"));
                    getChild(c,map);
                    childList.add(c);
                });
        if(childList.size() != 0)deviceTree.put("children",childList);
    }

}
