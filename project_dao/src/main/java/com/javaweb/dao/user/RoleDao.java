package com.javaweb.dao.user;

import com.javaweb.pojo.Role;
import com.javaweb.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: project_parent
 * @description:
 * @author: YuKai Fan
 * @create: 2019-04-24 16:14
 **/
public interface RoleDao extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role>{

    /**
     * 自定义JPQL
     * @param ids
     */
    @Modifying
    @Transactional
    @Query("update Role set status = 2 where id in (?1)")
    void delBatch(List<String> ids);

}