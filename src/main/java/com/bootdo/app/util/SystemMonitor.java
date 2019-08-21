package com.bootdo.app.util;

import com.bootdo.app.domain.CPUModel;
import org.apache.commons.collections.map.HashedMap;
import org.hyperic.sigar.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Administrator on 2019/6/10 0010.
 */
public class SystemMonitor {

    private void property() throws UnknownHostException {
        Runtime r = Runtime.getRuntime();
        Properties props = System.getProperties();
        InetAddress addr;
        addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress();
        Map<String, String> map = System.getenv();
        String userName = map.get("USERNAME");// 获取用户名
        String computerName = map.get("COMPUTERNAME");// 获取计算机名
        String userDomain = map.get("USERDOMAIN");// 获取计算机域名
        System.out.println("用户名:    " + userName);
        System.out.println("计算机名:    " + computerName);
        System.out.println("计算机域名:    " + userDomain);
        System.out.println("本地ip地址:    " + ip);
        System.out.println("本地主机名:    " + addr.getHostName());
        System.out.println("JVM可以使用的总内存:    " + r.totalMemory());
        System.out.println("JVM可以使用的剩余内存:    " + r.freeMemory());
        System.out.println("JVM可以使用的处理器个数:    " + r.availableProcessors());
        System.out.println("Java的运行环境版本：    " + props.getProperty("java.version"));
        System.out.println("Java的运行环境供应商：    " + props.getProperty("java.vendor"));
        System.out.println("Java供应商的URL：    " + props.getProperty("java.vendor.url"));
        System.out.println("Java的安装路径：    " + props.getProperty("java.home"));
        System.out.println("Java的虚拟机规范版本：    " + props.getProperty("java.vm.specification.version"));
        System.out.println("Java的虚拟机规范供应商：    " + props.getProperty("java.vm.specification.vendor"));
        System.out.println("Java的虚拟机规范名称：    " + props.getProperty("java.vm.specification.name"));
        System.out.println("Java的虚拟机实现版本：    " + props.getProperty("java.vm.version"));
        System.out.println("Java的虚拟机实现供应商：    " + props.getProperty("java.vm.vendor"));
        System.out.println("Java的虚拟机实现名称：    " + props.getProperty("java.vm.name"));
        System.out.println("Java运行时环境规范版本：    " + props.getProperty("java.specification.version"));
        System.out.println("Java运行时环境规范供应商：    " + props.getProperty("java.specification.vender"));
        System.out.println("Java运行时环境规范名称：    " + props.getProperty("java.specification.name"));
        System.out.println("Java的类格式版本号：    " + props.getProperty("java.class.version"));
        System.out.println("Java的类路径：    " + props.getProperty("java.class.path"));
        System.out.println("加载库时搜索的路径列表：    " + props.getProperty("java.library.path"));
        System.out.println("默认的临时文件路径：    " + props.getProperty("java.io.tmpdir"));
        System.out.println("一个或多个扩展目录的路径：    " + props.getProperty("java.ext.dirs"));
        System.out.println("操作系统的名称：    " + props.getProperty("os.name"));
        System.out.println("操作系统的构架：    " + props.getProperty("os.arch"));
        System.out.println("操作系统的版本：    " + props.getProperty("os.version"));
        System.out.println("文件分隔符：    " + props.getProperty("file.separator"));
        System.out.println("路径分隔符：    " + props.getProperty("path.separator"));
        System.out.println("行分隔符：    " + props.getProperty("line.separator"));
        System.out.println("用户的账户名称：    " + props.getProperty("user.name"));
        System.out.println("用户的主目录：    " + props.getProperty("user.home"));
        System.out.println("用户的当前工作目录：    " + props.getProperty("user.dir"));
    }

    public static List<Map<String,Object>> memory() throws SigarException {
        Sigar sigar = new Sigar();
        Mem mem = sigar.getMem();
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map = new HashedMap();
        // 内存总量
        map.put("RAMtotal",mem.getTotal() / 1024L);
        map.put("RAMused",mem.getUsed() / 1024L);
        map.put("RAMfree",mem.getFree() / 1024L);
        list.add(map);
        return list;
        /*Swap swap = sigar.getSwap();
        // 交换区总量
        System.out.println("交换区总量:    " + swap.getTotal() / 1024L + "K av");
        // 当前交换区使用量
        System.out.println("当前交换区使用量:    " + swap.getUsed() / 1024L + "K used");
        // 当前交换区剩余量
        System.out.println("当前交换区剩余量:    " + swap.getFree() / 1024L + "K free");*/
    }

