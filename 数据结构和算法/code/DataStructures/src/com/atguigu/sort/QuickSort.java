package com.atguigu.sort;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class QuickSort {

	public static void main(String[] args) {

		int[] arr = { 2, 1, 4, 7, 0 };
//		quickSort(arr, 0, arr.length - 1);
		 quickSort1(arr, 0, arr.length - 1);
		System.out.println("arr=" + Arrays.toString(arr));

		// //测试快排的执行速度
		// // 创建要给80000个的随机的数组
		// int[] arr = new int[8000000];
		// for (int i = 0; i < 8000000; i++) {
		// arr[i] = (int) (Math.random() * 8000000); // 生成一个[0, 8000000) 数
		// }
		//
		// System.out.println("排序前");
		// Date data1 = new Date();
		// SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd
		// HH:mm:ss");
		// String date1Str = simpleDateFormat.format(data1);
		// System.out.println("排序前的时间是=" + date1Str);
		//
		// quickSort(arr, 0, arr.length-1);
		//
		// Date data2 = new Date();
		// String date2Str = simpleDateFormat.format(data2);
		// System.out.println("排序前的时间是=" + date2Str);

	}

	// 以中间数为基准
	public static void quickSort(int[] arr, int left, int right) {
		int l = left; // 左下标
		int r = right; // 右下标
		// pivot 中轴值
		int pivot = arr[(left + right) / 2];
		int temp = 0; // 临时变量，作为交换时使用
		// while循环的目的是让比pivot 值小放到左边
		// 比pivot 值大放到右边
		while (l < r) {
			// 在pivot的左边一直找,找到大于等于pivot值,才退出
			while (arr[l] < pivot) {
				l += 1;
			}
			// 在pivot的右边一直找,找到小于等于pivot值,才退出
			while (arr[r] > pivot) {
				r -= 1;
			}
			// 如果l >= r说明pivot 的左右两的值，已经按照左边全部是
			// 小于等于pivot值，右边全部是大于等于pivot值
			if (l >= r) {
				break;
			}

			// 交换
			temp = arr[l];
			arr[l] = arr[r];
			arr[r] = temp;

			// 如果交换完后，发现这个arr[l] == pivot值 相等 r--， 前移
			if (arr[l] == pivot) {
				r -= 1;
			}
			// 如果交换完后，发现这个arr[r] == pivot值 相等 l++， 后移
			if (arr[r] == pivot) {
				l += 1;
			}
		}

		// 如果 l == r, 必须l++, r--, 否则为出现栈溢出
		if (l == r) {
			l += 1;
			r -= 1;
		}
		// 向左递归
		if (left < r) {
			quickSort(arr, left, r);
		}
		// 向右递归
		if (right > l) {
			quickSort(arr, l, right);
		}

	}

	// 以左边第一个数为基准 从小到大
	public static void quickSort1(int[] arr, int start, int end) {

		if (start > end) { // 递归退出条件
			return;
		}

		int base = arr[start];
		int low = start; // 左边的指针
		int high = end + 1; // 4 + 1 右边的指针
		while (true) {
			while (low < end && arr[++low] < base)
				;
			while (high > start && arr[--high] > base)
				;

			if (low < high) { // 交换这两个值
				swap(arr, low, high);
			} else {
				break;
			}
		}
		// 交换第一个和中间值 [2,1,4,7,0] -> low:2 high:4 ->[2,1,0,7,4] -> low:3,high:2
		swap(arr, start, high); // ->[0,1,2,7,4] //下面的递归以high为分界点

		// 左递归
		quickSort1(arr, start, high - 1);
		// 右递归
		quickSort1(arr, high + 1, end);

	}

	public static void swap(int arr[], int left, int right) {
		int temp = arr[left];
		arr[left] = arr[right];
		arr[right] = temp;
	}

}
