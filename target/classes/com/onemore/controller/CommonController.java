package com.onemore.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.onemore.dao.OrdSrvAttr;
import com.onemore.dao.VmDealTask;

/**
 * CommonController
 */
public class CommonController extends Controller {
	public void index() {
//		Page<VmDealTask> vmDealTasks = VmDealTask.dao.paginate(getParaToInt(0, 1), 10);
//		List<VmDealTask> taskList = vmDealTasks.getList();
//		for(VmDealTask task : taskList){
//			String taskId = task.get("TASK_ID");
//			System.out.println(taskId);
//		}
//		List<Record> list = OrdSrvAttr.dao.get(1,10);
		
//		System.out.println(list.size());
//		Page<OrdSrvAttr> ordSrvAttrs = OrdSrvAttr.dao.paginate(getParaToInt(0, 1), 10);
//		List<OrdSrvAttr> ordSrvAttrList = ordSrvAttrs.getList();
//		for(OrdSrvAttr ordSrvAttr : ordSrvAttrList){
//			String srvAttrInstId = ordSrvAttr.get("SRV_ATTR_INST_ID");
//			System.out.println(srvAttrInstId);
//		}
		render("/common/main.html");
	}
	
	public void task(){
		render("/common/task.html");
	}
	
	public void listTask(){
		render("/common/listTask.html");
	}
	public void getTask(){
		String page = getPara("page");
		String rows = getPara("rows");
		Page<VmDealTask> vmDealTasks = VmDealTask.dao.paginate(Integer.parseInt(page), Integer.parseInt(rows));
		int total = vmDealTasks.getTotalRow();
		setAttr("total", total);
		setAttr("rows", vmDealTasks.getList());
		renderJson();
	}
	
	public void getOneTask(){
		String flag = "1";
		String msg = "";
		String taskId = getPara("taskId");
		
		VmDealTask task = VmDealTask.dao.getTask(taskId);
		if(task !=null){
			flag = "0";
		}else{
			msg = "can not find task by taskId";
		}
		setAttr("flag", flag);
		setAttr("msg",msg);
		setAttr("task",task);
		renderJson();
	}
	
	public void listCustOrder(){
		render("/common/listCustOrder.html");
	}
	
	public void getCustOrder(){
		String page = getPara("page");
		String rows = getPara("rows");
		List<Record> custOrderList = OrdSrvAttr.dao.get(Integer.parseInt(page)-1, Integer.parseInt(rows));
		long total = OrdSrvAttr.dao.getCustOrderCount();
		setAttr("total", total);
		setAttr("rows", custOrderList);
		renderJson();
	}
	public void updateTask(){
		String flag = "1";
		String msg = "";
		String taskId = getPara("taskId");
		if(null == taskId || "".equals(taskId)){
			msg = "taskId can not be null";
		}
		VmDealTask dao = VmDealTask.dao;
		VmDealTask task = dao.getTask(taskId);
		if(task == null ){
			msg = "Can not find Task by taskId " + taskId ;
		}else{
			int updateCount = dao.updateTaskTime(taskId);
			if(updateCount == 0){
				msg = "update faild";
			}else {
				msg  = "update " + updateCount + " rows";
				flag  = "0";
			}
		}
		setAttr("flag", flag);
		setAttr("msg", msg);
		renderJson();
	}
	
	public void custOrder(){
		render("/common/custorder.html");

	}
	public void updateOrder(){
		String flag = "1";
		String msg = "";
		String custOrderId = getPara("customerOrderId");
		String attrId = getPara("attrId");
		String attrValue = getPara("attrValue");
		if(null == custOrderId || "".equals(custOrderId)){
			msg = "custOrderId can not be null";
		}
		if(null == attrId || "".equals(attrId)){
			msg = "attrId can not be null";
		}
		if(null == attrValue || "".equals(attrValue)){
			msg = "attrValue can not be null";
		}
		List<Record> custOrders = OrdSrvAttr.dao.getCustOrder(custOrderId, attrId);
		if(custOrders == null || custOrders.size() == 0 ){
			msg = "Can not find CustomerOrder by customerOrderId and AttrId [" + custOrderId + ", "+ attrId + " ]";
		}else{
			int updateCount = OrdSrvAttr.dao.updateCustOrder(custOrderId, attrId, attrValue);
			if(updateCount == 0 ){
				msg = "update faild";
			}else{
				msg  = "update " + updateCount + " rows";
				flag  = "0";
			}
		}
		setAttr("flag", flag);
		setAttr("msg", msg);
		renderJson();
	}
	
	public void updateDbConfig(){
		InputStream inputStream = null ;
		inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.txt");
		BufferedReader br = new BufferedReader( new InputStreamReader(inputStream));
		try {
			String line = null ;
			while((line = br.readLine()) != null){
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br != null ){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		renderJson();
	}
}