    public static CPUModel cpu() throws SigarException {
        Sigar sigar = new Sigar();

        List<Map<String,Object>> list = new ArrayList<>();
        CPUModel cpuModel = new CPUModel();

        CpuPerc perc = sigar.getCpuPerc();
        System.out.println("整体cpu的占用情况:");
        cpuModel.setCpuPercIdle(CpuPerc.format(perc.getIdle()));//获取当前cpu的空闲率
        cpuModel.setCpuPercCombined(CpuPerc.format(perc.getCombined()));//获取当前cpu的占用率
        cpuModel.setCpuPercIdleStyle("width:"+CpuPerc.format(perc.getIdle()));//获取当前cpu的空闲率
        cpuModel.setCpuPercCombinedStyle("width:"+CpuPerc.format(perc.getCombined()));//获取当前cpu的占用率


        CpuInfo infos[] = sigar.getCpuInfoList();
        CpuPerc cpuList[] = null;
        cpuList = sigar.getCpuPercList();
        for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用
            Map<String,Object> map = new HashedMap();
            CpuInfo info = infos[i];
            System.out.println("第" + (i + 1) + "块CPU信息");
            map.put("index","第" + (i + 1) + "块CPU信息");// CPU的总量MHz
            map.put("mhz",info.getMhz());// CPU的总量MHz
            map.put("vendor",info.getVendor());// 获得CPU的卖主，如：Intel
            map.put("model",info.getModel());// 获得CPU的类别，如：Celeron
            map.put("cacheSize",info.getCacheSize());// 缓冲存储器数量
            map.put("user",CpuPerc.format(cpuList[i].getUser()));// 用户使用率
            map.put("userStyle","width:"+CpuPerc.format(cpuList[i].getUser()));// 用户使用率
            map.put("sys",CpuPerc.format(cpuList[i].getSys()));// 系统使用率
            map.put("sysStyle","width:"+CpuPerc.format(cpuList[i].getSys()));// 系统使用率
            map.put("wait",CpuPerc.format(cpuList[i].getWait()));// 当前等待率
            map.put("waitStyle","width:"+CpuPerc.format(cpuList[i].getWait()));// 当前等待率
            map.put("nice", CpuPerc.format(cpuList[i].getNice()));//CPU当前错误率
            map.put("niceStyle", "width:"+CpuPerc.format(cpuList[i].getNice()));//CPU当前错误率
            map.put("idle", CpuPerc.format(cpuList[i].getIdle()));//当前空闲率
            map.put("idleStyle", "width:"+CpuPerc.format(cpuList[i].getIdle()));//当前空闲率
            map.put("combined", CpuPerc.format(cpuList[i].getCombined()));//总的使用率
            map.put("combinedStyle", "width:"+CpuPerc.format(cpuList[i].getCombined()));//总的使用率
//            printCpuPerc(cpuList[i]);
            list.add(map);
        }
        cpuModel.setCpuPercList(list);
        return cpuModel;
    }

    private static void printCpuPerc(CpuPerc cpu) {
        System.out.println("CPU用户使用率:    " + CpuPerc.format(cpu.getUser()));// 用户使用率
        System.out.println("CPU系统使用率:    " + CpuPerc.format(cpu.getSys()));// 系统使用率
        System.out.println("CPU当前等待率:    " + CpuPerc.format(cpu.getWait()));// 当前等待率
        System.out.println("CPU当前错误率:    " + CpuPerc.format(cpu.getNice()));//
        System.out.println("CPU当前空闲率:    " + CpuPerc.format(cpu.getIdle()));// 当前空闲率
        System.out.println("CPU总的使用率:    " + CpuPerc.format(cpu.getCombined()));// 总的使用率
    }

    public static void os() {
        OperatingSystem OS = OperatingSystem.getInstance();
        // 操作系统内核类型如： 386、486、586等x86
        System.out.println("操作系统:    " + OS.getArch());
        System.out.println("操作系统CpuEndian():    " + OS.getCpuEndian());//
        System.out.println("操作系统DataModel():    " + OS.getDataModel());//
        // 系统描述
        System.out.println("操作系统的描述:    " + OS.getDescription());
        // 操作系统类型
        // System.out.println("OS.getName():    " + OS.getName());
        // System.out.println("OS.getPatchLevel():    " + OS.getPatchLevel());//
        // 操作系统的卖主
        System.out.println("操作系统的卖主:    " + OS.getVendor());
        // 卖主名称
        System.out.println("操作系统的卖主名:    " + OS.getVendorCodeName());
        // 操作系统名称
        System.out.println("操作系统名称:    " + OS.getVendorName());
        // 操作系统卖主类型
        System.out.println("操作系统卖主类型:    " + OS.getVendorVersion());
        // 操作系统的版本号
        System.out.println("操作系统的版本号:    " + OS.getVersion());
    }

    private static void who() throws SigarException {
        Sigar sigar = new Sigar();
        Who who[] = sigar.getWhoList();
        if (who != null && who.length > 0) {
            for (int i = 0; i < who.length; i++) {
                // System.out.println("当前系统进程表中的用户名" + String.valueOf(i));
                Who _who = who[i];
                System.out.println("用户控制台:    " + _who.getDevice());
                System.out.println("用户host:    " + _who.getHost());
                // System.out.println("getTime():    " + _who.getTime());
                // 当前系统进程表中的用户名
                System.out.println("当前系统进程表中的用户名:    " + _who.getUser());
            }
        }
    }

    public static List<Map<String,Object>> file() throws Exception {
        Sigar sigar = new Sigar();
        List<Map<String,Object>> list = new ArrayList<>();
        FileSystem fslist[] = sigar.getFileSystemList();
        for (int i = 0; i < fslist.length; i++) {
            Map<String,Object> map = new HashedMap();
            FileSystem fs = fslist[i];
            if(fs.getDevName().startsWith("F:")){
                continue;
            }
            map.put("name","分区的盘符名称" + fs.getDevName());// 分区的盘符名称
            map.put("devName",fs.getDevName());// 分区的盘符名称
            map.put("dirName",fs.getDirName());// 分区的盘符路径
            map.put("sysTypeName",fs.getSysTypeName());// 文件系统类型，比如 FAT32、NTFS
            map.put("typeName",fs.getTypeName());// 文件系统类型名，比如本地硬盘、光驱、网络文件系统等
            map.put("type",fs.getType());// 文件系统类型

            FileSystemUsage usage = null;
            usage = sigar.getFileSystemUsage(fs.getDirName());
            switch (fs.getType()) {
                case 0: // TYPE_UNKNOWN ：未知
                    break;
                case 1: // TYPE_NONE
                    break;
                case 2: // TYPE_LOCAL_DISK : 本地硬盘
                    map.put("total",(usage.getTotal()/1024)/1024+"G");// 文件系统总大小
                    map.put("free",(usage.getFree()/1024)/1024+"G");// 文件系统剩余大小
                    map.put("avail",(usage.getAvail()/1024)/1024+"G");// 文件系统可用大小
                    map.put("used",(usage.getUsed()/1024)/1024+"G");// 文件系统已经使用量
                    double usePercent = usage.getUsePercent() * 100D;
                    map.put("usePercent",usePercent+"%");// 文件系统资源的利用率
                    map.put("usePercentStyle","width:"+usePercent+"%");// 文件系统资源的利用率

                    System.out.println(fs.getDevName() + "已经使用量:    " + usage.getUsed() + "KB");

                    break;
                case 3:// TYPE_NETWORK ：网络
                    break;
                case 4:// TYPE_RAM_DISK ：闪存
                    break;
                case 5:// TYPE_CDROM ：光驱
                    break;
                case 6:// TYPE_SWAP ：页面交换
                    break;
            }
            map.put("diskReads",usage.getDiskReads());// 读出
            map.put("diskWrites",usage.getDiskWrites());// 写入
            list.add(map);
        }
        return list;
    }

    public static List<Map<String,Object>> net() throws Exception {
        Sigar sigar = new Sigar();
        List<Map<String,Object>> list = new ArrayList<>();
        String ifNames[] = sigar.getNetInterfaceList();
        for (int i = 0; i < ifNames.length; i++) {
            Map<String,Object> map = new HashedMap();
            String name = ifNames[i];
            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
            map.put("name","网络设备名："+name);// 网络设备名
            map.put("address",ifconfig.getAddress());// IP地址
            map.put("netmask",ifconfig.getNetmask());// 子网掩码
            if ((ifconfig.getFlags() & 1L) <= 0L) {
                System.out.println("!IFF_UP...skipping getNetInterfaceStat");
                continue;
            }
            NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);

            map.put("rxPackets",ifstat.getRxPackets());// 接收的总包裹数
            map.put("txPackets",ifstat.getTxPackets());// 发送的总包裹数
            map.put("rxBytes",ifstat.getRxBytes());// 接收到的总字节数
            map.put("txBytes",ifstat.getTxBytes());// 发送的总字节数
            map.put("rxErrors",ifstat.getRxErrors());// 接收到的错误包数
            map.put("txErrors",ifstat.getTxErrors());// 发送数据包时的错误数
            map.put("rxDropped",ifstat.getRxDropped());// 接收时丢弃的包数
            map.put("txDropped",ifstat.getTxDropped());// 发送时丢弃的包数
            list.add(map);
        }
        return list;
    }

    private static void ethernet() throws SigarException {
        Sigar sigar = null;
        sigar = new Sigar();
        String[] ifaces = sigar.getNetInterfaceList();
        for (int i = 0; i < ifaces.length; i++) {
            NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
            if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress()) || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
                    || NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {
                continue;
            }
            System.out.println(cfg.getName() + "IP地址:" + cfg.getAddress());// IP地址
            System.out.println(cfg.getName() + "网关广播地址:" + cfg.getBroadcast());// 网关广播地址
            System.out.println(cfg.getName() + "网卡MAC地址:" + cfg.getHwaddr());// 网卡MAC地址
            System.out.println(cfg.getName() + "子网掩码:" + cfg.getNetmask());// 子网掩码
            System.out.println(cfg.getName() + "网卡描述信息:" + cfg.getDescription());// 网卡描述信息
            System.out.println(cfg.getName() + "网卡类型" + cfg.getType());//
        }
    }
}
