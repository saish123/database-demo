/**
 ******************************************************************************
 * C O P Y R I G H T  A N D  C O N F I D E N T I A L I T Y  N O T I C E
 * <p>
 * Copyright � 2008-2009 Mobicule Technologies Pvt. Ltd. All rights reserved. 
 * This is proprietary information of Mobicule Technologies Pvt. Ltd.and is 
 * subject to applicable licensing agreements. Unauthorized reproduction, 
 * transmission or distribution of this file and its contents is a 
 * violation of applicable laws.
 ******************************************************************************
 *
 * @project android-component-logging
 */
package com.sawant.database_demo.library;

/**
 * 
 * Logger Class
 *
 * @author SAISH SAWANT PATEL
 * @see 
 *
 *
 */
import android.util.Log;

import static com.sawant.database_demo.library.DatabaseConstants.*;

public class DbLogger {
	private static final int STACK_TRACE_LEVELS_UP = 3;

	public static String logValueSeperator = NEW_LINE_SEPARATOR;

	private DbLogger() {
	}

	private static String createLogStatement(String event, String methodName,
			int lineNumber, String logValueSeperator, String... inputs) {
		StringBuilder logString = new StringBuilder();

		for (String thisInput : inputs) {
			logString.append(thisInput);
			logString.append(logValueSeperator);
		}

		return new StringBuilder().append(START_SEPERATOR_LINE).append(event)
				.append(BRACKET_OPEN).append(methodName).append(LINE_SEPARATOR)
				.append(LINE_NUMBER_TAG).append(lineNumber)
				.append(BRACKET_CLOSE).append(NEW_LINE_SEPARATOR)
				.append(logString).append(END_SEPERATOR_LINE).toString();
	}

	public static void debug(String... inputs) {

		StackTraceElement currentStackElement = Thread.currentThread()
				.getStackTrace()[STACK_TRACE_LEVELS_UP];

		String className = currentStackElement.getClassName();
		String methodName = currentStackElement.getMethodName();
		String debugStatement = createLogStatement(NEW_LINE_SEPARATOR,
				methodName, currentStackElement.getLineNumber(),
				logValueSeperator, inputs);

		Log.d(className, debugStatement);

	}

	public static void error(String... inputs) {

		StackTraceElement currentStackElement = Thread.currentThread()
				.getStackTrace()[STACK_TRACE_LEVELS_UP];

		String className = currentStackElement.getClassName();
		String methodName = currentStackElement.getMethodName();
		String errorStatement = createLogStatement(NEW_LINE_SEPARATOR,
				methodName, currentStackElement.getLineNumber(),
				logValueSeperator, inputs);

		Log.e(className, errorStatement);
	}

	public static void error(Throwable exception, String... inputs) {

		StackTraceElement currentStackElement = Thread.currentThread()
				.getStackTrace()[STACK_TRACE_LEVELS_UP];

		String className = currentStackElement.getClassName();
		String methodName = currentStackElement.getMethodName();
		String errorStatement = createLogStatement(NEW_LINE_SEPARATOR,
				methodName, currentStackElement.getLineNumber(),
				logValueSeperator, inputs);

		Log.e(className, errorStatement, exception);

	}

	public static void info(String... inputs) {

		StackTraceElement currentStackElement = Thread.currentThread()
				.getStackTrace()[STACK_TRACE_LEVELS_UP];

		String className = currentStackElement.getClassName();
		String methodName = currentStackElement.getMethodName();
		String infoStatement = createLogStatement(NEW_LINE_SEPARATOR,
				methodName, currentStackElement.getLineNumber(),
				logValueSeperator, inputs);

		Log.i(className, infoStatement);
	}

	public static void verbose(String... inputs) {

		StackTraceElement currentStackElement = Thread.currentThread()
				.getStackTrace()[STACK_TRACE_LEVELS_UP];

		String className = currentStackElement.getClassName();
		String methodName = currentStackElement.getMethodName();
		String verboseStatement = createLogStatement(NEW_LINE_SEPARATOR,
				methodName, currentStackElement.getLineNumber(),
				logValueSeperator, inputs);

		Log.v(className, verboseStatement);
	}

	public static void warn(String... inputs) {

		StackTraceElement currentStackElement = Thread.currentThread()
				.getStackTrace()[STACK_TRACE_LEVELS_UP];

		String className = currentStackElement.getClassName();
		String methodName = currentStackElement.getMethodName();

		String warningStatement = createLogStatement(NEW_LINE_SEPARATOR,
				methodName, currentStackElement.getLineNumber(),
				logValueSeperator, inputs);

		Log.w(className, warningStatement);
	}
}
