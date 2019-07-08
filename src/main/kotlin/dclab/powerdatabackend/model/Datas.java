package dclab.powerdatabackend.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.Table;


@TableName(value = "datas")
public class Datas {
    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @TableField(value="factory")
    private  String factory;
    @TableField(value="line")
    private  String line;

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @TableField(value = "device")
    private String device;
    @TableField(value="timestamp")
    private  String timestamp;
    @TableField(value="APhaseElectricTension")
    private  Double APhaseElectricTension;
    @TableField(value="BPhaseElectricTension")
    private  Double        BPhaseElectricTension;
    @TableField(value = "CPhaseElectricTension")
    private  Double CPhaseElectricTension;
    @TableField(value="ABLineElectricTension")
    private  Double        ABLineElectricTension;
    @TableField(value = "BCLineElectricTension")
    private  Double BCLineElectricTension;
    @TableField(value = "CALineElectricTension")
    private  Double        CALineElectricTension;
    @TableField(value = "APhaseElectricCurrent")
    private   Double APhaseElectricCurrent;
    @TableField(value = "BPhaseElectricCurrent")
    private   Double        BPhaseElectricCurrent;
    @TableField(value = "CPhaseElectricCurrent")
    private  Double CPhaseElectricCurrent;
    @TableField(value = "ZeroSequenceCurrent")
    private  Double        ZeroSequenceCurrent;
    @TableField(value = "APhaseActivePower")
    private  Double APhaseActivePower;
    @TableField(value = "BPhaseActivePower")
    private  Double        BPhaseActivePower;
    @TableField(value = "CPhaseActivePower")
    private  Double CPhaseActivePower;
    @TableField(value = "ThreePhaseTotalActivePower")
    private  Double        ThreePhaseTotalActivePower;
    @TableField(value = "APhaseReactivePower")
    private   Double APhaseReactivePower;
    @TableField(value = "BPhaseReactivePower")
    private   Double        BPhaseReactivePower;
    @TableField(value = "CPhaseReactivePower")
    private   Double CPhaseReactivePower;
    @TableField(value = "ThreePhaseTotalReactivePower")
    private   Double        ThreePhaseTotalReactivePower;
    @TableField(value = "APhaseAtPower")
    private   Double APhaseAtPower;
    @TableField(value = "BPhaseAtPower")
    private   Double        BPhaseAtPower;
    @TableField(value = "CPhaseAtPower")
    private   Double CPhaseAtPower;
    @TableField(value = "ThreePhaseTotalAtPower")
    private   Double        ThreePhaseTotalAtPower;
    @TableField(value = "APhasePowerFactor")
    private   Double APhasePowerFactor;
    @TableField(value = "BPhasePowerFactor")
    private   Double        BPhasePowerFactor;
    @TableField(value = "CPhasePowerFactor")
    private   Double CPhasePowerFactor;
    @TableField(value = "AveragePowerFactor")
    private   Double        AveragePowerFactor;
    @TableField(value = "Frequency")
    private   Double Frequency;
    @TableField(value = "ForwardActive")
    private   Double        ForwardActive;
    @TableField(value = "ReverseActive")
    private   Double ReverseActive;
    @TableField(value = "ForwardReactiveWattage")
    private   Double        ForwardReactiveWattage;
    @TableField(value = "ReverseReactiveWattage")
    private  Double ReverseReactiveWattage;
    @TableField(value = "VoltageUnbalance")
    private   Double        VoltageUnbalance;
    @TableField(value = "ElectricCurrentUnbalance")
    private  Double ElectricCurrentUnbalance;
    @TableField(value = "APhaseVoltageHarmonicTotalDistortion")
    private   Double        APhaseVoltageHarmonicTotalDistortion;
    @TableField(value = "BPhaseVoltageHarmonicTotalDistortion")
    private   Double BPhaseVoltageHarmonicTotalDistortion;
    @TableField(value = "CPhaseVoltageHarmonicTotalDistortion")
    private  Double        CPhaseVoltageHarmonicTotalDistortion;
    @TableField(value = "TotalHarmonicDistortionOfAPhaseCurrent")
    private   Double TotalHarmonicDistortionOfAPhaseCurrent;
    @TableField(value = "TotalHarmonicDistortionOfBPhaseCurrent")
    private   Double        TotalHarmonicDistortionOfBPhaseCurrent;

