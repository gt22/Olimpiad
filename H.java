import java.io.IOException;
import java.io.InputStreamReader;

public class H {


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

	private static final double TWO_PI = Math.PI * 2;
	private static final double STEP = Math.toRadians(1e-4);
	private static Triangle[] triangles;

	private static int getCoins(double rad) {
		int coins = 0;
		for(Triangle t : triangles) {
			if(rad >= t.validStart && rad <= t.validEnd) {
				coins++;
			}
		}
		//System.out.println(deg + ":" + coins);
		//System.out.println();
		return coins;
	}

	public static void main(String[] args) throws IOException {
		InputStreamReader console = new InputStreamReader(System.in);
		int count = readArgs(console, 1)[0];
		triangles = new Triangle[count];
		for (int i = 0; i < count; i++) {
			int[] tr = readArgs(console, 6);
			triangles[i] = new Triangle(tr[0], tr[1], tr[2], tr[3], tr[4], tr[5]);
		}
		int maxCoins = 0;

		for (double i = 0; i < TWO_PI; i += STEP) {
			int coins = getCoins(i);
			if (coins > maxCoins) {
				maxCoins = coins;
				if (maxCoins == count) { //Can't get more coins than triangles
					break;
				}
			}
		}
		System.out.println(maxCoins);
	}

	static class Triangle {
		double a1, a2, b1, b2, c1, c2;
		double validStart = TWO_PI, validEnd = 0;
		Triangle(double a1, double a2, double b1, double b2, double c1, double c2) {
			this.a1 = a1;
			this.a2 = a2;
			this.b1 = b1;
			this.b2 = b2;
			this.c1 = c1;
			this.c2 = c2;
			calculateValid();
		}

		private void calculateValid() {
			Boolean wasValid = null;
			for(double i = 0; i < TWO_PI; i += STEP) {
				double sin = Math.sin(i);
				double cos = Math.cos(i);
				double[] rot = rotate(sin, cos);
				rot[0] = Math.round(rot[0] * 1000d) / 1000d;
				rot[1] = Math.round(rot[1] * 1000d) / 1000d;
				rot[2] = Math.round(rot[2] * 1000d) / 1000d;
				if(rot[0] <= rot[1] && rot[0] <= rot[2]) {
					if(i < validStart) {
						validStart = i;
					}
					if (i > validEnd) {
						validEnd = i;
					}
					wasValid = true;
				} else if(wasValid != null && wasValid) {
					break;
				}
			}
		}

		double[] rotate(double sin, double cos) {
			return new double[]{
					rotateY(a1, a2, sin, cos),
					rotateY(b1, b2, sin, cos),
					rotateY(c1, c2, sin, cos)
			};
		}

		private double rotateY(double x, double y, double sin, double cos) {
			return x * sin + y * cos;
		}
	}

}
