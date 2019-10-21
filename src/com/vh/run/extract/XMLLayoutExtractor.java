package com.vh.run.extract;

import com.vh.run.model.CustomView;

import org.xml.sax.InputSource;

import java.io.InputStream;
import java.util.Iterator;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * 解析XML
 */
public class XMLLayoutExtractor {


    public XMLLayoutBean parseXML(InputStream in) throws Exception {
        System.out.println("--- parseXML ---");
        InputSource saxin = new InputSource(in);

        //获取SAX分析器的工厂实例，专门负责创建SAXParser分析器
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        //获取SAXParser分析器的实例  
        SAXParser saxParser = saxParserFactory.newSAXParser();
        //
        XMLHandler h = new XMLHandler();

        saxParser.parse(saxin, h);

        // 打印自定义View
        for (Iterator iterator = h.xmll.getCustomViews().iterator(); iterator.hasNext(); ) {
            CustomView c = (CustomView) iterator.next();
            System.out.println("found customview " + c.getQualifiedclazz());
        }

        return h.xmll;
    }

    public static void main(String[] args) throws Exception {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("xml/layout/test_main_layout.xml");
        XMLLayoutBean t = new XMLLayoutExtractor().parseXML(in);
        System.out.println();
    }


}
