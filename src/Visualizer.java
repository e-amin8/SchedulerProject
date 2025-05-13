public class Visualizer {
    private String cpuState;
    private int cpuBursts;

    private String ioState;
    private int ioBursts;

    public Visualizer() {
        this.cpuState = "IDLE";
        this.cpuBursts = 0;
        this.ioState = "IDLE";
        this.ioBursts = 0;
    }

    public void updateCPU(String state, int cpuBursts) {
        cpuState = state;
        this.cpuBursts = cpuBursts;
        if (cpuState == "RUNNING") ioState = "IDLE";
        display();
    }

    public void updateIO(String state, int ioBursts) {
        ioState = state;
        this.ioBursts = ioBursts;
        if (ioState == "RUNNING") cpuState = "IDLE";
        display();
    }

    public void display() {

        String cpuBox = String.format("""
            +----------------------+
            |       CPU           |
            | State: %-10s   |
            | Bursts: %-9d   |
            +----------------------+
            """, cpuState, cpuBursts);
        
        String ioBox = String.format("""
            +----------------------+
            |       I/O           |
            | State: %-10s   |
            | Bursts: %-9d   |
            +----------------------+
            """, ioState, ioBursts);
        
        System.out.println(cpuBox);
        System.out.println(ioBox);
        System.out.println("===========");
        
    }

}
