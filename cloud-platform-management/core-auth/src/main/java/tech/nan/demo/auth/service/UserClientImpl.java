package tech.nan.demo.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.nan.demo.auth.client.UserClient;
import tech.nan.demo.auth.client.dto.UserDTO;
import tech.nan.demo.auth.infrastructure.UserMapper;
import tech.nan.demo.auth.model.DO.UserDO;
import tech.nan.demo.auth.model.DO.UserRelDO;
import tech.nan.demo.auth.model.converter.UserConverter;

import java.time.LocalDateTime;

@Service
public class UserClientImpl implements UserClient {

    @Autowired
    private UserConverter converter;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Long createUser(UserDTO userDTO) {
        // todo：校验
        UserDO userDO = converter.convert2UserDO(userDTO);
        userMapper.insert(userDO);
        UserRelDO userRelDO = UserRelDO.builder()
                .groupId(userDTO.getGroupId())
                .userId(userDO.getId())
                .updateTime(LocalDateTime.now())
                .build();
        userMapper.insertUserRel(userRelDO);
        return userDO.getId();
    }
}
