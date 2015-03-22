package com.sj.tagger.data.sink;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sj.tagger.ArticleData;

public class FilebasedArticleSink implements ArticleSink {

	private static Logger log = LogManager
			.getLogger(FilebasedArticleSink.class);

	private String sinkBasePath;

	public FilebasedArticleSink(String basePath) {
		this.sinkBasePath = basePath;
	}

	@Override
	public void flush(ArticleData taggedArticle) {
		try {
			log.debug("Flushing " + taggedArticle.getUrl());
			FileUtils.writeStringToFile(new File(sinkBasePath
					+ taggedArticle.getTitle().replaceAll("\\s+", "_")),
					taggedArticle.getTagged_text());
		} catch (IOException e) {
			log.error("Failed to flush " + taggedArticle.getUrl()
					+ ", error : " + e.getMessage());
		}
	}

}
