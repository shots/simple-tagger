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
		if (!sinkBasePath.endsWith(File.separator)) {
			sinkBasePath += File.separator;
		}
	}

	@Override
	public void flush(ArticleData taggedArticle) {
		try {
			String sinkfile = sinkBasePath
					+ taggedArticle.getTitle().replaceAll("\\s+", "_") + ".txt";
			log.debug("Flushing " + taggedArticle.getUrl() + " to " + sinkfile);
			FileUtils.writeStringToFile(new File(sinkfile),
					taggedArticle.getTagged_text());
		} catch (IOException e) {
			log.error("Failed to flush " + taggedArticle.getUrl()
					+ ", error : " + e.getMessage());
		}
	}
}
