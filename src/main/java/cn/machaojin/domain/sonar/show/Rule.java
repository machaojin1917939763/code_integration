package cn.machaojin.domain.sonar.show;

import cn.machaojin.domain.sonar.search.Impact;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author Ma Chaojin
 * @since 2024-05-07 16:55
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rule {
    private String key;
    private String repo;
    private String name;
    private Date createdAt;
    private String htmlDesc;
    private String mdDesc;
    private String severity;
    private String status;
    private boolean isTemplate;
    private List<String> tags;
    private List<String> sysTags;
    private String lang;
    private String langName;
    private List<Object> params;
    private String defaultDebtRemFnType;
    private String debtRemFnType;
    private String type;
    private String defaultRemFnType;
    private String defaultRemFnBaseEffort;
    private String remFnType;
    private String remFnBaseEffort;
    private boolean remFnOverloaded;
    private String scope;
    private boolean isExternal;
    private List<DescriptionSection> descriptionSections;
    private List<String> educationPrinciples;
    private Date updatedAt;
    private String cleanCodeAttribute;
    private String cleanCodeAttributeCategory;
    private List<Impact> impacts;
}
