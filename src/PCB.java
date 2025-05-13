import java.util.List;

public class PCB {

    // PCB object
    private String name;
    private String state;
    private int id;
    private int priority;
    private int arrival;
    private List<Integer> cpuBursts;
    private List<Integer> ioBursts;

    // the statistics of process execution
	private int startTime, finishTime, turnaroundTime, waitingTime;

    public PCB(String name, int id, int priority, int arrival, List<Integer> cpuBursts, List<Integer> ioBursts) {
        super();
        this.name = name;
        this.state = "NEW";
        this.id = id;
        this.priority =  priority;
        this.arrival = arrival;
        this.cpuBursts = cpuBursts;
        this.ioBursts = ioBursts;
        this.startTime = -1;
        this.finishTime = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }
    
    public int getArrival() {
        return arrival;
    }

    public void setArrival(int arrival) {
        this.arrival = arrival;
    }

    public List<Integer> getCpuBursts() {
        return cpuBursts;
    }

    public void setCpuBurst(List<Integer> cpuBursts) {
        this.cpuBursts = cpuBursts;
    }

    public List<Integer> getIoBursts() {
        return ioBursts;
    }

    public void setIoBursts(List<Integer> ioBursts) {
        this.ioBursts = ioBursts;
    }
    // private int startTime, finishTime, turnaroundTime, waitingTime;

    public int getStartTime() { 
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
        this.turnaroundTime = finishTime - arrival;
    }

    public int getTAT() {
        return turnaroundTime;
    }

    public void setTAT(int turnaroundTime) { 
        this.turnaroundTime = turnaroundTime;
    }

    public int getWT() {
        return waitingTime;
    }

    public void setWT(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public String toString() {
        return "Process [name=" + name +", id=" + id + " ]";  
    }
}
