/*
 * Copyright 2002-2015 the original author or authors.
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
package it.cosenonjaviste.alfresco.annotations.processors.runtime;

import it.cosenonjaviste.alfresco.annotations.WebScript;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * <code>BeanFactoryPostProcessor</code> for {@link WebScriptConfigurer} annotation.
 * <p>
 * Creates webscript bean name according to Alfresco requirements (<code>webscript.[name].[http-method]</code>)
 * from {@link WebScript} instance
 *
 * @author Andrea Como
 */
@Component
public class WebScriptConfigurer extends AbstractPostProcessorConfigurer {
    private static final Log log = LogFactory.getLog(WebScriptConfigurer.class);

    @Override
    protected void processBeanDefinition(ConfigurableListableBeanFactory beanFactory, BeanDefinition bd, String beanClassName, String definitionName) throws FatalBeanException {
        if (beanFactory instanceof DefaultListableBeanFactory factory) {
            try {
                final WebScript webScript = AnnotationUtils.findAnnotation(Class.forName(beanClassName), WebScript.class);
                if (webScript != null) {
                    String beanName = webScript.value();
                    if (StringUtils.hasText(beanName)) {
                        final String webScriptName = "webscript." + beanName + "." + webScript.method().toString().toLowerCase();
                        factory.removeBeanDefinition(definitionName);
                        factory.registerBeanDefinition(webScriptName, bd);
                    } else {
                        throw new FatalBeanException(String.format("%s is @WebScript annotated, but no value set.", beanClassName));
                    }
                }
            } catch (ClassNotFoundException e) {
                log.warn(String.format("ClassNotFoundException while searching for ChildOf annotation on bean name '%s' of type '%s'. This error is expected on Alfresco Community 4.2.c. for some classes in package 'org.alfresco.repo'", definitionName, beanClassName));
            }
        } else {
            log.error(String.format("Unable to register '%s' as webscript because beanFactory is not instance of 'DefaultListableBeanFactory'", definitionName));
        }
    }
}