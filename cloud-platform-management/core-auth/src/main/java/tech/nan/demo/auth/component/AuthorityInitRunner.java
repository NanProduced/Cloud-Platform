package tech.nan.demo.auth.component;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import tech.nan.demo.auth.infrastructure.AuthUrlMapper;
import tech.nan.demo.auth.infrastructure.AuthorityMapper;
import tech.nan.demo.auth.model.DO.AuthUrlDO;
import tech.nan.demo.auth.model.DO.AuthorityDO;

import java.util.List;

@Component
@Slf4j
public class AuthorityInitRunner implements ApplicationRunner {

    private final AuthUrlMapper authUrlMapper;

    private final AuthorityMapper authorityMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    public AuthorityInitRunner(AuthUrlMapper authUrlMapper, AuthorityMapper authorityMapper, RedisTemplate<String, Object> redisTemplate) {
        this.authUrlMapper = authUrlMapper;
        this.authorityMapper = authorityMapper;
        this.redisTemplate = redisTemplate;
    }

    private final String AUTH_URL_FORMAT = "auth_url:%s";

    private final String AUTHORITY_KEY = "authority:list";

    private final String AUTH_PREFIX = "AUTH_";

    @Override
    public void run(ApplicationArguments args) {
        List<AuthUrlDO> allAuthUrl = authUrlMapper.selectList(new LambdaQueryWrapper<>(AuthUrlDO.class).select(AuthUrlDO::getId, AuthUrlDO::getUrl, AuthUrlDO::getAuthorization));
        for (AuthUrlDO url : allAuthUrl) {
            redisTemplate.opsForValue().set(String.format(AUTH_URL_FORMAT, url.getUrl()), AUTH_PREFIX + url.getAuthorization());
        }
        log.info("Init the auth url, count:{}", allAuthUrl.size());

        List<AuthorityDO> allAuthority = authorityMapper.selectList(new LambdaQueryWrapper<>(AuthorityDO.class)
                .select(AuthorityDO::getId, AuthorityDO::getAuthority, AuthorityDO::getCode));
        redisTemplate.opsForValue().set(AUTHORITY_KEY, allAuthority);
        log.info("Init the authority list, count:{}", allAuthority.size());
    }
}
