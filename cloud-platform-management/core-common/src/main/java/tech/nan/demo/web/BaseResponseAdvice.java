package tech.nan.demo.web;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import tech.nan.demo.common.DynamicResponse;
import tech.nan.demo.utils.JsonUtils;

@RestControllerAdvice
public class BaseResponseAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Class converterType) {
        // 对响应进行统一处理
        // swagger2的响应不做处理
        return !methodParameter.getDeclaringClass().getName().contains("springfox") &&
                // @IgnoreDynamicResponse标注的方法不做处理
                !methodParameter.hasMethodAnnotation(IgnoreDynamicResponse.class);
    }

    @Override
    public Object beforeBodyWrite(Object obj, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        boolean isFeignInvocation = request.getHeaders().containsKey(CustomHttpHeaders.TRACE_ID);

        // feign调用不封装
        if (isFeignInvocation) {
            return obj;
        }

        // 已经包装过的响应不做处理
        if (obj instanceof DynamicResponse) {
            return obj;
        }

        //当返回类型是String时,消息转换器为StringHttpMessage,需先将统一响应体转为json
        if (obj instanceof String) {
            return JsonUtils.toJson(DynamicResponse.success(obj));
        }

        // 返回前端默认进行统一包装
        return DynamicResponse.success(obj);
    }
}
