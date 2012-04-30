import java.io.*;
import java.util.*;
import java.util.regex.*;

class Analyze {
	static char[] buf = new char[4096];
	static Pattern p1 = Pattern.compile("#[^\\n]*\\n", Pattern.CASE_INSENSITIVE);
//	static Pattern p2 = Pattern.compile("#\\s*(include|error|pragma|define|undef|if|elif|else|endif|#|\\d|\\n)", Pattern.CASE_INSENSITIVE);
	static Pattern p2 = Pattern.compile("#\\s*(if|elif)", Pattern.CASE_INSENSITIVE);
	static Pattern p2b = Pattern.compile("#\\s*define", Pattern.CASE_INSENSITIVE);
	static Pattern p3 = Pattern.compile("#\\s*\\w+\\s+", Pattern.CASE_INSENSITIVE);
	static Pattern p4 = Pattern.compile("\\(|\\)|\\||&|>|<|=|!|\\+|\\-|\\*|(?<!\\d)\\d+(?!\\d)|/\\*((?!\\*/).)*\\*/|//[^\\n]\\n|/", Pattern.CASE_INSENSITIVE);
	static Pattern p5 = Pattern.compile("^\\s*(\\S+)(?!\\S)", Pattern.CASE_INSENSITIVE);
	public static void main(String[] args) throws FileNotFoundException, IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter("config.h"));
		HashSet<String> config = new HashSet<String>();
		HashSet<String> define = new HashSet<String>();
		
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
						if(!s2.isEmpty()
						&& !"defined".equalsIgnoreCase(s2))
							config.add(s2);
					}
				}
				else if(p2b.matcher(s1).find()) {
					s1 = p2b.matcher(s1).replaceFirst(" ");
					Matcher m2 = p5.matcher(s1);
					m2.find();
					try {
						s1 = m2.group(1);
						s1 = p4.matcher(s1).replaceAll(" ");
						s1 = s1.trim();
						s1 = s1.split(" ")[0];
						s1 = s1.trim();
						if(!s1.isEmpty())
							define.add(s1);
					}
					catch(Throwable t){}
				}
			}
			
			in.close();
		}
		
		//System.out.println(config.size());
		for(String s : config) {
			if(!define.contains(s)) {
				//System.out.println(s);
				out.write(s);
				out.write("\n");
			}
		}
		out.close();
	}
}
