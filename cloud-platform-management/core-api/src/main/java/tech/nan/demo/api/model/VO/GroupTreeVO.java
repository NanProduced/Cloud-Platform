package tech.nan.demo.api.model.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupTreeVO {

    private Long groupId;

    private String groupName;

    private Long parent;

    private String path;

    private String hierarchy;

    private List<GroupTreeVO> subGroups;

}
