import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class G {
	private static List<List<CityCostPair>> flights;

	public static void main(String[] args) throws IOException {
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		init(console);
		loop(console);
	}

	private static void init(BufferedReader console) throws IOException {
		int[] in = readArgs(console.readLine(), 2);
		flights = new ArrayList<>(in[0]);
		readFlights(console, in[1]);
		console.readLine();
		/*flights = new ArrayList<>(100000);
		for (int i = 2; i < 100000; i++) {
			addFlight(1, i, 30);
			if (i != 50000) {
				addFlight(50000, i, 23);
			}
		}
		removeFlight(1, 2);
		removeFlight(1, 50000);
		removeFlight(1, 99999);
		findFlight(1, 50000);
		findFlight(1, 50000);*/
	}

	private static void loop(BufferedReader console) {
		console.lines().forEach(req -> {
			int[] arg = readArgs(req, 4);
			switch (arg[0]) {
				case 1: {
					addFlight(arg[1], arg[2], arg[3]);
					break;
				}
				case 2: {
					removeFlight(arg[1], arg[2]);
					break;
				}
				case 3: {
					System.out.println(findFlight(arg[1], arg[2]));
					break;
				}
				case 4: {
					break;
				}
			}
		});
	}

	private static <T> void fill(List<T> l, Supplier<T> val, int until) {
		for (int i = l.size(); i < until; ++i) {
			l.add(val.get());
		}
	}

	private static List<CityCostPair> expand(int until) {
		ArrayList<CityCostPair> ret;
		fill(flights, ArrayList::new, until);
		flights.add(ret = new ArrayList<>());
		return ret;
	}

	private static void addFlight(int from, int to, int cost) {
		//long time = System.currentTimeMillis();
		//Convert 1..n to 0..n-1
		--from;
		--to;
		List<CityCostPair> fl, tl;
		try {
			fl = flights.get(from);
		} catch (IndexOutOfBoundsException e) {
			fl = expand(from);
			//System.out.println("Expanding " + from);
		}
		try {
			tl = flights.get(to);
		} catch (IndexOutOfBoundsException e) {
			tl = expand(to);
			//System.out.println("Expanding " + to);
		}
		fl.add(new CityCostPair(to, cost));
		tl.add(new CityCostPair(from, cost));
		//System.out.println("Adding " + from + ":" + to + ":" + cost + ":::" + (System.currentTimeMillis() - time));
	}

	private static void removeFlight(int from, int to) {
		//long time = System.currentTimeMillis();
		//Convert 1..n to 0..n-1
		--from;
		--to;
		List<CityCostPair> fl = flights.get(from);
		List<CityCostPair> tl = flights.get(to);
		fl.remove(new CityCostPair(to, 0));
		tl.remove(new CityCostPair(from, 0));
		//System.out.println("Removing " + from + ":" + to + ":::" + (System.currentTimeMillis() - time));
	}

	private static int[] readArgs(String line, int count) {
		int[] ret = new int[count];
		StringBuilder numBuilder = new StringBuilder();
		int i = 0;
		for (char c : line.toCharArray()) {
			if (c == ' ') {
				ret[i++] = Integer.parseInt(numBuilder.toString());
				numBuilder = new StringBuilder();
				if (i == count) {
					break;
				}
			} else numBuilder.append(c);
		}
		if (i < count) {
			ret[i] = Integer.parseInt(numBuilder.toString());
		}
		return ret;
	}

	private static long findFlight(int from, int to) {
		//long time = System.currentTimeMillis();
		//Convert 1..n to 0..n-1
		--from;
		--to;
		int minCost = Integer.MAX_VALUE;
		List<CityCostPair> fs, ts;

		try {
			fs = flights.get(from);
			ts = flights.get(to);
		} catch (IndexOutOfBoundsException e) {
			return -1;
		}
		fs.sort(null);
		ts.sort(null);
		int i = 0, j = 0;
		while (i < fs.size() && j < ts.size()) {
			CityCostPair f = fs.get(i), t = ts.get(j);
			if (f.city == t.city) {
				int cost = f.cost + t.cost;
				if (cost < minCost) {
					minCost = cost;
				}
				++i;
				++j;
			} else {
				if (f.city == to) { //Straight flight
					if (f.cost < minCost) {
						minCost = f.cost;
					}
				} else if (t.city == from) { //Straight flight 2
					if (t.cost < minCost) {
						minCost = t.cost;
					}
				}
				if (f.city < t.city) {
					++i;
				} else {
					++j;
				}
			}
		}
		//System.out.println("Searching " + from + ":" + to + ":::" + (System.currentTimeMillis() - time));
		return minCost == Integer.MAX_VALUE ? -1 : minCost;
	}

	private static void readFlights(BufferedReader console, int count) throws IOException {
		for (int f = 0; f < count; ++f) {
			int[] flt = readArgs(console.readLine(), 3);
			addFlight(flt[0], flt[1], flt[2]);
		}
	}

	private static class CityCostPair implements Comparable<CityCostPair> {
		int city;
		int cost;

		CityCostPair(int city, int cost) {
			this.city = city;
			this.cost = cost;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			CityCostPair that = (CityCostPair) o;

			return city == that.city;
		}

		@Override
		public int hashCode() {
			return city;
		}

		@Override
		public int compareTo(CityCostPair o) {
			return Integer.compare(city, o.city);
		}

		@Override
		public String toString() {
			return city + ":" + cost;
		}
	}
}
