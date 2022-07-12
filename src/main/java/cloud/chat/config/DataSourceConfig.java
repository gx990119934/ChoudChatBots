package cloud.chat.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.HashMap;
import java.util.Map;

@Configuration
@MapperScan(basePackages = {"cloud.chat.data.mapper"}, sqlSessionFactoryRef = "botSqlSessionFactory")
public class DataSourceConfig {

    @Value("${application.datasource.url}")
    private String url;

    @Value("${application.datasource.username}")
    private String username;

    @Value("${application.datasource.password}")
    private String password;

    @Bean(name = "botDataSource")
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(getDriverClassName(url));
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(8);
        dataSource.setMinIdle(16);
        dataSource.setMaxActive(32);
        dataSource.setMaxWait(30000);

        dataSource.setValidationQuery("SELECT 1 FROM DUAL");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setMinEvictableIdleTimeMillis(1800000);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
        return dataSource;
    }
    private String getDriverClassName(String url){
        Map<String,String> driverMap = new HashMap<>() ;
        driverMap.put("mysql","com.mysql.cj.jdbc.Driver") ;
        driverMap.put("oracle","oracle.jdbc.OracleDriver") ;
        return driverMap.get(url.split(":")[1]) ;
    }

    @Bean(name = "botSqlSessionFactory")
    public SqlSessionFactory configBusSqlSessionFactory(@Qualifier("botDataSource") DruidDataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        SqlSessionFactory sqlSessionFactory = sessionFactory.getObject();
        sqlSessionFactory.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
        return sqlSessionFactory;
    }

    @Bean(name = "botSessionTemplate")
    public SqlSessionTemplate configBusSessionTemplate(@Qualifier("botSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
