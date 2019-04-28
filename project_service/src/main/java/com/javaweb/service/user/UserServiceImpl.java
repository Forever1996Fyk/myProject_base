package com.javaweb.service.user;

import com.javaweb.dao.user.UserDao;
import com.javaweb.pojo.User;
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
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User add(User user) {
        return userDao.save(user);
    }

    @Override
    public User update(User user) {
        //解决Spring Data Jpa部分数据更新问题
        User preUser = userDao.findById(user.getId()).get();
        BeanUtils.copyProperties(user, preUser, BeanUtil.getNullPropNames(user));//将user中非null的值，copy到preUser中
        return userDao.save(preUser);
    }

    @Override
    public void delete(String id) {
        userDao.deleteById(id);
    }

    @Override
    public Page<User> pageQuery(Map<String, Object> searchMap, int page, int size) {
        //封装一个分页对象
        Pageable pageable = PageRequest.of(page-1, size);
        //查询条件
        Specification<User> specifiaction = createSpecifiaction(searchMap);
        return userDao.findAll(specifiaction, pageable);
    }

    @Override
    public void delBatch(Map<String, Object> idsMap) {
        if(!CollectionUtils.isEmpty(idsMap)) {
            String[] ids = idsMap.get("ids").toString().split(",");
            List<String> list = CollectionUtils.arrayToList(ids);
            userDao.delBatch(list);
        }
    }

    /**
     * 构建条件
     * @param searchMap
     * @return
     */
    private Specification<User> createSpecifiaction(Map<String, Object> searchMap) {
        /**
         *
         * @param root：根对象，也就是把条件封装到哪个对象中.where 类名 = label.getId
         * @param criteriaQuery：封装的都是查询关键字.比如group by order by等(基本上用不到)
         * @param criteriaBuilder：用来封装条件对象的
         * @return
         */
        return new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //new一个集合存放所有的条件
                List<Predicate> predicateList = new ArrayList<>();
                //查询条件
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(criteriaBuilder.equal(root.get("id").as(String.class), searchMap.get("id")));
                }

                // 用户账号
                if (searchMap.get("account")!=null && !"".equals(searchMap.get("account"))) {
                    predicateList.add(criteriaBuilder.like(root.get("account").as(String.class), "%"+(String)searchMap.get("account")+"%")); //相当于 where account like
                }
                // 用户名称
                if (searchMap.get("user_name")!=null && !"".equals(searchMap.get("user_name"))) {
                    predicateList.add(criteriaBuilder.like(root.get("user_name").as(String.class), "%"+(String)searchMap.get("user_name")+"%"));
                }
                // 密码
                if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
                    predicateList.add(criteriaBuilder.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
                }
                // 用户昵称
                if (searchMap.get("nick_name")!=null && !"".equals(searchMap.get("nick_name"))) {
                    predicateList.add(criteriaBuilder.like(root.get("nick_name").as(String.class), "%"+(String)searchMap.get("nick_name")+"%"));
                }
                // 头像
                if (searchMap.get("user_icon")!=null && !"".equals(searchMap.get("user_icon"))) {
                    predicateList.add(criteriaBuilder.like(root.get("user_icon").as(String.class), "%"+(String)searchMap.get("user_icon")+"%"));
                }
                // 手机号
                if (searchMap.get("phone")!=null && !"".equals(searchMap.get("phone"))) {
                    predicateList.add(criteriaBuilder.like(root.get("phone").as(String.class), "%"+(String)searchMap.get("phone")+"%"));
                }
                // 邮箱
                if (searchMap.get("email")!=null && !"".equals(searchMap.get("email"))) {
                    predicateList.add(criteriaBuilder.like(root.get("email").as(String.class), "%"+(String)searchMap.get("email")+"%"));
                }
                // 省级
                if (searchMap.get("prov")!=null && !"".equals(searchMap.get("prov"))) {
                    predicateList.add(criteriaBuilder.like(root.get("prov").as(String.class), "%"+(String)searchMap.get("prov")+"%"));
                }
                // 地市级
                if (searchMap.get("city")!=null && !"".equals(searchMap.get("city"))) {
                    predicateList.add(criteriaBuilder.like(root.get("city").as(String.class), "%"+(String)searchMap.get("city")+"%"));
                }
                // 区县
                if (searchMap.get("dist")!=null && !"".equals(searchMap.get("dist"))) {
                    predicateList.add(criteriaBuilder.like(root.get("dist").as(String.class), "%"+(String)searchMap.get("dist")+"%"));
                }
                // 地址
                if (searchMap.get("address")!=null && !"".equals(searchMap.get("address"))) {
                    predicateList.add(criteriaBuilder.like(root.get("address").as(String.class), "%"+(String)searchMap.get("address")+"%"));
                }
                // 身份证号
                if (searchMap.get("idcard")!=null && !"".equals(searchMap.get("idcard"))) {
                    predicateList.add(criteriaBuilder.like(root.get("idcard").as(String.class), "%"+(String)searchMap.get("idcard")+"%"));
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