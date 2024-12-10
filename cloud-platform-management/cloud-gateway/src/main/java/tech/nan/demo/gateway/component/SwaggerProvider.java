package tech.nan.demo.gateway.component;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.Arrays;
import java.util.List;

/**
 * swagger2文档聚合业务配置
 * 通过网关的注册中心动态发现所有的微服务文档
 * @author Nan
 */
@Configuration
@Primary
@RequiredArgsConstructor
public class SwaggerProvider implements SwaggerResourcesProvider {

    public static final String SWAGGER_DOC = "/v2/api-docs";

    @Lazy
    @Autowired
    private RouteLocator routeLocator;

    @Autowired
    private GatewayProperties gatewayProperties;

    private final static List<String> ignoreRoutes = Arrays.asList();

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = Lists.newArrayList();
        List<String> routes = Lists.newArrayList();
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
        gatewayProperties.getRoutes().stream().filter(routeDefinition -> (routes.contains(routeDefinition.getId())) && routeDefinition.getOrder() != 1)
                .forEach(route -> route.getPredicates().stream()
                        .filter(predicateDefinition -> (("Path").equalsIgnoreCase(predicateDefinition.getName()) && !ignoreRoutes.contains(predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0"))))
                        .forEach(predicateDefinition -> resources.add(swaggerResource(route.getId(), predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + "0").replace("/**", SWAGGER_DOC)))));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
