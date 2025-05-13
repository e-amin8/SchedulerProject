import java.util.List;

public class FCFS extends Scheduler {

    public FCFS(List<PCB> allProcs) {
        super("FCFS", allProcs);
    }

    @Override
    public PCB pickNextProcess() {
        if (readyQueue.isEmpty()) {
            return null;
        } else {
            return readyQueue.get(0);
        }
    }

}
