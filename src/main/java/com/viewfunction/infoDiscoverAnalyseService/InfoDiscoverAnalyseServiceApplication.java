package com.viewfunction.infoDiscoverAnalyseService;

import com.infoDiscover.infoAnalyse.service.restful.infoDiscoverSpaceAnalyse.InfoDiscoverSpaceAnalyseService;
import com.infoDiscover.infoAnalyse.service.restful.infoDiscoverSpaceDataCRUD.InfoDiscoverSpaceDataCRUDService;
import com.infoDiscover.infoAnalyse.service.restful.systemManagement.SystemManagementService;
import com.infoDiscover.infoAnalyse.service.restful.typeDataStatisticsAnalyse.TypeDataStatisticsAnalyseService;
import com.infoDiscover.infoAnalyse.service.restful.typeInstanceAnalyse.TypeInstanceAnalyseService;
import com.viewfunction.infoDiscoverAnalyseService.util.ApplicationLauncherUtil;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.provider.JAXBElementProvider;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@ServletComponentScan(basePackages = { "com.infoDiscover.infoAnalyse"})
@ComponentScan(basePackages = { "com.infoDiscover.infoAnalyse"})
@EnableDiscoveryClient
public class InfoDiscoverAnalyseServiceApplication {

    @Autowired
    private Bus cxfBus;

    @Autowired
    private TypeInstanceAnalyseService typeInstanceAnalyseService;

    @Autowired
    private TypeDataStatisticsAnalyseService typeDataStatisticsAnalyseService;

    @Autowired
    private SystemManagementService systemManagementService;

    @Autowired
    private InfoDiscoverSpaceDataCRUDService infoDiscoverSpaceDataCRUDService;

    @Autowired
    private InfoDiscoverSpaceAnalyseService infoDiscoverSpaceAnalyseService;

    @Bean
    public JacksonJaxbJsonProvider jacksonJaxbJsonProvider() {
        return new JacksonJaxbJsonProvider();
    }

    @Bean
    public JAXBElementProvider jaxbElementProvider() {
        return new JAXBElementProvider();
    }

    @Bean
    public Server cxfRestFulServer() {
        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
        endpoint.setBus(cxfBus);
        endpoint.setAddress("/infoAnalyse");
        endpoint.setServiceBeans(Arrays.asList(typeInstanceAnalyseService,typeDataStatisticsAnalyseService,systemManagementService,infoDiscoverSpaceDataCRUDService,infoDiscoverSpaceAnalyseService));
        endpoint.setProviders(Arrays.asList(jacksonJaxbJsonProvider(), jaxbElementProvider()));
        //endpoint.setFeatures(Arrays.asList(new Swagger2Feature()));
        return endpoint.create();
    }

    public static void main(String[] args) {
		SpringApplication.run(InfoDiscoverAnalyseServiceApplication.class, args);
		ApplicationLauncherUtil.printApplicationConsoleBanner();
	}
}
