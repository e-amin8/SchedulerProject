import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        System.out.println("Starting scheduler...!");
        Thread.sleep(200);
        Scanner sc = null;
        @SuppressWarnings("resource")
        Scanner input = new Scanner(System.in);
        // System.out.println("===DEBUG===="+args.length);
        if (args.length == 1) {
            System.out.println("User passed file...looking for file " + args[0]);
            File processFile = new File(args[0]);
            sc = new Scanner(processFile);
            // System.out.println(args);
        } else if (args.length == 0) {
            System.out.println("Provide file name that contains processes: ");
            String fileName = input.nextLine().trim();
            File processFile = new File(fileName);
            sc = new Scanner(processFile);
        } else {
            System.out.println("You have entered too many arguments, please try again.");
        }
        String line;
        int id = 1;
        ArrayList<PCB> allProcs = new ArrayList<>();

        while(sc.hasNextLine()) {
            line = sc.nextLine();
            String[] arr = line.split(" ");
            ArrayList<Integer> cpuTimes = new ArrayList<>();
            ArrayList<Integer> ioTimes = new ArrayList<>();
            
            String name = arr[0];
            int arrivalTime = Integer.parseInt(arr[1].trim());
            int priority = Integer.parseInt(arr[2].trim());

            for (int i = 3; i < arr.length; i++) {
                if (i%2==0) {
                    ioTimes.add(Integer.parseInt(arr[i]));
                } else {
                    cpuTimes.add(Integer.parseInt(arr[i]));
                }
            }

            PCB proc = new PCB(name, id, priority, arrivalTime, cpuTimes, ioTimes);
            allProcs.add(proc);

            id++;
        }
        sc.close();

        System.out.println("Enter scheduling algorithm (FCFS/SJF/PS): ");
        String alg = input.nextLine();
        Scheduler scheduler = null;

        switch(alg.trim().toUpperCase()) {
            case "FCFS":
                scheduler = new FCFS(allProcs);
                break;
            case "SJF":
                scheduler = new SJF(allProcs);
                break;
            case "PS":
                scheduler = new PS(allProcs);
                break;
        }
        scheduler.schedule();
        String printLogs = "Y"; // default to yes, so logs will print
        System.out.println("Print process logs? (Y/N): ");
        if (input.hasNextLine()) {
            printLogs = input.nextLine();
        }
        if (printLogs.trim().equalsIgnoreCase("Y")) {
            System.out.println(scheduler.log);
        }
        // input.close();

    }
}
