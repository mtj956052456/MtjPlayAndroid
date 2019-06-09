package com.zhenqi.baselibrary.view;

import android.text.method.ReplacementTransformationMethod;

public class WordReplacement extends ReplacementTransformationMethod {

 
	@Override
	protected char[] getOriginal() {
		String strWord = "";
		//循环ASCII值 字符串形式累加到String
		for (char i = 0; i < 256; i++) {  
			strWord += String.valueOf(i);
		}
		//strWord转换为字符形式的数组
		return  strWord.toCharArray();
	}
 
	@Override
	protected char[] getReplacement() {
		char[] charReplacement = new char[255];
		//输入的字符在ASCII范围内，将其转换为*
		for (int i = 0; i < 255; i++) {
			charReplacement[i] = '*';
		}
		return charReplacement;
	}
 
}