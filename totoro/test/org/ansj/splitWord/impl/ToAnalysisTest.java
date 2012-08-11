package org.ansj.splitWord.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.ansj.domain.Term;
import org.ansj.library.TwoWordLibrary;
import org.ansj.splitWord.Analysis;
import org.ansj.splitWord.analysis.ToAnalysis;

public class ToAnalysisTest {
	public static void main(String[] args) throws IOException {
		Reader reader = new InputStreamReader(new FileInputStream("/Users/ansj/Documents/快盘/冒死记录中国神秘事件（真全本）.txt"), "GBK");
		Analysis toAnalysis = new ToAnalysis(reader);
		Term next = null;
		new TwoWordLibrary() ;
		long start = System.currentTimeMillis();
		while ((next = toAnalysis.next()) != null) {
			// System.out.println(next.getName() + ":" + next.maxNature);
			// sb.append(next.getName()+":"+next.maxNature+"/ ") ;
			// sb.append("\n") ;
//			System.out.println(next.getName() + ":" + next.getOffe());
		}
		System.out.println(System.currentTimeMillis() - start);
//		IOUtil.Writer("/Users/ansj/Documents/快盘/冒死.txt", "UTF-8", sb.toString()) ;
	}
}
