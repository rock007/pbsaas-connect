/**
*@Project: subway-backend
*@Author: sam
*@Date: 2016年12月13日
*@Copyright: 2016  All rights reserved.
*/
package com.pbsaas.connect.db.type;

/**
 * @author sam
 *
 */
public enum RoleType {
	
	empty(0, "未设置"),
	normal(1, "普通用户"),
	wy(2, "物业人员"),
	admin(3, "管理员");
	
	private int _value;
	private String _name;

	private RoleType(int value, String name) {
		_value = value;
		_name = name;
	}

	public int value() {
		return _value;
	}

	public String getName() {
		return _name;
	}
}
