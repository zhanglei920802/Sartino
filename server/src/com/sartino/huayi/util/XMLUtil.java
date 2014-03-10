package com.sartino.huayi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import com.sartino.huayi.domain.SimpleInfo;




public class XMLUtil {
	
	/**
	 * 批量存储数据为xml类型,并返回一个一个输出流
	 * 
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	@SuppressWarnings("static-access")
	public static InputStream saveRecordInBatch(List<SimpleInfo> data,File cacheFile)
			throws IOException, XmlPullParserException {
		XmlPullParserFactory xmlPullParserFactoty = null;
		XmlSerializer xmlserializer = null;

		OutputStream fos = null;
		if (!cacheFile.isFile()) {
			cacheFile.createNewFile();
		}

		xmlPullParserFactoty= xmlPullParserFactoty.newInstance();
		xmlserializer= xmlPullParserFactoty.newSerializer();

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
