package com.gj.crm.workbench.service.impl;

import com.gj.crm.commons.contants.Contants;
import com.gj.crm.commons.utils.DataUtils;
import com.gj.crm.commons.utils.UUIDUtils;
import com.gj.crm.settings.entity.User;
import com.gj.crm.workbench.entity.Customer;
import com.gj.crm.workbench.entity.FinalAo;
import com.gj.crm.workbench.entity.Tran;
import com.gj.crm.workbench.entity.TranHistory;
import com.gj.crm.workbench.mapper.CustomerMapper;
import com.gj.crm.workbench.mapper.TranHistoryMapper;
import com.gj.crm.workbench.mapper.TranMapper;
import com.gj.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 郭嘉
 * @date 2022/10/23 - 18:03
 */
@Service("tranService")
public class TranServiceImpl implements TranService {

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private TranMapper tranMapper;
    @Autowired
    private TranHistoryMapper tranHistoryMapper;

    @Override
    public void saveCreateTran(Map<String ,Object> map) {
        String name=(String) map.get("customerName");
        User user=(User)map.get(Contants.SSESSION_USER_KEY);
        Customer customer=customerMapper.selectCustomerByName(name);
        //判断客户是否为空，如果空则新建
        Customer newCustomer=null;
        if(customer==null){
             newCustomer=new Customer();
             customer.setOwner(user.getId());
             customer.setName(name);
             customer.setId(UUIDUtils.getUUID());
             customer.setCreateTime(DataUtils.formateDataTime(new Date()));
             customer.setCreateBy(user.getId());
             customerMapper.insertCustomer(customer);
        }
        //保存创建的交易
        Tran tran=new Tran();
        tran.setStage((String) map.get("stage"));
        tran.setOwner((String) map.get("owner"));
        tran.setNextContactTime((String) map.get("nextContactTime"));
        tran.setName((String) map.get("name"));
        tran.setMoney((String) map.get("money"));
        tran.setId(UUIDUtils.getUUID());
        tran.setExpectedDate((String) map.get("expectedDate"));
        tran.setCustomerId(customer.getId());
        tran.setCreateTime(DataUtils.formateDataTime(new Date()));
        tran.setCreateBy(user.getId());
        tran.setContactSummary((String) map.get("contactSummary"));
        tran.setContactsId((String) map.get("contactsId"));
        tran.setActivityId((String) map.get("activityId"));
        tran.setDescription((String) map.get("description"));
        tran.setSource((String) map.get("source"));
        tran.setType((String) map.get("type"));
        tranMapper.insertTran(tran);
        //保存交易历史
        TranHistory tranHistory=new TranHistory();
        tranHistory.setCreateBy(user.getId());
        tranHistory.setCreateTime(DataUtils.formateDataTime(new Date()));
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setId(UUIDUtils.getUUID());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setStage(tran.getStage());
        tranHistory.setTranId(tran.getId());
        tranHistoryMapper.insertTranHistory(tranHistory);
    }

    @Override
    public Tran queryTranForDetailById(String id) {
        return tranMapper.selectTranForDetailById(id);
    }

    @Override
    public List<FinalAo> queryCountOfTranByGroup() {
        return tranMapper.selectCountOfTranByGroup();
    }
}
