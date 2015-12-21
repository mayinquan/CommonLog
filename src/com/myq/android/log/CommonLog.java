/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.myq.android.log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CommonLog {
    private static String APP_DEFAULT_TAG = "CommonLog";// LOG默认TAG  
    private static final String TAG_CONTENT_PRINT = "%s.%s():%d:%s";  
    private static ConcurrentHashMap <String,Long> mTraceTime;
    private CommonLog() {
    }
    public static void v(String msg){
    	println(android.util.Log.VERBOSE, APP_DEFAULT_TAG, msg, null, 5);
    }
    /**
     * Send a {@link #VERBOSE} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void v(String tag, String msg) {
        println(android.util.Log.VERBOSE, tag, msg, null, 5);
    }

    /**
     * Send a {@link #VERBOSE} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void v(String tag, String msg, Throwable tr) {
    	println(android.util.Log.VERBOSE, tag, msg, tr, 5);
    }
    public static void d(String msg){
    	println(android.util.Log.DEBUG, APP_DEFAULT_TAG, msg, null, 5);
    }
    /**
     * Send a {@link #DEBUG} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void d(String tag, String msg) {
    	println(android.util.Log.DEBUG, tag, msg, null, 5);
    }
    /**
     * Send a {@link #DEBUG} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void d(String tag, String msg, Throwable tr) {
    	println(android.util.Log.DEBUG, tag, msg, tr, 5);
    }
    public static void i(String msg){
    	println(android.util.Log.INFO, APP_DEFAULT_TAG, msg, null, 5);
    }
    /**
     * Send an {@link #INFO} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void i(String tag, String msg) {
    	println(android.util.Log.INFO, tag, msg, null, 5);
    }
    /**
     * Send a {@link #INFO} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void i(String tag, String msg, Throwable tr) {
    	println(android.util.Log.INFO, tag, msg, tr, 5);
    }
    public static void w(String msg){
       	println(android.util.Log.WARN, APP_DEFAULT_TAG, msg, null, 5);
    }
    /**
     * Send a {@link #WARN} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void w(String tag, String msg) {
       	println(android.util.Log.WARN, tag, msg, null, 5);
    }
    /**
     * Send a {@link #WARN} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void w(String tag, String msg, Throwable tr) {
    	println(android.util.Log.WARN, tag, msg, tr, 5);
    }
    /**
     * Send a {@link #WARN} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param tr An exception to log
     */
    public static void w(String tag, Throwable tr) {
    	println(android.util.Log.WARN, tag, "", tr, 5);
    }
    public static void e(String msg){
    	println(android.util.Log.ERROR, APP_DEFAULT_TAG, msg, null, 5);
    }
    /**
     * Send an {@link #ERROR} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void e(String tag, String msg) {
    	println(android.util.Log.ERROR, tag, msg, null, 5);
    }
    /**
     * Send a {@link #ERROR} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void e(String tag, String msg, Throwable tr) {
    	println(android.util.Log.ERROR, tag, msg, tr, 5);
    }
    /**
     * Low-level logging call.
     * @param priority The priority/type of this log message
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @return The number of bytes written.
     */
    private static void println(int priority, String tag, String msg, Throwable tr, int traceIndex) {
    	if(BuildConfig.DEBUG){
        	android.util.Log.println(priority, tag, getContent(traceIndex, msg, tr));
        }
    }
    //format log
    private static String getContent(int traceIndex,String msg,Throwable tr) {  
    	String content = msg + (null != tr ? tr.toString() : "");
		try {
			StackTraceElement trace = Thread.currentThread().getStackTrace()[traceIndex];
			content = String.format(TAG_CONTENT_PRINT,
					trace.getClassName(), trace.getMethodName(), trace.getLineNumber(), msg);
			if(null != tr){
				content +="\n tr = " + tr.toString();
			}
		} catch (Exception e) {
		}
		return content;
    }
    
    /**
     * 开始时间戳，打印开始时间，与stopTimeTrace配合使用
     * @param timeTraceTag 时间戳名
     */
    public static void startTimeTrace(String timeTraceTag){
    	if(BuildConfig.DEBUG){
    		if(null == mTraceTime){
    			mTraceTime = new ConcurrentHashMap<String,Long>();
    		}
    		long startTime = System.currentTimeMillis();
    		mTraceTime.put(timeTraceTag, startTime);
    		android.util.Log.w(timeTraceTag, "strart time = " + startTime);
    	}
    }
    
    /**
     * 结束时间戳，打印结束时间，与startTimeTrace配合使用
     * @param timeTraceTag 时间戳名
     */
    public static void stopTimeTrace(String timeTraceTag){
    	if(BuildConfig.DEBUG){
    		if(null == mTraceTime){
    			android.util.Log.w(timeTraceTag, "not start.");
    		}else{
	    		Long time = mTraceTime.remove(timeTraceTag);
	    		if(null == time){
	    			android.util.Log.w(timeTraceTag, "not start.");
	    		}else{
	    			long endTime = System.currentTimeMillis();
	    			long diff =  endTime - time;
	    			android.util.Log.w(timeTraceTag, "end time = " + endTime +" ,diff = "+ diff + " ms");
	    		}
    		}
    	}
    }
    
	/**
	 * 打印类堆栈信息
	 * @param tag
	 */
	public static void printStackTrace(String tag) {
		if (BuildConfig.DEBUG) {
			try {
				StackTraceElement[] traces = Thread.currentThread().getStackTrace();
				StringBuilder builder = new StringBuilder();
				builder.append("[").append('\n');
				for (int i = 3; i < traces.length; i++) {
					builder.append("{class = ").append(traces[i].getClassName()).append(" , Method = ")
							.append(traces[i].getMethodName()).append("}").append('\n');
				}
				builder.append("]");
				android.util.Log.w(tag, builder.toString());
			} catch (Exception e) {
			}
		}
	}
	
	private static StringBuilder formatObjectToString(Object obj,StringBuilder builder){
		builder.append("{");
		if(obj instanceof String || obj.getClass().getSuperclass() == java.lang.Number.class){
			builder.append("type = ").append(obj.getClass().toString()).append(", value = ").append(obj).append('\n');
		}else{
			builder.append('\n').append("type = ").append(obj.getClass().toString()).append("{");
			Field[] fields = obj.getClass().getDeclaredFields();
			for(Field field:fields){
				try {
					field.setAccessible(true);
					builder.append("filedName = ").append(field.getName()).append(",")
					.append("value = ").append(field.get(obj)).append(",").append('\n');
				} catch (Exception e) {
				}
			}
			builder.append("}").append("}").append('\n');
		}
		return builder;
	}

	private static String formatObjectMethodValues(Object obj, String... methodNames) {
		StringBuilder builder = new StringBuilder();
		Method method = null;
		if (null != methodNames) {
			builder.append("{");
			for (String methodName : methodNames) {
				try {
					method = obj.getClass().getMethod(methodName, new Class[] {});
					Object value = method.invoke(obj, new Object[] {});
					builder.append(methodName).append(" = ").append(value).append(",");
				} catch (Exception e) {
					
				}
			}
			builder.append("}");
		}
		return builder.toString();
	}
    
	public static void printObj(Object obj){
    	printObj(APP_DEFAULT_TAG, obj);
    }
    
    /**
     * 打印对象属性名、属性值
     * @param tag
     * @param obj
     */
    public static void printObj(String tag, Object obj){
    	if(BuildConfig.DEBUG){
    		android.util.Log.w(tag, formatObjectToString(obj, new StringBuilder()).toString());
    	}
    }

    public static void printObj(Object obj, String... methodNames){
    	printObj(APP_DEFAULT_TAG, obj, methodNames);
    }
    
    /**
     * 打印对象方法值
     * @param obj
     * @param methodNames 方法名
     */
    public static void printObj(String tag, Object obj, String... methodNames){
    	if(BuildConfig.DEBUG){
    		String msg = formatObjectMethodValues(obj, methodNames);
    		android.util.Log.w(tag, msg);
    	}
    }
    
    public static void printList(List<Object> list){
    	printList(APP_DEFAULT_TAG, list);
    }
    
    /**
     * 打印list
     * @param tag
     * @param list
     */
    public static void printList(String tag, List<Object> list){
    	if(BuildConfig.DEBUG){
    		StringBuilder builder = new StringBuilder();
    		if(null == list){
    			builder.append("list is null");
    		}else{
    			builder.append("list size = ").append(list.size()).append('\n').append("[").append('\n');
    			for(Object obj : list){
    				builder = formatObjectToString(obj, builder);
    			}
    			builder.append("]").append('\n');
    		}
    		android.util.Log.w(tag, builder.toString());
    	}
    }
    
	/**
	 * 打印map
	 * @param tag
	 * @param map
	 */
	public static <K, V> void printMap(String tag, Map<K, V> map){
		if(BuildConfig.DEBUG){
			StringBuilder builder = new StringBuilder();
			if (null == map) {
				builder.append("map is null");
			} else {
				builder.append("map size = ").append(map.size()).append('\n').append("[").append('\n');
				for(K k:map.keySet()){
					builder.append("key = ").append(k).append(", value = ").append(formatObjectToString(map.get(k), builder));
				}
			}
			android.util.Log.w(tag, builder.toString());
		}
	}
    
	public static <K,V> void printMap(Map<K, V> map){
		printMap(APP_DEFAULT_TAG, map);
	}
}