package websocket.chat.database;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * Database configuration
 * Date: 2015-01-04
 *
 * @author wangzhonglin
 */
@Configuration
@MapperScan(basePackages = {"websocket.chat"}, annotationClass = Dao.class)
public class DatabaseConfig implements EnvironmentAware {
    private static Logger LOG = LoggerFactory.getLogger(DatabaseConfig.class);
    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(Environment evn) {
        this.propertyResolver = new RelaxedPropertyResolver(evn, "druid.datasource.");
    }

    @Bean
    public DataSource dataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(propertyResolver.getProperty("driverClass"));
        druidDataSource.setUrl(propertyResolver.getProperty("jdbcUrl"));
        druidDataSource.setUsername(propertyResolver.getProperty("username"));
        druidDataSource.setPassword(propertyResolver.getProperty("password"));
        return druidDataSource;
    }

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactoryBean sqlSessionFactory() {
        try {
            SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
            sqlSessionFactoryBean.setDataSource(dataSource());
            sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                    .getResources(propertyResolver.getProperty("mapperLocations")));
            return sqlSessionFactoryBean;
        } catch (IOException e) {
            LOG.error("Could not configure mybatis session factory.", e);
            return null;
        }
    }
}
