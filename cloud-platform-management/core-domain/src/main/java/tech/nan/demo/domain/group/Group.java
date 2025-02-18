package tech.nan.demo.domain.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Group {

    private Long groupId;

    private String groupName;

    private String description;

    private Long creatorId;

    private Long parent;

    private String path;

    /**
     * 0：组织root组
     * 1：子组
     */
    private Integer type;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public List<Long> analysisPath() {
        String[] split = path.split("\\|");
        return Arrays.stream(split).map(Long::parseLong).collect(Collectors.toList());
    }
}
