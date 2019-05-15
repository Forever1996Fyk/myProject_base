package com.javaweb.service.common;

import com.javaweb.pojo.Attachment;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * @program: project_parent
 * @description:
 * @author: YuKai Fan
 * @create: 2019-05-14 09:38
 **/
public interface AttachmentService {
    /**
     * 获取全部文件
     * @return
     */
    List<Attachment> findAll();

    /**
     * 根据参数获取文件
     * @param map
     * @return
     */
    List<Attachment> findByParam(Map<String, Object> map);

    /**
     * 根据id获取文件
     * @param id
     * @return
     */
    Attachment findOneById(String id);

    /**
     * 根据sha1查询文件
     * @param sha1
     * @return
     */
    Attachment findBySha1(String sha1);

    /**
     * 新增文件
     * @return
     */
    Attachment add(Attachment attachment);

    /**
     * 更新文件
     * @param attachment
     * @return
     */
    Attachment update(Attachment attachment);

    /**
     * 删除文件
     * @param id
     * @return
     */
    void delete(String id);

    /**
     * 分页+ 条件查询
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    Page<Attachment> pageQuery(Map<String, Object> searchMap, int page, int size);

    /**
     * 批量删除
     * @param idsMap
     */
    void delBatch(Map<String, Object> idsMap);
}