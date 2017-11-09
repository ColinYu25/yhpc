package com.safetys.nbyhpc.facade.iface;

import java.util.Date;
import java.util.List;

import com.safetys.framework.domain.model.pagination.Pagination;
import com.safetys.framework.exception.ApplicationAccessException;
import com.safetys.nbyhpc.domain.model.DaImageFile;
import com.safetys.nbyhpc.util.OperateResult;

public interface ImageFileFacadeIface {
	
	/**
	 * 创建对象
	 * 
	 * @param entity
	 *            实体
	 * @return 返回OperateResult对象
	 * @throws Exception
	 */
	public OperateResult create(DaImageFile entity) throws Exception;
	
	/**
	 * 修改
	 * 
	 * @param entity
	 *            实体
	 * @return 返回OperateResult对象
	 * @throws Exception
	 */
	public OperateResult update(DaImageFile entity) throws Exception;
	
	/**
	 * 修改
	 * 
	 * @param entity
	 *            实体
	 * @return 返回OperateResult对象
	 * @throws Exception
	 */
	public OperateResult merge(DaImageFile entity) throws Exception;

	/**
	 * 根据实体里面的id删除数据（真删除）
	 * 
	 * @param entity
	 *            实体
	 * @return 返回OperateResult对象
	 * @throws Exception
	 */
	public OperateResult deleteTrue(DaImageFile entity) throws Exception;
	
	/**
	 * 根据实体里面的id删除数据（假删除）
	 * 
	 * @param entity
	 *            实体
	 * @return 返回OperateResult对象
	 * @throws Exception
	 */
	public OperateResult deleteFalse(DaImageFile entity) throws Exception;
	
	/**
	 * 添加文件
	 * @param daImageFile
	 * @throws ApplicationAccessException
	 * */
	public String saveFile(DaImageFile daImageFile, String dirPath) throws ApplicationAccessException;
	
	/**
	 * 更新文件
	 * @param daImageFile
	 * @throws ApplicationAccessException
	 * */
	public String updateFile(DaImageFile daImageFile, DaImageFile _stFile) throws ApplicationAccessException;
	
	/**
	 * 加载文件列表
	 * @param daImageFile,pagination
	 * @throws ApplicationAccessException
	 * */
	public List<DaImageFile> loadFiles(Date beginTime, Date endTime,DaImageFile daImageFile,Pagination pagination) throws ApplicationAccessException;
	
	public List<DaImageFile> loadFiles(Long companyId) throws ApplicationAccessException;
	/**
	 * 查看
	 * @param daImageFile
	 * @throws ApplicationAccessException
	 * */
	public DaImageFile loadFile(DaImageFile daImageFile) throws ApplicationAccessException;
	
	/**
	 * 删除文件
	 * 
	 * @param path
	 * @return
	 */
	public String deleteFile(String path);
}
