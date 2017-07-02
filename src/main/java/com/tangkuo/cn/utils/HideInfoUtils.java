package com.tangkuo.cn.utils;

import org.apache.commons.lang.StringUtils;


/**
 * 隐藏敏感信息
 * 
 */
public class HideInfoUtils {

    /**
     * 隐藏证件号 身份证：36152819920225013X >= 36152**********13X 其他证件号：1212131313132 >= 1**********2
     * 
     * @Title: hideIdCard
     * @param @param id
     * @param @return 设定参数
     * @return String 返回类型
     * @throws
     */
    public static String hideIdCard(String id) {

        if (StringUtils.isBlank(id)) {
            return id;
        }

        // 身份证
        if (id.length() == 18) {
            return id.substring(0, 5) + "**********" + id.substring(15, 18);
        }

        // 其他证件
        StringBuilder result = new StringBuilder();
        result.append(id.substring(0, 1));
        for (int i = 0; i < id.length() - 2; i++) {
            result.append("*");
        }
        result.append(id.substring(id.length() - 1, id.length()));

        return result.toString();
    }

    /**
     * 隐藏手机号码 18688234693 >= 186****4693 保留前3后4，其他位隐藏
     * 
     * @Title: hidePhoneNum
     * @param @param num
     * @param @return 设定参数
     * @return String 返回类型
     * @throws
     */
    public static String hidePhoneNum(String num) {

        if (StringUtils.isBlank(num) || num.length() <= 7) {
            return num;
        }

        StringBuilder result = new StringBuilder(num.substring(0, 3));
        for (int i = 3; i < num.length() - 4; i++) {
            result.append('*');
        }
        result.append(num.substring(num.length() - 4, num.length()));

        return result.toString();
    }

    /**
     * 隐藏邮箱 aa@163.com >= a*@163.com a111b@163.com >= a***b@163.com
     * 
     * @Title: hideEmail
     * @param @param email
     * @param @return 设定参数
     * @return String 返回类型
     * @throws
     */
    public static String hideEmail(String email) {

        if (StringUtils.isBlank(email)) {
            return email;
        }

        int slot = email.indexOf("@");

        if (slot <= 1) {
            return email;
        }

        if (slot == 2) {
            return email.substring(0, 1) + "*" + email.substring(slot, email.length());
        }

        StringBuilder result = new StringBuilder(email.substring(0, 1));

        for (int i = 0; i < slot - 2; i++) {
            result.append("*");
        }

        return result.append(email.substring(slot - 1, email.length())).toString();
    }

    /**
     * 隐藏银行卡 6227123412347217 >= ************7217
     * 
     * @Title: hideBankCard
     * @param @param bankCard
     * @param @return 设定参数
     * @return String 返回类型
     * @throws
     */
    public static String hideBankCard(String bankCard) {

        if (StringUtils.isBlank(bankCard) || bankCard.length() < 4) {
            return bankCard;
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < bankCard.length() - 4; i++) {
            result.append("*");
        }
        result.append(bankCard.substring(bankCard.length() - 4, bankCard.length()));

        return result.toString();
    }
    /**
     * 总后台代扣业务
     * 隐藏银行卡 6227123412347217 >= *7217
     * 隐藏手机号 18688234693 >=*4693
     * 隐藏身份证 36152819920225013X >=*013x    其他证件号 1212131313132>=*3132
     * @Title: hideBankCard
     * @param @param bankCard
     * @param @return 设定参数
     * @return String 返回类型
     * @throws
     */
    public static String withholdHideCard(String Card) {

        if (StringUtils.isBlank(Card) || Card.length()< 5) {
            return Card;
        }

        StringBuilder result = new StringBuilder();
        result.append("*"+Card.substring(Card.length() - 4));

        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println("36152819920225013X" + " >= " + hideIdCard("36152819920225013X"));
        System.out.println("18688234693" + " >= " + hidePhoneNum("18688234693"));
        System.out.println("1234567" + " >= " + hidePhoneNum("1234567"));
        System.out.println("12345678" + " >= " + hidePhoneNum("12345678"));
        System.out.println("1234567890abc" + " >= " + hidePhoneNum("1234567890abc"));
        System.out.println("aa@163.com" + " >= " + hideEmail("aa@163.com"));
        System.out.println("a111b@163.com" + " >= " + hideEmail("a111b@163.com"));
        System.out.println("6227123412347217" + " >= " + hideBankCard("6227123412347217"));
        
        System.out.println("36152819920225013X" + " >= " + withholdHideCard("36152819920225013X"));
        System.out.println("131231" + " >= " + withholdHideCard("131231"));
        System.out.println("6227123412347217" + " >= " + withholdHideCard("6227123412347217"));
        System.out.println("1233" + " >= " + withholdHideCard("1233"));
        System.out.println("18688234693" + " >= " + withholdHideCard("18688234693"));
        System.out.println("1234567" + " >= " + withholdHideCard("1234567"));
        System.out.println("12345678" + " >= " + withholdHideCard("12345678"));
    }
}
