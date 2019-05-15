package com.javaweb.dao.common;

import com.javaweb.pojo.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface AttachmentDao extends JpaRepository<Attachment,String>,JpaSpecificationExecutor<Attachment>{

    /**
     * 自定义JPQL
     * @param ids
     */
    @Modifying
    @Transactional
    @Query("update Attachment set status = 0 where id in (?1)")
    void delBatch(List<String> ids);

    /**
     * 根据sha1获取文件
     * @param sha1
     * @param status
     * @return
     */
    @Query(value = "select * from tb_attachment where attach_sha1 = ? and status = ?", nativeQuery = true)
    Attachment findByAttach_sha1AndStatus(String sha1, Integer status);

    /**
     * 根据id获取文件
     * @param id
     * @return
     */
    @Query(value = "select * from tb_attachment where id = ? and status = ?", nativeQuery = true)
    Attachment findOneById(String id, Integer status);
}
