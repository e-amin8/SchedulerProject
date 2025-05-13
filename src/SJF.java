import java.util.List;

public class SJF extends Scheduler {

    public SJF(List<PCB> allProcs) {
        super("SJF", allProcs);
    }

    @Override
    public PCB pickNextProcess() {
        if (readyQueue.isEmpty()) {
            return null;
        }
        
        PCB shortestJob = null;
        int shortestBurst = Integer.MAX_VALUE;
        
        for (PCB pcb : readyQueue) {
            // finds the next shortest cpu burst
            if (!pcb.getCpuBursts().isEmpty()) {
                int nextBurst = pcb.getCpuBursts().get(0);
                if (nextBurst < shortestBurst) {
                    shortestBurst = nextBurst;
                    shortestJob = pcb;
                }
            }
        }
        
        if (shortestJob != null) {
            // System.out.println("Shortest Job First selecte d: " + shortestJob.getName() + " with burst: " + shortestBurst);
        }
        return shortestJob;
    }

}
