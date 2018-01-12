import java.io.IOException;
import java.io.InputStreamReader;

public class D {

	private static Sensor[] sensors;
	private static int H, T;
	private static int maxDrain = 0;

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

	private static Sensor[] readSensors(InputStreamReader console, int n) throws IOException {
		Sensor[] ret = new Sensor[n];
		for (int i = 0; i < n; i++) {
			int[] s = readArgs(console, 2);
			ret[i] = new Sensor(s[0], s[1]);
		}
		return ret;
	}

	private static void calcSum() {
		for (Sensor s : sensors) {
			maxDrain += s.v;
		}
	}

	//static long testTime = 0;
	//static int testCount = 0;
	//private static Map<Integer, Integer> waterCache = new HashMap<>();
	private static boolean test(int x) {
		//long time = System.currentTimeMillis();
		//testCount++;
		int h = 0;

		for (int i = 0; i < T; i++) {
			h += x;
			//Integer ch = waterCache.get(h);
			//if(ch != null) {
			//	h = ch;
			//	continue;
			//}
			//int ph = h;
			for (int j = sensors.length - 1; j >= 0; j--) {
				Sensor s = sensors[j];
				if (h >= s.h) {
					h -= Math.min(h - s.h + 1, s.v);
				}
			}
			//waterCache.put(ph, h);
			if (h >= H) return true;
		}
		//testTime += System.currentTimeMillis() - time;
		return false;
	}

	private static int findX() {
		//long time = System.currentTimeMillis();
		int step = maxDrain + H;
		int prevStep;
		int x = maxDrain + (H / 2);
		Boolean prevRead = null;
		while (true) {
			prevStep = step;
			if(step > 1) step /= 2;
			if (test(x)) {
				if (prevRead != null && !prevRead && prevStep== 1) {
					break;
				}
				x -= step;
				prevRead = Boolean.TRUE;
			} else {
				x += step;
				if (prevRead != null && prevRead && prevStep == 1) {
					break;
				}

				prevRead = Boolean.FALSE;
			}
		}
		//System.out.println("Test: " + testTime + "/" + testTime);
		//System.out.println("Find: " + (System.currentTimeMillis() - time - testTime));
		return x;
	}

	public static void main(String[] args) throws IOException {
		InputStreamReader console = new InputStreamReader(System.in);
		int[] in = readArgs(console, 3);
		H = in[1];
		T = in[2];
		sensors = readSensors(console, in[0]);
		calcSum();
		System.out.println(findX());
	}

	private static class Sensor {
		int h, v;

		public Sensor(int h, int v) {
			this.h = h;
			this.v = v;
		}
	}

}
