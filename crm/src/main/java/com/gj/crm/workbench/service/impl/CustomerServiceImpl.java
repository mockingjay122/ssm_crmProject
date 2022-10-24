package com.gj.crm.workbench.service.impl;

import com.gj.crm.workbench.mapper.CustomerMapper;
import com.gj.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 郭嘉
 * @date 2022/10/23 - 17:17
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public List<String> queryAllCustomerName(String customerName) {
        return customerMapper.selectAllCustomersName(customerName);
    }
}
