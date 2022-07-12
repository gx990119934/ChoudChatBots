package cloud.chat.data.mapper;

import cloud.chat.common.DbMapper;
import cloud.chat.data.model.Member;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

public interface MemberMapper extends DbMapper<Member> {
}