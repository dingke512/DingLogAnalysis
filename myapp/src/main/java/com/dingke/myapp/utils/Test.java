//package com.dingke.myapp.utils;
//
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import oshi.SystemInfo;
//import oshi.hardware.Baseboard;
//import oshi.hardware.CentralProcessor;
//import oshi.hardware.ComputerSystem;
//import oshi.hardware.Firmware;
//import oshi.hardware.GlobalMemory;
//import oshi.hardware.HWDiskStore;
//import oshi.hardware.HWPartition;
//import oshi.hardware.HardwareAbstractionLayer;
//import oshi.hardware.NetworkIF;
//import oshi.hardware.PowerSource;
//import oshi.hardware.UsbDevice;
//import oshi.software.os.FileSystem;
//import oshi.software.os.OSFileStore;
//import oshi.software.os.OperatingSystem;
//import oshi.software.os.OperatingSystemVersion;
//import oshi.util.Util;
//
//public class Test{
//
//
//    private static final int OSHI_WAIT_SECOND = 1000;
//    private static SystemInfo systemInfo = new SystemInfo();
//    private static HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
//    private static OperatingSystem osSystem = systemInfo.getOperatingSystem();
//
//    public static double convByteToG(long bytes){
//        double d = 1024*1024*1024*1.0;
//        return bytes * 1.0 / d;
//    }
//
//    public static void getHardwareInfo()
//    {
////    	SystemInfo systemInfo = new SystemInfo();
////    	HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
////    	OperatingSystem osSystem = systemInfo.getOperatingSystem();
//
//        //cpu信息
//        CentralProcessor centralProcessor = hardwareAbstractionLayer.getProcessor();
//        long[] prevTicks = centralProcessor.getSystemCpuLoadTicks();
//        Util.sleep(OSHI_WAIT_SECOND);
//        long[] ticks = centralProcessor.getSystemCpuLoadTicks();
//
//        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
//        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
//        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
//        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
//        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
//        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
//        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
//        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq ;
//
//        String cpuRate = new DecimalFormat("#.##%").format(cSys * 1.0 / totalCpu);
//        String idleRate = new DecimalFormat("#.##%").format(idle * 1.0 / totalCpu);
//        String userRate = new DecimalFormat("#.##%").format(user * 1.0 / totalCpu);
//        System.out.println("cpu 使用率：" + cpuRate);
//        System.out.println("cpu 当前使用率：" + idleRate);
//        System.out.println("用户使用率：" + userRate);
//
//        String cpuName = centralProcessor.getName();
//        long cpuFreq = centralProcessor.getVendorFreq();
//        boolean is64Bit = centralProcessor.isCpu64bit();
//        String cpuMode = centralProcessor.getModel();
//        double cpuLoad = centralProcessor.getSystemCpuLoad();
//        double cpuAvgLoad = centralProcessor.getSystemLoadAverage();
//        long cpuLogicCnt = centralProcessor.getLogicalProcessorCount();
//        long cpuPhysicalCnt = centralProcessor.getPhysicalProcessorCount();
//
//        System.out.println(String.format("cpu 基础信息：名称【%s】频率【%d】是否64位【%b】型号【%s】负载【%.2f】平均负载【%.2f】逻辑核数【%d】物理核数【%d】",
//                cpuName, cpuFreq, is64Bit, cpuMode, cpuLoad, cpuAvgLoad, cpuLogicCnt, cpuPhysicalCnt));
//
//
//        //--------------------- 内存信息
//        GlobalMemory globalMemory = hardwareAbstractionLayer.getMemory();
//        long total = globalMemory.getTotal();
//        long free = globalMemory.getAvailable();
//        String total_G = new DecimalFormat("#.##").format(convByteToG(total));
//        String free_G = new DecimalFormat("#.##").format(convByteToG(free));
//        String useRate = new DecimalFormat("#.##%").format((total - free)*1.0/total);
//        String freeRate = new DecimalFormat("#.##%").format(free * 1.0 / total);
//        System.out.println("总内存：" + total_G + "G 可用内存:" + free_G + "G 占比（" + freeRate + ") 使用率:" + useRate );
//
//        long swapTotal = globalMemory.getSwapTotal();
//        long swapUsed = globalMemory.getSwapUsed();
//        String swapTotal_G = new DecimalFormat("#.##").format(convByteToG(swapTotal));
//        String swapUsed_G = new DecimalFormat("#.##").format(convByteToG(swapUsed));
//        String swapUseRate = new DecimalFormat("#.##%").format(swapUsed * 1.0 / swapTotal);
//        String swapFreeRate = new DecimalFormat("#.##%").format((swapTotal - swapUsed) * 1.0 / swapTotal);
//        System.out.println("总内存【虚拟】：" + swapTotal_G + "G 已用内存:" + swapUsed_G + "G 占比（" + swapUseRate + ")  可用占比:" + swapFreeRate );
//
//        //-------------------- 硬盘信息
//        HWDiskStore hwDiskStore[] = hardwareAbstractionLayer.getDiskStores();
//        for(int i = 0; i < hwDiskStore.length; i++){
//            String diskName = hwDiskStore[i].getName();
//            String diskModel = hwDiskStore[i].getModel();
//            long diskSize = hwDiskStore[i].getSize();
//            String diskSerial = hwDiskStore[i].getSerial();
//            System.out.println(String.format("硬盘信息：名称【%s】序列号【%s】模式【%s】大小【%.2f】G",diskName, diskSerial, diskModel, convByteToG(diskSize)));
//            HWPartition hwPartition[] = hwDiskStore[i].getPartitions();
//            for(int j = 0; (hwPartition != null) && (j < hwPartition.length); j++){
//                long partitionSize = hwPartition[j].getSize();
//                String partitionName = hwPartition[i].getName();
//                System.out.println(String.format("硬盘分区信息：名称【%s】大小【%.2f】G",partitionName, convByteToG(partitionSize)));
//            }
//        }
//
//        //操作系统信息
//        String familyName = osSystem.getFamily();
//        String manufacturer = osSystem.getManufacturer();
//        OperatingSystemVersion operatingSystemVersion = osSystem.getVersion();
//        FileSystem fileSystem = osSystem.getFileSystem();
//        System.out.println(String.format("操作系统【%s】供应商【%s】版本号【%s】", familyName, manufacturer,
//                operatingSystemVersion.getCodeName() + " " +
//                        operatingSystemVersion.getVersion() + " " +
//                        operatingSystemVersion.getBuildNumber()));
//        long diskTotalSize = 0;
//        long freeTotalSize = 0;
//        OSFileStore[] fileStores = fileSystem.getFileStores();
//        for(int i = 0; (fileStores != null) && (i < fileStores.length); i++){
//            OSFileStore osFileStore = fileStores[i];
//            String storeName = osFileStore.getName();
//            long storeTotalSpace = osFileStore.getTotalSpace();
//            diskTotalSize = diskTotalSize + storeTotalSpace;
//            long storeFreeSpace = osFileStore.getUsableSpace();
//            freeTotalSize = freeTotalSize + storeFreeSpace;
//            String storeType = osFileStore.getType();
//            String storeVolume = osFileStore.getVolume();
//            System.out.println(String.format("分区【%d】信息:名称【%s】盘符【%s】类型【%s】空间【%.2f】G 可用空间【%.2f】G", i, storeName,
//                    storeVolume, storeType, convByteToG(storeTotalSpace), convByteToG(storeFreeSpace)));
//        }
//        System.out.println(String.format("磁盘总空间【%.2f】G, 可用【%.2f】G", convByteToG(diskTotalSize), convByteToG(freeTotalSize)));
//
//        //jvm信息
//
//
//        //网络信息
//        NetworkIF[] networkIFs = hardwareAbstractionLayer.getNetworkIFs();
//        for(int i = 0; i < networkIFs.length; i++){
//            NetworkIF networkIF = networkIFs[i];
//            String ipv4= "";
//            for(int j = 0; j < networkIF.getIPv4addr().length; j++){
//                ipv4 = ipv4 + "." + networkIF.getIPv4addr()[j];
//            }
//
//            String ipv6 = "";
//            for(int j = 0; j < networkIF.getIPv6addr().length; j++){
//                ipv6 = ipv6 + "." + networkIF.getIPv6addr()[j];
//            }
//
//            networkIF.updateNetworkStats();
//            String netWorkIinfo = String.format("网卡 %d 信息：名称【%s】显示名称【%s 】MAC【%s】IPV4【%s】IPV6【%s】接收字节数【%d】发送字节数【%d】接收数据包【%d】发送数据包【%d】是否可达【%d】响应错误【%d】速率【%d】bits/秒", i,
//                    networkIF.getName(),
//                    networkIF.getDisplayName(),
//                    networkIF.getMacaddr(),
//                    ipv4,
//                    ipv6,
//                    networkIF.getBytesRecv(),
//                    networkIF.getBytesSent(),
//                    networkIF.getPacketsRecv(),
//                    networkIF.getPacketsSent(),
//                    networkIF.getInErrors(),
//                    networkIF.getOutErrors(),
//                    networkIF.getSpeed());
//            System.out.println(netWorkIinfo);
//        }
//
//        //usb接口信息
//        UsbDevice[] usbDevices = hardwareAbstractionLayer.getUsbDevices(false);
//        if(usbDevices == null){
//            System.out.println("获取USB设备为空。");
//        }
//        for(int i = 0; (usbDevices != null) && (i < usbDevices.length); i++){
//            UsbDevice usbDevice = usbDevices[i];
//            String usbInfo = String.format("USB %d 信息:名称【%s】供应商【%s】供应商ID【%s】商品ID【%s】序列号【%s】", i,
//                    usbDevice.getName(),
//                    usbDevice.getVendor(),
//                    usbDevice.getVendorId(),
//                    usbDevice.getProductId(),
//                    usbDevice.getSerialNumber());
//            System.out.println(usbInfo);
//
//        }
//
//        //电源信息
//        PowerSource[] powerSources = hardwareAbstractionLayer.getPowerSources();
//        for(int i = 0; i < powerSources.length; i++){
//            PowerSource powerSource = powerSources[i];
//            System.out.println(String.format("电源%d信息：名称【%s】剩余电商【%f】剩余时间【%f】", i,
//                    powerSource.getName(),
//                    powerSource.getRemainingCapacity(),
//                    powerSource.getTimeRemaining()));
//        }
//
//        //计算机系统信息
//        ComputerSystem computerSystem = hardwareAbstractionLayer.getComputerSystem();
//        Firmware firmware = computerSystem.getFirmware();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        String firmWareReleaseDate = firmware.getReleaseDate().toString();
//        String firmwareStr = String.format("制造商(%s) 名称(%s) 描述信息(%s) 版本(%s) 发布时间(%s)",
//                firmware.getManufacturer(),
//                firmware.getName(),
//                firmware.getDescription(),
//                firmware.getVersion(),
//                firmWareReleaseDate);
//        Baseboard baseboard = computerSystem.getBaseboard();
//        String baseBoardStr = String.format("制造商(%s) 型号(%s) 版本信息(%s) 序列号(%s)",
//                baseboard.getManufacturer(),
//                baseboard.getModel(),
//                baseboard.getVersion(),
//                baseboard.getSerialNumber());
//        String computerSystemStr = String.format("系统信息：制造商【%s】型号【%s】序列号【%s】固件信息【%s】外壳信息【%s】",
//                computerSystem.getManufacturer(),
//                computerSystem.getModel(),
//                computerSystem.getSerialNumber(),
//                firmwareStr,
//                baseBoardStr);
//        System.out.println(computerSystemStr);
//
//    }
//
//    public static void main(String[] args){
//        getHardwareInfo();
//    }
//}