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

import it.cosenonjaviste.alfresco.annotations.ChildOf;
import org.apache.commons.logging.Log;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static org.apache.commons.logging.LogFactory.getLog;


/**
 * <code>BeanFactoryPostProcessor</code> for {@link ChildOf} annotation.
 * <br>
 * Mostly taken from <a href="https://jira.spring.io/browse/SPR-6343">...</a> and
 * <a href="https://github.com/janesser/spring-framework/blob/SPR-6343/spring-context/src/main/java/org/springframework/context/annotation/ChildOfConfigurer.java">...</a>
 *
 * @author Jan Esser
 * @cnj.editor Andrea Como
 */
@Component
public class ChildOfConfigurer extends AbstractPostProcessorConfigurer {
    private static final Log log = getLog(ChildOfConfigurer.class);

    @Override
    protected void processBeanDefinition(ConfigurableListableBeanFactory beanFactory, BeanDefinition bd, String beanClassName, String definitionName) throws FatalBeanException {
        try {
            final ChildOf childOf = AnnotationUtils.findAnnotation(Class.forName(beanClassName), ChildOf.class);
            if (childOf != null) {
                final String parentName = childOf.value();
                if (StringUtils.hasText(parentName)) {
                    bd.setParentName(parentName);
                } else {
                    throw new FatalBeanException(String.format("%s is @ChildOf annotated, but no value set.", beanClassName));
                }
            }
        } catch (ClassNotFoundException e) {
            log.warn(String.format("ClassNotFoundException while searching for ChildOf annotation on bean name '%s' of type '%s'. This error is expected on Alfresco Community 4.2.c. for some classes in package 'org.alfresco.repo'", definitionName, beanClassName));
        }
    }
}