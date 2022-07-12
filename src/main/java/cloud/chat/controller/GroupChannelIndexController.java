package cloud.chat.controller;

import cloud.chat.common.NotNull;
import cloud.chat.common.Result;
import cloud.chat.data.model.GroupChannelIndex;
import cloud.chat.service.GroupChannelIndexService;
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
@Api(tags = "群组索引")
@RestController
@RequestMapping("/api/v1/group/channel/index")
public class GroupChannelIndexController {

    @Autowired
    private GroupChannelIndexService groupChannelIndexService;

    @ApiOperation("新增")
    @PostMapping("/create")
    public Result create(@RequestBody GroupChannelIndex groupChannelIndex){
        return groupChannelIndexService.insert(groupChannelIndex);
    }

    @ApiOperation("批量新增")
    @PostMapping("/create/batch")
    public Result createBatch(@RequestBody List<GroupChannelIndex> groupChannelIndexList){
        return groupChannelIndexService.insertBatch(groupChannelIndexList);
    }

    @ApiOperation("删除")
    @NotNull(field = "id", name = "id", statusCode = 701)
    @PostMapping("/delete")
    public Result delete(@RequestBody GroupChannelIndex groupChannelIndex) {
        return groupChannelIndexService.delete(groupChannelIndex);
    }

    @ApiOperation("批量删除")
    @PostMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Long> idList) {
        return groupChannelIndexService.destroyBatch(idList);
    }

    @ApiOperation("修改")
    @NotNull(field = "id", name = "id", statusCode = 701)
    @PostMapping("/update")
    public Result update(@RequestBody GroupChannelIndex groupChannelIndex) {
        return groupChannelIndexService.update(groupChannelIndex);
    }

    @ApiOperation("批量修改")
    @PostMapping("/update/batch")
    public Result updateBatch(@RequestBody List<GroupChannelIndex> groupChannelIndexList) {
        return groupChannelIndexService.updateBatch(groupChannelIndexList);
    }

    @ApiOperation("查询")
    @NotNull(field = "id", name = "id", statusCode = 701)
    @PostMapping("/get")
    public Result get(@RequestBody GroupChannelIndex groupChannelIndex) {
        return groupChannelIndexService.selectOne(groupChannelIndex);
    }

    @ApiOperation("所有")
    @PostMapping("/find/all")
    public Result findAll() {
        GroupChannelIndex groupChannelIndex = new GroupChannelIndex();
        groupChannelIndex.setFlag(1);
        return groupChannelIndexService.select(groupChannelIndex);
    }


}
