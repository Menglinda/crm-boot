package com.ldh.crm.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldh.crm.mapper.UserMapper;
import com.ldh.crm.pojo.Admin;
import com.ldh.crm.pojo.User;
import com.ldh.crm.service.AdminService;
import com.ldh.crm.mapper.AdminMapper;
import net.sf.jsqlparser.statement.insert.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 35108
 * @description 针对表【admin】的数据库操作Service实现
 * @createDate 2023-03-31 14:53:26
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
        implements AdminService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public String adminLogin(Admin admin) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", admin.getEmail());
        queryWrapper.eq("password", admin.getPassword());
        List<Admin> list = list(queryWrapper);
        if (list != null && list.size() > 0) return  IdUtil.simpleUUID();
        return null;
    }

    @Override
    public String addUser(User user) {
        User user1 = userMapper.selectById(user.getEmail());
        if(user1!=null){
            return null;
        }
        int count=0;
        count= userMapper.insert(user);
        if (count>0) return IdUtil.simpleUUID();
        return null;
    }


}




