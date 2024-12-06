package com.springboot_backend.service;

import com.springboot_backend.dao.Collect;
import com.springboot_backend.dao.CollectRepository;

import java.util.List;

public interface CollectService {
    Collect getCollectById(int collect_id);
    List<Collect> getCollectByUser(int user_id);
    List<Collect> getCollectByProduct(String product_id);
    List<Collect> getCollectByUserAndProduct(int user_id, String product_id);
    Integer addNewCollect(int user_id, String product_id);
    Integer deleteCollectById(int collect_id);
    Integer deleteCollectByUserAndProduct(int user_id, String product_id);
    Integer updateCollect(int user_id, String product_id, int isLowReminder);
}
