import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class C {

	private static final String[][] tests = {
			{"abacaba", "aba", "5"},
			{"AbCa", "Aba", "3"},
			{"bbbxxyaxyaxbxyoo", "abo", "15"}
	};
	private static final boolean TEST = false;
	public static void main(String[] args) throws IOException {
		if(TEST) test();
		else run();
	}

	private static void test() {
		for(String[] test : tests) {
			String s = search(test[0], test[1]);
			System.out.println(s + ":::" + (s.length() == Integer.parseInt(test[2])));
		}
	}

	private static void run() throws IOException {
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(search(console.readLine(), console.readLine()));
	}

	private static boolean isUnique(char[] ch) {
		Set<Character> st = new HashSet<>();
		for(char c : ch) {
			if(!st.add(c)) return false;
		}
		return true;
	}

	private static String search(String base, String t) {
		Queue<String> queue = new LinkedList<>();
		Set<String> hashStorage = new HashSet<>();
		StringBuilder trunc = new StringBuilder();
		for(char c : base.toCharArray()) {
			if(t.contains(String.valueOf(c))) {
				trunc.append(c);
			}
		}
		queue.add(trunc.toString());
		char[] ta = t.toCharArray();
		//boolean isUnique = isUnique(ta);

		String s;
		while ((s = queue.poll()) != null) {
			char[] sa = s.toCharArray();
			boolean found = false;
			int j = 0;
			List<String> sub = new ArrayList<>(sa.length);
			for (int i = 0; i < sa.length; i++) {
				char c = sa[i];
				if (c == ta[j]) {
					j++;
					if (j == ta.length) {
						j = 0;
						found = true;
					}
					/*List<Integer> charIndexes = new ArrayList<>();
					for (int k = i; k < sa.length; k++) {
						char n = sa[k];
						if (n == c) {
							charIndexes.add(k);
						} else {
							break;
						}

					}
					StringBuilder cutB = new StringBuilder();
					int prevK = 0;
					for(int k : charIndexes) {
						cutB.append(s.substring(prevK, k));
						prevK = k;
					}
					cutB.append(s.substring(prevK + 1));*/
					String cut = s.substring(0, i) + s.substring(i + 1);
					if (hashStorage.add(cut)) {
						sub.add(cut);
					}
				}
			}
			if (found) {
				queue.addAll(sub);
			} else {
				return restoreString(base, s, t);
			}
		}
		return "";
	}

	private static String restoreString(String base, String trunc, String search) {
		StringBuilder ret = new StringBuilder();
		char[] ca = base.toCharArray();
		char[] ta = trunc.toCharArray();
		int j = 0;
		for (char c : ca) {
			if (j < ta.length && c == ta[j]) {
				j++;
				ret.append(c);
			} else if (search.indexOf(c) < 0) {
				ret.append(c);
			}
		}
		return ret.toString();
	}

}
