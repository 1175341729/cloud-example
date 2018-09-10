package com.springcloud.example.dynamic.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springcloud.example.common.message.PageMessage;
import com.springcloud.example.common.message.PageUtil;
import com.springcloud.example.dynamic.dao.SaleAreasMapper;
import com.springcloud.example.dynamic.model.SaleAreas;
import com.springcloud.example.dynamic.model.SaleAreasExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/***
 *  @Author dengwei
 *  @Description: TODO
 *  @Date 2018/9/10 11:18
 */
@Service
public class SaleAreasService {
    @Resource
    private SaleAreasMapper saleAreasMapper;
    public PageMessage listSaleAreas(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        SaleAreasExample example = new SaleAreasExample();
        List<SaleAreas> saleAreas = saleAreasMapper.selectByExample(example);
        PageInfo<SaleAreas> pageInfo = new PageInfo<>(saleAreas);
        return PageUtil.page(pageInfo);
    }
}
