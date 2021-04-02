package jieba;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tst {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String regEx="[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";
		Pattern p = Pattern.compile(regEx);
		String text = "我爸妈退休了，想出去旅游，但是我忙着考试，没办法抽空，希望能找个专业的导游，要包车哦";
		System.out.println(text);
		Matcher m = p.matcher(text);
		
		String newString = m.replaceAll("").trim();
		JiebaSegmenter segmenter = new JiebaSegmenter();
        System.out.println(newString);
        System.out.println(segmenter.sentenceProcess(newString));

	}

}