    public Double getAPhaseElectricTension() {
        return APhaseElectricTension;
    }

    public void setAPhaseElectricTension(Double APhaseElectricTension) {
        this.APhaseElectricTension = APhaseElectricTension;
    }

    public Double getBPhaseElectricTension() {
        return BPhaseElectricTension;
    }

    public void setBPhaseElectricTension(Double BPhaseElectricTension) {
        this.BPhaseElectricTension = BPhaseElectricTension;
    }

    public Double getCPhaseElectricTension() {
        return CPhaseElectricTension;
    }

    public void setCPhaseElectricTension(Double CPhaseElectricTension) {
        this.CPhaseElectricTension = CPhaseElectricTension;
    }

    public Double getABLineElectricTension() {
        return ABLineElectricTension;
    }

    public void setABLineElectricTension(Double ABLineElectricTension) {
        this.ABLineElectricTension = ABLineElectricTension;
    }

    public Double getBCLineElectricTension() {
        return BCLineElectricTension;
    }

    public void setBCLineElectricTension(Double BCLineElectricTension) {
        this.BCLineElectricTension = BCLineElectricTension;
    }

    public Double getCALineElectricTension() {
        return CALineElectricTension;
    }

    public void setCALineElectricTension(Double CALineElectricTension) {
        this.CALineElectricTension = CALineElectricTension;
    }

    public Double getAPhaseElectricCurrent() {
        return APhaseElectricCurrent;
    }

    public void setAPhaseElectricCurrent(Double APhaseElectricCurrent) {
        this.APhaseElectricCurrent = APhaseElectricCurrent;
    }

    public Double getBPhaseElectricCurrent() {
        return BPhaseElectricCurrent;
    }

    public void setBPhaseElectricCurrent(Double BPhaseElectricCurrent) {
        this.BPhaseElectricCurrent = BPhaseElectricCurrent;
    }

    public Double getCPhaseElectricCurrent() {
        return CPhaseElectricCurrent;
    }

    public void setCPhaseElectricCurrent(Double CPhaseElectricCurrent) {
        this.CPhaseElectricCurrent = CPhaseElectricCurrent;
    }

    public Double getZeroSequenceCurrent() {
        return ZeroSequenceCurrent;
    }

    public void setZeroSequenceCurrent(Double zeroSequenceCurrent) {
        ZeroSequenceCurrent = zeroSequenceCurrent;
    }

    public Double getAPhaseActivePower() {
        return APhaseActivePower;
    }

    public void setAPhaseActivePower(Double APhaseActivePower) {
        this.APhaseActivePower = APhaseActivePower;
    }

    public Double getBPhaseActivePower() {
        return BPhaseActivePower;
    }

    public void setBPhaseActivePower(Double BPhaseActivePower) {
        this.BPhaseActivePower = BPhaseActivePower;
    }

    public Double getCPhaseActivePower() {
        return CPhaseActivePower;
    }

    public void setCPhaseActivePower(Double CPhaseActivePower) {
        this.CPhaseActivePower = CPhaseActivePower;
    }

    public Double getThreePhaseTotalActivePower() {
        return ThreePhaseTotalActivePower;
    }

    public void setThreePhaseTotalActivePower(Double threePhaseTotalActivePower) {
        ThreePhaseTotalActivePower = threePhaseTotalActivePower;
    }

    public Double getAPhaseReactivePower() {
        return APhaseReactivePower;
    }

    public void setAPhaseReactivePower(Double APhaseReactivePower) {
        this.APhaseReactivePower = APhaseReactivePower;
    }

    public Double getBPhaseReactivePower() {
        return BPhaseReactivePower;
    }

    public void setBPhaseReactivePower(Double BPhaseReactivePower) {
        this.BPhaseReactivePower = BPhaseReactivePower;
    }

