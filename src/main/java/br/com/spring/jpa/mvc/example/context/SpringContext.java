package br.com.spring.jpa.mvc.example.context;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Gabriel Francisco - gabfssilva@gmail.com
 */
@Configuration
@ComponentScan("br.com.spring.jpa.mvc")
@EnableTransactionManagement
public class SpringContext {

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();

        //Aqui vc muda essas configs para as específicas do MySQL
        dataSource.setUrl("jdbc:h2:mem:springExample;DB_CLOSE_DELAY=1000");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        dataSource.setDriverClassName("org.h2.Driver");

        dataSource.setMaxTotal(20); //É muito importante definir o tamanho máximo de conexões ativas da sua aplicação para o banco
        dataSource.setMaxConnLifetimeMillis(10000); //bem como o tempo máximo que uma conexão pode ficar aberta
        dataSource.setMaxWaitMillis(15000); //e quanto tempo será esperado por uma conexão, caso todas as 20 estejam ocupadas

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan("br.com.spring.jpa.mvc.example.entities"); //package das suas entitidades JPA

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter(); //aqui você fala que você está usando Hibernate como implementação do JPA
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setJpaProperties(additionalProperties());

        return entityManagerFactoryBean;
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop"); //criar e destruir o banco de dados a cada startup, útil em ambiente de teste e usando banco em memória
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
       // se usar MySQL: properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return properties;
    }

    //Todo método que você usar @Transactional estará em um contexto transacional, graças à esse método.
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
