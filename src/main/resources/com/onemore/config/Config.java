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
 * API引导式配置
 */
public class Config extends JFinalConfig {
	
	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		// 加载少量必要配置，随后可用getProperty(...)获取值
		loadPropertyFile("config.properties");
		me.setDevMode(getPropertyToBoolean("devMode", true));
	}
	
	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add("/", CommonController.class);
	}
	
	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		// 配置C3p0数据库连接池插件
		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
		me.add(c3p0Plugin);
		C3p0Plugin c3p0Plugin2 = new C3p0Plugin(getProperty("jdbcOrdUrl"), getProperty("userOrd"), getProperty("passwordOrd").trim());
		me.add(c3p0Plugin2);
		
		// 配置ActiveRecord插件
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
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		
	}
	
	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler("root_path"));
		me.add(new ActionExtentionHandler());
	}
	
	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		JFinal.start("WebRoot", 80, "/", 5);
	}
}
