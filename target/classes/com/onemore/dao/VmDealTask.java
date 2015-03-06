package com.onemore.dao;

import java.util.Date;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * vm_deal_task

+---------------+----------------+------+-----+---------+-------+
| Field         | Type           | Null | Key | Default | Extra |
+---------------+----------------+------+-----+---------+-------+
| DEAL_TASK_ID  | varchar(75)    | YES  |     | NULL    |       |
| TASK_ID       | varchar(75)    | YES  |     | NULL    |       |
| WORKFLOW_ID   | varchar(75)    | YES  |     | NULL    |       |
| QUEUE_ID      | varchar(15)    | YES  |     | NULL    |       |
| DEAL_TYPE     | varchar(30)    | YES  |     | NULL    |       |
| RUNTIME       | datetime       | YES  |     | NULL    |       |
| STATE         | decimal(3,0)   | YES  |     | NULL    |       |
| CREATE_DATE   | datetime       | YES  |     | NULL    |       |
| DEV_ID        | varchar(15)    | YES  |     | NULL    |       |
| ERROR_MESSAGE | varchar(12000) | YES  |     | NULL    |       |
| REGION_ID     | varchar(18)    | YES  |     | NULL    |       |
+---------------+----------------+------+-----+---------+-------+

数据库字段名建议使用驼峰命名规则，便于与 java 代码保持一致，如字段名： userId
 */
@SuppressWarnings("serial")
public class VmDealTask extends Model<VmDealTask> {
	public static final VmDealTask dao = new VmDealTask();
	
	/**
	 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
	 */
	public Page<VmDealTask> paginate(int pageNumber, int pageSize) {
		return paginate(pageNumber, pageSize, "select * ", "from comframe.vm_deal_task order by create_date desc");
	}
	
	public int getTaskCount(String taskId){
//		return Db.queryInt(select (count))
		return 0 ;
	}
	public VmDealTask getTask(String taskId){
		return findById(taskId);
	}
	public int updateTaskTime(String taskId){
		String sql = "UPDATE vm_deal_task SET RUNTIME = ? WHERE TASK_ID= ? ";
		return Db.update(sql, new Object[]{new Date(),taskId});
	}
	
}
