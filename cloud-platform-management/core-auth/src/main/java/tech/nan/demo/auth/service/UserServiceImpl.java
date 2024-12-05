package tech.nan.demo.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.nan.demo.auth.infrastructure.UserMapper;
import tech.nan.demo.auth.infrastructure.UserRoleMapper;
import tech.nan.demo.auth.model.DO.AuthorityDO;
import tech.nan.demo.auth.model.DO.UserDO;
import tech.nan.demo.auth.model.DO.UserRoleDO;
import tech.nan.demo.auth.model.UserPrincipal;
import tech.nan.demo.auth.model.converter.UserConverter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService, Serializable {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${demo.authority.key}")
    private String authorityKey;

    @Override
    @SuppressWarnings("unchecked")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO userDO = userMapper.selectOne(new LambdaQueryWrapper<>(UserDO.class)
                .eq(UserDO::getUsername, username)
                .eq(UserDO::getStatus, 0));
        if (Objects.isNull(userDO)) {
            throw new UsernameNotFoundException(String.format("Can not find user: [%s].", username));
        }
        UserPrincipal user = userConverter.convert2User(userDO);
        if (!user.isEnabled()) {
            throw new DisabledException(String.format("User has been baned: [%s].", username));
        }
        List<UserRoleDO> userRoles = userRoleMapper.selectUserRolesByCode(userDO.getRolesCode());
        user.setRoles(userRoles.stream().map(UserRoleDO::getRoleName).collect(Collectors.toList()));
        int authoritiesCode = userRoles.stream().mapToInt(UserRoleDO::getAuthorities).reduce(0, (a, b) -> a | b);
        List<AuthorityDO> allAuthority = (List<AuthorityDO>) redisTemplate.opsForValue().get(authorityKey);
        assert allAuthority != null;
        user.setAuthorities(allAuthority
                .stream()
                .filter(auth -> (authoritiesCode & auth.getCode()) > 0)
                .map(auth -> new SimpleGrantedAuthority(auth.getAuthority()))
                .collect(Collectors.toList()));
        return user;
    }

}
