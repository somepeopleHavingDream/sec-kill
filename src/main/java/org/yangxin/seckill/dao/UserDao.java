package org.yangxin.seckill.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.yangxin.seckill.domain.User;

/**
 * @author yangxin
 * 2022/06/04 01:31
 */
@SuppressWarnings({"AlibabaCommentsMustBeJavadocFormat", "AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc", "UnusedReturnValue"})
@Mapper
public interface UserDao {

    //@Select("select * from user where id = #{id}")
    User getById(@Param("id") int id);

    @Insert("insert into user(id, name)values(#{id}, #{name})")
    int insert(User user);
}
