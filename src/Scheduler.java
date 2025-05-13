import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public abstract class Scheduler {

    protected List<PCB> allProcs;
    protected List<PCB> readyQueue;
    protected List<PCB> finishedProcs;
    protected List<Integer> cpuBursts;
    protected List<Integer> ioBursts;
    protected String alg;
    public String log; // log output, accessed by main method
    protected PCB curProc;
    protected int cpuBurst;
    protected int cpuWait;
    protected int ioBurst;
    protected int speed;
    protected int systemTime;
    protected int simulationMode;
    protected Visualizer visualizer;

    public Scheduler(String alg, List<PCB> allProcs) {
        this.allProcs = allProcs;
        this.alg = alg;
        log = "System output\n";
        systemTime = 0;
        speed = 1;
        simulationMode = 0;
        readyQueue = new ArrayList<PCB>();
        finishedProcs = new ArrayList<PCB>();
        visualizer = new Visualizer();
    }

    public void schedule() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Would you like to simulate automatically or manually? (0 for auto, 1 for manual): ");
        if (sc.hasNextLine()) {
            simulationMode = sc.nextInt();
        }
        System.out.println("Set burst speed (fps) by entering an integer (0<i<=30): ");
        if (sc.hasNextLine()) {
            speed = sc.nextInt();
        }
        // sc.nextLine();  // Consume the leftover newline character
        System.out.println("Scheduling Algorithm: " + alg);

        // continues checking for new processes added
        while (!readyQueue.isEmpty() || !allProcs.isEmpty()) {
            log += "\n==============\n";
            Iterator<PCB> iterator = allProcs.iterator();
            while(iterator.hasNext()) {
                PCB proc = iterator.next();
                if (proc.getArrival() <= systemTime) {
                    log += "Adding process: " + proc.getName() + " to ready queue @time: " + systemTime + "\n";
                    readyQueue.add(proc);
                    iterator.remove();
                }
            }
            if (!readyQueue.isEmpty()) {
                curProc = pickNextProcess();
                curProc.setStartTime(systemTime);
                curProc.setState("RUNNING");
                log += "Running Process: " + curProc.getName() + " @Time: " + systemTime + "\n";
            } else {
                systemTime++;
            }

            for (PCB proc : readyQueue) {
                if (proc.getState() == "READY") {
                    proc.setState("WAITING");
                }
            }
            
            if (!readyQueue.isEmpty()) {
                System.out.println("Running Process: " + curProc.getName() + " id: " + curProc.getID() + " @Time: " + systemTime);
                cpuBursts = curProc.getCpuBursts();
                ioBursts = curProc.getIoBursts();
                // continually execute bursts and alternate between both cpu & io
                while(!cpuBursts.isEmpty() || !ioBursts.isEmpty()) {
                    cpuBursts = curProc.getCpuBursts();
                    ioBursts = curProc.getIoBursts();
                    cpuBurst = cpuBursts.isEmpty() ? 0 : cpuBursts.get(0);
                    ioBurst = ioBursts.isEmpty() ? 0 : ioBursts.get(0);
                    if (cpuBurst > 0) {
                        visualizer.updateCPU("RUNNING", cpuBursts.size());

                        CPU.execute(curProc, speed);
                        systemTime++;
                        for(PCB proc : readyQueue) {
                            if (proc.getState() == "WAITING") {
                                proc.setWT(proc.getWT()+1);
                            }
                        }
                    } else if (ioBurst > 0) {
                        visualizer.updateIO("RUNNING", ioBursts.size());

                        IO.execute(curProc, speed);
                        systemTime++;
                        cpuWait++;
                        for(PCB proc : readyQueue) {
                            if (proc != curProc) {
                                proc.setWT(proc.getWT()+1);
                            }
                        }

                    } else {
                        if (cpuBursts.size() > 0) cpuBursts.remove(0);
                        if (ioBursts.size() > 0) ioBursts.remove(0);
                    }

                    //simulation mode
                    if (simulationMode == 1) {
                        System.out.println("Press Enter for next simulation step.");
                        sc.nextLine();
                    } else {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                curProc.setFinishTime(systemTime);
                curProc.setState("TERMINATED");
                finishedProcs.add(curProc);
                log += "Completed process @Time..." + systemTime + ", Turnaround time...." + curProc.getTAT() + ", and Wait time....." 
                + curProc.getWT() + "\n";
                // System.out.println("Finished process..." + curProc.getName() + ", @Time: " + systemTime);
                readyQueue.remove(curProc);
                visualizer.updateCPU("IDLE", cpuBursts.size());
                visualizer.updateIO("IDLE", ioBursts.size());
            // print();
            }
        }
        System.out.println("CPU Utilization = " + (100 - ((double)cpuWait/systemTime)) + "%\n");
        sc.close();
    }

    // selects next task based on algorithm
    public abstract PCB pickNextProcess();

    // prints All Processes, Remaining CPU Processes, Remaining IO Processes, and Finished Processes
    // Basic version of Visualizer
    public void print() {
        int rows = Math.max(readyQueue.size() - 1, 0);
        System.out.println("+----------------+-----+----+----------+");
        System.out.println("| Processes      | CPU | IO | Finished |");
        System.out.println("+----------------+-----+----+----------+");
        for (int i = 0; i <= rows; i++) {
            PCB curr = readyQueue.get(i);
            System.out.printf("| %-14s | %-3s | %-2s | %-8d |%n",
                // curr.getName(), curr.getCpuBursts().size(),
                // curr.getIoBursts().size(), finishedProcs.size());
                curr.getName(), CPU.getState(),
                IO.getState(), finishedProcs.size());
        }
        System.out.println("+----------------+-----+----+----------+");
    }
}
