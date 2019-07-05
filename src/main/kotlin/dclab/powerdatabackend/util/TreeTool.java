package dclab.powerdatabackend.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dclab.powerdatabackend.model.DeviceTree;

import java.util.List;
import java.util.Map;

public class TreeTool {


    private List<DeviceTree> rootList; //根节点对象存放到这里

    private List<DeviceTree> bodyList; //其他节点存放到这里，可以包含根节点

    public TreeTool(List<DeviceTree> rootList, List<DeviceTree> bodyList) {
        this.rootList = rootList;
        this.bodyList = bodyList;
    }

    public List<DeviceTree> getTree(){   //调用的方法入口
        if(bodyList != null && !bodyList.isEmpty()){
            //声明一个map，用来过滤已操作过的数据
            Map<String,String> map = Maps.newHashMapWithExpectedSize(bodyList.size());
            rootList.forEach(deviceTree -> getChild(deviceTree,map));
            return rootList;
        }
        return null;
    }

    public void getChild(DeviceTree deviceTree,Map<String,String> map){
        List<DeviceTree> childList = Lists.newArrayList();
        bodyList.stream()
                .filter(c -> !map.containsKey(c.getLabel()))
                .filter(c ->c.getPlabel().equals(deviceTree.getLabel()))
                .forEach(c ->{
                    map.put(c.getLabel(),c.getPlabel());
                    getChild(c,map);
                    childList.add(c);
                });
        deviceTree.setChildren(childList);
    }

}
