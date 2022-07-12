package cloud.chat.controller;

import cloud.chat.common.NotNull;
import cloud.chat.common.Result;
import cloud.chat.data.model.MsgRule;
import cloud.chat.service.MsgRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author gx
 */
@Api(tags = "消息管理")
@RestController
@RequestMapping("/api/v1/msg/rule")
public class MsgRuleController {

    @Autowired
    private MsgRuleService msgRuleService;

    @ApiOperation("新增")
    @NotNull(field = "msgKey", name = "key", statusCode = 701)
    @NotNull(field = "msgValue", name = "value", statusCode = 702)
    @PostMapping("/create")
    public Result create(@RequestBody MsgRule msgRule){
        return msgRuleService.insert(msgRule);
    }

    @ApiOperation("批量新增")
    @PostMapping("/create/batch")
    public Result createBatch(@RequestBody List<MsgRule> msgRuleList){
        return msgRuleService.insertBatch(msgRuleList);
    }

    @ApiOperation("删除")
    @NotNull(field = "id", name = "id", statusCode = 701)
    @PostMapping("/delete")
    public Result delete(@RequestBody MsgRule msgRule) {
        return msgRuleService.delete(msgRule);
    }

    @ApiOperation("批量删除")
    @PostMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Long> idList) {
        return msgRuleService.destroyBatch(idList);
    }

    @ApiOperation("修改")
    @NotNull(field = "id", name = "id", statusCode = 701)
    @PostMapping("/update")
    public Result update(@RequestBody MsgRule msgRule) {
        return msgRuleService.update(msgRule);
    }

    @ApiOperation("批量修改")
    @PostMapping("/update/batch")
    public Result updateBatch(@RequestBody List<MsgRule> msgRuleList) {
        return msgRuleService.updateBatch(msgRuleList);
    }

    @ApiOperation("查询")
    @NotNull(field = "id", name = "id", statusCode = 701)
    @PostMapping("/get")
    public Result get(@RequestBody MsgRule msgRule) {
        return msgRuleService.selectOne(msgRule);
    }

    @ApiOperation("所有")
    @PostMapping("/find/all")
    public Result findAll() {
        MsgRule msgRule = new MsgRule();
        msgRule.setFlag(1);
        return msgRuleService.select(msgRule);
    }


}
