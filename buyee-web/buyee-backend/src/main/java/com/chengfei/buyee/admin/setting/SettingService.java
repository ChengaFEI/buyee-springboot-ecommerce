package com.chengfei.buyee.admin.setting;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chengfei.buyee.common.entity.Setting;

@Service
public class SettingService {
    @Autowired private SettingRepository repo;
    // Read Tasks
    public List<Setting> readAllSettings() {return (List<Setting>) repo.findAll();}
}
