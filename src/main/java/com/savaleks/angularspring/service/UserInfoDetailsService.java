package com.savaleks.angularspring.service;

import com.savaleks.angularspring.dto.UserInfo;
import com.savaleks.angularspring.repo.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserInfoDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfo userInfo = userInfoRepository.findByUsername(username);
        if (userInfo == null){
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        // method create an instance and populates it with the user data returned from database.
        return new org.springframework.security.core.userdetails.User(
                userInfo.getUsername(), userInfo.getPassword(), getAuthorities(userInfo));
    }

    private Collection<GrantedAuthority> getAuthorities(UserInfo userInfo){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities = AuthorityUtils.createAuthorityList(userInfo.getRole());
        return authorities;
    }
}
