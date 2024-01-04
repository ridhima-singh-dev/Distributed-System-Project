package ds.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author Qinghan Huang
 * @Desc
 * @Version 1.0
 */
@Data
public class Account {
    private int id;
    private String email;
    private String username;
    private String password;
}
