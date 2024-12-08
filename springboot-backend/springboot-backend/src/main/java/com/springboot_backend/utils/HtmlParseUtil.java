package com.springboot_backend.utils;

import com.springboot_backend.dao.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HtmlParseUtil {
    public static void main(String[] args) throws IOException {
        new HtmlParseUtil().parseJD("java");
    }
    public void parseJD(String keywords) throws IOException {
        String url = "https://search.jd.com/Search?keyword=" + keywords;
        Map<String, String> cookies = new HashMap<String, String>();
        // 设置登录cookie，需要定时重置
        cookies.put("thor","07AD4A6FDEAFFC6EE9F9559B90C4C1D1ECF98E1AA93A9AB8E3CB5FBEA265B3CAB7B5DBDF7CA2ABB698AF78458613F9C3275372EFDA1C700A62C353E843793F446C09853AF20B0471C72B56C4C1A8BA90C1D1475338846A6DE397C3E0469133E47DF717625DA9A664CD017F135BF358D1788C05393303E09330F1B242A43CE6C466ADB64A9F9BBA21398721B94F26F715F6CEEFBCEBD416C163806E9D8B507120");
        Document document = Jsoup.connect(url).cookies(cookies).get();
        Element element = document.getElementById("J_goodsList");
        // 获取所有li元素
        Elements productsElements = element.getElementsByTag("li");
        // 每一个productElement就是一个li标签内容
        for(Element productElement : productsElements) {
            // 商品名称获取
            Element nameElement = productElement.getElementsByClass("p-name").first();
            Element subnameElement = nameElement != null ? nameElement.getElementsByTag("em").first() : null;
            String name = subnameElement != null ? subnameElement.text() : "unknown";

            // 商品详情获取
            String detail = nameElement != null ? nameElement.getElementsByTag("a").first().attr("title") : "unknown";
            if(Objects.equals(detail, "")) detail = "暂无";
            // 价格获取 ￥xxx
            String price = productElement.getElementsByClass("p-price").first().text();

            // 店铺名称获取
            Element shopElement = productElement.getElementsByClass("p-shop").first();
            String shop = shopElement != null ? shopElement.getElementsByTag("a").first().attr("title") : "unknown";

            // 商品链接获取
            Element photoElement = productElement.getElementsByClass("p-img").first();
            String productURL = photoElement.getElementsByTag("a").first().attr("href");

            // 图片获取
            String imgURL = productElement.getElementsByTag("img").first().attr("data-lazy-img");

            // 商品ID获取
            String productID = productElement.getElementsByTag("div").first().attr("id");
            System.out.println("======================================================================");
            System.out.println("商品名称：" + name);
            System.out.println("商品价格：" + price);
            System.out.println("商品详情：" + detail);
            System.out.println("图片链接：" + imgURL);
            System.out.println("店铺名称：" + shop);
            System.out.println("商品链接：" + productURL);
            System.out.println("商品ID：" + productID);
        }
    }
}
