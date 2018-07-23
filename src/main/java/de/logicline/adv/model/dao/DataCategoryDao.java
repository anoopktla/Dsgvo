package de.logicline.adv.model.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ADV_DATA_CATEGORIES")
public class DataCategoryDao {

    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private  Long advId;

    private String categoryOfData;
    private String purposeOfCollection;
    private String categoryOfSubjects;

    public String getCategoryOfData() {
        return categoryOfData;
    }

    public void setCategoryOfData(String categoryOfData) {
        this.categoryOfData = categoryOfData;
    }

    public String getPurposeOfCollection() {
        return purposeOfCollection;
    }

    public void setPurposeOfCollection(String purposeOfCollection) {
        this.purposeOfCollection = purposeOfCollection;
    }

    public String getCategoryOfSubjects() {
        return categoryOfSubjects;
    }

    public void setCategoryOfSubjects(String categoryOfSubjects) {
        this.categoryOfSubjects = categoryOfSubjects;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdvId() {
        return advId;
    }

    public void setAdvId(Long advId) {
        this.advId = advId;
    }
}
