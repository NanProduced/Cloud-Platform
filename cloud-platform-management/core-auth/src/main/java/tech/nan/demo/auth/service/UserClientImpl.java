package tech.nan.demo.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.nan.demo.auth.client.api.UserApi;
import tech.nan.demo.auth.client.dto.UserDTO;
import tech.nan.demo.auth.infrastructure.UserMapper;
import tech.nan.demo.auth.model.DO.UserDO;
import tech.nan.demo.auth.model.DO.UserRelDO;
import tech.nan.demo.auth.model.converter.UserConverter;

import java.time.LocalDateTime;
import java.util.Objects;

@RestController
public class UserClientImpl implements UserApi {

    @Autowired
    private UserConverter converter;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public Long createUser(@RequestBody @Validated UserDTO userDTO) {
        // todo：校验
        UserDO existUser = userMapper.selectOne(new LambdaQueryWrapper<>(UserDO.class).eq(UserDO::getUsername, userDTO.getUsername()));
        if (Objects.nonNull(existUser)) {
            return null;
        }
        UserDO userDO = converter.convert2UserDO(userDTO);
        userDO.setPassword(passwordEncoder.encode(userDO.getPassword()));
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
