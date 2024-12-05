package tech.nan.demo.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.util.Base64Utils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import tech.nan.demo.http.CustomHttpHeaders;
import tech.nan.demo.http.GenericInvocationContext;
import tech.nan.demo.utils.InvocationContextHolder;

public class InvocationContextExtractInterceptor implements WebRequestInterceptor {

    @Override
    public void preHandle(WebRequest request) throws Exception {
        final ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        GenericInvocationContext context = new GenericInvocationContext();
        String requestHeader = servletWebRequest.getHeader(CustomHttpHeaders.REQUEST_USER);
        if (StringUtils.isNotBlank(requestHeader)) {
            String json = new String(Base64Utils.decodeFromString(requestHeader));
            context.setRequestUser(json);
        }

        InvocationContextHolder.setContext(context);

    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {

    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {

    }
}
