package com.javaweb.service.user;

import com.javaweb.dao.user.PermissionDao;
import com.javaweb.pojo.Permission;
import com.javaweb.constant.StatusConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.javaweb.util.BeanUtil;

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
 * @create: 2019-04-24 16:31
 **/
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }

    @Override
    public Permission add(Permission permission) {
        return permissionDao.save(permission);
    }

    @Override
    public Permission update(Permission permission) {
        //解决Spring Data Jpa部分数据更新问题
        Permission prePermission = permissionDao.findById(permission.getId()).get();
        BeanUtils.copyProperties(permission, prePermission, BeanUtil.getNullPropNames(permission));//将role中非null的值，copy到preRole中
        return permissionDao.save(prePermission);
    }

    public Map<String, Object> findOne(String pid) {
        return permissionDao.findOneByPid(pid);
    }

    @Override
    public void delete(String id) {
        permissionDao.deleteById(id);
    }

    @Override
    public Page<Permission> pageQuery(Map<String, Object> searchMap, int page, int size) {
        //封装一个分页对象
        Pageable pageable = PageRequest.of(page-1, size);
        //查询条件
        Specification<Permission> specifiaction = createSpecifiaction(searchMap);
        Page<Permission> permissionPage = permissionDao.findAll(specifiaction, pageable);

        //根据pid查询上级目录
        for (Permission permission : permissionPage) {
            Map<String, Object> map = this.findOne(permission.getPid());
            permission.setpPermission(map);
        }
       return permissionPage;
    }

    @Override
    public void delBatch(Map<String, Object> idsMap) {
        if(!CollectionUtils.isEmpty(idsMap)) {
            String[] ids = idsMap.get("ids").toString().split(",");
            List<String> list = CollectionUtils.arrayToList(ids);
            permissionDao.delBatch(list);
        }
    }

    /**
     * 构建条件
     * @param searchMap
     * @return
     */
    private Specification<Permission> createSpecifiaction(Map<String, Object> searchMap) {
        /**
         *
         * @param root：根对象，也就是把条件封装到哪个对象中.where 类名 = label.getId
         * @param criteriaQuery：封装的都是查询关键字.比如group by order by等(基本上用不到)
         * @param criteriaBuilder：用来封装条件对象的
         * @return
         */
        return new Specification<Permission>() {
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //new一个集合存放所有的条件
                List<Predicate> predicateList = new ArrayList<>();
                //查询条件
                // 权限标识
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(criteriaBuilder.equal(root.get("id").as(String.class), (String)searchMap.get("id")));
                }
                // 权限名称
                if (searchMap.get("name")!=null && !"".equals(searchMap.get("name"))) {
                    predicateList.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+(String)searchMap.get("name")+"%"));
                }
                // 上级权限id
                if (searchMap.get("pid")!=null && !"".equals(searchMap.get("pid"))) {
                    predicateList.add(criteriaBuilder.like(root.get("pid").as(String.class), "%"+(String)searchMap.get("pid")+"%"));
                }
                // 等级
                if (searchMap.get("level")!=null && !"".equals(searchMap.get("level"))) {
                    predicateList.add(criteriaBuilder.equal(root.get("level").as(String.class), searchMap.get("level")));
                }
                // 备注
                if (searchMap.get("remark")!=null && !"".equals(searchMap.get("remark"))) {
                    predicateList.add(criteriaBuilder.like(root.get("remark").as(String.class), "%"+(String)searchMap.get("remark")+"%"));
                }
                // 创建人
                if (searchMap.get("create_user_id")!=null && !"".equals(searchMap.get("create_user_id"))) {
                    predicateList.add(criteriaBuilder.like(root.get("create_user_id").as(String.class), "%"+(String)searchMap.get("create_user_id")+"%"));
                }
                // 更新人
                if (searchMap.get("update_user_id")!=null && !"".equals(searchMap.get("update_user_id"))) {
                    predicateList.add(criteriaBuilder.like(root.get("update_user_id").as(String.class), "%"+(String)searchMap.get("update_user_id")+"%"));
                }

                //状态 默认查询没有删除的数据
                predicateList.add(criteriaBuilder.notEqual(root.get("status").as(String.class), StatusConstant.Delete.getValue()));

                //new一个数组作为最终返回值的条件
                Predicate[] predicate = new Predicate[predicateList.size()];
                //把list转为数组
                predicateList.toArray(predicate);

                return criteriaBuilder.and(predicate);//连接查询条件
            }
        };
    }

}