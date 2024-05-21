//package com.batch2.batch2.all;
//
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.jdbc.datasource.init.DataSourceInitializer;
//import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
//
//import javax.sql.DataSource;
//
//@Configuration
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class SchemaInitializer {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @PostConstruct()
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public void itemDataSourceInitializer() {
//        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
//        resourceDatabasePopulator.addScript(new ClassPathResource("schema/schema.sql"));
//        resourceDatabasePopulator.addScript(new ClassPathResource("schema/batch-schema.sql"));
//        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
//        dataSourceInitializer.setDataSource(dataSource);
//        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
//        dataSourceInitializer.afterPropertiesSet();
//    }
//}
