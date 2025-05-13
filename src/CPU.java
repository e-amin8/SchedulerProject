import java.util.List;

public class CPU {

    private static String state = "IDLE";
    
    public static String getState() {
        return state;
    }
    
    public static void execute(PCB proc, int cpuBurst) {

        state = "RUNNING";

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            System.out.println("Error in CPU...dumping stack trace.");
            e.printStackTrace();
        }

        int val = proc.getCpuBursts().get(0) - cpuBurst;
        List<Integer> res = proc.getCpuBursts();
        res.set(0, val);
        proc.setCpuBurst(res);

        state = "IDLE";
    }

}
