import java.io.*;
import java.util.*;
import java.util.regex.*;

class Analyze {
	static char[] buf = new char[4096];
	static Pattern p1 = Pattern.compile("#[^\\n]*\\n", Pattern.CASE_INSENSITIVE);
//	static Pattern p2 = Pattern.compile("#\\s*(include|error|pragma|define|undef|if|elif|else|endif|#|\\d|\\n)", Pattern.CASE_INSENSITIVE);
	static Pattern p2 = Pattern.compile("#\\s*(if|elif)", Pattern.CASE_INSENSITIVE);
	static Pattern p3 = Pattern.compile("#\\s*\\w+\\s+", Pattern.CASE_INSENSITIVE);
	static Pattern p4 = Pattern.compile("defined|\\(|\\)|\\||&|>|<|=|!|\\+|\\-|\\*|(?<!\\d)\\d+(?!\\d)|/\\*((?!\\*/).)*\\*/|//[^\\n]\\n|/", Pattern.CASE_INSENSITIVE);
	public static void main(String[] args) throws FileNotFoundException, IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter("config.h"));
		HashSet<String> config = new HashSet<String>();
		
		for(String fname : args) {
			FileReader in = new FileReader(fname);
			
			int len;
			StringBuilder sb = new StringBuilder();
			while ((len = in.read(buf)) > 0) {
				sb.append(buf);
			}
			
			Matcher m = p1.matcher(sb.toString());
			//System.out.println(m.matches());
			//System.out.println(sb.toString());
			
			while(m.find()) {
				String s1 = m.group().trim();
				if(p2.matcher(s1).find()) {
					s1 = p3.matcher(s1).replaceFirst(" ");
					s1 = p4.matcher(s1).replaceAll(" ");
					for(String s2 : s1.split(" ")) {
						s2 = s2.trim();
						if(!s2.isEmpty())
							config.add(s2);
					}
				}
			}
			
			in.close();
		}
		
			
		for(String s : config) {
			System.out.println(s);
			out.write(s);
			out.write("\n");
		}
		out.close();
	}
}
