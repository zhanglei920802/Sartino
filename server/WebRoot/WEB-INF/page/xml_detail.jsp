<?xml version="1.0" encoding="UTF-8"?><%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<photo id="${datas.mId}"> 
	<content>${datas.mContent}</content>
	<overview>${datas.mOverView}</overview>
	<urls size="${datas.msize}">
	<c:forEach items="${datas.urls}" var = "items" ><url>${items}</url></c:forEach>
	</urls>
</photo>