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
    @Select("SELECT * FROM ngdc.user;")
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
    @Insert("INSERT INTO ngdc.user (username, email, password, create_time)" +
            " VALUES (#{user.username}, #{user.email}, #{user.password}, #{user.createTime})")
    int insert(@Param("user") User user);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户数据
     */
    @Select("SELECT * FROM ngdc.user WHERE username = #{username};")
    User findByUsername(@Param("username") String username);

    /**
     * 根据用户id查询用户
     *
     * @param id 用户id
     * @return 用户数据
     */
    @Select("SELECT * FROM ngdc.user WHERE id = #{id};")
    User findById(@Param("id") int id);

}
