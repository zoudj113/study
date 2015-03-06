package com.onemore.config;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.onemore.controller.ActionExtentionHandler;
import com.onemore.controller.CommonController;
import com.onemore.dao.OrdSrvAttr;
import com.onemore.dao.VmDealTask;

/**
 * API����ʽ����
 */
public class Config extends JFinalConfig {
	
	/**
	 * ���ó���
	 */
	public void configConstant(Constants me) {
		// ����������Ҫ���ã�������getProperty(...)��ȡֵ
		loadPropertyFile("config.properties");
		me.setDevMode(getPropertyToBoolean("devMode", true));
	}
	
	/**
	 * ����·��
	 */
	public void configRoute(Routes me) {
		me.add("/", CommonController.class);
	}
	
	/**
	 * ���ò��
	 */
	public void configPlugin(Plugins me) {
		// ����C3p0���ݿ����ӳز��
		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
		me.add(c3p0Plugin);
		C3p0Plugin c3p0Plugin2 = new C3p0Plugin(getProperty("jdbcOrdUrl"), getProperty("userOrd"), getProperty("passwordOrd").trim());
		me.add(c3p0Plugin2);
		
		// ����ActiveRecord���
		ActiveRecordPlugin arp = new ActiveRecordPlugin("comframe",c3p0Plugin);
		ActiveRecordPlugin arp2 = new ActiveRecordPlugin("ord",c3p0Plugin2);
		arp.setShowSql(true);
		arp2.setShowSql(true);
		me.add(arp);
		me.add(arp2);
		
		arp.addMapping("vm_deal_task", "task_id", VmDealTask.class);
		arp2.addMapping("ord_srv_attr_21","srv_attr_order_id", OrdSrvAttr.class);
	}
	
	/**
	 * ����ȫ��������
	 */
	public void configInterceptor(Interceptors me) {
		
	}
	
	/**
	 * ���ô�����
	 */
	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler("root_path"));
		me.add(new ActionExtentionHandler());
	}
	
	/**
	 * ����ʹ�� JFinal �ֲ��Ƽ��ķ�ʽ������Ŀ
	 * ���д� main ��������������Ŀ����main�������Է����������Class�ඨ���У���һ��Ҫ���ڴ�
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}
}
