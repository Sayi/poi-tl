package com.deepoove.poi.tl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

public class TemplateParserTest {

	@Test
	public void TestRule() {
		Pattern compile = Pattern.compile("");
//		Pattern compile = Pattern.compile(TemplateResolver.RULER_REGEX);
//		System.out.println(compile.matcher("{{123}}").matches());
//		System.out.println(compile.matcher("{{@ada_123}}").matches());
//		System.out.println(compile.matcher("{{#ada_123}}").matches());
//		System.out.println(compile.matcher("{{#ada_@23}}").matches());
//		System.out.println(compile.matcher("{{ad a}}").matches());
//		System.out.println(compile.matcher("{123}").matches());
//		System.out.println(compile.matcher("{ada}}").matches());
//		System.out.println(compile.matcher("{ada}}ad").matches());
//		Matcher matcher = compile.matcher("{{ada_123}}");
//		System.out.println(matcher);
//		
//		Matcher matcher2 = compile.matcher("{{ada}}ad{{sayi}}");
//		while(matcher2.find())
//			System.out.println(matcher2.group());
//
//		//String str = "{{ada}}ad";
//		//System.out.println(str.matches(TemplateParser.RULER_REGEX));
//		
//		
//		String regEx="(\\{\\{)|(\\}\\})";   
//		Pattern p = Pattern.compile(regEx);   
//		Matcher m = p.matcher("{{@ada_123}}ada{{sayi}}");   
//		System.out.println( m.replaceAll("").trim());
		
		String str = "ada{{sayi}}dsfsad{dfds{{@ada}}dsfsad{{qishi}}{{youdou}}";
		//toConsole(str.split(TemplateResolver.RULER_REGEX));
		//toConsole(compile.split(str));
		Matcher matcher = compile.matcher(str);
		matcher.matches();
		while (matcher.find()){
			String group = matcher.group();
			System.out.println(group);
		}
		
		
		List<Pair<Integer, Integer>> pairs = new ArrayList<Pair<Integer, Integer>>();
		String text = str;
		int start = 0;
		int end = 0;
		matcher = compile.matcher(text);
		while (matcher.find()){
			String group = matcher.group();
			start= text.indexOf(group, end);
			end = start + group.length();
			pairs.add(new ImmutablePair<Integer, Integer>(start, end));
		}
		
		for (Pair<Integer, Integer> p : pairs){
			System.out.println(p.getLeft() + ":" + p.getValue());
			System.out.println(text.substring(p.getLeft(), p.getRight()));
		}
		
		
	}
	
	public void toConsole(String[] str){
		if (str == null) System.out.println("null");
		for (String s : str) System.out.println(s);
	}
	
	
}
