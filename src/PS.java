import java.util.List;

public class PS extends Scheduler {

    public PS(List<PCB> allProcs) {
        super("PS", allProcs);
    }

    @Override
    public PCB pickNextProcess() {
        if (readyQueue.isEmpty()) {
            return null;
        } else {
            int min = Integer.MAX_VALUE;
            PCB res = null;
            for (PCB pcb : readyQueue) {
                if (pcb.getPriority() < min) {
                    min = pcb.getPriority();
                    res = pcb;
                }
            }
            // System.out.println("Priority Sort selected: " + res.getName() + " with highest priority: " + min);
            return res;
        }
    }

}
