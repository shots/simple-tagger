package com.sj.tagger;

import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sj.tagger.data.sink.ArticleSink;
import com.sj.tagger.data.source.ArticleSource;

public class DataProcessingPipeline {
	private static Logger log = LogManager
			.getLogger(DataProcessingPipeline.class);

	private ArticleSource source;
	private ArticleSink sink;
	private Tagger tagger;

	public DataProcessingPipeline setSource(ArticleSource source) {
		this.source = source;
		return this;
	}

	public DataProcessingPipeline setSink(ArticleSink sink) {
		this.sink = sink;
		return this;
	}

	public DataProcessingPipeline setTagger(Tagger tagger) {
		this.tagger = tagger;
		return this;
	}

	public void process() {
		URL url;
		while ((url = source.getNext()) != null) {
			log.info("Processing URL : " + url.toString());
			String rawText = tagger.getText(url);
			if (StringUtils.isNotEmpty(rawText)) {
				String taggedText = tagger.tagText(rawText);
				ArticleData taggedArticle = new ArticleData()
						.setText(rawText)
						.setTagged_text(taggedText)
						.setTitle(
								url.getHost()
										+ url.getPath().replaceAll("/", "_"));
				sink.flush(taggedArticle);
			}
		}
	}
}
