//package com.dingke.myapp.utils;
//
//import oshi.SystemInfo;
//import oshi.hardware.*;
//import oshi.software.os.FileSystem;
//import oshi.software.os.OSFileStore;
//import oshi.software.os.OperatingSystem;
//import oshi.software.os.OperatingSystemVersion;
//import java.text.DecimalFormat;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Resources {
//    private static SystemInfo systemInfo = new SystemInfo();
//    private static HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
//    private static OperatingSystem osSystem = systemInfo.getOperatingSystem();
//
//    public static double convByteToG(long bytes){
//        double d = 1024*1024*1024*1.0;
//        return bytes * 1.0 / d;
//    }
//
//
//    public <T> Map<String,T>  LocalInfo(){
//        Map<String,T> map = new HashMap<>();
//        //todo cpu
//        CentralProcessor centralProcessor = hardwareAbstractionLayer.getProcessor();
//        String cpuName = centralProcessor.getName();
//        Integer cpuLogicCnt = centralProcessor.getLogicalProcessorCount();
//        Integer cpuPhysicalCnt = centralProcessor.getPhysicalProcessorCount();
//        map.put("cpuName", (T) cpuName);
//        map.put("逻辑核数", (T) cpuLogicCnt);
//        map.put("物理核数", (T) cpuPhysicalCnt);
//
//        //todo memory
//        GlobalMemory globalMemory = hardwareAbstractionLayer.getMemory();
//        long total = globalMemory.getTotal();
//        String total_G = new DecimalFormat("#.##").format(convByteToG(total));
//        map.put("总内存", (T) total_G);
//
//
//        //todo 硬盘
//        FileSystem fileSystem = osSystem.getFileSystem();
//        long diskTotalSize = 0;
//        long freeTotalSize = 0;
//        OSFileStore[] fileStores = fileSystem.getFileStores();
//        for(int i = 0; (fileStores != null) && (i < fileStores.length); i++){
//            OSFileStore osFileStore = fileStores[i];
//            long storeTotalSpace = osFileStore.getTotalSpace();
//            diskTotalSize = diskTotalSize + storeTotalSpace;
//            long storeFreeSpace = osFileStore.getUsableSpace();
//            freeTotalSize = freeTotalSize + storeFreeSpace;
//        }
//        map.put("磁盘总空间", (T) String.format("%.2f", convByteToG(diskTotalSize)));
//        map.put("可用", (T) String.format("%.2f", convByteToG(freeTotalSize)));
//        //todo system
//        String familyName = osSystem.getFamily();
//        String manufacturer = osSystem.getManufacturer();
//        OperatingSystemVersion operatingSystemVersion = osSystem.getVersion();
//        map.put("操作系统", (T) familyName);
//        map.put("供应商", (T) manufacturer);
//        map.put("版本号", (T) (operatingSystemVersion.getCodeName() + " " +
//                        operatingSystemVersion.getVersion() + " " +
//                        operatingSystemVersion.getBuildNumber()));
//        return map;
//    }
//
//    public static void main(String[] args) {
//        Resources r = new Resources();
//        Map<String,Object> m = r.LocalInfo();
//        System.out.println(m);
//    }
//
//
//
//
//}
