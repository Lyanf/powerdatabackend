package dclab.powerdatabackend.controller;

import com.alibaba.fastjson.JSONObject;
import dclab.powerdatabackend.model.Data;
import dclab.powerdatabackend.model.Datas;
import dclab.powerdatabackend.service.DataService;
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
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UploadExcel {
    @Autowired
    private DataService dataService;

    @Autowired
    private DataSource dataSource;

    @RequestMapping("/uploadexcel")
    @ResponseBody
    public String importExcel(@RequestParam(value="filename") MultipartFile file){

        if(file.isEmpty()){
            String str = "{status : 'Failed',msg : '文件为空'}";
            //转换字符串为JSONObject
            JSONObject result = JSONObject.parseObject(str);
            return result.toJSONString();
        }
        InputStream is = null;
        try{
            is = file.getInputStream();
            //获取文件名
            String fileName = file.getOriginalFilename();
            String[] str = fileName.split("_");
            System.out.println(str[0] + "|" +str[1]);

            //根据版本选择创建Workbook的方式
            Workbook wb = null;
            Sheet sheetAt = null;
            wb = new XSSFWorkbook(is);
            sheetAt = wb.getSheetAt(0);
            List<Datas> datalist = new ArrayList<Datas>();

            for (int i = sheetAt.getFirstRowNum(); i < sheetAt.getLastRowNum(); i++) {
                Row row = sheetAt.getRow(i);
                int rowNum = row.getRowNum();
                if (rowNum == 0 || rowNum == 1) {
                    continue;
                }
                Datas dt = new Datas();
                dt.setFactory(str[0]);
                dt.setLine(str[1]);
                dt.setDevice(str[2]);
                dt.setTimestamp(row.getCell(0).getStringCellValue());
                Double APhaseElectricTension = row.getCell(1).getNumericCellValue();
                dt.setAPhaseElectricTension(APhaseElectricTension);
                Double BPhaseElectricTension = row.getCell(2).getNumericCellValue();
                dt.setBPhaseElectricTension(BPhaseElectricTension);
                Double CPhaseElectricTension = row.getCell(3).getNumericCellValue();
                dt.setCPhaseElectricTension(CPhaseElectricTension);
                Double ABLineElectricTension = row.getCell(4).getNumericCellValue();
                dt.setABLineElectricTension(ABLineElectricTension);
                Double BCLineElectricTension = row.getCell(5).getNumericCellValue();
                dt.setBCLineElectricTension(BCLineElectricTension);
                Double CALineElectricTension = row.getCell(6).getNumericCellValue();
                dt.setCALineElectricTension(CALineElectricTension);
                Double APhaseElectricCurrent = row.getCell(7).getNumericCellValue();
                dt.setAPhaseElectricCurrent(APhaseElectricCurrent);
                Double BPhaseElectricCurrent = row.getCell(8).getNumericCellValue();
                dt.setBPhaseElectricCurrent(BPhaseElectricCurrent);
                Double CPhaseElectricCurrent = row.getCell(9).getNumericCellValue();
                dt.setCPhaseElectricCurrent(CPhaseElectricCurrent);
                Double ZeroSequenceCurrent = row.getCell(10).getNumericCellValue();
                dt.setZeroSequenceCurrent(ZeroSequenceCurrent);
                Double APhaseActivePower = row.getCell(11).getNumericCellValue();
                dt.setAPhaseActivePower(APhaseActivePower);
                Double BPhaseActivePower = row.getCell(12).getNumericCellValue();
                dt.setBPhaseActivePower(BPhaseActivePower);
                Double CPhaseActivePower = row.getCell(13).getNumericCellValue();
                dt.setCPhaseActivePower(CPhaseActivePower);
                Double ThreePhaseTotalActivePower = row.getCell(14).getNumericCellValue();
                dt.setThreePhaseTotalActivePower(ThreePhaseTotalActivePower);
                Double APhaseReactivePower = row.getCell(15).getNumericCellValue();
                dt.setAPhaseReactivePower(APhaseReactivePower);
                Double BPhaseReactivePower = row.getCell(16).getNumericCellValue();
                dt.setBPhaseReactivePower(BPhaseReactivePower);
                Double CPhaseReactivePower = row.getCell(17).getNumericCellValue();
                dt.setCPhaseReactivePower(CPhaseReactivePower);
                Double ThreePhaseTotalReactivePower = row.getCell(18).getNumericCellValue();
                dt.setThreePhaseTotalReactivePower(ThreePhaseTotalReactivePower);
                Double APhaseAtPower = row.getCell(19).getNumericCellValue();
                dt.setAPhaseAtPower(APhaseAtPower);
                Double BPhaseAtPower = row.getCell(20).getNumericCellValue();
                dt.setBPhaseAtPower(BPhaseAtPower);
                Double CPhaseAtPower = row.getCell(21).getNumericCellValue();
                dt.setCPhaseAtPower(CPhaseAtPower);
                Double ThreePhaseTotalAtPower = row.getCell(22).getNumericCellValue();
                dt.setThreePhaseTotalAtPower(ThreePhaseTotalAtPower);
                Double APhasePowerFactor = row.getCell(23).getNumericCellValue();
                dt.setAPhasePowerFactor(APhasePowerFactor);
                Double BPhasePowerFactor = row.getCell(24).getNumericCellValue();
                dt.setBPhasePowerFactor(BPhasePowerFactor);
                Double CPhasePowerFactor = row.getCell(25).getNumericCellValue();
                dt.setCPhasePowerFactor(CPhasePowerFactor);
                Double AveragePowerFactor = row.getCell(26).getNumericCellValue();
                dt.setAveragePowerFactor(AveragePowerFactor);
                Double Frequency = row.getCell(27).getNumericCellValue();
                dt.setFrequency(Frequency);
                Double ForwardActive = row.getCell(28).getNumericCellValue();
                dt.setForwardActive(ForwardActive);
                Double ReverseActive = row.getCell(29).getNumericCellValue();
                dt.setReverseActive(ReverseActive);
                Double ForwardReactiveWattage = row.getCell(30).getNumericCellValue();
                dt.setForwardReactiveWattage(ForwardReactiveWattage);
                Double ReverseReactiveWattage = row.getCell(31).getNumericCellValue();
                dt.setReverseReactiveWattage(ReverseReactiveWattage);
                Double VoltageUnbalance = row.getCell(32).getNumericCellValue();
                dt.setVoltageUnbalance(VoltageUnbalance);
                Double ElectricCurrentUnbalance = row.getCell(33).getNumericCellValue();
                dt.setElectricCurrentUnbalance(ElectricCurrentUnbalance);
                Double APhaseVoltageHarmonicTotalDistortion = row.getCell(34).getNumericCellValue();
                dt.setAPhaseVoltageHarmonicTotalDistortion(APhaseVoltageHarmonicTotalDistortion);
                Double BPhaseVoltageHarmonicTotalDistortion = row.getCell(35).getNumericCellValue();
                dt.setBPhaseVoltageHarmonicTotalDistortion(BPhaseVoltageHarmonicTotalDistortion);
                Double CPhaseVoltageHarmonicTotalDistortion = row.getCell(36).getNumericCellValue();
                dt.setCPhaseVoltageHarmonicTotalDistortion(CPhaseVoltageHarmonicTotalDistortion);
                Double TotalHarmonicDistortionOfAPhaseCurrent = row.getCell(37).getNumericCellValue();
                dt.setTotalHarmonicDistortionOfAPhaseCurrent(TotalHarmonicDistortionOfAPhaseCurrent);
                Double TotalHarmonicDistortionOfBPhaseCurrent = row.getCell(38).getNumericCellValue();
                dt.setTotalHarmonicDistortionOfBPhaseCurrent(TotalHarmonicDistortionOfBPhaseCurrent);
                Double TotalHarmonicDistortionOfCPhaseCurrent = row.getCell(39).getNumericCellValue();
                dt.setTotalHarmonicDistortionOfCPhaseCurrent(TotalHarmonicDistortionOfCPhaseCurrent);
                Double MaximumPositiveActiveDemand = row.getCell(40).getNumericCellValue();
                dt.setMaximumPositiveActiveDemand(MaximumPositiveActiveDemand);
                Double MaximumReverseActiveDemand = row.getCell(41).getNumericCellValue();
                dt.setMaximumReverseActiveDemand(MaximumReverseActiveDemand);
                Double MaximumForwardReactivePowerDemand = row.getCell(42).getNumericCellValue();
                dt.setMaximumForwardReactivePowerDemand(MaximumForwardReactivePowerDemand);
                Double MaximumReverseReactivePowerDemand = row.getCell(43).getNumericCellValue();
                dt.setMaximumReverseReactivePowerDemand(MaximumReverseReactivePowerDemand);
                datalist.add(dt);

            }

            if(datalist.size() > 0){
                dataService.saveBatch(datalist);
            }
        }catch (IOException e){
            e.printStackTrace();

        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }

        String str = "{status : 'Success',msg : '导入成功'}";
        //转换字符串为JSONObject
        JSONObject result = JSONObject.parseObject(str);
        return result.toJSONString();

    }
}
