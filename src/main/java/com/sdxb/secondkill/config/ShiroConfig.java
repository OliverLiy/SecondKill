package com.sdxb.secondkill.config;

import com.sdxb.secondkill.service.CustomRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ShiroConfig
 * @Description: TODO shiro配置
 * @Create by: Liyu
 * @Date: 2020/7/28 16:32
 */
@Configuration
public class ShiroConfig {

    @Bean
    public CustomRealm customRealm(){
        return new CustomRealm();
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
        manager.setRealm(customRealm());
        return manager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager());
        bean.setLoginUrl("/login");
        bean.setUnauthorizedUrl("/unauth");
        Map<String,String> filterChainDefinitionMap = new HashMap<String, String>();
        filterChainDefinitionMap.put("/login","anon");
        filterChainDefinitionMap.put("/register","anon");
        filterChainDefinitionMap.put("/item/*","authc");
        filterChainDefinitionMap.put("/kill/execute/*","authc");
        filterChainDefinitionMap.put("/**","authc");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return bean;
    }
}
