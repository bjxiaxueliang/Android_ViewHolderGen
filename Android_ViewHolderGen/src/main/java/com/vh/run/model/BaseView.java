package com.vh.run.model;

import com.vh.run.extract.FormalNameUtil;

public class BaseView {

    // View的Id
    private String id;
    // 格式化的Id(去掉"_"并把 首字母大写)
    private String formalId;
    // 例如 LinearLayout
    private String qualifiedclazz;


    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;

        setFormalId(FormalNameUtil.formalize(id));
    }

    public String getQualifiedclazz() {
        return qualifiedclazz;
    }

    public void setQualifiedclazz(String qualifiedclazz) {
        this.qualifiedclazz = qualifiedclazz;
    }

    public String getFormalId() {
        return formalId;
    }

    public void setFormalId(String formalId) {
        this.formalId = formalId;
    }

}
