package com.atguigu.jdk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorDemo {

	public static void main(String[] args) {
		List<String> a = new ArrayList<>();
		a.add("jack");// ..
		// ��ȡ��������
		Iterator Itr = a.iterator();
		while (Itr.hasNext()) {
			System.out.println(Itr.next());
		}
	}

}
