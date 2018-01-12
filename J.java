import javafx.util.Pair;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class J {


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
		int[] in = readArgs(console, 2);
		int cities = in[0];
		int roadsCount = in[1];
		int x = cities;
		boolean[] hasExit = new boolean[cities];
		boolean[] hasEntrance = new boolean[cities];
		Set<Pair<Integer, Integer>> roads = new HashSet<>();
		for(int i = 0; i < roadsCount; i++) {
			int[] road = readArgs(console, 2);
			int from = road[0] - 1;
			int to = road[1] - 1;
			if(roads.add(new Pair<>(from, to))) {
				if (!hasExit[from]) {
					if (!hasExit[to] && !hasEntrance[to]) {
						x--;
					}
					hasExit[from] = true;
					hasEntrance[to] = true;
				}
			}
			System.out.print(x);
			System.out.print(' ');
		}
	}

}
