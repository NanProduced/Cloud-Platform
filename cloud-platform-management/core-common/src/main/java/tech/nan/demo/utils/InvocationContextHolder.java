package tech.nan.demo.utils;

import org.springframework.lang.NonNull;
import tech.nan.demo.http.GenericInvocationContext;
import tech.nan.demo.web.InvocationContextExtractInterceptor;

/**
 * Servlet环境下当前请求用户上下文持有者,不支持子线程获取
 *
 * 通过 {@link InvocationContextExtractInterceptor} 拦截请求并放进ThreadLocal
 */
public class InvocationContextHolder {

    private static final ThreadLocal<GenericInvocationContext> contextHolder = new ThreadLocal<>();

    private InvocationContextHolder() {

    }

    public static void clearContext() {
        contextHolder.remove();
    }

    @NonNull
    public static GenericInvocationContext getContext() {
        return contextHolder.get();
    }

    public static void setContext(GenericInvocationContext context) {
        contextHolder.set(context);
    }

    @NonNull
    public static GenericInvocationContext createEmptyContext() {
        contextHolder.set(new GenericInvocationContext());
        return contextHolder.get();
    }
}
