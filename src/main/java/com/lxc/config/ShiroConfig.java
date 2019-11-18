package com.lxc.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
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
        //"anon"：无需认证即可访问
        filterMap.put("/resources/**", "anon");
        filterMap.put("/doLogin", "anon");
        filterMap.put("/register", "anon");
        filterMap.put("/doRegister", "anon");
        filterMap.put("/logout", "logout");

        //只有管理员可访问的页面
        filterMap.put("/book/add", "authc,roles[ADMIN]");
        filterMap.put("/book/update", "authc,roles[ADMIN]");
        filterMap.put("/book/withdraw", "authc,roles[ADMIN]");
        filterMap.put("/book/onSale", "authc,roles[ADMIN]");
        filterMap.put("/book/delete", "authc,roles[ADMIN]");
        filterMap.put("/user/add", "authc,roles[ADMIN]");
        filterMap.put("/user/users", "authc,roles[ADMIN]");
        filterMap.put("/user/delete", "authc,roles[ADMIN]");
        filterMap.put("/user/changeRole", "authc,roles[ADMIN]");
        filterMap.put("/book/upload", "authc,roles[ADMIN]");

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

    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