    public Double getCPhaseReactivePower() {
        return CPhaseReactivePower;
    }

    public void setCPhaseReactivePower(Double CPhaseReactivePower) {
        this.CPhaseReactivePower = CPhaseReactivePower;
    }

    public Double getThreePhaseTotalReactivePower() {
        return ThreePhaseTotalReactivePower;
    }

    public void setThreePhaseTotalReactivePower(Double threePhaseTotalReactivePower) {
        ThreePhaseTotalReactivePower = threePhaseTotalReactivePower;
    }

    public Double getAPhaseAtPower() {
        return APhaseAtPower;
    }

    public void setAPhaseAtPower(Double APhaseAtPower) {
        this.APhaseAtPower = APhaseAtPower;
    }

    public Double getBPhaseAtPower() {
        return BPhaseAtPower;
    }

    public void setBPhaseAtPower(Double BPhaseAtPower) {
        this.BPhaseAtPower = BPhaseAtPower;
    }

    public Double getCPhaseAtPower() {
        return CPhaseAtPower;
    }

    public void setCPhaseAtPower(Double CPhaseAtPower) {
        this.CPhaseAtPower = CPhaseAtPower;
    }

    public Double getThreePhaseTotalAtPower() {
        return ThreePhaseTotalAtPower;
    }

    public void setThreePhaseTotalAtPower(Double threePhaseTotalAtPower) {
        ThreePhaseTotalAtPower = threePhaseTotalAtPower;
    }

    public Double getAPhasePowerFactor() {
        return APhasePowerFactor;
    }

    public void setAPhasePowerFactor(Double APhasePowerFactor) {
        this.APhasePowerFactor = APhasePowerFactor;
    }

    public Double getBPhasePowerFactor() {
        return BPhasePowerFactor;
    }

    public void setBPhasePowerFactor(Double BPhasePowerFactor) {
        this.BPhasePowerFactor = BPhasePowerFactor;
    }

    public Double getCPhasePowerFactor() {
        return CPhasePowerFactor;
    }

    public void setCPhasePowerFactor(Double CPhasePowerFactor) {
        this.CPhasePowerFactor = CPhasePowerFactor;
    }

    public Double getAveragePowerFactor() {
        return AveragePowerFactor;
    }

    public void setAveragePowerFactor(Double averagePowerFactor) {
        AveragePowerFactor = averagePowerFactor;
    }

    public Double getFrequency() {
        return Frequency;
    }

    public void setFrequency(Double frequency) {
        Frequency = frequency;
    }

    public Double getForwardActive() {
        return ForwardActive;
    }

    public void setForwardActive(Double forwardActive) {
        ForwardActive = forwardActive;
    }

    public Double getReverseActive() {
        return ReverseActive;
    }

    public void setReverseActive(Double reverseActive) {
        ReverseActive = reverseActive;
    }

    public Double getForwardReactiveWattage() {
        return ForwardReactiveWattage;
    }

    public void setForwardReactiveWattage(Double forwardReactiveWattage) {
        ForwardReactiveWattage = forwardReactiveWattage;
    }

    public Double getReverseReactiveWattage() {
        return ReverseReactiveWattage;
    }

    public void setReverseReactiveWattage(Double reverseReactiveWattage) {
        ReverseReactiveWattage = reverseReactiveWattage;
    }

    public Double getVoltageUnbalance() {
        return VoltageUnbalance;
    }

    public void setVoltageUnbalance(Double voltageUnbalance) {
        VoltageUnbalance = voltageUnbalance;
    }

    public Double getElectricCurrentUnbalance() {
        return ElectricCurrentUnbalance;
    }

    public void setElectricCurrentUnbalance(Double electricCurrentUnbalance) {
        ElectricCurrentUnbalance = electricCurrentUnbalance;
    }

    public Double getAPhaseVoltageHarmonicTotalDistortion() {
        return APhaseVoltageHarmonicTotalDistortion;
    }

