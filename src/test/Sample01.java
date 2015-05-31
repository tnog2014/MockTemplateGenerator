package test;

import java.util.HashMap;
import java.util.Map;

public class Sample01 {

	// 戻り値あり, public, インスタンスメソッド
	public Map<String, Integer> getMap(String key, int value) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put(key, value);
		return map;
	}

	// 戻り値あり, private, インスタンスメソッド
	private Map<String, Integer> getMap2(String key, int value) {
		System.out.println("getMap2 called:" + key + "," + value);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put(key, value);
		return map;
	}

	// 戻り値あり, public, 静的メソッド
	public static Map<String, Integer> getMap3(String key, int value) {
		System.out.println("getMap3 called:" + key + "," + value);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put(key, value);
		return map;
	}

	// 戻り値あり, private, 静的メソッド
	private static Map<String, Integer> getMap4(String key, int value) {
		System.out.println("getMap3 called:" + key + "," + value);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put(key, value);
		return map;
	}

	// voidメソッド

	// 戻り値なし, public, インスタンスメソッド
	public void nop1(String key, int value) {
		System.out.println("nop1 called " + key + "," + value);
	}

	// 戻り値なし, private, インスタンスメソッド
	private void nop2(String key, int value) {
		System.out.println("nop2 called " + key + "," + value);
	}

	// 戻り値なし, public, 静的メソッド
	public static void nop3(String key, int value) {
		System.out.println("nop3 called " + key + "," + value);
	}

	// 戻り値なし, private, 静的メソッド
	private static void nop4(String key, int value) {
		System.out.println("nop4 called " + key + "," + value);
	}

}
