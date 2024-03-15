#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
#parse("File Header.java")

import javax.annotation.Nonnull;

import nl.c2c.ac.domino.database.AcDatabase;
import nl.c2c.ac.util.config.AcConfig;
import nl.c2c.ac.util.config.ConfigKey;
import nl.c2c.ac.util.config.model.CasewareCloudConfig;

/**
 * ${NAME} service.
 */
public final class ${NAME}Service {

	${NAME}Service() {
		// empty constructor
	}

	/**
	 * Gets ${NAME}Config or exception.
	 *
	 * @param acConfig
	 *            the ac config
	 * @param database
	 *            the database to get the key from
	 * @return the ${NAME}Config or exception
	 */
	public static @Nonnull ${NAME}Config getConfigOrException(@Nonnull AcConfig acConfig, @Nonnull AcDatabase database) {
		return acConfig.getConfigSettingOrException(${NAME}Config.class, ConfigKey.buildToKey(database.getFilePath().toString()));
	}
}