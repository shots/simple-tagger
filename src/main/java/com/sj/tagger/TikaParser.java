package com.sj.tagger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.LinkContentHandler;
import org.apache.tika.sax.TeeContentHandler;
import org.apache.tika.sax.ToHTMLContentHandler;

public class TikaParser implements HtmlTextParser {

	private static Logger log = LogManager.getLogger(TikaParser.class);

	private ParseContext parseContext = new ParseContext();
	private HtmlParser parser = new HtmlParser();

	public TikaParser() {
		parseContext = new ParseContext();
		parser = new HtmlParser();
	}

	public ArticleData parse(URL url) {
		ArticleData parsedData = null;
		InputStream input = null;
		try {
			LinkContentHandler linkHandler = new LinkContentHandler();
			BodyContentHandler textHandler = new BodyContentHandler(-1);
			ToHTMLContentHandler toHTMLHandler = new ToHTMLContentHandler();
			TeeContentHandler teeHandler = new TeeContentHandler(linkHandler,
					textHandler, toHTMLHandler);
			Metadata metadata = new Metadata();
			input = url.openStream();
			parser.parse(input, teeHandler, metadata, parseContext);
			parsedData = new ArticleData().setText(textHandler.toString())
					.setTitle(metadata.get("title")).setUrl(url.toString());
		} catch (Exception e) {
			log.debug("Failed to parse the url : " + url + ", exception : "
					+ e.getMessage());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					//
				}
			}
		}

		return parsedData;
	}

	public static void main(String args[]) throws Exception {

		TikaParser parser = new TikaParser();
		System.out
				.println("Data : "
						+ parser.parse(
								new URL(
										"http://gumgum-public.s3.amazonaws.com/numbers.html"))
								.getText());
	}
}
