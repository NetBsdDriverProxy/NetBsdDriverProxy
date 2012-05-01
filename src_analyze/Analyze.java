import java.io.*;
import java.util.*;
import java.util.regex.*;

class Analyze {
	static char[] buf = new char[4096];
	static Pattern p = Pattern.compile("#.*$", Pattern.CASE_INSENSITIVE);
	public static void main(String[] args) throws FileNotFoundException, IOException {
		for(String fname : args) {
			FileReader in = new FileReader(fname);
			BufferedWriter out = new BufferedWriter(new FileWriter("config.h"));
				out.write("ABC");
				out.write("ABC");
			
			int len;
			StringBuilder sb = new StringBuilder();
			while ((len = in.read(buf)) > 0) {
				sb.append(buf);
			}
			
			Matcher m = p.matcher(sb.toString());
			//System.out.println(m.matches());
			//System.out.println(sb.toString());
			
			LinkedList<String> l = new LinkedList<String>();
			while(m.find()) {
				l.add(m.group());
			}
			
			for(String s : l) {
				out.write(s);
				out.write("\n");
			}
			
			out.close();
			in.close();
		}
	}
}