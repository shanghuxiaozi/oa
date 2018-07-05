package com.oa;

//import javax.servlet.DispatcherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
//import org.springframework.context.annotation.Bean;
//import com.friends.common.filter.XssFilter;
//import org.springframework.util.AlternativeJdkIdGenerator;

/**
 * 主应用
 * 添加组件尽量采用注解的方式
 * 名称：TransferApplication.java<br>
 * 描述：<br>
 * 类型：JAVA<br>
 * 最近修改时间: 2017年5月5日 下午4:27:32 <br>
 * @since  2017年8月21日
 * @authour lzl
 */
@ServletComponentScan
@SpringBootApplication
                   
/*@ImportResource("classpath:dubbo-consumer.xml")*/
public class OaApplication extends SpringBootServletInitializer{
	private static Logger logger=LoggerFactory.getLogger(OaApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(OaApplication.class, args);
		logger.info("start ok");
	}

	/*
	*  账号：sys_admin
	*  密码：System123
	*/
	
/*	@Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }*/
}


