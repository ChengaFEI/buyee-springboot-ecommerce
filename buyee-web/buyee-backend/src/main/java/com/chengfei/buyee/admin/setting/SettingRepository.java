package com.chengfei.buyee.admin.setting;
import org.springframework.data.repository.CrudRepository;
import com.chengfei.buyee.common.entity.Setting;
public interface SettingRepository extends CrudRepository<Setting, String> {
    
}
