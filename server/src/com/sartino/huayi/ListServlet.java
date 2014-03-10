package com.sartino.huayi;

import java.beans.SimpleBeanInfo;
import java.io.File;
import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sartino.huayi.domain.DetailInfo;
import com.sartino.huayi.domain.NavigationInfo;
import com.sartino.huayi.domain.PhotoInfo;
import com.sartino.huayi.domain.SimpleInfo;
import com.sartino.huayi.domain.SimpleInfos;
import com.sartino.huayi.domain.TitleTypeInfo;
import com.sartino.huayi.service.ImageService;
import com.sartino.huayi.service.impl.ImageServiceBean;
import com.sartino.huayi.util.DataProcess;
import com.sun.xml.internal.stream.writers.XMLWriter;

public class ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ImageService service = new ImageServiceBean();

	public ListServlet() {
		super();
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String offset = request.getParameter("offset");
		String maxresult = request.getParameter("maxresult");
		String queryType = request.getParameter("q");

		String format = request.getParameter("format");
		String type = request.getParameter("type");
		System.out.println("in==========================in");
		if ("json".equals(format)) {// 处理json数据格式
			String id = request.getParameter("id");
			StringBuilder builder = null;

			if ("image".equals(type)) {

				List<PhotoInfo> images = service.getPhoto(id, offset,
						maxresult, new Integer(queryType));
				try {
					builder = DataProcess.ProcessImages(images);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if ("menu".equals(type)) {
				List<NavigationInfo> navigation = service.getNavigations();
				builder = DataProcess.ProcessNavigation(navigation);

			} else if ("title".equals(type)) {

				List<TitleTypeInfo> title = service.getTitleType();
				builder = DataProcess.ProcessTitleType(title);
			} else if ("imageInfo".equals(type)) {// 这里我只是接受图片的名称，这样可以避免更多的网络传输
				builder = DataProcess.getPicList(id);
				// System.out.println(builder.toString());
			}

			// request.setAttribute("json", builder.toString());
			request.getRequestDispatcher("/WEB-INF/page/jsonvideonews.jsp")
					.forward(request, response);

		} else if ("xml".equals(format)) {// xml format
			System.out.println("xml:" + type);
			if ("simpleinfo".equals(type)) {// 简要信息
				int category = Integer
						.valueOf(request.getParameter("category"));
				int pageindex = Integer.valueOf(request
						.getParameter("pageindex"));
				int pagesize = Integer
						.valueOf(request.getParameter("pagesize"));
				int action = Integer.valueOf(request.getParameter("action"));
				List<SimpleInfo> lists = service.getSimpleImageInfo(pageindex,
						pagesize, category, action);
				SimpleInfos datas = new SimpleInfos(lists, lists.size());
				request.setAttribute("infos", datas);
				request.getRequestDispatcher("/WEB-INF/page/xml.jsp").forward(
						request, response);
				
				// http://vmw2k3:8080/Server/servlet/ListServlet?format=xml&type=simpleinfo&id=5&category=12&pageindex=0&pagesize=12&action=1
			} else if ("detailinfo".equals(type)) {
				System.out.println("detailinfo=================");
				String id = request.getParameter("id");
				DetailInfo datas = service.getDetialImageInfo(Integer
						.valueOf(id));
				request.setAttribute("datas", datas);
				request.getRequestDispatcher("/WEB-INF/page/xml_detail.jsp")
						.forward(request, response);
				

			}

		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	@Override
	public void init() throws ServletException {
	}

}
