package com.friends.utils;

import org.apache.commons.lang3.StringEscapeUtils;

public class StringEscapeUtilsTest {
	public static void main(String args[]){  
        
          
        System.out.println("转义HTML,注意汉字:"+StringEscapeUtils.escapeHtml3("<font>chen磊  xing</font>"));    //转义HTML,注意汉字  
          
    }   
}
