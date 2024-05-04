package cn.machaojin.database;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
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