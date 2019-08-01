/**
 * Copyright (2019, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.uitplus.utils;

import java.util.ArrayList;

import com.github.kubesys.kubernetes.api.model.virtualmachine.Lifecycle.CreateAndStartVMFromISO;
import com.github.kubesys.kubernetes.api.model.virtualmachine.Lifecycle.CreateAndStartVMFromImage;
import com.github.kubesys.kubernetes.api.model.virtualmachine.Lifecycle.DeleteVM;
import com.github.kubesys.kubernetes.api.model.virtualmachine.Lifecycle.RebootVM;
import com.github.kubesys.kubernetes.api.model.virtualmachine.Lifecycle.ResetVM;
import com.github.kubesys.kubernetes.api.model.virtualmachine.Lifecycle.ResumeVM;
import com.github.kubesys.kubernetes.api.model.virtualmachine.Lifecycle.StartVM;
import com.github.kubesys.kubernetes.api.model.virtualmachine.Lifecycle.StopVM;
import com.github.kubesys.kubernetes.api.model.virtualmachine.Lifecycle.StopVMForce;
import com.github.kubesys.kubernetes.api.model.virtualmachine.Lifecycle.SuspendVM;
import com.github.uitplus.beans.CreateVMFromISOBean;
import com.github.uitplus.beans.CreateVMFromISOBean.Cdroms;
import com.github.uitplus.beans.CreateVMFromISOBean.Console;
import com.github.uitplus.beans.CreateVMFromISOBean.DataCloudDiskSpecifications;
import com.github.uitplus.beans.CreateVMFromISOBean.Network;
import com.github.uitplus.beans.CreateVMFromISOBean.RootCloudDiskSpecificationFromISO;
import com.github.uitplus.beans.CreateVMFromImageBean;
import com.github.uitplus.beans.CreateVMFromImageBean.RootCloudDiskSpecificationFromTemplate;
import com.github.uitplus.beans.DeleteVMBean;
import com.github.uitplus.beans.RebootVMBean;
import com.github.uitplus.beans.RemoveVMBean;
import com.github.uitplus.beans.ResetVMBean;
import com.github.uitplus.beans.ResumeVMBean;
import com.github.uitplus.beans.StartVMBean;
import com.github.uitplus.beans.StopForceVMBean;
import com.github.uitplus.beans.StopVMBean;
import com.github.uitplus.beans.SuspendVMBean;

/**
 * @author wuheng@otcaix.iscas.ac.cn
 * @author xuyuanjia2017@otcaix.iscas.ac.cn
 * @author xianghao16@otcaix.iscas.ac.cn
 * @author yangchen18@otcaix.iscas.ac.cn
 * @since Sat Jul 27 09:28:49 CST 2019
 **/
public class BeanConvertorUtils {

