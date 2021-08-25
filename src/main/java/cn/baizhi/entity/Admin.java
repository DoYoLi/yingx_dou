package cn.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//生成get、set、to string方法
@Data
//生成无参构造
@NoArgsConstructor
//生成有参构造
@AllArgsConstructor
public class Admin implements Serializable {

  private String id;
  private String username;
  private String password;
  private long status;

}
