package wang.penglei.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import wang.penglei.user.model.User;

import java.util.List;

/**
 * @author Yuicon
 */
@Mapper
@Repository
public interface UserMapper {

    @Select("SELECT * FROM ngdc.user;")
    @Results({
            @Result(property = "createTime", column = "create_time"),
    })
    List<User> findAll();

}
