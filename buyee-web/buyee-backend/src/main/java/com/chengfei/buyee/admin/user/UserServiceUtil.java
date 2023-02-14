package com.chengfei.buyee.admin.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.chengfei.buyee.common.entity.User;
public class UserServiceUtil {
    @Autowired private static PasswordEncoder passwordEncoder;
    static void encodePassword(User user) {
	String encodedPassword = passwordEncoder.encode(user.getPassword());
	user.setPassword(encodedPassword);
    }
}
