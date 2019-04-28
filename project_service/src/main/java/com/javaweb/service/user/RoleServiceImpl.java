package com.javaweb.service.user;

import com.javaweb.dao.user.RoleDao;
import com.javaweb.pojo.Role;
import constant.StatusConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import util.BeanUtil;

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
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Role add(Role role) {
        return roleDao.save(role);
    }

    @Override
    public Role update(Role role) {
        //解决Spring Data Jpa部分数据更新问题
        Role preRole = roleDao.findById(role.getId()).get();
        BeanUtils.copyProperties(role, preRole, BeanUtil.getNullPropNames(role));//将role中非null的值，copy到preRole中
        return roleDao.save(preRole);
    }

    @Override
    public void delete(String id) {
        roleDao.deleteById(id);
    }

    @Override
    public Page<Role> pageQuery(Map<String, Object> searchMap, int page, int size) {
        //封装一个分页对象
        Pageable pageable = PageRequest.of(page-1, size);
        //查询条件
        Specification<Role> specifiaction = createSpecifiaction(searchMap);
        return roleDao.findAll(specifiaction, pageable);
    }

    @Override
    public void delBatch(Map<String, Object> idsMap) {
        if(!CollectionUtils.isEmpty(idsMap)) {
            String[] ids = idsMap.get("ids").toString().split(",");
            List<String> list = CollectionUtils.arrayToList(ids);
            roleDao.delBatch(list);
        }
    }

    /**
     * 构建条件
     * @param searchMap
     * @return
     */
    private Specification<Role> createSpecifiaction(Map<String, Object> searchMap) {
        /**
         *
         * @param root：根对象，也就是把条件封装到哪个对象中.where 类名 = label.getId
         * @param criteriaQuery：封装的都是查询关键字.比如group by order by等(基本上用不到)
         * @param criteriaBuilder：用来封装条件对象的
         * @return
         */
        return new Specification<Role>() {
            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //new一个集合存放所有的条件
                List<Predicate> predicateList = new ArrayList<>();
                //查询条件
                // 角色标识
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(criteriaBuilder.equal(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 角色名称
                if (searchMap.get("role_name")!=null && !"".equals(searchMap.get("role_name"))) {
                    predicateList.add(criteriaBuilder.like(root.get("role_name").as(String.class), "%"+(String)searchMap.get("role_name")+"%"));
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