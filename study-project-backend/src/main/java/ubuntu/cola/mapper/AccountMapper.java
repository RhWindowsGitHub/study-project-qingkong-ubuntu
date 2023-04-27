package ubuntu.cola.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import ubuntu.cola.entity.pojo.Account;

/**
 * @author Cola_Ubuntu
 * @name AccountMapper
 * @DATE 2023/4/27 下午5:44
 * @description AccountMapper
 */
@Mapper
public interface AccountMapper {

    @Select("SELECT * FROM sys_account WHERE username=#{text} OR email=#{text}")
    Account findAccountByUsernameOrEmail(String text);
}
