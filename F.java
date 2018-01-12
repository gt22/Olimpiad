import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.valueOf;

public class F {

	private static final BigInteger TWO = valueOf(2);

	public static BigInteger bigIntSqRootFloor(BigInteger x, boolean t)
			throws IllegalArgumentException {
		long time = System.currentTimeMillis();
		if (x.compareTo(BigInteger.ZERO) < 0) {
			throw new IllegalArgumentException("Negative argument.");
		}
		if (x .equals(BigInteger.ZERO) || x.equals(ONE)) {
			return x;
		} // end if
		BigInteger y;
		for (y = t ? TWO.pow((x.bitLength() / 2) + 1) : x.divide(TWO);
			 y.compareTo(x.divide(y)) > 0;
			 y = ((x.divide(y)).add(y)).divide(TWO)) {
			//System.out.println(y);
		}
		//System.out.println("sqrt:" + (System.currentTimeMillis() - time));
		return y;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

		BigInteger n = new BigInteger(console.readLine());
		if (n.equals(ONE)) {
			System.out.println(1);
			return;
		}
		System.out.println(calc(n));
		//System.out.println("time:" + (System.currentTimeMillis() - time));
	}

	private static BigInteger calc(BigInteger n) {
		BigInteger b = bigIntSqRootFloor(n.subtract(ONE), true);
		BigInteger a1 = n.subtract(b.pow(2)), a2 = 	b.add(ONE).pow(2).subtract(n).add(ONE);

		if(a1.compareTo(a2) < 0) { //x1 < y1
			return a1;
		} else {
			return a2;
		}
	}

}
