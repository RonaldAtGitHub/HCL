#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
#parse("File Header.java")

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.c2c.ac.testannotations.tagged.UnitTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link ${CLASS_NAME}} test.
 */
@UnitTest
@ExtendWith(MockitoExtension.class)
class ${NAME} {
  ${BODY}  
}