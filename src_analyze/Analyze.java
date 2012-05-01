import java.io.*;

class Analyze {
	static byte[] buf = new byte[4096];
	public static void main(String[] args) throws FileNotFoundException, IOException {
		for(String fname : args) {
			FileInputStream fis = new FileInputStream(fname);
			
			int len;
			StringBuilder sb = new StringBuilder();
			while ((len = fis.read(buf)) > 0) {
				sb.append(buf);
			}
			
			//System.out.println(sb.length());
		}
	}
}