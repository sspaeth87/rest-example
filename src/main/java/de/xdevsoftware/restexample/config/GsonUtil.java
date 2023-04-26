
package de.xdevsoftware.restexample.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class GsonUtil
{
	private static Gson gson;
	private static Gson gsonExpose;
	
	public static Gson getInstance()
	{
		if(GsonUtil.gson == null)
		{
			GsonUtil.gson = GsonUtil.getGsonBuilderInstance(false).create();
		}
		return GsonUtil.gson;
	}
	
	public static Gson getExposeInstance()
	{
		if(GsonUtil.gsonExpose == null)
		{
			GsonUtil.gsonExpose = GsonUtil.getGsonBuilderInstance(true).create();
		}
		return GsonUtil.gsonExpose;
	}
	
	public static Gson getInstance(final boolean onlyExpose)
	{
		if(!onlyExpose)
		{
			if(GsonUtil.gson == null)
			{
				GsonUtil.gson = GsonUtil.getGsonBuilderInstance(false).create();
			}
			return GsonUtil.gson;
		}
		else
		{
			if(GsonUtil.gsonExpose == null)
			{
				GsonUtil.gsonExpose = GsonUtil.getGsonBuilderInstance(true).create();
			}
			return GsonUtil.gsonExpose;
		}
	}

	private static GsonBuilder getGsonBuilderInstance(final boolean onlyExpose)
	{
		final GsonBuilder gsonBuilder = new GsonBuilder();
		if(onlyExpose)
		{
			gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		}
		return gsonBuilder;
	}
	
	public static <T> T fromJson(
		final String json,
		final Class<T> classOfT,
		final boolean onlyExpose)
	{
		try
		{
			return GsonUtil.getInstance(onlyExpose).fromJson(json, classOfT);
		}
		catch(final Exception ex)
		{
			// Log exception
			return null;
		}
	}
	
}
