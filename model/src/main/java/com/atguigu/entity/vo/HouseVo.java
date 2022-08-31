package com.atguigu.entity.vo;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 展示首页
 */
@Data
public class HouseVo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String communityName;//小区名
	private Long id;
	private String name;//房源名
	private String buildArea;//建筑面积
	private BigDecimal totalPrice;//总价
	private BigDecimal unitPrice;//单价
	private Long houseTypeId;//房屋类型id
	private Long floorId;//楼层id
	private Long directionId;//朝向id
	private String defaultImageUrl;//房源图片
	private Date createTime;//创建时间
	private String houseTypeName;//房屋类型
	private String floorName;//楼层
	private String directionName;//朝向

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