package cn.diguo.sso.dao;

import cn.diguo.sso.dao.*;
import cn.diguo.sso.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: basi.cproject
 * @description:
 * @author: Hailong
 * @create: 2018-12-11 10:15
 **/
@Mapper
public interface IUserMapper {
    User selectUserByUsername(String username);
}