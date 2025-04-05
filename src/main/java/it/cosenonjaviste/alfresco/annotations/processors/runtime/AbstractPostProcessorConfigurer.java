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

import org.apache.commons.logging.Log;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.StringUtils;

import java.util.Arrays;

import static org.apache.commons.logging.LogFactory.getLog;

/**
 * <code>BeanFactoryPostProcessor</code> for annotation processing during Spring context startup.
 *
 * <br>
 * Mostly taken from <a href="https://jira.spring.io/browse/SPR-6343">...</a> and
 * <a href="https://github.com/janesser/spring-framework/blob/SPR-6343/spring-context/src/main/java/org/springframework/context/annotation/ChildOfConfigurer.java">...</a>
 *
 * @author Jan Esser
 * @cnj.editor Andrea Como
 */
public abstract class AbstractPostProcessorConfigurer implements BeanFactoryPostProcessor, PriorityOrdered {
    private static final Log log = getLog(AbstractPostProcessorConfigurer.class);
    private int order = Ordered.LOWEST_PRECEDENCE;

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        Arrays.stream(beanDefinitionNames)
                .forEach(definitionName -> processBeanDefinition(beanFactory, definitionName));
    }

    private void processBeanDefinition(ConfigurableListableBeanFactory beanFactory, String definitionName) {
        new BeanDefinitionProcessor(beanFactory, definitionName).process();
    }

    /**
     * Performs the bean post-processing.
     *
     * @param beanFactory the bean factory
     * @param bd the bean definition
     * @param beanClassName the class name of the bean
     * @param definitionName the definition name of the bean
     * @throws FatalBeanException if there is an error processing the bean
     */
    protected abstract void processBeanDefinition(ConfigurableListableBeanFactory beanFactory,
                                                  BeanDefinition bd,
                                                  String beanClassName,
                                                  String definitionName)
            throws FatalBeanException;

    @Override
    public int getOrder() {
        return order;
    }

    @SuppressWarnings("unused")
    public void setOrder(int order) {
        this.order = order;
    }

    private class BeanDefinitionProcessor {
        private final ConfigurableListableBeanFactory beanFactory;
        private final String definitionName;
        private BeanDefinition beanDefinition;
        private String beanClassName;

        public BeanDefinitionProcessor(ConfigurableListableBeanFactory beanFactory, String definitionName) {
            this.beanFactory = beanFactory;
            this.definitionName = definitionName;
        }

        public void process() {
            try {
                beanDefinition = beanFactory.getBeanDefinition(definitionName);

                beanClassName = beanDefinition.getBeanClassName();
                if (StringUtils.hasText(beanClassName)) {
                    processBeanClass();
                }
            } catch (NoSuchBeanDefinitionException ex) {
                log.warn(ex.getMessage());
            }
        }

        private void processBeanClass() {
            try {
                processBeanDefinition(beanFactory, beanDefinition, beanClassName, definitionName);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new FatalBeanException("Unknown class defined.", e);
            }
        }
    }
}
