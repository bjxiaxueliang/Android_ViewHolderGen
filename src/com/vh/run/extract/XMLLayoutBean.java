package com.vh.run.extract;

import com.vh.run.model.AdapterView;
import com.vh.run.model.CustomView;
import com.vh.run.model.Fragment;
import com.vh.run.model.ImageView;
import com.vh.run.model.Include;
import com.vh.run.model.Layout;
import com.vh.run.model.RadioGroup;
import com.vh.run.model.TextView;

import java.util.ArrayList;

/**
 * 各种View列表
 */
public class XMLLayoutBean {

    // XML文件名称
    private String name;
    
    // 格式化的name(去掉"_"并把 首字母大写)
    private String formalName;

    private ArrayList<TextView> texts = new ArrayList<TextView>();

    private ArrayList<Layout> layouts = new ArrayList<Layout>();

    private ArrayList<Include> includes = new ArrayList<Include>();

    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    private ArrayList<AdapterView> adapterviews = new ArrayList<AdapterView>();

    private ArrayList<CustomView> customViews = new ArrayList<CustomView>();

    private ArrayList<ImageView> images = new ArrayList<ImageView>();

    private ArrayList<RadioGroup> rgroup = new ArrayList<RadioGroup>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        //
        setFormalName(FormalNameUtil.formalize(name)+"VH");
    }

    public String getFormalName() {
        return formalName;
    }

    public void setFormalName(String formalName) {
        this.formalName = formalName;
    }
    
    
    public ArrayList<TextView> getTexts() {
        return texts;
    }

    public void setTexts(ArrayList<TextView> texts) {
        this.texts = texts;
    }

    public ArrayList<Layout> getLayouts() {
        return layouts;
    }

    public void setLayouts(ArrayList<Layout> layouts) {
        this.layouts = layouts;
    }

    public ArrayList<Include> getIncludes() {
        return includes;
    }

    public void setIncludes(ArrayList<Include> includes) {
        this.includes = includes;
    }

    public ArrayList<Fragment> getFragments() {
        return fragments;
    }

    public void setFragments(ArrayList<Fragment> fragments) {
        this.fragments = fragments;
    }

    public ArrayList<AdapterView> getAdapterviews() {
        return adapterviews;
    }

    public void setAdapterviews(ArrayList<AdapterView> adapterviews) {
        this.adapterviews = adapterviews;
    }

    public ArrayList<CustomView> getCustomViews() {
        return customViews;
    }

    public void setCustomViews(ArrayList<CustomView> customViews) {
        this.customViews = customViews;
    }

    public ArrayList<ImageView> getImages() {
        return images;
    }

    public void setImages(ArrayList<ImageView> images) {
        this.images = images;
    }

    public ArrayList<RadioGroup> getRgroup() {
        return rgroup;
    }

    public void setRgroup(ArrayList<RadioGroup> rgroup) {
        this.rgroup = rgroup;
    }

}
