package com.lxc.config;

import com.lxc.entity.Book;
import com.lxc.entity.User;
import com.lxc.helper.CurrentUser;
import com.lxc.helper.UserManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CurrentUserMethodArgumentResolverTest {

    @InjectMocks
    private CurrentUserMethodArgumentResolver resolver;

    @Mock
    private UserManager UserManager;

    @Test
    public void supportsParameter_happyPath() {

        MethodParameter parameter = createParameter(User.class, true);
        assertThat(resolver.supportsParameter(parameter)).isTrue();
    }

    @Test
    public void supportsParameter_shouldBeFalse_ifClassWrong() {

        MethodParameter parameter = createParameter(Book.class, true);
        assertThat(resolver.supportsParameter(parameter)).isFalse();
    }

    @Test
    public void supportsParameter_shouldBeFalse_ifAnnotationWrong() {

        MethodParameter parameter = createParameter(User.class, false);
        assertThat(resolver.supportsParameter(parameter)).isFalse();
    }

    @Test
    public void supportsParameter_shouldBeFalse_ifBothWrong() {

        MethodParameter parameter = createParameter(Book.class, false);
        assertThat(resolver.supportsParameter(parameter)).isFalse();
    }

    @Test
    public void resolveArgument_happyPath() {

        MethodParameter parameter = createParameter(User.class, true);
        User User = mock(User.class);
        when(UserManager.getCurrentUser()).thenReturn(User);

        assertThat(resolver.resolveArgument(parameter, mock(ModelAndViewContainer.class),
                mock(NativeWebRequest.class), mock(WebDataBinderFactory.class)))
                .isEqualTo(User);
    }

    private MethodParameter createParameter(Class<?> className, boolean trueOrFalse) {

        MethodParameter parameter = mock(MethodParameter.class);
        doReturn(className).when(parameter).getParameterType();
        when(parameter.hasParameterAnnotation(CurrentUser.class)).thenReturn(trueOrFalse);
        return parameter;
    }
}
