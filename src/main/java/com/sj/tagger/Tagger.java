package com.sj.tagger;

import java.net.URL;

public interface Tagger {
	String tagText(String text);

	String getText(URL url);
}