    public void setAPhaseVoltageHarmonicTotalDistortion(Double APhaseVoltageHarmonicTotalDistortion) {
        this.APhaseVoltageHarmonicTotalDistortion = APhaseVoltageHarmonicTotalDistortion;
    }

    public Double getBPhaseVoltageHarmonicTotalDistortion() {
        return BPhaseVoltageHarmonicTotalDistortion;
    }

    public void setBPhaseVoltageHarmonicTotalDistortion(Double BPhaseVoltageHarmonicTotalDistortion) {
        this.BPhaseVoltageHarmonicTotalDistortion = BPhaseVoltageHarmonicTotalDistortion;
    }

    public Double getCPhaseVoltageHarmonicTotalDistortion() {
        return CPhaseVoltageHarmonicTotalDistortion;
    }

    public void setCPhaseVoltageHarmonicTotalDistortion(Double CPhaseVoltageHarmonicTotalDistortion) {
        this.CPhaseVoltageHarmonicTotalDistortion = CPhaseVoltageHarmonicTotalDistortion;
    }

    public Double getTotalHarmonicDistortionOfAPhaseCurrent() {
        return TotalHarmonicDistortionOfAPhaseCurrent;
    }

    public void setTotalHarmonicDistortionOfAPhaseCurrent(Double totalHarmonicDistortionOfAPhaseCurrent) {
        TotalHarmonicDistortionOfAPhaseCurrent = totalHarmonicDistortionOfAPhaseCurrent;
    }

    public Double getTotalHarmonicDistortionOfBPhaseCurrent() {
        return TotalHarmonicDistortionOfBPhaseCurrent;
    }

    public void setTotalHarmonicDistortionOfBPhaseCurrent(Double totalHarmonicDistortionOfBPhaseCurrent) {
        TotalHarmonicDistortionOfBPhaseCurrent = totalHarmonicDistortionOfBPhaseCurrent;
    }

    public Double getTotalHarmonicDistortionOfCPhaseCurrent() {
        return TotalHarmonicDistortionOfCPhaseCurrent;
    }

    public void setTotalHarmonicDistortionOfCPhaseCurrent(Double totalHarmonicDistortionOfCPhaseCurrent) {
        TotalHarmonicDistortionOfCPhaseCurrent = totalHarmonicDistortionOfCPhaseCurrent;
    }

    public Double getMaximumPositiveActiveDemand() {
        return MaximumPositiveActiveDemand;
    }

    public void setMaximumPositiveActiveDemand(Double maximumPositiveActiveDemand) {
        MaximumPositiveActiveDemand = maximumPositiveActiveDemand;
    }

    public Double getMaximumReverseActiveDemand() {
        return MaximumReverseActiveDemand;
    }

    public void setMaximumReverseActiveDemand(Double maximumReverseActiveDemand) {
        MaximumReverseActiveDemand = maximumReverseActiveDemand;
    }

    public Double getMaximumForwardReactivePowerDemand() {
        return MaximumForwardReactivePowerDemand;
    }

    public void setMaximumForwardReactivePowerDemand(Double maximumForwardReactivePowerDemand) {
        MaximumForwardReactivePowerDemand = maximumForwardReactivePowerDemand;
    }

    public Double getMaximumReverseReactivePowerDemand() {
        return MaximumReverseReactivePowerDemand;
    }

    public void setMaximumReverseReactivePowerDemand(Double maximumReverseReactivePowerDemand) {
        MaximumReverseReactivePowerDemand = maximumReverseReactivePowerDemand;
    }
    @TableField(value = "TotalHarmonicDistortionOfCPhaseCurrent")
    private   Double TotalHarmonicDistortionOfCPhaseCurrent;
    @TableField(value = "MaximumPositiveActiveDemand")
    private   Double        MaximumPositiveActiveDemand;
    @TableField(value = "MaximumReverseActiveDemand")
    private   Double MaximumReverseActiveDemand;
    @TableField(value = "MaximumForwardReactivePowerDemand")
    private  Double        MaximumForwardReactivePowerDemand;
    @TableField(value = "MaximumReverseReactivePowerDemand")
    private  Double MaximumReverseReactivePowerDemand;

}
