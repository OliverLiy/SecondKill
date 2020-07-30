package com.sdxb.secondkill.mapper;

import com.sdxb.secondkill.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @ClassName: UserMapper
 * @Description: TODO
 * @Create by: Liyu
 * @Date: 2020/7/28 16:26
 */
@Mapper
public interface UserMapper {
    @Select("select * from user where user_name=#{username}")
    User SelectByName(@Param("username")String username);

    @Insert("insert into user(user_name,password) values(#{username},#{password})")
    int insertuser(@Param("username") String username, @Param("password") String password);
}
