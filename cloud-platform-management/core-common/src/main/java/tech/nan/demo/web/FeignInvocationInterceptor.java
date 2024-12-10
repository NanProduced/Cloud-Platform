package tech.nan.demo.web;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tech.nan.demo.utils.InvocationContextHolder;
import tech.nan.demo.utils.TraceService;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

/**
 * 发起Feign请求拦截器，将调用信息传递给下游
 * @author Nan
 */
public class FeignInvocationInterceptor implements RequestInterceptor {

    @Autowired
    private TraceService traceService;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        final ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(attributes)) {
            return;
        }

        final GenericInvocationContext invocationContext = InvocationContextHolder.getContext();

        final String traceId = Optional.ofNullable(invocationContext.getTraceId()).orElse(traceService.getTraceId());
        if (StringUtils.isNotBlank(traceId)) {
            requestTemplate.header(CustomHttpHeaders.TRACE_ID, traceId);
        }

        if (invocationContext.isUserRequest()) {
            requestTemplate.header(CustomHttpHeaders.REQUEST_USER, Base64Utils.encodeToString(
                    invocationContext.getRequestUserAsJson().getBytes(StandardCharsets.UTF_8)));
        }
    }
}
