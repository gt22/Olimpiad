import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;

public class B {


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

	private static int[] generate(int len) {
		Random rnd = new Random();
		return IntStream.range(0, len).map(i -> rnd.nextInt((int) (1e4))).toArray();
	}
	private static HashMap<Integer, BigInteger> sums = new HashMap<>();

	public static void main(String[] args) throws IOException {
		InputStreamReader console = new InputStreamReader(System.in);
		//int len = readArgs(console, 1)[0];
		//int[] arr = readArgs(console, len);
		int len = 50000;
		int[] arr = generate(len);
		System.out.println(Arrays.toString(arr));
		long time = System.currentTimeMillis();
		BigInteger maxSum = null;
		int startIdx = 0, endIdx = 0;
		for(int i = 0; i < len; i++) {
			int start = arr[i];
			BigInteger sum = sums.computeIfAbsent(start, BigInteger::valueOf);
		}
		System.out.println(maxSum);
		System.out.println((startIdx + 1) + " " + (endIdx + 1));
		System.out.println(System.currentTimeMillis() - time);
	}


}
