package cn.machaojin.database;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.jakarta.StatViewServlet;
import com.alibaba.druid.support.jakarta.WebStatFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ma Chaojin
 * @since 2024-04-29 20:25
 */
public class DruidConfig {
    /**
     * @return servlet registration bean
     * @description 注册一个StatViewServlet, 进行druid监控页面配置
     */
    @Bean
    public ServletRegistrationBean druidStatViewServlet() {
        //先配置管理后台的servLet，访问的入口为/druid/
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
                new StatViewServlet(), "/druid/*");
        // IP白名单 (没有配置或者为空，则允许所有访问)
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        // IP黑名单 (存在共同时，deny优先于allow)
        servletRegistrationBean.addInitParameter("deny", "");
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "machaojin");
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    /**
     * @return filter registration bean
     * @description 注册一个过滤器，允许页面正常浏览
     */
    @Bean
    public FilterRegistrationBean druidStatFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(
                new WebStatFilter());
        // 添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        // 添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    /**
     * @return
     * @description 配置慢sql拦截器
     */
    @Bean(name = "statFilter")
    public StatFilter statFilter() {
        StatFilter statFilter = new StatFilter();
        //慢sql时间设置,即执行时间大于200毫秒的都是慢sql
        statFilter.setSlowSqlMillis(30);
        statFilter.setLogSlowSql(true);
        statFilter.setMergeSql(true);
        return statFilter;
    }

    /**
     * @return
     * @description 配置日志拦截器
     */
    @Bean(name = "logFilter")
    public Slf4jLogFilter logFilter() {
        Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
        slf4jLogFilter.setDataSourceLogEnabled(true);
        slf4jLogFilter.setStatementExecutableSqlLogEnable(true);
        return slf4jLogFilter;
    }

    @Bean
    @Primary
    @Qualifier("mainDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.main")
    DataSource mainConfig() throws SQLException {
        DruidDataSource build = DruidDataSourceBuilder.create().build();
        List<Filter> filters = new ArrayList<>();
        filters.add(statFilter());
        filters.add(logFilter());
        build.setProxyFilters(filters);
        return build;
    }
}