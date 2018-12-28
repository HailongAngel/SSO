package cn.diguo.sso.service.impl;

import cn.diguo.sso.dao.IUserMapper;
import cn.diguo.sso.model.User;
import cn.diguo.sso.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @program: sso
 * @description:
 * @author: Hailong
 * @create: 2018-12-27 17:32
 **/
@Service
public class UserServiceImpl implements IUserService {
   @Autowired
   private IUserMapper userDao;

    @Override
    public User LoginInfo(String username) {
        return userDao.selectUserByUsername(username);

    }
}
