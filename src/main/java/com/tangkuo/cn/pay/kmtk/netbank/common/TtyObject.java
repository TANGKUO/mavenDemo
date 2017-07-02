/**
 * Copyright (c) 2011-2014 All Rights Reserved.
 */
package com.kame.micropay.commons;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

/**
 * 类说明：<br>
 * 
 * <p>
 * 详细描述：<br>
 * 
 * </p>
 * 
 * <pre>
 * ——————————————————————————————————————————————————————————————————
 * |		修改人		|		修改时间			|		修改原因
 * ——————————————————————————————————————————————————————————————————
 * |	xavier 曾宪新	|		2014年4月25日		|	
 * ——————————————————————————————————————————————————————————————————
 * </pre>
 * 
 * @author xavier 曾宪新(Xavier.zeng)
 * 
 *         CreateDate: 2014年4月25日
 */
public class TtyObject implements Serializable {
    
    private static final long serialVersionUID = -1133660048887177718L;
    
    /**
     * 主健ID
     */
    @Id
    private long id;

	/**
	 * Getter method for property <tt>id</tt>.
	 * 
	 * @return property value of id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter method for property <tt>id</tt>.
	 * 
	 * @param id value to be assigned to property id
	 */
	public void setId(long id) {
		this.id = id;
	}
	
}
