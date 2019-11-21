package com.lxc.config;

import com.lxc.shiro.MyRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        shiroFilterFactoryBean.setLoginUrl("/login");
        Map<String, String> filterMap = new LinkedHashMap<>();

        //无需认证即可访问
        filterMap.put("/resources/**", "anon");
        filterMap.put("/doLogin", "anon");
        filterMap.put("/register", "anon");
        filterMap.put("/doRegister", "anon");
        filterMap.put("/logout", "logout");

        //只有管理员可访问的页面
        filterMap.put("/admin/**", "authc,roles[ADMIN]");

        //登录用户可访问
        filterMap.put("/**", "authc");

        shiroFilterFactoryBean.setUnauthorizedUrl("/user/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("myRealm") MyRealm myRealm) {

        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(myRealm);
        return defaultWebSecurityManager;
    }

    @Bean(name = "myRealm")
    public MyRealm getRealm() {
        return new MyRealm();
    }
}
