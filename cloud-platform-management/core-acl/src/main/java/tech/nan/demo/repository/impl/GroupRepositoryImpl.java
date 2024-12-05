package tech.nan.demo.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tech.nan.demo.converter.CommonConverter;
import tech.nan.demo.domain.group.Group;
import tech.nan.demo.mysql.DO.GroupDO;
import tech.nan.demo.mysql.mapper.GroupMapper;
import tech.nan.demo.repository.GroupRepository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class GroupRepositoryImpl extends ServiceImpl<GroupMapper, GroupDO> implements GroupRepository {

    @Autowired
    private CommonConverter converter;

    private final String PATH_FAN = "|";

    @Override
    public Group createOrgRootGroup(Group group) {
        GroupDO groupDO = converter.convert2GroupDO(group);
        this.save(groupDO);
        groupDO.setPath("0|" + groupDO.getGroupId());
        this.updateById(groupDO);
        return converter.convert2Group(groupDO);
    }

    @Override
    public Group createNormalGroup(Group group) {
        GroupDO groupDO = converter.convert2GroupDO(group);
        this.save(groupDO);
        GroupDO parent = this.getById(group.getParent());
        groupDO.setPath(parent.getPath() + PATH_FAN + groupDO.getGroupId());
        this.updateById(groupDO);
        return converter.convert2Group(groupDO);
    }

    @Override
    public Group getGroupById(Long groupId) {
        GroupDO groupDO = this.getById(groupId);
        return converter.convert2Group(groupDO);
    }

    @Override
    public String getGroupPath(Long groupId) {
        return this.lambdaQuery()
                .select(GroupDO::getPath)
                .eq(GroupDO::getGroupId, groupId)
                .oneOpt()
                .map(GroupDO::getPath)
                .orElse(null);
    }

    @Override
    public List<Group> getAllSubGroupById(Long groupId) {
        List<Group> result = new ArrayList<>();
        Group parent = getGroupById(groupId);
        result.add(parent);
        // 匹配以父组 path 开头的子组
        List<GroupDO> subGroups = this.lambdaQuery()
                .likeRight(GroupDO::getPath, parent.getPath() + "|")
                .select(GroupDO::getGroupId, GroupDO::getGroupName, GroupDO::getParent)
                .list();
        result.addAll(converter.convert2Groups(subGroups));
        return result;
    }
}

