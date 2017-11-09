package cn.safetys.sync.attach;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;

import com.safetys.nbyhpc.domain.model.SyncFileWait;
import com.safetys.nbyhpc.domain.persistence.iface.SyncFileWaitFacadeIface;

import cn.safetys.constant.SystemConstant;
/**
 * 附件服务
 * @author HelloFR
 *
 */
public class AttachServer {
	
	public static AttachServer P;

	protected static final Log log = LogFactory.getLog(AttachServer.class);
	
	public void init(){
		P = this;
	}

	private SystemConstant systemConstant;

	private SyncFileWaitFacadeIface syncFileWaitFacadeIface;
	
	private String getUploadUrl(){
		return systemConstant.getAttachServer()+"/upload.sj";
	}
	
	@Async
	public void asyncUpload(File file,String serverSavePath){
		this.upload(file, serverSavePath);
	}
	
	public void upload(File file,String serverSavePath){
		Boolean result = HttpFileUtils.upload(this.getUploadUrl(), file, serverSavePath);
		if(!result){
			syncFileWaitFacadeIface.add(file.getAbsolutePath(),serverSavePath);
		}
	}
	
	/**
	 * 执行等待同步列表
	 */
	public void executeWait(){
		log.info("开始向文件服务器上传文件 ... ");
		List<SyncFileWait> list = syncFileWaitFacadeIface.findAll();
		boolean b =false;
		for(SyncFileWait item : list){
			log.info("#准备上传文件："+item.getFilePath());
			b = HttpFileUtils.upload(this.getUploadUrl(), item.getRealFilePath(), item.getFilePath());
			if(b){
				syncFileWaitFacadeIface.delete(item);
			}else{
				log.info("#上传失败文件："+item.getFilePath());
			}
		}
		log.info(" ... 向文件服务器上传文件结束");
	}

	public void setSystemConstant(SystemConstant systemConstant) {
		this.systemConstant = systemConstant;
	}

	public void setSyncFileWaitFacadeIface(
			SyncFileWaitFacadeIface syncFileWaitFacadeIface) {
		this.syncFileWaitFacadeIface = syncFileWaitFacadeIface;
	}


	


	
}
