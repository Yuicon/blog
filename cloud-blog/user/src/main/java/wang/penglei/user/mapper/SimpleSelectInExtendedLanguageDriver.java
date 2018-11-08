package wang.penglei.user.mapper;

import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yuicon
 * 实现 in 操作
 *
 * @example
 *     @Lang(SimpleSelectInExtendedLanguageDriver.class)
 *     @Select("SELECT * FROM ngdc.energy_device where code in (#{codes});")
 *     List<Device> findInCode(@Param("codes") Set<Integer> codes);
 */
public class SimpleSelectInExtendedLanguageDriver extends XMLLanguageDriver implements LanguageDriver {

    private final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)}\\)");

    @Override
    public SqlSource createSqlSource(Configuration configuration,
                                     String script, Class<?> parameterType) {

        Matcher matcher = inPattern.matcher(script);
        if (matcher.find()) {
            script = matcher.replaceAll("(<foreach collection=\"$1\" item=\"__item\" separator=\",\" >#{__item}</foreach>)");
        }

        script = "<script>" + script + "</script>";
        return super.createSqlSource(configuration, script, parameterType);
    }

}
