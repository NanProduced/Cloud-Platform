package tech.nan.demo.application.model.VO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GroupTreeVO {

    private Long groupId;

    private String groupName;

    private List<GroupTreeVO> subGroups;

}
