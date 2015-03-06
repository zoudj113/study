package com.onemore.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

public class ActionExtentionHandler extends Handler {
	@Override
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, boolean[] isHandled) {
		int index = target.lastIndexOf(".htm");
		if (target.lastIndexOf(".htm") != -1) {
			target = target.substring(0, index);
		}
		nextHandler.handle(target, request, response, isHandled);
	}

}
