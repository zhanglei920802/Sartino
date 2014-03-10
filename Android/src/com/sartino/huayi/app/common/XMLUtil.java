package com.sartino.huayi.app.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.os.Environment;

import com.sartino.huayi.app.bean.Image;
import com.sartino.huayi.app.bean.SimpleInfo;

public class XMLUtil {
	/**
	 * 批量获取数据
	 * 
	 * @return
	 * @throws IOException
	 */
	public static List<SimpleInfo> getSimpleInfoInBatch(InputStream inputstream)
			throws IOException {
		if (inputstream == null) {
			return null;
		}
		int preSize = 0;
		int actSize = 0;
		XmlPullParser xmlPullParser = null;
		XmlPullParserFactory xmlPullParserFactory = null;
		List<SimpleInfo> lists = null;
		SimpleInfo tmp = null;
		Image imgTmp = null;
		try {
			xmlPullParserFactory = XmlPullParserFactory.newInstance();
			xmlPullParser = xmlPullParserFactory.newPullParser();
			xmlPullParser.setInput(inputstream, "utf-8");
			int eventType = xmlPullParser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					lists = new ArrayList<SimpleInfo>();
					break;
				case XmlPullParser.START_TAG:
					if ("summarys".equals(xmlPullParser.getName())) {
						preSize = Integer.valueOf(xmlPullParser
								.getAttributeValue(0));
					}
					if ("summary".equals(xmlPullParser.getName())) {
						tmp = new SimpleInfo();
						tmp.setId(Integer.valueOf(xmlPullParser
								.getAttributeValue(0)));// id
					}
					if ("summary".equals(xmlPullParser.getName())) {
						tmp.setCategory(Integer.valueOf(xmlPullParser
								.getAttributeValue(1)));
					}
					if ("zhtitle".equals(xmlPullParser.getName())) {
						tmp.setZh_title(xmlPullParser.nextText());
						tmp.setZh_title(tmp.getZh_title().replace("BBBBB", "&"));
					}
					if ("entitle".equals(xmlPullParser.getName())) {
						tmp.setEn_title(xmlPullParser.nextText());
						tmp.setEn_title(tmp.getEn_title().replace("BBBBB", "&"));
					}
					if ("detatilurl".equals(xmlPullParser.getName())) {
						tmp.setDetail(xmlPullParser.nextText());
					}
					if ("thumb".equals(xmlPullParser.getName())) {
						imgTmp = new Image();
					}
					if ("imgurl".equals(xmlPullParser.getName())) {
						imgTmp.setUrl(xmlPullParser.nextText());
					}
					if ("width".equals(xmlPullParser.getName())) {
						imgTmp.setWidth(Integer.valueOf(xmlPullParser
								.nextText()));
					}
					if ("height".equals(xmlPullParser.getName())) {
						imgTmp.setHeight(Integer.valueOf(xmlPullParser
								.nextText()));
					}
					if ("name".equals(xmlPullParser.getName())) {
						imgTmp.setName(xmlPullParser.nextText());

					}
					if ("thumburl".equals(xmlPullParser.getName())) {
						imgTmp.setThumbUrl(xmlPullParser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					if ("thumb".equals(xmlPullParser.getName())) {
						tmp.setImg(imgTmp);
					}
					if ("summary".equals(xmlPullParser.getName())) {
						lists.add(tmp);
						actSize++;
					}
					break;
				}

				eventType = xmlPullParser.next();
			}

		} catch (Exception e) {

			tmp = null;
			imgTmp = null;
			inputstream = null;
			lists = null;
//			inputstream.close();
			System.out.println("XMLUtil.getSimpleInfoInBatch()" + "出错了"
					+ e.getMessage());
			return null;

		}

		if (preSize != actSize) {
			try {
				throw new Exception("数据异常");
			} catch (Exception e) {
				System.out.println("XMLUtil.getSimpleInfoInBatch()"
						+ e.getMessage());
			}
		}

		tmp = null;
		imgTmp = null;
		inputstream.close();
		inputstream = null;
		return lists;
	}

	/**
	 * 批量存储数据为xml类型,并返回一个一个输出流
	 * 
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	@SuppressWarnings("static-access")
	public static InputStream saveRecordInBatch(List<SimpleInfo> data)
			throws IOException, XmlPullParserException {
		XmlPullParserFactory xmlPullParserFactoty = null;
		XmlSerializer xmlserializer = null;

		File cacheFile = new File(Environment.getExternalStorageDirectory(),
				"test.xml");
		OutputStream fos = null;
		if (!cacheFile.isFile()) {
			cacheFile.createNewFile();
		}

		xmlPullParserFactoty = xmlPullParserFactoty.newInstance();
		xmlserializer = xmlPullParserFactoty.newSerializer();

		fos = new FileOutputStream(cacheFile);
		xmlserializer.setOutput(fos, "utf-8");
		xmlserializer.startDocument("utf-8", true);
		xmlserializer.startTag(null, "summarys");
		xmlserializer.attribute(null, "size", String.valueOf(data.size()));

		for (SimpleInfo elm : data) {
			if (elm != null) {
				xmlserializer.startTag(null, "summary");
				xmlserializer
						.attribute(null, "id", String.valueOf(elm.getId()));
				xmlserializer.attribute(null, "type",
						String.valueOf(elm.getCategory()));

				xmlserializer.startTag(null, "zhtitle");
				xmlserializer.text(elm.getZh_title());
				xmlserializer.endTag(null, "zhtitle");

				xmlserializer.startTag(null, "entitle");
				xmlserializer.text(elm.getEn_title());
				xmlserializer.endTag(null, "entitle");

				xmlserializer.startTag(null, "detatilurl");
				xmlserializer.text(elm.getDetail());
				xmlserializer.endTag(null, "detatilurl");

				xmlserializer.startTag(null, "thumb");
				xmlserializer.startTag(null, "imgurl");
				xmlserializer.text(elm.getImg().getUrl());
				xmlserializer.endTag(null, "imgurl");
				xmlserializer.startTag(null, "thumburl");
				xmlserializer.text(elm.getImg().getThumbUrl());
				xmlserializer.endTag(null, "thumburl");
				xmlserializer.startTag(null, "width");
				xmlserializer.text(String.valueOf(elm.getImg().getWidth()));
				xmlserializer.endTag(null, "width");
				xmlserializer.startTag(null, "height");
				xmlserializer.text(String.valueOf(elm.getImg().getHeight()));
				xmlserializer.endTag(null, "height");
				xmlserializer.startTag(null, "name");
				xmlserializer.text(elm.getImg().getName());
				xmlserializer.endTag(null, "name");
				xmlserializer.endTag(null, "thumb");

				xmlserializer.endTag(null, "summary");

			}

		}
		xmlserializer.endTag(null, "summarys");
		xmlserializer.endDocument();
		xmlserializer.flush();
		fos.flush();
		fos.close();
		fos = null;

		return new FileInputStream(cacheFile);

	}

	
}
