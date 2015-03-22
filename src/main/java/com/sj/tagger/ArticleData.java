package com.sj.tagger;

import org.apache.commons.lang.StringUtils;

public class ArticleData {
	private String title;
	private String url;
	private String text;
	private String tagged_text;

	public boolean isTagged() {
		return StringUtils.isNotEmpty(tagged_text);
	}

	public String getText() {
		return text;
	}

	public String getTitle() {
		return title;
	}

	public ArticleData setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public ArticleData setUrl(String url) {
		this.url = url;
		return this;
	}

	public String getTagged_text() {
		return tagged_text;
	}

	public ArticleData setTagged_text(String tagged_text) {
		this.tagged_text = tagged_text;
		return this;
	}

	public ArticleData setText(String text) {
		this.text = text;
		return this;
	}
}
