package org.ansj.library;

import java.io.BufferedReader;

import org.ansj.domain.Branch;
import org.ansj.domain.Forest;
import org.ansj.domain.WoodInterface;
import org.ansj.util.IOUtil;
import org.ansj.util.MyStaticValue;

public class MakeArrayLibrary {
	// 用户自定义词典
	private static Forest forest = null;
	// 词性词典
	private static Forest natureForest = null;

	/**
	 * 词典的加载,第一个参数是词典的路径.第二个参数是词典的首字词性.第三个参数是需要得到的词典森林
	 * 
	 * @return 词典森林
	 */
	public static void init() {
		try {
			forest = makeLibrary(MyStaticValue.rb.getString("userLibrary"), 0,
					new Forest());
			// 加载词性词典
			natureForest = makeLibrary(MyStaticValue.rb.getString("measure"),
					5, new Forest());
			natureForest = makeLibrary(
					MyStaticValue.rb.getString("familyName"), 7, natureForest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Forest makeLibrary(String path, int nature, Forest forest)
			throws Exception {
		BufferedReader br = IOUtil.getReader(path, "UTF-8");
		String temp = null;

		while ((temp = br.readLine()) != null) {
			// 是否有下一个
			boolean hasNext = true;
			// 是否是一个词
			boolean isWords = true;
			// 树状
			WoodInterface branch = forest;
			char[] chars = temp.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				if (chars.length == (i + 1)) {
					isWords = true;
					hasNext = false;
				} else {
					isWords = false;
					hasNext = true;
				}
				int status = 1;
				if (isWords && hasNext) {
					status = 2;
				}

				if (!isWords && hasNext) {
					status = 1;
				}

				if (isWords && !hasNext) {
					status = 3;
				}
				branch.add(new Branch(chars[i], status, nature));
				branch = branch.get(chars[i]);
			}

		}
		return forest;
	}

	public static Forest getForest() {
		if (forest == null) {
			init();
		}
		return forest;
	}

	public static Forest getNatureForest() {
		if (natureForest == null) {
			init();
		}
		return natureForest;
	}
}
