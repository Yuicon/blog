package wang.penglei.user.mapper;

import model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yuicon
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    @Select("SELECT * FROM yuicon.user;")
    @Results({
            @Result(property = "createTime", column = "create_time"),
    })
    List<User> findAll();

    /**
     * 注册
     *
     * @param user 注册数据
     * @return 记录数
     */
    @Insert("INSERT INTO yuicon.user (username, email, password, createTime)" +
            " VALUES (#{user.username}, #{user.email}, #{user.password}, #{user.createTime})")
    int insert(@Param("user") User user);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户数据
     */
    @Select("SELECT * FROM yuicon.user WHERE username = #{username} AND isDelete = 0;")
    List<User> findByUsername(@Param("username") String username);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户数据
     */
    @Select("SELECT * FROM yuicon.user WHERE email = #{email} AND isDelete = 0;")
    User findByEmail(@Param("email") String email);

    /**
     * 根据用户id查询用户
     *
     * @param id 用户id
     * @return 用户数据
     */
    @Select("SELECT * FROM yuicon.user WHERE id = #{id} AND isDelete = 0;")
    User findById(@Param("id") int id);

}
