package com.zgy.skillexchange.util;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchFile {
	public void find(String path, String reg) {
		Pattern pat = Pattern.compile(reg);
		File file = new File(path);
		File[] arr = file.listFiles();
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].isDirectory()) {// 如果是文件夹
				find(arr[i].getAbsolutePath(), reg);
			}
			Matcher mat = pat.matcher(arr[i].getAbsolutePath());
			// 根据正则表达式，寻找匹配的文件
			if (mat.matches()) {
				System.out.println(arr[i].getAbsolutePath());
			}
		}
	}
 
	public static List<String> getFileList(File file) {
 
		List<String> result = new ArrayList<String>();
 
		if (!file.isDirectory()) {
			System.out.println(file.getAbsolutePath());
			result.add(file.getAbsolutePath());
		} else {
			File[] directoryList = file.listFiles(new FileFilter() {
				public boolean accept(File file) {
					if (file.isFile() && file.getName().indexOf("txt") > -1) {
						return true;
					} else {
						return false;
					}
				}
			});
			for (int i = 0; i < directoryList.length; i++) {
				result.add(directoryList[i].getPath());
			}
		}
 
		return result;
	}
}
