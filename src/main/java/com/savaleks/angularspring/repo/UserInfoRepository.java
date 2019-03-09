package com.savaleks.angularspring.repo;

import com.savaleks.angularspring.dto.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    public UserInfo findByUsername(String username);
}
