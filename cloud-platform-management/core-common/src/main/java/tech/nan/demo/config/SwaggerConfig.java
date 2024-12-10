package tech.nan.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /*
    请求类的描述：
    @Api:对请求类的说明，可用注解@Api(tags = "XXX")标注对应模块

    方法和方法参数的描述：
    @ApiOperation:方法的说明
    @ApiImplicitParams:方法参数的说明
    @ApiImplicitParam:用于指定单个参数的说明

    方法的响应状态的描述：
    @ApiResponses:方法返回值的说明
    @ApiResponse:用于指定单个参数的说明

    对象的描述：
    @ApiModel:用在JavaBean类上，说明JavaBean的整体用途
    @ApiModelProperty：用在JavaBean类的属性上面，说明此属性的含义
     */

    /**
     * 单独部署微服务时需要在启动类application.yml中添加swagger.enable配置，生产环境可设置为false
     */
    @Value("${swagger.enable}")
    private Boolean enable;

    @Value("${spring.application.name}")
    private String serviceName;

    @Bean
    public Docket createRestApi(ApiInfo apiInfo) {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enable)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("tech.nan.demo"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(serviceName + " apis")
                .description("云平台后端接口文档")
                .version("1.0")
                .build();
    }
}
