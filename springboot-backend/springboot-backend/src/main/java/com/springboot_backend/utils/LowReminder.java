package com.springboot_backend.utils;

import com.springboot_backend.dao.*;
import com.springboot_backend.service.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class LowReminder {
    // 降价提醒逻辑
    @Autowired
    CollectService collectService;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    HistoryService historyService;

    @Autowired
    SpiderService spiderService;

    @Autowired
    SimpleEmailSender simpleEmailSender;

    @PostConstruct
    public void lowRemind() throws IOException {
        // 获取所有设置了降价提醒的信息
        List<Collect> collectList = collectService.getCollectByAllIsLowReminder();
        List<String> productIDs = new ArrayList<>();
        // 所有设置了降价提醒的商品需要重新爬取数据
        for (Collect collect : collectList) {
            if(!productIDs.contains(collect.getProductID())) {
                productIDs.add(collect.getProductID());
            }
        }
        // 遍历所有商品，查看数据库中最新历史价格
        for (String productID : productIDs) {
            Product product = productService.getProductById(productID);
            History oldHistory = historyService.getLastHistoryByProductID(productID);
            LocalDate date = LocalDate.now();
            // 如果日期相同，不做处理
            if(oldHistory.getDate().equals(date)) {
                continue;
            }
            // 否则，利用商品名称+平台调用对应爬虫，爬取最新历史价格
            String productName = product.getName();
            switch(product.getPlatform())
            {
                case "当当":
                    spiderService.DDSpider(productName);
                    break;
                case "亚马逊":
                    spiderService.AmazonSpider(productName);
                    break;
                case "苏宁":
                    spiderService.SNSpider(productName);
                    break;
                default:
                    break;
            }

            // 再次比较最新历史价格
            History newHistory = historyService.getLastHistoryByProductID(productID);
            if(newHistory.getPrice() >= oldHistory.getPrice()) continue;

            // 如果降价，获取该商品所有设置了降价的用户
            List<Collect> userCollectList = collectService.getCollectByProduct(productID);
            for (Collect collect : userCollectList) {
                // 没有设置降价提醒
                if(collect.getIsLowReminder() == 0)
                    continue;
                // 将该用户找出来
                User user = userService.getUserById(collect.getUserID());
                // 发送邮件
                String remindMessage = "亲爱的：" + user.getName() + ", 您订阅的商品：" + productName +
                        "迎来了折扣！快来看看吧！" + "（商品链接为：" + product.getProductURL() + ")";
                String subject = "商品降价提醒";
                simpleEmailSender.sendMessage(user.getEmail(), subject, remindMessage);
            }

        }
    }
}
