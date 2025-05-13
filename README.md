## Introduction

This is project from my Operating Systems course. Implemented First Come First Serve and Shortest Job First CPU scheduling algorithms.
## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Classes
Below is a brief description of vital classes

PCB - represents a process control block, maintains job statistics (start time, wait time, etc.) for final calculations
CPU - represents the CPU completing tasks, program uses execute method to assign a task
Scheduler - represents a scheduler and acts as the "heart" of the program, maintains list of all CPU & IO bursts
