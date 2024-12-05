package tech.nan.demo.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import tech.nan.demo.mysql.DO.UserDO;
import tech.nan.demo.mysql.mapper.UserMapper;
import tech.nan.demo.repository.UserRepository;

public class UserRepositoryImpl extends ServiceImpl<UserMapper, UserDO> implements UserRepository {

}
