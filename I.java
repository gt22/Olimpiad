import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class I {


	static Score[] idx;
	static List<Score> min, max;
	static int[] offlinePoints;
	static int totalPoints;

	private static int[] readArgs(InputStreamReader r, int count) throws IOException {
		int[] ret = new int[count];
		StringBuilder numBuilder = new StringBuilder();
		char c;
		int i = 0;
		while ((c = (char) r.read()) != '\n') {
			if (c == ' ') {
				ret[i++] = Integer.parseInt(numBuilder.toString());
				numBuilder = new StringBuilder();
			} else {
				numBuilder.append(c);
			}
		}
		if (i < count) {
			ret[i] = Integer.parseInt(numBuilder.toString());
		}
		return ret;
	}

	public static void main(String[] args) throws IOException {
		InputStreamReader console = new InputStreamReader(System.in);
		int[] in = readArgs(console, 4);
		totalPoints = in[3];
		offlinePoints = readArgs(console, in[1]);
		idx = new Score[in[0]];
		readTable(console, in[0], in[1]);
		List<Score> lst = Arrays.asList(idx);
		min = new ArrayList<>(lst);
		max = new ArrayList<>(lst);
		min.sort(Score.min().reversed());
		max.sort(Score.max().reversed());
		for (int i = 0; i < in[2]; i++) {
			int[] cmd = readArgs(console, 4);
			switch (cmd[0]) {
				case 1: {
					Score s = idx[cmd[1] - 1];
					int maxx = search(s, true) + 1;
					int minx = search(s, false) + 1;
					System.out.println(maxx + " " + minx);
					break;
				}
				case 2: {
					idx[cmd[1] - 1].setOffline(cmd[2] - 1, cmd[3]);
					min.sort(Score.min().reversed());
					max.sort(Score.max().reversed());
					break;
				}
				case 3: {
					System.out.println("Min: " + min);
					System.out.println("Max: " + max);
				}
			}
		}
	}

	private static int search(Score s, boolean searchMax) {
		if(searchMax) {
			for (int i = 0; i < min.size(); i++) {
				Score sc = min.get(i);
				if (s.smax >= sc.smin) {
					return i;
				}
			}
			return min.size();
		} else {
			for (int i = max.size() - 1; i >= 0; i--) {
				Score sc = max.get(i);
				if (s.smin < sc.smax) {
					return s.smax == sc.smax ? i : i + 1;
				}
			}
			return 0;
		}

	}

	private static void readTable(InputStreamReader r, int n, int m) throws IOException {
		for (int i = 0; i < n; i++) {
			idx[i] = new Score(readArgs(r, m));
		}
	}

	private static class Score {
		int[] scores;
		int smin, smax;

		Score(int[] s) {
			scores = s;
			for (int i = 0; i < s.length; i++) {
				int score = scores[i];
				smin += score;
				if (score < totalPoints - offlinePoints[i]) {
					smax += score;
				} else {
					smax += totalPoints;
				}
			}
		}

		static Comparator<Score> min() {
			return Comparator.comparingInt(s -> s.smin);
		}

		static Comparator<Score> max() {
			return Comparator.comparingInt(s -> s.smax);
		}

		static Comparator<Score> minToMax() {
			return (s1, s2) -> Integer.compare(s1.smax, s2.smin);
		}

		static Comparator<Score> maxToMin() {
			return (s1, s2) -> Integer.compare(s1.smin, s2.smax);
		}

		void setOffline(int j, int score) {
			smin += score - scores[j];
			smax -= totalPoints - score;
		}

		@Override
		public String toString() {
			return smin + ":" + smax;
		}
	}

}
