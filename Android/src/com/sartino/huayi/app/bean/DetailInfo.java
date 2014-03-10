package com.sartino.huayi.app.bean;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import com.sartino.huayi.app.AppException;

public class DetailInfo extends Enity {

	private static final long serialVersionUID = 3927411515436321641L;
	private int mId = 0;
	private String mOverView = null;
	private String mContent = null;
	private List<String> urls = new ArrayList<String>();

	public int getmId() {
		return mId;
	}

	public void setmId(int mId) {
		this.mId = mId;
	}

	public String getmOverView() {
		return mOverView;
	}

	public void setmOverView(String mOverView) {
		this.mOverView = mOverView;
	}

	public String getmContent() {
		return mContent;
	}

	public void setmContent(String mContent) {
		this.mContent = mContent;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public DetailInfo(int mId, String mOverView, String mContent,
			List<String> urls) {
		super();
		this.mId = mId;
		this.mOverView = mOverView;
		this.mContent = mContent;
		this.urls = urls;
	}

	public DetailInfo() {
		super();
	}

	@Override
	public String toString() {
		return "DetailInfo [mId=" + mId + ", mOverView=" + mOverView
				+ ", mContent=" + mContent + ", urls=" + urls.toString() + "]";
	}

	public static DetailInfo parse(InputStream ins) {
		XmlPullParserFactory xmlPullParseFacotry = null;
		XmlPullParser xmlParser = null;
		DetailInfo detailInfo = null;
		int expctSize = 0;
		try {
			xmlPullParseFacotry = XmlPullParserFactory.newInstance();
			xmlParser = xmlPullParseFacotry.newPullParser();
			xmlParser.setInput(ins, Base.UTF8);

			int eventType = xmlParser.getEventType();

			while (XmlPullParser.END_DOCUMENT != eventType) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					detailInfo = new DetailInfo();
					break;
				case XmlPullParser.START_TAG:
					if ("photo".equals(xmlParser.getName())) {
						detailInfo.setmId(Integer.valueOf(xmlParser
								.getAttributeValue(0)));
					}

					if ("overview".equals(xmlParser.getName())) {
						detailInfo.setmOverView(xmlParser.nextText());
						detailInfo.setmOverView(detailInfo.getmOverView()
								.replace("AAAAA", "<br />"));
						detailInfo.setmOverView(detailInfo.getmOverView()
								.replace("BBBBB", "&"));
						detailInfo.setmOverView(detailInfo.getmOverView()
								.replace("&nbsp;", "  "));
						detailInfo.setmOverView("  "
								+ detailInfo.getmOverView());
					}

					if ("content".equals(xmlParser.getName())) {
						detailInfo.setmContent(xmlParser.nextText());
						detailInfo.setmContent(detailInfo.getmContent()
								.replace("AAAAA", "<br />"));

						detailInfo.setmContent(detailInfo.getmContent()
								.replace("BBBBB", "&"));
						detailInfo.setmContent(detailInfo.getmContent()
								.replace("&nbsp;", "  "));
						detailInfo.setmContent("  "
								+ detailInfo.getmContent());
					}

					if ("url".equals(xmlParser.getName())) {
						detailInfo.getUrls().add(xmlParser.nextText());
					}

					if ("urls".equals(xmlParser.getName())) {
						expctSize = Integer.valueOf(xmlParser
								.getAttributeValue(0));
					}
					break;

				case XmlPullParser.END_TAG:
					break;

				}

				eventType = xmlParser.next();
			}
		} catch (XmlPullParserException e) {
			System.out.println("DetailInfo.parse()" + e.getMessage());
		} catch (IOException e) {
			AppException.xml(e);
		} finally {
			try {
				ins.close();
			} catch (IOException e) {
				System.out.println("DetailInfo.parse()" + e.getMessage());
			}
		}

		if (expctSize != detailInfo.getUrls().size()) {
			return null;
		}
		return detailInfo;
	}
}
