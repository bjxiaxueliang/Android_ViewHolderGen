package com.vh.run.extract;

import com.vh.run.model.AdapterView;
import com.vh.run.model.BaseView;
import com.vh.run.model.CustomView;
import com.vh.run.model.Fragment;
import com.vh.run.model.ImageView;
import com.vh.run.model.Include;
import com.vh.run.model.Layout;
import com.vh.run.model.RadioGroup;
import com.vh.run.model.TextView;
import com.vh.run.utils.LogUtils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Arrays;
import java.util.List;

/**
 * 比对解析Layout文件
 */
public class XMLHandler extends DefaultHandler {

    private static final String TAG = "XMLHandler";

    //-------------------存放View数据的列表-----------------------
    public XMLLayoutBean xmll = new XMLLayoutBean();

    //--------------------比对的View数据----------------------
    private String[] AdapterViews = {"ListView", "GridView", "Spinner", "AdapterViewFlipper", "StackView"};

    private String[] ImageViews = {"ImageView", "ImageButton"};

    private String[] Fragments = {"Fragment", "DialogFragment", "ListFragment", "WebViewFragment", "fragment"};

    private String[] Layouts = {"LinearLayout", "RelativeLayout", "FrameLayout", "GridLayout"};

    private String[] Textviews = {"TextView", "EditText", "Button", "CheckBox", "RadioButton", "ToggleButton", "Switch", "AutoCompleteTextView"
            , "MultiAutoCompleteTextView", "CheckedTextView"};

    private String[] Radiogroups = {"RadioGroup"};


    /**
     * @param uri
     * @param localName
     * @param qualifiedName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) throws SAXException {

        LogUtils.d(TAG,"---startElement---");
        LogUtils.d(TAG,"uri: " + uri);
        LogUtils.d(TAG,"localName: " + localName);
        LogUtils.d(TAG,"qualifiedName: " + qualifiedName);

        // AdapterViews中查找qualifiedName
        if (binarySearch(Arrays.asList(AdapterViews), qualifiedName) >= 0) {
            handleAdapterView(uri, localName, qualifiedName, attributes);
        }
        // ImageViews中查找qualifiedName
        else if (binarySearch(Arrays.asList(ImageViews), qualifiedName) >= 0) {
            handleImageView(uri, localName, qualifiedName, attributes);
        }
        // Fragments中查找qualifiedName
        else if (binarySearch(Arrays.asList(Fragments), qualifiedName) >= 0) {
            handleFragment(uri, localName, qualifiedName, attributes);
        }
        // Layouts中查找qualifiedName
        else if (binarySearch(Arrays.asList(Layouts), qualifiedName) >= 0) {
            handleLayout(uri, localName, qualifiedName, attributes);
        }
        // Textviews中查找qualifiedName
        else if (binarySearch(Arrays.asList(Textviews), qualifiedName) >= 0) {
            handleTextView(uri, localName, qualifiedName, attributes);
        }
        // Radiogroups中查找qualifiedName
        else if (binarySearch(Arrays.asList(Radiogroups), qualifiedName) >= 0) {
            handleRadioGroup(uri, localName, qualifiedName, attributes);
        }
        // 查找include标签
        else if ("include".equals(qualifiedName)) {
            handleInclude(uri, localName, qualifiedName, attributes);
        }
        // 自定义View
        else {
            handleCustomTag(uri, localName, qualifiedName, attributes);
        }
    }

    /**
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
    }

    /**
     * @param uri
     * @param localName
     * @param qName      例如 LinearLayout
     * @param attributes
     */
    private void handleRadioGroup(String uri, String localName, String qName, Attributes attributes) {
        String idstr = attributes.getValue("android:id");
        if (idstr == null)
            return;

        BaseView base = new RadioGroup();
        base.setId(getId(idstr));
        base.setQualifiedclazz(qName);
        xmll.getRgroup().add((RadioGroup) base);

    }

