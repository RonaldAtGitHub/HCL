#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
#parse("File Header.java")

import static nl.c2c.ac.util.config.AcConfig.*;

import com.google.auto.value.AutoValue;

import nl.c2c.ac.util.config.AcConfigSetting;

@AutoValue
@SettingAutoValue()
public abstract class ${NAME}Config implements AcConfigSetting {
	private static final long serialVersionUID = 1L;

	//type your variable name and press: ctrl + alt + T select acget

	public static ${NAME}Config create(
			//type your variable name and press -> ctrl + alt + T select acpos 
			) {
		return new AutoValue_${NAME}Config(
				variable);
	}
}