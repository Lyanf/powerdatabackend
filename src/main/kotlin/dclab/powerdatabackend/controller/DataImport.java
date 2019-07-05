package dclab.powerdatabackend.controller;


import com.alibaba.fastjson.JSONObject;
import dclab.powerdatabackend.util.ExcelOp;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class DataImport {

    @Autowired
    private DataSource dataSource;

    @RequestMapping("/uploaddata")
    @ResponseBody
    public String importExcel(@RequestParam(value="filename") MultipartFile file) {

        if (file.isEmpty()) {
            String str = "{status : 'Failed',msg : '文件为空'}";
            //转换字符串为JSONObject
            JSONObject result = JSONObject.parseObject(str);
            return result.toJSONString();
        }
        InputStream is = null;
        try {
            is = file.getInputStream();
            //获取文件名
            String fileName = file.getOriginalFilename();
            String[] str = fileName.split("_");

            //根据版本选择创建Workbook的方式
            Workbook wb = null;
            Sheet sheetAt = null;
            wb = new XSSFWorkbook(is);
            sheetAt = wb.getSheetAt(0);
            List<Map<String, Object>> datalist = new ArrayList<>();
            List<String> colname = new ArrayList<>();
            Map<String, String> nameMap = new HashMap<>();
            nameMap = ExcelOp.dataStructure();
            colname.add(nameMap.get("时间"));
            Row r = sheetAt.getRow(1);
            int colnum = sheetAt.getRow(3).getPhysicalNumberOfCells();
            for (int i = 1; i < colnum; i++) {
                String s= r.getCell(i).getStringCellValue();
                colname.add(nameMap.get(s));
            }
            System.out.println(colname);

            for (int i = sheetAt.getFirstRowNum(); i < sheetAt.getLastRowNum(); i++) {
                Row row = sheetAt.getRow(i);
                int rowNum = row.getRowNum();
                if (rowNum == 0 || rowNum == 1) {
                    continue;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("factory", str[0]);
                map.put("line",str[1]);
                map.put("device", str[2]);
                map.put(colname.get(0),row.getCell(0).getStringCellValue());
                for (int j = 1; j < colnum; j++) {
                    if(colname.get(j) == null){
                        continue;
                    }
                    map.put(colname.get(j),row.getCell(j).getNumericCellValue());
                }

                datalist.add(map);
            }
//            System.out.println(datalist.get(0));
            ExcelOp.insertAll("datas",datalist,dataSource.getConnection());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String str = "{status : 'Success',msg : '导入成功'}";
        //转换字符串为JSONObject
        JSONObject result = JSONObject.parseObject(str);
        return result.toJSONString();
    }


}
