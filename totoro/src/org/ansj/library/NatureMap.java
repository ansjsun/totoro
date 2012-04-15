package org.ansj.library;

import static org.ansj.library.NatureEnum.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NatureMap {
	public static NatureEnum[] natures = null;
	public static Map<NatureEnum,Integer> natrueMap = new HashMap<NatureEnum,Integer>() ;
	

	static {
		List<NatureEnum> all = new ArrayList<NatureEnum>();
		all.add(NULL) ;//木有这个东西
		all.add(n); // 名词
		all.add(nr); // 人名
		all.add(nr1); // 汉语姓氏
		all.add(nr2); // 汉语名字
		all.add(nrj); // 日语人名
		all.add(nrf); // 音译人名
		all.add(ns); // 地名
		all.add(nsf); // 音译地名
		all.add(nt); // 机构团体名
		all.add(nz); // 其它专名
		all.add(nl); // 名词性惯用语
		all.add(ng); // 名词性语素
		all.add(t); // 时间词
		all.add(tg); // 时间词性语素
		all.add(s); // 处所词
		all.add(f); // 方位词
		all.add(v); // 动词
		all.add(vd); // 副动词
		all.add(vn); // 名动词
		all.add(vshi); // 动词“是”
		all.add(vyou); // 动词“有”
		all.add(vf); // 趋向动词
		all.add(vx); // 形式动词
		all.add(vi); // 不及物动词（内动词）
		all.add(vl); // 动词性惯用语
		all.add(vg); // 动词性语素
		all.add(a); // 形容词
		all.add(ad); // 副形词
		all.add(an); // 名形词
		all.add(ag); // 形容词性语素
		all.add(al); // 形容词性惯用语
		all.add(b); // 区别词
		all.add(bl); // 区别词性惯用语
		all.add(z); // 状态词
		all.add(r); // 代词
		all.add(rr); // 人称代词
		all.add(rz); // 指示代词
		all.add(rzt); // 时间指示代词
		all.add(rzs); // 处所指示代词
		all.add(rzv); // 谓词性指示代词
		all.add(ry); // 疑问代词
		all.add(ryt); // 时间疑问代词
		all.add(rys); // 处所疑问代词
		all.add(ryv); // 谓词性疑问代词
		all.add(rg); // 代词性语素
		all.add(m); // 数词
		all.add(mq); // 数量词
		all.add(q); // 量词
		all.add(qv); // 动量词
		all.add(qt); // 时量词
		all.add(d); // 副词
		all.add(p); // 介词
		all.add(pba); // 介词“把”
		all.add(pbei); // 介词“被”
		all.add(c); // 连词
		all.add(cc); // 并列连词
		all.add(u); // 助词
		all.add(uzhe); // 着
		all.add(ule); // 了); //喽
		all.add(uguo); // 过
		all.add(ude1); // 的); //底
		all.add(ude2); // 地
		all.add(ude3); // 得
		all.add(usuo); // 所
		all.add(udeng); // 等 等等 云云
		all.add(uyy); // 一样 一般 似的 般
		all.add(udh); // 的话
		all.add(uls); // 来讲 来说 而言 说来
		all.add(uzhi); // 之
		all.add(ulian); // 连 （“连小学生都会”）
		all.add(e); // 叹词
		all.add(y); // 语气词(delete yg)
		all.add(o); // 拟声词
		all.add(h); // 前缀
		all.add(k); // 后缀
		all.add(x); // 字符串
		all.add(xx); // 非语素字
		all.add(xu); // 网址URL
		all.add(w); // 标点符号
		all.add(wkz); // 左括号，全角：（ 〔 ［ ｛ 《 【 〖 〈 半角：( [ { <
		all.add(wky); // 右括号，全角：） 〕 ］ ｝ 》 】 〗 〉 半角： ) ] { >
		all.add(wyz); // 左引号，全角：“); //‘ 『
		all.add(wyy); // 右引号，全角：” ’ 』
		all.add(wj); // 句号，全角：。
		all.add(ww); // 问号，全角：？ 半角：?
		all.add(wt); // 叹号，全角：！ 半角：!
		all.add(wd); // 逗号，全角：， 半角：,
		all.add(wf); // 分号，全角：； 半角： ,
		all.add(wn); // 顿号，全角：、
		all.add(wm); // 冒号，全角：： 半角： :
		all.add(ws); // 省略号，全角：…… …
		all.add(wp); // 破折号，全角：—— －－ ——－ 半角：--- ----
		all.add(wb); // 百分号千分号，全角：％ ‰ 半角：%
		all.add(wh); // 单位符号，全角：￥ ＄ ￡ ° ℃ 半角：$
		natures = new NatureEnum[all.size()];
		natures = all.toArray(natures);
		
		for (int i = 0; i < natures.length; i++) {
			natrueMap.put(natures[i], i) ;
		}
	}

	
}
