package wang.penglei.user.mapper;

import model.Account;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yuicon
 */
@Mapper
@Repository
public interface AccountMapper {

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    @Select("SELECT * FROM account;")
    List<Account> findAll();

    /**
     * 注册用户
     *
     * @param account 注册数据
     * @return 记录数
     */
    @Insert("INSERT INTO account (username, email, password, createAtIP)" +
            " VALUES (#{account.username}, #{account.email}, #{account.password}, #{account.createAtIP})")
    int insert(@Param("account") Account account);

    /**
     * 更新用户
     *
     * @param account 用户数据
     * @return 记录数
     */
    @Update("UPDATE account SET `password` = #{account.password}, `sex` = #{account.sex}," +
            " `loginTimes` = #{account.loginTimes}, `lastLoginAt` = #{account.lastLoginAt}," +
            " `lastLoginIP` = #{account.lastLoginIP},`state` = #{account.state} WHERE `id` = #{account.id}")
    int update(@Param("account") Account account);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户数据
     */
    @Select("SELECT * FROM account WHERE username = #{username};")
    Account findByUsername(@Param("username") String username);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户数据
     */
    @Select("SELECT * FROM account WHERE email = #{email};")
    Account findByEmail(@Param("email") String email);

    /**
     * 根据用户id查询用户
     *
     * @param id 用户id
     * @return 用户数据
     */
    @Select("SELECT * FROM account WHERE id = #{id};")
    Account findById(@Param("id") int id);

}