    /**
     * @param uri
     * @param localName
     * @param qName      例如 LinearLayout
     * @param attributes
     */
    private void handleCustomTag(String uri, String localName, String qName, Attributes attributes) {
        String idstr = attributes.getValue("android:id");
        if (idstr == null)
            return;
        BaseView base = new CustomView();
        base.setId(getId(idstr));
        base.setQualifiedclazz(qName);
        xmll.getCustomViews().add((CustomView) base);
    }

    /**
     * @param uri
     * @param localName
     * @param qName      例如 LinearLayout
     * @param attributes
     */
    private void handleInclude(String uri, String localName, String qName, Attributes attributes) {
        String idstr = attributes.getValue("android:id");
        if (idstr == null)
            return;
        BaseView base = new Include();
        base.setId(getId(idstr));
        base.setQualifiedclazz(attributes.getValue("layout").replace("@layout/", ""));
        xmll.getIncludes().add((Include) base);
    }

    /**
     * list中查找 o
     *
     * @param list
     * @param o
     * @return 存在则为1，不存在则为-1
     */
    private int binarySearch(List list, Object o) {
        if (list == null)
            return -1;
        for (int i = 0; i < list.size(); i++) {
            Object o1 = list.get(i);
            if (o1.equals(o))
                return 1;
        }
        return -1;
    }

    /**
     * @param uri
     * @param localName
     * @param qName      例如 LinearLayout
     * @param attributes
     */
    private void handleTextView(String uri, String localName, String qName, Attributes attributes) {
        String idstr = attributes.getValue("android:id");
        if (idstr == null)
            return;
        BaseView base = new TextView();
        base.setId(getId(idstr));
        base.setQualifiedclazz(qName);
        xmll.getTexts().add((TextView) base);
    }

    /**
     * @param uri
     * @param localName
     * @param qName      例如 LinearLayout
     * @param attributes
     */
    private void handleLayout(String uri, String localName, String qName, Attributes attributes) {
        String idstr = attributes.getValue("android:id");
        if (idstr == null)
            return;
        BaseView base = new Layout();
        base.setId(getId(idstr));
        base.setQualifiedclazz(qName);
        xmll.getLayouts().add((Layout) base);
    }

    /**
     * @param uri
     * @param localName
     * @param qName      例如 LinearLayout
     * @param attributes
     */
    private void handleFragment(String uri, String localName, String qName, Attributes attributes) {
        String idstr = attributes.getValue("android:id");
        if (idstr == null)
            return;
        BaseView base = new Fragment();
        base.setId(getId(idstr));
        base.setQualifiedclazz(qName);
        ((Fragment) base).setFragmentclazz(attributes.getValue("android:name"));
        xmll.getFragments().add((Fragment) base);
    }

    /**
     * @param uri
     * @param localName
     * @param qName      例如 LinearLayout
     * @param attributes
     */
    private void handleImageView(String uri, String localName, String qName, Attributes attributes) {
        String idstr = attributes.getValue("android:id");
        if (idstr == null)
            return;
        BaseView base = new ImageView();
        base.setId(getId(idstr));
        base.setQualifiedclazz(qName);
        xmll.getImages().add((ImageView) base);
    }

    /**
     * @param uri
     * @param localName
     * @param qName      例如 LinearLayout
     * @param attributes
     */
    private void handleAdapterView(String uri, String localName, String qName, Attributes attributes) {
        String idstr = attributes.getValue("android:id");
        if (idstr == null)
            return;
        //
        BaseView base = new AdapterView();
        // id
        base.setId(getId(idstr));
        // 对应的View
        base.setQualifiedclazz(qName);
        //
        xmll.getAdapterviews().add((AdapterView) base);
    }


    /**
     * 获取Id
     * <p>
     * 去掉@id/和+
     *
     * @param id
     * @return
     */
    private static String getId(String id) {
        String ret = id.replace("+", "").replace("@id/", "");
        return ret;
    }

}
