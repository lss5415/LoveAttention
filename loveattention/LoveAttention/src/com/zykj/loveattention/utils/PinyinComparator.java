package com.zykj.loveattention.utils;

import java.util.Comparator;

import com.zykj.loveattention.data.SortModel;

/**
 * @author lss 2015年7月7日 根据拼音来排列ListView里面的数据类
 *
 */
public class PinyinComparator implements Comparator<SortModel> {

	public int compare(SortModel o1, SortModel o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}

}
