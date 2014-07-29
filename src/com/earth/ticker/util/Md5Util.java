package com.earth.ticker.util;

import java.security.MessageDigest;

public class Md5Util {
	    public final static String MD5(String s) {
	        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};       
	        try {
	            byte[] btInput = s.getBytes();
	            // ���MD5ժҪ�㷨�� MessageDigest ����
	            MessageDigest mdInst = MessageDigest.getInstance("MD5");
	            // ʹ��ָ�����ֽڸ���ժҪ
	            mdInst.update(btInput);
	            // �������
	            byte[] md = mdInst.digest();
	            // ������ת����ʮ����Ƶ��ַ���ʽ
	            int j = md.length;
	            char str[] = new char[j * 2];
	            int k = 0;
	            for (int i = 0; i < j; i++) {
	                byte byte0 = md[i];
	                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
	                str[k++] = hexDigits[byte0 & 0xf];
	            }
	            return new String(str);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    public static void main(String[] args) {
	        System.out.println(Md5Util.MD5("20121221"));
	        System.out.println(Md5Util.MD5("����"));
	    }
}
