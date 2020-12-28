package com.dataworks.eventsubscriber.adapter;

import com.dataworks.eventsubscriber.model.dao.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserToUserDetailsAdapterTest {
    @Mock
    User user;
    @InjectMocks
    UserToUserDetailsAdapter userToUserDetailsAdapter;

    @Test
    void getAuthorities() {
        //given
        var roles = "ROLE_ADMIN,ROLE_USER";
        var expectedRole1 = "ROLE_ADMIN";
        var expectedRole2 = "ROLE_USER";
        //when
        when(user.getRole()).thenReturn(roles);
        //then
        var result = userToUserDetailsAdapter.getAuthorities();
        assertThat(result).isInstanceOf(Collection.class);
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.toArray()[0].toString()).isEqualTo(expectedRole1);
        assertThat(result.toArray()[1].toString()).isEqualTo(expectedRole2);
    }

    @Test
    void getPassword() {
        //given
        var returnPassword = "123456";

        //when
        when(user.getPassword()).thenReturn(returnPassword);

        //then
        var result = userToUserDetailsAdapter.getPassword();
        assertThat(result).isEqualTo(returnPassword);
    }

    @Test
    void getUsername() {
        //given
        var returnEmail = "ricky@hr.nl";
        //when
        when(user.getEmail()).thenReturn(returnEmail);
        //then
        var result = userToUserDetailsAdapter.getUsername();
        assertThat(result).isEqualTo(returnEmail);
    }

    @Test
    void isAccountNonExpired() {
        //given
        var returnNonExpired = true;
        //when
        //then
        var result = userToUserDetailsAdapter.isAccountNonExpired();
        assertThat(result).isEqualTo(returnNonExpired);
    }

    @Test
    void isAccountNonLocked() {
        //given
        var returnNonLocked = true;
        //when
        //then
        var result = userToUserDetailsAdapter.isAccountNonLocked();
        assertThat(result).isEqualTo(returnNonLocked);
    }

    @Test
    void isCredentialsNonExpired() {
        //given
        var returnNonExpired = true;
        //when
        //then
        var result = userToUserDetailsAdapter.isAccountNonExpired();
        assertThat(result).isEqualTo(returnNonExpired);
    }

    @Test
    void isEnabled() {
        //given
        var isVerified = true;
        //when
        when(user.isEmailVerified()).thenReturn(isVerified);
        //then
        var result = userToUserDetailsAdapter.isEnabled();
        assertThat(result).isEqualTo(isVerified);
    }

    @Test
    void isDisabled() {
        //given
        var isVerified = false;
        //when
        when(user.isEmailVerified()).thenReturn(isVerified);
        //then
        var result = userToUserDetailsAdapter.isEnabled();
        assertThat(result).isEqualTo(isVerified);
    }
}