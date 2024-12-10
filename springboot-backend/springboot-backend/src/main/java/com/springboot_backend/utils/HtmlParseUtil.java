package com.springboot_backend.utils;

import com.springboot_backend.dao.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.*;
@Component
public class HtmlParseUtil {
    public static void main(String[] args) throws IOException {
        HtmlParseUtil htmlParseUtil = new HtmlParseUtil();
        htmlParseUtil.parseSN("牛肉干");
    }

    public List<Product> parseJD(String keywords) throws IOException {
        try {
            String url = "https://search.jd.com/Search?keyword=" + keywords;
//            url = "https://item.jd.com/100102400142.html";
            Map<String, String> cookies = new HashMap<String, String>();
            List<Product> productList = new ArrayList<>();
            // 设置登录cookie，需要定时重置
            cookies.put("thor", "07AD4A6FDEAFFC6EE9F9559B90C4C1D1ECF98E1AA93A9AB8E3CB5FBEA265B3CAB7B5DBDF7CA2ABB698AF78458613F9C3275372EFDA1C700A62C353E843793F446C09853AF20B0471C72B56C4C1A8BA90C1D1475338846A6DE397C3E0469133E47DF717625DA9A664CD017F135BF358D1788C05393303E09330F1B242A43CE6C466ADB64A9F9BBA21398721B94F26F715F6CEEFBCEBD416C163806E9D8B507120");
//            cookies.put("thor", "FCEFB17C68A6956B16885A116411EE8674CDF6495EF568FC5628C94AC6609E770304FACE0099DA84E66D3052B79481B39AF461AF4B0809B21EE07B194DEBAC0E7678CE10CD2CB8C625BA252FC8FDAFA166764EDCD67B50B1A912176212ACCB33135D53BC138107E85B148945E314FD28E2E38C9F510E5C36255BFADA6A944F3A1206AB1A50C6092DA4F1D5CB1BC73E3E0D4A8CDB23F318D5667EB009523513A4");
            Document document = Jsoup.connect(url).cookies(cookies).get();
            Element element = document.getElementById("J_goodsList");
            if(element == null) {
                System.out.println(document.html());
                System.out.println("京东获取失败");
                return Collections.emptyList();
            }
            // 获取所有li元素
            Elements productsElements = element.getElementsByTag("li");
            // 每一个productElement就是一个li标签内容
            for (Element productElement : productsElements) {
                // 商品名称获取
                Element nameElement = productElement.getElementsByClass("p-name").first();
                if (nameElement == null) continue;
                Element subnameElement = nameElement.getElementsByTag("em").first();
                String name = subnameElement != null ? subnameElement.text() : "unknown";
//                System.out.println("商品名称：" + name);

                // 商品详情获取
                String detail = nameElement.getElementsByTag("a").first().attr("title");
                if (Objects.equals(detail, "")) detail = "暂无";
//                System.out.println("商品详情：" + detail);

                // 价格获取 ￥xxx
                Element priceElement = productElement.getElementsByClass("p-price").first();
                String price = priceElement != null ? priceElement.getElementsByTag("i").first().text() : "0.00";
//                System.out.println("商品价格：" + price);

                // 店铺名称获取
                Element shopElement = productElement.getElementsByClass("curr-shop hd-shopname").first();
                String shop = shopElement != null ? shopElement.attr("title") : "unknown";
                // 使用正则表达式检查字符串是否全是空格
                if (shop.matches("^\\s*$")) {
                    shop = "未知店铺";
                }

                // 商品链接获取
                Element photoElement = productElement.getElementsByClass("p-img").first();
                String productURL = photoElement != null ? photoElement.getElementsByTag("a").first().attr("href") : "...";
//                System.out.println("商品链接：" + productURL);

                // 图片获取
                String photoURL = productElement.getElementsByTag("img").first().attr("data-lazy-img");
//                System.out.println("图片链接：" + photoURL);
                // 商品ID获取
                String productID = productElement.getElementsByTag("div").first().attr("id");

                Product product = new Product();
                product.setId("JD-" + productID);
                product.setName(name);
                product.setDetail(detail);
                product.setPrice(Double.parseDouble(price));
                product.setShop(shop);
                product.setPhotoURL(photoURL);
                product.setProductURL(productURL);
                product.setPlatform("京东");
                productList.add(product);
//            System.out.println("======================================================================");
//            System.out.println("商品名称：" + name);
//            System.out.println("商品价格：" + price);
//            System.out.println("商品详情：" + detail);
//            System.out.println("图片链接：" + photoURL);
//            System.out.println("店铺名称：" + shop);
//            System.out.println("商品链接：" + productURL);
//            System.out.println("商品ID：" + productID);
            }
            System.out.println("京东关键词：" + keywords + ", 爬取成功");
            return productList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Product> parseAmazon(String keywords) throws IOException {
        try {
            String url = "https://www.amazon.com/s?language=zh_CN&currency=CNY&k=" + keywords;
            List<Product> productList = new ArrayList<>();
            Document document = Jsoup.connect(url).header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3").get();
            Elements productsElements = document.select("[data-component-type='s-search-result']");
            if(productsElements == null) {
                System.out.println("亚马逊也干了");
                return Collections.emptyList();
            }
            for (Element productElement : productsElements) {
                // 商品名称获取
                Element nameElement = productElement.getElementsByClass("a-size-base-plus a-color-base a-text-normal").first();
                if(nameElement == null) continue;
                String name = nameElement.text();

                // 商品链接获取
                Element productURLElement = productElement.getElementsByClass("a-link-normal s-underline-text s-underline-link-text s-link-style a-text-normal").first();
                String productURL = "https://www.amazon.com" + productURLElement.attr("href");

                // 商品ID获取
                String productID = productElement.attr("data-asin");

                // 图片链接获取
                Element photoElement = productElement.getElementsByClass("s-image").first();
                String photoURL = photoElement != null ? photoElement.attr("src") : "...";

                // 商品价格 可能需要进行汇率转化
                Element currencyElement = productElement.getElementsByClass("a-price-symbol").first();
                String currency = currencyElement != null ? currencyElement.text() : "unknown";
                Element decimalElement = productElement.getElementsByClass("a-price-whole").first();
                String decimal = decimalElement != null ? decimalElement.text().replace(",", "") : "unknown";
                Element fractionElement = productElement.getElementsByClass("a-price-fraction").first();
                String fraction = fractionElement != null ? fractionElement.text().replace(",", "").substring(0, 2) : "unknown";
                if (currency.equals("unknown") || decimal.equals("unknown") || fraction.equals("unknown"))
                    continue;
                String price = decimal + fraction;
                double item_price = Double.parseDouble(price);
                if (currency.equals("US$"))
                    item_price *= 7.1023;
                long round = Math.round(item_price * 100);
                item_price = round / 100.0;

                Product product = new Product();
                product.setId("Amazon-" + productID);
                product.setName(name);
                product.setDetail("暂无");
                product.setPrice(item_price);
                product.setShop("未知店铺");
                product.setPhotoURL(photoURL);
                product.setProductURL(productURL);
                product.setPlatform("亚马逊");
                productList.add(product);
//                System.out.println("======================================================================");
//                System.out.println("商品名称：" + name);
//                System.out.println("商品价格：" + item_price);
//                System.out.println("图片链接：" + photoURL);
//                System.out.println("商品链接：" + productURL);
//                System.out.println("商品ID：" + productID);
            }
            System.out.println("亚马逊关键词：" + keywords + ", 爬取成功, 共计"+productList.size()+"项");
            return productList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Product> parseDD(String keywords) throws IOException {
        try {
            String url = "https://search.dangdang.com/?key=" + keywords;
            //        解析该网站
            Document document = Jsoup.parse(new URL(url), 30000);

            List<Product> productList = new ArrayList<>();
//                    System.out.println(document.html());      //打印获取网页时的状态，可以查看一些懒加载的真实属性
            //获取指定的标签
            Element element = document.getElementById("search_nature_rg");
            if(element == null) return Collections.emptyList();
//            System.out.println(element.html());
            //获取标签内的所有指定的标签
            Elements elements = element.getElementsByTag("li");
            for (Element productElement : elements) {

//                System.out.println(productElement.html());
                // 获取商品ID
                String productID = productElement.attr("id");
                if(productID.isEmpty()) continue;
                // 获取商品名称
                Element nameElement = productElement.getElementsByTag("a").first();
                String name = nameElement.attr("title");

                // 商品链接
                String productURL = "https:" + nameElement.attr("href");

                // 商品价格
                Element priceElement = productElement.getElementsByClass("price_n").first();
                String price = priceElement != null ? priceElement.text().substring(1) : "unknown";
                if(price == "unknown") continue;
                price = price.replaceAll("[^0-9.]", "");// 使用正则表达式取出非数字部分
                // 图片链接
                Element photoURLElement = productElement.getElementsByTag("img").first();
                String photoURL = photoURLElement.attr("data-original");
                if(photoURL.isEmpty()) photoURL = photoURLElement.attr("src");

                // 店铺名称（可能有）
                Element shopElement = productElement.getElementsByClass("search_shangjia").first();
                if(shopElement == null)
                    shopElement = productElement.getElementsByClass("link").first();
                Element subShopElement = shopElement != null ? shopElement.getElementsByTag("a").first() : null;
                String shop = subShopElement != null ? subShopElement.attr("title") : "未知店铺";
                // 商品详情
                Element detailElement = productElement.getElementsByClass("search_hot_word").first();
                String detail = detailElement != null ? detailElement.text() : "暂无";

//                System.out.println("======================================================================");
//                System.out.println("商品名称：" + name);
//                System.out.println("商品价格：" + price);
//                System.out.println("图片链接：" + photoURL);
//                System.out.println("商品链接：" + productURL);
//                System.out.println("商品ID：" + productID);
                Product product = new Product();
                product.setId("DD-" + productID);
                product.setName(name);
                product.setDetail(detail);
                product.setPrice(Double.parseDouble(price));
                product.setShop(shop);
                product.setPhotoURL(photoURL);
                product.setProductURL(productURL);
                product.setPlatform("当当");
                productList.add(product);

            }
            System.out.println("当当网关键词：" + keywords + "，爬取成功, 共计"+productList.size()+"项");
            return productList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Product> parseSN(String keywords) throws IOException {
        try{
            // 加载苏宁信息
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless"); // 启用新的无头模式
            System.setProperty("webdriver.chrome.driver", "D:/Software/chromedriver-win64/chromedriver-win64/chromedriver.exe");
            WebDriver driver = new ChromeDriver(options);
            driver.get("https://search.suning.com/"+keywords+"/");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);"); // 滚动到底部

            Thread.sleep(2000); // 或者使用显式等待

            // 等待页面加载完成
            String pageSource = driver.getPageSource();
            driver.quit();
            Document document = Jsoup.parse(pageSource);
//            System.out.println(document.html());
            Element element = document.getElementById("product-list");
            Elements elements = element.getElementsByTag("li");
            List<Product> productList = new ArrayList<>();
            for (Element productElement : elements) {
                // 商品ID获取
                String productID = productElement.attr("id");

                // 商品名称详情链接获取
                Element nameElement = productElement.getElementsByClass("title-selling-point").first();
//                if(nameElement == null) continue;
                Element subNameElement = nameElement.getElementsByTag("a").first();
//                if(subNameElement == null) continue;
                String name = subNameElement.text();
                Element detailElement = subNameElement.getElementsByTag("em").first();
                String detail = detailElement != null ? detailElement.text() : "暂无";
                String productURL = subNameElement.attr("href");
                if(productURL.startsWith("https"))
                    continue;
                productURL = "https:" + productURL.replace("https", "http");

                // 图片获取
                Element photoElement = productElement.getElementsByClass("res-img").first();
                Element subPhotoElement = photoElement.getElementsByTag("img").first();
                String photoURL = subPhotoElement.attr("src");

                // 价格获取
                Element priceElement = productElement.getElementsByClass("def-price").first();
//                if(priceElement == null) continue;
                String price = priceElement.text();
                if (price.matches("^\\s*$")) {
                    continue; // 是否没有加载出来
                }
                price = price.replaceAll("[^0-9.]", "");// 使用正则表达式取出非数字部分
                // 店铺名称获取
                Element storeElement = productElement.getElementsByClass("store-name").first();
                String shop = storeElement != null ? storeElement.text() : "未知店铺";

//                System.out.println("======================================================================");
//                System.out.println("商品名称：" + name);
//                System.out.println("商品价格：" + price);
//                System.out.println("图片链接：" + photoURL);
//                System.out.println("商品链接：" + productURL);
//                System.out.println("商品ID：" + productID);
                Product product = new Product();
                product.setId("SN-" + productID);
                product.setName(name);
                product.setDetail(detail);
                product.setPrice(Double.parseDouble(price));
                product.setShop(shop);
                product.setPhotoURL(photoURL);
                product.setProductURL(productURL);
                product.setPlatform("苏宁");
                productList.add(product);
            }
            System.out.println("苏宁关键词："+keywords+"爬取成功, 共计"+productList.size()+"项");
            return productList;
        } catch(Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}