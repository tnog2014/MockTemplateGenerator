package mockgen;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MockTemplateGen {

	private static final String DQ = "\"";
	private static final String TAB = "\t";
	private static final String NL = "\r\n";
	private String templateDir = "template";

	public static void main(String[] args) throws Exception {
		MockTemplateGen gen = new MockTemplateGen();
		String className = "test.Sample01";
		gen.generate(className);
	}

	public void generate(String className) throws Exception {
		Class clazz = Class.forName(className);
		Method[] methods = clazz.getDeclaredMethods();
		String template = null;
		for (Method m : methods) {
			System.out.println(analyzeMethod(clazz, m));
		}
	}

	public String analyzeMethod(Class clazz, Method m) throws Exception {
		int mm = m.getModifiers();
		Modifier modifier = new Modifier();
		boolean isStatic = modifier.isStatic(mm);
		boolean isPrivate = modifier.isPrivate(mm);

		Class[] parameters = m.getParameterTypes();
		String methodName = m.getName();
		Class retType = m.getReturnType();
		String sRetType = retType.getSimpleName();
		Type gretType = m.getGenericReturnType();
		Class<?>[] xType = m.getExceptionTypes();
		Type[] gxType = m.getGenericExceptionTypes();
		String className = clazz.getSimpleName();
		TemplateEngine engine = new TemplateEngine();
		String objs = getParameterString(parameters);
		String args = "";
		if (parameters.length > 0) {
			args = ", " + objs;
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("retType", sRetType);
		map.put("className", className);
		map.put("methodName", methodName);
		map.put("args", args);
		map.put("objs", objs);

		String template = null;

		// 戻り値がある場合、指定した戻り値を返すモック生成メソッドを作る。
		if (!sRetType.equals("void")) {
			if (isStatic) {
				// 静的メソッドの場合
				// setReturnValue_
				if (isPrivate) {
					template = "setReturnValue_private.vm";
				} else {
					template = "setReturnValue_public.vm";
				}
			} else {
				// インスタンスメソッドの場合
				// getMockWithReturnValue_
				if (isPrivate) {
					template = "getMockWithReturnValue_private.vm";
				} else {
					template = "getMockWithReturnValue_public.vm";
				}
			}

		} else {
			// 戻り値がない場合、doNothingを設定したモック生成メソッドを作る。
			if (isStatic) {
				// 静的メソッドの場合
				// setDoNothing_
				template = "setDoNothing.vm";
			} else {
				// インスタンスメソッドの場合
				// getMockWithDoNothing_
				if (isPrivate) {
					template = "getMockWithDoNothing_private.vm";
				} else {
					template = "getMockWithDoNothing_public.vm";
				}
			}
		}

		String output = "";
		if (template != null) {
			output = engine.merge(map, "template/" + template);
		}
		return output;
	}

	private String getParameterString(Class[] parameters) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < parameters.length; i++) {
			Class p = parameters[i];
			String str = p.getSimpleName();
			// ここの文字列は変わる。
			sb.append(convertType(str));
			if (i < parameters.length - 1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}

	String convertType(String str) {
		String ret = null;
		if (str.equals("int")) {
			ret = "Mockito.anyInt()";
		} else if (str.equals("String")) {
			ret = "Mockito.anyString()";
		} else if (str.equals("boolean")) {
			ret = "Mockito.anyBoolean()";
		} else if (str.equals("byte")) {
			ret = "Mockito.anyByte()";
		} else if (str.equals("char")) {
			ret = "Mockito.anyChar()";
		} else if (str.equals("double")) {
			ret = "Mockito.anyDouble()";
		} else if (str.equals("float")) {
			ret = "Mockito.anyFloat()";
		} else if (str.equals("Collection")) {
			ret = "Mockito.anyCollection()";
		} else if (str.equals("List")) {
			ret = "Mockito.anyList()";
		} else if (str.equals("Map")) {
			ret = "Mockito.anyMap()";
		} else if (str.equals("long")) {
			ret = "Mockito.anyLong()";
		} else if (str.equals("short")) {
			ret = "Mockito.anyShort()";
		} else {
			ret = "(" + str + ")" + "Mockito.anyObject()";
		}
		return ret;
	}

}
