package com.javaweb.service.common;

import com.javaweb.dao.common.AttachmentDao;
import com.javaweb.enums.StatusEnum;
import com.javaweb.pojo.Attachment;
import com.javaweb.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: project_parent
 * @description:
 * @author: YuKai Fan
 * @create: 2019-05-14 09:39
 **/
@Service
public class AttachmentServiceImpl implements AttachmentService{
    @Autowired
    private AttachmentDao attachmentDao;
    @Override
    public List<Attachment> findAll() {
        return attachmentDao.findAll();
    }

    @Override
    public List<Attachment> findByParam(Map<String, Object> map) {
        //查询条件
        Specification<Attachment> specifiaction = createSpecification(map);
        return attachmentDao.findAll(specifiaction);
    }

    @Override
    public Attachment findOneById(String id) {
        return attachmentDao.findOneById(id, StatusEnum.Normal.getValue());
    }

    @Override
    public Attachment findBySha1(String sha1) {
        return attachmentDao.findByAttach_sha1AndStatus(sha1, StatusEnum.Normal.getValue());
    }

    @Override
    public Attachment add(Attachment attachment) {
        return attachmentDao.save(attachment);
    }

    @Override
    public Attachment update(Attachment attachment) {
        //解决Spring Data Jpa部分数据更新问题
        Attachment preAttachment = attachmentDao.findById(attachment.getId()).get();
        BeanUtils.copyProperties(attachment, preAttachment, BeanUtil.getNullPropNames(attachment));//将role中非null的值，copy到preAttachment中
        return attachmentDao.save(attachment);
    }

    @Override
    public void delete(String id) {
        attachmentDao.deleteById(id);
    }

    @Override
    public Page<Attachment> pageQuery(Map<String, Object> searchMap, int page, int size) {
        //封装一个分页对象
        Pageable pageable = PageRequest.of(page-1, size);
        Specification<Attachment> specification = createSpecification(searchMap);
        return attachmentDao.findAll(specification, pageable);
    }

    @Override
    public void delBatch(Map<String, Object> idsMap) {
        if(!CollectionUtils.isEmpty(idsMap)) {
            String[] ids = idsMap.get("ids").toString().split(",");
            List<String> list = CollectionUtils.arrayToList(ids);
            attachmentDao.delBatch(list);
        }
    }

    /**
     * 动态条件构建
     * @param searchMap
     * @return
     */
    private Specification<Attachment> createSpecification(Map searchMap) {

        return new Specification<Attachment>() {

            @Override
            public Predicate toPredicate(Root<Attachment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // 附件标识
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.equal(root.get("id").as(String.class), (String) searchMap.get("id")));
                }
                // 文件md5
                if (searchMap.get("attach_md5") != null && !"".equals(searchMap.get("attach_md5"))) {
                    predicateList.add(cb.equal(root.get("attach_md5").as(String.class), (String) searchMap.get("attach_md5")));
                }
                // 文件SHA1值
                if (searchMap.get("attach_sha1") != null && !"".equals(searchMap.get("attach_sha1"))) {
                    predicateList.add(cb.equal(root.get("attach_sha1").as(String.class), "%" + (String) searchMap.get("attach_sha1") + "%"));
                }
                // 文件原名
                if (searchMap.get("attach_origin_title") != null && !"".equals(searchMap.get("attach_origin_title"))) {
                    predicateList.add(cb.like(root.get("attach_origin_title").as(String.class), "%" + (String) searchMap.get("attach_origin_title") + "%"));
                }
                // 附件属性，例身份证证明
                if (searchMap.get("attach_utily") != null && !"".equals(searchMap.get("attach_utily"))) {
                    predicateList.add(cb.like(root.get("attach_utily").as(String.class), "%" + (String) searchMap.get("attach_utily") + "%"));
                }
                // 文件名
                if (searchMap.get("attach_name") != null && !"".equals(searchMap.get("attach_name"))) {
                    predicateList.add(cb.like(root.get("attach_name").as(String.class), "%" + (String) searchMap.get("attach_name") + "%"));
                }
                // 文件后缀
                if (searchMap.get("attach_postfix") != null && !"".equals(searchMap.get("attach_postfix"))) {
                    predicateList.add(cb.equal(root.get("attach_postfix").as(String.class), (String) searchMap.get("attach_postfix")));
                }
                // 附件
                if (searchMap.get("attachment") != null && !"".equals(searchMap.get("attachment"))) {
                    predicateList.add(cb.like(root.get("attachment").as(String.class), "%" + (String) searchMap.get("attachment") + "%"));
                }
                // 备注
                if (searchMap.get("remark") != null && !"".equals(searchMap.get("remark"))) {
                    predicateList.add(cb.like(root.get("remark").as(String.class), "%" + (String) searchMap.get("remark") + "%"));
                }
                // 创建人
                if (searchMap.get("create_user_id") != null && !"".equals(searchMap.get("create_user_id"))) {
                    predicateList.add(cb.like(root.get("create_user_id").as(String.class), "%" + (String) searchMap.get("create_user_id") + "%"));
                }
                // 更新人
                if (searchMap.get("update_user_id") != null && !"".equals(searchMap.get("update_user_id"))) {
                    predicateList.add(cb.like(root.get("update_user_id").as(String.class), "%" + (String) searchMap.get("update_user_id") + "%"));
                }
                // 文件路径
                if (searchMap.get("attach_path") != null && !"".equals(searchMap.get("attach_path"))) {
                    predicateList.add(cb.equal(root.get("attach_path").as(String.class), (String) searchMap.get("attach_path")));
                }

                //文件状态为1
                predicateList.add(cb.equal(root.get("status"), 1));

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };
    }
}