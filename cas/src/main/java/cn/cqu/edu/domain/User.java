package cn.cqu.edu.domain;

import org.springframework.data.annotation.Id;

public class User {
    // 这里标记userId就是后面findById的Id
    @Id
    private String userId;
    private String password;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    } 
}
