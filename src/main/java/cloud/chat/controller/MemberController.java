package cloud.chat.controller;

import cloud.chat.common.NotNull;
import cloud.chat.common.Result;
import cloud.chat.data.model.Member;
import cloud.chat.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gx
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation("新增")
    @NotNull(field = "name", name = "name", statusCode = 701)
    @NotNull(field = "password", name = "password", statusCode = 702)
    @PostMapping("/create")
    public Result create(@RequestBody Member member){
        return memberService.insert(member);
    }

    @ApiOperation("修改")
    @NotNull(field = "id", name = "id", statusCode = 701)
    @PostMapping("/update")
    public Result update(@RequestBody Member member){
        return null;
    }


}
