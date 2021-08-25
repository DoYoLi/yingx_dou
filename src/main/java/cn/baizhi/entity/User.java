package cn.baizhi.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements Serializable {

  @Excel(name = "id",width = 50)
  private String id;
  @Excel(name = "手机号")
  private String phone;
  @Excel(name = "姓名")
  private String username;
  @Excel(name = "性别")
  private String sex;
  @Excel(name = "头像",width = 10 , height = 20,type = 2)
  private String headimg;
  @Excel(name = "个性签名")
  private String brief;
  @Excel(name = "微信")
  private String wechat;
  @JsonFormat(pattern = "yyyy-MM-dd")
  @Excel(name = "创建日期",format = "yyyy-MM-dd")
  private Date createdate;
  @Excel(name = "状态")
  private Integer status;

}
