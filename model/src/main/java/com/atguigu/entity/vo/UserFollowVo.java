package com.atguigu.entity.vo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
@Data
public class UserFollowVo implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;  //关注 id
	private Long houseId; //房源id
	private Date createTime;  //关注时间
	private String communityName; //小区名字
	private String name;  //房源名称
	private String buildArea; //建筑面积
	private BigDecimal totalPrice;//总价
	private String defaultImageUrl;//房源默认图片
	private Long houseTypeId;//户型
	private Long floorId;//楼层
	private Long directionId;//朝向

	private String houseTypeName;
	private String floorName;
	private String directionName;

	public String getCreateTimeString() {
		Date date = this.getCreateTime();
		if(null == date) {
			return "";
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = df.format(date);
		return dateString;
	}
}

