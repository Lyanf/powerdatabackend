package dclab.powerdatabackend.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import dclab.powerdatabackend.mapper.OldDatasMapper;
import dclab.powerdatabackend.model.Datas;
import org.springframework.stereotype.Service;


@Service
public class DataService extends ServiceImpl<OldDatasMapper, Datas> {

}

