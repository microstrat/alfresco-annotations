package it.cosenonjaviste.alfresco.annotations.processors.compiletime;

import it.cosenonjaviste.alfresco.annotations.WebScriptDescriptor;
import it.cosenonjaviste.alfresco.annotations.constants.AuthenticationType;
import it.cosenonjaviste.alfresco.annotations.constants.FormatType;
import it.cosenonjaviste.alfresco.annotations.constants.TransactionType;
import org.junit.Test;

import java.io.StringWriter;
import java.io.Writer;

import static org.junit.Assert.*;

/**
 * Tests for {@link WebScriptDescriptorWriter#doWrite(WebScriptDescriptor, Writer)}
 *
 * @author Andrea Como
 */
public class WebScriptDescriptorWriterTest {

    @Test
    public void shouldGenerateDefaultDescriptor() {

        WebScriptDescriptor annotation = TestWebscript1.class.getAnnotation(WebScriptDescriptor.class);

        String descriptor = fillTemplateIntoString(annotation);

        assertNotNull(descriptor);
        assertTrue(descriptor.contains("<shortname>test1</shortname>"));
        assertTrue(descriptor.contains("<description></description>"));
        assertTrue(descriptor.contains("<url><![CDATA[/v1/test]]></url>"));
        assertTrue(descriptor.contains("<authentication>none</authentication>"));
        assertTrue(descriptor.contains("<format default=\"html\">any</format>"));
        assertFalse(descriptor.contains("<transaction>"));
    }

    @Test
    public void shouldGenerateMultipleUri() {

        WebScriptDescriptor annotation = TestWebscript2.class.getAnnotation(WebScriptDescriptor.class);

        String descriptor = fillTemplateIntoString(annotation);

        assertNotNull(descriptor);
        assertTrue(descriptor.contains("<url><![CDATA[/v1/test]]></url>"));
        assertTrue(descriptor.contains("<url><![CDATA[/v1/test?q={q}]]></url>"));
    }

    @Test
    public void shouldHaveAuthAndRunAsAttribute() {

        WebScriptDescriptor annotation = TestWebscript3.class.getAnnotation(WebScriptDescriptor.class);

        String descriptor = fillTemplateIntoString(annotation);

        assertNotNull(descriptor);
        assertTrue(descriptor.contains("<authentication runas=\"Joe\">user</authentication>"));
    }

    @Test
    public void shouldHaveJsonDefaultFormat() {

        WebScriptDescriptor annotation = TestWebscript4.class.getAnnotation(WebScriptDescriptor.class);

        String descriptor = fillTemplateIntoString(annotation);

        assertNotNull(descriptor);
        assertTrue(descriptor.contains("<format default=\"json\">any</format>"));
    }

    @Test
    public void shouldHaveTransactionLevel() {

        WebScriptDescriptor annotation = TestWebscript5.class.getAnnotation(WebScriptDescriptor.class);

        String descriptor = fillTemplateIntoString(annotation);

        assertNotNull(descriptor);
        assertTrue(descriptor.contains("<transaction>requiresnew</transaction>"));
    }

    private String fillTemplateIntoString(WebScriptDescriptor annotation) {
        StringWriter writer = new StringWriter();
        new WebScriptDescriptorWriter(null).doWrite(annotation, writer);

        return writer.toString();
    }

    @WebScriptDescriptor(shortName = "test1", urls = "/v1/test")
    private static class TestWebscript1 {

    }

    @WebScriptDescriptor(shortName = "test2", urls = {"/v1/test", "/v1/test?q={q}"})
    private static class TestWebscript2 {

    }

    @WebScriptDescriptor(shortName = "test3", urls = {"/v1/test", "/v1/test?q={q}"}, runAs = "Joe", authentication = AuthenticationType.USER)
    private static class TestWebscript3 {

    }

    @WebScriptDescriptor(shortName = "test4", urls = "/v1/test?q={q}", format = FormatType.JSON, authentication = AuthenticationType.USER)
    private static class TestWebscript4 {

    }

    @WebScriptDescriptor(shortName = "test5", urls = "/v1/test?q={q}", transaction = TransactionType.REQUIRES_NEW)
    private static class TestWebscript5 {

    }
}