	public static CreateAndStartVMFromISO toCreateAndStartVMFromISO(CreateVMFromISOBean bean) {
		CreateAndStartVMFromISO image = new CreateAndStartVMFromISO();

		if (bean.getName() == null 
				|| bean.getOs() == null
				|| bean.getCpu() == null
				|| bean.getCpu().getCpunum() == null
				|| bean.getIso() == null
				|| bean.getMemory() == null
				|| bean.getRootCloudDiskSpecificationFromISO() == null
				|| bean.getRootCloudDiskSpecificationFromISO().getCapacity() == null
				|| bean.getConsole() == null) {
			throw new RuntimeException("Parameters [name, cpu, memory, iso, capacity, console] cannot be null");
		}
		
		// default
		image.setVirt_type("kvm"); 
		image.setNoautoconsole(true); 
		
		// basic
		image.setMetadata("uuid=" + nameToUUID(bean.getName()));
		image.setOs_variant(bean.getOs());
		String vcpus = (bean.getCpu().getCpuset() == null) ? 
				String.valueOf(bean.getCpu().getCpunum()) :
					bean.getCpu().getCpunum() +",cpuset=" + bean.getCpu().getCpuset();
		image.setVcpus(vcpus);
		image.setMemory(String.valueOf(bean.getMemory()));
		image.setCdrom(bean.getIso());
		
		StringBuffer nsb = new StringBuffer();
		StringBuffer dsb = new StringBuffer();
		StringBuffer csb = new StringBuffer();
		RootCloudDiskSpecificationFromISO rootDisk = bean.getRootCloudDiskSpecificationFromISO();
		dsb.append("--disk size=" + rootDisk.getCapacity());
		if (rootDisk.getDiskBandwidth() != null) {
			dsb.append(",total_bytes_sec=" + rootDisk.getDiskBandwidth());
		}
		if (rootDisk.getReadingSpeed() != null) {
			dsb.append(",read_bytes_sec=" + rootDisk.getReadingSpeed());
		}
		if (rootDisk.getWriteSpeed() != null) {
			dsb.append(",write_bytes_sec=" + rootDisk.getWriteSpeed());
		}
		dsb.append(" ");
		
		advancedNetwrokParameters(bean.getNetwork(), image, nsb);
		advancedDiskParameters(bean.getDataCloudDiskSpecifications(), bean.getCdroms(), image, dsb);
		advancedConsoleParameters(bean.getConsole(), image, csb);
		
		return image;
	}

	public static CreateAndStartVMFromImage toCreateAndStartVMFromImage(CreateVMFromImageBean bean) {
		CreateAndStartVMFromImage image = new CreateAndStartVMFromImage();

		if (bean.getName() == null 
				|| bean.getOs() == null
				|| bean.getCpu() == null
				|| bean.getCpu().getCpunum() == null
				|| bean.getTemplate() == null
				|| bean.getMemory() == null
				|| bean.getRootCloudDiskSpecificationFromTemplate() == null
				|| bean.getConsole() == null) {
			throw new RuntimeException("Parameters [name, cpu, memory, template, capacity, console] cannot be null");
		}
		
		// default
		image.setVirt_type("kvm"); 
		image.setBoot("hd");
		image.setNoautoconsole(true); 
		
		// basic
		image.setMetadata("uuid=" + nameToUUID(bean.getName()));
		image.setOs_variant(bean.getOs());
		String vcpus = (bean.getCpu().getCpuset() == null) ? 
				String.valueOf(bean.getCpu().getCpunum()) :
					bean.getCpu().getCpunum() +",cpuset=" + bean.getCpu().getCpuset();
		image.setVcpus(vcpus);
		image.setMemory(String.valueOf(bean.getMemory()));
		image.setCdrom(bean.getTemplate());
		
		StringBuffer nsb = new StringBuffer();
		StringBuffer dsb = new StringBuffer();
		StringBuffer csb = new StringBuffer();
		RootCloudDiskSpecificationFromTemplate rootDisk = bean.getRootCloudDiskSpecificationFromTemplate();
		dsb.append("--disk ROOTDISK");
		if (rootDisk.getDiskBandwidth() != null) {
			dsb.append(",total_bytes_sec=" + rootDisk.getDiskBandwidth());
		}
		if (rootDisk.getReadingSpeed() != null) {
			dsb.append(",read_bytes_sec=" + rootDisk.getReadingSpeed());
		}
		if (rootDisk.getWriteSpeed() != null) {
			dsb.append(",write_bytes_sec=" + rootDisk.getWriteSpeed());
		}
		dsb.append(" ");
		
		advancedNetwrokParameters(bean.getNetwork(), image, nsb);
		advancedDiskParameters(bean.getDataCloudDiskSpecifications(), bean.getCdroms(), image, dsb);
		advancedConsoleParameters(bean.getConsole(), image, csb);
		
		return image;
	}
	
