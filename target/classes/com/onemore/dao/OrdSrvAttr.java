package com.onemore.dao;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;


@SuppressWarnings("serial")
public class OrdSrvAttr extends Model<OrdSrvAttr> {
	public static final OrdSrvAttr dao = new OrdSrvAttr();
	
	/**
	 * 所有 sql 写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
	 */
	public List<Record> paginate(int pageNumber, int pageSize) {
		
//		List<Record> votes = Db.find(c3p0Plugin2.getDataSource(), "select * from vote");
		List<Record> recordList = Db.use("ord").find("select * from ord.ord_srv_attr_21 order by create_date desc");
		return recordList;
//		Db.use("ord").paginate(pageNumber, pageSize, "select * ", "from ord.ord_srv_attr_21 order by create_date desc");
	}
	
	public List<Record> get(int pageNumber,int pageSize){
		List<Record> recordList = Db.use("ord").find("select * from ord.ord_srv_attr_21 order by create_date desc limit ?,?" ,new Object[]{(pageNumber-1)*pageSize,pageNumber*pageSize});
		return recordList;
	}
	
	
	public List<Record> getCustOrder(String custOrderId,String attrId){
		List<Record> recordList = Db.use("ord").find("select * from ord.ord_srv_attr_21 where customer_order_id = ? and attr_id = ? " ,new Object[]{custOrderId,attrId});
		return recordList;
	}
	 
	public long getCustOrderCount(){
		return Db.use("ord").queryLong("select count(*) from ord.ord_srv_attr_21 ");
	}
	public int updateCustOrder (String custOrderId,String attrId , String attrValue ){
		String sql = "UPDATE ord_srv_attr_21 SET attr_value = ? WHERE customer_order_id = ? and attr_id = ? ";
		return Db.use("ord").update(sql, new Object[]{attrValue,custOrderId,attrId});
	}
}
