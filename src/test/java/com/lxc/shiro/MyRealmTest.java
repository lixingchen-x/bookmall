package com.lxc.shiro;

import com.lxc.entity.Role;
import com.lxc.entity.User;
import com.lxc.service.UserService;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class MyRealmTest {

    @InjectMocks
    private MyRealm myRealm;

    @Mock
    private UserService userService;

    @Test
    public void doGetAuthorizationInfo_happyPath() {

        PrincipalCollection principalCollection = mock(PrincipalCollection.class);
        User user = User.builder().id(1).role(new Role(Role.ADMIN_ROLE_CODE)).build();
        when(principalCollection.getPrimaryPrincipal()).thenReturn(user);

        AuthorizationInfo authorizationInfo = myRealm.doGetAuthorizationInfo(principalCollection);

        assertThat(authorizationInfo.getRoles()).contains("ADMIN");
    }

    @Test
    public void doGetAuthenticationInfo_happyPath() {

        User user = User.builder().id(1).username("a").password("123456").build();
        AuthenticationToken authenticationToken = mock(AuthenticationToken.class);
        when(authenticationToken.getPrincipal()).thenReturn("a");
        when(userService.findByUsername("a")).thenReturn(user);

        assertThat(myRealm.doGetAuthenticationInfo(authenticationToken).getPrincipals().getPrimaryPrincipal())
                .isEqualTo(user);
    }

    @Test
    public void doGetAuthenticationInfo_shouldReturnNull_ifUserDoesNotExist() {

        AuthenticationToken authenticationToken = mock(AuthenticationToken.class);
        when(authenticationToken.getPrincipal()).thenReturn("a");
        when(userService.findByUsername("a")).thenReturn(null);

        assertThat(myRealm.doGetAuthenticationInfo(authenticationToken)).isNull();
    }
}
