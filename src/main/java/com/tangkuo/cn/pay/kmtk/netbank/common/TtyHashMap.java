package com.tangkuo.cn.pay.kmtk.netbank.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;


/**
 * 纯字符串字典结构。
 *
 */
public class TtyHashMap extends HashMap<String, String> {

    private static final long serialVersionUID = -1277791390393392630L;

    public TtyHashMap() {
        super();
    }

    public TtyHashMap(Map<? extends String, ? extends String> m) {
        super(m);
    }

    public String put(String key, Object value) {
        String strValue;

        if (value == null) {
            strValue = null;
        } else if (value instanceof String) {
            strValue = (String) value;
        } else if (value instanceof Integer) {
            strValue = ((Integer) value).toString();
        } else if (value instanceof Long) {
            strValue = ((Long) value).toString();
        } else if (value instanceof Float) {
            strValue = ((Float) value).toString();
        } else if (value instanceof Double) {
            strValue = ((Double) value).toString();
        } else if (value instanceof Boolean) {
            strValue = ((Boolean) value).toString();
        } else if (value instanceof Date) {
            DateFormat format = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
            format.setTimeZone(TimeZone.getTimeZone(Constants.DATE_TIMEZONE));
            strValue = format.format((Date) value);
        } else {
            strValue = value.toString();
        }

        return this.put(key, strValue);
    }

    public String put(String key, String value) {
        if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value) ) {
            return super.put(key, value);
        } else {
            return null;
        }
    }
    
    /** 
     * @see java.util.AbstractMap#toString()
     */
    @Override
    public String toString() {
    	StringBuilder strs = new StringBuilder();
		Iterator<Map.Entry<String, String>> it = this.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			strs.append(entry.getKey()+"="+entry.getValue()+"&");
		}
		if (strs.length()>0) {
			strs.delete(strs.length()-1, strs.length());
		}else{
			strs.append(hashCode());
		}
		return strs.toString();
    }
    
}