	protected static void advancedConsoleParameters(Console console, CreateAndStartVMFromISO image,
			StringBuffer csb) {
		if (console.getVnc() != null) {
			csb.append("--graphics ").append("vnc");
			String consolePassword = console.getVnc().getConsolePassword();
			if (consolePassword != null) {
				csb.append(",listen=0.0.0.0,password=" + consolePassword);
			}
			csb.append(" ");
		}
		
		if (console.getSpice() != null) {
			csb.append("--graphics ").append("spice");
			String consolePassword = console.getSpice().getConsolePassword();
			if (consolePassword != null) {
				csb.append(",listen=0.0.0.0,password=" + consolePassword);
			}
			csb.append(" ");
		}
		image.setGraphics(csb.substring("--graphics".length()));
	}

	protected static void advancedDiskParameters(ArrayList<DataCloudDiskSpecifications> dataDisks, 
			ArrayList<Cdroms> cdroms, CreateAndStartVMFromISO image, StringBuffer dsb) {
		for (DataCloudDiskSpecifications dataDisk: dataDisks) {
			if (dataDisk.getPath() == null && dataDisk.getCapacity() == null) {
				throw new RuntimeException("Parameters [path and capacity] cannot be null");
			}
			
			if (dataDisk.getPath() != null && dataDisk.getCapacity() != null) {
				throw new RuntimeException("Parameters [path and capacity] cannot have a value at the same time");
			}
			if (dataDisk.getPath() != null) {
				dsb.append("--disk " +  dataDisk.getPath());
			} else {
				dsb.append("--disk size=" +  dataDisk.getCapacity());
			}
			
			if (dataDisk.getDiskBandwidth() != null) {
				dsb.append(",total_bytes_sec=" + dataDisk.getDiskBandwidth());
			}
			
			if (dataDisk.getReadingSpeed() != null) {
				dsb.append(",read_bytes_sec=" + dataDisk.getReadingSpeed());
			}
			if (dataDisk.getWriteSpeed() != null) {
				dsb.append(",write_bytes_sec=" + dataDisk.getWriteSpeed());
			}
			dsb.append(" ");
		}
		
		for (Cdroms cd : cdroms) {
			if (cd.getIso() == null) {
				throw new RuntimeException("Parameter [iso] cannot be null");
			}
			
			dsb.append("--disk " +  cd.getIso() + ",device=cdrom,perms=ro ");
		}
		image.setDisk(dsb.substring("--disk".length()));
	}

	protected static void advancedNetwrokParameters(ArrayList<Network> networks, CreateAndStartVMFromISO image,
			StringBuffer nsb) {
		// network
		for (Network network : networks) {
			if (network.getBridge() == null) {
				throw new RuntimeException("Parameter [bridge] cannot be null");
			}
			nsb.append("--network bridge=" + network.getBridge() + " ");
		}
		image.setNetwork(nsb.substring("--network".length()));
	}
	
	
	protected static String nameToUUID(String name) {
		StringBuffer sb = new StringBuffer();
		sb.append(name.substring(0, 8)).append("-")
				.append(name.substring(8, 12)).append("-")
				.append(name.substring(12, 16)).append("-")
				.append(name.substring(16, 20)).append("-")
				.append(name.substring(20, 32));
		return sb.toString();
	}
	
	public static DeleteVM toDeleteVM(DeleteVMBean bean) {
		return new DeleteVM();
	}
	
	public static RebootVM toRebootVM(RebootVMBean bean) {
		return new RebootVM();
	}
	
	public static ResetVM toResetVM(ResetVMBean bean) {
		return new ResetVM();
	}
	
	public static DeleteVM toRemoveVM(RemoveVMBean bean) {
		return new DeleteVM();
	}
	
	public static ResumeVM toResumeVM(ResumeVMBean bean) {
		return new ResumeVM();
	}
	
	public static StartVM toStartVM(StartVMBean bean) {
		return new StartVM();
	}
	
	public static StopVMForce toStopForceVM(StopForceVMBean bean) {
		return new StopVMForce();
	}
	
	public static StopVM toStopVM(StopVMBean bean) {
		return new StopVM();
	}
	
	public static SuspendVM toSuspendVM(SuspendVMBean bean) {
		return new SuspendVM();
	}
	
}
