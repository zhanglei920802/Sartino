<?xml version="1.0" encoding="UTF-8"?><%@ page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%> <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<summarys size="${infos.size}"><c:forEach items="${infos.datas}" var = "items" >
	<summary id="${items.id}" type="${items.category}">
		<zhtitle>${items.zh_title}</zhtitle>
		<entitle>${items.en_title}</entitle>
		<detatilurl>${items.detail}</detatilurl>
		<thumb>
			<imgurl>${items.img.url}</imgurl>
			<width>${items.img.width}</width>
			<height>${items.img.height}</height>
			<name>${items.img.name}</name>
			<thumburl>${items.img.thumbUrl}</thumburl>
		</thumb>
	</summary></c:forEach>
</summarys>