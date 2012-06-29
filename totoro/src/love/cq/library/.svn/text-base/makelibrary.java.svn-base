package love.cq.library;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import love.cq.domain.Str;
import love.cq.util.IOUtil;
import love.cq.util.MyStaticValue;

public class MakeLibrary {
	private static String charEncoding = "UTF-8";
	private static String path = MyStaticValue.rb.getString("library") ;
	private static String measure = MyStaticValue.rb.getString("measure") ;
	private static String sortLibrary = MyStaticValue.rb.getString("sortLibrary") ;
	
	private static TreeMap<Str, Str> treeMap = new TreeMap<Str, Str>();

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
		englishLibrary() ;
		numberLibrary() ;
		sortLibrary();
		System.out.println(System.currentTimeMillis() - start);
	}

	/**
	 * 构造简单的英语词典 status:4
	 * @throws Exception
	 */
	public static void englishLibrary() throws Exception{
		String[] stringArray = MyStaticValue.ENGLISH.split("") ;
		String str = natureLibrary(stringArray,4) ;
		IOUtil.Writer(InitDictionary.arraysEnglishPath, "UTF-8", str);
	}
	/**
	 * 构造简单的数字词典 status:5
	 * @throws Exception
	 */
	public static void numberLibrary() throws Exception{
		String[] stringArray = MyStaticValue.NUMBER.split("") ;
		String str = natureLibrary(stringArray,5) ;
		IOUtil.Writer(InitDictionary.arraysNumberPath, "UTF-8", str);
	}
	/**
	 * 构造简单的具有通用词性的词典,比如数字.英语...
	 * @throws Exception
	 */
	public static String natureLibrary(String[] StringArray , int nature) {
		StringBuilder sb = new StringBuilder() ;
		for (int i = 0; i < StringArray.length; i++) {
			if(StringArray[i]!=null&&StringArray[i].trim().length()>0){
				sb.append(StringArray[i] + "	" + nature) ;
				sb.append('\n') ;
			}
		}
		return sb.toString() ;
	}
	/**
	 * 重构词典.根据结构体的规则将base数组模型构建出来 base模型的规则首先按顺序排列.其次按hashCode排列
	 * @throws IOException
	 */
	
	public static void sortLibrary() throws IOException{
		BufferedReader reader = IOUtil.getReader(path, charEncoding);
		sortLibrary(reader,2,3) ;
		write(treeMap,sortLibrary);
		
	}
	public static void sortLibrary(BufferedReader reader,int status2,int status3) throws IOException {
		
		String temp = null;
		
		Str str = null;
		Str tempStr;
		String line;
		String[] temps;
		Str strTemp = null;
		while ((line = reader.readLine()) != null) {
			temps = line.split("	");
			temp = temps[0];
			int length = temp.length() + 1;
			for (int i = 1; i < length; i++) {
				tempStr = new Str(temp.substring(0, i));
				str = new Str(tempStr.getStr());
				if ((strTemp = treeMap.get(tempStr)) != null) {
					if (i == length - 1) {
						if (strTemp.getStatu() == 2) {
							if (temps.length > 2)
								strTemp.setNature(temps[2]);
						} else {
							if (temps.length > 2)
								strTemp.setNature(temps[2]);
						}
						if (strTemp.getStatu() == 1) {
							strTemp.setStatu(status2);
						}
					} else {
						if (strTemp.getStatu() == 3) {
							strTemp.setStatu(status2);
						}
					}
					continue;
				} else {
					if (i == length - 1) {
						if (temps.length > 2)
							str.setNature(temps[2]);
						str.setStatu(status3);
					} else {
						str.setStatu(1);
					}
				}
				treeMap.put(tempStr, str);
			}
		}
		
	}
	
	public static void write(TreeMap treeMap,String sortLibrary){
		StringBuilder sb = new StringBuilder();
		Iterator<Str> it = treeMap.values().iterator();
		while (it.hasNext()) {
			sb.append(it.next());
			sb.append("\n");
		}
		sb.append("垃圾数据	3");
		IOUtil.Writer(sortLibrary, "UTF-8", sb.toString());
	}

}
