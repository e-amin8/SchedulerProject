import java.util.List;

public class IO {

    private static String state = "IDLE";

    public static String getState() {
        return state;
    }

    public static void execute(PCB proc, int ioBurst) {
        
        state = "RUNNING";

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            System.out.println("Error in IO device...dumping stack trace.");
            e.printStackTrace();
        }

        int val = proc.getIoBursts().get(0) - ioBurst;
        List<Integer> res = proc.getIoBursts();
        res.set(0, val);
        proc.setIoBursts(res);

    }
}
