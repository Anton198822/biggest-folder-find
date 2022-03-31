import java.io.File;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.concurrent.ForkJoinPool;

import static java.lang.Double.parseDouble;

public class Main {
    private final static Number BYTES_IN_KB = new BigInteger("1024");
    private final static Number BYTES_IN_MB = new BigInteger("1048576");
    private final static Number BYTES_IN_GB = new BigInteger("1073741824");
    private final static Number BYTES_IN_TB = new BigInteger("1099511627776");

    private final static String REGEX = "[BKMGT][b]?";
    private final static String KB_REGEX = "[0-9]+[,][0-9]{1,2}[K][b]?";
    private final static String MB_REGEX = "[0-9]+[,][0-9]{1,2}[M][b]?";
    private final static String GB_REGEX = "[0-9]+[,][0-9]{1,2}[G][b]?";
    private final static String TB_REGEX = "[0-9]+[,][0-9]{1,2}[T][b]?";

    public static void main(String[] args) {

        String folderPath = "C:\\Users\\Anton\\java_basics";
        File file = new File(folderPath);

        long start = System.currentTimeMillis();

        FolderSizeCalculator calculator = new FolderSizeCalculator(file);

        ForkJoinPool pool = new ForkJoinPool();
        long size = pool.invoke(calculator);

        System.out.println(size);
        System.out.println(getHumanReadableSize(size));
        System.out.println(getSizeFromHumanReadable(getHumanReadableSize(size)));

        long duration = System.currentTimeMillis() - start;
        System.out.println(duration + " ms");

    }

    public static long getFolderSize(File folder) {
        if (folder.isFile()) {
            return folder.length();
        }
        long sum = 0;
        File[] files = folder.listFiles();
        for (File file : files) {
            sum += getFolderSize(file);
        }
        return sum;
    }

    public static String getHumanReadableSize(long size) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        if (size < BYTES_IN_KB.longValue()) {
            return size + "B";
        } else if (size < BYTES_IN_MB.longValue()) {
            return decimalFormat.format((double) size / BYTES_IN_KB.longValue()) + "Kb";
        } else if (size < BYTES_IN_GB.longValue()) {
            return decimalFormat.format((double) size / BYTES_IN_MB.longValue()) + "Mb";
        } else if (size < BYTES_IN_TB.longValue()) {
            return decimalFormat.format((double) size / BYTES_IN_GB.longValue()) + "Gb";
        }
        return decimalFormat.format((double) size / BYTES_IN_TB.longValue()) + "Tb";
    }

    public static long getSizeFromHumanReadable(String size) {
        double v = parseDouble(size.replaceAll(REGEX, "")
                .replace(',', '.'));
        if (size.matches(KB_REGEX)) {
            return Math.round(v * BYTES_IN_KB.longValue());
        } else if (size.matches(MB_REGEX)) {
            return (long) (v * BYTES_IN_MB.longValue());
        } else if (size.matches(GB_REGEX)) {
            return (long) (v * BYTES_IN_GB.longValue());
        } else if (size.matches(TB_REGEX)) {
            return (long) (v * BYTES_IN_TB.longValue());
        } else {
            return (long) v;
        }
    }
}
