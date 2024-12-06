package com.springboot_backend.service;

import com.springboot_backend.dao.Collect;
import com.springboot_backend.dao.CollectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    private CollectRepository collectRepository;

    @Override
    public Collect getCollectById(int collect_id) {
        return collectRepository.findById(collect_id)
                .orElseThrow(() -> new IllegalArgumentException("收藏不存在"));
    }

    @Override
    public List<Collect> getCollectByUser(int user_id) {
        return collectRepository.findByUserID(user_id);
    }

    @Override
    public List<Collect> getCollectByProduct(String product_id) {
        return collectRepository.findByProductID(product_id);
    }

    @Override
    public List<Collect> getCollectByUserAndProduct(int user_id, String product_id) {
        return collectRepository.findByUserIDAndProductID(user_id, product_id);
    }

    @Override
    public Integer addNewCollect(int user_id, String product_id) {
        List<Collect> collectList = collectRepository.findByUserIDAndProductID(user_id, product_id);
        if (!collectList.isEmpty()) {
            throw new IllegalArgumentException("重复收藏");
        }
        Collect collect = new Collect();
        collect.setUserID(user_id);
        collect.setProductID(product_id);
        collect.setIsLowReminder(0); // 默认没有设置降价提示
        collectRepository.save(collect);// 自动修改主键ID
        return collect.getCollectID();
    }

    @Override
    @Transactional
    public Integer deleteCollectById(int collect_id) {
        collectRepository.deleteById(collect_id);
        return 1;
    }

    @Override
    @Transactional
    public Integer deleteCollectByUserAndProduct(int user_id, String product_id) {
        collectRepository.deleteByUserIDAndProductID(user_id, product_id);
        return 1;
    }

    @Override
    @Transactional
    public Integer updateCollect(int user_id, String product_id, int isLowReminder) {
        List<Collect> collectList = collectRepository.findByUserIDAndProductID(user_id, product_id);
        if(collectList.isEmpty()) {
            return 0; // 收藏不存在
        }
        if(collectList.get(0).getIsLowReminder() == isLowReminder)
            return 0; // 不需要重复修改
        collectList.get(0).setIsLowReminder(isLowReminder);
        collectRepository.save(collectList.get(0));
        return 1;
    }
}
