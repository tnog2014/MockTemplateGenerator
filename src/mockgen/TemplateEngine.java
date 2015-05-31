package mockgen;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class TemplateEngine {

	public static void main(String[] args) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "JJJJJ");
		String res = new TemplateEngine().merge(map, "template/mytemplate.vm");
		System.out.println(res);
	}

	public String merge(Map<String, String> map, String templatePath)
			throws Exception {

		Properties prop = new Properties();
		prop.setProperty("input.encoding", "UTF-8");
		prop.setProperty("output.encoding", "UTF-8");
		Velocity.init(prop);

		VelocityContext context = new VelocityContext();
		for (Entry<String, String> entry : map.entrySet()) {
			context.put(entry.getKey(), entry.getValue());
		}
		Template template = Velocity.getTemplate(templatePath);
		StringWriter sw = new StringWriter();
		template.merge(context, sw);
		return sw.toString();
	}

}