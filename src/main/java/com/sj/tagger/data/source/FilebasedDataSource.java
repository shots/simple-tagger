package com.sj.tagger.data.source;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FilebasedDataSource implements ArticleSource {

	private static Logger log = LogManager.getLogger(FilebasedDataSource.class);

	private Queue<URL> seedSet;

	public FilebasedDataSource(String seedFile) throws IOException {
		seedSet = new LinkedList<URL>();

		@SuppressWarnings("unchecked")
		List<String> urls = FileUtils.readLines(new File(seedFile));
		if (CollectionUtils.isNotEmpty(urls)) {
			for (String u : urls) {
				try {
					URL url = new URL(u);
					seedSet.add(url);
				} catch (MalformedURLException e) {
					log.debug(u
							+ ": Bad URL, not adding to the seedSet, exception : "
							+ e.getMessage());
				}

			}
		}
	}

	@Override
	public URL getNext() {
		return seedSet.poll();
	}
	
	public static void main(String []args) {
		
	}

}
