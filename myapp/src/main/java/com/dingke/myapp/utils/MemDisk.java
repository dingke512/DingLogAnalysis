//package com.dingke.myapp.utils;
//
//import java.io.File;
//import java.lang.management.ManagementFactory;
//import java.text.DecimalFormat;
//
//import com.dingke.myapp.vo.Resource;
//import com.sun.management.OperatingSystemMXBean;
//import oshi.SystemInfo;
//import oshi.hardware.CentralProcessor;
//
//public class MemDisk {
//
//    public static void main(String[] args)
//    {
////        getMemInfo();
////        System.out.println();
//        getDiskInfo();
//        sysInfo();
//    }
//
//    public static void getDiskInfo()
//    {
//        File[] disks = File.listRoots();
//        for(File file : disks)
//        {
//            System.out.print(file.getPath() + "    ");
//            System.out.print("空闲未使用 = " + file.getFreeSpace() / 1024 / 1024 + "M" + "    ");// 空闲空间
//            System.out.print("已经使用 = " + file.getUsableSpace() / 1024 / 1024 + "M" + "    ");// 可用空间
//            System.out.print("总容量 = " + file.getTotalSpace() / 1024 / 1024 + "M" + "    ");// 总空间
//            System.out.println();
//        }
//    }
//
//    public static void getMemInfo()
//    {
//        OperatingSystemMXBean mem = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
//        System.out.println("Total RAM：" + mem.getTotalPhysicalMemorySize() / 1024 / 1024 + "MB");
//        System.out.println("Available　RAM：" + mem.getFreePhysicalMemorySize() / 1024 / 1024 + "MB");
//
//    }
//
//    public static void getCpuRate(){
//
//    }
//
//    public static void sysInfo(){
//        OperatingSystemMXBean mem = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
//        float m = (mem.getTotalPhysicalMemorySize() / 1024 / 1024);
//        System.out.println(String.format("%.2f",(m/1024)));
//
//
//        while(true) {
//            SystemInfo systemInfo = new SystemInfo();
//            CentralProcessor processor = systemInfo.getHardware().getProcessor();
//            long[] prevTicks = processor.getSystemCpuLoadTicks();
//            long[] ticks = processor.getSystemCpuLoadTicks();
//            long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
//            long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
//            long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
//            long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
//            long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
//            long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
//            long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
//            long totalCpu = user + nice + cSys + idle + iowait + irq + softirq ;
//            String cpuRate = new DecimalFormat("#.##%").format(cSys * 1.0 / totalCpu);
//            String idleRate = new DecimalFormat("#.##%").format(idle * 1.0 / totalCpu);
//            String userRate = new DecimalFormat("#.##%").format(user * 1.0 / totalCpu);
//            System.out.println("cpu 使用率：" + cpuRate);
//            System.out.println("cpu 当前使用率：" + idleRate);
//            System.out.println("用户使用率：" + userRate);
//        }
//
//    }
//
//
//
//}