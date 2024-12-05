package tech.nan.demo.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import tech.nan.demo.domain.group.Group;
import tech.nan.demo.domain.organization.Organization;
import tech.nan.demo.domain.terminal.Terminal;
import tech.nan.demo.domain.user.User;
import tech.nan.demo.mysql.DO.GroupDO;
import tech.nan.demo.mysql.DO.OrganizationDO;
import tech.nan.demo.mysql.DO.TerminalDO;
import tech.nan.demo.mysql.DO.UserDO;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CommonConverter {

    Terminal convert2Terminal(TerminalDO terminalDO);

    List<Terminal> convert2Terminals(List<TerminalDO> terminalDOs);

    GroupDO convert2GroupDO(Group group);

    Group convert2Group(GroupDO groupDO);

    List<Group> convert2Groups(List<GroupDO> groupDOS);

    OrganizationDO convert2OrgDO(Organization organization);

}
